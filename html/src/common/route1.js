import React from 'react';
import MainPage from '../pages/mainPage';
import Login from '../pages/Login';
const IntentionList = React.lazy(() => import('../pages/Intention/index.js'));
const IntentionDetail = React.lazy(() => import('../pages/IntentionDetail'));
const RobotDetail = React.lazy(() => import('../pages/RobotDetail'));
export default [
  {
    name: '机器人列表',
    path: '/mainPage',
    component: MainPage,
    exact: true,
    children: [
      {
        name: '机器人详情',
        path: '/robotDetail/:id',
        exact: true,
        component: RobotDetail,
      },
    ],
  },
  {
    name: '意图列表',
    path: '/intentionList',
    component: IntentionList,
    exact: true,
    children: [
      {
        name: '意图详情',
        exact: true,
        path: '/intentionDetail/:id',
        component: IntentionDetail,
      },
    ],
  },{
    name:'用户登录',
    path:'/userLogin',
    component:Login,
    exact:true,
  }
];
