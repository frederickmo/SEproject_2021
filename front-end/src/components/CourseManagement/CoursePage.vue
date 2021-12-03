<template>
  <va-card gradient color="#e0e5df">
      <va-card-content style="text-align: left">
          <div class="course-title">{{this.courseName}}</div>
          <div class="course-description">{{this.courseDescription}}</div>
            <va-card 
            v-for="(task, index) in tasks"
            :key="index"
            color="#b5c4b1" 
            gradient
            style="margin-bottom: 10px"
            >
              <va-card-content style="rgb(60, 60, 60); font-weight: bold">
                <div style="display: flex">
                  <!-- va-card高度: 76px -->
                  <!-- va-card高度: 36px -->
                  <div style="line-height: 36px; width: 80%; font-size: 18px">{{task.name}}</div>
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

            tasks: [],
        }
    }, 
    mounted () {
        this.id = localStorage.getItem("id")
        this.courseId = localStorage.getItem("courseId")
        console.log("获得courseId了吗? courseId=" + this.courseId)
        console.log("params:", this.$route.params)
        console.log("params == null? " + this.$route.params == null)
        console.log("params == undefined? " + this.$route.params == undefined)
        if (this.$route.params == null) {
            console.log("执行这个了吗？")
            fetch(this.$URL + '/course/get?id=' + this.courseId, {
                method: "GET"
            }.then(response => {
                console.log(response)
                let result = response.json()
                result.then(res => {
                    console.log(res)
                    this.courseName = res.name
                    this.courseDescription = res.description
                    this.year = res.year
                    this.semester = res.semester
                })
            }))
        } else {
            console.log("是不是执行到这了")
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
            })
        })
    },
  methods: {
    switchToCourse (id, index) {
      console.log("调用switchToCourse时，courseId传过去了吗？ courseId的值是：" + id)
      console.log("调用switchToCourse时，index的值是：" + index)
      console.log(this.tasks[index])
      this.$router.push({
        name: 'Exam',
        params: {
            taskId: this.tasks[index].id,
            courseId: this.tasks[index].courseId,
            description: this.tasks[index].description,
            type: this.tasks[index].type,
            url: this.tasks[index].url
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