<template>
<div>
  <el-header>
    Header
  </el-header>
  <el-container>
    <el-aside>

    </el-aside>
  </el-container>
</div>
</template>

<script>
// import { defineComponent, ref } from 'vue'

// export default defineComponent({
//   setup() {
//     const item = {
//       date: '2016-05-02',
//       name: 'Tom',
//       address: 'No. 189, Grove St, Los Angeles',
//     }

//     const tableData = ref(Array(20).fill(item))

//     return {
//       tableData,
//     }
//   },
// })
</script>

<style>
html, body {
  margin: 0
}

.el-header {
  background-color: #b3c0d1;
  color: var(--el-text-color-primary);
  line-height: 60px;
}

.el-aside {
  color: var(--el-text-color-primary);
}
</style>