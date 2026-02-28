import axios from 'axios'
import { authStore } from '../stores/auth'

const http = axios.create({
  baseURL: 'http://localhost:8080/api'
})

http.interceptors.request.use((config) => {
  if (authStore.token) {
    config.headers.Authorization = `Bearer ${authStore.token}`
  }
  return config
})

export default http
