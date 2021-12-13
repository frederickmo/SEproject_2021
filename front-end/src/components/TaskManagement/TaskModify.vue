<template>
  <va-card gradient color="#e0e5df">
      <va-card-content style="text-align: left">
        <h1>修改项目信息</h1>
          <div class="course-title">项目名字：{{this.taskName}}<va-input
      class="mb-4"
      v-model="this.taskName"
      placeholder="请输入修改后的内容"
      outline
    />

</div>
          <div class="course-description">项目描述：{{this.taskDescription}}<va-input
      class="mb-4"
      v-model="this.taskDescription"
      placeholder="请输入修改后的内容"
      outline
    /></div>
            <div class="course-year">项目截止日期：{{this.deadline}}
                 <a-date-picker v-model="this.deadline" style="width: 200px;" />
                <!-- <va-input
      class="mb-4"
      v-model="this.deadline"
      placeholder="请输入修改后的内容"
      outline
    /> -->
    </div>
            <div class="course-semester">项目类型：{{this.type}}<va-input
      class="mb-4"
      v-model="this.type"
      placeholder="请输入修改后的内容"
      outline
    /></div>
    <div class="course-semester">项目url：{{this.url}}<va-input
      class="mb-4"
      v-model="this.url"
      placeholder="请输入修改后的内容"
      outline
    /></div>
            <div style="height: 20px" />

            <va-button @click="modifytask()" color="#e0e5df" style="color: rgb(40,40,40)">确认修改</va-button>
           
      </va-card-content>
  </va-card>
<div style="height: 2px" />
  
</template>

<script>
export default {
    data () {
        return {
            id: '',
            taskId:'',
            type: '',
            taskName: '',
            taskDescription: '',
            url: '',
            deadline: '',
            manage:'',
            task:[],
            student:[],
            teacher:[]
        }
    }, 
    mounted () {
        this.id = localStorage.getItem("userId")
        this.courseId = localStorage.getItem("courseId")
        this.taskId=localStorage.getItem("taskId")
        console.log("从上个页面传来的params.courseId: " + this.$route.params.courseId)
        // if (this.$route.params.courseId == undefined) {
            console.log(this.$route.params.taskId)
            fetch(this.$URL + '/task/get?id=' + this.$route.params.taskId, {
                method: "GET"
            }).then(response => {
                console.log(response)
                let result = response.json()
                result.then(res => {
                     console.log(res)
                    this.taskName = res.name
                    this.taskDescription = res.description
                    //if(res.year!=null)
                      this.url = res.url
                    //else
                      this.type = res.type
                    //if(res.semester!=null)
                      this.deadline = res.deadline
                   // else
                     
                    //console.log(this.courseName+" "+this.courseDescription)
                })
            })

        

    },
  methods: {
    modifytask()
    {
      
        
         let submitForm = {
             courseId:this.$route.params.courseId,  
                id: this.$route.params.taskId,
                name:this.taskName,
                type:this.type,
                url:this.url,
                description:this.taskDescription,
                deadline:this.deadline
            }
            if(submitForm.url==null)
              submitForm.url=''
            if(submitForm.taskDescription==null)
              submitForm.taskDescription=''
            console.log(submitForm)
            fetch(this.$URL + "/task/update", {
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