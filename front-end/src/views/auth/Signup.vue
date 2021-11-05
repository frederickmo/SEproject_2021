<template>
    <form @submit.prevent="onsubmit">
        <va-input
        v-model="email"
        type="email"
        label="email"
        :error="!!emailErrors.length"
        :error-messages="emailErrors"
        />

        <va-input
        v-model="password"
        type="password"
        label="password"
        :error="!!passwordErrors.length"
        :error-messages="passwordErrors"
        style="margin-top: 10px"
        />
    <div style="margin-top: 15px">
        <va-button @click="onsubmit">注册</va-button>
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
            emailErrors: [],
            passwordErrors: [],
        }
    },
    computed: {
        formReady () {
            return !this.emailErrors.length && !this.passwordErrors.length
        }
    },
    methods: {
        onsubmit () {
            let myReg=/^(\w|(\.\w+))+@([a-zA-Z0-9_-]+\.)+(com|org|cn|net)+$/
            console.log(this.email, this.password)
            if (!this.email) {
                this.emailErrors = ['email不能为空！']
            } else if (!myReg.test(this.email)) {
                this.emailErrors = ['请填入正确格式的email!']
            } else {
                this.emailErrors = []
            }
            this.passwordErrors = this.password ? [] : ['密码不能为空！']
            if (!this.formReady) {
                return
            } else {
                // this.$vaToast.init({
                //     message: '注册成功！',
                //     color: 'success'
                // })
                this.$notification.success('注册成功！')
                this.$router.push({
                    name: 'SignupSuccess',
                    params: {
                        email: this.email,
                        password: this.password
                    }
                })
            }
        },
    }

}
</script>

<style>

</style>