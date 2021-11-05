<template>
  <div>
      <va-card class="auth-box">
          <va-card-content>
              <va-tabs style="font-size: 16px" center v-model="tabIndex">
                  <template #tabs>
                  <va-tab name="Login">登录</va-tab>
                  <va-tab name="Signup">注册</va-tab>
                  </template>
              </va-tabs>
              <va-separator />
              <div>
                  <router-view />
              </div>
          </va-card-content>
      </va-card>
  </div>

</template>

<script>
// import Login from './Login.vue'
// import Signup from './Signup.vue'

export default {
    name: 'Auth',
    components: {
        // Login,
        // Signup,
    },
    data () {
        return {
            tabValue: 0,
            activeName: ""
            // valueStr: 0
        }
    },
    computed: {
        tabIndex: {
            set(tabName) {
                this.$router.push({name: tabName})
            },
            get() {
                return this.$route.name
            }
        }
    },
    mounted() {
        console.log("tabValue transferred here: ", this.$route.params.tabValue)
        this.$router.push({name: 'Login'})
        this.activeName = 'login'
    },
    methods: {
        handleClick(tab, event) {
            console.log(tab, event)
            console.log('activeName: ', this.activeName)
            this.$router.push({name: this.activeName})
        }
    }
}
</script>

<style>
html, body {
    width: 100%;
}

.auth-box {
    width: 35%;
    height: 30%;
    min-height: 240px;
    position: fixed;
    margin: auto;
    left: 0;
    right: 0;
    top: 30%;
}

</style>