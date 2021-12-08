<template>
  <div>
      <div style="margin-bottom: 10px">
        <va-breadcrumbs separator=">">
            <va-breadcrumbs-item label="实验管理" disabled />
            <va-breadcrumbs-item label="我的实验" to="/home/mytasks" />
        </va-breadcrumbs>
      </div>
    <va-card>
      <va-card-title style="font-size: 20px">我的实验</va-card-title>
        <va-card-content style="text-align: left">


            <!-- <va-accordion v-model="value" style="width: 400px;">
    <va-collapse
      v-for="(collapse, index) in collapses"
      :key="index"
      :header="collapse.title"
    >
      <va-button  @click="this.$router.push({name: 'Exam'})">
        {{ collapse.content }}
      </va-button>
    </va-collapse>
  </va-accordion> -->


          <div style="font-size: 20px; font-weight: bold; color: #777; height: 40px; line-height: 40px">未完成<va-icon color="#28609d" name="announcement" /></div>
            <va-card 
            v-for="(task, index) in tasksUnfinished"
            :key="index"
            :color="task[6] ? '#ac9b91' : '#b5c4b1'" 
            gradient
            style="margin-bottom: 10px"
            >
              <va-card-content style="rgb(60, 60, 60); font-weight: bold">
                <div style="display: flex">
                  <!-- va-card高度: 76px -->
                  <!-- va-card高度: 36px -->
                  <div style="line-height: 36px; width: 33%; font-size: 18px">{{task[4]}}</div>
                  <div style="line-height: 36px; width: 24%; font-size: 15px">{{task[2]}}</div>
                  <div style="line-height: 36px; width: 15%">截止日期：{{task[5] ? task[5] : "暂无"}}</div>
                  <div v-show="isOverdue(task[5])" style="line-height: 36px; width: 18%; color: #e00"><va-icon color="#e00" name="error_outline" />已逾期</div>
                  <div v-show="!isOverdue(task[5])" style="line-height: 36px; width: 18%"/>
                  <div><va-button @click="switchToCourseUnfinished(index)" color="#e0e5df" style="color: rgb(40,40,40)">点击进入</va-button></div>
                </div>
              </va-card-content>
            </va-card>

          <div style="height: 20px" />

          <div style="font-size: 20px; font-weight: bold; color: #777; height: 40px; line-height: 40px">已完成<va-icon color="#51b296" name="thumb_up" /></div>
            <va-card 
            v-for="(task, index) in tasksFinished"
            :key="index"
            :color="task[6] ? '#ac9b91' : '#b5c4b1'" 
            gradient
            style="margin-bottom: 10px"
            >
              <va-card-content style="rgb(60, 60, 60); font-weight: bold">
                <div style="display: flex">
                  <!-- va-card高度: 76px -->
                  <!-- va-card高度: 36px -->
                  <div style="line-height: 36px; width: 33%; font-size: 18px">{{task[4]}}</div>
                  <div style="line-height: 36px; width: 24%; font-size: 15px">{{task[2]}}</div>
                  <div style="line-height: 36px; width: 32%">截止日期：{{task[5] ? task[5] : "暂无"}}</div>
                  <div><va-button @click="switchToCourseFinished(index)" color="#e0e5df" style="color: rgb(40,40,40)">点击进入</va-button></div>
                </div>
              </va-card-content>
            </va-card>

        </va-card-content>
    </va-card>
  </div>
  
</template>

<script>
export default {
  data () {
    return {
      value: [false, false, false],
      collapses: [
        { title: '考试1', content: '参加考试' },
        { title: '考试2', content: '参加考试' },
        { title: '考试3', content: '参加考试' },
      ],

      studentId: '',

      tasksFinished: [],
      tasksUnfinished: [],
    }
  },
  mounted () {
    this.studentId = localStorage.getItem("userId")
    console.log("studentId: " + this.studentId)

    fetch(this.$URL + "/task/getAll/finished/deadline/asc?studentId=" + this.studentId, {
      method: "GET"
    }).then(response => {
      // console.log(response)
      let result = response.json()
      result.then(res => {
        console.log(res)
        this.tasksFinished = res
      })
    })

    fetch(this.$URL + "/task/getAll/unfinished/deadline/asc?studentId=" + this.studentId, {
      method: "GET"
    }).then(response => {
      // console.log(response)
      let result = response.json()
      result.then(res => {
        console.log(res)
        this.tasksUnfinished = res
      })
    })
  },
  methods: {
    switchToCourseUnfinished(index) {
      this.$router.push({
        name: this.tasksUnfinished[index][6] ? 'ComplexTask' : 'OnlineTask',
        params: {
            taskId: this.tasksUnfinished[index][3],
            deadline: this.tasksUnfinished[index][5]
        }
      })
    },
    switchToCourseFinished(index) {
      this.$router.push({
        name: this.tasksFinished[index][6] ? 'ComplexTask' : 'OnlineTask',
        params: {
            taskId: this.tasksFinished[index][3],
            deadline: this.tasksFinished[index][5]
        }
      })
    },
    isOverdue (date) {
      /**
       * now的写法用当前时间转日期再转时间来进行比较的原因是
       * 如果直接用当前时间和设定的ddl(只精确到日期)来比较的话，
       * ddl转时间是当天的0点整，但是设定的ddl本身意义应该是到当天结束为止。
       * 所以把当前时间转换为当天0点整再进行比较就能等效上述效果。
       */
      let deadline = new Date(date)
      let now = new Date(new Date().toLocaleDateString())
      if (date == '' || date == undefined || date == null) {
        return false
      } else if (now <= deadline) {
        return false
      } else {
        return true
      }
    }
  }
}
</script>

<style>

</style>