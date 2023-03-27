import Vue from 'vue'
import App from './App.vue'
import router from './router'

import elementui from "element-ui"
import 'element-ui/lib/theme-chalk/index.css';
import store from './store'
import axios from "axios"


Vue.config.productionTip = false
Vue.use(elementui)
// 将axios绑定到Vue的原型上
Vue.prototype.axios = axios

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
