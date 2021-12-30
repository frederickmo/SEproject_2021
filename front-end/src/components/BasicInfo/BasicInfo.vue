<template>
  <div>
    <div style="margin-bottom: 10px">
      <va-breadcrumbs separator=">">
        <va-breadcrumbs-item label="基本信息" disabled />
        <va-breadcrumbs-item label="查看基本信息" to="/home/basicinfo" />
      </va-breadcrumbs>
    </div>
    <va-modal
      ref="modal"
      v-model="showAvatar"
      hide-default-actions
      title="查看大图"
    >
      <div><img style="max-height: 450px" :src="myAvatarUrl()" /></div>
      <div style="text-align: center">
        <va-button
          flat
          style="margin-top: 15px; color: gray"
          @click="this.showAvatar = !this.showAvatar"
          >关闭</va-button
        >
      </div>
    </va-modal>

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
        style="position: absolute; right: 30px; top: 20px"
        shape="round"
        @click="this.$router.replace({ path: '/refresh' })"
      >
        <template #icon>
          <icon-refresh />
        </template>
        刷新
      </a-button>
      <va-card-title style="font-size: 20px">
        基本信息
        <va-button
          flat
          @click="this.$router.push({ name: 'EditBasicInfo' })"
          style="font-size: 10px; color: gray; margin-left: 10px"
        >
          修改信息
        </va-button>
      </va-card-title>
      <va-card-content>
        <table class="va-table">
          <tbody style="font-size: 16px">
            <tr>
              <td class="head">
                <div style="height: 62px"></div>
                <div style="line-height: 18px">头像</div>
              </td>
              <td>
                <va-avatar
                  square
                  :src="myAvatarUrl()"
                  @click="this.showAvatar = !this.showAvatar"
                  style="width: 80px; height: 80px"
                />
              </td>
            </tr>
            <tr>
              <td class="head">姓名</td>
              <td>{{ this.name }}</td>
            </tr>
            <tr>
              <td class="head">email</td>
              <td>{{ this.email }}</td>
            </tr>
            <tr>
              <td class="head">性别</td>
              <td>{{ myGender() }}</td>
            </tr>
            <tr>
              <td class="head">身份</td>
              <td>{{ myIdentity() }}</td>
            </tr>
            <tr v-show="this.identity != 4">
              <td class="head">账号激活状态</td>
              <td>{{ this.activated == 1 ? "已激活" : "未激活" }}</td>
            </tr>
          </tbody>
        </table>
      </va-card-content>
    </va-card>
    <!-- <va-button flat :to="{name: 'Information'}">information</va-button> -->
  </div>
</template>

<script>
export default {
  data() {
    return {
      showAvatar: false,

      id: "",
      name: "",
      gender: "",
      email: "",
      password: "",
      activated: 0,
      identity: "",

      activateAccountModalVisible: false,
      isSendingVerificationEmailButtonClicked: false,

      verificationCode: "",
      loading: false,

      showAlert: false,
    };
  },
  mounted() {
    this.id = localStorage.getItem("userId");
    this.password = localStorage.getItem("password");

    this.email = localStorage.getItem("userEmail");
    this.activated = localStorage.getItem("userActivated");
    if (this.activated == 0 || this.activated == "0") {
      this.showAlert = true;
    }

    fetch(this.$URL + "/user/get?id=" + this.id, {
      method: "GET",
      headers: { satoken: localStorage.getItem("token") },
    }).then((res) => {
      var result = res.json();
      result.then((res) => {
        // console.log(res)
        this.name = res.name;
        this.gender = res.gender;
        this.email = res.email;
        this.identity = res.identity;
        this.activated = res.activated;
      });
    });

    localStorage.setItem("gender", this.gender);
    // console.log("this.gender: " , this.gender)
    localStorage.setItem("username", this.name);
    // console.log("this.username: " , this.name)
  },
  methods: {
    myGender() {
      if (this.gender == 1) {
        return "男";
      } else if (this.gender == 2) {
        return "女";
      } else {
        return "未知";
      }
    },
    myIdentity() {
      if (this.identity == 1) {
        return "学生";
      } else if (this.identity == 2) {
        return "助教";
      } else if (this.identity == 3) {
        return "教师";
      } else if (this.identity == 4) {
        return "管理员";
      } else {
        return "未知";
      }
    },
    myAvatarUrl() {
      var myAvatar =
        this.$URL + "/file/download/avatar/avatar_" + this.id + ".jpg";
      // console.log("avatarUrl: ", myAvatar)
      return myAvatar;
    },

    handleActivateAccount() {
      this.activateAccountModalVisible = !this.activateAccountModalVisible;
    },

    handleSendVerificationEmail() {
      this.loading = true;
      // console.log(this.email)

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
          this.id,
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
.head {
  font-weight: bold;
  width: 150px;
  min-width: 120px;
}
</style>
