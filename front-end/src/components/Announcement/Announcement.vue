<template>
  <div>
      <div style="margin-bottom: 10px">
        <va-breadcrumbs separator=">">
            <va-breadcrumbs-item label="通知公告" disabled />
            <va-breadcrumbs-item label="公告列表" to="/home/announcement" />
        </va-breadcrumbs>
      </div>
      <a-modal
      v-model:visible="modalVisible"
      footer="false"
      hide-cancel
      ok-text="取消"
      >
          <template #title>
              新增公告
          </template>
          <a-form :model="form">
              <a-form-item field="topic" label="标题" :rules="[{required: true, message: '请输入公告标题'}]"
              :validate-trigger="['change', 'input']"
              >
                  <a-input v-model="form.topic" placeholder="输入公告标题" allow-clear />
              </a-form-item>
              <a-form-item field="content" label="详细内容" :rules="[{required: true, message: '请输入公告内容'}]">
                  <a-textarea v-model="form.content" :auto-size="{minRows: 3, maxRows: 8}" placeholder="输入公告内容" allow-clear />
              </a-form-item>
              <a-form-item>
                  <a-radio-group v-model="form.type">
                      <a-radio value="0">所有人可见</a-radio>
                      <a-radio value="1">仅学生可见</a-radio>
                      <a-radio value="2">仅教师可见</a-radio>
                      <a-radio value="3">仅管理员可见</a-radio>
                  </a-radio-group>
              </a-form-item>
              <a-form-item>
                  <a-button @click="submitForm">提交</a-button>
              </a-form-item>
          </a-form>
      </a-modal>
      <a-modal
      v-model:visible="displayAnnouncementModalVisible"
      hide-cancel
      ok-text="关闭"
      >
          <template #title>
              {{displayAnnouncement.topic}}
          </template>
          <a-textarea v-show="!modifyAnnouncementStatus" v-model="this.displayAnnouncement.content" 
          auto-size
          readonly />
          <a-textarea v-show="modifyAnnouncementStatus" v-model="this.displayAnnouncement.content" 
          auto-size />
          <a-button v-show="!modifyAnnouncementStatus"
          @click="this.modifyAnnouncementStatus=!this.modifyAnnouncementStatus">
          点击修改
          </a-button>
          <a-button v-show="modifyAnnouncementStatus" @click="confirmModifyAnnouncement">确认提交</a-button>
      </a-modal>
  <va-card>
      <va-card-title style="font-size: 20px">公告列表</va-card-title>
      <va-card-content>
          <div style="text-align: left">
              <a-button @click="this.modalVisible=!this.modalVisible">新增公告</a-button>
              </div>
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

            modalVisible: false,
            displayAnnouncementModalVisible: false,

            modifyAnnouncementStatus: false,

            form: {
                topic: '',
                content: '',
                postedId: '',
                type: 0
            },

            displayAnnouncement: {
                topic: '',
                content: ''
            }
        }
    },
    mounted () {
        // console.log(this.$route.params.userInfo)
        // this.userInfo = this.$route.params.userInfo

        this.userId = localStorage.getItem("userId")
        this.userIdentity = localStorage.getItem("userIdentity")
        console.log("userId: ", this.userId)

        fetch(this.$URL + "/notice/get/time/desc", {
            method: "GET",
            headers: { "satoken": localStorage.getItem("token") }
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
            // this.$modal.info({
            //     title: this.notifications[index].topic,
            //     content: this.notifications[index].content
            // })
            this.displayAnnouncement.topic = this.notifications[index].topic
            this.displayAnnouncement.content = this.notifications[index].content
            this.displayAnnouncementModalVisible = !this.displayAnnouncementModalVisible

        },
        submitForm() {
            this.form.postedId = this.userId
            console.log("打印一下form：", this.form)
            if (!this.form.topic.length) {
                this.$message.info("请输入公告标题")
                return
            } else if (!this.form.content.length) {
                this.$message.info("请输入公告内容")
                return
            }
            fetch(this.$URL + "/notice/add", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "satoken": localStorage.getItem("token")
                },
                body: JSON.stringify(this.form)
            }).then(res => res.json())
            .then(res => {
                console.log(res)
                if (res.code == 200) {
                    this.$notification.success("添加成功")
                    this.$router.replace({path: '/refresh'})

                }
            })
        },
        confirmModifyAnnouncement() {
            /**
             * TODO: 这里还没写呢，明天白天在写
             */

            this.$message.success("修改成功")
            this.$router.replace({path: '/refresh'})
        }
    }
}
</script>

<style>
.va-card-title {
    font-size: 20px;
}

</style>