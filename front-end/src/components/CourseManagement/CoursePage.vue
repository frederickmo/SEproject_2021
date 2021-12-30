<template>
  <div>
    <div style="margin-bottom: 10px">
      <va-breadcrumbs separator=">">
        <va-breadcrumbs-item label="课程管理" disabled />
        <va-breadcrumbs-item label="我的课程" to="/home/mycourses" />
        <va-breadcrumbs-item label="所有实验项目" disabled />
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
      <va-card-content style="text-align: left">
        <div class="course-title">{{ this.courseName }}</div>
        <div class="course-description">{{ this.courseDescription }}</div>

        <el-table
          :data="
            totalTasks.filter(
              (data) =>
                !search | data.name.toLowerCase().includes(search.toLowerCase())
            )
          "
        >
          <el-table-column label="项目ID" prop="id" />
          <el-table-column label="项目名" prop="name" />
          <el-table-column label="类型" prop="type" />
          <el-table-column label="截止日期" prop="deadline" />
          <el-table-column align="right">
            <template #header>
              <el-input
                v-model="searchSimple"
                size="mini"
                placeholder="请输入"
              />
            </template>
            <template #default="scope">
              <div>
                <a-button @click="switchToTask(scope.row)">前往</a-button>
              </div>
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
      id: "",
      courseId: "",
      courseName: "",
      courseDescription: "",
      year: "",
      semester: "",

      courseInfo: [],

      courseDeadlines: [],

      tasks: [],

      simpleTasks: [],
      complexTasks: [],

      totalTasks: [],

      search: "",
    };
  },
  mounted() {
    this.id = localStorage.getItem("userId");
    this.courseId = localStorage.getItem("courseId");
    // console.log("courseId", this.courseId);
    if (this.$route.params.courseId == undefined) {
      // console.log("从子路由跳到父路由，需要重新查询课程数据");
      fetch(this.$URL + "/course/get?id=" + this.courseId, {
        method: "GET",
        headers: { satoken: localStorage.getItem("token") },
      }).then((response) => {
        // console.log(response)
        let result = response.json();
        result.then((res) => {
          // console.log(res)
          this.courseName = res.name;
          this.courseDescription = res.description;
          this.year = res.year;
          this.semester = res.semester;
        });
      });
    } else {
      // console.log("从父路由跳到子路由，直接把查询结果传参即可，无需重新查询");
      this.courseName = this.$route.params.name;
      this.courseDescription = this.$route.params.description;
      this.year = this.$route.params.year;
      this.semester = this.$route.params.semester;
      // console.log("从上个页面传来的courseName: ", this.courseName);
    }

    fetch(this.$URL + "/task/get/simple?courseId=" + this.courseId, {
      method: "GET",
      headers: { satoken: localStorage.getItem("token") },
    })
      .then((response) => {
        // console.log(response)
        let result = response.json();
        result.then((res) => {
          // console.log(res);
          this.simpleTasks = res;
          for (let i = 0; i < this.simpleTasks.length; ++i) {
            this.simpleTasks[i].type =
              this.simpleTasks[i].type == "0" ? "小型" : "大型";
            if (this.simpleTasks[i].deadline == undefined) {
              this.simpleTasks[i].deadline = "暂无";
            } else {
              this.simpleTasks[i].deadline += this.isOverdue(
                this.simpleTasks[i].deadline
              )
                ? "（已截止）"
                : "";
            }
          }
          this.totalTasks = this.totalTasks.concat(this.simpleTasks);
          // console.log(this.totalTasks);
        });
      })
      .then(async () => {
        fetch(this.$URL + "/task/get/complex?courseId=" + this.courseId, {
          method: "GET",
          headers: { satoken: localStorage.getItem("token") },
        }).then((response) => {
          let result = response.json();
          result.then((res) => {
            // console.log(res);
            this.complexTasks = res;
            for (let i = 0; i < this.complexTasks.length; ++i) {
              this.complexTasks[i].type =
                this.complexTasks[i].type == "0" ? "小型" : "大型";
              this.complexTasks[i].deadline += this.isOverdue(
                this.complexTasks[i].deadline
              )
                ? "（已截止）"
                : "";
            }
            this.totalTasks = this.totalTasks.concat(this.complexTasks);
            // console.log(this.totalTasks);
          });
        });
      });
  },
  methods: {
    switchToTaskSimple(index) {
      // console.log("调用switchToCourse时，courseId传过去了吗？ courseId的值是：" + id)
      // console.log("调用switchToCourse时，index的值是：" + index)
      // console.log(this.tasks[index])
      // console.log("taskName：" + this.simpleTasks[index].name);
      localStorage.setItem("curTaskId", this.simpleTasks[index].id);
      this.$router.push({
        name: "OnlineTask_InCoursePage",
        params: {
          taskId: this.simpleTasks[index].id,
          taskName: this.simpleTasks[index].name,
          courseId: this.simpleTasks[index].courseId,
          description: this.simpleTasks[index].description,
          type: this.simpleTasks[index].type,
          url: this.simpleTasks[index].url,
          deadline: this.simpleTasks[index].deadline,
        },
      });
    },
    switchToTaskComplex(index) {
      // console.log("调用switchToCourse时，courseId传过去了吗？ courseId的值是：" + id)
      // console.log("调用switchToCourse时，index的值是：" + index)
      // console.log(this.tasks[index])
      localStorage.setItem("curTaskId", this.complexTasks[index].id);
      this.$router.push({
        name: "ComplexTask_InCoursePage",
        params: {
          taskId: this.complexTasks[index].id,
          taskName: this.complexTasks[index].name,
          courseId: this.complexTasks[index].courseId,
          description: this.complexTasks[index].description,
          type: this.complexTasks[index].type,
          url: this.complexTasks[index].url,
          deadline: this.complexTasks[index].deadline,
        },
      });
    },
    isOverdue(date) {
      /**
       * now的写法用当前时间转日期再转时间来进行比较的原因是
       * 如果直接用当前时间和设定的ddl(只精确到日期)来比较的话，
       * ddl转时间是当天的0点整，但是设定的ddl本身意义应该是到当天结束为止。
       * 所以把当前时间转换为当天0点整再进行比较就能等效上述效果。
       */
      let now = new Date(new Date().toLocaleDateString());
      let deadline = new Date(date);
      if (date == null || date == "" || date == undefined) {
        return false;
      } else if (now <= deadline) {
        return false;
      } else {
        return true;
      }
    },
    switchToTask(row) {
      // console.log(row);
      localStorage.setItem("curTaskId", row.id);
      this.$router.push({
        name:
          row.type == "大型"
            ? "ComplexTask_InCoursePage"
            : "OnlineTask_InCoursePage",
        params: {
          taskId: row.id,
        },
      });
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
  font-size: 15px;
  margin-bottom: 20px;
}
</style>
