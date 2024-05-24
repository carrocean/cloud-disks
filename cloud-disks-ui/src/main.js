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
import Confirm from './utils/Confirm'
import Utils from './utils/Utils'
//自定义组件
import Table from '@/components/Table.vue'
import Icon from '@/components/Icon.vue'


const app = createApp(App)


app.use(ElementPlus)
app.use(router)
app.component("Table", Table);
app.component("Icon", Icon);

//配置全局组件
app.config.globalProperties.Verify=Verify;
app.config.globalProperties.Message=Message;
app.config.globalProperties.Request=Request;
app.config.globalProperties.Confirm=Confirm;
app.config.globalProperties.Utils=Utils;
app.config.globalProperties.VueCookies=VueCookies;
app.mount('#app')
