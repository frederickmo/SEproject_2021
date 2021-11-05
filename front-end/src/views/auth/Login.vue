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
        <va-button @click="onsubmit">Log in</va-button>
        <va-button flat style="margin-left: 10px" @click="this.$router.push({name: 'RecoverPassword', params: {email: this.email}})">Forget password?</va-button>
    </div>
    </form>
</template>

<script>
export default {
    name: 'LogIn',
    data() {
        return {
            email: '',
            password: '',
        }
    },
    mounted () {
        console.log('params from the last page:')
        console.log(this.$route.params.email, this.$route.params.password)
    },
    methods: {
        onsubmit () {
            console.log(this.email, this.password)
            this.$router.push({
                name: 'Home',
                params: {
                    email: this.email,
                    password: this.password
                }
            });
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