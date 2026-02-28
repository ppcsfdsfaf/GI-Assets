export const authStore = {
  token: localStorage.getItem('token') || '',
  username: localStorage.getItem('username') || '',
  role: localStorage.getItem('role') || '',
  setAuth(data) {
    this.token = data.token
    this.username = data.username
    this.role = data.role
    localStorage.setItem('token', data.token)
    localStorage.setItem('username', data.username)
    localStorage.setItem('role', data.role)
  },
  clear() {
    this.token = ''
    this.username = ''
    this.role = ''
    localStorage.clear()
  }
}
