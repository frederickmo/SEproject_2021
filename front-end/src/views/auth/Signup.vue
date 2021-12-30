<template>
  <form @submit.prevent="onsubmit">
    <va-input
      v-model="id"
      type="id"
      label="id"
      :error="!!idErrors.length"
      :error-messages="idErrors"
    />

    <va-input
      v-model="email"
      type="email"
      label="email"
      :error="!!emailErrors.length"
      :error-messages="emailErrors"
      style="margin-top: 10px"
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
      <va-button @click="onsubmit">注册</va-button>
      <!-- <va-button @click="toLogIn">to log in</va-button> -->
    </div>
  </form>
</template>

<script>
export default {
  name: "SignUp",
  data() {
    return {
      id: "",
      email: "",
      password: "",
      idErrors: [],
      emailErrors: [],
      passwordErrors: [],
    };
  },
  computed: {
    formReady() {
      return (
        !this.emailErrors.length &&
        !this.passwordErrors.length &&
        !this.idErrors.length
      );
    },
  },
  methods: {
    onsubmit() {
      let myReg = /^(\w|(\.\w+))+@([a-zA-Z0-9_-]+\.)+(com|org|cn|net)+$/;
    //   console.log(this.id, this.email, this.password);
      if (!this.email) {
        this.emailErrors = ["email不能为空！"];
      } else if (!myReg.test(this.email)) {
        this.emailErrors = ["请填入正确格式的email!"];
      } else {
        this.emailErrors = [];
      }
      this.passwordErrors = this.password ? [] : ["密码不能为空！"];
      this.idErrors = this.id ? [] : ["ID不能为空！"];
      if (!this.formReady) {
        return;
      } else {
        let request = {
          id: this.id,
          password: this.password,
          email: this.email,
        };
        fetch(this.$URL + "/user/register", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(request),
        })
          .then((res) => res.json())
          .then((res) => {
            // console.log(res);
            if (res.code == 200) {
              this.$message.success("注册成功！");
              this.$router.push({
                name: "SignupSuccess",
                params: {
                  id: this.id,
                  password: this.password,
                },
              });
            } else if (res.code == 403) {
              this.$message.error("用户已存在！");
            } else if (res.code == 409) {
              this.$message.error("该邮箱已被注册！");
            } else {
              this.$message.error("注册失败！");
            }
          });
      }
    },
  },
};
</script>

<style></style>
