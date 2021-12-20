<template>
  <div>
      <a-modal
      v-model:visible="modifyInfoModalVisible"
      hide-cancel
      ok-text="取消"
      >
        <template #title>
            修改信息
        </template>
        <div style="display: flex; line-height: 200%">
            <div style="margin-left: 2%; width: 8%; font-weight: bold; text-align: center">ID</div>
            <div style="width: 20%">
                <a-input v-model="this.modifyInfo.id" readonly />
            </div>
            <div style="width: 10%; font-weight: bold; text-align: center">姓名</div>
            <div style="width: 30%">
                <a-input v-model="this.modifyInfo.name" readonly />
            </div>
        </div>
        <div style="display: flex; line-height: 200%; margin-top: 10px">
            <div style="width: 10%; font-weight: bold; text-align: center">性别</div>
            <div style="width: 20%">
                <a-input v-model="this.modifyInfo.gender" readonly />
            </div>
            <div style="width: 10%; font-weight: bold; text-align: center">邮箱</div>
            <div style="width: 50%">
                <a-input v-model="this.modifyInfo.email" readonly />
            </div>
        </div>
        <div style="display: flex; line-height: 200%; margin-top: 10px">
            <div style="width: 10%; font-weight: bold; text-align: center">身份</div>
            <div>
            <a-radio-group v-model="modifyForm.identity" type="button">
                <a-radio value="1">学生</a-radio>
                <a-radio value="2">助教</a-radio>
                <a-radio value="3">教师</a-radio>
                <a-radio value="4">管理员</a-radio>
            </a-radio-group>
            </div>
        </div>
        <div style="display: flex; line-height: 200%; margin-top: 10px">
            <div style="width: 15%; font-weight: bold; text-align: center">激活状态</div>
            <div>
            <a-radio-group v-model="modifyForm.activated" type="button">
                <a-radio value="0">未激活</a-radio>
                <a-radio value="1">已激活</a-radio>
            </a-radio-group>
            </div>
        </div>
        <div style="margin-top: 20px">
            <a-button @click="confirmModifyInfo()">提交更改</a-button>
        </div>
      </a-modal>
      <va-card>
        <va-card-title style="font-size: 20px">{{this.handleChangeTitle()}}</va-card-title>
          <va-card-content>
              <div style="display: flex">
                  <a-button style="margin-right: 10px" @click="this.status=1">学生管理</a-button>
                  <a-button style="margin-right: 10px" @click="this.status=2">教师管理</a-button>
                  <a-button style="margin-right: 10px" @click="this.status=3">所有用户管理</a-button>
              </div>
            <el-table
            v-show="this.status==1"
            stripe
            :data="
            students.filter(
                (data) =>
                    !search | data.name.toLowerCase().includes(search.toLowerCase())
                )"
            >
                <el-table-column label="ID" prop="id" />
                <el-table-column label="姓名" prop="name" />
                <el-table-column label="性别" prop="gender" />
                <el-table-column label="邮箱" prop="email" />
                <el-table-column label="激活状态" prop="activated" />
                <el-table-column align="right">
                    <template #header>
                        <el-input v-model="search" size="mini" placeholder="输入姓名关键词查找" />
                    </template>
                    <template #default="scope">
                        <a-button @click="handleModifyStudentInfo(scope.$index)">修改信息</a-button>
                    </template>
                </el-table-column>

            </el-table>
            <el-table
            v-show="this.status==2"
            stripe
            :data="
            teachers.filter(
                (data) =>
                    !search | data.name.toLowerCase().includes(search.toLowerCase())
                )"
            >
                <el-table-column label="ID" prop="id" />
                <el-table-column label="身份" prop="identity" />
                <el-table-column label="姓名" prop="name" />
                <el-table-column label="性别" prop="gender" />
                <el-table-column label="邮箱" prop="email" />
                <el-table-column label="激活状态" prop="activated" />
                <el-table-column align="right">
                    <template #header>
                        <el-input v-model="search" size="mini" placeholder="输入姓名关键词查找" />
                    </template>
                    <template #default="scope">
                        <a-button @click="handleModifyTeacherInfo(scope.$index)">修改信息</a-button>
                    </template>
                </el-table-column>

            </el-table>
            <el-table
            v-show="this.status==3"
            stripe
            :data="
            allUsers.filter(
                (data) =>
                    !search | data.name.toLowerCase().includes(search.toLowerCase())
                )"
            >
                <el-table-column label="ID" prop="id" />
                <el-table-column label="姓名" prop="name" />
                <el-table-column label="性别" prop="gender" />
                <el-table-column label="邮箱" prop="email" />
                <el-table-column label="身份" prop="identity" />  
                <el-table-column label="激活状态" prop="activated" />
                <el-table-column align="right">
                    <template #header>
                        <el-input v-model="search" size="mini" placeholder="输入姓名关键词查找" />
                    </template>
                    <template #default="scope">
                        <a-button @click="handleModifyInfo(scope.$index)">修改信息</a-button>
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
            administratorId: '',
            status: 1,

            modifyInfoModalVisible: false,

            students: [],
            teachers: [],
            allUsers: [],

            search: '',

            modifyInfo: {
                id: '',
                name: '',
                gender: '',
                email: '',
            },

            modifyForm: {
                identity: '',
                activated: ''
            }
        }
    },
    mounted() {
        this.administratorId = localStorage.getItem("userId")
        console.log(this.administratorId)

        fetch(this.$URL + "/user/get/student", {
            method: "GET",
            headers: { "satoken": localStorage.getItem("token") }
        })
        .then(res => res.json())
        .then(res => {
            this.students = res
            console.log("students:", this.students)
            for (let i = 0; i < this.students.length; ++i) {
                this.students[i].gender = this.students[i].gender==1? "男" : (this.students[i].gender==2 ? "女" : "未知")
                this.students[i].email = this.students[i].email ? this.students[i].email : "暂无"
                this.students[i].activated = this.students[i].activated ? "已激活" : "未激活"
            }
        })
        .then(async () => {
            fetch(this.$URL + "/user/get/teacher", {
                method: "GET",
                headers: { "satoken": localStorage.getItem("token") }
            })
            .then(res => res.json())
            .then(res => {
                this.teachers = res
                console.log("teachers: ", this.teachers)
                for (let i = 0; i < this.teachers.length; ++i) {
                    this.teachers[i].gender = this.teachers[i].gender==1 ? "男" : (this.teachers[i].gender==2 ? "女" : "未知")
                    this.teachers[i].email = this.teachers[i].email ? this.teachers[i].email : "暂无"
                    this.teachers[i].identity = this.teachers[i].identity==2 ? "助教" : "教师"
                    this.teachers[i].activated = this.teachers[i].activated ? "已激活" : "未激活"
                }
            })
        })
        .then(async () => {
            fetch(this.$URL + "/user/get/all", {
                method: "GET",
                headers: { "satoken": localStorage.getItem("token") }
            })
            .then(res => res.json())
            .then(res => {
                this.allUsers = res
                console.log("allUsers:", this.allUsers)
                for (let i = 0; i < this.allUsers.length; ++i) {
                    this.allUsers[i].gender = this.allUsers[i].gender==1 ? "男" : (this.allUsers[i].gender==2 ? "女" : "未知")
                    this.allUsers[i].email = this.allUsers[i].email ? this.allUsers[i].email : "暂无"
                    this.allUsers[i].identity = this.allUsers[i].identity==1 ? "学生" : (this.allUsers[i].identity==2 ? "助教" : (this.allUsers[i].identity==3 ? "教师" : (this.allUsers[i].identity==4 ? "管理员" : "未知")))
                    this.allUsers[i].activated = this.allUsers[i].activated ? "已激活" : "未激活"
                }
            })
        })
    },
    methods: {
        handleChangeTitle() {
            return this.status == 1 ? "学生管理" : (this.status == 2 ? "教师管理" : "所有用户管理")
        },
        handleModifyStudentInfo(index) {
            this.modifyInfo.id = this.students[index].id
            this.modifyInfo.name = this.students[index].name
            this.modifyInfo.gender = this.students[index].gender
            this.modifyInfo.email = this.students[index].email
            // this.modifyInfo.identity = this.students[index].identity
            // this.modifyInfo.activated = this.students[index].activated

            this.modifyInfoModalVisible = !this.modifyInfoModalVisible
        },
        handleModifyTeacherInfo(index) {
            this.modifyInfo.id = this.teachers[index].id
            this.modifyInfo.name = this.teachers[index].name
            this.modifyInfo.gender = this.teachers[index].gender
            this.modifyInfo.email = this.teachers[index].email
            // this.modifyInfo.identity = this.teachers[index].identity
            // this.modifyInfo.activated = this.teachers[index].activated

            this.modifyInfoModalVisible = !this.modifyInfoModalVisible
        },
        handleModifyInfo(index) {
            this.modifyInfo.id = this.allUsers[index].id
            this.modifyInfo.name = this.allUsers[index].name
            this.modifyInfo.gender = this.allUsers[index].gender
            this.modifyInfo.email = this.allUsers[index].email
            // this.modifyInfo.identity = this.allUsers[index].identity
            // this.modifyInfo.activated = this.allUsers[index].activated

            this.modifyInfoModalVisible = !this.modifyInfoModalVisible
        },
        confirmModifyInfo() {
            let req = {
                id: this.modifyInfo.id,
                identity: this.modifyForm.identity,
                activated: this.modifyForm.activated
            }
            console.log("req:", req)
            fetch(this.$URL + "/user/update/administrator", {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    "satoken": localStorage.getItem("token")
                },
                body: JSON.stringify(req)
            })
            .then(res => res.json())
            .then(res => {
                console.log(res)
                if (res.code == 200) {
                    this.$message.success("修改成功")
                    this.$router.replace({path: '/refresh'})
                } else {
                    this.$message.error("修改失败")
                }
            })
        }
    }
}
</script>

<style>

</style>