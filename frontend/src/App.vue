<template>
  <el-container class="app-container">
    <el-header class="header">
      <div class="header-content">
        <h1 class="logo">
          <el-icon :size="32"><Workflow /></el-icon>
          Workflow Engine
        </h1>
        <el-menu
          :default-active="activeMenu"
          mode="horizontal"
          @select="handleMenuSelect"
          router
        >
          <el-menu-item index="/">工作流列表</el-menu-item>
          <el-menu-item index="/create">创建工作流</el-menu-item>
        </el-menu>
      </div>
    </el-header>
    
    <el-main class="main">
      <router-view />
    </el-main>
  </el-container>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import { Workflow } from '@element-plus/icons-vue'

const route = useRoute()
const activeMenu = ref('/')

watch(
  () => route.path,
  (newPath) => {
    activeMenu.value = newPath
  },
  { immediate: true }
)

const handleMenuSelect = (index) => {
  activeMenu.value = index
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  min-height: 100vh;
}

.app-container {
  height: 100vh;
  background: #f5f7fa;
}

.header {
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border-bottom: 1px solid rgba(255, 255, 255, 0.3);
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  padding: 0 40px;
}

.header-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100%;
}

.logo {
  font-size: 28px;
  font-weight: 700;
  color: #303133;
  display: flex;
  align-items: center;
  gap: 12px;
}

.logo .el-icon {
  color: #667eea;
}

.el-menu--horizontal {
  border-bottom: none;
}

.el-menu--horizontal.el-menu--popup {
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.main {
  padding: 40px;
  max-width: 1400px;
  margin: 0 auto;
  width: 100%;
}
</style>
