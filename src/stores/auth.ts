import {defineStore} from "pinia";
import router from "@/router";

export const useAuthStore = defineStore({
  id: 'auth',
  state: () => {
    return {
      user: null,
      token: '',
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

      if(response.status === 200) {
        const token = await response.text()
        this.user = username
        this.token = token
        await router.push(this.returnUrl || '/')
      }
    }
  }
})