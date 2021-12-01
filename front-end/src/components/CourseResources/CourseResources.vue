<template>
<div>
    <div style="margin-bottom: 10px">
        <va-breadcrumbs separator=">">
            <va-breadcrumbs-item label="课程资源" disabled />
            <va-breadcrumbs-item label="课程资源" to="/home/courseresources" />
        </va-breadcrumbs>
    </div>
  <va-card>
      <va-card-title style="font-size: 20px">课程资源</va-card-title>
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
          <va-list>
              <va-list-label style="text-align: left; font-size: 16px">实验报告</va-list-label>
              <va-list-item
              v-for="(file, index) in reports"
              :key="index">
                {{file.name}}
              </va-list-item>
          </va-list>
      </va-card-content>
  </va-card>

</div>
</template>

<script>
export default {
    data () {
        return {
            resources: [],
            r: 0,
            reports: [],
            test:""
        }
    },
    mounted () {
        fetch(this.$URL + "/file/getAllFiles", {
            method: "GET"
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
            return this.$URL + "/file/downloadFile/" + fileName;
        }
    }
}
</script>

<style>

.va-card va-card-content va-list-label {
    text-align: left;
}
</style>