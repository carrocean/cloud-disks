import { request } from '@/utils/Request.js';

// 接口配置项
var prefix = '/api/cloud/disks/recycle/';
var managerUrl = {
    recycleList: prefix + 'recycleList',
    recycleDelete: prefix + 'recycleDelete',
    recycleRecover: prefix + 'recycleRecover'
}

export function recycleList(data) {
    return request({
        url: managerUrl.recycleList,
        method: 'post',
        data:data,
    })
}

export function recycleDelete(data) {
    return request({
        url: managerUrl.recycleDelete,
        method: 'delete',
        data:data,
    })
}

export function recycleRecover(data) {
    return request({
        url: managerUrl.recycleRecover,
        method: 'post',
        data:data,
    })
}