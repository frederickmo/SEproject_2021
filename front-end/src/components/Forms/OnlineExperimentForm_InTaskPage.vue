<template>
  <div>
    <div style="margin-bottom: 10px">
      <va-breadcrumbs separator=">">
        <va-breadcrumbs-item label="实验管理" disabled />
        <va-breadcrumbs-item label="我的实验" to="/home/mytasks" />
        <va-breadcrumbs-item label="在线实验项目" disabled />
      </va-breadcrumbs>
    </div>
    <a-modal v-model:visible="showModal" @ok="handleModalOk">
      <template #title> 提示 </template>
      <div>您已经提交，此次提交将会覆盖之前的提交。是否重新提交？</div>
    </a-modal>
    <a-alert type="warning" style="margin-bottom: 10px" v-show="isOverdue()"
      >该实验报告已截止。您现在仅可查看，无法进行提交。</a-alert
    >
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
      <div style="height: 15px" />
      <div style="height: 20px" />
      <a-space style="margin-bottom: 20px">
        <div style="font-size: 30px; font-weight: bold">
          实验报告：{{ this.taskName }}
        </div>
        <div v-show="isSubmitted">
          <va-icon color="#89d7bc" name="check_circle" />已提交
        </div>
        <div v-show="!isSubmitted"><va-icon name="cancel" />未提交</div>
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
          <a-input v-model="form_studentid" allow-clear />
          <span class="label">姓名</span>
          <a-input v-model="form_name" allow-clear />
          <span class="label">合作者</span>
          <a-input v-model="form_cooperator" allow-clear />
        </a-space>
        <div style="height: 20px" />
        <a-space>
          <span class="label">实验地点</span>
          <a-input v-model="form_place" allow-clear />
          <span class="label">实验时间</span>
          <a-date-picker v-model="form_date" />
        </a-space>
        <div style="height: 20px" />
        <a-space>
          <span class="label">实验目的</span>
          <!-- <va-input class="long-text" type="textarea" v-model="form_aim" /> -->
          <a-textarea class="long-text" v-model="form_aim" auto-size />
        </a-space>
        <div style="height: 20px" />
        <a-space>
          <span class="label">实验原理</span>
          <a-textarea class="long-text" v-model="form_principle" auto-size />
        </a-space>
        <div style="height: 20px" />
        <a-space>
          <span class="label">实验设备</span>
          <a-textarea class="long-text" v-model="form_device" auto-size />
        </a-space>
        <div style="height: 20px" />
        <a-space>
          <span class="label">实验步骤</span>
          <a-textarea class="long-text" v-model="form_procedure" auto-size />
        </a-space>
        <div style="height: 20px" />
        <a-space>
          <span class="label">实验现象</span>
          <a-textarea class="long-text" v-model="form_phenomenon" auto-size />
        </a-space>
        <div style="height: 20px" />
        <a-space>
          <span class="label">分析讨论</span>
          <a-textarea class="long-text" v-model="form_conclusion" auto-size />
        </a-space>
        <div style="height: 30px" />
        <va-button color="#9fbcc2" gradient @click="handleSubmitButton"
          >提交</va-button
        >
        <div style="height: 30px" />
      </va-form>

      <!-- <div>
                <div>Values:</div>
                <div>ID: {{form_studentid}}</div>
                <div>姓名: {{form_name}}</div>
                <div>合作者: {{form_cooperator}}</div>
                <div>实验时间: {{form_date}}</div>
                <div>实验地点: {{form_place}}</div>
                <div>实验目的: {{form_aim}}</div>
                <div>实验原理: {{form_principle}}</div>
                <div>实验设备: {{form_device}}</div>
                <div>实验步骤: {{form_procedure}}</div>
                <div>实验现象: {{form_phenomenon}}</div>
                <div>分析讨论: {{form_conclusion}}</div>
            </div> -->
    </va-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      studentId: "",
      taskId: "",
      taskName: "",
      deadline: "",
      url: "",
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

      // inputColumns: [
      //     {name: '实验目的'},
      //     {name: '实验原理'},
      //     {name: '实验设备'},
      //     {name: '实验步骤'},
      //     {name: '实验现象'},
      //     {name: '分析讨论'}
      // ],

      courseId: "",

      isSubmitted: false,
      showModal: false,
    };
  },
  mounted() {
    // console.log("params:", this.$route.params)
    // console.log(this.$route.params.taskId, this.$route.params.taskName)
    this.taskId = this.$route.params.taskId
      ? this.$route.params.taskId
      : localStorage.getItem("curTaskId");
    // this.taskName = this.$route.params.taskName
    // this.deadline = this.$route.params.deadline

    /**
     * 不知道为什么跨页面传参出了问题，从上一个页面传过来的参数到了本页面少了几个属性。
     * 只好重新查询了。
     */

    /**
     * 还差日期比较的功能还没做 => 超过截止日期后无法提交
     */

    this.studentId = localStorage.getItem("userId");

    fetch(this.$URL + "/task/get?id=" + this.taskId, {
      method: "GET",
      headers: { satoken: localStorage.getItem("token") },
    }).then((response) => {
      let result = response.json();
      result.then((res) => {
        this.taskName = res.name;
        this.deadline = res.deadline;
        this.url = res.url;
        // console.log("deadline: " , this.deadline)
      });
    });

    fetch(
      this.$URL +
        "/finishes/isFinished?studentId=" +
        this.studentId +
        "&taskId=" +
        this.taskId,
      {
        method: "GET",
        headers: { satoken: localStorage.getItem("token") },
      }
    ).then((response) => {
      // console.log(response)
      let result = response.text();
      result.then((res) => {
        if (res == "true") {
          this.isSubmitted = true;
        } else {
          this.isSubmitted = false;
        }
      });
    });

    fetch(this.$URL + "/task/get?id=" + this.taskId, {
      method: "GET",
      headers: { satoken: localStorage.getItem("token") },
    }).then((response) => {
      let result = response.json();
      result.then((res) => {
        this.courseId = res.courseId;
        // console.log("courseId: ", this.courseId)
      });
    });
  },
  methods: {
    isOverdue() {
      /**
       * now的写法用当前时间转日期再转时间来进行比较的原因是
       * 如果直接用当前时间和设定的ddl(只精确到日期)来比较的话，
       * ddl转时间是当天的0点整，但是设定的ddl本身意义应该是到当天结束为止。
       * 所以把当前时间转换为当天0点整再进行比较就能等效上述效果。
       */
      let deadline_date = new Date(this.deadline);
      let now = new Date(new Date().toLocaleDateString());
      if (
        now <= deadline_date ||
        this.deadline == null ||
        this.deadline == undefined ||
        this.deadline == ""
      ) {
        return false;
      } else {
        return true;
      }
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
    handleSubmitButton() {
      if (this.isOverdue()) {
        this.$notification.error("该实验已逾期，无法提交。");
      } else if (this.isSubmitted) {
        this.showModal = !this.showModal;
      } else {
        this.submitTask();
      }
      // this.submitTask()
    },
    handleModalOk() {
      this.submitTask();
    },
    submitTask() {
      let submitForm = {
        courseId: this.courseId,
        taskId: this.taskId,

        // 按理来说下面的studentId应该填入实验报告里写的id,
        // 但是这个ID如果乱写（和学生本身的ID/数据库中的ID）不匹配的话会报错，
        // 所以最好还是直接给他填了

        studentId: this.studentId,
        studentName: this.form_name,
        cooperator: this.form_cooperator,
        date: this.form_date,
        place: this.form_place,
        content: {
          aim: this.form_aim,
          principle: this.form_principle,
          device: this.form_device,
          procedure: this.form_procedure,
          phenomenon: this.form_phenomenon,
          conclusion: this.form_conclusion,
        },
      };
      fetch(this.$URL + "/finishes/upload/online", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          satoken: localStorage.getItem("token"),
        },
        body: JSON.stringify(submitForm),
      }).then((response) => {
        // console.log(response)
        let result = response.json();
        result.then((res) => {
          // console.log(res)
          if (res.status == 200) {
            this.$notification.success("提交成功！");
            this.$router.go(-1);
          }
        });
      });
    },
  },
};
</script>

<style>
.label {
  font-size: 16px;
  font-weight: bold;
}

.long-text {
  width: 800px;
}
</style>
