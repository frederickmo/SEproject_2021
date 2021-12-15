<template>
  <va-card gradient color="#e0e5df">
      <va-card-content style="text-align: left">
        <h1>修改课程信息</h1>
          
          <div class="course-title" style="float: left">课程名字：<va-input
      class="mb-4"
      v-model="this.courseName"
      placeholder="请输入修改后的内容"
      outline
    />

</div>
          <div class="course-description" >
            课程描述：
            <va-input
      class="mb-4"
      v-model="this.courseDescription"
      placeholder="请输入修改后的内容"
      outline
    />
    
    </div>


         
<div style="height: 15px" />
    
     
    <div class="course-year" style="float: left ">开设年份：
      <va-input
      class="mb-4"
      v-model="this.year"
      placeholder="请输入修改后的内容"
      outline
    />
    <!-- <a-year-picker v-model="this.year" :defaultValue="2021":style="style" style="width: 200px;" /> -->
    
    </div>
            <div class="course-semester" >学年：<va-input
      class="mb-4"
      v-model="this.semester"
      placeholder="请输入修改后的内容"
      outline
    /></div>
            <div style="height: 20px" />

            <va-button @click="handleClick3" color="#e0e5df" style="color: rgb(40,40,40)">确认修改</va-button>
           

           <a-modal v-model:visible="visible3" @ok="handleOk3" @cancel="handleCancel3" unmountOnClose @before-ok="handleBeforeOk3">
    <template #title>
      修改课程
    </template>
    <div>您确定要修改吗</div>
  </a-modal>
      </va-card-content>
  </va-card>
<div style="height: 2px" />
  <va-card gradient color="#e0e5df">
      <va-card-content style="text-align: left">
        <h1>修改课程成员</h1>
           <va-card 
            v-for="(student, index) in student"
            :key="index"
            color="#b5c4b1" 
            gradient
            style="margin-bottom: 10px"
            >
              <va-card-content style="rgb(60, 60, 60); font-weight: bold">
                <h2 style="text-align:left">学生</h2>
                <div style="display: flex">
                  <!-- va-card高度: 76px -->
                  <!-- va-card高度: 36px -->
                  <div style="line-height: 36px; width: 10%; font-size: 18px">{{student.id}}</div>
                  
                  <div style="line-height: 36px; width: 80%; font-size: 18px">{{student.name}}</div>
                  <div>
                    <!-- </div>
                  <div> -->
                  <div style="height: 2px" /> 
                  <va-button @click="DropCourse(student.id)" color="#e0e5df" style="color: rgb(40,40,40)">点击退课</va-button>
                  </div>
                  
                  
                </div>
                

              </va-card-content>
              
            </va-card>

             <va-card 
            v-for="(teacher, index) in teacher"
            :key="index"
            color="#b5c4b1" 
            gradient
            style="margin-bottom: 10px"
            >
              <va-card-content style="rgb(60, 60, 60); font-weight: bold">
                <h2 style="text-align:left">教师</h2>
                <div style="display: flex">
                  <!-- va-card高度: 76px -->
                  <!-- va-card高度: 36px -->
                  <div style="line-height: 36px; width: 10%; font-size: 18px">{{teacher.id}}</div>
                  
                  <div style="line-height: 36px; width: 77%; font-size: 18px">{{teacher.name}}</div>
                  <div>
                    <!-- </div>
                  <div> -->
                  <div style="height: 2px" /> 
                  <va-button @click="DropmanageCourse(teacher.id)" color="#e0e5df" style="color: rgb(40,40,40)">点击取消授课</va-button>
                  </div>
                  
                  
                </div>
                

              </va-card-content>
              
            </va-card>
            
      </va-card-content>
  </va-card>
  <div style="height: 10px" />
  <div>
    <div>
   
   <va-button  @click="handleClick" color="#e0e5df" style="color: rgb(40,40,40); ">点击添加教师</va-button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;               
   <va-button  @click="handleClick1" color="#e0e5df" style="color: rgb(40,40,40); ">点击添加学生</va-button>
  <a-modal v-model:visible="visible1" @ok="handleOk1" @cancel="handleCancel1" unmountOnClose @before-ok="handleBeforeOk1">
    <template #title>
      请输入学生id
    </template>
    <div><va-input
      class="mb-4"
      v-model="this.studentid"
      placeholder="请输入学生id"
      outline
    /></div>
  </a-modal>

  <a-modal v-model:visible="visible" @ok="handleOk" @cancel="handleCancel" unmountOnClose @before-ok="handleBeforeOk">
    <template #title>
      请输入教师id
    </template>
    <div><va-input
      class="mb-4"
      v-model="this.teacherid"
      placeholder="请输入教师id"
      outline
    /></div>
  </a-modal>
   <div style="height: 10px" />
  </div>
  
  <!-- <va-button @click="handleClick" @click="deleteCourse()" color="#FF0000" style="background-color: rgb(0,0,0) text-align:center">点击删除课程</va-button>  -->
<va-button @click="handleClick4"  color="#FF0000" style="background-color: rgb(0,0,0) text-align:center">点击删除课程</va-button> 
  <a-modal v-model:visible="visible4" @ok="handleOk4" @cancel="handleCancel4" unmountOnClose @before-ok="handleBeforeOk4">
    <template #title>
      删除课程
    </template>
    <div>您确定要删除吗</div>
  </a-modal>
  </div>

</template>

<script>
export default {
    data () {
        return {
          visible: false,
          visible1: false,
          visible3: false,
          visible4: false,
            teacherid: '',
            courseId: '',
            courseName: '',
            courseDescription: '',
            year: '',
            semester: '',
            course_manage:'',
            studentid:'',
            course:[],
            student:[],
            teacher:[]

        }
    }, 
    mounted () {
        this.id = localStorage.getItem("userId")
        this.courseId = localStorage.getItem("courseId")
        console.log("从上个页面传来的params.courseId: " + this.$route.params.courseId)
        // if (this.$route.params.courseId == undefined) {
            console.log("从子路由跳到父路由，需要重新查询课程数据")
             fetch(this.$URL + "/course/get?id="  + this.$route.params.courseId, {
                method: "GET"
            }).then(response => {
                // console.log(response)
                let result = response.json()
                result.then(res => {
                     console.log(res)
                     this.course_manage=res.manager
                    this.courseName = res.name
                    this.courseDescription = res.description
                    if(res.year!=null)
                      this.year = res.year
                    else
                      this.year = "暂未设置"
                    if(res.semester!=null)
                      this.semester = res.semester
                    else
                      this.semester = "暂未设置"
                    console.log(this.courseName+" "+this.courseDescription)
                })
            })

        fetch(this.$URL + "/takes/get/course/detail?courseId=" + this.courseId, {
      method: "GET"
    }).then(response => {
      console.log(response)
      let result = response.json()
      result.then(res => {
        console.log(res)
        this.student = res
      })
    })

        fetch(this.$URL + "/manages/get/course/detail?courseId=" + this.courseId, {
      method: "GET"
    }).then(response => {
      console.log(response)
      let result = response.json()
      result.then(res => {
        console.log(res)
        this.teacher = res
      })
    })
        
 

    },
  methods: {
     
     handleClick() {
      this.visible = true;
    },
    handleClick2(){
      this.$modal({});
    },
    handleOk() {
      // this.visible = false;
      console.log(this.courseId+" "+this.teacherid)
  fetch(this.$URL + "/manages/add?courseId=" + this.courseId+"&teacherId="+this.teacherid, {
      method: "POST"
    }).then(response => {
      console.log(response)
      let result = response.json()
      result.then(res => {
        console.log(res)
        if(res.status==200)
          {
            this.$notification.success("添加教师成功")
            console.log(this.teacher)
            
            fetch(this.$URL + "/manages/get/course/detail?courseId=" + this.courseId, {
      method: "GET"
    }).then(response => {
      console.log(response)
      let result = response.json()
      result.then(res => {
      console.log(res)
      this.teacher=res
      })
    })
          }
          else{
            this.$notification.error("教师id有误")
          }
        //this.manage=res.manager
      })
    })
    this.teacherid=''
    },
    handleCancel() {
      this.visible = false;
      this.teacherid='' 
    },
    handleBeforeOk( done){
      window.setTimeout(()=>{
        done()
      },3000)

    
  },
   handleClick1() {
      this.visible1 = true;
    },
    // handleClick2(){
    //   this.$modal({});
    // },
    handleOk1() {
      // this.visible = false;
 let submitForm = {
                studentId : this.studentid,
                courseId: this.courseId,
                
            }
            console.log(submitForm)
            fetch(this.$URL + "/takes/add", {
                method: "POST",
                headers: { "Content-Type": "application/json"},
                body: JSON.stringify(submitForm)
            }).then(response => {
                // console.log(response)
                let result = response.json()
                result.then(res => {
                    // console.log(res)
                    if (res.status == 200) {
                        this.$notification.success('添加成功！')
                         
                        //this.$router.go(0)
                         fetch(this.$URL + "/takes/get/course/detail?courseId=" + this.courseId, {
      method: "GET"
    }).then(response => {
      console.log(response)
      let result = response.json()
      result.then(res => {
        console.log(res)
        this.student = res
      })
    })
                    }
                    else{
                      this.$notification.error('输入学生id有误！')
                        //this.$router.go(-1)
                    }
                })
            })
            this.studentid=''
    },
    handleCancel1() {
      this.visible = false;
      this.studentid=''
    },
    handleBeforeOk1( done){
      window.setTimeout(()=>{
        done()
      },3000)

    
  },
    

    handleClick3() {
      this.visible3 = true;
    },
    
    handleOk3() {
      let submitForm = {
                id: this.courseId,
                name:this.courseName,
                year:this.year,
                semester:this.semester,
                description:this.courseDescription,
                manager:localStorage.getItem("userId")
            }
            if(submitForm.year=="暂未设置")
              submitForm.year=''
            if(submitForm.semester=="暂未设置")
              submitForm.semester=''
            console.log(submitForm)
            fetch(this.$URL + "/course/update", {
                method: "PUT",
                headers: { "Content-Type": "application/json"},
                body: JSON.stringify(submitForm)
            }).then(response => {
                // console.log(response)
                let result = response.json()
                result.then(res => {
                    // console.log(res)
                    if (res.status == 200) {
                        this.$notification.success('修改成功')
                        this.$router.go(-1)
                    }
                })
            })
    },
    handleCancel3() {
      this.visible3 = false;
     
    },
    handleBeforeOk3( done){
      window.setTimeout(()=>{
        done()
      },3000)

    
  },

handleClick4() {
      this.visible4 = true;
    },
    
    handleOk4() {
     var courseId=this.courseId
      console.log(courseId)
        fetch(this.$URL + "/course/remove?courseId=" + courseId, {
      method: "DELETE"
    }).then(response => {
      console.log(response)
      let result = response.json()
      result.then(res => {
        if (res.status == 200) {
                        this.$notification.success('删除成功')
                        this.$router.go(-1)
                       }
                       })
    })
    },
    handleCancel4() {
      this.visible4 = false;
     
    },
    handleBeforeOk4( done){
      window.setTimeout(()=>{
        done()
      },3000)

    
  },
   
                       
    modifycourse()
    {
      
        
         let submitForm = {
                id: this.courseId,
                name:this.courseName,
                year:this.year,
                semester:this.semester,
                description:this.courseDescription,
                manager:localStorage.getItem("userId")
            }
            if(submitForm.year=="暂未设置")
              submitForm.year=''
            if(submitForm.semester=="暂未设置")
              submitForm.semester=''
            console.log(submitForm)
            fetch(this.$URL + "/course/update", {
                method: "PUT",
                headers: { "Content-Type": "application/json"},
                body: JSON.stringify(submitForm)
            }).then(response => {
                // console.log(response)
                let result = response.json()
                result.then(res => {
                    // console.log(res)
                    if (res.status == 200) {
                        this.$notification.success('修改成功')
                        this.$router.go(-1)
                    }
                })
            })

    },
    DropCourse(studentid)
    {
      console.log(this.courseId+" "+studentid)
         fetch(this.$URL + "/takes/remove?courseId=" + this.courseId+"&studentId="+studentid, {
      method: "DELETE"
    }).then(response => {
      console.log(response)
      let result = response.json()
      result.then(res => {
       if (res.status == 200) {
                        this.$notification.success('退课成功')
                        this.$router.go(-1)
                    }
      })
    })

    
    },

    DropmanageCourse(teacherid)
    {
      console.log(this.course_manage+" "+ localStorage.getItem("userId"))
      if( localStorage.getItem("userId")!=this.course_manage)
      {
        this.$notification.success('您不是责任教师，无法执行该操作')

      }
      else{
      if(teacherid==this.id)
      {
                        this.$notification.success('无法取消责任教师')
                        //this.$router.go(-1)
                    }
                    else{
      console.log(this.courseId,teacherid)
 fetch(this.$URL + "/manages/delete?courseId=" + this.courseId+"&teacherId="+teacherid, {
      method: "DELETE"
    }).then(response => {
      console.log(response)
      let result = response.json()
      result.then(res => {
       if (res.status == 200) {
                        this.$notification.success('取消教师授课成功')
                        this.$router.go(-1)
                    }
      })
    })
                    }
    }
    }
  }
}
</script>

<style>
.course-title {
    /* text-align: left; */
    font-size: 25px;
    font-weight: bold;
    color: #656565;
    margin-bottom: 15px
}

.course-description {
    font-size: 25px;
    font-weight: bold;
    color: #656565;
    margin-bottom: 15px
}

.course-year{
    font-size: 25px;
    font-weight: bold;
    color: #656565;
    margin-bottom: 15px
}
.course-semester{
    font-size: 25px;
    font-weight: bold;
    color: #656565;
    margin-bottom: 15px
}
</style>