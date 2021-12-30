<template>
  <div>
    <div style="margin-bottom: 10px">
      <va-breadcrumbs separator=">">
        <va-breadcrumbs-item label="成绩管理" disabled />
        <va-breadcrumbs-item label="我的成绩" to="/home/mygrades" />
      </va-breadcrumbs>
    </div>
    <!-- <a-button style="margin-right: 10px" @click="this.Scorestatus=!this.Scorestatus">实验项目成绩</a-button>
      <a-button @click="this.Scorestatus=!this.Scorestatus">课程最终成绩</a-button> -->
    <va-card>
      <a-button
        style="position: absolute; right: 30px; top: 15px"
        shape="round"
        @click="this.$router.replace({ path: '/refresh' })"
      >
        <template #icon>
          <icon-refresh />
        </template>
        刷新
      </a-button>
      <va-card-title style="font-size: 20px">{{
        this.Scorestatus ? "实验项目成绩" : "课程最终成绩"
      }}</va-card-title>
      <!-- <va-card-title v-show="!this.Scorestatus" style="font-size: 20px">课程最终成绩</va-card-title> -->
      <div style="text-align: left; margin-left: 20px">
        <!-- <va-switch style="font-weight: bold" v-model="Scorestatus" true-inner-label="实验项目成绩" false-inner-label="课程最终成绩" /> -->
        <a-button @click="handleTaskGrades">实验项目成绩</a-button>
        <a-button style="margin-left: 10px" @click="handleCourseGrades"
          >课程最终成绩</a-button
        >
      </div>

      <!-- 实验项目各自成绩 -->
      <div v-show="this.Scorestatus">
        <div v-if="this.show == 1">您尚未参加任何实验项目</div>
        <va-card-content v-if="this.show == 0">
          <div v-for="(item, index) in scorelist" :key="index">
            <div style="font-size: 20px; font-weight: bold">
              {{ name[index] }}
            </div>
            <br />
            <el-table :data="scorelist[index]" style="width: 100%">
              <el-table-column prop="2" label="项目ID" />
              <el-table-column prop="3" label="项目名字" />
              <el-table-column prop="4" label="得分" />
              <!-- <el-table-row> -->
            </el-table>

            <div
              style="
                margin-top: 10px;
                margin-bottom: 40px;
                font-size: 15px;
                font-weight: bold;
              "
            >
              <div v-if="score[index] != 0">{{ score[index] }}</div>
              <div v-else>本项目未参加测试</div>
            </div>
            <br />
          </div>
        </va-card-content>
      </div>

      <!-- 课程总成绩 -->
      <div v-show="!this.Scorestatus">
        <div v-if="this.show == 1">您尚未参加任何实验项目</div>
        <va-card-content v-if="1">
          <el-table
            v-if="1"
            :data="
              coursescore.filter(
                (data) =>
                  !search ||
                  data.name.toLowerCase().includes(search.toLowerCase())
              )
            "
            style="width: 100%"
          >
            <el-table-column label="项目名称" prop="name" />{{ name[1] }}
            <el-table-column label="得分" prop="score" />
            <el-table-column align="right">
              <template #header>
                <el-input
                  v-model="search"
                  size="mini"
                  placeholder="Type to search"
                />
              </template>
            </el-table-column>
          </el-table>
        </va-card-content>
      </div>
    </va-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      Scorestatus: true,
      showAvatar: false,
      count: 0,
      num: 0,
      studentId: "",
      scorelist: [],
      coursename: [],
      name: [],
      score: [],
      show: 0,
      coursescore: [],
      // taskID:'',
      // answer:'',
      // finishTime:'',
      // finished:'',
      // score:''
      //items:scorelist,
      averagescore: 0,
    };
  },
  mounted() {
    this.studentId = localStorage.getItem("userId");
    this.password = localStorage.getItem("password");
    this.Scorestatus =
      localStorage.getItem("curTabIndex") != undefined
        ? localStorage.getItem("curTabIndex")
        : true;
    // console.log("curTabIndex:" + localStorage.getItem("curTabIndex"));
    // console.log("mounted的scorestatus:" + this.Scorestatus);
    fetch(
      this.$URL +
        "/finishes/get/record/detail/student/groupByCourse?studentId=" +
        this.studentId,
      {
        method: "GET",
        headers: { satoken: localStorage.getItem("token") },
      }
    ).then((res) => {
      var result = res.json();
      result.then((res) => {
        // console.log(res)
        if (res.code == 402) this.show = 1;
        else {
          this.scorelist = res;
          //console.log(this.scorelist[0][0][1])
          for (var i in this.scorelist) {
            //console.log(this.scorelist[i].length)
            if (this.scorelist[i].length != 0) {
              //this.name[i]=this.scorelist[i][0][1]
              this.num++;
              //console.log(this.name)
              for (var x in this.scorelist[i]) {
                if (this.scorelist[i][x][4] == null) {
                  this.count++;
                  this.scorelist[i][x][4] = "未批改";
                  //continue
                } else {
                  // console.log(this.score[i]+" "+this.scorelist[i][x][4])
                  if (x == 0 && this.scorelist[i][x][4] != "未批改")
                    this.score[i] = this.scorelist[i][x][4];
                  else this.score[i] = this.score[i] + this.scorelist[i][x][4];
                  // console.log("woshi"+this.score[i])
                }
              }
              // console.log(!this.score[i])
              if (!this.score[i]) this.score[i] = "全部项目未批改";
              else
                this.score[i] =
                  "本项目的平均分为：" +
                  this.score[i] / (this.scorelist[i].length - this.count) +
                  "分";

              // if(!this.score[i])
              //   this.score[i]="全部项目未批改"
              this.count = 0;
            } else {
              this.score[i] = 0;
              //this.name[i]=[]
            }
          }
          // console.log(this.score)
        }
      }),
        fetch(this.$URL + "/takes/get/student?studentId=" + this.studentId, {
          method: "GET",
          headers: { satoken: localStorage.getItem("token") },
        }).then((res) => {
          var result = res.json();
          result.then((res) => {
            //console.log(res)
            this.coursescore = res;
            // this.coursescore.map(((item)=> {
            // this.coursescore.push(Object.assign({},item,{name:"1"}))
            // }))
            for (var h in this.coursescore)
              this.coursescore[h].name = this.name[h];
            // console.log(this.coursescore)
          });
        }),
        fetch(
          this.$URL + "/takes/get/student/detail?studentId=" + this.studentId,
          {
            method: "GET",
            headers: { satoken: localStorage.getItem("token") },
          }
        ).then((res) => {
          var result = res.json();
          result.then((res) => {
            for (var i in res) {
              this.name.push(res[i].name);
            }
            for (var h in this.coursescore)
              this.coursescore[h].name = this.name[h];
          });
        });
    });
  },
  methods: {
    handleTaskGrades() {
      localStorage.setItem("curTabIndex", true);
      // console.log("curTabIndex:" + localStorage.getItem("curTabIndex"));
      this.Scorestatus = true;
    },
    handleCourseGrades() {
      localStorage.setItem("curTabIndex", false);
      // console.log("curTabIndex:" + localStorage.getItem("curTabIndex"));
      this.Scorestatus = false;
    },
  },
};
</script>

<style></style>
