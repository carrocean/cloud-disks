import { createRouter, createWebHistory } from 'vue-router'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect:'/login'
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/Login.vue')
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('../views/Register.vue')
    },
    {
      path: '/file',
      name: 'file',
      component: () => import( '../views/File.vue'),
      meta: {
        requireAuth: true, //  当前路由是否需要登录才可进入
        title: '网盘',
        content: {
          description: '图片 文档 视频 音乐 其他 回收站 我的分享'
        }
      }
    },
  ]
})

export default router
