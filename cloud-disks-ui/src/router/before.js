import router from '@/router/index'
import common from "@/libs/globalFunction/common.js";
import config from "@/config/index.js";

// 路由全局前置守卫
router.beforeEach((to, from, next) => {
    // 检查cookie中的token
    const token = common.getCookies(config.tokenKeyName); // 确保你有一个函数来获取cookie中的token

    if (token) {
        // 如果访问的是登录页，重定向到主页
        if (to.path === '/login') {
            alert('登录成功，自动跳转到首页！');
            next('/file');
        } else {
            // 如果是访问主页或其他需要登录后才能访问的页面，执行正常逻辑
            next(); // 允许路由跳转
        }
    } else {
        // 如果没有token，且尝试访问需要登录的页面，则重定向到登录页
        if (to.path === '/home' || to.path === '/') {
            next('/login');
        } else {
            next(); // 允许其他不需要登录的页面访问
        }
    }
});
