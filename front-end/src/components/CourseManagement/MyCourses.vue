<template>
  <div>
      <div style="margin-bottom: 10px">
        <va-breadcrumbs separator=">">
            <va-breadcrumbs-item label="课程管理" disabled />
            <va-breadcrumbs-item label="我的课程" to="/home/mycourses" />
        </va-breadcrumbs>
      </div>
    <va-card>
      <va-card-title style="font-size: 20px">我的课程</va-card-title>
        <va-card-content>
            暂无课程
        </va-card-content>
    </va-card>
  </div>
</template>

<script>
export default {

}
</script>

<style>

</style>