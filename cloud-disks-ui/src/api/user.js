//导入创建好的axios实例
import { request } from '@/utils/Request.js';

//导出login方法，供其它地方使用
export function login(data) {
    return request({
        url: '/login',
        method: 'post',
        data,
    })
}

export function register(data) {
    return request({
        url: '/register',
        method: 'post',
        data,
    })
}
