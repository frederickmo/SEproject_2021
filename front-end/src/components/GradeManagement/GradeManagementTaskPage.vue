<template>
  <div>
      <div style="margin-bottom: 10px">
        <va-breadcrumbs separator=">">
            <va-breadcrumbs-item label="成绩管理" disabled />
            <va-breadcrumbs-item label="所有课程" to="/home/grademanagement" />
            <va-breadcrumbs-item label="所有实验项目" to="/home/grademanagement/course" />
            <va-breadcrumbs-item label="学生提交记录" to="/home/grademanagement/course/task" />
        </va-breadcrumbs>
      </div>
    <va-card>
      <el-table
      :data="
      submitRecordsJson.filter(
        (data) =>
          !search | data.name.toLowerCase().includes(search.toLowerCase())
      )"
      >
        <el-table-column label="学号" prop="id" />
        <el-table-column label="姓名" prop="name" />
        <el-table-column label="已提交" prop="finished" />
        <el-table-column label="成绩" prop="score" />
        <el-table-column align="right">
          <template #header>
            <el-input v-model="search" size="mini" placeholder="请输入" />
          </template>
          <template #default="scope">
            <div style="display: flex">
              <a-button @click="handleFetchTaskFile(scope.$index)">作业</a-button>
              <a-button @click="handleMark(scope.row)">评分</a-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <!-- <el-table
      :data="
      notSubmitScoreGivenRecordsJson.filter(
        (data) =>
          !search | data.name.toLowerCase().includes(search.toLowerCase())
      )"
      v-show="notSubmitScoreGivenRecords.length"
      >
        <el-table-column label="学号" prop="id" />
        <el-table-column label="姓名" prop="name" />
        <el-table-column label="已提交" prop="finished" />
        <el-table-column label="成绩" prop="score" />
        <el-table-column align="right">
          <template #header>
            <el-input v-model="search" size="mini" placeholder="请输入" />
          </template>
          <template #default="scope">
            <div style="display: flex">
              <a-button @click="handleFetchTaskFile(scope.$index)">作业</a-button>
              <a-button @click="handleMark(scope.row)">评分</a-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <el-table
      :data="
      notSubmitRecordsJson.filter(
        (data) =>
          !search | data.name.toLowerCase().includes(search.toLowerCase())
      )"
      v-show="notSubmitRecords.length"
      >
        <el-table-column label="学号" prop="id" />
        <el-table-column label="姓名" prop="name" />
        <el-table-column label="已提交" prop="finished" />
        <el-table-column label="成绩" prop="score" />
        <el-table-column align="right">
          <template #header>
            <el-input v-model="search" size="mini" placeholder="请输入" />
          </template>
          <template #default="scope">
            <div style="display: flex">
              <a-button @click="handleFetchTaskFile(scope.$index)">作业</a-button>
              <a-button @click="handleMark(scope.row)">评分</a-button>
            </div>
          </template>
        </el-table-column>
      </el-table> -->
    </va-card>
    <a-modal
    style="text-align: center"
    v-model:visible="markModalVisible">
      <div>分数</div>
      <a-input-number v-model="markedStudentScore" :min="0" :max="100" />
      <va-button @click="updateScore">提交</va-button>
    </a-modal>
  </div>
</template>

<script>


export default {


  
  data() {

      return {
        taskId: '',
        taskName: '',
        teacherId: '',

        markedStudentId: '',
        markedStudentScore: 0,

        submitRecords: [],
        submitRecordsJson: [],
        notSubmitRecords: [],
        notSubmitRecordsJson: [],
        notSubmitScoreGivenRecords: [],
        notSubmitScoreGivenRecordsJson: [],

        columns: [
          {title: '学号', dataIndex: 'id'},
          {title: '姓名', dataIndex: 'name'},
          {title: '已提交', dataIndex: 'finished'},
          {title: '成绩', dataIndex: 'score'}
        ],
        rowSelection: {
          type: 'checkbox',
          showCheckedAll: true
        },

        markModalVisible: false,

        // newScore: 0,

        search: '',

      }
      

    
  },
  mounted() {

    this.taskId = this.$route.params.taskId ? this.$route.params.taskId : localStorage.getItem("currentTaskId")

    console.log("taskId: " + this.taskId)

    this.teacherId = localStorage.getItem("userId")

    fetch(this.$URL + "/finishes/get/record/detail/task/finished?taskId=" + this.taskId, {
      method: "GET"
    }).then(response => {
      console.log(response)
      let result = response.json()
      result.then(res => {
        console.log(res)
        this.submitRecords = res

        for (let i = 0; i < this.submitRecords.length; ++i) {
          let json = {
            id: this.submitRecords[i][1],
            name: this.submitRecords[i][2],
            finished: this.submitRecords[i][4] ? "已提交" : "未提交",
            score: this.submitRecords[i][5]
          }
          this.submitRecordsJson.push(json)
        }

        console.log("submitRecordJson: ", this.submitRecordsJson)
      })
    })


    fetch(this.$URL + "/finishes/get/record/detail/task/unfinished/scoreGiven?taskId=" + this.taskId, {
      method: "GET"
    }).then(response => {
      console.log(response)
      let result = response.json()
      result.then(res => {
        console.log(res)
        this.notSubmitScoreGivenRecords = res

        for (let i = 0; i < this.notSubmitScoreGivenRecords.length; ++i) {
          console.log("submitRecords的第0个元素:", this.notSubmitScoreGivenRecords[0])
          let json = {
            id: this.notSubmitScoreGivenRecords[i][1],
            name: this.notSubmitScoreGivenRecords[i][2],
            finished: this.notSubmitScoreGivenRecords[i][3] ? "已提交" : "未提交",
            score: this.notSubmitScoreGivenRecords[i][4]
          }
          this.notSubmitScoreGivenRecordsJson.push(json)
        }

        console.log("notSubmitScoreGivenRecordJson: ", this.notSubmitScoreGivenRecordsJson)
      })
    })

    fetch(this.$URL + "/finishes/get/record/detail/task/unfinished/scoreNotGiven?taskId=" + this.taskId, {
      method: "GET"
    }).then(response => {
      console.log(response)
      let result = response.json()
      result.then(res => {
        console.log(res)
        this.notSubmitRecords = res

        for (let i = 0; i < this.notSubmitRecords.length; ++i) {
          console.log("submitRecords的第0个元素:", this.notSubmitRecords[0])
          let json = {
            id: this.notSubmitRecords[i].id,
            name: this.notSubmitRecords[i].name,
            finished: "未提交",
            score: 0
          }
          this.notSubmitRecordsJson.push(json)
        }

        console.log("notSubmitRecordsJson: ", this.notSubmitRecordsJson)
        
      })
    })
  },
  methods: {
    handleFetchTaskFile(index) {
      console.log(index)
      console.log(this.submitRecords[index])
      window.open(this.$URL + "/file/download/taskUpload/" + this.taskId + "/" + this.submitRecords[index][1] + "/" + this.submitRecords[index][3])
    },
    handleMark(row) {
      // this.markedStudentId = id
      // this.markedStudentScore = score
      // console.log("markedStudentId上传成功了吗？", this.markedStudentId)
      // console.log("markedStudentScore上传成功了吗?", this.markedStudentScore)
      // console.log("????\n???\n?????\n???????\n")
      console.log("taskId", this.taskId)
      console.log(row)
      this.markedStudentId = row.id
      this.markedStudentScore = row.score
      this.markModalVisible = !this.markModalVisible
    },
    updateScore() {
      console.log(this.markedStudentId, this.markedStudentScore)

      let req = {
        teacherId: this.teacherId,
        studentId: this.markedStudentId,
        taskId: this.taskId,
        score: this.markedStudentScore
      }

      fetch(this.$URL + "/checks/add", {
        method: "POST",
        body: JSON.stringify(req),
        headers: { "Content-Type": "application/json" }
      }).then(response => {
        console.log(response)
        let result = response.json()
        result.then(res => {
          console.log(res)
          if (res.status == 200) {
            this.$notification.success("成绩上传/更新成功")
            /**
             * 以下replace语句是用来跳转到一个空白页面{name: 'Refresh'}实现当前界面自动重新加载的效果
             * 
             * 经实践，填path比填name更快。
             */
            // this.$router.replace({name: 'Refresh'})
            this.$router.replace({path: '/refresh'})
          } else {
            this.$notification.error("成绩上传失败")
          }
        })
      })
    }
  }
}
</script>

<style>
.table-example--pagination {
    text-align: center;
    text-align: -webkit-center;
  }
</style>