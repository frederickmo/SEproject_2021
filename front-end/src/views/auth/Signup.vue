<template>
    <form @submit.prevent="onsubmit">
        <va-input
        v-model="email"
        type="email"
        label="email"
        />

        <va-input
        v-model="password"
        type="password"
        label="password"
        style="margin-top: 10px"
        />
    <div style="margin-top: 15px">
        <va-button @click="onsubmit">Sign up</va-button>
        <!-- <va-button @click="toLogIn">to log in</va-button> -->
    </div>
    </form>
</template>

<script>
export default {
    name: 'SignUp',
    data () {
        return {
            email: '',
            password: '',
        }
    },
    methods: {
        onsubmit () {
            console.log(this.email, this.password)
            this.$router.push({name: 'Home'})
        },
        toLogIn () {
            console.log('Now switch to login page.')
            this.$router.push({name: 'Login'})
        }
    }

}
</script>

<style>

</style>