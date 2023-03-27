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