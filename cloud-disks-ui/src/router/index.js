import { createRouter, createWebHistory } from 'vue-router'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    /*{
      path: '/',
      name: 'home',
      component: HomeView
    },*/
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
      path: '/',
      redirect:'/Framework'
    },
    {
      path: '/Framework',
      name: 'Framework',
      component: () => import("../views/Framework.vue"),
      children:[
        {
          path:'/',
          redirect:"/main/all"
        },
        {
          path:"/main/:category",
          name:'首页',
          meta:{
            needLogin: true,
            menuCode:"main"
          },
          component:() => import("../views/main/Main.vue")
        },
        
        {
          path:"/recycle",
          name:'回收站',
          meta:{
            needLogin: true,
            menuCode:"recycle"
          },
          component:() => import("../views/recycle/Recycle.vue")
        },
        
      ]
    }
  ]
})

export default router
