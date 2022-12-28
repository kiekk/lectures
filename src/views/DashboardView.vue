<script lang="ts" setup>

import {useAuthStore} from "@/stores/auth";
import {onMounted, ref} from "vue";

const auth = useAuthStore()
const token = auth.token
const data = ref('')

onMounted(async () => {
  const response = await fetch('/home', {
    headers: {
      Authorization: `Bearer ${token}`
    }
  })
  data.value = await response.text()
})
</script>

<template>
  <h1 class="text-3xl mb-8">Dashboard</h1>
  <p>{{ data }}</p>
</template>