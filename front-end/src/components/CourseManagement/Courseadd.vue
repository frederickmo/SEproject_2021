<template>
  <va-card gradient color="#e0e5df">
    <va-card-content style="text-align: left">
      <!-- <div class="course-title">课程名字：<va-input
      class="mb-4"
      v-model="this.courseName"
      placeholder="请输入课程名字"
      outline
    />

</div>
<div class="course-id">课程id：<va-input
      class="mb-4"
      v-model="this.courseId"
      placeholder="请输入课程id"
      outline
    /></div>
          <div class="course-description">课程描述：<va-input
      class="mb-4"
      v-model="this.courseDescription"
      placeholder="请输入课程描述"
      outline
    /></div>
            <div class="course-year">开设年份：
                <a-year-picker v-model="this.year" style="width: 200px;" />
            </div>
            <div class="course-semester">学年：<va-input
      class="mb-4"
      v-model="this.semester"
      placeholder="请输入学年"
      outline
    /></div>
            <div style="height: 20px" /> -->
      <div style="display: flex; line-height: 200%; margin-top: 10px">
        <div style="width: 18%; font-weight: bold; text-align: center">
          课程名称：
        </div>
        <div style="width: 20%">
          <a-input v-model="this.courseName" />
        </div>
        <div style="width: 18%; font-weight: bold; text-align: center">
          课程Id：
        </div>
        <div style="width: 30%">
          <a-input v-model="this.courseId" />
        </div>
      </div>
      <div style="display: flex; line-height: 200%; margin-top: 10px">
        <div style="width: 18%; font-weight: bold; text-align: center">
          开设年份：
        </div>
        <div style="width: 20%">
          <a-year-picker v-model="this.year" style="width: 200px" />
        </div>
        <div style="width: 18%; font-weight: bold; text-align: center">
          学年：
        </div>
        <div style="width: 30%">
          <a-input v-model="this.semester" />
        </div>
      </div>
      <div style="display: flex; line-height: 200%; margin-top: 10px">
        <div style="width: 18%; font-weight: bold; text-align: center">
          课程描述：
        </div>
        <div style="width: 30%">
          <a-input v-model="this.courseDescription" />
        </div>
      </div>
      <div style="text-align: center">
        <a-button
          type="outline"
          @click="addcourse()"
          color="#e0e5df"
          style="color: rgb(40, 40, 40)"
          >确认添加</a-button
        >
      </div>
    </va-card-content>
  </va-card>
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

      course: [],
    };
  },
  mounted() {
    this.id = localStorage.getItem("userId");
  },
  methods: {
    addcourse() {
      let submitForm = {
        id: this.courseId,
        name: this.courseName,
        year: this.year,
        semester: this.semester,
        description: this.courseDescription,
        manager: localStorage.getItem("userId"),
      };
    //   console.log(submitForm);
      fetch(this.$URL + "/course/add", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          satoken: localStorage.getItem("token"),
        },
        body: JSON.stringify(submitForm),
      }).then((response) => {
        // console.log(response);
        let result = response.json();
        result.then((res) => {
          // console.log(res)
          if (res.code == 200) {
            this.$notification.success("添加成功");
            this.$router.go(-1);
            fetch(
              this.$URL +
                "/manages/add?courseId=" +
                this.courseId +
                "&teacherId=" +
                this.id,
              {
                method: "POST",
                headers: { satoken: localStorage.getItem("token") },
              }
            ).then((response) => {
            //   console.log(response);
              let result = response.json();
              result.then((res) => {
                // console.log(res);
                this.manage=res.manager
              });
            });
          }
        });
      });
    //   console.log(this.courseId + " " + localStorage.getItem("userId"));
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
.course-id {
  font-size: 25px;
  font-weight: bold;
  color: #656565;
  margin-bottom: 15px;
}
</style>
