<template>
  <router-view />
</template>

<script>
// import Home from './views/Home.vue'

export default {
  name: "App",
  components: {
    // Home
  },
  mounted() {
    // this.$router.push({name: 'Auth'})
    this.$router.push({ name: "Login" });
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  /* margin-top: 60px; */
}
</style>
