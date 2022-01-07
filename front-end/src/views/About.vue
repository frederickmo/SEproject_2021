<template>

<div>
  <va-navbar>
    <template v-slot:left>
            <div
        @click="minimized=!minimized"
      >
        <va-icon :name="iconOnChange()" />
      </div>

    </template>
    <template v-slot:center>
      <div class="left">
        中文
        <va-navbar-item>HEADER Header</va-navbar-item>
      </div>
    </template>
    <template v-slot:right>
      <va-button flat text-color="#222222" @click="this.$router.push({name: 'Home'})">to home</va-button>
    </template>
  </va-navbar>
  <el-header>
    Header
  </el-header>


  
  <el-container>
    <!-- <el-aside>
      <el-menu :default-openeds="['1']">
        <el-sub-menu index="1">
          <template #title>
            Navigator One
          </template>
          <el-menu-item-group>
            <template #title>Group 1</template>
            <el-menu-item index="1-1">Option 1</el-menu-item>
            <el-menu-item index="1-2">Option 2</el-menu-item>
          </el-menu-item-group>
        </el-sub-menu>

      </el-menu>
    </el-aside> -->

    <!-- <va-sidebar
    :minimized="minimized"
    textColor="dark"
    width="11rem"
    minimizedWidth="64px"
    >
      <va-sidebar-item>
        <va-sidebar-item-content>
          <va-icon name="format_indent_decrease" />
          <va-sidebar-item-title v-if="!minimized">
            nAVIGATOR中文加长加长
          </va-sidebar-item-title>
        </va-sidebar-item-content>
      </va-sidebar-item>
      <div
        class="demo-controls px-2 py-2"
        style="background: var(--va-background); width: 100%;"
        @click="minimized=!minimized"
      >
        <va-icon :name="iconOnChange()" />
      </div>

    </va-sidebar> -->

<!-- 上面是单独的va-sidebar,下面用va-accordion试试 -->


  <va-sidebar
:minimized="minimized"
textColor="dark"
width="12rem"
minimizedWidth="0">

  <va-accordion
  v-model="value"
  multiply>
    <va-collapse
    key="0"
    :header="minimized? '':'第一段标题'"
    icon="home">
      <va-sidebar-item>
        <va-sidebar-item-content>
          <va-icon name="format_indent_decrease" />
          <va-sidebar-item-title>
            nAVIGATOR中文加长加长
          </va-sidebar-item-title>
        </va-sidebar-item-content>
      </va-sidebar-item>
    </va-collapse>
    <va-collapse
    key="1"
    :header="minimized? '':'第二段标题'"
    icon="home">
      <va-sidebar-item>
        <va-sidebar-item-content>一段内容</va-sidebar-item-content>
      </va-sidebar-item>
    </va-collapse>
    <va-collapse
    key="2"
    :header="minimized? '':'第三段标题'"
    icon="home">
      <va-sidebar-item>
        <va-sidebar-item-content>一段内容</va-sidebar-item-content>
      </va-sidebar-item>
    </va-collapse>
  </va-accordion>
</va-sidebar>


    <el-container>
      <el-main>
        Main
      </el-main>
    </el-container>
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

  export default {
    data () {
      return {
        items: [
          {title: 'Navigation 1', icon: 'room'},
          {title: 'Navigation 2', icon: 'room'},
          {title: 'Navigation 3', icon: 'loop'}
        ],
        value: [false, false, false],
        minimized: false
      }
    },
    methods: {
      checkboxLabelOnChange () {
        if (this.minimized) {
          return ""
        } else {
          return "Minimized"
        }
      },
      iconOnChange () {
        if (this.minimized) {
          return "arrow_right"
        } else {
          return "arrow_left"
        }
      }
    }
  }
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

.va-sidebar {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  /* color: rgb(248,48,48); */
  font-weight: 550;
}

.va-accordion {
  text-align: left;
}

.va-sidebar-item-title {
  height: 24px;
  line-height: 24px
}
</style>