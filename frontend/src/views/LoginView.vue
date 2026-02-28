<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import http from '../api/http'
import { authStore } from '../stores/auth'

const router = useRouter()
const form = reactive({ username: '', password: '' })
const error = ref('')

const submit = async () => {
  error.value = ''
  try {
    const { data } = await http.post('/auth/login', form)
    authStore.setAuth(data)
    router.push('/')
  } catch (e) {
    error.value = e.response?.data?.message || '登录失败'
  }
}
</script>

<template>
  <div class="container">
    <div class="card">
      <h2>任务管理平台登录</h2>
      <p class="hint">默认管理员：admin / admin123</p>
      <input v-model="form.username" placeholder="用户名" />
      <input v-model="form.password" type="password" placeholder="密码" />
      <button @click="submit">登录</button>
      <p class="error" v-if="error">{{ error }}</p>
    </div>
  </div>
</template>
