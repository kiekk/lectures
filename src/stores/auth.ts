import {defineStore} from "pinia";

export const useAuthStore = defineStore({
  id: 'auth',
  state: () => {
    return {
      user: null,
      token: null,
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

      console.log(response.status)
      console.log(response.text())
    }
  }
})