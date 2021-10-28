<template>
  <div>
      <va-card class="auth-box">
          <!-- <div>This is the params transferred here: {{this.$route.params.tabValue}}</div> -->
          <!-- <va-card-title style="height: 20%;">Log in</va-card-title> -->
          <!-- <div style="text-align: center">Log in</div>
          <va-divider /> -->
          <va-card-content>


              <!-- <a-tabs center  @change="onChangeTab">
                  <a-tab-pane key='login' title="LOG IN">
                  </a-tab-pane>
                  <a-tab-pane key='signup' title="SIGN UP">
                  </a-tab-pane>
              </a-tabs> -->

              <el-tabs v-model="activeName" @tab-click="handleClick">
                  <el-tab-pane label="Log in" name="login" />
                  <el-tab-pane label="Sign up" name="signup" />

              </el-tabs>



              <div>
                  <Login v-if="tabValue==0" />
                  <Signup v-else />
              </div>


              <!-- <va-tabs center v-model="tabIndex">
                  <template #tabs>
                  <va-tab name="login">Login</va-tab>
                  <va-tab name="signup">Signup</va-tab>
                  </template>
              </va-tabs>
              <va-separator /> -->
              <!-- <div>
                  <Login v-if="tabValue==0" />
                  <Signup v-else />
              </div> -->


          </va-card-content>
      </va-card>
      <!-- <va-card>
          <va-card-content>
              <va-tabs v-model="value">
                      <va-tab key="one">1</va-tab>
                      <va-tab key="two">2</va-tab>
                      <va-tab key="three">3</va-tab>
              </va-tabs>
          </va-card-content>
      </va-card> -->
  </div>

</template>

<script>
import Login from './Login.vue'
import Signup from './Signup.vue'

export default {
    name: 'Auth',
    components: {
        Login,
        Signup,
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
                if (tabName == 'login') {
                    this.tabValue = 0
                }
                else {
                    this.tabValue = 1
                }
            },
            get() {
                return this.$route.name
            }
        }
    },
    mounted() {
        console.log("tabValue transferred here: ", this.$route.params.tabValue)
        this.tabValue = this.$route.params.tabValue
        if (this.tabValue == 0) {
            this.activeName = "login"
        } else {
            this.activeName = "signup"
        }
    },
    methods: {
        onTouchTabLogin () {
            this.tabValue = 0
            console.log('tabValue: ', this.tabValue)
        },
        onTouchTabSignup () {
            this.tabValue = 1
            console.log('tabValue: ', this.tabValue)
        },
        onChangeTab (tabValue) {
            console.log('tabValue: ' , tabValue)
            if (tabValue == 'login') {
                this.tabValue = 0
            }
            else {
                this.tabValue = 1
            }
        },
        handleClick(tab, event) {
            console.log(tab, event)
            console.log('activeName: ', this.activeName)
            if (this.activeName == 'login') {
                this.tabValue = 0
            } else {
                this.tabValue = 1
            }
        }
    }
}
</script>

<style>
.auth-box {
    width: 35%;
    height: 40%;
    position: fixed;
    margin: auto;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
}

</style>