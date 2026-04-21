<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <p>
      For a guide and recipes on how to configure / customize this project,<br>
      check out the
      <a href="https://vitejs.dev/guide/" target="_blank" rel="noopener"
        >vite documentation</a
      >.
    </p>
  </div>
</template>

<script setup>
defineProps({
  msg: String
})
</script>

<style scoped>
h1 {
  font-weight: normal;
}
</style>
