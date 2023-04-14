import request from "@/utils/request"

export function test(page) {
    return request({
        url:`/Servers1/findAll/${page}`,
        method:"GET",
        // params:{
        //     page
        // }
    })
}


export function getInfo(list) {
    return request({
        url: '/tSkill/getTaskList',
        method: 'get',
        // params,
        param: {list}
    })
}

export function insertTaskSkill(insert) {
    return request({
        url: '/tSkill/insertTaskSkill',
        method: 'get',
        param: {insert}
    })
}

export function deleteTaskByIdList(remove) {
    return request({
        url: '/tSkill/deleteTaskByIdList',
        method: 'get',
        param: {remove}
    })
}

export function updateTaskById(ud) {
    return request({
        url: '/tSkill/updateTaskById',
        method: 'get',
        param: {ud}
    })
}

export function getRobotList(page){
    return request({
        url: `/tRobot/getRobotList?number=${page.number}&size=${page.size}`,
        method: 'get'
    })
}



// const options = {
//     contentBase: '../dist',
//     hot: true,
//     host: 'localhost',
//     proxy:{
//         '/api':{
//             target:'http://172.17.4.154:8021',
//             changeOrigin: true,
//             pathRewrite:{
//                 '^/api':''
//             }
//         }
//     }
// }


// export function login(info){
//     return{
//         url:'/tSkill/getTaskList',
//         method:"GET",
//         param:info
//     }
// }
