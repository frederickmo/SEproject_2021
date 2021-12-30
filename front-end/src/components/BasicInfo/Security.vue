<template>
  <div>
    <div style="margin-bottom: 10px">
      <va-breadcrumbs separator=">">
        <va-breadcrumbs-item label="基本信息" disabled />
        <va-breadcrumbs-item label="账号安全" to="/home/security" />
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
      <va-card-title style="font-size: 20px; font-weight: bold"
        >账号安全</va-card-title
      >
      <va-card-content>
        <div style="height: 20px" />
        <div style="text-align: left">
          <div style="font-size: 18px; font-weight: bold">修改密码</div>

          <a-spin :loading="loading1" tip="邮件发送中，请稍等……">
            <div v-show="step == 1">
              <div style="height: 80px" />
              <div style="font-size: 15px; line-height: 20px">
                点击下方按钮发送验证码至邮箱：<br />{{ this.userEmail }}
              </div>
              <div style="height: 30px" />
              <div>
                <a-button shape="round" @click="handleSendEmail"
                  >发送验证码</a-button
                >
              </div>
            </div>
          </a-spin>

          <a-spin :loading="loading2" tip="验证码检验中，请稍等……">
            <div v-show="step == 2">
              <div style="height: 80px" />
              <div style="display: flex; line-height: 200%">
                <div style="font-size: 15px">请输入验证码：</div>
                <a-input
                  style="width: 150px"
                  v-model="verificationCode"
                  allow-clear
                />
              </div>
              <div style="height: 30px" />
              <div>
                <a-button
                  shape="round"
                  style="margin-right: 20px"
                  @click="handleSubmitVerificationCode"
                  >确认提交</a-button
                >
                <a-button
                  shape="round"
                  @click="this.$router.replace({ path: '/refresh' })"
                  >返回</a-button
                >
              </div>
            </div>
          </a-spin>

          <div v-show="step == 3">
            <div style="height: 80px" />
            <!-- <div style="display: flex; line-height: 200%; margin-bottom: 30px">
                        <div style="font-size: 15px">请输入新密码：</div>
                        <a-input style="width: 150px" v-model="verificationCode" allow-clear />
                    </div>
                    <div style="display: flex; line-height: 200%">
                        <div style="font-size: 15px">请确认新密码：</div>
                        <a-input style="width: 150px" v-model="verificationCode" allow-clear />
                    </div> -->
            <a-form ref="formRef" :model="passwords">
              <a-form-item field="password" label="请输入新密码：">
                <a-input style="width: 150px" v-model="passwords.password" />
              </a-form-item>
              <a-form-item
                field="confirmPassword"
                label="请确认新密码："
                :rules="rules"
              >
                <a-input
                  style="width: 150px"
                  v-model="passwords.confirmPassword"
                />
              </a-form-item>
              <div style="margin-top: 30px">
                <a-button
                  shape="round"
                  style="margin-right: 20px; margin-left: 12%"
                  @click="handleSubmitNewPassword"
                  >确认提交</a-button
                >
                <a-button
                  shape="round"
                  @click="this.$router.replace({ path: '/refresh' })"
                  >返回</a-button
                >
              </div>
            </a-form>
            <div style="height: 30px" />
          </div>
        </div>
        <div style="height: 80px" />
      </va-card-content>
    </va-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userId: "",
      userEmail: "",
      oldPassword: "",
      newPassword: "",
      confirmNewPassword: "",

      passwords: {
        password: "",
        confirmPassword: "",
      },

      verificationCode: "",

      step: 1,
      loading1: false,
      loading2: false,

      rules: [
        {
          validator: (value, cb) => {
            return new Promise((resolve) => {
              window.setTimeout(() => {
                if (value != this.passwords.password) {
                  cb("两次输入的密码不一致");
                }
                resolve();
              }, 10);
            });
          },
        },
      ],
    };
  },
  mounted() {
    this.userId = localStorage.getItem("userId");
    this.userEmail = localStorage.getItem("userEmail");
    // console.log(this.userId, this.userEmail);
  },
  methods: {
    handleSendEmail() {
      // this.step = 2
      this.loading1 = true;
      fetch(
        this.$URL + "/user/updatePassword/sendEmail?userId=" + this.userId,
        {
          method: "POST",
        }
      )
        .then((res) => res.json())
        .then((res) => {
        //   console.log(res);
          if (res.code == 200) {
            this.$message.success("邮件已发送，请前往邮箱查收");
            this.loading1 = false;
            this.step = 2;
          } else if (res.code == 402) {
            this.loading1 = false;
            this.$message.error("用户邮箱不存在，请先填写一个邮箱");
          } else {
            this.loading1 = false;
            this.$message.error("邮件发送失败，请重试");
          }
        });
    },
    handleSubmitVerificationCode() {
      // this.step = 3
      this.loading2 = true;
      fetch(
        this.$URL +
          "/user/updatePassword/checkCode?code=" +
          this.verificationCode +
          "&userId=" +
          this.userId,
        {
          method: "POST",
        }
      )
        .then((res) => res.json())
        .then((res) => {
        //   console.log(res);
          if (res.code == 200) {
            this.loading2 = false;
            this.$message.success("验证成功");
            this.step = 3;
          } else if (res.code == 417) {
            this.loading2 = false;
            this.$message.error("验证码已过期，请重新发送");
          } else if (res.code == 414) {
            this.loading2 = false;
            this.$message.error("验证码错误");
          } else {
            this.loading2 = false;
            this.$message.error("验证失败");
          }
        });
    },
    checkPassword() {
      // if (this.newPassword.length != this.confirmNewPassword.length || this.)
    },
    handleSubmitNewPassword() {
      if (
        this.passwords.password == undefined ||
        this.passwords.password == ""
      ) {
        this.$message.error("请输入新密码");
        return;
      } else if (
        this.passwords.confirmPassword == undefined ||
        this.passwords.confirmPassword == ""
      ) {
        this.$message.error("请输入新密码");
      } else if (this.passwords.password != this.passwords.confirmPassword) {
        this.$message.error("两次输入的密码不一致");
      } else {
        fetch(
          this.$URL +
            "/user/updatePassword?userId=" +
            this.userId +
            "&newPassword=" +
            this.passwords.password,
          {
            method: "POST",
            headers: { satoken: localStorage.getItem("token") },
          }
        )
          .then((res) => res.json())
          .then((res) => {
            // console.log(res);
            if (res.code == 200) {
              this.$message.success("密码已重置！");
              this.$router.replace({ path: "/refresh" });
            } else if (res.code == 403) {
              this.$message.error("新密码与旧密码相同");
            } else {
              this.$message.error("修改失败");
            }
          });
      }
    },
  },
};
</script>

<style></style>
