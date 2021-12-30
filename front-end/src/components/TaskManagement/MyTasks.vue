<template>
  <div>
    <div style="margin-bottom: 10px">
      <va-breadcrumbs separator=">">
        <va-breadcrumbs-item label="实验管理" disabled />
        <va-breadcrumbs-item label="我的实验" to="/home/mytasks" />
      </va-breadcrumbs>
    </div>
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
      <va-card-title style="font-size: 20px">我的实验项目</va-card-title>
      <va-card-content style="text-align: left">
        <div style="margin-bottom: 15px">
          <va-switch
            style="font-weight: bold"
            v-model="isCalendarView"
            true-inner-label="日历视图"
            false-inner-label="列表视图"
          />
        </div>

        <div v-show="isCalendarView" style="text-align: center">
          <v-calendar
            :masks="masks"
            :attributes="attributes"
            style="width: 95%"
            is-expanded
          >
            <template v-slot:day-content="{ day, attributes }">
              <div
                style="
                  min-height: 60px;
                  font-size: 15px;
                  font-weight: bold;
                  text-align: center;
                "
              >
                <div
                  :style="
                    day == new Date() ? 'color: blue; font-size: 200px' : ''
                  "
                >
                  {{ day.day }}
                </div>
                <div
                  v-for="task in attributes"
                  :key="task.key"
                  style="
                    margin-top: 5px;
                    line-height: 20px;
                    width: 95%;
                    border-radius: 8px;
                  "
                >
                  <a-popover v-show="task.customData.showPopover">
                    <a-button
                      v-show="task.customData.showPopover"
                      type="text"
                      shape="round"
                      style="font-weight: bold"
                      :status="task.customData.buttonStatus"
                      >{{ task.customData.status }}</a-button
                    >
                    <template #content>
                      <div style="text-align: center">
                        <div>
                          {{ task.customData.title }}
                        </div>
                        <div>
                          <a-button
                            shape="round"
                            type="text"
                            @click="
                              toTaskPage(
                                task.customData.taskId,
                                task.customData.type
                              )
                            "
                            >前往</a-button
                          >
                        </div>
                      </div>
                    </template>
                  </a-popover>
                  <div v-show="!task.customData.showPopover">
                    <a-button
                      shape="round"
                      style="
                        font-weight: bold;
                        color: white;
                        background: #6667ab;
                      "
                      type="text"
                      >今日</a-button
                    >
                  </div>
                </div>
              </div>
            </template>
          </v-calendar>
        </div>

        <div v-show="!isCalendarView">
          <div
            style="
              font-size: 20px;
              font-weight: bold;
              color: #777;
              height: 40px;
              line-height: 40px;
            "
          >
            未完成&nbsp;<va-icon color="#28609d" name="announcement" />
          </div>

          <el-table
            :data="
              tasksUnfinishedData.filter(
                (data) =>
                  !searchUnfinished
                  | data.name
                    .toLowerCase()
                    .includes(searchUnfinished.toLowerCase())
              )
            "
          >
            <el-table-column label="项目ID" prop="id" />
            <el-table-column label="项目名" prop="name" />
            <el-table-column label="类型" prop="type" />
            <el-table-column label="截止日期" prop="deadline" />
            <el-table-column align="right">
              <template #header>
                <el-input
                  v-model="searchUnfinished"
                  size="mini"
                  placeholder="请输入"
                />
              </template>
              <template #default="scope">
                <div>
                  <a-button @click="switchToTask(scope.row)">前往</a-button>
                  <!-- <a-button :disabled="scope.row.finished=='未提交'" @click="handleFetchTaskFile(scope.row, scope.$index)">作业</a-button>
                    <a-button style="margin-left: 10px" @click="handleMark(scope.row)">评分</a-button> -->
                </div>
              </template>
            </el-table-column>
          </el-table>

          <!-- <va-card 
              v-for="(task, index) in tasksUnfinished"
              :key="index"
              :color="task[6] ? '#ac9b91' : '#b5c4b1'" 
              gradient
              style="margin-bottom: 10px"
              >
                <va-card-content style="rgb(60, 60, 60); font-weight: bold">
                  <div style="display: flex">
                    <div style="line-height: 36px; width: 33%; font-size: 18px">{{task[4]}}</div>
                    <div style="line-height: 36px; width: 24%; font-size: 15px">{{task[2]}}</div>
                    <div style="line-height: 36px; width: 15%">截止日期：{{task[5] ? task[5] : "暂无"}}</div>
                    <div v-show="isOverdue(task[5])" style="line-height: 36px; width: 18%; color: #e00"><va-icon color="#e00" name="error_outline" />已逾期</div>
                    <div v-show="!isOverdue(task[5])" style="line-height: 36px; width: 18%"/>
                    <div><va-button @click="switchToCourseUnfinished(index)" color="#e0e5df" style="color: rgb(40,40,40)">点击进入</va-button></div>
                  </div>
                </va-card-content>
              </va-card> -->

          <div style="height: 20px" />

          <div
            style="
              font-size: 20px;
              font-weight: bold;
              color: #777;
              height: 40px;
              line-height: 40px;
            "
          >
            已完成&nbsp;<va-icon color="#51b296" name="thumb_up" />
          </div>

          <el-table
            :data="
              tasksFinishedData.filter(
                (data) =>
                  !searchFinished
                  | data.name
                    .toLowerCase()
                    .includes(searchFinished.toLowerCase())
              )
            "
          >
            <el-table-column label="项目ID" prop="id" />
            <el-table-column label="项目名" prop="name" />
            <el-table-column label="类型" prop="type" />
            <el-table-column label="截止日期" prop="deadline" />
            <el-table-column align="right">
              <template #header>
                <el-input
                  v-model="searchFinished"
                  size="mini"
                  placeholder="请输入"
                />
              </template>
              <template #default="scope">
                <div>
                  <a-button @click="switchToTask(scope.row)">前往</a-button>
                  <!-- <a-button :disabled="scope.row.finished=='未提交'" @click="handleFetchTaskFile(scope.row, scope.$index)">作业</a-button>
                    <a-button style="margin-left: 10px" @click="handleMark(scope.row)">评分</a-button> -->
                </div>
              </template>
            </el-table-column>
          </el-table>

          <!-- <va-card 
              v-for="(task, index) in tasksFinished"
              :key="index"
              :color="task[6] ? '#ac9b91' : '#b5c4b1'" 
              gradient
              style="margin-bottom: 10px"
              >
                <va-card-content style="rgb(60, 60, 60); font-weight: bold">
                  <div style="display: flex">
                    <div style="line-height: 36px; width: 33%; font-size: 18px">{{task[4]}}</div>
                    <div style="line-height: 36px; width: 24%; font-size: 15px">{{task[2]}}</div>
                    <div style="line-height: 36px; width: 32%">截止日期：{{task[5] ? task[5] : "暂无"}}</div>
                    <div><va-button @click="switchToCourseFinished(index)" color="#e0e5df" style="color: rgb(40,40,40)">点击进入</va-button></div>
                  </div>
                </va-card-content>
              </va-card> -->
        </div>
      </va-card-content>
    </va-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      masks: {
        weekdays: "WWW",
      },

      studentId: "",

      tasksFinished: [],
      tasksUnfinished: [],

      tasksFinishedData: [],
      tasksUnfinishedData: [],

      isCalendarView: true,

      events: [],

      attributes: [
        {
          key: "today",
          customData: {
            title: "今日",
            status: "",
            class: "",
            type: "",
            taskId: "",
            buttonStatus: "今日",
            showPopover: false,
          },
          dates: new Date(),
        },
      ],

      searchFinished: "",
      searchUnfinished: "",
    };
  },
  mounted() {
    // console.log(this.attributes);
    this.studentId = localStorage.getItem("userId");
    // console.log("studentId: " + this.studentId);

    fetch(
      this.$URL +
        "/task/getAll/finished/deadline/asc?studentId=" +
        this.studentId,
      {
        method: "GET",
        headers: { satoken: localStorage.getItem("token") },
      }
    )
      .then((res) => res.json())
      .then((res) => {
        // console.log("已完成", res);
        this.tasksFinished = res;
        for (let i = 0; i < this.tasksFinished.length; ++i) {
          let attr = {
            key: i + 1,
            customData: {
              title: this.tasksFinished[i][4],
              status: "已完成项目",
              class: "finished",
              type: this.tasksFinished[i][6],
              taskId: this.tasksFinished[i][3],
              buttonStatus: "success",
              showPopover: true,
            },
            dates: new Date(this.tasksFinished[i][5]),
          };
          this.attributes.push(attr);
          let taskFinished = {
            id: this.tasksFinished[i][3],
            name: this.tasksFinished[i][4],
            deadline: this.tasksFinished[i][5]
              ? this.tasksFinished[i][5]
              : "暂无",
            type: this.tasksFinished[i][6] ? "大型" : "小型",
          };
          this.tasksFinishedData.push(taskFinished);
        }
        // console.log(this.tasksFinishedData);
      })
      .then(async () => {
        fetch(
          this.$URL +
            "/task/getAll/unfinished/deadline/asc?studentId=" +
            this.studentId,
          {
            method: "GET",
            headers: { satoken: localStorage.getItem("token") },
          }
        )
          .then((res) => res.json())
          .then((res) => {
            // console.log("未完成", res);
            this.tasksUnfinished = res;

            let proLen = this.attributes.length;

            for (let i = 0; i < this.tasksUnfinished.length; ++i) {
              let taskUnfinished = {
                id: this.tasksUnfinished[i][3],
                name: this.tasksUnfinished[i][4],
                deadline:
                  this.tasksUnfinished[i][5] == undefined
                    ? "暂无"
                    : this.isOverdue(this.tasksUnfinished[i][5])
                    ? this.tasksUnfinished[i][5] + "（已逾期）"
                    : this.tasksUnfinished[i][5],
                type: this.tasksUnfinished[i][6] ? "大型" : "小型",
              };
              this.tasksUnfinishedData.push(taskUnfinished);
              if (
                this.tasksUnfinished[i][5] == null ||
                this.tasksUnfinished[i][5] == undefined ||
                this.tasksUnfinished[i][5] == ""
              ) {
                continue;
              }
              let attr = {
                key: i + proLen + 1,
                customData: {
                  title: this.tasksUnfinished[i][4],
                  status:
                    new Date(this.tasksUnfinished[i][5]) > new Date()
                      ? "未提交项目"
                      : "已逾期项目",
                  class:
                    new Date(this.tasksUnfinished[i][5]) > new Date()
                      ? "normal"
                      : "overdue",
                  type: this.tasksUnfinished[i][6],
                  taskId: this.tasksUnfinished[i][3],
                  buttonStatus:
                    new Date(this.tasksUnfinished[i][5]) > new Date()
                      ? "normal"
                      : "danger",
                  showPopover: true,
                },
                dates: new Date(this.tasksUnfinished[i][5]),
              };
              this.attributes.push(attr);
            }
            // console.log(this.tasksUnfinishedData);
          });
      });
  },
  methods: {
    switchToCourseUnfinished(index) {
      localStorage.setItem("curTaskId", this.tasksUnfinished[index][3]);
      this.$router.push({
        name: this.tasksUnfinished[index][6]
          ? "ComplexTask_InTaskPage"
          : "OnlineTask_InTaskPage",
        params: {
          taskId: this.tasksUnfinished[index][3],
          deadline: this.tasksUnfinished[index][5],
        },
      });
    },
    switchToCourseFinished(index) {
      localStorage.setItem("curTaskId", this.tasksFinished[index][3]);
      this.$router.push({
        name: this.tasksFinished[index][6]
          ? "ComplexTask_InTaskPage"
          : "OnlineTask_InTaskPage",
        params: {
          taskId: this.tasksFinished[index][3],
          deadline: this.tasksFinished[index][5],
        },
      });
    },
    isOverdue(date) {
      /**
       * now的写法用当前时间转日期再转时间来进行比较的原因是
       * 如果直接用当前时间和设定的ddl(只精确到日期)来比较的话，
       * ddl转时间是当天的0点整，但是设定的ddl本身意义应该是到当天结束为止。
       * 所以把当前时间转换为当天0点整再进行比较就能等效上述效果。
       */
      let deadline = new Date(date);
      let now = new Date(new Date().toLocaleDateString());
      if (date == "" || date == undefined || date == null) {
        return false;
      } else if (now <= deadline) {
        return false;
      } else {
        return true;
      }
    },

    toTaskPage(taskId, type) {
      localStorage.setItem("curTaskId", taskId);
      // console.log(taskId, type);
      this.$router.push({
        name: type ? "ComplexTask_InTaskPage" : "OnlineTask_InTaskPage",
        params: {
          taskId: taskId,
        },
      });
    },
    switchToTask(row) {
      // console.log(row);
      localStorage.setItem("curTaskId", row.id);
      this.$router.push({
        name:
          row.type == "大型"
            ? "ComplexTask_InTaskPage"
            : "OnlineTask_InTaskPage",
        params: {
          taskId: row.id,
        },
      });
    },
  },
};
</script>

<style>
.finished {
  background: rgb(71, 207, 162);
}
.overdue {
  background: rgb(226, 80, 55);
}
.normal {
  background: white;
}
</style>
