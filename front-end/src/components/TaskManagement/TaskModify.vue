<template>
  <va-card gradient color="#e0e5df">
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
    <va-card-content style="text-align: left">
      <h1>修改项目信息</h1>
      <div class="course-title">
        项目名字：{{ this.taskName
        }}<va-input
          class="mb-4"
          v-model="this.taskName"
          placeholder="请输入修改后的内容"
          outline
        />
      </div>
      <div class="course-description">
        项目描述：{{ this.taskDescription
        }}<va-input
          class="mb-4"
          v-model="this.taskDescription"
          placeholder="请输入修改后的内容"
          outline
        />
      </div>
      <div class="course-year">
        项目截止日期：{{ this.deadline }}
        <a-date-picker v-model="this.deadline" style="width: 200px" />
        <!-- <va-input
      class="mb-4"
      v-model="this.deadline"
      placeholder="请输入修改后的内容"
      outline
    /> -->
      </div>
      <div class="course-semester">
        项目类型：{{ this.type
        }}<va-input
          class="mb-4"
          v-model="this.type"
          placeholder="请输入修改后的内容"
          outline
        />
      </div>
      <div class="course-semester">项目文档：</div>
      <div>
        <va-button
          color="#aaa"
          style="color: #777"
          flat
          @click="uploadModalVisible = !uploadModalVisible"
          >上传</va-button
        >
      </div>
      <a-modal v-model:visible="uploadModalVisible">
        <template #title> 上传文件 </template>
        <el-upload
          ref="upload"
          :action="getUploadUrl()"
          :headers="headers()"
          :auto-upload="false"
          style="text-align: center"
        >
          <template #trigger>
            <va-button gradient :rounded="false">选择文件</va-button>
          </template>
          <va-button
            style="margin-left: 20px"
            flat
            :rounded="false"
            @click="submitUpload"
            >上传</va-button
          >
          <!-- <template #tip>
      <div class="el-upload__tip">
        jpg/png files with a size less than 500kb
      </div>
    </template> -->
        </el-upload>
      </a-modal>
      <div style="height: 20px" />

      <va-button
        @click="handleClick3"
        color="#e0e5df"
        style="color: rgb(40, 40, 40)"
        >确认修改</va-button
      >

      <a-modal
        v-model:visible="visible3"
        @ok="handleOk3"
        @cancel="handleCancel3"
        unmountOnClose
        @before-ok="handleBeforeOk3"
      >
        <template #title> 修改课程 </template>
        <div>您确定要修改吗</div>
      </a-modal>
    </va-card-content>
  </va-card>

  <div style="height: 12px" />
  <va-button
    @click="handleClick4"
    color="#FF0000"
    style="background-color: rgb(0,0,0) text-align:center"
    >点击删除项目</va-button
  >
  <a-modal
    v-model:visible="visible4"
    @ok="handleOk4"
    @cancel="handleCancel4"
    unmountOnClose
    @before-ok="handleBeforeOk4"
  >
    <template #title> 删除项目 </template>
    <div>您确定要删除吗</div>
  </a-modal>
</template>

<script>
export default {
  data() {
    return {
      visible3: false,
      visible4: false,
      uploadModalVisible: false,
      id: "",
      taskId: "",
      type: "",
      taskName: "",
      taskDescription: "",
      url: "",
      deadline: "",
      manage: "",
      task: [],
      student: [],
      teacher: [],
    };
  },
  mounted() {
    this.id = localStorage.getItem("userId");
    this.courseId = localStorage.getItem("courseId");
    this.taskId = localStorage.getItem("taskId");
    // if (this.$route.params.courseId == undefined) {
    // console.log(this.$route.params.taskId);
    fetch(this.$URL + "/task/get?id=" + this.$route.params.taskId, {
      method: "GET",
      headers: { satoken: localStorage.getItem("token") },
    }).then((response) => {
      // console.log(response);
      let result = response.json();
      result.then((res) => {
        // console.log(res);
        this.taskName = res.name;
        this.taskDescription = res.description;
        //if(res.year!=null)
        this.url = res.url;
        //else
        this.type = res.type;
        //if(res.semester!=null)
        this.deadline = res.deadline;
        // else

        //console.log(this.courseName+" "+this.courseDescription)
      });
    });
  },
  methods: {
    headers() {
      return {
        satoken: localStorage.getItem("token"),
      };
    },
    getUploadUrl() {
      return this.$URL + "/file/upload/redirect";
    },
    submitUpload() {
      this.$refs.upload.submit();
    },
    handleClick3() {
      this.visible3 = true;
    },

    handleOk3() {
      let submitForm = {
        courseId: this.$route.params.courseId,
        id: this.$route.params.taskId,
        name: this.taskName,
        type: this.type,
        url: this.url,
        description: this.taskDescription,
        deadline: this.deadline,
      };
      if (submitForm.url == null) submitForm.url = "";
      if (submitForm.taskDescription == null) submitForm.taskDescription = "";
      // console.log(submitForm);
      fetch(this.$URL + "/task/update", {
        method: "PUT",
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
          if (res.code == 200) {
            this.$notification.success("修改成功");
            this.$router.go(-1);
          }
        });
      });
    },
    handleCancel3() {
      this.visible3 = false;
    },
    handleBeforeOk3(done) {
      window.setTimeout(() => {
        done();
      }, 3000);
    },
    handleClick4() {
      this.visible4 = true;
    },

    handleOk4() {
      // var courseId = this.courseId;
      // console.log(courseId);
      fetch(this.$URL + "/task/remove?taskId=" + this.$route.params.taskId, {
        method: "DELETE",
        headers: { satoken: localStorage.getItem("token") },
      }).then((response) => {
        // console.log(response);
        let result = response.json();
        result.then((res) => {
          if (res.code == 200) {
            this.$notification.success("删除成功");
            this.$router.go(-1);
          }
        });
      });
    },
    handleCancel4() {
      this.visible4 = false;
    },
    handleBeforeOk4(done) {
      window.setTimeout(() => {
        done();
      }, 3000);
    },
    modifytask() {
      let submitForm = {
        courseId: this.$route.params.courseId,
        id: this.$route.params.taskId,
        name: this.taskName,
        type: this.type,
        url: this.url,
        description: this.taskDescription,
        deadline: this.deadline,
      };
      if (submitForm.url == null) submitForm.url = "";
      if (submitForm.taskDescription == null) submitForm.taskDescription = "";
      // console.log(submitForm);
      fetch(this.$URL + "/task/update", {
        method: "PUT",
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
          if (res.code == 200) {
            this.$notification.success("修改成功");
            this.$router.go(-1);
          }
        });
      });
    },
  },
};
</script>

<style>
.course-title {
  /* text-align: left; */
  font-size: 25px;
  font-weight: bold;
  color: #656565;
  margin-bottom: 15px;
}

.course-description {
  font-size: 25px;
  font-weight: bold;
  color: #656565;
  margin-bottom: 15px;
}

.course-year {
  font-size: 25px;
  font-weight: bold;
  color: #656565;
  margin-bottom: 15px;
}
.course-semester {
  font-size: 25px;
  font-weight: bold;
  color: #656565;
  margin-bottom: 15px;
}
</style>
