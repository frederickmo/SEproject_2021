<template>
  <div>
      <div style="margin-bottom: 10px">
        <va-breadcrumbs separator=">">
            <va-breadcrumbs-item label="考试管理" disabled />
            <va-breadcrumbs-item label="我的考试" to="/home/myexams" />
        </va-breadcrumbs>
      </div>
    <va-card>
      <va-card-title style="font-size: 20px">我的考试</va-card-title>
        <va-card-content>
            暂无考试
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