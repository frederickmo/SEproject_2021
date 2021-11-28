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
            <va-accordion v-model="value" style="width: 400px;">
    <va-collapse
      v-for="(collapse, index) in collapses"
      :key="index"
      :header="collapse.title"
    >
      <va-button  @click="this.$router.push({name: 'Exam'})">
        {{ collapse.content }}
      </va-button>
    </va-collapse>
  </va-accordion>
        </va-card-content>
    </va-card>
  </div>
  
</template>

<script>
export default {
  data () {
    return {
      value: [false, false, false],
      collapses: [
        { title: '考试1', content: '参加考试' },
        { title: '考试2', content: '参加考试' },
        { title: '考试3', content: '参加考试' },
      ],
    }
  },
}
</script>

<style>

</style>