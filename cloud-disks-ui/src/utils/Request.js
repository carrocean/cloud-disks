import axios from "axios";

import Message from '../utils/Message';
import common from "@/libs/globalFunction/common.js";
import globalConfig from "@/config/index.js";

const contentTypeForm ='application/x-www-form-urlencoded;charset=UTF-8'
const contentTypeJson='application/json'

const responseTypeJson= "json"

let loading=null;
const instance =axios.create({
    baseURL:'http://carrocean.top:30001',
    timeout:10*1000,
})
//请求拦截
instance.interceptors.request.use(
    (config)=> {
        config.headers['token'] = common.getCookies(globalConfig.tokenKeyName)
        return config;
    },
    (error)=> {
        Message.error("error");
        return Promise.reject("error");
    }
);
//请求后拦截器
instance.interceptors.response.use(
    (res) => {
        return res.data;
    },
    (error) => {
        return Promise.reject({showError:true,msg:"网络异常"});
    }
);

export function request(config) {
    return instance(config);
}