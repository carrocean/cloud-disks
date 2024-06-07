//导入创建好的axios实例
import { request } from '@/utils/Request.js';

// 接口配置项
var prefix = '/api/cloud/disks/user/';
var managerUrl = {
    login: prefix + 'login',
    register: prefix + 'register',
    checkUserLoginInfo: prefix + 'checkUserLoginInfo'
}

export function login(data) {
    return request({
        url: managerUrl.login,
        method: 'post',
        data:data,
    })
}

export function register(data) {
    return request({
        url: managerUrl.register,
        method: 'post',
        data:data,
    })
}

export function checkUserLoginInfo() {
    return request({
        url: managerUrl.checkUserLoginInfo,
        method: 'get',
    })
}
