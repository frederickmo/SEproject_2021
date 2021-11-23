<template>
  <div>
      <div style="margin-bottom: 10px">
        <va-breadcrumbs separator=">">
            <va-breadcrumbs-item label="基本信息" disabled />
            <va-breadcrumbs-item label="查看基本信息" to="/home/basicinfo" />
        </va-breadcrumbs>
      </div>
      <va-modal ref="modal" v-model="showAvatar"
      hide-default-actions title="查看大图"
      >
      <div><img src="https://avatars.githubusercontent.com/u/58105082?v=4" /></div>
      <div style="text-align: center">
        <va-button flat style="margin-top: 15px; color: gray" @click="this.showAvatar=!this.showAvatar">关闭</va-button>
      </div>
        
      </va-modal>
    <va-card>
      <va-card-title style="font-size: 20px">
        基本信息
        <va-button
        flat
        @click="this.$router.push({name: 'EditBasicInfo'})"
        style="font-size: 10px; color: gray; margin-left: 10px">
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
                      src="https://avatars.githubusercontent.com/u/58105082?v=4"
                      @click="this.showAvatar=!this.showAvatar"
                      style="width: 80px; height: 80px" />
                  </td>
                </tr>
                <tr>
                  <td class="head">姓名</td>
                  <td>{{this.name}}</td>
                </tr>
                <tr>
                  <td class="head">E-mail</td>
                  <td>{{this.email}}</td>
                </tr>
                <tr>
                  <td class="head">性别</td>
                  <td>{{myGender()}}</td>
                </tr>
                <tr>
                  <td class="head">身份</td>
                  <td>{{myStatus()}}</td>
                </tr>
                <tr>
                  <td class="head">账号激活状态</td>
                  <td>{{this.activated==1?'已激活':'未激活'}}</td>
                </tr>
              </tbody>
            </table>
        </va-card-content>
    </va-card>
    <va-button flat :to="{name: 'Information'}">information</va-button>
  </div>
</template>

<script>
export default {
  data () {
    // let myStatus = () => {
    //   if (this.status == 0) {
    //     return "学生"
    //   } else if (this.status == 1) {
    //     return ""
    //   } else if (this.status == 2) {
    //     return ""
    //   } else if (this. status == 3) {
    //     return ""
    //   } else {
    //     return ""
    //   }
    // }
    return {
      showAvatar: false,

      name: '',
      gender: 2,
      email: '123@gmail.com',
      password: '12345',
      activated: 1,
      status: 0,
      userInfo: {
        email: this.email,
        password: this.password,
        name: 'John Doe',
        gender: 1,
        activated: this.activated,
        status: this.status
      }
    }
  },
  mounted () {
    // console.log(this.$route.params.userInfo.email)
    // this.userInfo = this.$route.params.userInfo ? this.$route.params.userInfo : this.userInfo
    // this.email = this.$route.params.userInfo ? this.$route.params.userInfo.email : this.email
    // console.log(localStorage.getItem('userInfo'))
    // this.userInfo = localStorage.getItem('userInfo')
    this.name = localStorage.getItem('name')
    this.gender = localStorage.getItem('gender')
  },
  methods: {
    myGender () {
      if (this.gender == 1) {
        return "男"
      } else if (this.gender == 2) {
        return "女"
      } else {
        return "未知"
      }
    },
    myStatus () {
      if (this.status == 0) {
        return "学生"
      } else if (this.status == 1) {
        return "助教"
      } else if (this.status == 2) {
        return "教师"
      } else if (this.status == 3) {
        return "责任教师"
      } else {
        return "未知"
      }
    }
  }
}
</script>

<style>
.head {
  font-weight: bold;
  width: 150px;
  min-width: 120px;
}
</style>