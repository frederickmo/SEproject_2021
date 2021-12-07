<template>
  <va-card>
      <va-card-content>
        <div style="height: 10px" />
        <a-space style="margin-bottom: 20px">
            <div style="font-size: 30px; font-weight: bold">实验报告：{{taskName}}</div>
            <div v-show="isSubmitted"><va-icon color="#89d7bc" name="check_circle" />已提交</div>
            <div v-show="!isSubmitted"><va-icon name="cancel" />未提交</div>
        </a-space>
      <div />
        <a-space style="margin-bottom: 20px">
          <span class="label">获取实验说明文档：</span>
          <va-button color="#e2e0df" style="color: black" @click="getTaskGuide">点击获取</va-button>
        </a-space>
          <div style="height: 30px" />
          <!-- <va-button color="#9fbcc2" gradient @click="submitTask">提交</va-button> -->
          <a-upload
          draggable
          accept=".doc,.docx,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,.pdf"
          :action="getFileUploadUrl()"
          :data="additionalData()"
          />
          <div style="height: 30px" />
      </va-card-content>
  </va-card>
</template>

<script>
export default {
    data () {
        return {
            taskId: '',
            taskName: '',
            deadline: '',

            studentId: '',
            courseId: '',

            isSubmitted: false,
        }
    },
    mounted () {
        this.taskId = this.$route.params.taskId
        // this.taskName = this.$route.params.taskName
        // this.deadline = this.$route.params.deadline



        /**
         * 还差日期比较的功能还没做 => 超过截止日期后无法提交
         */

        this.studentId = localStorage.getItem("userId")

        fetch(this.$URL + "/task/get?id=" + this.taskId, {
            method: "GET"
        }).then(response => {
            let result = response.json()
            result.then(res => {
                this.taskName = res.name
                this.deadline = res.deadline
            })
        })

        fetch(this.$URL + "/finishes/isFinished?studentId=" + this.studentId + "&taskId=" + this.taskId, {
            method: "GET"
        }).then(response => {
            console.log(response)
            let result = response.text()
            result.then(res => {
                if (res == "true") {
                    this.isSubmitted = true
                } else {
                    this.isSubmitted = false
                }
            })
        })

        fetch(this.$URL + "/task/get?id=" + this.taskId, {
            method: "GET"
        }).then(response => {
            let result = response.json()
            result.then(res => {
                this.courseId = res.courseId
                console.log("courseId: ", this.courseId)
            })
        })
    },
    methods: {
        getTaskGuide() {
            window.open(this.$URL + "/file/download/taskGuide/" + this.courseId + "/" + this.taskId + ".pdf");
        },
        getFileUploadUrl() {
            return this.$URL + "/finishes/upload/complex"
        },
        additionalData() {
            return {
                "newFileName": this.taskId + "_" + this.studentId + ".pdf",
                "location": "/taskUpload/" + this.courseId + "/" + this.taskId,
                "studentId": this.studentId,
                "taskId": this.taskId
            }
        }
    }
}
</script>

<style>

</style>