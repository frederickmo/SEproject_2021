<template>
  <va-card style="height: 100px">
      <ca-card-content>
          RECOVER PASSWORD
          <va-divider />
          <va-button @click="this.$router.push({name: 'Auth', params: {tabValue: 0, email: this.email, password: this.password}})">to log in</va-button>
      </ca-card-content>
  </va-card>
</template>

<script>
export default {
    name: 'RecoverPassword',
    data () {
        return {
            email: "fabricated_email@gmail.com",
            password: "12345"
        }
    },
    mounted () {
        console.log(this.$route.params.email)
    }
}
</script>

<style>

</style>