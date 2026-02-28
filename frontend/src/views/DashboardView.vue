<script setup>
import { onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import http from '../api/http'
import { authStore } from '../stores/auth'

const router = useRouter()
const tasks = ref([])
const users = ref([])
const message = ref('')
const taskForm = reactive({ title: '', description: '', assigneeId: '' })
const userForm = reactive({ username: '', password: '', role: 'USER' })
const statuses = ['NEW', 'IN_PROGRESS', 'REVIEW', 'DONE', 'REJECTED']

const load = async () => {
  const taskResp = await http.get('/tasks')
  tasks.value = taskResp.data

  if (authStore.role === 'ADMIN') {
    const userResp = await http.get('/users')
    users.value = userResp.data
  }
}

const logout = () => {
  authStore.clear()
  router.push('/login')
}

const createTask = async () => {
  await http.post('/tasks', { ...taskForm, assigneeId: Number(taskForm.assigneeId) })
  Object.assign(taskForm, { title: '', description: '', assigneeId: '' })
  message.value = '任务发放成功'
  load()
}

const updateStatus = async (taskId, status) => {
  await http.patch(`/tasks/${taskId}/status`, { status })
  message.value = '任务状态已更新'
  load()
}

const transferTask = async (taskId, assigneeId) => {
  await http.patch(`/tasks/${taskId}/transfer`, { assigneeId: Number(assigneeId) })
  message.value = '任务已流转'
  load()
}

const createUser = async () => {
  await http.post('/users', userForm)
  Object.assign(userForm, { username: '', password: '', role: 'USER' })
  message.value = '用户创建成功'
  load()
}

onMounted(load)
</script>

<template>
  <div class="dashboard">
    <header>
      <h2>欢迎，{{ authStore.username }}（{{ authStore.role }}）</h2>
      <button @click="logout">退出</button>
    </header>

    <p class="success" v-if="message">{{ message }}</p>

    <section v-if="authStore.role === 'ADMIN'" class="panel">
      <h3>管理员 - 发放任务</h3>
      <input v-model="taskForm.title" placeholder="任务标题" />
      <textarea v-model="taskForm.description" placeholder="任务描述" />
      <select v-model="taskForm.assigneeId">
        <option disabled value="">选择指派用户</option>
        <option v-for="u in users.filter((x) => x.role === 'USER')" :key="u.id" :value="u.id">{{ u.username }}</option>
      </select>
      <button @click="createTask">创建任务</button>
    </section>

    <section v-if="authStore.role === 'ADMIN'" class="panel">
      <h3>管理员 - 用户管理</h3>
      <input v-model="userForm.username" placeholder="用户名" />
      <input v-model="userForm.password" placeholder="密码" />
      <select v-model="userForm.role">
        <option value="USER">普通用户</option>
        <option value="ADMIN">管理员</option>
      </select>
      <button @click="createUser">新建用户</button>
    </section>

    <section class="panel">
      <h3>任务列表</h3>
      <table>
        <thead>
          <tr>
            <th>ID</th><th>标题</th><th>描述</th><th>状态</th><th>负责人</th><th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="task in tasks" :key="task.id">
            <td>{{ task.id }}</td>
            <td>{{ task.title }}</td>
            <td>{{ task.description }}</td>
            <td>{{ task.status }}</td>
            <td>{{ task.assigneeName || '-' }}</td>
            <td>
              <select @change="updateStatus(task.id, $event.target.value)" :value="task.status">
                <option v-for="status in statuses" :key="status" :value="status">{{ status }}</option>
              </select>
              <template v-if="authStore.role === 'ADMIN'">
                <select @change="transferTask(task.id, $event.target.value)">
                  <option disabled selected value="">流转给...</option>
                  <option v-for="u in users.filter((x) => x.role === 'USER')" :key="u.id" :value="u.id">{{ u.username }}</option>
                </select>
              </template>
            </td>
          </tr>
        </tbody>
      </table>
    </section>
  </div>
</template>
