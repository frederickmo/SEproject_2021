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
      <div><img style="max-height: 450px" :src="myAvatarUrl()" /></div>
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
                      :src="myAvatarUrl()"
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
                  <td>{{myIdentity()}}</td>
                </tr>
                <tr>
                  <td class="head">账号激活状态</td>
                  <td>{{this.activated==1?'已激活':'未激活'}}</td>
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
  data () {
    return {
      showAvatar: false,

      id: '',
      name: '',
      gender: 0,
      email: '',
      password: '',
      activated: 1,
      identity: 0,
    }
  },
  mounted () {
    this.id = localStorage.getItem("userId")
    this.password = localStorage.getItem("password")
    fetch(this.$URL + "/user/get?id=" + this.id, {
      method: "GET"
    }).then((res) => {
      var result = res.json()
      result.then((res) => {
        console.log(res)
        this.name = res.name
        this.gender = res.gender
        this.email = res.email
        this.identity = res.identity
        this.activated = res.activated
      })
    })

    localStorage.setItem("gender", this.gender)
    console.log("this.gender: " , this.gender)
    localStorage.setItem("username", this.name)
    console.log("this.username: " , this.name)
    // console.log("name存入localstorage了吗？" + this.localStorage.getItem("gender"))
    // console.log("gender存入localstorage了吗？ " + this.localStorage.getItem("username"))
    
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
    myIdentity () {
      if (this.identity == 1) {
        return "学生"
      } else if (this.identity == 2) {
        return "助教"
      } else if (this.identity == 3) {
        return "教师"
      } else if (this.identity == 4) {
        return "责任教师"
      } else {
        return "未知"
      }
    },
    myAvatarUrl() {
      var myAvatar = this.$URL + "/file/download/avatar/avatar_" + this.id + ".jpg"
      console.log("avatarUrl: ", myAvatar)
      return myAvatar
      
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