// axios 请求配置文件
import axios from 'axios'

const service = axios.create({
    baseURL:"http://localhost:8021/",
    // method:"GET",
})

// 请求拦截器
service.interceptors.request.use(
    function (config) {
        // 在发送请求之前做些什么
        console.log("请求拦截");
        return config;
    },
    function (error) {
        // 对请求错误做些什么
        return Promise.reject(error);
    }
)

// // 响应拦截器
service.interceptors.response.use(
    // 对响应的数据做什么操作
    function(response) {
        return response
    },
    // 对相应错误做什么
    function(error){
        return error
    }
)

export default service