import Vue from 'vue'
import VueRouter from 'vue-router'
import IndexView from '../views/IndexView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path:"/login",
    component:()=>import("@/views/Login_")
  },
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
      }
    ]
  },
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
