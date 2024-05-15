
import {request} from '@/api/request.js'

export function loginApi(data) {
    return request({
        url: '/login',
        method: 'post',
        data,
    })
}

export function registerApi(data) {
    return request({
        url: '/register',
        data
    })
}

