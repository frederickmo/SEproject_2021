<template>
  <div class="app-container">
    <el-row :gutter="24">
      <el-col :span="24">
        <el-card style="margin-bottom: 10px">
          距离考试结束还有：<span style="color: #ff0000"
            >{{ min }}分钟{{ sec }}秒</span
          >
          <el-button
            style="float: right; margin-top: -10px"
            type="primary"
            icon="el-icon-plus"
            :loading="loading"
            @click="handHandExam()"
          >
            {{ handleText }}
          </el-button>
        </el-card>
      </el-col>

      <el-col :span="18" :xs="24" style="margin: 0 auto">
        <el-card
          class="qu-content"
          v-for="(item, index) in questionData"
          :key="index"
        >
          <p>
            {{ index + 1 }}.{{ item.questionTitle }} ({{
              item.questionscore
            }}分)
          </p>
          <div v-if="item.questionType === 0">
            <el-radio-group v-model="item.questionanswer" @change="getValue()">
              <el-radio label="A"
                >A. {{ item.questionSelectA }}
                <div style="clear: both"
              /></el-radio>
              <el-radio label="B"
                >B. {{ item.questionSelectB }}
                <div style="clear: both"
              /></el-radio>
              <el-radio label="C"
                >C. {{ item.questionSelectC }}
                <div style="clear: both"
              /></el-radio>
              <el-radio label="D"
                >D. {{ item.questionSelectD }}
                <div style="clear: both"
              /></el-radio>
            </el-radio-group>
          </div>
          <div v-if="item.questionType === 1">
            <el-checkbox-group v-model="item.questionanswer">
              <el-checkbox label="A"
                >A. {{ item.questionSelectA }}
                <div style="clear: both"
              /></el-checkbox>
              <el-checkbox label="B"
                >B. {{ item.questionSelectB }}
                <div style="clear: both"
              /></el-checkbox>
              <el-checkbox label="C"
                >C. {{ item.questionSelectC }}
                <div style="clear: both"
              /></el-checkbox>
              <el-checkbox label="D"
                >D. {{ item.questionSelectD }}
                <div style="clear: both"
              /></el-checkbox>
            </el-checkbox-group>
          </div>
          <div v-if="item.questionType === 2">
            <el-input
              v-model="item.questionanswer"
              placeholder="Please input"
            />
          </div>
          <div v-if="item.questionType === 3">
            <el-radio-group v-model="item.questionanswer">
              <el-radio label="A"
                >A. {{ item.questionSelectA }}
                <div style="clear: both"
              /></el-radio>
              <el-radio label="B"
                >B. {{ item.questionSelectB }}
                <div style="clear: both"
              /></el-radio>
            </el-radio-group>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
// import { Loading } from 'element-ui'

export default {
  name: "ExamProcess",

  data() {
    return {
      score: 0,
      showPrevious: false,
      showNext: true,
      loading: false,
      handleText: "交卷",
      pageLoading: false,
      // 试卷ID
      testId: "",
      // 用户ID
      userId: "",
      radioNum: 0,
      allItem: [],
      // 当前题目内容
      questionData: [
        {
          questionTitle: "稻香",
          questionSelectA: "1",
          questionSelectB: "1",
          questionSelectC: "1",
          questionSelectD: "1",
          questionType: 0,
          questionanswer: "",
          questionscore: 10,
          rightanswer: "A",
        },
        {
          questionTitle: "稻香",
          questionSelectA: "1",
          questionSelectB: "1",
          questionSelectC: "1",
          questionSelectD: "1",
          questionType: 1,
          questionanswer: [],
          questionscore: 20,
          rightanswer: ["A", "B"],
        },
        {
          questionTitle: "稻香",
          questionSelectA: "1",
          questionSelectB: "1",
          questionSelectC: "1",
          questionSelectD: "1",
          questionType: 1,
          questionanswer: [],
          questionscore: 20,
          rightanswer: ["A", "B"],
        },
        {
          questionTitle: "稻香",
          questionSelectA: "1",
          questionSelectB: "1",
          questionSelectC: "1",
          questionSelectD: "1",
          questionType: 0,
          questionanswer: "",
          questionscore: 10,
          rightanswer: "A",
        },
        {
          questionTitle: "光速是多少",
          questionType: 2,
          questionanswer: [],
          questionscore: 10,
          rightanswer: "12",
        },
        {
          questionTitle: "稻香",
          questionSelectA: "ture",
          questionSelectB: "false",
          questionType: 3,
          questionanswer: "",
          questionscore: 5,
          rightanswer: "A",
        },
      ],

      // 单选选定值
      // radioValue: '',
      // // 多选选定值
      // multiValue: [],
      // judge:'',
      // input:[],
      // 已答ID

      min: "10",
      sec: "10",
      UserQQuestionQueryVo: {
        testId: "",
        userQId: "",
        questionList: [],
      },

      taskId: "",
      courseId: "",
      description: "",
      type: "",
      url: "",
    };
  },
  created() {
    // const testId = this.$route.params.testId
    // if (typeof testId !== 'undefined') {
    //   this.UserQQuestionQueryVo.testId = testId
    //   this.testId = testId
    //   this.fetchData(testId)
    // }
  },
  mounted() {
    this.taskId = this.$route.params.taskId;
    this.courseId = this.$route.params.courseId;
    this.description = this.$route.params.description;
    this.type = this.$route.params.type;
    this.url = this.$route.params.url;

    // console.log("从上个界面传过来的信息：");
    // console.log("taskId: " + this.taskId);
    // console.log("courseId: " + this.courseId);
    // console.log("description: " + this.description);
    // console.log("type: " + this.type);
    // console.log("url: " + this.url);
  },
  methods: {
    // 倒计时
    countdown() {
      const testTimesum = this.testData.testTimesum;
      // 强制交卷
      if (testTimesum < 0) {
        this.doHandler();
        return;
      }
      // 时
      const min = parseInt((testTimesum / 60) % 60);
      const sec = parseInt(testTimesum % 60);
      this.min = min > 9 ? min : "0" + min;
      this.sec = sec > 9 ? sec : "0" + sec;
      const that = this;
      this.testData.testTimesum -= 1;
      setTimeout(function () {
        that.countdown();
      }, 1000);
    },

    handHandExam() {
      for (var item of this.questionData) {
        //     if(item.questionanswer.length!=item.rightanswer.length)
        //       {
        //         continue
        //         }
        // else
        // {
        //   var w=0
        //   for(var i of item.questionanswer)
        //   {
        //     var m=0
        //     for(var it of item.rightanswer)
        //     {
        //       if(it===i)
        //       {
        //         m=1
        //       }
        //     }
        //     if(m==0)
        //       w=1
        //   }
        //   if(w!=1)
        //   this.score=this.score+item.score
        // }
        //    if(MultSelectTest(item.questionanswer,item.rightanswer))
        // item.questionanswer.sort();
        // item.rightanswer.sort();

        if (typeof item.questionanswer === "object") item.questionanswer.sort();

        if (item.questionanswer.toString() === item.rightanswer.toString())
          this.score += item.questionscore;
        // }
        // alert("本次考试分数为："+this.score+"分")
      }
      // alert(this.score)
      alert("本次考试分数为：" + this.score + "分");
      this.score = 0;
      window.location.href = "/home/myexams";
    },
  },
};
</script>

<style scoped>
.qu-content div {
  line-height: 30px;
}
.el-checkbox-group label,
.el-radio-group label {
  width: 100%;
}
.card-title {
  background: #eee;
  line-height: 35px;
  text-align: center;
  font-size: 14px;
}
.card-line {
  padding-left: 10px;
}
.card-line span {
  cursor: pointer;
  margin: 2px;
}

.el-radio,
.el-checkbox {
  padding: 9px 20px 9px 10px;
  border-radius: 4px;
  border: 1px solid #dcdfe6;
  margin-bottom: 10px;
}
.is-checked {
  border: #409eff 1px solid;
}
.el-radio img,
.el-checkbox img {
  max-width: 200px;
  max-height: 200px;
  border: #dcdfe6 1px dotted;
}

.el-checkbox__inner {
  display: none;
}

.el-radio__inner {
  display: none;
}

.el-checkbox__label {
  line-height: 30px;
}

.el-radio__label {
  line-height: 30px;
}
</style>
