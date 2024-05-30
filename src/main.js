import { createApp } from 'vue'
import App from './App.vue'
import router from './router' // 引用路由配置文件

createApp(App)
  .use(router) // 使用路由配置
  .mount('#app')
