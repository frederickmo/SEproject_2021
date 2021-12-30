<template>
  <div>
    <div style="margin-bottom: 10px">
      <va-breadcrumbs separator=">">
        <va-breadcrumbs-item label="通知公告" disabled />
        <va-breadcrumbs-item label="公告列表" to="/home/announcement" />
      </va-breadcrumbs>
    </div>
    <!-- 以下是新增公告的Modal -->
    <a-modal
      v-model:visible="modalVisible"
      footer="false"
      hide-cancel
      ok-text="取消"
    >
      <template #title> 新增公告 </template>
      <a-form :model="form">
        <a-form-item
          field="topic"
          label="标题"
          :rules="[{ required: true, message: '请输入公告标题' }]"
          :validate-trigger="['change', 'input']"
        >
          <a-input
            v-model="form.topic"
            placeholder="输入公告标题"
            allow-clear
          />
        </a-form-item>
        <a-form-item
          field="content"
          label="详细内容"
          :rules="[{ required: true, message: '请输入公告内容' }]"
        >
          <a-textarea
            v-model="form.content"
            :auto-size="{ minRows: 3, maxRows: 8 }"
            placeholder="输入公告内容"
            allow-clear
          />
        </a-form-item>
        <a-form-item>
          <a-radio-group v-model="form.type">
            <a-radio value="0">所有人可见</a-radio>
            <a-radio value="1">仅学生可见</a-radio>
            <a-radio value="2">仅教师可见</a-radio>
            <a-radio value="3">仅管理员可见</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item>
          <a-button @click="submitForm">提交</a-button>
        </a-form-item>
      </a-form>
    </a-modal>
    <!-- 以下是查看/修改公告的Modal -->
    <a-modal
      v-model:visible="displayAnnouncementModalVisible"
      hide-cancel
      ok-text="关闭"
      :close="(this.modifyAnnouncementStatus = false)"
    >
      <template #title>
        {{ modifyAnnouncementStatus ? "修改公告" : displayAnnouncement.topic }}
      </template>
      <a-textarea
        style="margin-bottom: 10px; background: white"
        v-show="!modifyAnnouncementStatus"
        v-model="this.displayAnnouncement.content"
        auto-size
        readonly
      />
      <div v-show="modifyAnnouncementStatus">
        <div style="font-weight: bold; margin-bottom: 5px">标题</div>
        <div style="margin-bottom: 5px">
          <a-input v-model="this.displayAnnouncement.topic" />
        </div>
        <div style="font-weight: bold; margin-bottom: 5px">内容</div>
        <div style="margin-bottom: 5px">
          <a-textarea v-model="this.displayAnnouncement.content" auto-size />
        </div>
        <div style="font-weight: bold; margin-bottom: 5px">类型</div>
        <div style="margin-bottom: 10px">
          <a-radio-group v-model="this.displayAnnouncement.type">
            <a-radio value="0">所有人可见</a-radio>
            <a-radio value="1">仅学生可见</a-radio>
            <a-radio value="2">仅教师可见</a-radio>
            <a-radio value="3">仅管理员可见</a-radio>
          </a-radio-group>
        </div>
      </div>
      <a-button
        style="margin-top: 10px"
        v-show="!modifyAnnouncementStatus & (this.userIdentity == 4)"
        @click="this.modifyAnnouncementStatus = !this.modifyAnnouncementStatus"
      >
        点击修改
      </a-button>
      <a-button
        v-show="modifyAnnouncementStatus"
        @click="confirmModifyAnnouncement"
        >确认提交</a-button
      >
    </a-modal>
    <!-- 以下是确认移除公告的Modal -->
    <a-modal
      v-model:visible="removeModalVisible"
      simple
      v-on:ok="handleConfirmRemove"
    >
      <template #title> 移除公告 </template>
      <div style="text-align: center">
        <div>确认移除？</div>
        <!-- <a-button @click="handleConfirmRemove">确认</a-button> -->
      </div>
    </a-modal>
    <!-- 激活账号的modal -->
    <a-modal
      v-model:visible="activateAccountModalVisible"
      hide-cancel
      ok-text="取消"
      v-on:close="this.isSendingVerificationEmailButtonClicked = false"
    >
      <template #title> 激活账号 </template>
      <div v-show="loading" style="text-align: center">
        <a-spin tip="请稍等，邮件发送中……" />
        请稍等，邮件发送中……
      </div>
      <div v-show="!loading">
        <div v-show="!isSendingVerificationEmailButtonClicked">
          <div style="margin-bottom: 10px">
            请点击下方按钮，系统将向您的邮箱中发送验证邮件，请查看邮件后正确填写验证码。账号将被激活。
          </div>
          <a-button type="primary" @click="handleSendVerificationEmail"
            >发送验证码</a-button
          >
        </div>
        <div
          style="text-align: center"
          v-show="isSendingVerificationEmailButtonClicked"
        >
          <a-alert type="success"
            >验证码已发送至：{{ this.userEmail
            }}<br />请在30分钟内进行填写，逾期验证码将失效。</a-alert
          >
          <div style="display: flex; width: 70%; margin-top: 20px">
            <div
              style="
                width: 30%;
                margin-left: 35%;
                line-height: 200%;
                font-weight: bold;
              "
            >
              验证码
            </div>
            <a-input
              v-model="verificationCode"
              style="width: 45%"
              allow-clear
            />
            <a-button
              style="margin-left: 10px"
              @click="handleSubmitVerificationCode"
              >提交</a-button
            >
          </div>
        </div>
      </div>
    </a-modal>
    <a-alert v-show="showAlert" type="warning" show-icon="false">
      当前账号未激活。是否现在进行激活？
      <a-button type="primary" status="success" @click="handleActivateAccount"
        >激活</a-button
      >
    </a-alert>
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
      <va-card-title style="font-size: 20px">公告列表</va-card-title>
      <va-card-content>
        <div v-show="this.userIdentity == 4" style="text-align: left">
          <a-button @click="this.modalVisible = !this.modalVisible"
            >新增公告</a-button
          >
        </div>
        <el-table
          stripe
          :data="
            notifications.filter(
              (data) =>
                !search
                | data.topic.toLowerCase().includes(search.toLowerCase())
            )
          "
        >
          <el-table-column label="标题" prop="topic" />
          <!-- <el-table-column label="预览" prop="content" /> -->
          <el-table-column label="更新时间" prop="updatedTime" />
          <el-table-column align="right">
            <template #header>
              <el-input
                v-model="search"
                size="mini"
                placeholder="输入关键词查找"
              />
            </template>
            <template #default="scope">
              <a-button
                style="margin-right: 10px"
                @click="handleShowAnnouncementDetail(scope.$index)"
                >查看</a-button
              >
              <a-button
                v-show="this.userIdentity == 4"
                status="danger"
                @click="handleRemoveAnnouncement(scope.$index)"
                >移除</a-button
              >
            </template>
          </el-table-column>
        </el-table>
      </va-card-content>
    </va-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      studentNotifications: [
        {
          title: "考试通知",
          abstract: "这是一条占位置的通知",
          date: "2021-11-4",
        },
        {
          title: "实验通知",
          abstract: "这是一条假装有用的通知",
          date: "2021-10-5",
        },
        {
          title: "实验结束通知",
          abstract: "虽然现在没有任何实验可以做",
          date: "2021-12-31",
        },
        {
          title: "课程结束预告",
          abstract: "这是一条没有用的通知",
          date: "2021-11-20",
        },
        {
          title: "开课预告",
          abstract: "这是一门挂科率90%的课",
          date: "2021-12-15",
        },
      ],
      teacherNotifications: [],
      userId: "",
      userName: "",
      userIdentity: "",
      userEmail: "",
      userActivated: "",

      notifications: [],
      search: "",

      modalVisible: false,
      displayAnnouncementModalVisible: false,

      modifyAnnouncementStatus: false,

      form: {
        topic: "",
        content: "",
        postedId: "",
        type: 0,
      },

      displayAnnouncement: {
        id: "",
        topic: "",
        content: "",
        type: "",
      },

      removeIndex: 0,
      removeId: 0,
      removeModalVisible: false,

      activateAccountModalVisible: false,
      isSendingVerificationEmailButtonClicked: false,

      verificationCode: "",
      loading: false,

      showAlert: false,
    };
  },
  mounted() {
    // console.log(this.$route.params.userInfo)
    // this.userInfo = this.$route.params.userInfo

    this.userId = localStorage.getItem("userId");
    this.userIdentity = localStorage.getItem("userIdentity");
    this.userEmail = localStorage.getItem("userEmail");
    this.userActivated = localStorage.getItem("userActivated");
    if (this.userActivated == 0 || this.userActivated == "0") {
      this.showAlert = true;
    }
    // console.log("userId: ", this.userId)

    if (this.userIdentity == 1) {
      fetch(this.$URL + "/notice/get/student", {
        method: "GET",
        headers: { satoken: localStorage.getItem("token") },
      })
        .then((res) => res.json())
        .then((res) => {
          // console.log(res)
          this.notifications = res;
        });
    } else if (this.userIdentity == 2 || this.userIdentity == 3) {
      fetch(this.$URL + "/notice/get/teacher", {
        method: "GET",
        headers: { satoken: localStorage.getItem("token") },
      })
        .then((res) => res.json())
        .then((res) => {
          // console.log(res)
          this.notifications = res;
        });
    } else if (this.userIdentity == 4) {
      fetch(this.$URL + "/notice/get", {
        method: "GET",
        headers: { satoken: localStorage.getItem("token") },
      })
        .then((response) => response.json())
        .then((res) => {
          // console.log(res)
          this.notifications = res;
        });
    }
  },
  methods: {
    showStudentVersion() {
      if (
        this.userIdentity == 2 ||
        this.userIdentity == 3 ||
        this.userIdentity == 4
      ) {
        return false;
      } else {
        return true;
      }
    },
    handleShowAnnouncementDetail(index) {
      // this.$modal.info({
      //     title: this.notifications[index].topic,
      //     content: this.notifications[index].content
      // })
      this.displayAnnouncement.id = this.notifications[index].id;
      this.displayAnnouncement.topic = this.notifications[index].topic;
      this.displayAnnouncement.content = this.notifications[index].content;
      this.displayAnnouncement.type = this.notifications[index].type;
      this.displayAnnouncementModalVisible =
        !this.displayAnnouncementModalVisible;
    },
    handleRemoveAnnouncement(index) {
      this.removeIndex = index;
      this.removeId = this.notifications[index].id;
      this.removeModalVisible = !this.removeModalVisible;
    },
    handleConfirmRemove() {
      // console.log("被移除的公告：", this.notifications[this.removeIndex])

      fetch(this.$URL + "/notice/remove?id=" + this.removeId, {
        method: "DELETE",
        headers: { satoken: localStorage.getItem("token") },
      })
        .then((res) => res.json())
        .then((res) => {
          // console.log(res)
          if (res.code == 200) {
            this.$message.success("成功移除");
            this.$router.replace({ path: "/refresh" });
          } else {
            this.$message.error("移除失败");
          }
        });
    },
    submitForm() {
      this.form.postedId = this.userId;
      // console.log("打印一下form：", this.form)
      if (!this.form.topic.length) {
        this.$message.info("请输入公告标题");
        return;
      } else if (!this.form.content.length) {
        this.$message.info("请输入公告内容");
        return;
      }
      fetch(this.$URL + "/notice/add", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          satoken: localStorage.getItem("token"),
        },
        body: JSON.stringify(this.form),
      })
        .then((res) => res.json())
        .then((res) => {
          // console.log(res)
          if (res.code == 200) {
            this.$notification.success("添加成功");
            this.$router.replace({ path: "/refresh" });
          }
        });
    },
    confirmModifyAnnouncement() {
      // console.log(this.displayAnnouncement)

      fetch(this.$URL + "/notice/update", {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          satoken: localStorage.getItem("token"),
        },
        body: JSON.stringify(this.displayAnnouncement),
      })
        .then((res) => res.json())
        .then((res) => {
          // console.log(res)
          if (res.code == 200) {
            this.$message.success("修改成功");
            this.$router.replace({ path: "/refresh" });
          } else {
            this.$message.error("修改失败");
          }
        });
    },

    handleActivateAccount() {
      this.activateAccountModalVisible = !this.activateAccountModalVisible;
    },

    handleSendVerificationEmail() {
      // console.log(this.userEmail)
      this.loading = true;

      fetch(
        this.$URL +
          "/user/verify/send?email=" +
          this.userEmail +
          "&userId=" +
          this.userId,
        {
          method: "POST",
          headers: { satoken: localStorage.getItem("token") },
        }
      )
        .then((res) => res.json())
        .then((res) => {
          // console.log(res)
          if (res.code == 200) {
            this.loading = false;
            this.$message.success("验证码已发送");
            this.isSendingVerificationEmailButtonClicked =
              !this.isSendingVerificationEmailButtonClicked;
          } else {
            this.$message.error("验证码发送失败，请稍后重试");
          }
        });
    },
    handleSubmitVerificationCode() {
      // console.log("验证码：", this.verificationCode)

      fetch(
        this.$URL +
          "/user/verify/activate?code=" +
          this.verificationCode +
          "&userId=" +
          this.userId,
        {
          method: "POST",
          headers: { satoken: localStorage.getItem("token") },
        }
      )
        .then((res) => {
          // console.log(res)
          return res.json();
        })
        .then((res) => {
          // console.log(res)
          if (res.code == 200) {
            this.$message.success("激活成功");
            // this.$router.replace({path: '/refresh'})
            location.reload();
          } else {
            this.$message.error("激活失败，请稍后重试");
          }
        });

      // this.isSendingVerificationEmailButtonClicked = !this.isSendingVerificationEmailButtonClicked;
    },
  },
};
</script>

<style>
.va-card-title {
  font-size: 20px;
}
</style>
