<template>
  <div>
    <va-card>
      <a-button
        style="position: absolute; right: 30px; top: 15px"
        shape="round"
        @click="this.$router.replace({ path: '/refresh' })"
      >
        <template #icon>
          <icon-refresh />
        </template>
        刷新
      </a-button>
      <va-card-content>
        <div style="height: 10px" />
        <a-space style="margin-bottom: 20px">
          <div style="font-size: 30px; font-weight: bold">
            实验报告：{{ this.taskName }}
          </div>
          <!-- <div v-show="isSubmitted"><va-icon color="#89d7bc" name="check_circle" />已提交</div>
                    <div v-show="!isSubmitted"><va-icon name="cancel" />未提交</div> -->
        </a-space>
        <div />
        <a-space style="margin-bottom: 20px">
          <span class="label">获取实验说明文档：</span>
          <va-button color="#e2e0df" style="color: black" @click="getTaskGuide"
            >点击获取</va-button
          >
        </a-space>

        <va-form>
          <a-space>
            <span class="label">学号</span>
            <a-input v-model="form_studentid" readonly />
            <span class="label">姓名</span>
            <a-input v-model="form_name" readonly />
            <span class="label">合作者</span>
            <a-input v-model="form_cooperator" readonly />
          </a-space>
          <div style="height: 20px" />
          <a-space>
            <span class="label">实验地点</span>
            <a-input v-model="form_place" readonly />
            <span class="label">实验时间</span>
            <!-- <a-date-picker v-model="form_date" allow-clear="false" readonly /> -->
            <a-input v-model="form_date" readonly />
          </a-space>
          <div style="height: 20px" />
          <a-space>
            <span class="label">实验目的</span>
            <!-- <va-input class="long-text" type="textarea" v-model="form_aim" /> -->
            <a-textarea class="long-text" v-model="form_aim" readonly />
          </a-space>
          <div style="height: 20px" />
          <a-space>
            <span class="label">实验原理</span>
            <a-textarea class="long-text" v-model="form_principle" readonly />
          </a-space>
          <div style="height: 20px" />
          <a-space>
            <span class="label">实验设备</span>
            <a-textarea class="long-text" v-model="form_device" readonly />
          </a-space>
          <div style="height: 20px" />
          <a-space>
            <span class="label">实验步骤</span>
            <a-textarea class="long-text" v-model="form_procedure" readonly />
          </a-space>
          <div style="height: 20px" />
          <a-space>
            <span class="label">实验现象</span>
            <a-textarea class="long-text" v-model="form_phenomenon" readonly />
          </a-space>
          <div style="height: 20px" />
          <a-space>
            <span class="label">分析讨论</span>
            <a-textarea class="long-text" v-model="form_conclusion" readonly />
          </a-space>
          <div style="height: 30px" />
          <!-- <va-button color="#9fbcc2" gradient @click="submitTask">提交</va-button>
                    <div style="height: 30px" /> -->
          <a-space>
            <div>分数</div>
            <a-input-number
              style="width: 80%"
              v-model="markedStudentScore"
              :min="0"
              :max="100"
            />
            <va-button color="#9fbcc2" gradient @click="updateScore"
              >提交</va-button
            >
          </a-space>
          <div style="height: 30px" />
        </va-form>
      </va-card-content>
    </va-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      teacherId: "",

      studentId: "",
      courseId: "",
      taskId: "",
      taskName: "",
      deadline: "",
      url: "",
      reportName: "",

      form_studentid: "",
      form_name: "",
      form_cooperator: "",
      form_date: "",
      form_place: "",
      form_aim: "",
      form_principle: "",
      form_device: "",
      form_procedure: "",
      form_phenomenon: "",
      form_conclusion: "",

      // reportFile: {}
      markedStudentScore: "",
    };
  },
  mounted() {
    // console.log(this.$route.params);
    this.teacherId = this.$route.params.teacherId;
    this.studentId = this.$route.params.studentId;
    this.taskId = this.$route.params.taskId;
    this.markedStudentScore = this.$route.params.score;

    fetch(this.$URL + "/task/get?id=" + this.taskId, {
      method: "GET",
      headers: { satoken: localStorage.getItem("token") },
    })
      .then((response) => response.json())
      .then((res) => {
        this.taskName = res.name;
        this.courseId = res.courseId;
        this.url = res.url;
      });

    fetch(
      this.$URL +
        "/finishes/get?studentId=" +
        this.studentId +
        "&taskId=" +
        this.taskId,
      {
        method: "GET",
        headers: { satoken: localStorage.getItem("token") },
      }
    )
      .then((response) => response.json())
      .then((res) => {
        // console.log(res);
        this.reportName = res.answer;
        // console.log("reportName: ", this.reportName);
        // console.log("先打印哪个？1");
      })
      .then(async () => {
        // console.log("先打印那个？2");
        fetch(
          this.$URL +
            "/finishes/get/onlineReport?studentId=" +
            this.studentId +
            "&taskId=" +
            this.taskId,
          {
            method: "GET",
            headers: { satoken: localStorage.getItem("token") },
          }
        )
          .then((response) => response.json())
          .then((res) => {
            // console.log(res);
            this.form_studentid = res.studentId;
            this.form_date = res.date;
            this.form_name = res.studentName;
            this.form_place = res.place;
            this.form_aim = res.content.aim;
            this.form_principle = res.content.principle;
            this.form_device = res.content.device;
            this.form_procedure = res.content.procedure;
            this.form_phenomenon = res.content.phenomenon;
            this.form_conclusion = res.content.conclusion;
          });
      });

    // var file = require()

    // let reportFile = require(this.$URL + "/file/download/taskUpload/" + this.taskId + "/" + this.studentId + "/" + this.studentId + "/" + this.reportName)
    // console.log(reportFile)
  },
  methods: {
    updateScore() {
    //   console.log(this.markedStudentId, this.markedStudentScore);

      let req = {
        teacherId: this.teacherId,
        studentId: this.studentId,
        taskId: this.taskId,
        score: this.markedStudentScore,
      };

      fetch(this.$URL + "/checks/add", {
        method: "POST",
        body: JSON.stringify(req),
        headers: {
          "Content-Type": "application/json",
          satoken: localStorage.getItem("token"),
        },
      }).then((response) => {
        // console.log(response);
        let result = response.json();
        result.then((res) => {
        //   console.log(res);
          if (res.status == 200) {
            this.$notification.success("成绩上传/更新成功");
            /**
             * 以下replace语句是用来跳转到一个空白页面{name: 'Refresh'}实现当前界面自动重新加载的效果
             *
             * 经实践，填path比填name更快。
             */
            // this.$router.replace({name: 'Refresh'})
            // this.$router.replace({path: '/refresh'})
            this.$router.go(-1);
          } else {
            this.$notification.error("成绩上传失败");
          }
        });
      });
    },
    getTaskGuide() {
      window.open(
        this.$URL +
          "/file/download/taskGuide/" +
          this.courseId +
          "/" +
          this.taskId +
          "/" +
          this.url
      );
    },
  },
};
</script>

<style>
.long-text {
  width: 800px;
}
</style>
