import Vue from 'vue'
import App from './App.vue'
import router from './router'

import elementui from "element-ui"
import 'element-ui/lib/theme-chalk/index.css';
import store from './store'
import axios from "axios"
import JsonExcel from 'vue-json-excel'
import request from '@/utils/request'//添加
Vue.prototype.request=request // 添加


Vue.prototype.HOST="/api"
Vue.config.productionTip = false
Vue.use(elementui)
// 将axios绑定到Vue的原型上
Vue.prototype.$axios = axios
// 导出为excel
Vue.component('downloadExcel', JsonExcel)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
