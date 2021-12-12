<template>
  <va-card gradient color="#e0e5df">
      <va-card-content style="text-align: left">
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

            course:[]
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
            fetch(this.$URL + "/course/updateCourse", {
                method: "PUT",
                headers: { "Content-Type": "application/json"},
                body: JSON.stringify(submitForm)
            }).then(response => {
                // console.log(response)
                let result = response.json()
                result.then(res => {
                    // console.log(res)
                    if (res.status == 200) {
                        this.$notification.success('上传成功')
                        this.$router.go(-1)
                    }
                })
            })


   
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