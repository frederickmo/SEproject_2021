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
              v-for="(file, index) in resources"
              :key="index"
              to="/home">
                {{file.name}}
              </va-list-item>
          </va-list>
          <va-list>
              <va-list-label style="text-align: left; font-size: 16px">实验报告</va-list-label>
              <va-list-item
              v-for="(file, index) in reports"
              :key="index"
              to="/home">
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
            resources: [
                {name: '实验须知.pdf'},
                {name: '教学大纲.pdf'},
                {name: '实验参考手册.pdf'},
            ],
            reports: [
                {name: '计算机组成原理实验一.docx'},
                {name: '计算机网络实验一.docx'},
                {name: '计算机系统结构实验三.docx'},
            ]
        }
    }
}
</script>

<style>

.va-card va-card-content va-list-label {
    text-align: left;
}
</style>