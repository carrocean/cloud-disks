import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
//引入Element-plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css' 

import '@/assets/icon/iconfont.css'
import '@/assets/base.scss'
//引入VueCookies
import { VueCookies } from 'vue-cookies'


import Verify from './utils/Verify'
import Message from './utils/Message'
import Request from './utils/Request'
import Confirm from './utils/Confirm'

//自定义组件
import Table from '@/components/Table.vue'
import Icon from '@/components/Icon.vue'

import axios from 'axios'

const app = createApp(App)


app.use(ElementPlus)
app.use(router)
app.use(axios)
app.component("Table", Table);
app.component("Icon", Icon);

//配置全局组件
app.config.globalProperties.Verify=Verify;
app.config.globalProperties.Message=Message;
app.config.globalProperties.Request=Request;
app.config.globalProperties.Request=Confirm;
app.config.globalProperties.VueCookies=VueCookies;
app.mount('#app')
