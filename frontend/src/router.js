import { createRouter, createWebHistory } from 'vue-router'
import LoginView from './views/LoginView.vue'
import DashboardView from './views/DashboardView.vue'
import { authStore } from './stores/auth'

const routes = [
  { path: '/login', component: LoginView },
  { path: '/', component: DashboardView }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to) => {
  if (to.path !== '/login' && !authStore.token) {
    return '/login'
  }
  if (to.path === '/login' && authStore.token) {
    return '/'
  }
})

export default router
