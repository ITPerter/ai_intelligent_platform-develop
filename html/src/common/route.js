import React from 'react';
import MainPage from '../pages/mainPage';
import Login from '../pages/Login';
import { exact } from 'prop-types';
const IntentionList = React.lazy(() => import('../pages/Intention/index.js'));
const IntentionDetail = React.lazy(() => import('../pages/IntentionDetail'));
const RobotDetail = React.lazy(() => import('../pages/RobotDetail'));
const BaseData=React.lazy(()=>import('../pages/BaseData'));
const BaseDataDetail=React.lazy(()=>import('../pages/BaseDataDetail'));

// 新增的
const TaskSkillList = React.lazy(() => import('../pages/TaskSkillList'))
const TaskSkillListDetail = React.lazy(() => import('../pages/TaskSkillListDetail'))


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
  },
  {
    name:'基础资料列表',
    path:'/baseData',
    exact:true,
    component:BaseData,
    children:[
      {
        name:'基础资料详情',
        path:'/baseDataDetail/:number',
        exact:true,
        component:BaseDataDetail,
      }
    ]
  },
  {
    name: '任务型技能列表',
    path:'/taskSkillList',
    exact: true,
    component: TaskSkillList,
    children: [
      {
        name: '任务型技能列表详情',
        path: '/taskSkillListDetail/:number',
        exact:true,
        component:TaskSkillListDetail
      }
    ]
  }
];
