<template>
  <div>
    <div style="margin-bottom: 10px">
      <va-breadcrumbs separator=">">
        <va-breadcrumbs-item label="课程管理" disabled />
        <va-breadcrumbs-item label="课程管理" to="/home/coursemanagement" />
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
      <va-card-title style="font-size: 20px">课程管理</va-card-title>
      <va-card-content>
        <div style="text-align: left">
          <a-button
            v-if="this.identity == 3"
            @click="add()"
            color="#e0e5df"
            style="color: rgb(40, 40, 40)"
            >新增课程</a-button
          >
        </div>
        <div style="height: 20px" />
        <h2 style="text-align: left">我管理的课程</h2>
        <va-card
          v-for="(course, index) in courses"
          :key="index"
          color="#b5c4b1"
          gradient
          style="margin-bottom: 10px"
        >
          <va-card-content style="rgb(60, 60, 60); font-weight: bold">
            <div style="display: flex">
              <!-- va-card高度: 76px -->
              <!-- va-card高度: 36px -->
              <div style="line-height: 36px; width: 89%; font-size: 18px">
                {{ course.name }}
              </div>
              <div>
                <va-button
                  @click="modifyCourse(course.id, index, 0)"
                  color="#e0e5df"
                  style="color: rgb(40, 40, 40)"
                  >点击进入</va-button
                >
                <!-- </div>
                  <div> -->
                <!-- <div style="height: 2px" />
                  <va-button @click="deleteCourse(course.id)" color="#e0e5df" style="color: rgb(40,40,40)">点击删除</va-button> -->
              </div>
            </div>
          </va-card-content>
        </va-card>
        <div style="height: 20px" />
        <h2 style="text-align: left">我任课的课程</h2>
        <va-card
          v-for="(teach, index) in teach"
          :key="index"
          color="#ac9b91"
          gradient
          style="margin-bottom: 10px"
        >
          <va-card-content style="rgb(60, 60, 60); font-weight: bold">
            <div style="display: flex">
              <!-- va-card高度: 76px -->
              <!-- va-card高度: 36px -->
              <div style="line-height: 36px; width: 90%; font-size: 18px">
                {{ teach.name }}
              </div>
              <div>
                <va-button
                  @click="modifyCourse(teach.id, index, 1)"
                  color="#e0e5df"
                  style="color: rgb(40, 40, 40)"
                  >点击进入</va-button
                >
                <!-- </div>
                  <div> -->
                <div style="height: 2px" />
                <!-- <va-button @click="deleteCourse(teach.id)" color="#e0e5df" style="color: rgb(40,40,40)">点击删除</va-button> -->
              </div>
            </div>
          </va-card-content>
        </va-card>
      </va-card-content>
    </va-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      id: "",
      name: "",
      email: "",
      password: "",
      identity: 0,
      activated: 0,

      courses: [],
      teach: [],
    };
  },
  mounted() {
    this.id = localStorage.getItem("userId");
    // console.log("id: ", this.id);
    fetch(this.$URL + "/user/get?id=" + this.id, {
      method: "GET",
      headers: { satoken: localStorage.getItem("token") },
    })
      .then((res) => res.json())
      .then((res) => {
        this.identity = res.identity;
      });
    fetch(this.$URL + "/manages/get/course/managerId?managerId=" + this.id, {
      method: "GET",
      headers: { satoken: localStorage.getItem("token") },
    }).then((response) => {
      // console.log(response);
      let result = response.json();
      result.then((res) => {
        // console.log(res);
        this.courses = res;
      });
    });

    fetch(this.$URL + "/manages/get/teachingCourse?teacherId=" + this.id, {
      method: "GET",
      headers: { satoken: localStorage.getItem("token") },
    }).then((response) => {
      // console.log(response);
      let result = response.json();
      result.then((res) => {
        // console.log(res);
        this.teach = res;
      });
    });
  },
  methods: {
    deleteCourse(courseId) {
      // console.log(courseId);
      fetch(this.$URL + "/course/remove?courseId=" + courseId, {
        method: "DELETE",
        headers: { satoken: localStorage.getItem("token") },
      }).then((response) => {
        // console.log(response);
        let result = response.json();
        result.then((res) => {
          if (res.status == 200) {
            this.$notification.success("删除成功");
          }

          fetch(
            this.$URL + "/manages/get/course/managerId?managerId=" + this.id,
            {
              method: "GET",
              headers: { satoken: localStorage.getItem("token") },
            }
          ).then((response) => {
            // console.log(response);
            let result = response.json();
            result.then((res) => {
              // console.log(res);
              this.courses = res;
            });
          });
        });
      });
    },
    modifyCourse(courseId, index, o) {
      localStorage.setItem("courseId", courseId)
      this.$router.push({
        name: "CourseModify",
        params: {
          courseId: this.courses[index].id,
          name: this.courses[index].name,
          description: this.courses[index].description,
          year: this.courses[index].year,
          semester: this.courses[index].semester,
          on: o,
        },
      });
    },
    add() {
      this.$router.push({
        name: "Courseadd",
      });
    },
  },
};
</script>

<style>
.modi {
  height: 20px;
  width: 100%;
}
</style>
