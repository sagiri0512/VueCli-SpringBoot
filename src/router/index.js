import {
    createRouter,
    createWebHashHistory
} from 'vue-router'
import MailLogin from '../components/MailLogin.vue'
import UserInfoLoginVue from '../components/UserInfoLogin.vue'

const routes = [
    {
        path: '/', // 设置默认路由的路径为根路径
        redirect: '/UserInfoLoginVue' // 将根路径重定向到 '/MailLogin'
    },
    {
        path: '/MailLogin',
        name: 'MailLogin',
        component: MailLogin
    },
    {
        path: '/UserInfoLoginVue',
        name: 'UserInfoLoginVue',
        component: UserInfoLoginVue
    },
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router