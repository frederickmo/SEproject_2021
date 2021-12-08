<template>
  <div>
      <div style="margin-bottom: 10px">
        <va-breadcrumbs separator=">">
            <va-breadcrumbs-item label="成绩管理" disabled />
            <va-breadcrumbs-item label="所有课程" to="/home/grademanagement" />
        <va-breadcrumbs-item label="所有实验项目" to="/home/grademanagement/course" />
        </va-breadcrumbs>
      </div>
    <va-card>
      <va-card-title style="font-size: 20px">所有课程项目</va-card-title>
        <va-card-content v-show="tasks.length">
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
                  <div style="line-height: 36px; width: 90%; font-size: 18px">{{task.name}}</div>
                  <div><va-button @click="switchToTask(task.id, index)" color="#e0e5df" style="color: rgb(40,40,40)">点击进入</va-button></div>
                </div>
              </va-card-content>
            </va-card>
        </va-card-content>

        <va-card-content v-show="!tasks.length">暂无课程项目</va-card-content>
    </va-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      teacherId: '',
      courseId: '',
      courseName: '',

      tasks: [],
    }
  },
  mounted() {
    console.log(this.$route.params)
    this.teacherId = localStorage.getItem("userId")
    this.courseId = localStorage.getItem("courseId") ? localStorage.getItem("courseId") : this.$route.params.courseId

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
    switchToTask (taskId, index) {
      // console.log("调用switchToCourse时，courseId传过去了吗？ courseId的值是：" + id)
      // console.log("调用switchToCourse时，index的值是：" + index)
      // console.log(this.courses[index])
      localStorage.setItem("currentTaskId", taskId)
      // console.log("设置courseID了吗？courseId=" + localStorage.getItem("courseId"))
      this.$router.push({
        name: 'GradeManagementTaskPage',
        params: {
          taskId: this.tasks[index].id,
          taskName: this.tasks[index].name,
          courseId: this.courseId,
          type: this.tasks[index].type,
          deadline: this.tasks[index].deadline
        }
      })
    }
  }
}
</script>

<style>

</style>