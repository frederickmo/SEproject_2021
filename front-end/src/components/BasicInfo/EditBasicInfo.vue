<template>
    <div>
        <div style="margin-bottom: 10px">
            <va-breadcrumbs separator=">">
                <va-breadcrumbs-item label="基本信息" disabled />
                <va-breadcrumbs-item label="修改基本信息" to="/home/editbasicinfo" />
            </va-breadcrumbs>
        </div>

        <va-card>
            <va-card-title style="font-size: 20px">修改基本信息</va-card-title>
            <va-card-content>
                <table class="va-table">
                    <tbody style="font-size: 16px">
                        <tr style="width: 220px">
                            <td class="head">头像</td>
                            <td style="text-align: center; width: 170px"><va-button flat style="color: #555">修改头像</va-button></td>
                        </tr>
                    </tbody>
                </table>
                <table class="va-table">
                    <tbody style="font-size: 16px">
                        <tr>
                            <td style="width: 250px">
                                <va-form @submit.prevent="onSubmit">
                                    <div style="display: flex; height: 36px; margin-bottom: 20px">
                                        <div class="head" style="margin-right: 30px">姓名</div>
                                        <va-input v-model="name" />
                                    </div>
                                    <div style="display: flex; height: 36px">
                                        <div class="head" style="margin-right: 30px">性别</div>
                                        <va-select
                                        v-model="myGender"
                                        :options="['男','女']" />
                                    </div>
                                        <va-button @click="onSubmit" style="margin-left: 60px; margin-top: 20px;">提交</va-button>
                                </va-form>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </va-card-content>
        </va-card>
    </div>
</template>

<script>
export default {
    data () {
        return {
            email: '',
            password: '',
            name: '',

            myGender: '',
            gender: 1,
            userInfo: {
                email: this.email,
                password: this.password,
                name: 'John Doe',
                gender: 1,
                activated: this.activated,
                status: this.status
            }
        }
    },
    mounted () {

    },
    methods: {
        onSubmit () {
            console.log(this.name, this.gender)
            if (this.myGender == '男') {
                this.gender = 1
            } else if (this.myGender == '女') {
                this.gender = 2
            } else {
                this.gender = 0
            }
        }
    }
}
</script>

<style scoped>
.head {
  font-weight: bold;
  width: 80px;
  min-width: 40px;
  line-height: 36px;
}

</style>