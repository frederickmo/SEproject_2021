<template>
  <div>
    <div style="margin-bottom: 10px">
      <va-breadcrumbs separator=">">
        <va-breadcrumbs-item label="成员管理" disabled />
        <va-breadcrumbs-item label="成员管理" to="/home/accountmanagement" />
      </va-breadcrumbs>
    </div>
    <!-- 修改信息的Modal -->
    <a-modal
      v-model:visible="modifyInfoModalVisible"
      hide-cancel
      ok-text="取消"
      v-on:close="handleCloseModifyModal"
    >
      <template #title> 修改信息 </template>
      <div style="display: flex; line-height: 200%">
        <div
          style="
            margin-left: 2%;
            width: 8%;
            font-weight: bold;
            text-align: center;
          "
        >
          ID
        </div>
        <div style="width: 20%">
          <a-input v-model="this.modifyInfo.id" readonly />
        </div>
        <div style="width: 10%; font-weight: bold; text-align: center">
          姓名
        </div>
        <div style="width: 30%">
          <a-input v-model="this.modifyInfo.name" readonly />
        </div>
      </div>
      <div style="display: flex; line-height: 200%; margin-top: 10px">
        <div style="width: 10%; font-weight: bold; text-align: center">
          性别
        </div>
        <div style="width: 20%">
          <a-input v-model="this.modifyInfo.gender" readonly />
        </div>
        <div style="width: 10%; font-weight: bold; text-align: center">
          邮箱
        </div>
        <div style="width: 50%">
          <a-input v-model="this.modifyInfo.email" readonly />
        </div>
      </div>
      <div style="display: flex; line-height: 200%; margin-top: 10px">
        <div style="width: 10%; font-weight: bold; text-align: center">
          身份
        </div>
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
        <div style="width: 15%; font-weight: bold; text-align: center">
          激活状态
        </div>
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
    <!-- 移除用户的提醒Modal -->
    <a-modal
      v-model:visible="removeModalVisible"
      v-on:ok="confirmRemove"
      simple
    >
      <template #title> 移除用户 </template>
      <div style="text-align: center">确认移除？</div>
    </a-modal>
    <va-card>
      <a-button
        style="position: absolute; right: 30px; top: 15px"
        shape="round"
        @click="this.$router.replace({ path: '/refresh' })"
      >
        <template #icon>
          <icon-refresh />
        </template>
        刷新
      </a-button>
      <va-card-title style="font-size: 20px">{{
        this.handleChangeTitle()
      }}</va-card-title>
      <va-card-content>
        <div style="display: flex">
          <a-button style="margin-right: 10px" @click="this.status = 1"
            >学生管理</a-button
          >
          <a-button style="margin-right: 10px" @click="this.status = 2"
            >教师管理</a-button
          >
          <a-button style="margin-right: 10px" @click="this.status = 3"
            >所有用户管理</a-button
          >
        </div>
        <el-table
          v-show="this.status == 1"
          stripe
          :data="
            students.filter(
              (data) =>
                !search | data.name.toLowerCase().includes(search.toLowerCase())
            )
          "
        >
          <el-table-column label="ID" prop="id" />
          <el-table-column label="姓名" prop="name" />
          <el-table-column label="性别" prop="gender" />
          <el-table-column label="邮箱" prop="email" />
          <el-table-column label="激活状态" prop="activated" />
          <el-table-column align="right">
            <template #header>
              <el-input
                v-model="search"
                size="mini"
                placeholder="输入姓名关键词查找"
              />
            </template>
            <template #default="scope">
              <a-button @click="handleModifyStudentInfo(scope.$index)"
                >修改</a-button
              >
              <a-button
                status="danger"
                style="margin-left: 10px"
                @click="handleRemove(scope.row)"
                >移除</a-button
              >
            </template>
          </el-table-column>
        </el-table>
        <el-table
          v-show="this.status == 2"
          stripe
          :data="
            teachers.filter(
              (data) =>
                !search | data.name.toLowerCase().includes(search.toLowerCase())
            )
          "
        >
          <el-table-column label="ID" prop="id" />
          <el-table-column label="身份" prop="identity" />
          <el-table-column label="姓名" prop="name" />
          <el-table-column label="性别" prop="gender" />
          <el-table-column label="邮箱" prop="email" />
          <el-table-column label="激活状态" prop="activated" />
          <el-table-column align="right">
            <template #header>
              <el-input
                v-model="search"
                size="mini"
                placeholder="输入姓名关键词查找"
              />
            </template>
            <template #default="scope">
              <a-button @click="handleModifyTeacherInfo(scope.$index)"
                >修改</a-button
              >
              <a-button
                status="danger"
                style="margin-left: 10px"
                @click="handleRemove(scope.row)"
                >移除</a-button
              >
            </template>
          </el-table-column>
        </el-table>
        <el-table
          v-show="this.status == 3"
          stripe
          :data="
            allUsers.filter(
              (data) =>
                !search | data.name.toLowerCase().includes(search.toLowerCase())
            )
          "
        >
          <el-table-column label="ID" prop="id" />
          <el-table-column label="姓名" prop="name" />
          <el-table-column label="性别" prop="gender" />
          <el-table-column label="邮箱" prop="email" />
          <el-table-column label="身份" prop="identity" />
          <el-table-column label="激活状态" prop="activated" />
          <el-table-column align="right">
            <template #header>
              <el-input
                v-model="search"
                size="mini"
                placeholder="输入姓名关键词查找"
              />
            </template>
            <template #default="scope">
              <a-button @click="handleModifyInfo(scope.$index)">修改</a-button>
              <a-button
                status="danger"
                style="margin-left: 10px"
                @click="handleRemove(scope.row)"
                >移除</a-button
              >
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
      administratorId: "",
      status: 1,

      modifyInfoModalVisible: false,
      removeModalVisible: false,

      removeId: "",

      students: [],
      teachers: [],
      allUsers: [],

      search: "",

      modifyInfo: {
        id: "",
        name: "",
        gender: "",
        email: "",
        identity: "",
        activated: "",
      },

      modifyForm: {
        identity: "",
        activated: "",
      },
    };
  },
  mounted() {
    this.administratorId = localStorage.getItem("userId");
    // console.log(this.administratorId)

    this.status = localStorage.getItem("pageStatus")
      ? localStorage.getItem("pageStatus")
      : 1;

    fetch(this.$URL + "/user/get/student", {
      method: "GET",
      headers: { satoken: localStorage.getItem("token") },
    })
      .then((res) => res.json())
      .then((res) => {
        this.students = res;
        // console.log("students:", this.students)
        for (let i = 0; i < this.students.length; ++i) {
          this.students[i].name =
            this.students[i].name == null ? "未知" : this.students[i].name;
          this.students[i].gender =
            this.students[i].gender == 1
              ? "男"
              : this.students[i].gender == 2
              ? "女"
              : "未知";
          this.students[i].email = this.students[i].email
            ? this.students[i].email
            : "暂无";
          this.students[i].activated = this.students[i].activated
            ? "已激活"
            : "未激活";
        }
        // console.log("修改后的students:", this.students)
      })
      .then(async () => {
        fetch(this.$URL + "/user/get/teacher", {
          method: "GET",
          headers: { satoken: localStorage.getItem("token") },
        })
          .then((res) => res.json())
          .then((res) => {
            this.teachers = res;
            // console.log("teachers: ", this.teachers)
            for (let i = 0; i < this.teachers.length; ++i) {
              this.teachers[i].name =
                this.teachers[i].name == null ? "未知" : this.teachers[i].name;
              this.teachers[i].gender =
                this.teachers[i].gender == 1
                  ? "男"
                  : this.teachers[i].gender == 2
                  ? "女"
                  : "未知";
              this.teachers[i].email = this.teachers[i].email
                ? this.teachers[i].email
                : "暂无";
              this.teachers[i].identity =
                this.teachers[i].identity == 2 ? "助教" : "教师";
              this.teachers[i].activated = this.teachers[i].activated
                ? "已激活"
                : "未激活";
            }
            // console.log("修改后的teachers:", this.teachers)
          });
      })
      .then(async () => {
        fetch(this.$URL + "/user/get/all", {
          method: "GET",
          headers: { satoken: localStorage.getItem("token") },
        })
          .then((res) => res.json())
          .then((res) => {
            this.allUsers = res;
            // console.log("allUsers:", this.allUsers)
            for (let i = 0; i < this.allUsers.length; ++i) {
              this.allUsers[i].name =
                this.allUsers[i].name == null ? "未知" : this.allUsers[i].name;
              this.allUsers[i].gender =
                this.allUsers[i].gender == 1
                  ? "男"
                  : this.allUsers[i].gender == 2
                  ? "女"
                  : "未知";
              this.allUsers[i].email = this.allUsers[i].email
                ? this.allUsers[i].email
                : "暂无";
              this.allUsers[i].identity =
                this.allUsers[i].identity == 1
                  ? "学生"
                  : this.allUsers[i].identity == 2
                  ? "助教"
                  : this.allUsers[i].identity == 3
                  ? "教师"
                  : this.allUsers[i].identity == 4
                  ? "管理员"
                  : "未知";
              this.allUsers[i].activated = this.allUsers[i].activated
                ? "已激活"
                : "未激活";
            }
            // console.log("修改后的allusers:", this.allUsers)
          });
      });
  },
  methods: {
    handleChangeTitle() {
      return this.status == 1
        ? "学生管理"
        : this.status == 2
        ? "教师管理"
        : "所有用户管理";
    },
    handleCloseModifyModal() {
      this.modifyForm.identity = "";
      this.modifyForm.activated = "";
    },
    handleModifyStudentInfo(index) {
      this.modifyInfo.id = this.students[index].id;
      this.modifyInfo.name = this.students[index].name;
      this.modifyInfo.gender = this.students[index].gender;
      this.modifyInfo.email = this.students[index].email;
      this.modifyInfo.identity = this.students[index].identity;
      this.modifyInfo.activated = this.students[index].activated;

      this.modifyInfoModalVisible = !this.modifyInfoModalVisible;
    },
    handleModifyTeacherInfo(index) {
      this.modifyInfo.id = this.teachers[index].id;
      this.modifyInfo.name = this.teachers[index].name;
      this.modifyInfo.gender = this.teachers[index].gender;
      this.modifyInfo.email = this.teachers[index].email;
      this.modifyInfo.identity = this.teachers[index].identity;
      this.modifyInfo.activated = this.teachers[index].activated;

      this.modifyInfoModalVisible = !this.modifyInfoModalVisible;
    },
    handleModifyInfo(index) {
      if (this.allUsers[index].identity == "管理员") {
        this.$message.error("不能修改管理员");
      } else {
        this.modifyInfo.id = this.allUsers[index].id;
        this.modifyInfo.name = this.allUsers[index].name;
        this.modifyInfo.gender = this.allUsers[index].gender;
        this.modifyInfo.email = this.allUsers[index].email;
        this.modifyInfo.identity = this.allUsers[index].identity;
        this.modifyInfo.activated = this.allUsers[index].activated;

        this.modifyInfoModalVisible = !this.modifyInfoModalVisible;
      }
    },
    confirmModifyInfo() {
      // console.log(this.modifyInfo)
      // console.log(this.modifyForm)
      if (this.modifyForm.identity == 4) {
        this.$message.error("不能修改用户身份为管理员");
        return;
      } else if (
        this.modifyInfo.identity == 1 &&
        (this.modifyForm.identity == 2 || this.modifyForm.identity == 3)
      ) {
        this.$message.error("不能将学生修改为助教或教师");
        return;
      } else if (
        (this.modifyInfo.identity == "助教" ||
          this.modifyInfo.identity == "教师") &&
        this.modifyForm.identity == 1
      ) {
        this.$message.error("不能将教师或助教修改为学生");
        return;
      }
      let req = {
        id: this.modifyInfo.id,
        identity: this.modifyForm.identity,
        activated: this.modifyForm.activated,
      };
      // console.log("req:", req)
      fetch(this.$URL + "/user/update/administrator", {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          satoken: localStorage.getItem("token"),
        },
        body: JSON.stringify(req),
      })
        .then((res) => res.json())
        .then((res) => {
          // console.log(res)
          if (res.code == 200) {
            this.$message.success("修改成功");
            localStorage.setItem("pageStatus", this.status);
            this.$router.replace({ path: "/refresh" });
          } else {
            this.$message.error("修改失败");
          }
        });
    },
    handleRemove(row) {
      // console.log(row)
      if (row.id == this.administratorId) {
        this.$message.error("不能移除自己");
      } else if (row.identity == "管理员") {
        this.$message.error("不能移除管理员");
      } else {
        this.removeId = row.id;
        this.removeModalVisible = !this.removeModalVisible;
      }
    },
    confirmRemove() {
      // console.log("被移除的id：", this.removeId)
      fetch(this.$URL + "/user/remove?id=" + this.removeId, {
        method: "DELETE",
        headers: { satoken: localStorage.getItem("token") },
      })
        .then((res) => res.json())
        .then((res) => {
          // console.log(res)
          if (res.code == 200) {
            this.$message.success("移除用户成功");
            localStorage.setItem("pageStatus", this.status);
            this.$router.replace({ path: "/refresh" });
          } else {
            this.$message.error("移除用户失败");
          }
        });
    },
  },
};
</script>

<style></style>
