<template>
    <div>
        <div style="margin-bottom: 10px">
            <va-breadcrumbs separator=">">
                <va-breadcrumbs-item label="基本信息" disabled />
                <va-breadcrumbs-item label="修改基本信息" to="/home/editbasicinfo" />
            </va-breadcrumbs>
        </div>

        <a-modal
        v-model:visible="showModal"
        hide-cancel="true"
        @ok="handleModalOk"
        >
            <template #title>
                上传头像
            </template>
            <el-upload
                ref="upload"
                :action="getUploadAvatarUrl()"
                :auto-upload="false"
                :data="additionalData()"
                :headers="headers()"
                style="text-align: center"
                :before-upload="beforeAvatarUpload"
            >
                <template #trigger>
                <va-button gradient :rounded="false">选择文件</va-button>
                </template>
                <va-button style="margin-left: 20px" flat :rounded="false" @click="submitUpload">上传</va-button>
            </el-upload> 
        </a-modal>

        <va-card>
            <va-card-title style="font-size: 20px">修改基本信息</va-card-title>
            <va-card-content>
                <table class="va-table">
                    <tbody style="font-size: 16px">
                        <tr style="width: 220px">
                            <td class="head">头像</td>
                            <td style="text-align: center; width: 170px">
                                <va-button @click="showModal=!showModal" flat style="color: #555">修改头像</va-button>
                                </td>
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
                                        <va-input v-model="nameInput" />
                                    </div>
                                    <div style="display: flex; height: 36px">
                                        <div class="head" style="margin-right: 30px">性别</div>
                                        <va-select
                                        v-model="genderInput"
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
            id: '',
            name: '',
            gender: '',

            genderInput: '',
            nameInput: '',

            genderSubmit: '',
            nameSubmit: '',

            avatarUrl: '',

            showModal: false,
        }
    },
    mounted () {
        this.id = localStorage.getItem("userId")
        fetch(this.$URL + "/user/get?id=" + this.id, {
        method: "GET",
        headers: { "satoken": localStorage.getItem("token") }
        }).then(res => res.json())
        .then((res) => {
            console.log(res)
            this.name = res.name
            this.gender = res.gender
        })
    },
    methods: {
        onSubmit () {

            /**
             * 修改了当提交信息为空时候的逻辑，现在在后端进行检查了，若提交空信息给后端则不修改。
             */
            console.log(this.name, this.gender)
            console.log(this.genderInput)
            if (this.genderInput == '男') {
                this.genderSubmit = 1
                // console.log("?")
            }
            else if (this.genderInput == '女') {
                this.genderSubmit = 2
                // console.log("??")
            } else {
                this.genderSubmit = 0
                // console.log("???")
            }
        
            let req = {
                id: this.id,
                name: this.nameInput,
                gender: this.genderSubmit
            }

            // console.log(req)

            fetch(this.$URL + "/user/update", {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    "satoken": localStorage.getItem("token")
                },
                body: JSON.stringify(req)
            }).then(response => response.json())
            .then(res => {
                // console.log(res)
                if (res.code == 200) {
                    this.$notification.success("修改成功");
                    localStorage.setItem("gender", this.gender)
                    localStorage.setItem("username", this.name)
                    /**
                     * 以下replace语句是用来跳转到一个空白页面{name: 'Refresh'}实现当前界面自动重新加载的效果
                     * 
                     * 经实践，填path比填name更快。
                     */
                    // this.$router.replace({name: 'Refresh'})
                    // this.$router.replace({path: '/refresh'})
                    this.$router.push({name: 'BasicInfo'})
                } else {
                    this.$notification.error("修改失败")
                }
            })
            
        },
        onPreview(file) {
            this.$modal.info({
                title: 'Preview',
                content: (
                <div style="text-align: center">
                    <img style="max-width: 100%" src={file.url || URL.createObjectURL(file.originFile)} />
                </div>
                )
            });
        },
        getUploadAvatarUrl() {
            return this.$URL + "/file/upload/avatar"
        },
        beforeAvatarUpload(file) {
            // console.log("file.type: " + file.type)
            const isIMAGE = (file.type === 'image/jpeg' || file.type === 'image/png')
            const isLt2M = file.size / 1024 / 1024 < 2

            if (!isIMAGE) {
                this.$notification.error('请上传jpg或png格式的图片')
            }
            if (!isLt2M) {
                this.$$notification.error('图片大小请小于2M')
            }
            return isIMAGE && isLt2M
        },
        renameAvatar() {
            return "avatar_" + this.id;
        },
        additionalData() {
            return {
                "id": this.id
            }
        },
        headers(){
            return {
                "satoken": localStorage.getItem("token")
            }
        },
        submitUpload() {
            this.$refs.upload.submit()
        },
        handleModalOk() {
            /**
             * TODO: 这里会直接跳转到登录前的界面，需要后续借助SA-TOKEN实现已登录自动跳转
             */
            location.reload()
            
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