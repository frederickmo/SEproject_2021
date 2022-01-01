<template>
  <div>
      <div style="margin-bottom: 10px">
        <va-breadcrumbs separator=">">
            <va-breadcrumbs-item label="成绩管理" disabled />
            <va-breadcrumbs-item label="所有课程" to="/home/grademanagement" />
        <va-breadcrumbs-item label="课程成绩管理" to="/home/grademanagement/course" />
        </va-breadcrumbs>
      </div>
    <va-card>
      <va-card-title style="font-size: 20px">课程成绩管理</va-card-title>
        <va-card-content v-show="tasks.length">
          <div>
          <div style="display: flex">
              <a-button style="margin-right: 10px" @click="this.Scorestatus=1">课程项目成绩</a-button>
              <a-button @click="this.Scorestatus=2">课程成员成绩</a-button>
          </div>
          <div style="height: 12px" />
          </div>
            <!-- <va-card 
            v-for="(task, index) in tasks"
            :key="index"
            color="#b5c4b1" 
            gradient
            style="margin-bottom: 10px"
            >
              <va-card-content v-if="this.Scorestatus==1" style="rgb(60, 60, 60); font-weight: bold">
                <div style="display: flex">
                
                  <div style="line-height: 36px; width: 90%; font-size: 18px">{{task.name}}</div>
                  <div><va-button @click="switchToTask(task.id, index)" color="#e0e5df" style="color: rgb(40,40,40)">点击进入</va-button></div>
                </div>
              </va-card-content>
            </va-card> -->
          <el-table v-if="this.Scorestatus==1"
          :data="tasks.filter(
              (data) =>
                !search || data.name.toLowerCase().includes(search.toLowerCase())
            )
          "
          style="width: 100%"
          >
           
            <el-table-column label="项目名称" prop="name" />
            <el-table-column align="right">
            <template #header>
              <el-input v-model="search" size="mini" placeholder="Type to search" />
            </template>
            <template #default="scope">
              
              <el-button
                size="mini"
                type="danger"
                @click="switchToTask(scope.row)"
                >点击进入</el-button
              >
              </template>
              </el-table-column>
          </el-table>

          <el-table v-if="this.Scorestatus==2"
          stripe
          :data="student.filter(
              (data) =>
                !search || data.name.toLowerCase().includes(search.toLowerCase())
            )
          "
          style="width: 100%"
          >
            <el-table-column label="学号" prop="id" />
            <el-table-column label="姓名" prop="name" />
            <el-table-column align="right">
            <template #header>
              <el-input v-model="search" size="mini" placeholder="Type to search" />
            </template>
            <template #default="scope">
              
              <a-button style="margin-left: 10px" @click="handleMark(scope.row)">评分</a-button>
              </template>
              </el-table-column>
          </el-table>
        </va-card-content>

        <va-card-content v-show="!tasks.length">暂无课程项目</va-card-content>
    </va-card>
  </div>

  <a-modal
    v-model:visible="markModalVisible">
    <template #title>
      评分
    </template>
    <div style="text-align: center">
      
      <h3 style="text-align:left">项目得分(90%)</h3>
      <el-table 
      :data="score.filter(
          (data) =>
            !search || data.name.toLowerCase().includes(search.toLowerCase())
        )
      "
      style="width: 100%"
      >
        <el-table-column label="项目名称" prop="3" />
        <el-table-column label="得分" prop="4" />
        <el-table-column align="right">
        <template #header>
          <el-input v-model="search" size="mini" placeholder="Type to search" />
        </template>
        
          </el-table-column>
      </el-table>
      <div style="height: 20px" />
      <h3 style="text-align:left">签到次数(10%)</h3>
      <div>本学期已签到{{this.count}}次</div>
      <div style="height:100px " />
      <div style="margin-bottom: 10px">分数</div>
      <a-input-number style="width: 15%" v-model="markedStudentScore" :min="0" :max="100" />
      <a-button style="margin-left: 10px" @click="updateScore">提交</a-button>
    </div>
    </a-modal>
</template>

<script>
export default {
  data() {
    return {
      teacherId: '',
      courseId: '',
      courseName: '',
      Scorestatus:1,
      tasks: [],
      student:[],
      search:'',
      markModalVisible:false,
      studentid:'',
      markedStudentScore:'',
      score:[],
      count:''
    }
  },
  mounted() {
    console.log(this.$route.params)
    this.teacherId = localStorage.getItem("userId")
    this.courseId = localStorage.getItem("courseId") ? localStorage.getItem("courseId") : this.$route.params.courseId

    fetch(this.$URL + "/task/get/course?courseId=" + this.courseId, {
      method: "GET",
      headers: { "satoken": localStorage.getItem("token") }
    }).then(response => {
      console.log(response)
      let result = response.json()
      result.then(res => {
        console.log(res)
        this.tasks = res
      })
    })
    fetch(this.$URL + "/takes/get/course/detail?courseId=" + this.courseId, {
      method: "GET",
      headers: { "satoken": localStorage.getItem("token") }
    }).then(response => {
      console.log(response)
      let result = response.json()
      result.then(res => {
        console.log(res)
        this.student = res
        
      })
    })
  },
  methods: {
    handleMark(row) {
       fetch(this.$URL + "/finishes/get/record/detail/studentAndCourse?courseId=" + this.courseId+"&studentId="+row.id, {
        method: "GET",
        headers: { "satoken": localStorage.getItem("token") }
        }).then(response => {
          console.log(response)
          let result = response.json()
          result.then(res => {
            console.log(res)
            this.score = res
            for(var i in this.score)
            {
              if(this.score[i][4]==null)
              {
                this.score[i][4]="未批改"
              }
            }
          })
        })

        fetch(this.$URL + "/user/getSign?userId=" +row.id, {
        method: "GET",
        headers: { "satoken": localStorage.getItem("token") }
        }).then(response => {
          console.log(response)
          let result = response.json()
          result.then(res => {
            console.log(res)
            this.count=res.count
          })
        })
      //console.log("taskId", this.taskId)
      console.log(row)
      this.studentid=row.id
      // this.markedStudentId = row.id
      // this.markedStudentScore = row.score
      this.markModalVisible = !this.markModalVisible
    },
    updateScore(){
      let req = {
        studentId: this.studentid,
        courseId: this.courseId,
        score: this.markedStudentScore
      }
      console.log(req)
      fetch(this.$URL + "/takes/mark", {
        method: "PUT",
        body: JSON.stringify(req),
        headers: {
          "Content-Type": "application/json",
          "satoken": localStorage.getItem("token")
        }
      }).then(response => {
        console.log(response)
        let result = response.json()
        result.then(res => {
          console.log(res)
          if (res.code == 200) {
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
    },
    switchToTask (scope) {
      // console.log("调用switchToCourse时，courseId传过去了吗？ courseId的值是：" + id)
      // console.log("调用switchToCourse时，index的值是：" + index)
      // console.log(this.courses[index])‘
      console.log(scope)
      localStorage.setItem("currentTaskId", scope.id)
      // localStorage.setItem("currentTaskId", taskId)
      // console.log("设置courseID了吗？courseId=" + localStorage.getItem("courseId"))
      this.$router.push({
        name: 'GradeManagementTaskPage',
        params: {
          taskId: scope.id,
          taskName: scope.name,
          courseId: scope.courseId,
          type: scope.type,
          deadline: scope.deadline
        }
      })
    }
  }
}
</script>

<style>

</style>