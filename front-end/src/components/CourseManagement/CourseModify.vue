<template>
  <va-card gradient color="#e0e5df">
      <va-card-content style="text-align: left">
        <h1>修改课程信息</h1>
          <div class="course-title">课程名字：{{this.courseName}}<va-input
      class="mb-4"
      v-model="this.courseName"
      placeholder="请输入修改后的内容"
      outline
    />

</div>
          <div class="course-description">课程描述：{{this.courseDescription}}<va-input
      class="mb-4"
      v-model="this.courseDescription"
      placeholder="请输入修改后的内容"
      outline
    /></div>
            <div class="course-year">开设年份：{{this.year}}<va-input
      class="mb-4"
      v-model="this.year"
      placeholder="请输入修改后的内容"
      outline
    /></div>
            <div class="course-semester">学年：{{this.semester}}<va-input
      class="mb-4"
      v-model="this.semester"
      placeholder="请输入修改后的内容"
      outline
    /></div>
            <div style="height: 20px" />

            <va-button @click="modifycourse()" color="#e0e5df" style="color: rgb(40,40,40)">确认修改</va-button>
           
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
</template>

<script>
export default {
    data () {
        return {
            id: '',
            courseId: '',
            courseName: '',
            courseDescription: '',
            year: '',
            semester: '',
            manage:'',
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
            fetch(this.$URL + '/course/get?id=' + this.courseId, {
                method: "GET"
            }).then(response => {
                // console.log(response)
                let result = response.json()
                result.then(res => {
                     console.log(res)
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
        
 fetch(this.$URL + "/course​/get?id=" + this.courseId, {
      method: "GET"
    }).then(response => {
      console.log(response)
      let result = response.json()
      result.then(res => {
        console.log(res)
        this.manage=res.manager
      })
    })

    },
  methods: {
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
      if(teacherid!=this.manage)
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