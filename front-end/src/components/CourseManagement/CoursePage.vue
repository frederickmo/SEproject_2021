<template>
  <va-card gradient color="#e0e5df">
      <va-card-content style="text-align: left">
          <div class="course-title">{{this.courseName}}</div>
          <div class="course-description">{{this.courseDescription}}</div>
            <va-card 
            v-for="(task, index) in tasks"
            :key="index"
            :color="task.type? '#ac9b91':'#b5c4b1'" 
            gradient
            style="margin-bottom: 10px"
            >
              <va-card-content style="rgb(60, 60, 60); font-weight: bold">
                <div style="display: flex">
                  <!-- va-card高度: 76px -->
                  <!-- va-card高度: 36px -->
                  <div style="line-height: 36px; width: 40%; font-size: 18px">{{task.name}}</div>
                  <div style="line-height: 36px; width: 50%">截止日期：{{task.deadline}}</div>
                  <div><va-button @click="switchToCourse(task.id, index)" color="#e0e5df" style="color: rgb(40,40,40)">点击进入</va-button></div>
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
        }
    }, 
    mounted () {
        this.id = localStorage.getItem("userId")
        this.courseId = localStorage.getItem("courseId")
        console.log("从上个页面传来的params.courseId: " + this.$route.params.courseId)
        if (this.$route.params.courseId == undefined) {
            console.log("从子路由跳到父路由，需要重新查询课程数据")
            fetch(this.$URL + '/course/get?id=' + this.courseId, {
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

        fetch(this.$URL + "/task/get/course?courseId=" + this.courseId, {
            method: "GET"
        }).then(response => {
            console.log(response)
            let result = response.json()
            result.then(res => {
                console.log(res)
                this.tasks = res

                // console.log("获取的截止时间的类型：" + typeof res[0].deadline)

                // for(let i = 0; i < res.length; ++i) {
                //   this.courseDeadlines.push(res[i].deadline)
                // }
                // console.log(this.courseDeadlines)
            })
        })
    },
  methods: {
    switchToCourse (id, index) {
      // console.log("调用switchToCourse时，courseId传过去了吗？ courseId的值是：" + id)
      // console.log("调用switchToCourse时，index的值是：" + index)
      // console.log(this.tasks[index])
      this.$router.push({
        name: this.tasks[index].type ? 'ComplexTask' : 'OnlineTask',
        params: {
            taskId: this.tasks[index].id,
            taskName: this.tasks[index].name,
            courseId: this.tasks[index].courseId,
            description: this.tasks[index].description,
            type: this.tasks[index].type,
            url: this.tasks[index].url,
            deadline: this.tasks[index].deadline
        }
      })
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