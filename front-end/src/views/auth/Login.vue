<template>
    <form @submit.prevent="onSubmit">
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
        <va-button @click="onSubmit">登录</va-button>
        <va-button flat style="margin-left: 10px" @click="this.$router.push({name: 'RecoverPassword', params: {email: this.email}})">忘记密码？</va-button>
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
            emailErrors: [],
            passwordErrors: [],
        }
    },
    computed: {
        formReady() {
            return !this.emailErrors.length && !this.passwordErrors.length
        }
    },
    mounted () {
        console.log('params from the last page:')
        console.log(this.$route.params.email, this.$route.params.password)
        this.email = this.$route.params.email
        this.password = this.$route.params.password
    },
    methods: {
        onSubmit () {
            let myReg=/^(\w|(\.\w+))+@([a-zA-Z0-9_-]+\.)+(com|org|cn|net)+$/
            console.log(this.email, this.password)
            if (!this.email) {
                this.emailErrors = ['email不能为空']
            } else if (!myReg.test(this.email)) {
                this.emailErrors = ['请填入正确格式的email']
            } else {
                this.emailErrors = []
            }
            this.passwordErrors = this.password ? [] : ['密码不能为空']
            if (!this.formReady) {
                return
            } else {
                const req = {
                    username: this.email,
                    password: this.password
                }

                fetch(this.$URL + "/login", {
                    method: "POST",
                    headers: {"Content-Type": "application/x-www-form-urlencoded;charset=UTF-8"},
                    body: JSON.stringify(req),
                }).then((res) => {
                    console.log(res)
                    console.log()
                    let response = res.json()
                    response.then((response) => {
                        console.log(response)
                    })


                })

                // fetch(this.$URL + "/login?username=" + this.email + "&password=" + this.password)
                // .then((res) => {
                //     console.log(res)
                //     console.log(res.status)
                //     if (res.status == 200) {
                //         this.$notification.success('登陆成功')
                //         this.$router.push({
                //             name: 'Home',
                //             params: {
                //                 email: this.email,
                //                 password: this.password
                //             }
                //         })
                //     }
                //     else {
                //         this.$notification.error('登录失败')
                //         return
                //     }
                // })





                // this.$router.push({
                //     name: 'Home',
                //     params: {
                //         email: this.email,
                //         password: this.password
                //     }
                // })
            }
        },
    }
}
</script>

<style>

</style>