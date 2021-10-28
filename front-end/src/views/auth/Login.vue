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
        <va-button @click="onsubmit">Login</va-button>
        <!-- <va-button @click="toSignUp">to sign up</va-button> -->
    </div>
    </form>
</template>

<script>
export default {
    name: 'Login',
    data() {
        return {
            email: '',
            password: '',
        }
    },
    methods: {
        onsubmit () {
            console.log(this.email, this.password)
            this.$router.push({name: 'Home'});
        },
        toSignUp () {
            console.log('Now switch to Signup page.')
            this.$router.push({name: 'Signup'})
        }
    }
}
</script>

<style>

</style>