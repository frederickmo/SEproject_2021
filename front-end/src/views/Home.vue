<template>

  <va-navbar style="height: 8%; min-height: 60px;">
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
        @click="onClickMyCourses()">
          <va-sidebar-item-title>
            我的课程
          </va-sidebar-item-title>
        </va-sidebar-item-content>
      </va-sidebar-item>
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
    header="考试管理"
    icon="mode">
      <va-sidebar-item>
        <va-sidebar-item-content
        @click="onClickMyExams()">
          <va-sidebar-item-title>
            我的考试
          </va-sidebar-item-title>
        </va-sidebar-item-content>
      </va-sidebar-item>
      <va-sidebar-item>
        <va-sidebar-item-content
        @click="onClickExamManagement()">
          <va-sidebar-item-title>
            考试管理
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
        @click="onClickMyGrades()">
          <va-sidebar-item-title>
            我的成绩
          </va-sidebar-item-title>
        </va-sidebar-item-content>
      </va-sidebar-item>
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
    <va-sidebar-item>
      <va-sidebar-item-content
      style="text-align: center;" 
      @click="this.$router.push({name: 'About'})">
        <va-sidebar-item-title>
          About
        </va-sidebar-item-title>
      </va-sidebar-item-content>
    </va-sidebar-item>
  </va-accordion>
</va-sidebar>
  <el-container>

    <el-main>
      <!-- <Announcement v-if="mainIndex==0" />
      <PersonalInfo v-if="mainIndex==1" />
      <EditPersonalInfo v-if="mainIndex==2" /> -->

      <!-- 这里改成了嵌套路由 -->
      <router-view />

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

    }
  },
  mounted () {
      console.log("this.$URL: ", this.$URL)

      this.id = localStorage.getItem("id")


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

        localStorage.setItem("id", this.id)
        localStorage.setItem("password", this.password)
      })
    })

    this.$router.push({
      name: 'BasicInfo'
    })

  },
  methods: {

    myAvatarUrl() {
      var myAvatar = this.$URL + "/file/download/avatar/avatar_" + this.id + ".jpg"
      console.log("avatarUrl: ", myAvatar)
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
    onClickMyExams () {
      this.$router.push({
        name: 'MyExams',
        params: {
          userInfo: this.userInfo
        }
      })
    },
    onClickExamManagement () {
      if (this.identity == 0 || this.identity == 1) {
        this.$notification.warning('无操作权限')
      } else {
        this.$router.push({
          name: 'ExamManagement',
          params: {
            userInfo: this.userInfo
          }})
      }
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

</style>