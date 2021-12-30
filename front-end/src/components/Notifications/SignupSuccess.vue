<template>
  <div>
    <va-card class="auth-box">
      <!-- <va-card-title style="text-align: center; font-size: 20px">注册成功</va-card-title> -->
      <va-card-content
        style="
          margin-top: 20px;
          margin-bottom: 15px;
          font-size: 20px;
          font-weight: bold;
        "
      >
        注册成功
      </va-card-content>
      <va-card-content>
        <va-button flat @click="onSwitchToLogin()">前往登录</va-button>
      </va-card-content>
    </va-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      id: 0,
      password: "",
    };
  },
  mounted() {
    // console.log(this.$route.params.id, this.$route.params.password);
    this.id = this.$route.params.id;
    this.password = this.$route.params.password;
  },
  methods: {
    onSwitchToLogin() {
      this.$router.push({
        name: "Login",
        params: {
          id: this.id,
          password: this.password,
        },
      });
    },
  },
};
</script>

<style scoped>
html,
body {
  width: 100%;
}

.auth-box {
  width: 35%;
  height: 20%;
  min-height: 160px;
  position: fixed;
  margin: auto;
  left: 0;
  right: 0;
  top: 30%;
}
</style>
