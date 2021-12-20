<template>
  <div>
      <div style="margin-bottom: 10px">
        <va-breadcrumbs separator=">">
            <va-breadcrumbs-item label="成绩管理" disabled />
            <va-breadcrumbs-item label="我的成绩" to="/home/mygrades" />
        </va-breadcrumbs>
      </div>
    <va-card>
      <va-card-title style="font-size: 20px">我的成绩</va-card-title>
        <va-card-content>
           
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
  </div>
  
</template>

<script>
export default {
  data () {
    return {
      showAvatar: false,
      count:0,
      num:0,
      studentId: '',
      scorelist:[],
      name:[],
      score:[], 
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
        //console.log(res)
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
        //console.log(this.name)
        // this.name = res.name
        // this.gender = res.gender
        // this.email = res.email
        // this.identity = res.identity
        // this.activated = res.activated
        
        console.log(this.score)
      })
    }),

    fetch(this.$URL + "/takes/get/student/detail?studentId=" + this.studentId, {
      method: "GET",
      headers: { "satoken": localStorage.getItem("token") }
    }).then((res) =>{
      var result = res.json()
      result.then((res) => {
      //  console.log(res)
      // for(var i in res)
      // {
      //   console.log(res[i].name)
      //   if(this.name.indexOf(res[i].name)>-1)
      //   {
      //      console.log(res[i].name)
      //   }
      //   else
      //   {
      //     this.name[this.num]=res[i].name
      //     this.num++
      //   }

        
      // }
      for(var i in res)
      {
        this.name.push(res[i].name)
      }
      
      })
    })

    // localStorage.setItem("gender", this.gender)
    // console.log("this.gender: " , this.gender)
    // localStorage.setItem("username", this.name)
    // console.log("this.username: " , this.name)
    // console.log("name存入localstorage了吗？" + this.localStorage.getItem("gender"))
    // console.log("gender存入localstorage了吗？ " + this.localStorage.getItem("username"))
    
  },
  methods: {
    
    
  }

}
</script>

<style>

</style>