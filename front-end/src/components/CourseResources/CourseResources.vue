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
      <va-card-title style="font-size: 20px">
          课程资源 
          <va-button color="#aaa" style="color: #777" flat @click="uploadModalVisible=!uploadModalVisible">上传</va-button>
      </va-card-title>
      <va-card-content>
          <va-list>
              <va-list-label style="text-align: left; font-size: 16px">课程资料</va-list-label>
              <va-list-item
              v-for="(file,index) in resources"
              :key="index"
              >
                <a :href="generateUrl(file.name)">{{file.name}}</a>
              </va-list-item>
          </va-list>
          <!-- <va-list>
              <va-list-label style="text-align: left; font-size: 16px">实验报告</va-list-label>
              <va-list-item
              v-for="(file, index) in reports"
              :key="index">
                {{file.name}}
              </va-list-item>
          </va-list> -->
      </va-card-content>
  </va-card>
  <a-modal
  v-model:visible="uploadModalVisible"
  >
  <template #title>
      上传文件
  </template>
  <el-upload
    ref="upload"
    :action="getUploadUrl()"
    :auto-upload="false"
    style="text-align: center"
  >
    <template #trigger>
      <va-button gradient :rounded="false">选择文件</va-button>
    </template>
    <va-button style="margin-left: 20px" flat :rounded="false" @click="submitUpload">上传</va-button>
    <!-- <template #tip>
      <div class="el-upload__tip">
        jpg/png files with a size less than 500kb
      </div>
    </template> -->
  </el-upload> 
</a-modal>
</div>
</template>

<script>
export default {
    data () {
        return {
            resources: [],
            r: 0,
            reports: [],
            test:"",

            currentIndex: 0,

            uploadModalVisible: false,
        }
    },
    mounted () {
        fetch(this.$URL + "/file/getAll?path=/courseResource", {
            method: "GET",
            headers: { "satoken": localStorage.getItem("token") }
        }).then((response) => {
            let result = response.json()
            result.then((res) => {
                console.log(res)
                this.resources=res
                console.log(this.resources)
            })
        })
    },
    methods: {
        generateUrl(fileName) {
            return this.$URL + "/file/download/courseResource/" + fileName;
        },

        getUploadUrl() {
            return this.$URL + "/file/upload/redirect"
        },
        additionalData() {
            return {
                location: "/courseResource"
            }
        },
        submitUpload() {
            this.$refs.upload.submit()
        },
    }
}
</script>

<style>

.va-card va-card-content va-list-label {
    text-align: left;
}
</style>