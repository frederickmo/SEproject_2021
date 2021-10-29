<template>
  <!-- <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <HelloWorld msg="Welcome to Your Vue.js App"/>
    <va-button @click="toLogin">to-login</va-button>
    <a-button shape="round" @click="toSignup">to-signup</a-button>
  </div> -->
  <!-- <div> -->
<!-- <el-header>
  element plus header
</el-header> -->
      <a-layout-header style="padding-left: 20px;">
        111
      </a-layout-header >

<a-layout class="layout-demo">
  
    <a-layout-sider collapsible breakpoint="xl">
      <div class="logo" />
      <a-menu
        :defaultOpenKeys="['1']"
        :defaultSelectedKeys="['0_3']"
        :style="{ width: '100%' }"
        @menuItemClick="onClickMenuItem"
      >
        <a-menu-item key="0_1" disabled>
          <icon-link />
          Menu 1
        </a-menu-item>
        <a-menu-item key="0_2">
          <IconCalendar />
          Menu 2
        </a-menu-item>
        <a-menu-item key="0_3">
          <IconCalendar />
          Menu 3
        </a-menu-item>
        <a-sub-menu key="1">
          <template #title>
            <span><IconCalendar />Navigation 1</span>
          </template>
          <a-menu-item key="1_1">Menu 1</a-menu-item>
          <a-menu-item key="1_2">Menu 2</a-menu-item>
          <a-sub-menu key="2" title="Navigation 2">
            <a-menu-item key="2_1">Menu 1</a-menu-item>
            <a-menu-item key="2_2">Menu 2</a-menu-item>
          </a-sub-menu>
          <a-sub-menu key="3" title="Navigation 3">
            <a-menu-item key="3_1">Menu 1</a-menu-item>
            <a-menu-item key="3_2">Menu 2</a-menu-item>
            <a-menu-item key="3_3">Menu 3</a-menu-item>
          </a-sub-menu>
        </a-sub-menu>
        <a-sub-menu key="4">
          <template #title>
            <span><IconCalendar />Navigation 4</span>
          </template>
          <a-menu-item key="4_1">Menu 1</a-menu-item>
          <a-menu-item key="4_2">Menu 2</a-menu-item>
          <a-menu-item key="4_3">Menu 3</a-menu-item>
        </a-sub-menu>
        <a-sub-menu key="5">
          <template #title>
            <span><IconCalendar />Exit</span>
          </template>
          <a-menu-item>
            <router-link :to="{name: 'Auth', params: {tabValue: 0}}">to log in</router-link>
          </a-menu-item>
          <a-menu-item>
            <router-link :to="{name: 'Auth', params: {tabValue: 1}}">to sign up</router-link>
          </a-menu-item>
          <a-menu-item>
            <router-link to="/about">to about</router-link>
          </a-menu-item>
        </a-sub-menu>
      </a-menu>
      <!-- trigger -->
      <template #trigger="{ collapsed }">
        <!-- <icon-forward v-if="collapsed"/> -->
        <icon-to-right v-if="collapsed"/>
        <icon-to-left v-else />
        <!-- <icon-backward v-else /> -->
      </template>
    </a-layout-sider>
    <a-layout>
      <a-layout style="padding: 0 24px;">
        <a-breadcrumb :style="{ margin: '16px 0' }">
          <a-breadcrumb-item>Home</a-breadcrumb-item>
          <a-breadcrumb-item>List</a-breadcrumb-item>
          <a-breadcrumb-item>App</a-breadcrumb-item>
        </a-breadcrumb>
        <a-layout-content>
          啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>啊？<br>
          </a-layout-content>
      </a-layout>
    </a-layout>
  </a-layout>
        <!-- <a-layout-footer>Footer</a-layout-footer> -->
  <!-- </div> -->
</template>

<script>
// @ is an alias to /src
// import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'Home',
  components: {
    // HelloWorld
  },
  data () {
    return {

    }
  },
  methods: {
    toLogin () {
      this.$router.push({
        name: 'Auth',
        params: {
          tabValue: 0
        }
      })
      console.log(this.$route)
    },
    toSignup () {
      this.$router.push({
        name: 'Auth',
        params: {
          tabValue: 1
        }
      })
      console.log(this.$route)
    },
    onClickMenuItem() {
      this.$vaToast.init({
        message: 'ありがとうございます',
        color: 'primary'
      })
    }
  }
}
</script>

<style>
#root, body, html {
  height: 100%
}

.layout-demo {
  display: flex;
  height: 100%;
  min-height: 100%;
  background: var(--color-fill-2);
  border: 1px solid var(--color-border);
  /* width: 15% */
}
.layout-demo :deep(.arco-layout-sider) .logo {
  height: 32px;
  margin: 12px 8px;
  background: rgba(255, 255, 255, 0.2);
}
.layout-demo :deep(.arco-layout-sider-light) .logo{
  background: var(--color-fill-2);
}
.layout-demo :deep(.arco-layout-header)  {
  height: 64px;
  line-height: 64px;
  background: var(--color-bg-3);
}
.layout-demo :deep(.arco-layout-footer) {
  height: 48px;
  color: var(--color-text-2);
  font-weight: 400;
  font-size: 14px;
  line-height: 48px;
}
.layout-demo :deep(.arco-layout-content) {
  color: var(--color-text-2);
  font-weight: 400;
  font-size: 14px;
  background: var(--color-bg-3);
}
.layout-demo :deep(.arco-layout-footer),
.layout-demo :deep(.arco-layout-content)  {
  display: flex;
  flex-direction: column;
  justify-content: center;
  color: var(--color-white);
  font-size: 16px;
  font-stretch: condensed;
  text-align: center;
}
</style>