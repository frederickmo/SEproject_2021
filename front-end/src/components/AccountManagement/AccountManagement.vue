<template>
  <div>
      <va-card>
          <va-card-content>
              <div style="display: flex">
                  <a-button style="margin-right: 10px" @click="this.status=1">学生管理</a-button>
                  <a-button @click="this.status=2">教师管理</a-button>
              </div>
            <el-table
            stripe
            :data="
            students.filter(
                (data) =>
                    !search | data.topic.toLowerCase().includes(search.toLowerCase())
                )"
            >
                <el-table-column label="标题" prop="topic" />
                <el-table-column label="发布时间" prop="updatedTime" />
                <el-table-column align="right">
                    <template #header>
                        <el-input v-model="search" size="mini" placeholder="输入关键词查找" />
                    </template>
                    <template #default="scope">
                        <a-button @click="handleShowNotificationDetail(scope.$index)">修改信息</a-button>
                    </template>
                </el-table-column>

            </el-table>
          </va-card-content>
      </va-card>
  </div>
</template>

<script>
export default {
    data() {
        return {
            status: 1,

            students: [],
            teachers: [],

            search: '',
        }
    },
    mounted() {
        
    },
    methods: {

    }
}
</script>

<style>

</style>