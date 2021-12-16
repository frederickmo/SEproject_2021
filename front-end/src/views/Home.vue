<template>

  <va-navbar color="#3d708f" gradient style="height: 8%; min-height: 60px;">
    <template v-slot:left>
      <va-checkbox v-model="minimized" label="Minimized(暂时还没实现)" />
    </template>
    <template v-slot:center>
      <span style="font-weight: bold; font-size: 18px">实验教学管理系统</span>
    </template>
    <template v-slot:right>
      <div style="height: 60px; min-height: 60px; display: flex">
        <div style="height: 60px">
          <va-button style="top: 12px" round @click="this.$router.push({name: 'BasicInfo'})">
          <va-avatar :src="myAvatarUrl()" />
          </va-button>
        </div>
      <div style="line-height: 60px; margin-left: 20px">
        欢迎，{{this.name}}
      </div>
      </div>
    </template>
  </va-navbar>

<el-container style="width: 100%; height: 92%; border: 1px solid #eee">

  <va-sidebar
textColor="dark"
width="12rem"
minimizedWidth="0">

  <va-accordion
  v-model="value"
  v-show="this.identity==4"
  style="font-weight: bold"
  multiply
  inset
  >

    <va-collapse
    key="0"
    header="基本信息"
    icon="person_outline">
      <va-sidebar-item>
        <va-sidebar-item-content
        @click="onClickBasicInfo()">
          <va-sidebar-item-title>
            查看基本信息
          </va-sidebar-item-title>
        </va-sidebar-item-content>
      </va-sidebar-item>
      <va-sidebar-item>
        <va-sidebar-item-content
        @click="onClickEditBasicInfo()">
          <va-sidebar-item-title>
            修改基本信息
          </va-sidebar-item-title>
        </va-sidebar-item-content>
      </va-sidebar-item>
    </va-collapse>

    <va-collapse
    key="1"
    header="通知公告"
    icon="notifications"
    >
      <va-sidebar-item>
        <va-sidebar-item-content @click="this.$router.push({name: 'Announcement'})">
          <va-sidebar-item-title>通知公告</va-sidebar-item-title>
        </va-sidebar-item-content>
      </va-sidebar-item>
    </va-collapse>
    
    <va-collapse
    key="2"
    header="用户管理"
    icon="manage_accounts"
    >
      <va-sidebar-item>
        <va-sidebar-item-content @click="this.$router.push({name: 'AccountManagement'})">
          <va-sidebar-item-title>用户管理</va-sidebar-item-title>
        </va-sidebar-item-content>
      </va-sidebar-item>
    </va-collapse>
    
    <va-sidebar-item>
      <va-sidebar-item-content
      style="text-align: center;" 
      @click="this.$router.push({name: 'Login', params: {tabValue: 0}})">
        <va-sidebar-item-title>
          退出登录
        </va-sidebar-item-title>
      </va-sidebar-item-content>
    </va-sidebar-item>

  </va-accordion>


  <va-accordion
  v-model="value"
  v-show="this.identity==2||this.identity==3"
  style="font-weight: bold"
  multiply
  inset>
  
    <va-collapse
    key="0"
    header="通知公告"
    icon="notifications">
      <va-sidebar-item>
        <va-sidebar-item-content
        @click="this.$router.push({name: 'Announcement'})">
        <va-sidebar-item-title>通知公告</va-sidebar-item-title>
        </va-sidebar-item-content>
      </va-sidebar-item>
    </va-collapse>

    <va-collapse
    key="1"
    header="基本信息"
    icon="person_outline">
      <va-sidebar-item>
        <va-sidebar-item-content
        @click="onClickBasicInfo()">
          <va-sidebar-item-title>
            查看基本信息
          </va-sidebar-item-title>
        </va-sidebar-item-content>
      </va-sidebar-item>
      <va-sidebar-item>
        <va-sidebar-item-content
        @click="onClickEditBasicInfo()">
          <va-sidebar-item-title>
            修改基本信息
          </va-sidebar-item-title>
        </va-sidebar-item-content>
      </va-sidebar-item>
    </va-collapse>
    <va-collapse
    key="2"
    header="课程资源"
    icon="attach_file">
      <va-sidebar-item>
        <va-sidebar-item-content
        @click="onClickCourseResources()">
          <va-sidebar-item-title>
            课程资源
          </va-sidebar-item-title>
        </va-sidebar-item-content>
      </va-sidebar-item>
    </va-collapse>
    <va-collapse
    key="3"
    header="课程管理"
    icon="source">
      <va-sidebar-item>
        <va-sidebar-item-content
        @click="onClickCourseManagement()">
          <va-sidebar-item-title>
            课程管理
          </va-sidebar-item-title>
        </va-sidebar-item-content>
      </va-sidebar-item>
    </va-collapse>
    <va-collapse
    key="4"
    header="实验项目管理"
    icon="mode">
      <va-sidebar-item>
        <va-sidebar-item-content
        @click="onClickTaskManagement()">
          <va-sidebar-item-title>
            实验项目管理
          </va-sidebar-item-title>
        </va-sidebar-item-content>
      </va-sidebar-item>
    </va-collapse>
    <va-collapse
    key="5"
    header="成绩管理"
    icon="emoji_events">
      <va-sidebar-item>
        <va-sidebar-item-content
        @click="onClickGradeManagement()">
          <va-sidebar-item-title>
            成绩管理
          </va-sidebar-item-title>
        </va-sidebar-item-content>
      </va-sidebar-item>
    </va-collapse>
    <va-sidebar-item>
      <va-sidebar-item-content
      style="text-align: center;" 
      @click="this.$router.push({name: 'Login', params: {tabValue: 0}})">
        <va-sidebar-item-title>
          退出登录
        </va-sidebar-item-title>
      </va-sidebar-item-content>
    </va-sidebar-item>
  </va-accordion>

  <va-accordion
  v-model="value"
  v-show="this.identity==1"
  style="font-weight: bold"
  multiply
  inset>
    <va-collapse
    key="0"
    header="通知公告"
    icon="notifications">
      <va-sidebar-item>
        <va-sidebar-item-content
        @click="this.$router.push({name: 'Announcement'})">
        <va-sidebar-item-title>通知公告</va-sidebar-item-title>
        </va-sidebar-item-content>
      </va-sidebar-item>
    </va-collapse>

    <va-collapse
    key="1"
    header="基本信息"
    icon="person_outline">
      <va-sidebar-item>
        <va-sidebar-item-content
        @click="onClickBasicInfo()">
          <va-sidebar-item-title>
            查看基本信息
          </va-sidebar-item-title>
        </va-sidebar-item-content>
      </va-sidebar-item>
      <va-sidebar-item>
        <va-sidebar-item-content
        @click="onClickEditBasicInfo()">
          <va-sidebar-item-title>
            修改基本信息
          </va-sidebar-item-title>
        </va-sidebar-item-content>
      </va-sidebar-item>
    </va-collapse>
    <va-collapse
    key="2"
    header="课程资源"
    icon="attach_file">
      <va-sidebar-item>
        <va-sidebar-item-content
        @click="onClickCourseResources()">
          <va-sidebar-item-title>
            课程资源
          </va-sidebar-item-title>
        </va-sidebar-item-content>
      </va-sidebar-item>
    </va-collapse>
    <va-collapse
    key="3"
    header="我的课程"
    icon="source">
      <va-sidebar-item>
        <va-sidebar-item-content
        @click="onClickMyCourses()">
          <va-sidebar-item-title>
            我的课程
          </va-sidebar-item-title>
        </va-sidebar-item-content>
      </va-sidebar-item>
    </va-collapse>
    <va-collapse
    key="4"
    header="我的实验项目"
    icon="mode">
      <va-sidebar-item>
        <va-sidebar-item-content
        @click="onClickMyTasks()">
          <va-sidebar-item-title>
            我的实验项目
          </va-sidebar-item-title>
        </va-sidebar-item-content>
      </va-sidebar-item>
    </va-collapse>
    <va-collapse
    key="5"
    header="我的成绩"
    icon="emoji_events">
      <va-sidebar-item>
        <va-sidebar-item-content
        @click="onClickMyGrades()">
          <va-sidebar-item-title>
            我的成绩
          </va-sidebar-item-title>
        </va-sidebar-item-content>
      </va-sidebar-item>
    </va-collapse>
    <va-sidebar-item>
      <va-sidebar-item-content
      style="text-align: center;" 
      @click="this.$router.push({name: 'Login', params: {tabValue: 0}})">
        <va-sidebar-item-title>
          退出登录
        </va-sidebar-item-title>
      </va-sidebar-item-content>
    </va-sidebar-item>

    <!-- <va-sidebar-item>
      <va-sidebar-item-content
      style="text-align: center;" 
      @click="this.$router.push({name: 'About'})">
        <va-sidebar-item-title>
          About
        </va-sidebar-item-title>
      </va-sidebar-item-content>
    </va-sidebar-item> -->
  </va-accordion>
  

</va-sidebar>
  <el-container>

    <el-main>
      <!-- <Announcement v-if="mainIndex==0" />
      <PersonalInfo v-if="mainIndex==1" />
      <EditPersonalInfo v-if="mainIndex==2" /> -->

      <!-- 这里改成了嵌套路由 -->

      <transition name="fade">
      <router-view />
      </transition>

    </el-main>
  </el-container>
</el-container>
</template>

<script>
// @ is an alias to /src
// import HelloWorld from '@/components/HelloWorld.vue'

// import Announcement from '../components/Announcement.vue'
// import PersonalInfo from '../components/PersonalInfo.vue'
// import EditPersonalInfo from '../components/EditPersonalInfo.vue'

export default {
  name: 'Home',
  components: {
    // HelloWorld
    // Announcement,
    // PersonalInfo,
    // EditPersonalInfo,
  },
  data () {
    return {
      minimized: false,
      mainIndex: 0,


      id: 0,
      name: '',
      gender: 0,
      email: '',
      password: '',
      activated: 0,
      identity: 0,

      studentHome: true,

    }
  },
  mounted () {
      // console.log("this.$URL: ", this.$URL)

      this.id = localStorage.getItem("userId")
      console.log("mounted获取到id了吗? id = " + this.id)


    fetch(this.$URL + "/user/get?id=" + this.id, {
      method: "GET",
    }).then((res) => {
      console.log(res)
      var result = res.json()
      result.then((result) => {
        console.log(result)
        this.id = result.id
        this.name = result.name
        this.password = result.password
        this.identity = result.identity
        this.activated = result.activated

        // localStorage.setItem("userId", this.id)
        // localStorage.setItem("password", this.password)
        localStorage.setItem("username", this.name)
        localStorage.setItem("gender", this.gender)
        localStorage.setItem("userIdentity", this.identity)

        if (this.identity == 2 || this.identity == 3 || this.identity == 4) {
          this.studentHome = false
        } else {
          this.studentHome = true
        }

        this.$router.push({
          name: 'BasicInfo'
        })

      })
    })


  },
  methods: {
    showByIdentity () {
      if (this.identity == 0 && this.identity == 1) {
        return false
      } else {
        return true
      }
    },

    myAvatarUrl() {
      // console.log("看看哪个先加载：this.id = " + this.id)
      let myAvatar = this.$URL + "/file/download/avatar/avatar_" + this.id + ".jpg"
      // console.log("avatarUrl: ", myAvatar)
      return myAvatar
      
    },
    onClickAnnouncement () {
      this.$router.push({
        name: 'Announcement',
        params: {
          userInfo: this.userInfo
        }
      })
    },
    onClickBasicInfo () {
      this.$router.push({
        name: 'BasicInfo',
        params: {
          userInfo: this.userInfo
        }
      })
    },
    onClickEditBasicInfo () {
      this.$router.push({
        name: 'EditBasicInfo',
        params: {
          userInfo: this.userInfo
        }
      })
    },
    onClickCourseResources () {
      this.$router.push({
        name: 'CourseResources',
        params: {
          userInfo: this.userInfo
        }
      })
    },
    onClickMyCourses () {
      this.$router.push({
        name: 'MyCourses',
        params: {
          userInfo: this.userInfo
        }
      })
    },
    onClickCourseManagement () {
      if (this.identity == 0 || this.identity == 1) {
        this.$notification.warning('无操作权限')
      } else {
        this.$router.push({
          name: 'CourseManagement',
          params: {
            userInfo: this.userInfo
          }})
      }
    },
    onClickMyTasks () {
      this.$router.push({
        name: 'MyTasks',
        params: {
          userInfo: this.userInfo
        }
      })
    },
    onClickTaskManagement () {
      this.$router.push({
        name: 'TaskManagement',
        params: {
          userInfo: this.userInfo
        }})
      
    },
    onClickMyGrades () {
      this.$router.push({
        name: 'MyGrades',
        params: {
          userInfo: this.userInfo
        }
      })
    },
    onClickGradeManagement () {
      if (this.identity == 0 || this.identity == 1) {
        this.$notification.warning('无操作权限')
      } else {
        this.$router.push({
          name: 'GradeManagement',
          params: {
            userInfo: this.userInfo
          }})
      }
    },
  }
}
</script>

<style>

html,
body,
#app {
  padding: 0;
  margin: 0;
  height: 100%
}

.el-container {
  /*设置内部填充为0，几个布局元素之间没有间距*/
  padding: 0px;
  /*外部间距也是如此设置*/
  margin: 0px;
  /*统一设置高度为100%*/
  height: 100%;
}

.fade-enter {
  opacity:0;
}
.fade-leave{
  opacity:1;
}
.fade-enter-active{
  transition:opacity .5s;
}
.fade-leave-active{
  opacity:0;
  transition:opacity .5s;
}

</style>