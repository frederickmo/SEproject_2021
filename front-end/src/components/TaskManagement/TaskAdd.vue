<template>
  <va-card gradient color="#e0e5df">
    <va-card-content style="text-align: left">
      <div style="display: flex; line-height: 200%; margin-top: 10px">
        <div style="width: 18%; font-weight: bold; text-align: center">
          实验项目名称：
        </div>
        <div style="width: 20%">
          <a-input v-model="this.Name" />
        </div>
        <div style="width: 18%; font-weight: bold; text-align: center">
          类型：
        </div>
        <div style="width: 30%">
          <a-input v-model="this.type" />
        </div>
        <!-- <div style="width: 18%; font-weight: bold; text-align: center">
          实验项目Id：
        </div>
        <div style="width: 30%">
          <a-input v-model="this.courseId" />
        </div> -->
      </div>
      <div style="display: flex; line-height: 200%; margin-top: 10px">
        <div style="width: 18%; font-weight: bold; text-align: center">
          课程Id：
        </div>
        <div style="width: 20%">
          <a-input v-model="this.Id" />
        </div>
        <div style="width: 18%; font-weight: bold; text-align: center">
          截止日期：
        </div>
        <div style="width: 20%">
          <a-date-picker v-model="this.deadline" style="width: 200px" />
        </div>
        
      </div>
      <div style="display: flex; line-height: 200%; margin-top: 10px">
        <div style="width: 18%; font-weight: bold; text-align: center">
          项目描述：
        </div>
        <div style="width: 30%">
          <a-input v-model="this.Description" />
        </div>
      </div>
      <div style="text-align: center">
        <a-button
          type="outline"
          @click="addtask()"
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
      Id: "",
      courseId: "",
      Name: "",
      Description: "",
      type: "",
      deadline: "",

      course: [],
    };
  },
  mounted() {
    this.courseId = localStorage.getItem("courseId");
  },
  methods: {
    addtask() {
        console.log("123")
      let submitForm = {
        id: this.Id,
        courseId:this.courseId,
        name: this.Name,
        type: this.type,
        deadline: this.deadline,
        description: this.Description,
      };
      console.log(submitForm)
    //   console.log(submitForm);
      fetch(this.$URL + "/task/add", {
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
           console.log(res)
          if (res.code == 200) {
            this.$notification.success("添加成功");
            this.$router.go(-1);
            // fetch(
            //   this.$URL +
            //     "/manages/add?courseId=" +
            //     this.courseId +
            //     "&teacherId=" +
            //     this.id,
            //   {
            //     method: "POST",
            //     headers: { satoken: localStorage.getItem("token") },
            //   }
            // ).then((response) => {
            // //   console.log(response);
            //   let result = response.json();
            //   result.then((res) => {
            //     // console.log(res);
            //     this.manage=res.manager
            //   });
            // });
          }
          else{
             console.log(res)
             // this.$message.error();
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
