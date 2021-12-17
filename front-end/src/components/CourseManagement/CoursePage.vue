<template>
  <va-card gradient color="#e0e5df">
      <va-card-content style="text-align: left">
          <div class="course-title">{{this.courseName}}</div>
          <div class="course-description">{{this.courseDescription}}</div>
            <va-card 
            v-for="(task, index) in simpleTasks"
            :key="index"
            color="#b5c4b1" 
            gradient
            style="margin-bottom: 10px"
            >
              <va-card-content style="rgb(60, 60, 60); font-weight: bold">
                <div style="display: flex">
                  <!-- va-card高度: 76px -->
                  <!-- va-card高度: 36px -->
                  <div style="line-height: 36px; width: 35%; font-size: 18px">{{task.name}}</div>
                  <div style="line-height: 36px; width: 37%">截止日期：{{task.deadline ? task.deadline : "暂无"}}</div>
                  <div v-show="isOverdue(task.deadline)" style="line-height: 36px; width: 18%; color: #e00"><va-icon color="#e00" name="error_outline" />已逾期</div>
                  <div v-show="!isOverdue(task.deadline)" style="line-height: 36px; width: 18%"/>
                  <div><va-button @click="switchToTaskSimple(index)" color="#e0e5df" style="color: rgb(40,40,40)">点击进入</va-button></div>
                </div>
              </va-card-content>
            </va-card>
            <div style="height: 20px" />
            <va-card 
            v-for="(task, index) in complexTasks"
            :key="index"
            color="#ac9b91" 
            gradient
            style="margin-bottom: 10px"
            >
              <va-card-content style="rgb(60, 60, 60); font-weight: bold">
                <div style="display: flex">
                  <!-- va-card高度: 76px -->
                  <!-- va-card高度: 36px -->
                  <div style="line-height: 36px; width: 35%; font-size: 18px">{{task.name}}</div>
                  <div style="line-height: 36px; width: 37%">截止日期：{{task.deadline ? task.deadline : "暂无"}}</div>
                  <div v-show="isOverdue(task.deadline)" style="line-height: 36px; width: 18%; color: #e00"><va-icon color="#e00" name="error_outline" />已逾期</div>
                  <div v-show="!isOverdue(task.deadline)" style="line-height: 36px; width: 18%"/>
                  <div><va-button @click="switchToTaskComplex(index)" color="#e0e5df" style="color: rgb(40,40,40)">点击进入</va-button></div>
                </div>
              </va-card-content>
            </va-card>
      </va-card-content>
  </va-card>
</template>

<script>
export default {
    data () {
        return {
            id: '',
            courseId: '',
            courseName: '',
            courseDescription: '',
            year: '',
            semester: '',

            courseInfo: [],

            courseDeadlines: [],

            tasks: [],

            simpleTasks: [],
            complexTasks: []
        }
    }, 
    mounted () {
        this.id = localStorage.getItem("userId")
        this.courseId = localStorage.getItem("courseId")
        console.log("从上个页面传来的params.courseId: " + this.$route.params.courseId)
        if (this.$route.params.courseId == undefined) {
            console.log("从子路由跳到父路由，需要重新查询课程数据")
            fetch(this.$URL + '/=' + this.courseId, {
                method: "GET"
            }).then(response => {
                // console.log(response)
                let result = response.json()
                result.then(res => {
                    // console.log(res)
                    this.courseName = res.name
                    this.courseDescription = res.description
                    this.year = res.year
                    this.semester = res.semester
                })
            })
        } else {
            console.log("从父路由跳到子路由，直接把查询结果传参即可，无需重新查询")
            this.courseName = this.$route.params.name
            this.courseDescription = this.$route.params.description
            this.year = this.$route.params.year
            this.semester = this.$route.params.semester
            console.log("从上个页面传来的courseName: ", this.courseName)
        }

        fetch(this.$URL + "/task/get/simple?courseId=" + this.courseId, {
            method: "GET"
        }).then(response => {
            // console.log(response)
            let result = response.json()
            result.then(res => {
                // console.log(res)
                this.simpleTasks = res
            })
        })
        fetch(this.$URL + "/task/get/complex?courseId=" + this.courseId, {
          method: "GET"
        }).then(response => {
          let result = response.json()
          result.then(res => {
            this.complexTasks = res
          })
        })
    },
  methods: {
    switchToTaskSimple (index) {
      // console.log("调用switchToCourse时，courseId传过去了吗？ courseId的值是：" + id)
      // console.log("调用switchToCourse时，index的值是：" + index)
      // console.log(this.tasks[index])
      console.log("taskName：" + this.simpleTasks[index].name)
      this.$router.push({
        name: 'OnlineTask',
        params: {
            taskId: this.simpleTasks[index].id,
            taskName: this.simpleTasks[index].name,
            courseId: this.simpleTasks[index].courseId,
            description: this.simpleTasks[index].description,
            type: this.simpleTasks[index].type,
            url: this.simpleTasks[index].url,
            deadline: this.simpleTasks[index].deadline
        }
      })
    },
    switchToTaskComplex (index) {
      // console.log("调用switchToCourse时，courseId传过去了吗？ courseId的值是：" + id)
      // console.log("调用switchToCourse时，index的值是：" + index)
      // console.log(this.tasks[index])
      this.$router.push({
        name: 'ComplexTask',
        params: {
            taskId: this.complexTasks[index].id,
            taskName: this.complexTasks[index].name,
            courseId: this.complexTasks[index].courseId,
            description: this.complexTasks[index].description,
            type: this.complexTasks[index].type,
            url: this.complexTasks[index].url,
            deadline: this.complexTasks[index].deadline
        }
      })
    },
    isOverdue(date) {
      /**
       * now的写法用当前时间转日期再转时间来进行比较的原因是
       * 如果直接用当前时间和设定的ddl(只精确到日期)来比较的话，
       * ddl转时间是当天的0点整，但是设定的ddl本身意义应该是到当天结束为止。
       * 所以把当前时间转换为当天0点整再进行比较就能等效上述效果。
       */
      let now = new Date(new Date().toLocaleDateString())
      let deadline = new Date(date)
      if (date == null || date == '' || date == undefined) {
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
.course-title {
    /* text-align: left; */
    font-size: 25px;
    font-weight: bold;
    color: #656565;
    margin-bottom: 15px
}

.course-description {
    font-size: 15px;
    margin-bottom: 20px;
}
</style>