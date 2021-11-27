<template>

  <div class="app-container">

    <el-row :gutter="24">

      <el-col :span="24">
        <el-card style="margin-bottom: 10px">

          距离考试结束还有：<span style="color: #ff0000;">{{ min }}分钟{{ sec }}秒</span>
          <el-button style="float: right; margin-top: -10px" type="primary" icon="el-icon-plus" :loading="loading" @click="handHandExam()">
            {{ handleText }}
          </el-button>

        </el-card>
      </el-col>

      <!-- <el-col :span="6" :xs="24" style="margin-bottom: 10px"> -->

        <!-- <el-card>

          <p class="card-title">答题卡</p>
          <el-row :gutter="24" class="card-line" style="padding-left: 10px">
            <el-tag type="info">未作答</el-tag>
            <el-tag type="success">已作答</el-tag>
          </el-row>

          <div v-if="testData.radioList!==undefined && testData.radioList.length > 0"> -->
            <!-- <p class="card-title">单选题</p>
            <el-row :gutter="24" class="card-line">
              <el-tag v-for="(item,index) in testData.radioList" :type="cardItemClass(item.answered, item.questionId)" @click="switchQuestion(item)"> {{ item.sort+1 }}</el-tag>
            </el-row> -->
            <!-- <p class="card-title">单选题</p>
            <el-row :gutter="24" class="card-line">
              <el-tag :key="tag" v-for="tag in testData.radioList" 
                :disable-transitions="false" type="2"
                @click="switchQuestion(tag)">
                 {{tag}}
              </el-tag>
            </el-row>
          </div>

          <div v-if="testData.multiList!==undefined && testData.multiList.length > 0">
            <p class="card-title">多选题</p> -->
            <!-- <el-row :gutter="24" class="card-line">
              <el-tag v-for="(item,index) in testData.multiList ":key="index" :type="cardItemClass(item.answered, item.questionId)" @click="switchQuestion(item)">{{ item.sort+1-testData.radioList.length}}</el-tag>
            </el-row> -->
            <!-- <el-row :gutter="24" class="card-line">
              <el-tag :key="tag" v-for="tag in testData.multiList" 
                :disable-transitions="false" type="2"
                @click="switchQuestion(tag)">
                 {{tag}}
              </el-tag>
            </el-row>
          </div>

        </el-card> -->

      <!-- </el-col> -->

      <el-col :span="18" :xs="24" style="margin:0 auto;">

        <el-card class="qu-content" >
          <!-- <p v-if="questionData.questionTitle && questionData.sort < this.radioNum">{{ questionData.sort + 1 }}.{{ questionData.questionTitle }}</p>
          <p v-if="questionData.questionTitle && questionData.sort >= this.radioNum">{{ questionData.sort - this.radioNum + 1 }}.{{ questionData.questionTitle }}</p> -->
          
          <p>{{ questionData[now].questionTitle }}</p>
          <!-- <p>{{ item.questionTitle }}</p> -->
          <div v-if="questionData[now].questionType === 0">
            <el-radio-group v-model="radioValue" @change="getValue()">
              
              <el-radio label="A">A. {{ questionData[now].questionSelectA }} <div style="clear: both" /></el-radio>
              <el-radio label="B">B. {{ questionData[now].questionSelectB }} <div style="clear: both" /></el-radio>
              <el-radio label="C">C. {{ questionData[now].questionSelectC }} <div style="clear: both" /></el-radio>
              <el-radio label="D">D. {{ questionData[now].questionSelectD }} <div style="clear: both" /></el-radio>
              <!-- <el-radio :label="A">A. {{ item.questionSelectA }} </el-radio>
              <el-radio :label="B">B. {{ item.questionSelectB }} </el-radio>
              <el-radio :label="C">C. {{ item.questionSelectC }} </el-radio> 
              <el-radio :label="D">D. {{ item.questionSelectD }} </el-radio> -->
            </el-radio-group>
          </div>
          <div v-if="questionData[now].questionType === 1">

            <el-checkbox-group v-model="multiValue">
              <el-checkbox label="A">A. {{ questionData[now].questionSelectA }} <div style="clear: both" /></el-checkbox>
              <el-checkbox label="B">B. {{ questionData[now].questionSelectB }} <div style="clear: both" /></el-checkbox>
              <el-checkbox label="C">C. {{ questionData[now].questionSelectC }} <div style="clear: both" /></el-checkbox>
              <el-checkbox label="D">D. {{ questionData[now].questionSelectD }} <div style="clear: both" /></el-checkbox>
            </el-checkbox-group>

          </div>
          <div v-if="questionData[now].questionType === 2">

            <el-input v-model="input" placeholder="Please input" />

          </div>
          <div v-if="questionData[now].questionType === 3">
            <el-radio-group v-model="judge">
              <el-radio label="A">A. {{ questionData[now].questionSelectA }} <div style="clear: both" /></el-radio>
              <el-radio label="B">B. {{ questionData[now].questionSelectB }} <div style="clear: both" /></el-radio>
             
            </el-radio-group>
          </div>
        </el-card>

        <div style="margin-top: 20px">
          <el-button v-if="now!= 0" type="primary" icon="el-icon-back" @click="handPrevious()">
            上一题
          </el-button>

          <el-button v-if="now!= questionData.length-1" type="warning" icon="el-icon-right" @click="handNext()">
            下一题
          </el-button>

        </div>

      </el-col>

    </el-row>
  </div>

</template>

<script>
  // import { Loading } from 'element-ui'
  export default {
    name: 'ExamProcess',
    data() {
      return {
        now:0,
        showPrevious: false,
        showNext: true,
        loading: false,
        handleText: '交卷',
        pageLoading: false,
        // 试卷ID
        testId: '',
        // 用户ID
        userId: '',
        radioNum: 0,
        allItem: [],
        // 当前题目内容
        questionData: [{
          questionTitle: '稻香',
          questionSelectA:'1',
          questionSelectB:'1',
          questionSelectC:'1',
          questionSelectD:'1',
          questionType:0,
          questionanswer:''
        }, {
         questionTitle: '稻香',
          questionSelectA:'1',
          questionSelectB:'1',
          questionSelectC:'1',
          questionSelectD:'1',
          questionType:1,
          questionanswer:[]
        }, {
          questionTitle: '稻香',
          questionSelectA:'1',
          questionSelectB:'1',
          questionSelectC:'1',
          questionSelectD:'1',
          questionType:1,
          questionanswer:[]
        }, {
          questionTitle: '稻香',
          questionSelectA:'1',
          questionSelectB:'1',
          questionSelectC:'1',
          questionSelectD:'1',
          questionType:0,
          questionanswer:''
        },
        {
          questionTitle: '光速是多少',
          questionType:2,
          questionanswer:[]
        },
        {
         questionTitle: '稻香',
          questionSelectA:'ture',
          questionSelectB:'false',
          questionType:3,
          questionanswer:''
        }]
          
        
        ,
        
        // 单选选定值
        radioValue: '',
        // 多选选定值
        multiValue: [],
        judge:'',
        input:[],
        // 已答ID
        answeredIds: [],
        min: '10',
        sec: '10',
        UserQQuestionQueryVo: {
          testId: '',
          userQId: '',
          questionList: [],
        }
      }
    },
    created() {
      // const testId = this.$route.params.testId
      // if (typeof testId !== 'undefined') {
      //   this.UserQQuestionQueryVo.testId = testId
      //   this.testId = testId
      //   this.fetchData(testId)
      // }
    },
    methods: {
      // 倒计时
      countdown() {
        const testTimesum = this.testData.testTimesum
        // 强制交卷
        if (testTimesum < 0) {
          this.doHandler()
          return
        }
        // 时
        const min = parseInt(testTimesum / 60 % 60)
        const sec = parseInt(testTimesum % 60)
        this.min = min > 9 ? min : '0' + min
        this.sec = sec > 9 ? sec : '0' + sec
        const that = this
        this.testData.testTimesum -= 1
        setTimeout(function() {
          that.countdown()
        }, 1000)
      },
      
      // 答题卡样式
      // cardItemClass(answered, questionId) {
      //   if (questionId === this.questionData.questionId) {
      //     return 'warning'
      //   }
      //   if (answered) {
      //     return 'success'
      //   }
      //   if (!answered) {
      //     return 'info'
      //   }
      // },
     
      /**
       * 下一题
       */
      handNext() {
        // const index = this.questionData.sort
        // this.handSave(this.allItem[index])
        // this.fetchquestionData(this.allItem[index + 1])
        
        this.now=this.now+1
         
        if(this.questionData[this.now-1].questionType === 0) {
          
           if (this.radioValue !== '') {
             this.questionData[this.now-1].questionanswer = this.radioValue
             this.radioValue = ''
             
           }
         }
         else if(this.questionData[this.now-1].questionType === 3)
         {
           if (this.judge !== '') {
             this.questionData[this.now-1].questionanswer = this.radioValue
             this.judge=''
           }
         }
         else if(this.questionData[this.now-1].questionType === 1)
          {
           if(this.multiValue.length > 0){
            //  this.questionData[this.now-1].questionanswer = this.multiValue.join("")
             this.questionData[this.now-1].questionanswer = this.multiValue
             this.multiValue = []
             
           }
         }
         else
         {
           if(this.input.length>0){
             this.questionData[this.now-1].questionanswer = this.input.join("")
             
             this.input=[]
         }
         }
        if(this.questionData[this.now].questionanswer.length > 0){
        if(this.questionData[this.now].questionType === 0) {
             this.radioValue=this.questionData[this.now].questionanswer
             
           
         }
         else if(this.questionData[this.now].questionType === 3)
         {

             this.judge=this.questionData[this.now].questionanswer
             
           
         }
          else if(this.questionData[this.now].questionType === 1) {      
             this.multiValue = this.questionData[this.now].questionanswer
           
         }
         else
         {
             this.input = this.questionData[this.now].questionanswer.join("")
         }
        }
      },
      /**
       * 上一题
       */
      handPrevious() {
        // const index = this.questionData.sort
        // this.handSave(this.allItem[index])
        // this.fetchquestionData(this.allItem[index - 1])
        if(this.questionData[this.now-1].questionanswer.length > 0){
        if(this.questionData[this.now-1].questionType === 0) {
             this.radioValue=this.questionData[this.now-1].questionanswer
             
           
         }
         else if(this.questionData[this.now-1].questionType === 3)
         {

             this.judge=this.questionData[this.now-1].questionanswer
             
           
         }
          else if(this.questionData[this.now-1].questionType === 1) { 
            // alert(this.questionData[this.now-1].questionanswer)     
            this.multiValue = this.questionData[this.now-1].questionanswer
           
         }
         else
         {
             this.input = this.questionData[this.now-1].questionanswer.join("")
         }
        }
         this.now=this.now-1
      },
      /**
       * 切换题目
       */
      // switchQuestion(item) {
      //   const index = this.questionData.sort
      //   this.handSave(this.allItem[index])
      //   this.fetchquestionData(item)
      // },
      // async doHandler() {
      //   this.handleText = '正在交卷，请等待...'
      //   this.loading = true
      //   this.UserQQuestionQueryVo.questionList = []
      //   this.UserQQuestionQueryVo.questionList.push(this.testData.radioList)
      //   this.UserQQuestionQueryVo.questionList.push(this.testData.multiList)
      //   await this.$http
      //     .post("/test/userQQuestion/commitTest", this.UserQQuestionQueryVo)
      //     .then(() => {
      //       this.$message({
      //         message: '试卷提交成功，即将进入历史考试！',
      //         type: 'success'
      //       })
      //       this.$router.push({name: 'historyExam'})
      //     })
      // },
      // 交卷操作
      // handHandExam() {
      //   const that = this
      //   // 交卷保存答案
      //   this.handSave(this.questionData, function() {
      //     const notAnswered = that.countNotAnswered()
      //     let msg = '确认要交卷吗？'
      //     if (notAnswered > 0) {
      //       msg = '您还有' + notAnswered + '题未作答，确认要交卷吗?'
      //     }
      //     that.$confirm(msg, '提示', {
      //       confirmButtonText: '确定',
      //       cancelButtonText: '取消',
      //       type: 'warning'
      //     }).then(() => {
      //       that.doHandler()
      //     }).catch(() => {
      //       that.$message({
      //         type: 'info',
      //         message: '交卷已取消，您可以继续作答！'
      //       })
      //     })
      //   })
      // },
      // // 保存答案
      // handSave(item, callback) {
      //   if(item.questionType === 0) {
      //     if (this.radioValue !== '') {
      //       item.questionYesanswer = this.radioValue
      //     }
      //   } else {
      //     if(this.multiValue.length > 0){
      //       item.questionYesanswer = this.multiValue.join("")
      //     }
      //   }
      //   if (item.questionYesanswer.length > 0) {
      //     // 加入已答列表
      //     this.questionData.answered = true
      //   }
      //   // 最后一个动作，交卷
      //   if (callback) {
      //     callback()
      //   }
      // },
      // // 试卷详情
      // fetchquestionData(item) {
      //   // 打开
      //   // const loading = Loading.service({
      //   //   text: '拼命加载中',
      //   //   background: 'rgba(0, 0, 0, 0.7)'
      //   // })
      //   this.showPrevious = item.questionId !== this.allItem[0].questionId;
      //   const last = this.allItem.length - 1
      //   this.showNext = item.questionId !== this.allItem[last].questionId;
      //   // 获得详情
      //   this.questionData = item
      //   this.radioValue = ''
      //   this.multiValue = []
      //   if(this.questionData.answered === true) {
      //     if (this.questionData.questionType === 0) {
      //       this.radioValue = this.questionData.questionYesanswer
      //     } else {
      //       this.multiValue = this.questionData.questionYesanswer.split("")
      //     }
      //   }
      //   // 关闭详情
      //   // loading.close()
      // },
      // async fetchData(testId) {
      //   // 用户
      //   const res = await this.$http.get("auth/auth/verifyQ");
      //   this.userId = res.data.data.userId;
      //   this.UserQQuestionQueryVo.userQId = this.userId
      //   // 试卷
      //   await this.$http.get(
      //     "/test/test/findTestById",
      //     {
      //       params: {
      //         testId: testId
      //       }
      //     })
      //     .then(response => {
      //       this.testData = response.data.data
      //       this.testData.testTimesum = this.testData.testTimesum * 60
      //     })
      //   // 题目
      //   await this.$http.get(
      //     "/test/testQuestion/findQuestionsByTestIdAndUserQId",
      //     {
      //       params: {
      //         testId: testId,
      //         userQId: this.userId
      //       }
      //     })
      //     .then(response => {
      //       this.testData.radioList = response.data.data[0]
      //       this.radioNum = this.testData.radioList.length
      //       this.testData.multiList = response.data.data[1]
      //       for (var item of this.testData.radioList.entries()){
      //         item[1].sort = item[0]
      //         item[1].answered = false
      //         this.allItem.push(item[1])
      //       }
      //       for (item of this.testData.multiList.entries()){
      //         item[1].sort = item[0] + this.radioNum
      //         item[1].answered = false
      //         this.allItem.push(item[1])
      //       }
      //     })
      //   await this.$http
      //     .post("test/testUserQ/addTestforUserQ/"+this.testId+"/"+this.userId)
      //   // 获得第一题内容
      //   this.questionData = this.testData.radioList[0]
      //   // 当前选定
      //   this.fetchquestionData(this.questionData)
      //   // 倒计时
      //   this.countdown()
      // },
    }
  }
</script>

<style scoped>
  .qu-content div{
    line-height: 30px;
  }
  .el-checkbox-group label,.el-radio-group label{
    width: 100%;
  }
  .card-title{
    background: #eee;
    line-height: 35px;
    text-align: center;
    font-size: 14px;
  }
  .card-line{
    padding-left: 10px
  }
  .card-line span {
    cursor: pointer;
    margin: 2px;
  }

  .el-radio, .el-checkbox{
    padding: 9px 20px 9px 10px;
    border-radius: 4px;
    border: 1px solid #dcdfe6;
    margin-bottom: 10px;
  }
  .is-checked{
    border: #409eff 1px solid;
  }
  .el-radio img, .el-checkbox img{
    max-width: 200px;
    max-height: 200px;
    border: #dcdfe6 1px dotted;
  }
  
  .el-checkbox__inner {
    display: none;
  }

  .el-radio__inner{
    display: none;
  }

  .el-checkbox__label{
    line-height: 30px;
  }

  .el-radio__label{
    line-height: 30px;
  }
</style>