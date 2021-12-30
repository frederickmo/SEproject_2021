<template>
  <div>
    <div style="margin-bottom: 10px">
      <va-breadcrumbs separator=">">
        <va-breadcrumbs-item label="课程管理" disabled />
        <va-breadcrumbs-item label="我的课程" to="/home/mycourses" />
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
      <va-card-title style="font-size: 20px">我的课程</va-card-title>
      <va-card-content>
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
              <div style="line-height: 36px; width: 90%; font-size: 18px">
                {{ course.name }}
              </div>
              <div>
                <va-button
                  @click="switchToCourse(course.id, index)"
                  color="#e0e5df"
                  style="color: rgb(40, 40, 40)"
                  >点击进入</va-button
                >
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
    };
  },
  mounted() {
    this.id = localStorage.getItem("userId");
    // console.log("id: ", this.id);

    fetch(this.$URL + "/takes/get/student/detail?studentId=" + this.id, {
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
  },
  methods: {
    switchToCourse(courseId, index) {
      // console.log("调用switchToCourse时，courseId传过去了吗？ courseId的值是：" + id)
      // console.log("调用switchToCourse时，index的值是：" + index)
      // console.log(this.courses[index])
      localStorage.setItem("courseId", courseId);
      this.$router.push({
        name: "CoursePage",
        params: {
          courseId: this.courses[index].id,
          name: this.courses[index].name,
          description: this.courses[index].description,
          year: this.courses[index].year,
          semester: this.courses[index].semester,
        },
      });
    },
  },
};
</script>

<style></style>
