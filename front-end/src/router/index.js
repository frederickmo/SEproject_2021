import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'

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
                path: '/home/mytasks',
                name: 'MyTasks',
                component: () =>
                    import ('../components/TaskManagement/MyTasks.vue')
            },
            {
                path: '/home/task',
                name: 'Task',
                component: () =>
                    import ('../components/TaskManagement/Task.vue')
            },
            {
                path: '/home/taskmanagement',
                name: 'TaskManagement',
                component: () =>
                    import ('../components/TaskManagement/TaskManagement.vue')
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



            // Forms / 在线表格
            {
                path: '/home/task/onlinetask',
                name: 'OnlineTask',
                component: () => 
                    import ('../components/Forms/OnlineExperimentForm.vue')
            },

            {
                path: '/home/task/complextask',
                name: 'ComplexTask',
                component: () => 
                    import ("../components/Forms/ComplexExperimentPage.vue")
            }
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