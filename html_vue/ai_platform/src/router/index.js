import Vue from 'vue'
import VueRouter from 'vue-router'
import IndexView from '../views/IndexView.vue'

Vue.use(VueRouter)

const routes = [
  // {
  //   path:"/login",
  //   name: 'login',
  //   component:()=>import("@/views/login/index")
  // },
  {
    path: '/',
    name: 'home',
    component: IndexView,
    children:[
      {
        path:"robotList",
        component: () => import("@/views/Robot/RobotList")
      },
      {
        path:"robotDetail/:id",
        component:() => import("@/views/Robot/RobotDetail")
      },
      {
        path:"intentionDetail/:id",
        component: ()=> import("@/views/Intention/intentionDetail")
      },
      {
        path:"intetionList",
        component:()=>import("@/views/Intention/intentionList")
      },
      {
        path:'baseData',
        component:()=>import("@/views/BaseData/baseData")
      },
      {
        path:'questioningSkills',
        component:()=>import("@/views/skill/QuestioningSkills")
      },
      {
        path:'taskSkills',
        component:()=>import("@/views/skill/TaskSkills")
      },
      {
        path:'skillDetails',
        component:()=>import("@/views/skill/Details/skillDetails")
      },
      {
        path:'intentionDetails',
        component:()=>import("@/views/skill/Details/intentionDetails")
      },
      {
        path:'sampleSet',
        component:()=>import("@/views/skill/Details/sampleSet")
      },
      {
        path:'skillCode',
        component:()=>import("@/views/skill/QAskills/skillCode")
      },
      {
        path:'domainDetails',
        component:()=>import("@/views/skill/QAskills/domainDetails")
      },
      {
        path:'tripApplication',
        component:()=>import("@/views/skill/Details/tripApplication")
      },{
        path:'robotIntentDetails',
        component:()=>import("@/views/Robot/robotIntentDetails")
      },

    ]
  },
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
