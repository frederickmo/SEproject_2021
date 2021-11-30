<template>
    <form @submit.prevent="onSubmit">
        <va-input
        v-model="id"
        type="id"
        label="id"
        :error="!!idErrors.length"
        :error-messages="idErrors"
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
        <va-button flat style="margin-left: 10px" @click="this.$router.push({name: 'RecoverPassword', params: {id: this.id}})">忘记密码？</va-button>
    </div>
    </form>
</template>

<script>
export default {
    name: 'LogIn',
    data() {
        return {
            id: '',
            password: '',
            idErrors: [],
            passwordErrors: [],
        }
    },
    computed: {
        formReady() {
            return !this.idErrors.length && !this.passwordErrors.length
        }
    },
    mounted () {

        this.id = 1852461
        this.password = 12345

        fetch(this.$URL + "/user/isLogin", {
            method: "GET"
        }).then((res) => {
            let result = res.text()
            result.then(res => {
                console.log(res)
                if (res == 1) {
                    this.$notification.success("当前用户已登录")
                    this.$router.push({name: 'Home'})
                } else {
                    return
                }
            })
        })

        console.log('params from the last page:')
        console.log(this.$route.params.id, this.$route.params.password)
        this.id = this.$route.params.id ? this.$route.params.id : 1852461
        this.password = this.$route.params.password ? this.$route.params.password : 12345
    },
    methods: {
        onSubmit () {
            // let myReg=/^(\w|(\.\w+))+@([a-zA-Z0-9_-]+\.)+(com|org|cn|net)+$/
            console.log(this.id, this.password)
            if (!this.id) {
                this.idErrors = ['id不能为空']
            } else {
                this.idErrors = []
            }
            this.passwordErrors = this.password ? [] : ['密码不能为空']
            if (!this.formReady) {
                return
            } else {
                fetch(this.$URL + "/user/login?id=" + this.id + "&password=" + this.password, {
                    method: "GET",
                }).then((res) => {
                    let result = res.json()
                    result.then((res) => {
                        console.log("打印token了吗？")
                        console.log(res)
                        if (res.status==200) {
                            this.$notification.success("登录成功")
                            localStorage.setItem("id", this.id)
                            localStorage.setItem("password", this.password)
                            this.$router.push({name: 'Home'})
                        } else {
                            this.$notification.error("用户不存在或密码错误")
                        }
                    })

                })

                // fetch(this.$URL + "/login?username=" + this.id + "&password=" + this.password)
                // .then((res) => {
                //     console.log(res)
                //     console.log(res.status)
                //     if (res.status == 200) {
                //         this.$notification.success('登陆成功')
                //         this.$router.push({
                //             name: 'Home',
                //             params: {
                //                 id: this.id,
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
                //         id: this.id,
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