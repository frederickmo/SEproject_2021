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
          <va-list>
              <!-- <va-list-label style="font-size: 20px">
                  公告列表
              </va-list-label> -->

              <va-list-item
              v-for="(notification, index) in notifications"
              :key="index"
              to="/home">
                <!-- <va-list-item-section>
                    <va-avatar></va-avatar>
                </va-list-item-section> -->
                <va-list-item-section style="text-align: left; font-size: 16px">
                    <va-list-item-label style="font-weight: bold">{{notification.title}}</va-list-item-label>
                <va-list-item-label caption>{{notification.abstract}}</va-list-item-label>
                </va-list-item-section>
                
                <va-list-item-section style="text-align: right">
                    <va-list-item-label>{{notification.date}}</va-list-item-label>
                </va-list-item-section>

                <va-list-item-section icon>
                    <va-icon name="remove_red_eye" color="gray" />
                </va-list-item-section>


              </va-list-item>
          </va-list>
      </va-card-content>
  </va-card>
  </div>
</template>

<script>
export default {
    data () {
        return {
            notifications: [
                { title: '考试通知', abstract: '这是一条占位置的通知', date: '2021-11-4'},
                {title: '实验通知', abstract: '这是一条假装有用的通知', date: '2021-10-5'},
                {title: '实验结束通知', abstract: '虽然现在没有任何实验可以做', date: '2021-12-31'},
                {title: '课程结束预告', abstract: '这是一条没有用的通知', date: '2021-11-20'},
                {title: '开课预告', abstract: '这是一门挂科率90%的课', date: '2021-12-15'},
            ],
            userInfo: {}
        }
    },
    mounted () {
        console.log(this.$route.params.userInfo)
        this.userInfo = this.$route.params.userInfo
    }
}
</script>

<style>
.va-card-title {
    font-size: 20px;
}

</style>