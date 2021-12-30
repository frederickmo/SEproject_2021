import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";

// 从直接头部import改成在component属性import => es6中提出的路由懒加载机制
// 路由懒加载：在访问该路由时才加载页面，减轻初次访问时加载过多页面造成的等待时间过长

const routes = [
  {
    path: "/home",
    name: "Home",
    component: Home,
    children: [
      {
        path: "/refresh",
        name: "Refresh",
        component: () => import("../components/Refresh.vue"),
      },
      {
        path: "/redirecttohome",
        name: "RedirectToHome",
        component: () => import("../components/RedirectToHome.vue"),
      },

      {
        path: "/home/announcement",
        name: "Announcement",
        component: () => import("../components/Announcement/Announcement.vue"),
      },

      {
        path: "/home/basicinfo",
        name: "BasicInfo",
        component: () => import("../components/BasicInfo/BasicInfo.vue"),
      },
      {
        path: "/home/editbasicinfo",
        name: "EditBasicInfo",
        component: () => import("../components/BasicInfo/EditBasicInfo.vue"),
      },
      {
        path: "/home/security",
        name: "Security",
        component: () => import("../components/BasicInfo/Security.vue"),
      },

      {
        path: "/home/courseresources",
        name: "CourseResources",
        component: () =>
          import("../components/CourseResources/CourseResources.vue"),
      },

      {
        path: "/home/mycourses",
        name: "MyCourses",
        component: () => import("../components/CourseManagement/MyCourses.vue"),
      },
      {
        path: "/home/coursemanagement",
        name: "CourseManagement",
        component: () =>
          import("../components/CourseManagement/CourseManagement.vue"),
      },
      {
        path: "/home/course",
        name: "CoursePage",
        component: () =>
          import("../components/CourseManagement/CoursePage.vue"),
      },
      {
        path: "/home/course/modify",
        name: "CourseModify",
        component: () =>
          import("../components/CourseManagement/CourseModify.vue"),
      },
      {
        path: "/home/course/add",
        name: "Courseadd",
        component: () => import("../components/CourseManagement/Courseadd.vue"),
      },

      {
        path: "/home/mytasks",
        name: "MyTasks",
        component: () => import("../components/TaskManagement/MyTasks.vue"),
      },
      {
        path: "/home/task",
        name: "Task",
        component: () => import("../components/TaskManagement/Task.vue"),
      },
      {
        path: "/home/taskmanagement",
        name: "TaskManagement",
        component: () =>
          import("../components/TaskManagement/TaskManagement.vue"),
      },
      {
        path: "/home/taskmanagement/manage",
        name: "TaskManagePage",
        component: () =>
          import("../components/TaskManagement/TaskManagePage.vue"),
      },
      {
        path: "/home/taskmanagement/modify",
        name: "TaskModify",
        component: () => import("../components/TaskManagement/TaskModify.vue"),
      },

      {
        path: "/home/mygrades",
        name: "MyGrades",
        component: () => import("../components/GradeManagement/MyGrades.vue"),
      },
      {
        path: "/home/grademanagement",
        name: "GradeManagement",
        component: () =>
          import("../components/GradeManagement/GradeManagement.vue"),
      },
      {
        path: "/home/grademanagement/course",
        name: "GradeManagementCoursePage",
        component: () =>
          import("../components/GradeManagement/GradeManagementCoursePage.vue"),
      },
      {
        path: "/home/grademanagement/course/task",
        name: "GradeManagementTaskPage",
        component: () =>
          import("../components/GradeManagement/GradeManagementTaskPage.vue"),
      },

      // AccountManagement / 账户管理
      {
        path: "/home/accountmanagement",
        name: "AccountManagement",
        component: () =>
          import("../components/AccountManagement/AccountManagement.vue"),
      },

      // Forms / 在线表格
      {
        path: "/home/task/onlinetask",
        name: "OnlineTask_InTaskPage",
        component: () =>
          import("../components/Forms/OnlineExperimentForm_InTaskPage.vue"),
      },

      {
        path: "/home/task/complextask",
        name: "ComplexTask_InTaskPage",
        component: () =>
          import("../components/Forms/ComplexExperimentPage_InTaskPage.vue"),
      },
      {
        path: "home/course/onlinetask",
        name: "OnlineTask_InCoursePage",
        component: () =>
          import("../components/Forms/OnlineExperimentForm_InCoursePage.vue"),
      },
      {
        path: "home/course/complextask",
        name: "ComplexTask_InCoursePage",
        component: () =>
          import("../components/Forms/ComplexExperimentPage_InCoursePage.vue"),
      },
      {
        path: "/home/grademanagement/course/task/check",
        name: "OnlineReportCheck",
        component: () =>
          import("../components/Forms/OnlineReportCheckPage.vue"),
      },
    ],
  },
  {
    path: "/auth",
    name: "Auth",
    component: () => import("../views/auth/Auth.vue"),
    children: [
      {
        path: "/auth/login",
        name: "Login",
        component: () => import("../views/auth/Login.vue"),
      },
      {
        path: "/auth/signup",
        name: "Signup",
        component: () => import("../views/auth/Signup.vue"),
      },
      {
        path: "/auth/recoverpassword",
        name: "RecoverPassword",
        component: () => import("../views/auth/RecoverPassword.vue"),
      },
    ],
  },
  {
    path: "/auth/signupsuccess",
    name: "SignupSuccess",
    component: () => import("../components/Notifications/SignupSuccess.vue"),
  },
  // {
  //   path: '/personalinfo',
  //   name: 'PersonalInfo',
  //   component: PersonalInfo
  // },
  // {
  //   path: '/editpersonalinfo',
  //   name: 'EditPersonalInfo',
  //   component: EditPersonalInfo
  // },
  // {
  //   path: '/announcement',
  //   name: 'Announcement',
  //   component: Announcement
  // },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
