<template>
  <div style="margin-bottom: 10px">
    <va-breadcrumbs separator=">">
      <va-breadcrumbs-item label="实验项目管理" disabled />
      <va-breadcrumbs-item label="实验项目管理" to="/home/taskmanagement" />
      <va-breadcrumbs-item label="实验项目信息" disabled />
    </va-breadcrumbs>
  </div>
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
    <va-card-content style="text-align: left">
      <a-modal
        v-model:visible="displayAnnouncementModalVisible"
        hide-cancel
        ok-text="关闭"
        :close="(this.modifyAnnouncementStatus = false)"
      >
        <template #title>
          {{ modifyAnnouncementStatus ? "修改信息" : displayAnnouncement.name }}
        </template>
        <a-textarea
          style="margin-bottom: 10px"
          v-show="!modifyAnnouncementStatus"
          v-model="this.displayAnnouncement.Description"
          auto-size
          readonly
        />
        <a-textarea
          style="margin-bottom: 10px"
          v-show="!modifyAnnouncementStatus"
          v-model="this.displayAnnouncement.deadline"
          auto-size
          readonly
        />

        <div v-show="modifyAnnouncementStatus">
          <div style="font-weight: bold; margin-bottom: 5px">名称</div>
          <div style="margin-bottom: 5px">
            <a-input v-model="this.displayAnnouncement.name" />
          </div>
          <div style="font-weight: bold; margin-bottom: 5px">项目说明</div>
          <div style="margin-bottom: 5px">
            <a-input v-model="this.displayAnnouncement.Description" />
          </div>
          <div style="font-weight: bold; margin-bottom: 5px">截止日期</div>
          <div style="margin-bottom: 5px">
            <a-textarea v-model="this.displayAnnouncement.deadline" auto-size />
          </div>
          <div style="font-weight: bold; margin-bottom: 5px">类型</div>
          <a-radio-group v-model="this.displayAnnouncement.url" type="button">
            <a-radio value="0">小型项目</a-radio>
            <a-radio value="1">大型项目</a-radio>
          </a-radio-group>
        </div>
        <div style="height: 20px" />
        <a-button
          style="margin-top: 10px"
          v-show="!modifyAnnouncementStatus"
          @click="
            this.modifyAnnouncementStatus = !this.modifyAnnouncementStatus
          "
        >
          点击修改
        </a-button>
        <a-button v-show="modifyAnnouncementStatus" @click="handleOk3"
          >确认提交</a-button
        >
      </a-modal>
      <a-modal
        v-model:visible="displayAnnouncementModalVisible1"
        hide-cancel
        ok-text="关闭"
        :close="(this.modifyAnnouncementStatus1 = false)"
      >
        <template #title> 上传实验说明文档 </template>
        <div>
          <div style="font-weight: bold; margin-bottom: 5px">项目文档</div>
          <div style="margin-bottom: 5px">
            <el-upload
              ref="upload"
              :action="getUploadUrl()"
              :headers="headers()"
              :auto-upload="false"
              :data="additionalData()"
              style="text-align: center"
              :on-success="handleOnSuccess"
              :on-error="handleOnError"
            >
              <template #trigger>
                <va-button
                  @click="handleUploadFileButton"
                  gradient
                  :rounded="false"
                  >选择文件</va-button
                >
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
          </div>
        </div>
      </a-modal>
      <h1>{{ this.courseName }}</h1>
      <div class="course-description">{{ this.courseDescription }}</div>
      <!-- <va-card 
            v-for="(task, index) in simpleTasks"
            :key="index"
            color="#b5c4b1" 
            gradient
            style="margin-bottom: 10px"
            > -->
      <va-card>
        <va-card-content style="rgb(60, 60, 60); font-weight: bold">
          <el-table
            v-if="this.status == 1"
            :data="
              simpleTasks.filter(
                (data) =>
                  !search ||
                  data.name.toLowerCase().includes(search.toLowerCase())
              )
            "
            style="width: 100%"
          >
            <el-table-column label="名称" prop="name" />
            <el-table-column label="截止日期" prop="deadline" />
            <el-table-column label="是否逾期" prop="over" />
            <el-table-column align="right">
              <template #header>
                <el-input
                  v-model="search"
                  size="mini"
                  placeholder="Type to search"
                />
              </template>
              <template #default="scope">
                <el-button
                  size="mini"
                  type="danger"
                  @click="handleShowtaskDetail(scope.row)"
                  >查看</el-button
                >
                <el-button
                  size="mini"
                  type="danger"
                  @click="handleShowtaskDetail1(scope.row)"
                  >上传实验说明文档</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </va-card-content>
      </va-card>
      <va-card>
        <va-card-content style="rgb(60, 60, 60); font-weight: bold">
          <el-table
            v-if="this.status == 1"
            :data="
              complexTasks.filter(
                (data) =>
                  !search1 ||
                  data.name.toLowerCase().includes(search1.toLowerCase())
              )
            "
            style="width: 100%"
          >
            <el-table-column label="名称" prop="name" />
            <el-table-column label="截止日期" prop="deadline" />
            <el-table-column label="是否逾期" prop="over" />
            <el-table-column align="right">
              <template #header>
                <el-input
                  v-model="search1"
                  size="mini"
                  placeholder="Type to search"
                />
              </template>
              <template #default="scope">
                <el-button
                  size="mini"
                  type="danger"
                  @click="handleShowtaskDetail(scope.row)"
                  >查看</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </va-card-content>
      </va-card>

      <div style="height: 20px" />
    </va-card-content>
  </va-card>
</template>

<script>
export default {
  data() {
    return {
      isSubmitted: false,
      search: "",
      search1: "",
      status: 1,
      id: "",
      courseId: "",
      courseName: "",
      courseDescription: "",
      year: "",
      semester: "",
      taskId: "",
      courseInfo: [],

      courseDeadlines: [],

      tasks: [],

      simpleTasks: [],
      complexTasks: [],
      modalVisible: false,
      displayAnnouncementModalVisible: false,
      modifyAnnouncementStatus: false,
      displayAnnouncementModalVisible1: false,
      modifyAnnouncementStatus1: false,
      displayAnnouncement: {
        id: "",
        courseId: "",
        name: "",
        Description: "",
        url: "",
        deadline: "",
        type: "",
      },
    };
  },
  mounted() {
    this.id = localStorage.getItem("userId");
    this.courseId = localStorage.getItem("courseId");
    console.log(
      "从上个页面传来的params.courseId: " + this.$route.params.courseId
    );
    if (this.$route.params.courseId == undefined) {
      console.log("从子路由跳到父路由，需要重新查询课程数据");
      fetch(this.$URL + "/course/get?id=" + this.courseId, {
        method: "GET",
        headers: { satoken: localStorage.getItem("token") },
      }).then((response) => {
        // console.log(response)
        let result = response.json();
        result.then((res) => {
          // console.log(res)
          this.courseName = res.name;
          this.courseDescription = res.description;
          this.year = res.year;
          this.semester = res.semester;
        });
      });
    } else {
      console.log("从父路由跳到子路由，直接把查询结果传参即可，无需重新查询");
      this.courseName = this.$route.params.name;
      this.courseDescription = this.$route.params.description;
      this.year = this.$route.params.year;
      this.semester = this.$route.params.semester;
      console.log("从上个页面传来的courseName: ", this.courseName);
    }

    fetch(this.$URL + "/task/get/simple?courseId=" + this.courseId, {
      method: "GET",
      headers: { satoken: localStorage.getItem("token") },
    }).then((response) => {
      // console.log(response)
      let result = response.json();
      result.then((res) => {
        // console.log(res)
        this.simpleTasks = res;
        for (var i in this.simpleTasks) {
          let now = new Date(new Date().toLocaleDateString());
          let deadline = new Date(this.simpleTasks[i].deadline);
          if (
            this.simpleTasks[i].deadline == null ||
            this.simpleTasks[i].deadline == "" ||
            this.simpleTasks[i].deadline == undefined
          ) {
            this.simpleTasks[i].over = "";
          } else if (now <= deadline) {
            this.simpleTasks[i].over = "";
          } else {
            this.simpleTasks[i].over = "已逾期";
          }
          if (!this.simpleTasks[i].deadline) {
            this.simpleTasks[i].deadline = "暂无";
          }
        }
      });
    });
    fetch(this.$URL + "/task/get/complex?courseId=" + this.courseId, {
      method: "GET",
      headers: { satoken: localStorage.getItem("token") },
    }).then((response) => {
      let result = response.json();
      result.then((res) => {
        this.complexTasks = res;
        for (var i in this.complexTasks) {
          let now = new Date(new Date().toLocaleDateString());
          let deadline = new Date(this.complexTasks[i].deadline);
          if (
            this.complexTasks[i].deadline == null ||
            this.complexTasks[i].deadline == "" ||
            this.complexTasks[i].deadline == undefined
          ) {
            this.complexTasks[i].over = "";
          } else if (now <= deadline) {
            this.complexTasks[i].over = "";
          } else {
            this.complexTasks[i].over = "已逾期";
          }
          if (!this.complexTasks[i].deadline) {
            this.complexTasks[i].deadline = "暂无";
          }
        }
      });
    });
  },
  methods: {
    headers() {
      return {
        satoken: localStorage.getItem("token"),
      };
    },
    // getFileUploadUrl() {
    //     return this.$URL + "/finishes/upload/complex"
    // },
    handleOnSuccess() {
      this.$notification.success("上传成功");
    },
    handleOnError() {
      this.$notification.error("上传失败");
    },

    getUploadUrl() {
      return this.$URL + "/file/upload/taskGuide";
    },
    additionalData() {
      console.log(this.courseId + "/" + this.taskId + ".pdf");
      return {
        // "newFileName": this.courseId + "/" + this.taskId + ".pdf",
        // "location": "/taskGuide/" + this.courseId + "/" + this.taskId,
        courseId: this.courseId,
        taskId: this.taskId,
      };
    },
    submitUpload() {
      this.$refs.upload.submit();
    },
    handleOk3() {
      let submitForm = {
        courseId: this.displayAnnouncement.courseId,
        id: this.displayAnnouncement.id,
        name: this.displayAnnouncement.name,
        type: this.displayAnnouncement.type,
        url: this.displayAnnouncement.url,
        description: this.displayAnnouncement.Description,
        deadline: this.displayAnnouncement.deadline,
      };
      if (submitForm.url == null) submitForm.url = "";
      if (submitForm.taskDescription == null) submitForm.taskDescription = "";
      console.log(submitForm);
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
    handleShowtaskDetail(index) {
      // this.$modal.info({
      //     title: this.notifications[index].topic,
      //     content: this.notifications[index].content
      // })
      console.log(index);
      this.displayAnnouncement.id = index.id;
      console.log("66");
      this.displayAnnouncement.courseId = index.courseId;
      this.displayAnnouncement.name = index.name;
      this.displayAnnouncement.Description = index.description;
      this.displayAnnouncement.url = index.url;
      this.displayAnnouncement.type = index.type;
      this.displayAnnouncement.deadline = index.deadline;
      this.displayAnnouncementModalVisible =
        !this.displayAnnouncementModalVisible;
      console.log("66");
    },
    handleShowtaskDetail1(index) {
      // this.$modal.info({
      //     title: this.notifications[index].topic,
      //     content: this.notifications[index].content
      // })
      console.log(index);
      this.taskId = index.id;
      this.displayAnnouncementModalVisible1 =
        !this.displayAnnouncementModalVisible1;
      console.log("66");
    },
    handleRemoveAnnouncement(index) {
      this.removeIndex = index;
      this.removeId = this.notifications[index].id;
      this.removeModalVisible = !this.removeModalVisible;
    },
    switchToTaskSimple(simpleTasks) {
      // console.log("调用switchToCourse时，courseId传过去了吗？ courseId的值是：" + id)
      // console.log("调用switchToCourse时，index的值是：" + index)
      // console.log(this.tasks[index])
      console.log(simpleTasks);
      //console.log("taskName：" + this.simpleTasks[index].name)
      this.$router.push({
        name: "TaskModify",
        params: {
          taskId: simpleTasks.id,
          taskName: simpleTasks.name,
          courseId: simpleTasks.courseId,
          description: simpleTasks.description,
          type: simpleTasks.type,
          url: simpleTasks.url,
          deadline: simpleTasks.deadline,
        },
      });
    },
    switchToTaskComplex(index) {
      // console.log("调用switchToCourse时，courseId传过去了吗？ courseId的值是：" + id)
      // console.log("调用switchToCourse时，index的值是：" + index)
      // console.log(this.tasks[index])
      this.$router.push({
        name: "TaskModify",
        params: {
          taskId: this.complexTasks[index].id,
          taskName: this.complexTasks[index].name,
          courseId: this.complexTasks[index].courseId,
          description: this.complexTasks[index].description,
          type: this.complexTasks[index].type,
          url: this.complexTasks[index].url,
          deadline: this.complexTasks[index].deadline,
        },
      });
    },

    isOverdue(date) {
      /**
       * now的写法用当前时间转日期再转时间来进行比较的原因是
       * 如果直接用当前时间和设定的ddl(只精确到日期)来比较的话，
       * ddl转时间是当天的0点整，但是设定的ddl本身意义应该是到当天结束为止。
       * 所以把当前时间转换为当天0点整再进行比较就能等效上述效果。
       */
      let now = new Date(new Date().toLocaleDateString());
      let deadline = new Date(date);
      if (date == null || date == "" || date == undefined) {
        return false;
      } else if (now <= deadline) {
        return false;
      } else {
        return true;
      }
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
  font-size: 15px;
  margin-bottom: 20px;
}
</style>
