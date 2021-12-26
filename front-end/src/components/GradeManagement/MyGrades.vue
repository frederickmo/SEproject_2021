<template>
  <div>
      <div style="margin-bottom: 10px">
        <va-breadcrumbs separator=">">
            <va-breadcrumbs-item label="成绩管理" disabled />
            <va-breadcrumbs-item label="我的成绩" to="/home/mygrades" />
        </va-breadcrumbs>
      </div>
      <a-button style="margin-right: 10px" @click="this.Scorestatus=1">实验项目成绩</a-button>
      <a-button @click="this.Scorestatus=2">课程最终成绩</a-button>
    <va-card v-if="this.Scorestatus==1">
      <div style="display: flex">
      </div>
      <va-card-title style="font-size: 20px">实验项目成绩</va-card-title>
      <h1 v-if="this.show==1">您尚未参加任何实验项目</h1>
        <va-card-content v-if="this.show==0">
           
           <div v-for="(item,index) in scorelist" :key="index">
             <h1>{{name[index]}}</h1>
             <br>
            <el-table :data="scorelist[index]" style="width: 100%">
              <el-table-column prop="2" label="项目ID"  />
              <el-table-column prop="3" label="项目名字"  />          
              <el-table-column prop="4" label="得分" />
              <!-- <el-table-row> -->
            </el-table>

            <h2 v-if="score[index]!=0">{{score[index]}}</h2>
            <h2 v-else>本项目未参加测试</h2>
            <br>
           </div>
            <!-- <H3>所有项目的平均成绩为：{{averagescore}}分</H3> -->
        </va-card-content>
        
    </va-card>
 
 
 <!-- <va-card v-if="this.Scorestatus==2">
      <div style="display: flex">
      </div>
      <va-card-title style="font-size: 20px">课程最终成绩</va-card-title>
      <h1 v-if="this.show==1">您尚未参加任何实验项目</h1>
        <va-card-content v-if="this.show==0">
           
           <div v-for="(item,index) in name" :key="index">
             <h1>{{name[index]}}</h1>
             <br>
            <el-table :data="scorelist[index]" style="width: 100%">
              <el-table-column prop="2" label="项目ID"  />
              <el-table-column prop="3" label="项目名字"  />          
              <el-table-column prop="coursescore.id" label="得分" />
             
            </el-table>

            <h2 v-if="score[index]!=0">{{score[index]}}</h2>
            <h2 v-else>本项目未参加测试</h2>
            <br>
           </div>
          
        </va-card-content>
        
    </va-card> -->
    <va-card v-if="this.Scorestatus==2">
      <div style="display: flex">
      </div>
      <va-card-title style="font-size: 20px">课程最终成绩</va-card-title>
      <h1 v-if="this.show==1">您尚未参加任何实验项目</h1>
        <va-card-content v-if="1">
           
          <el-table v-if="1"
          :data="coursescore.filter(
              (data) =>
                !search || data.name.toLowerCase().includes(search.toLowerCase())
            )
          "
          style="width: 100%"
          >        
            <el-table-column label="项目名称" prop="name" />{{name[1]}}
            <el-table-column label="得分" prop="score" />
            <el-table-column align="right">
            <template #header>
              <el-input v-model="search" size="mini" placeholder="Type to search" />
            </template>
            
              </el-table-column>
          </el-table>
        </va-card-content>
        
    </va-card>
  </div>
  
</template>

<script>
export default {
  data () {
    return {
      Scorestatus:1,
      showAvatar: false,
      count:0,
      num:0,
      studentId: '',
      scorelist:[],
      coursename:[],
      name:[],
      score:[], 
      show:0,
      coursescore:[],
      // taskID:'',
      // answer:'',
      // finishTime:'',
      // finished:'',
      // score:''
      //items:scorelist,
      averagescore:0
    }
  },
  mounted () {
    this.studentId = localStorage.getItem("userId")
    this.password = localStorage.getItem("password")
    fetch(this.$URL + "/finishes/get/record/detail/student/groupByCourse?studentId=" + this.studentId, {
      method: "GET",
      headers: { "satoken": localStorage.getItem("token") }
    }).then((res) => {
      var result = res.json()
      result.then((res) => {
        console.log(res)
        if(res.code==402)
          this.show=1
        else
        {
          this.scorelist=res
          //console.log(this.scorelist[0][0][1])
          for(var i in this.scorelist)
          {
            //console.log(this.scorelist[i].length)
            if(this.scorelist[i].length!=0)
            {
              //this.name[i]=this.scorelist[i][0][1]
              this.num++
              //console.log(this.name)
              for(var x in this.scorelist[i])
              {
                if(this.scorelist[i][x][4]==null)
                  {
                    this.count++
                    this.scorelist[i][x][4]="未批改"
                    //continue
                  }
                  else
                  {
                console.log(this.score[i]+" "+this.scorelist[i][x][4])
                if((x==0)&&(this.scorelist[i][x][4]!="未批改"))
                  this.score[i]=this.scorelist[i][x][4]
                else
                  this.score[i]=this.score[i]+this.scorelist[i][x][4]
                console.log("woshi"+this.score[i])
              }
              }
              console.log(!this.score[i])
              if(!this.score[i])
                this.score[i]="全部项目未批改"
              else
                this.score[i]="本项目的平均分为："+this.score[i]/(this.scorelist[i].length- this.count)+"分"
              
              // if(!this.score[i])
              //   this.score[i]="全部项目未批改"
              this.count=0
          }
            else{
              this.score[i]=0
              //this.name[i]=[]
            }
          
          }
          console.log(this.score)
        }
      }),
    fetch(this.$URL + "/takes/get/student?studentId=" + this.studentId, {
    method: "GET",
    headers: { "satoken": localStorage.getItem("token") }
    }).then((res) => {
      var result = res.json()
      result.then((res) => {
        //console.log(res)
        this.coursescore=res
        // this.coursescore.map(((item)=> {
        // this.coursescore.push(Object.assign({},item,{name:"1"}))
        // }))
        for(var h in this.coursescore)
          this.coursescore[h].name=this.name[h]
        console.log(this.coursescore)
      })
    }),

    fetch(this.$URL + "/takes/get/student/detail?studentId=" + this.studentId, {
      method: "GET",
      headers: { "satoken": localStorage.getItem("token") }
    }).then((res) =>{
      var result = res.json()
      result.then((res) => {
      for(var i in res)
      {
        this.name.push(res[i].name)
      }
       for(var h in this.coursescore)
          this.coursescore[h].name=this.name[h]
      })
    })
    })
  
    
  },
  methods: {
    
    
  }

}
</script>

<style>

</style>