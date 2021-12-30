<template>
  <div>
    <!-- STEP1: 输入学号 -->
    <div style="text-align: center; font-weight: bold" v-show="step == 1">
      <a-spin :loading="loading" tip="请稍等">
        <div>
          <div style="font-size: 20px; margin-bottom: 20px">找回密码</div>
          <div style="margin: auto; width: 80%; margin-bottom: 30px">
            <div style="display: flex; line-height: 200%">
              <div style="width: 50%; text-align: right; margin-right: 3%">
                输入学号/工号
              </div>
              <div style="width: 50%">
                <a-input v-model="userId" allow-clear />
              </div>
            </div>
          </div>
          <a-button
            @click="handleSubmitUserId"
            shape="round"
            style="font-weight: bold; font-size: 18px"
            >提交</a-button
          >
        </div>
      </a-spin>
    </div>
    <!-- STEP2: 发送邮件/输入验证码 -->
    <div style="text-align: center; font-weight: bold" v-show="step == 2">
      <div>
        <div style="font-size: 20px; margin-bottom: 20px">提交验证码</div>
        <div style="margin: auto; width: 80%; margin-bottom: 30px">
          <div style="display: flex; line-height: 200%">
            <div style="width: 40%; text-align: right; margin-right: 3%">
              验证码：
            </div>
            <div style="width: 40%">
              <a-input v-model="verificationCode" allow-clear />
            </div>
          </div>
        </div>
        <a-button
          @click="handleSubmitVerificationCode"
          shape="round"
          style="font-weight: bold; font-size: 18px"
          >提交</a-button
        >
      </div>
    </div>
    <div style="text-align: center; font-weight: bold" v-show="step == 3">
      <div style="font-size: 18px; margin-bottom: 25px">您的密码为：</div>
      <a-input
        style="margin-bottom: 25px; width: 60%"
        v-model="password"
        readonly
      />
      <div>
        <a-button
          style="font-size: 18px; font-weight: bold"
          shape="round"
          @click="
            this.$router.push({
              name: 'Login',
              params: { id: this.userId, password: this.password },
            })
          "
          >前往登录</a-button
        >
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "RecoverPassword",
  data() {
    return {
      userId: "",
      verificationCode: "",
      password: "",

      step: 1,

      loading: false,
    };
  },
  mounted() {
    // console.log(this.$route.params.email);
  },
  methods: {
    handleSubmitUserId() {
      this.loading = true;
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
            this.loading = false;
            this.step = 2;
            this.$message.success("验证码已发送，请前往邮箱查收。");
          }
        });
    },
    handleSubmitVerificationCode() {
      this.loading = true;
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
            this.loading = false;
            this.step = 3;
            this.$message.success("验证成功");
          } else {
            this.loading = false;
            this.$message.error("验证失败，请重试");
            return;
          }
        })
        .then(async () => {
          fetch(this.$URL + "/user/recoverPassword?userId=" + this.userId, {
            method: "GET",
          })
            .then((res) => res.text())
            .then((res) => {
            //   console.log(res);
              this.password = res;
            });
        });
    },
  },
};
</script>

<style></style>
