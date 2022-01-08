<template>
  <div>
    <div style="margin-bottom: 10px">
      <va-breadcrumbs separator=">">
        <va-breadcrumbs-item label="课程管理" disabled />
        <va-breadcrumbs-item label="课程管理" to="/home/coursemanagement" />
        <va-breadcrumbs-item
          :label="this.modifystatus == 1 ? '课程信息' : '成员管理'"
          disabled
        />
      </va-breadcrumbs>
    </div>
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
        this.modifystatus == 1 ? "课程信息" : "成员管理"
      }}</va-card-title>
      <div>
        <div style="display: flex; margin-left: 20px">
          <a-button style="margin-right: 10px" @click="this.modifystatus = 1"
            >课程信息</a-button
          >
          <a-button @click="this.modifystatus = 2">成员管理</a-button>
        </div>
      </div>

      <div v-if="this.modifystatus == 1">
        <div style="height: 12px" />
        <div style="text-align: left; margin-left: 20px">课程信息查看</div>
        <div
          v-if="this.modifystatus == 1"
          style="display: flex; line-height: 200%; margin-top: 10px"
        >
          <div style="width: 18%; font-weight: bold; text-align: center">
            课程名称：
          </div>
          <div style="width: 20%">
            <a-input v-model="this.courseName" readonly />
          </div>
          <div style="width: 18%; font-weight: bold; text-align: center">
            课程描述：
          </div>
          <div style="width: 30%">
            <a-input v-model="this.courseDescription" readonly />
          </div>
        </div>
        <div
          v-if="this.modifystatus == 1"
          style="display: flex; line-height: 200%; margin-top: 10px"
        >
          <div style="width: 18%; font-weight: bold; text-align: center">
            开设年份：
          </div>
          <div style="width: 20%">
            <a-input v-model="this.year" readonly />
          </div>
          <div style="width: 18%; font-weight: bold; text-align: center">
            学期：
          </div>
          <div style="width: 30%">
            <a-input v-model="this.semester" readonly />
          </div>
        </div>
        <div style="height: 20px" />
        <div style="text-align: center">
          <!-- <a-button @click="handleOk3()" color="#FF0000" style="background-color: rgb(0,0,0) text-align:center">提交更改</a-button> -->
          <a-button
            @click="this.modifystatus = 3"
            color="#e0e5df"
            style="color: rgb(40, 40, 40)"
            >点击修改</a-button
          >
        </div>
        <div style="height: 30px" />
      </div>

      <div v-if="this.modifystatus == 3">
        <div style="height: 12px" />
        <div style="text-align: left">课程信息修改</div>
        <div style="display: flex; line-height: 200%; margin-top: 10px">
          <div style="width: 18%; font-weight: bold; text-align: center">
            课程名称：
          </div>
          <div style="width: 20%">
            <a-input v-model="this.courseName" />
          </div>
          <div style="width: 18%; font-weight: bold; text-align: center">
            课程描述：
          </div>
          <div style="width: 30%">
            <a-input v-model="this.courseDescription" />
          </div>
        </div>
        <div style="display: flex; line-height: 200%; margin-top: 10px">
          <div style="width: 18%; font-weight: bold; text-align: center">
            开设年份：
          </div>
          <div style="width: 20%">
            <a-input v-model="this.year" />
          </div>
          <div style="width: 18%; font-weight: bold; text-align: center">
            学期：
          </div>
          <div style="width: 30%">
            <a-input v-model="this.semester" />
          </div>
        </div>
        <div style="height: 12px" />
        <div style="text-align: center">
          <!-- <a-button @click="handleOk3()" color="#FF0000" style="background-color: rgb(0,0,0) text-align:center">提交更改</a-button> -->
          <a-button
            @click="handleOk3()"
            color="#e0e5df"
            style="color: rgb(40, 40, 40)"
            >确认修改</a-button
          >
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <a-button
            v-if="this.on == 0"
            @click="handleClick4"
            status="danger"
            style="background-color: rgb(0,0,0) text-align:center"
            >点击删除课程</a-button
          >
        </div>
      </div>
      <div style="height: 2px" />

      <div v-if="this.modifystatus == 2">
        <div style="height: 12px" />
        <div style="display: flex; margin-left: 20px">
          <a-button
            size="mini"
            style="margin-right: 10px"
            @click="this.status = 1"
            >学生管理</a-button
          >
          <a-button size="mini" @click="this.status = 2">教师管理</a-button>
        </div>
        <el-table
          v-if="this.status == 1"
          :data="
            student.filter(
              (data) =>
                !search ||
                data.name.toLowerCase().includes(search.toLowerCase())
            )
          "
        >
          <el-table-column label="Id" prop="id" />
          <el-table-column label="Name" prop="name" />
          <el-table-column align="right">
            <template #header>
              <el-input
                v-model="search"
                size="mini"
                placeholder="Type to search"
              />
            </template>
            <template #default="scope">
              <el-button
                size="mini"
                type="danger"
                @click="DropCourse(scope.row)"
                >点击退课</el-button
              >
            </template>
          </el-table-column>
        </el-table>

        <el-table
          v-show="this.status == 2"
          :data="
            teacher.filter(
              (data) =>
                !search ||
                data.name.toLowerCase().includes(search.toLowerCase())
            )
          "
          style="width: 100%"
        >
          <el-table-column label="Id" prop="id" />
          <el-table-column label="Name" prop="name" />
          <el-table-column align="right">
            <template #header>
              <el-input
                v-model="search"
                size="mini"
                placeholder="Type to search"
              />
            </template>
            <template #default="scope">
              <el-button
                size="mini"
                type="danger"
                @click="DropmanageCourse(scope.row)"
                >点击取消授课</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <div style="height: 20px" />
        <div style="text-align: center">
          <a-button
            v-if="this.on == 0"
            @click="handleClick"
            color="#e0e5df"
            style="color: rgb(40, 40, 40)"
            >点击添加教师</a-button
          >
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

          <a-button
            v-if="this.on == 0"
            @click="handleClick1"
            color="#e0e5df"
            style="color: rgb(40, 40, 40)"
            >点击添加学生</a-button
          >
          <div style="height: 20px" />
          <el-upload
          :action="url"
          :http-request="importExcel"
          list-type="text"
          :show-file-list="false"
          >
          <el-button size="small" type="primary">批量导入学生</el-button>
          </el-upload>
        </div>
        <div style="height: 30px" />
      </div>
    </va-card>
  </div>

  <div style="height: 10px" />
  <div>
    <div>
      <a-modal
        v-model:visible="visible1"
        @ok="handleOk1"
        @cancel="handleCancel1"
        unmountOnClose
        @before-ok="handleBeforeOk1"
      >
        <template #title> 请输入学生id </template>
        <div>
          <va-input
            class="mb-4"
            v-model="this.studentid"
            placeholder="请输入学生id"
            outline
          />
        </div>
      </a-modal>

      <a-modal
        v-model:visible="visible"
        @ok="handleOk"
        @cancel="handleCancel"
        unmountOnClose
        @before-ok="handleBeforeOk"
      >
        <template #title> 请输入教师id </template>
        <div>
          <va-input
            class="mb-4"
            v-model="this.teacherid"
            placeholder="请输入教师id"
            outline
          />
        </div>
      </a-modal>
      <div style="height: 10px" />
    </div>

    <a-modal
      v-model:visible="visible4"
      @ok="handleOk4"
      @cancel="handleCancel4"
      unmountOnClose
      @before-ok="handleBeforeOk4"
    >
      <template #title> 删除课程 </template>
      <div>您确定要删除吗</div>
    </a-modal>
  </div>
</template>

<script>
import XLSX from "xlsx";
export default {
  data() {
    return {
      on: "",
      modifystatus: 1,
      status: 1,
      search: "",
      modifyInfoModalVisible: false,
      visible: false,
      visible1: false,
      visible3: false,
      visible4: false,
      teacherid: "",
      courseId: "",
      courseName: "",
      courseDescription: "",
      year: "",
      semester: "",
      course_manage: "",
      studentid: "",
      course: [],
      student: [],
      teacher: [],
    };
  },
  mounted() {
    this.on = this.$route.params.on;
    // console.log(this.on);
    this.id = localStorage.getItem("userId");
    this.courseId = localStorage.getItem("courseId");
    // if (this.$route.params.courseId == undefined) {
    // console.log("从子路由跳到父路由，需要重新查询课程数据");
    fetch(this.$URL + "/course/get?id=" + this.$route.params.courseId, {
      method: "GET",
      headers: { satoken: localStorage.getItem("token") },
    }).then((response) => {
      // console.log(response)
      let result = response.json();
      result.then((res) => {
        // console.log(res);
        this.course_manage = res.manager;
        this.courseName = res.name;
        this.courseDescription = res.description;
        if (res.year != null) this.year = res.year;
        else this.year = "暂未设置";
        if (res.semester != null) this.semester = res.semester;
        else this.semester = "暂未设置";
        // console.log(this.courseName + " " + this.courseDescription);
      });
    });

    fetch(this.$URL + "/takes/get/course/detail?courseId=" + this.courseId, {
      method: "GET",
      headers: { satoken: localStorage.getItem("token") },
    }).then((response) => {
      // console.log(response);
      let result = response.json();
      result.then((res) => {
        // console.log(res);
        this.student = res;
      });
    });

    fetch(this.$URL + "/manages/get/course/detail?courseId=" + this.courseId, {
      method: "GET",
      headers: { satoken: localStorage.getItem("token") },
    }).then((response) => {
      // console.log(response);
      let result = response.json();
      result.then((res) => {
        // console.log(res);
        this.teacher = res;
      });
    });
  },
  methods: {
    importExcel(content) {
      const file = content.file;
      // let file = file.files[0] // 使用传统的input方法需要加上这一步
      const types = file.name.split(".")[1];
      const fileType = ["xlsx", "xlc", "xlm", "xls", "xlt", "xlw", "csv"].some(
        (item) => item === types
      );
      if (!fileType) {
        this.$message("格式错误！请重新选择");
        return;
      }
      this.file2Xce(file).then((tabJson) => {
        if (tabJson && tabJson.length > 0) {
          this.xlsxJson = tabJson;
          this.fileList = this.xlsxJson[0].sheet;

          this.fileList.forEach((item, index, arr) => {
            console.log(item, index, arr);
            // if (item[n] === this.name) {
            //   console.log(item[n]+"90")
            //   this.dataForm = item['XXXX'] // 需要的值的表头
            // }
            //let n = '学号'
            this.studentid = item["学号"];
            this.handleOk1();
          });
        }
      });
    },
    file2Xce(file) {
      return new Promise(function (resolve, reject) {
        console.log(reject);
        const reader = new FileReader();
        reader.onload = function (e) {
          const data = e.target.result;
          this.wb = XLSX.read(data, {
            type: "binary",
          });
          const result = [];
          this.wb.SheetNames.forEach((sheetName) => {
            result.push({
              sheetName: sheetName,
              sheet: XLSX.utils.sheet_to_json(this.wb.Sheets[sheetName]),
            });
          });
          resolve(result);
        };
        // reader.readAsBinaryString(file.raw)
        reader.readAsBinaryString(file); // 传统input方法
      });
    },
    handleClick() {
      this.visible = true;
    },
    handleClick2() {
      this.$modal({});
    },
    handleOk() {
      // this.visible = false;
      // console.log(this.courseId + " " + this.teacherid);
      fetch(
        this.$URL +
          "/manages/add?courseId=" +
          this.courseId +
          "&teacherId=" +
          this.teacherid,
        {
          method: "POST",
          headers: { satoken: localStorage.getItem("token") },
        }
      ).then((response) => {
        // console.log(response);
        let result = response.json();
        result.then((res) => {
          // console.log(res);
          if (res.code == 200) {
            this.$message.success("添加教师成功");
            // console.log(this.teacher);

            fetch(
              this.$URL +
                "/manages/get/course/detail?courseId=" +
                this.courseId,
              {
                method: "GET",
                headers: { satoken: localStorage.getItem("token") },
              }
            ).then((response) => {
              // console.log(response);
              let result = response.json();
              result.then((res) => {
                // console.log(res);
                this.teacher = res;
              });
            });
          } else {
            this.$message.error("教师id有误");
          }
          //this.manage=res.manager
        });
      });
      this.teacherid = "";
    },
    handleCancel() {
      this.visible = false;
      this.teacherid = "";
    },
    handleBeforeOk(done) {
      window.setTimeout(() => {
        done();
      }, 3000);
    },
    handleClick1() {
      this.visible1 = true;
    },
    // handleClick2(){
    //   this.$modal({});
    // },
    handleOk1() {
      // this.visible = false;
      let submitForm = {
        studentId: this.studentid,
        courseId: this.courseId,
      };
      console.log(submitForm);
      fetch(this.$URL + "/takes/add", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          satoken: localStorage.getItem("token"),
        },
        body: JSON.stringify(submitForm),
      }).then((response) => {
        // console.log(response)
        let result = response.json();
        result.then((res) => {
          console.log(res);
          if (res.code == 200) {
            this.$message.success("添加成功！");

            //this.$router.go(0)
            fetch(
              this.$URL + "/takes/get/course/detail?courseId=" + this.courseId,
              {
                method: "GET",
                headers: { satoken: localStorage.getItem("token") },
              }
            ).then((response) => {
              console.log(response);
              let result = response.json();
              result.then((res) => {
                console.log(res);
                this.student = res;
              });
            });
          } else {
            //if(res.msg=="该学生已经选修该实验课程")
            console.log(res.msg);
            this.$message.error(res.msg);
            //this.$router.go(-1)
          }
        });
      });
      this.studentid = "";
    },
    handleCancel1() {
      this.visible = false;
      this.studentid = "";
    },
    handleBeforeOk1(done) {
      window.setTimeout(() => {
        done();
      }, 3000);
    },

    handleClick3() {
      this.visible3 = true;
    },

    handleOk3() {
      let submitForm = {
        id: this.courseId,
        name: this.courseName,
        year: this.year,
        semester: this.semester,
        description: this.courseDescription,
        manager: localStorage.getItem("userId"),
      };
      if (submitForm.year == "暂未设置") submitForm.year = "";
      if (submitForm.semester == "暂未设置") submitForm.semester = "";
      // console.log(submitForm);
      fetch(this.$URL + "/course/update", {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          satoken: localStorage.getItem("token"),
        },
        body: JSON.stringify(submitForm),
      }).then((response) => {
        // console.log(response)
        let result = response.json();
        result.then((res) => {
          // console.log(res)
          if (res.code == 200) {
            this.$message.success("修改成功");
            this.$router.go(-1);
          }
        });
      });
    },
    handleCancel3() {
      this.visible3 = false;
    },
    handleBeforeOk3(done) {
      window.setTimeout(() => {
        done();
      }, 3000);
    },

    handleClick4() {
      this.visible4 = true;
    },

    handleOk4() {
      var courseId = this.courseId;
      // console.log(courseId);
      fetch(this.$URL + "/course/remove?courseId=" + courseId, {
        method: "DELETE",
        headers: { satoken: localStorage.getItem("token") },
      }).then((response) => {
        // console.log(response);
        let result = response.json();
        result.then((res) => {
          if (res.code == 200) {
            this.$message.success("删除成功");
            this.$router.go(-1);
          }
        });
      });
    },
    handleCancel4() {
      this.visible4 = false;
    },
    handleBeforeOk4(done) {
      window.setTimeout(() => {
        done();
      }, 3000);
    },

    modifycourse() {
      let submitForm = {
        id: this.courseId,
        name: this.courseName,
        year: this.year,
        semester: this.semester,
        description: this.courseDescription,
        manager: localStorage.getItem("userId"),
      };
      if (submitForm.year == "暂未设置") submitForm.year = "";
      if (submitForm.semester == "暂未设置") submitForm.semester = "";
      // console.log(submitForm);
      fetch(this.$URL + "/course/update", {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          satoken: localStorage.getItem("token"),
        },
        body: JSON.stringify(submitForm),
      }).then((response) => {
        // console.log(response)
        let result = response.json();
        result.then((res) => {
          // console.log(res)
          if (res.code == 200) {
            this.$message.success("修改成功");
            this.$router.go(-1);
          }
        });
      });
    },
    DropCourse(student) {
      // console.log(this.courseId + " " + student.id);
      fetch(
        this.$URL +
          "/takes/remove?courseId=" +
          this.courseId +
          "&studentId=" +
          student.id,
        {
          method: "DELETE",
          headers: { satoken: localStorage.getItem("token") },
        }
      ).then((response) => {
        // console.log(response);
        let result = response.json();
        result.then((res) => {
          if (res.code == 200) {
            this.$message.success("退课成功");
            this.$router.replace({ path: "/refresh" });
          }
        });
      });
    },

    DropmanageCourse(teacher) {
      // console.log(this.course_manage + " " + localStorage.getItem("userId"));
      if (localStorage.getItem("userId") != this.course_manage) {
        this.$message.success("您不是责任教师，无法执行该操作");
      } else {
        if (teacher.id == this.id) {
          this.$message.success("无法取消责任教师");
          //this.$router.go(-1)
        } else {
          // console.log(this.courseId, teacher.id);
          fetch(
            this.$URL +
              "/manages/delete?courseId=" +
              this.courseId +
              "&teacherId=" +
              teacher.id,
            {
              method: "DELETE",
              headers: { satoken: localStorage.getItem("token") },
            }
          ).then((response) => {
            // console.log(response);
            let result = response.json();
            result.then((res) => {
              if (res.code == 200) {
                this.$message.success("取消教师授课成功");
                this.$router.replace({ path: "/refresh" });
              }
            });
          });
        }
      }
    },
  },
};
</script>

<style>
.course-title {
  /* text-align: left; */
  font-size: 25px;
  font-weight: bold;
  color: #656565;
  margin-bottom: 15px;
}

.course-description {
  font-size: 25px;
  font-weight: bold;
  color: #656565;
  margin-bottom: 15px;
}

.course-year {
  font-size: 25px;
  font-weight: bold;
  color: #656565;
  margin-bottom: 15px;
}
.course-semester {
  font-size: 25px;
  font-weight: bold;
  color: #656565;
  margin-bottom: 15px;
}
</style>