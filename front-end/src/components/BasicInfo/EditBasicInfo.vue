<template>
    <div>
        <div style="margin-bottom: 10px">
            <va-breadcrumbs separator=">">
                <va-breadcrumbs-item label="基本信息" disabled />
                <va-breadcrumbs-item label="修改基本信息" to="/home/editbasicinfo" />
            </va-breadcrumbs>
        </div>

        <va-modal
        v-model="showModal"
        title="修改头像">
            <div>
                <a-upload 
                list-type="picture-card"
                @preview="onPreview"
                auto-upload="false"
                multiple="false"
                draggable="true"
                accept="image/jpeg, image/png"
                :action="getUploadUrl()"
                :data="additionalData()"
                />
                <!--TODO: 现在头像是自动上传的 / 命名还有点问题-->
            </div>
        </va-modal>

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
        method: "GET"
        }).then((res) => {
        var result = res.json()
        result.then((res) => {
            console.log(res)
            this.name = res.name
            this.gender = res.gender
        })
        })
    },
    methods: {
        onSubmit () {
            console.log(this.name, this.gender)
            if (this.genderInput == '男') {
                this.genderSubmit = 1
            }
            if (this.genderInput == '女') {
                this.genderSubmit = 2
            } else {
                this.genderSubmit = 0
            }
        
            let req = {
                id: this.id,
                name: this.nameInput,
                gender: this.genderSubmit
            }

            console.log("现在提交的是哪个name？", req.name)

            fetch(this.$URL + "/user/update", {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(req)
            }).then(response => {
                let result = response.json()
                result.then(res => {
                    console.log(res)
                    if (res.status == 200) {
                        this.$notification.success("修改成功");
                        localStorage.setItem("gender", this.gender)
                        localStorage.setItem("username", this.name)
                    } else {
                        this.$notification.error("修改失败")
                    }
                })
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
        getUploadUrl() {
            return this.$URL + "/file/upload/avatar"
        },
        renameAvatar() {
            return "avatar_" + this.id;
        },
        additionalData() {
            return {"id": this.id}
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