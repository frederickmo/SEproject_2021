<template>
  <div>
    <div style="margin-bottom: 10px">
      <va-breadcrumbs separator=">">
        <va-breadcrumbs-item label="课程资源" disabled />
        <va-breadcrumbs-item label="课程资源" to="/home/courseresources" />
      </va-breadcrumbs>
    </div>
    <!-- <iframe v-show="showIframe" :src="getFileUrl()" /> -->
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
      <va-card-title style="font-size: 20px">
        课程资源
        <va-button
          v-if="this.identity == 3 || this.identity == 2"
          color="#aaa"
          style="color: #777"
          flat
          @click="uploadModalVisible = !uploadModalVisible"
          >上传</va-button
        >
      </va-card-title>
      <va-card-content style="text-align: left">
        <a-dropdown position="tr" trigger="hover" @select="handleSelectCourse">
          <a-button>选择课程</a-button>
          <template #content>
            <a-doption
              v-for="(course, index) in courses"
              :key="index"
              :value="index"
              >{{ course.name }}</a-doption
            >
          </template>
        </a-dropdown>
        <a-list
          size="small"
          :bordered="false"
          style="text-align: left; margin-top: 20px"
          v-if="this.resources.length"
        >
          <template #header>
            <div style="font-size: 18px; font-weight: bold">
              {{ courses[curCourseIndex].name }}课程资源
            </div>
          </template>
          <a-list-item v-for="(file, index) in resources" :key="index">
            <a :href="generateUrl(file.name)"
              ><icon-file style="margin-right: 5px" />{{ file.name }}</a
            >
          </a-list-item>
        </a-list>
        <div style="margin-top: 32px; margin-left: 5px" v-else>
          <div style="font-size: 18px; font-weight: bold">
            {{ courses[curCourseIndex].name }}课程资源
          </div>
          <div style="margin-top: 15px">暂无内容</div>
        </div>
      </va-card-content>
    </va-card>
    <a-modal v-model:visible="uploadModalVisible">
      <template #title> 上传文件 </template>
      <el-upload
        ref="upload"
        :action="getUploadUrl()"
        :headers="headers()"
        :data="additionalData()"
        :auto-upload="false"
        style="text-align: center"
        :on-success="handleOnSuccess"
        :on-error="handleOnError"
      >
        <template #trigger>
          <va-button gradient :rounded="false">选择文件</va-button>
        </template>
        <va-button
          style="margin-left: 20px"
          flat
          :rounded="false"
          @click="submitUpload"
          >上传</va-button
        >
      </el-upload>
    </a-modal>
  </div>
</template>

<script>
export default {
  data() {
    return {
      resources: [],
      r: 0,
      reports: [],
      test: "",
      identity: "",

      curCourseIndex: 0, // 当前选择的课程，这个要和select的结果绑定，因为要修改课程资源的a-list的标题。
      courses: [
        // 这个是写的临时列表，后面应该用相应接口查询学生的所有课程。
        { name: "计算机网络" },
        { name: "计算机组成原理" },
      ],

      currentIndex: 0,

      uploadModalVisible: false,
    };
  },
  mounted() {
    this.id = localStorage.getItem("userId");

    fetch(this.$URL + "/user/get?id=" + this.id, {
      method: "GET",
      headers: { satoken: localStorage.getItem("token") },
    })
      .then((res) => res.json())
      .then((res) => {
        // console.log(res);
        this.identity = res.identity;
        //console.log(res.identity)
        if (this.identity == 3) {
          fetch(
            this.$URL + "/manages/get/teacher/detail?teacherId=" + this.id,
            {
              method: "GET",
              headers: { satoken: localStorage.getItem("token") },
            }
          ).then((response) => {
            // console.log(response);
            let result = response.json();
            result.then((res) => {
            //   console.log(res);
              this.courses = res;
            });
          });
        } else {
          fetch(this.$URL + "/takes/get/student/detail?studentId=" + this.id, {
            method: "GET",
            headers: { satoken: localStorage.getItem("token") },
          }).then((response) => {
            // console.log(response);
            let result = response.json();
            result.then((res) => {
            //   console.log(res);
              this.courses = res;
            });
          });
        }
      });
  },
  methods: {
    headers() {
      return {
        satoken: localStorage.getItem("token"),
      };
    },
    generateUrl(fileName) {
      // console.log(this.$URL + "/file/download/courseResource/" + +this.courses[this.curCourseIndex].id+fileName)
      return (
        this.$URL +
        "/file/download/courseResource/" +
        +this.courses[this.curCourseIndex].id +
        "/" +
        fileName
      );
    },

    getUploadUrl() {
      return this.$URL + "/file/upload/redirect";
    },
    additionalData() {
    //   console.log("/courseResource/" + this.courses[this.curCourseIndex].id);
    //   console.log("65/courseResource/" + this.courses[this.curCourseIndex].id);
      fetch(
        this.$URL +
          "/file/getAll?path=/courseResource/" +
          this.courses[this.curCourseIndex].id,
        {
          method: "GET",
          headers: { satoken: localStorage.getItem("token") },
        }
      ).then((response) => {
        let result = response.json();
        result.then((res) => {
        //   console.log(res);
          this.resources = res;
        //   console.log(this.resources);
        });
      });
      return {
        location: "/courseResource/" + this.courses[this.curCourseIndex].id,
        //fileName: "123.txt"
      };
    },
    handleOnSuccess() {
      this.$notification.success("上传成功");
    },
    handleOnError() {
      this.$notification.error("上传失败");
    },
    submitUpload() {
      this.$refs.upload.submit();
    },

    handleSelectCourse(index) {
      // 这个index已经绑定了每个选项在courses里的index，到时候替换下面的a-list里面的内容。
    //   console.log(index);
      this.curCourseIndex = index;
    //   console.log(this.courses[this.curCourseIndex].id);
      // 选择课程之后切换相应课程的课程资源 => 使用接口查询
    },
  },
};
</script>

<style>
.va-card va-card-content va-list-label {
  text-align: left;
}
</style>
