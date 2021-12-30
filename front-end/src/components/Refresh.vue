<template>
  <div></div>
</template>

<script>
export default {
  name: "Refresh",
  data() {
    return {};
  },
  beforeRouteEnter(to, from, next) {
    next((vm) => {
      vm.$router.replace(from.path);
    });
  },
};
</script>

<style></style>
