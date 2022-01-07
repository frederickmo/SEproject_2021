
<template>
    <div style="background-color:#FCFCFC;font-family:'宋体';height:100%;">
        <div>
            <my-bread level1='个人中心' level2='修改密码' :level3="levelName"></my-bread>
            <va-button flat :to="{name: 'Home'}">back</va-button>
        </div>
        <div style="margin-top:25px;margin-left:80px;">
            <el-row :gutter="10">
                <el-col :span="2">
                    <div style="background-color:#FFEBCD;width:60px;height:60px;display:inline-block;border-radius:50%;overflow:hidden;">
                        <el-image src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" style="width:60px;height:60px;"></el-image>
                    </div>
                    <div style="margin-top:5px;margin-left:6px;"><span>王某某</span></div>
                    <div style="margin-top:50px;margin-left:1px;"><el-button type="text" style="font-size:15px;color:#4D4D4D;"  @click="infomationClick()">个人信息<span style="color:#B0E0E6;" v-show="infomationShow" class="el-icon-s-promotion"></span></el-button></div>
                    <div style="margin-top:5px;margin-left:1px;"><el-button type="text" style="font-size:15px;color:#4D4D4D;" @click="passwordClick()">修改密码<span style="color:#B0E0E6;" v-show="passwordShow" class="el-icon-s-promotion"></span></el-button></div>
                </el-col>

                <el-col :span="21">
                    <el-row :gutter="10" style="margin-top:20px;">
                        <el-col :span="2"><div style="text-align:right;"><span>账号id：</span></div></el-col>
                        <el-col :span="5">0000000000</el-col>
                    </el-row>
                    <!-- 个人信息 -->
                    <el-row v-show="infomationShow">
                        <el-card style="margin-top:30px;">
                            <el-row>
                                <el-col :span="6">
                                    <el-row :gutter="12" style="margin-top:20px;">
                                        <el-col :span="8"><div style="text-align:right;"><span>姓名：</span></div></el-col>
                                        <el-col :span="16">王某某</el-col>
                                    </el-row>
                                    
                                    <el-row :gutter="12" style="margin-top:30px;">
                                        <el-col :span="8"><div style="text-align:right;"><span>电子邮箱：</span></div></el-col>
                                        <el-col :span="16">2036128127@qq.com</el-col>
                                    </el-row>
                                    <el-row :gutter="12" style="margin-top:30px;">
                                        <el-col :span="8"><div style="text-align:right;"><span>班级：</span></div></el-col>
                                        <el-col :span="16">某某班</el-col>
                                    </el-row>
                                    <el-row :gutter="12" style="margin-top:30px;">
                                        <el-col :span="8"><div style="text-align:right;"><span>角色：</span></div></el-col>
                                        <el-col :span="16">学生</el-col>
                                    </el-row>
                                   
                                </el-col>
                                <el-col :span="18">
                                    <el-row>
                                        <el-col>
                                            <el-timeline>
                                                <el-timeline-item timestamp="实验一" placement="top">
                                                    <el-card>
                                                        <div><span><span style="font-family:'宋体';font-size:15px;"><strong>实验名称</strong></span><span style="font-family:'宋体';font-size:12px;margin-left:20px;"><strong>任课教师</strong></span></span></div>
                                                        <div><span>实验完成时间</span>/<span>成绩</span></div>
                                                    </el-card>
                                                </el-timeline-item>
                                                <el-timeline-item timestamp="实验二" placement="top">
                                                    <el-card>
                                                        <div><span><span style="font-family:'宋体';font-size:15px;"><strong>实验名称</strong></span><span style="font-family:'宋体';font-size:12px;margin-left:20px;"><strong>任课教师</strong></span></span></div>
                                                        <div><span>实验完成时间</span>/<span>成绩</span></div>
                                                    </el-card>
                                                </el-timeline-item>
                                                <el-timeline-item timestamp="实验三" placement="top">
                                                    <el-card>
                                                       <div><span><span style="font-family:'宋体';font-size:15px;"><strong>实验名称</strong></span><span style="font-family:'宋体';font-size:12px;margin-left:20px;"><strong>任课教师</strong></span></span></div>
                                                        <div><span>实验完成时间</span>/<span>成绩</span></div>
                                                    </el-card>
                                                </el-timeline-item>
                                                <el-timeline-item timestamp="实验四" placement="top">
                                                    <el-card>
                                                        <div><span><span style="font-family:'宋体';font-size:15px;"><strong>实验名称</strong></span><span style="font-family:'宋体';font-size:12px;margin-left:20px;"><strong>任课教师</strong></span></span></div>
                                                        <div><span>实验完成时间</span>/<span>成绩</span></div>
                                                    </el-card>
                                                </el-timeline-item>
                                                <el-timeline-item timestamp="实验五" placement="top">
                                                    <el-card>
                                                       <div><span><span style="font-family:'宋体';font-size:15px;"><strong>实验名称</strong></span><span style="font-family:'宋体';font-size:12px;margin-left:20px;"><strong>任课教师</strong></span></span></div>
                                                        <div><span>实验完成时间</span>/<span>成绩</span></div>
                                                    </el-card>
                                                </el-timeline-item>
                                            </el-timeline>
                                        </el-col>
                                    </el-row>
                                </el-col>
                            </el-row>
                        </el-card>
                    </el-row>
                    <!-- 修改密码 -->
                    <el-row v-show="passwordShow">
                        <el-card style="margin-top:30px;">
                            <el-form :model="personalForm">
                                <el-row :gutter="12" style="margin-top:20px;">
                                    <el-col :span="6">
                                        <div style="text-align:right;"><span>账号：</span></div>
                                    </el-col>
                                    <el-col :span="5">
                                        <el-form-item prop="account">
                                            <el-input type="text" v-model="personalForm.account" placeholder="请输入您的账号" ></el-input>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                
                                <el-row :gutter="12" style="margin-top:1px;">
                                    <el-col :span="6">
                                        <div style="text-align:right;"><span>新密码：</span></div>
                                    </el-col>
                                    <el-col :span="5">
                                        <el-form-item prop="password1">
                                            <el-input type="password" show-password v-model="personalForm.password1" placeholder="请输入新的密码" ></el-input>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <el-row :gutter="12" style="margin-top:1px;">
                                    <el-col :span="6">
                                        <div style="text-align:right;"><span>确认密码：</span></div>
                                    </el-col>
                                    <el-col :span="5">
                                        <el-form-item prop="password2">
                                            <el-input type="password" show-password v-model="personalForm.password2" placeholder="请再次输入新的密码" ></el-input>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                
                                <el-row :gutter="12" style="margin-top:10px;">
                                    <el-col :span="17" style="text-align:center;">
                                        <el-button type="primary" plain round size="medium" @click="submit()">修改</el-button>
                                    </el-col>
                                </el-row>
                            </el-form>
                        </el-card>
                    </el-row>
                </el-col>
            </el-row>
        </div>
        
        <div>

        </div>
    </div>
</template>
<script>

export default {
    name: 'Information',
    data(){
        return {
            // src:yu,
            levelName:'',
            isCollapse:false,
            infomationShow:false,
            passwordShow:false,
           
            isDisabled:false,
            time:60,
            personalForm:{
                account:"",
                
                verification:"",
                password1:"",
                password2:"",
            }
        }
    },
    methods:{
        handleOpen(key, keyPath) {
            console.log(key, keyPath)
            console.log(key)
            console.log(keyPath)
        },
        

        infomationClick(){    //个人信息事件
            this.infomationShow = true
            this.passwordShow = false
        },
        
        passwordClick(){     //密码事件
            this.infomationShow = false
            this.passwordShow = true
        },
        
        submit(){  //提交
            if(this.personalForm.account === ""){
                this.$message.warning("账号不能为空")
            }
            else if(this.personalForm.password1 === ""){
                this.$message.warning("密码不能为空")
            }
            else if(this.personalForm.password2 === ""){
                this.$message.warning("密码不能为空")
            }
            else{
                if(this.personalForm.password1 === this.personalForm.password2){
                    this.$message.success("修改成功")
                    // const params = {
                    //     account:this.personalForm.account,
                    //     phone_num:this.personalForm.phone_num,
                    //     password1:this.personalForm.password1,
                    //     password2:this.personalForm.password2,
                    //     verification:this.personalForm.verification,
                    // }
                    // console.log(params)
                    // this.$http.post('',params).then(res => {
                    //     console.log(res)
                    // })
                }else{
                    this.$message.warning("两次输入的密码不一致,请重新输入")
                }
                
            }
        },
 
    }
}
</script>

<style>
    .name{ text-align: right; }
    .value{ text-align: left; }
</style>
