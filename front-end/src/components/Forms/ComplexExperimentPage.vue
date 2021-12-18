<template>
  <va-card>
      <va-card-content>
        <div style="height: 15px" />
        <va-alert v-show="isOverdue()" style="width: 80%; font-size: 16px; font-weight: bold" icon="info" color="#f3d989" >
            该实验报告已逾期。您现在仅可查看，无法进行提交。
        </va-alert>
        <div style="height: 20px" />
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
          <!-- <a-upload
          draggable
          accept=".doc,.docx,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,.pdf"
          :action="getFileUploadUrl()"
          :data="additionalData()"
          /> -->
        <el-upload
            ref="upload"
            :action="getFileUploadUrl()"
            :auto-upload="false"
            :data="additionalData()"
            style="text-align: center"
            :on-success="handleOnSuccess"
            :on-error="handleOnError"
        >
            <template #trigger>


                <!-- TODO: 下面这个在trigger里的button怎么disable？ 如何禁止逾期的大型项目的提交？ -->
            <va-button  @click="handleUploadFileButton" gradient :rounded="false">选择文件</va-button>
            </template>
            <va-button style="margin-left: 20px" flat :rounded="false" @click="submitUpload">上传</va-button>
        </el-upload> 
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
            method: "GET",
            headers: { "satoken": localStorage.getItem("token") }
        }).then(response => {
            let result = response.json()
            result.then(res => {
                this.taskName = res.name
                this.deadline = res.deadline
            })
        })

        fetch(this.$URL + "/finishes/isFinished?studentId=" + this.studentId + "&taskId=" + this.taskId, {
            method: "GET",
            headers: { "satoken": localStorage.getItem("token") }
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
            method: "GET",
            headers: { "satoken": localStorage.getItem("token") }
        }).then(response => {
            let result = response.json()
            result.then(res => {
                this.courseId = res.courseId
                console.log("courseId: ", this.courseId)
            })
        })
    },
    methods: {
        isOverdue () {
        /**
         * now的写法用当前时间转日期再转时间来进行比较的原因是
         * 如果直接用当前时间和设定的ddl(只精确到日期)来比较的话，
         * ddl转时间是当天的0点整，但是设定的ddl本身意义应该是到当天结束为止。
         * 所以把当前时间转换为当天0点整再进行比较就能等效上述效果。
         */
        let deadline_date = new Date(this.deadline)
        let now = new Date(new Date().toLocaleDateString())
        if (now <= deadline_date || this.deadline == null || this.deadline == undefined || this.deadline == '') {
            return false
        } else {
            return true
        }
        },
        getTaskGuide() {
            window.open(this.$URL + "/file/download/taskGuide/" + this.courseId + "/" + this.taskId + ".pdf");
        },
        getFileUploadUrl() {
            return this.$URL + "/finishes/upload/complex"
        },
        additionalData() {
            return {
                "newFileName": this.taskId + "_" + this.studentId + ".pdf",
                "location": "/taskUpload/" + this.taskId + "/" + this.studentId,
                "studentId": this.studentId,
                "taskId": this.taskId
            }
        },
        submitUpload() {
            this.$refs.upload.submit()
        },
        handleOnSuccess() {
            this.$notification.success("上传成功")
        },
        handleOnError() {
            this.$notification.error("上传失败")
        },
        // handleBeforeUpload() {
        //     if (this.isOverdue()) {
        //         this.$notification.error('该实验已逾期，无法提交。')
        //         return
        //     }
        // }
        handleUploadFileButton() {
            if (this.isSubmitted) {
                this.$notification.warning("该项目已提交，此次提交将会覆盖之前的内容")
            }
        }
    }
}
</script>

<style>

</style>