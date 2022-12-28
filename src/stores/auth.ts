import {defineStore} from "pinia";
import router from "@/router";

export const useAuthStore = defineStore({
  id: 'auth',
  state: () => {
    return {
      user: localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')!) : null,
      token: localStorage.getItem('token') ? JSON.parse(localStorage.getItem('token')!) : null,
      returnUrl: '/',
    }
  },
  actions: {
    async login(username: string, password: string) {
      const response = await fetch('/token', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({username, password})
      })

      if (response.status === 200) {
        const token = await response.text()
        localStorage.setItem('user', JSON.stringify(username))
        localStorage.setItem('token', JSON.stringify(token))
        this.user = username
        this.token = token
        await router.push(this.returnUrl || '/')
      }
    },
    logout() {
      localStorage.removeItem('user')
      localStorage.removeItem('token')
      this.user = null
      this.token = null
      router.push('/login')
    }
  }
})