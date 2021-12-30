<template>
  <div>
    <form @submit.prevent="onSubmit" style="margin-top: 30px">
      <va-input
        v-model="id"
        type="id"
        label="id"
        :error="!!idErrors.length"
        :error-messages="idErrors"
      />

      <va-input
        v-model="password"
        type="password"
        label="password"
        :error="!!passwordErrors.length"
        :error-messages="passwordErrors"
        style="margin-top: 10px"
      />
      <div style="margin-top: 15px">
        <va-button @click="onSubmit">登录</va-button>
        <va-button
          flat
          style="margin-left: 10px"
          @click="
            this.$router.push({
              name: 'RecoverPassword',
              params: { id: this.id },
            })
          "
          >忘记密码？</va-button
        >
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: "LogIn",
  data() {
    return {
      id: "",
      password: "",
      idErrors: [],
      passwordErrors: [],

      activated: "",
    };
  },
  computed: {
    formReady() {
      return !this.idErrors.length && !this.passwordErrors.length;
    },
  },
  mounted() {
    this.id = this.$route.params.id ? this.$route.params.id : 101;
    this.password = this.$route.params.password
      ? this.$route.params.password
      : 12345;

    fetch(this.$URL + "/user/isLogin", {
      method: "GET",
      headers: { satoken: localStorage.getItem("token") },
    })
      .then((res) => res.text())
      .then((res) => {
        // console.log(res);
        if (res == 1) {
          // this.$message.success("当前用户已登录")
          this.$router.push({ name: "Home" });
        } else {
          return;
        }
      });

    fetch(this.$URL + "/user/get?id=" + this.id, {
      method: "GET",
      headers: { satoken: localStorage.getItem("token") },
    })
      .then((res) => res.json())
      .then((res) => {
        this.activated = res.activated;
      });

    // console.log("params from the last page:");
    // console.log(this.$route.params.id, this.$route.params.password);
    this.id = this.$route.params.id ? this.$route.params.id : 101;
    this.password = this.$route.params.password
      ? this.$route.params.password
      : 12345;
  },
  methods: {
    onSubmit() {
      // let myReg=/^(\w|(\.\w+))+@([a-zA-Z0-9_-]+\.)+(com|org|cn|net)+$/
    //   console.log(this.id, this.password);
      if (!this.id) {
        this.idErrors = ["id不能为空"];
      } else {
        this.idErrors = [];
      }
      this.passwordErrors = this.password ? [] : ["密码不能为空"];
      if (!this.formReady) {
        return;
      } else {
        fetch(
          this.$URL +
            "/user/login?id=" +
            this.id +
            "&password=" +
            this.password,
          {
            method: "GET",
          }
        )
          .then((res) => res.json())
          .then((res) => {
            // console.log(res);
            if (res.code == 200) {
              if (this.activated) {
                this.$message.success("登录成功");
              }
              localStorage.setItem("userId", this.id);
              localStorage.setItem("password", this.password);
              localStorage.setItem("token", res.data.tokenValue);
              this.$router.push({ name: "Home" });
            } else {
              this.$message.error("用户不存在或密码错误");
            }
          });
      }
    },
  },
};
</script>

<style></style>
