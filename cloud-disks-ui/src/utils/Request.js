import axios from "axios";

import { ElLoading } from "element-plus";
import router from "@/router";

import Message from '../utils/Message';

const contentTypeForm ='application/x-www-form-urlencoded;charset=UTF-8'
const contentTypeJson='application/json'

const responseTypeJson= "json"

let loading=null;
const instance =axios.create({
    baseURL:'http://localhost:30001',
    timeout:10*1000,
})
//请求拦截
instance.interceptors.request.use(
    (config)=> {
        if(config.showLoading) {
            loading=ElLoading.service({
                lock:true,
                text:'加载中......',
                background:'rgba(0,0,0,0.7)',
            });
        }
        return config;
    },
    (error)=> {
        if(config.showLoading && loading) {
            loading.close();
        }
        Message.error("请求发送失败");
        return Promise.reject("请求发送失败");
    }
);
//请求后拦截器
instance.interceptors.response.use(
    (res) => {
        return res.data;
    },
    (error) => {
        if(error.config.showLoading&&loading) {
            loading.close();
        }
        return Promise.reject({showError:true,msg:"网络异常"});
    }
);

export function request(config) {
    return instance(config);
}