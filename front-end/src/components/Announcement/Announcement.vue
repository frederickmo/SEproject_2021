<template>
  <div>
      <div style="margin-bottom: 10px">
        <va-breadcrumbs separator=">">
            <va-breadcrumbs-item label="通知公告" disabled />
            <va-breadcrumbs-item label="公告列表" to="/home/announcement" />
        </va-breadcrumbs>
      </div>
  <va-card>
      <va-card-title style="font-size: 20px">公告列表</va-card-title>
      <va-card-content>
          <el-table
          stripe
          :data="
          notifications.filter(
              (data) =>
                !search | data.topic.toLowerCase().includes(search.toLowerCase())
            )"
          >
            <el-table-column label="标题" prop="topic" />
            <!-- <el-table-column label="预览" prop="content" /> -->
            <el-table-column label="发布时间" prop="updatedTime" />
            <el-table-column align="right">
                <template #header>
                    <el-input v-model="search" size="mini" placeholder="输入关键词查找" />
                </template>
                <template #default="scope">
                    <a-button @click="handleShowNotificationDetail(scope.$index)">查看</a-button>
                </template>
            </el-table-column>

          </el-table>
      </va-card-content>
  </va-card>
  </div>
</template>

<script>
export default {
    data () {
        return {
            studentNotifications: [
                { title: '考试通知', abstract: '这是一条占位置的通知', date: '2021-11-4'},
                {title: '实验通知', abstract: '这是一条假装有用的通知', date: '2021-10-5'},
                {title: '实验结束通知', abstract: '虽然现在没有任何实验可以做', date: '2021-12-31'},
                {title: '课程结束预告', abstract: '这是一条没有用的通知', date: '2021-11-20'},
                {title: '开课预告', abstract: '这是一门挂科率90%的课', date: '2021-12-15'},
            ],
            teacherNotifications: [

            ],
            userId: '',
            userName: '',
            userIdentity: '',

            notifications: [],
            search: '',
        }
    },
    mounted () {
        // console.log(this.$route.params.userInfo)
        // this.userInfo = this.$route.params.userInfo

        this.userId = localStorage.getItem("userId")
        this.userIdentity = localStorage.getItem("userIdentity")

        fetch(this.$URL + "/notice/get/time/desc", {
            method: "GET"
        }).then(response => response.json())
        .then(res => {
            console.log(res)
            this.notifications = res
        })
    },
    methods: {
        showStudentVersion () {
            if (this.userIdentity == 2 || this.userIdentity == 3 || this.userIdentity == 4) {
                return false
            } else {
                return true
            }
        },
        handleShowNotificationDetail(index) {
            this.$modal.info({
                title: this.notifications[index].topic,
                content: this.notifications[index].content
            })
        }
    }
}
</script>

<style>
.va-card-title {
    font-size: 20px;
}

</style>