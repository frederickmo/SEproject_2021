<template>
  <div>
    <div style="margin-bottom: 10px">
      <va-breadcrumbs separator=">">
        <va-breadcrumbs-item label="基本信息" disabled />
        <va-breadcrumbs-item label="修改基本信息" to="/home/editbasicinfo" />
      </va-breadcrumbs>
    </div>

    <a-modal v-model:visible="showModal" hide-cancel="true" @ok="handleModalOk">
      <template #title> 上传头像 </template>
      <el-upload
        ref="upload"
        :action="getUploadAvatarUrl()"
        :auto-upload="false"
        :data="additionalData()"
        :headers="headers()"
        style="text-align: center"
        :before-upload="beforeAvatarUpload"
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
      </el-upload>
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
            >验证码已发送至：{{ this.email
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
      <va-card-title style="font-size: 20px">修改基本信息</va-card-title>
      <va-card-content>
        <table class="va-table">
          <tbody style="font-size: 16px">
            <tr style="width: 220px">
              <td class="head">头像</td>
              <td style="text-align: center; width: 170px">
                <va-button
                  @click="showModal = !showModal"
                  flat
                  style="color: #555"
                  >修改头像</va-button
                >
              </td>
            </tr>
          </tbody>
        </table>
        <table class="va-table">
          <tbody style="font-size: 16px">
            <tr>
              <td style="width: 250px">
                <va-form @submit.prevent="onSubmit">
                  <div style="display: flex; height: 36px; margin-bottom: 20px">
                    <div class="head" style="margin-right: 30px">姓名</div>
                    <va-input v-model="nameInput" />
                  </div>
                  <div style="display: flex; height: 36px; margin-bottom: 20px">
                    <div class="head" style="margin-right: 30px">性别</div>
                    <va-select v-model="genderInput" :options="['男', '女']" />
                  </div>
                  <div style="display: flex; height: 36px">
                    <div class="head" style="margin-right: 30px">email</div>
                    <va-input v-model="emailInput" />
                  </div>
                  <va-button
                    @click="onSubmit"
                    style="margin-left: 60px; margin-top: 20px"
                    >提交</va-button
                  >
                </va-form>
              </td>
            </tr>
          </tbody>
        </table>
      </va-card-content>
    </va-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      id: "",
      name: "",
      gender: "",

      email: "",
      activated: "",

      genderInput: "",
      nameInput: "",
      emailInput: "",

      genderSubmit: "",
      nameSubmit: "",

      avatarUrl: "",

      showModal: false,

      activateAccountModalVisible: false,
      isSendingVerificationEmailButtonClicked: false,

      verificationCode: "",
      loading: false,

      showAlert: false,
    };
  },
  mounted() {
    this.id = localStorage.getItem("userId");

    this.email = localStorage.getItem("userEmail");
    this.activated = localStorage.getItem("userActivated");
    if (this.activated == 0 || this.activated == "0") {
      this.showAlert = true;
    }

    fetch(this.$URL + "/user/get?id=" + this.id, {
      method: "GET",
      headers: { satoken: localStorage.getItem("token") },
    })
      .then((res) => res.json())
      .then((res) => {
        // console.log(res)
        this.name = res.name;
        this.gender = res.gender;
      });
  },
  methods: {
    onSubmit() {
      /**
       * 修改了当提交信息为空时候的逻辑，现在在后端进行检查了，若提交空信息给后端则不修改。
       */
    //   console.log(this.name, this.gender);
    //   console.log(this.genderInput);
      if (this.genderInput == "男") {
        this.genderSubmit = 1;
        // console.log("?")
      } else if (this.genderInput == "女") {
        this.genderSubmit = 2;
        // console.log("??")
      } else {
        this.genderSubmit = 0;
        // console.log("???")
      }

      let req = {
        id: this.id,
        name: this.nameInput,
        gender: this.genderSubmit,
        email: this.emailInput,
      };

      // console.log(req)

      fetch(this.$URL + "/user/update", {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          satoken: localStorage.getItem("token"),
        },
        body: JSON.stringify(req),
      })
        .then((response) => response.json())
        .then((res) => {
          // console.log(res)
          if (res.code == 200) {
            this.$message.success("修改成功");
            localStorage.setItem("gender", this.genderSubmit);
            localStorage.setItem("username", this.nameInput);
            localStorage.setItem("userEmail", this.emailInput);
            /**
             * 以下replace语句是用来跳转到一个空白页面{name: 'Refresh'}实现当前界面自动重新加载的效果
             *
             * 经实践，填path比填name更快。
             */
            // this.$router.replace({name: 'Refresh'})
            // this.$router.replace({path: '/refresh'})
            this.$router.push({ name: "BasicInfo" });
          } else if (res.code == 409) {
            this.$message.error("该邮箱已被注册");
          } else {
            this.$message.error("修改失败");
          }
        });
    },
    onPreview(file) {
      this.$modal.info({
        title: "Preview",
        content: (
          <div style="text-align: center">
            <img
              style="max-width: 100%"
              src={file.url || URL.createObjectURL(file.originFile)}
            />
          </div>
        ),
      });
    },
    getUploadAvatarUrl() {
      return this.$URL + "/file/upload/avatar";
    },
    beforeAvatarUpload(file) {
      // console.log("file.type: " + file.type)
      const isIMAGE = file.type === "image/jpeg" || file.type === "image/png";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isIMAGE) {
        this.$notification.error("请上传jpg或png格式的图片");
      }
      if (!isLt2M) {
        this.$$notification.error("图片大小请小于2M");
      }
      return isIMAGE && isLt2M;
    },
    renameAvatar() {
      return "avatar_" + this.id;
    },
    additionalData() {
      return {
        id: this.id,
      };
    },
    headers() {
      return {
        satoken: localStorage.getItem("token"),
      };
    },
    submitUpload() {
      this.$refs.upload.submit();
    },
    handleModalOk() {
      /**
       * TODO: 这里会直接跳转到登录前的界面，需要后续借助SA-TOKEN实现已登录自动跳转
       */
      location.reload();
    },

    handleActivateAccount() {
      this.activateAccountModalVisible = !this.activateAccountModalVisible;
    },

    handleSendVerificationEmail() {
      this.loading = true;
    //   console.log(this.userEmail);

      fetch(
        this.$URL +
          "/user/verify/send?email=" +
          this.email +
          "&userId=" +
          this.id,
        {
          method: "POST",
          headers: { satoken: localStorage.getItem("token") },
        }
      )
        .then((res) => res.json())
        .then((res) => {
        //   console.log(res);
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
    //   console.log("验证码：", this.verificationCode);

      fetch(
        this.$URL +
          "/user/verify/activate?code=" +
          this.verificationCode +
          "&userId=" +
          this.id,
        {
          method: "POST",
          headers: { satoken: localStorage.getItem("token") },
        }
      )
        .then((res) => {
        //   console.log(res);
          return res.json();
        })
        .then((res) => {
        //   console.log(res);
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

<style scoped>
.head {
  font-weight: bold;
  width: 80px;
  min-width: 40px;
  line-height: 36px;
}
</style>
