import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import LogIn from '../views/auth/Login.vue'
import SignUp from '../views/auth/Signup.vue'
import Auth from '../views/auth/Auth.vue'
import RecoverPassword from '../views/auth/RecoverPassword.vue'

import BasicInfo from '../components/BasicInfo/BasicInfo.vue'
import EditBasicInfo from '../components/BasicInfo/EditBasicInfo.vue'

import Announcement from '../components/Announcement/Announcement.vue'

import CourseResources from '../components/CourseResources/CourseResources.vue'

import MyCourses from '../components/CourseManagement/MyCourses.vue'
import CourseManagement from '../components/CourseManagement/CourseManagement.vue'

import MyExams from '../components/ExamManagement/MyExams.vue'
import ExamManagement from '../components/ExamManagement/ExamManagement.vue'

import MyGrades from '../components/GradeManagement/MyGrades.vue'
import GradeManagement from '../components/GradeManagement/GradeManagement.vue'

import Information from '../components/Information.vue'

const routes = [
  {
    path: '/home',
    name: 'Home',
    component: Home,
    children: [
      {
        path: '/home/announcement',
        name: 'Announcement',
        component: Announcement
      },
      {
        path: '/home/basicinfo',
        name: 'BasicInfo',
        component: BasicInfo
      },
      {
        path: '/home/editbasicinfo',
        name: 'EditBasicInfo',
        component: EditBasicInfo
      },
      {
        path: '/home/courseresources',
        name: 'CourseResources',
        component: CourseResources
      },
      {
        path: '/home/mycourses',
        name: 'MyCourses',
        component: MyCourses
      },
      {
        path: '/home/coursemanagement',
        name: 'CourseManagement',
        component: CourseManagement
      },
      {
        path: '/home/myexams',
        name: 'MyExams',
        component: MyExams
      },
      {
        path: '/home/exammanagement',
        name: 'ExamManagement',
        component: ExamManagement
      },
      {
        path: '/home/mygrades',
        name: 'MyGrades',
        component: MyGrades
      },
      {
        path: '/home/grademanagement',
        name: 'GradeManagement',
        component: GradeManagement
      },
    ]
  },
  {
    path: '/auth',
    name: 'Auth',
    component: Auth
  },
  {
    path: '/login',
    name: 'Login',
    component: LogIn
  },
  {
    path: '/signup',
    name: 'Signup',
    component: SignUp
  },
  {
    path: '/recoverpassword',
    name: 'RecoverPassword',
    component: RecoverPassword
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
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
  {
    path: '/information',
    name: 'Information',
    component: Information
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
