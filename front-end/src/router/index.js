import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
// import LogIn from '../views/auth/Login.vue'
// import SignUp from '../views/auth/Signup.vue'
// import Auth from '../views/auth/Auth.vue'
// import RecoverPassword from '../views/auth/RecoverPassword.vue'

// import BasicInfo from '../components/BasicInfo/BasicInfo.vue'
// import EditBasicInfo from '../components/BasicInfo/EditBasicInfo.vue'

// import Announcement from '../components/Announcement/Announcement.vue'

// import CourseResources from '../components/CourseResources/CourseResources.vue'

// import MyCourses from '../components/CourseManagement/MyCourses.vue'
// import CourseManagement from '../components/CourseManagement/CourseManagement.vue'

// import MyExams from '../components/ExamManagement/MyExams.vue'
// import ExamManagement from '../components/ExamManagement/ExamManagement.vue'

// import MyGrades from '../components/GradeManagement/MyGrades.vue'
// import GradeManagement from '../components/GradeManagement/GradeManagement.vue'

// 从直接头部import改成在component属性import => es6中提出的路由懒加载机制
// 路由懒加载：在访问该路由时才加载页面，减轻初次访问时加载过多页面造成的等待时间过长

import Information from '../components/Information.vue'

const routes = [{
        path: '/home',
        name: 'Home',
        component: Home,
        children: [{
                path: '/home/announcement',
                name: 'Announcement',
                component: () =>
                    import ('../components/Announcement/Announcement.vue')
            },



            {
                path: '/home/basicinfo',
                name: 'BasicInfo',
                component: () =>
                    import ('../components/BasicInfo/BasicInfo.vue')
            },
            {
                path: '/home/editbasicinfo',
                name: 'EditBasicInfo',
                component: () =>
                    import ('../components/BasicInfo/EditBasicInfo.vue')
            },



            {
                path: '/home/courseresources',
                name: 'CourseResources',
                component: () =>
                    import ('../components/CourseResources/CourseResources.vue')
            },




            {
                path: '/home/mycourses',
                name: 'MyCourses',
                component: () =>
                    import ('../components/CourseManagement/MyCourses.vue')
            },
            {
                path: '/home/coursemanagement',
                name: 'CourseManagement',
                component: () =>
                    import ('../components/CourseManagement/CourseManagement.vue')
            },
            {
                path: '/home/course',
                name: 'CoursePage',
                component: () => 
                    import ('../components/CourseManagement/CoursePage.vue')
            },


            
            {
                path: '/home/myexams',
                name: 'MyExams',
                component: () =>
                    import ('../components/ExamManagement/MyExams.vue')
            },
            {
                path: '/home/exam',
                name: 'Exam',
                component: () =>
                    import ('../components/ExamManagement/Exam.vue')
            },
            {
                path: '/home/exammanagement',
                name: 'ExamManagement',
                component: () =>
                    import ('../components/ExamManagement/ExamManagement.vue')
            },



            {
                path: '/home/mygrades',
                name: 'MyGrades',
                component: () =>
                    import ('../components/GradeManagement/MyGrades.vue')
            },
            {
                path: '/home/grademanagement',
                name: 'GradeManagement',
                component: () =>
                    import ('../components/GradeManagement/GradeManagement.vue')
            },
        ]
    },
    {
        path: '/auth',
        name: 'Auth',
        component: () =>
            import ('../views/auth/Auth.vue'),
        children: [{
                path: '/auth/login',
                name: 'Login',
                component: () =>
                    import ('../views/auth/Login.vue')
            },
            {
                path: '/auth/signup',
                name: 'Signup',
                component: () =>
                    import ('../views/auth/Signup.vue')
            },
            {
                path: '/auth/recoverpassword',
                name: 'RecoverPassword',
                component: () =>
                    import ('../views/auth/RecoverPassword.vue')
            },
        ]
    },
    {
        path: '/about',
        name: 'About',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () =>
            import ( /* webpackChunkName: "about" */ '../views/About.vue')
    },
    {
        path: '/auth/signupsuccess',
        name: 'SignupSuccess',
        component: () =>
            import ('../components/Notifications/SignupSuccess.vue')
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