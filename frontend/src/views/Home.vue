<template>
  <div class="home-container">
    <el-header class="header">
      <div class="header-left">
        <span class="logo">工作流系统</span>
      </div>
      <div class="header-right">
        <el-dropdown>
          <span class="user-info">
            <el-icon><User /></el-icon>
            {{ user.username }}
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="handleLogout">
                退出登录
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </el-header>
    
    <el-main class="main">
      <div class="welcome-card">
        <el-card shadow="never">
          <h2 class="welcome-title">欢迎回来！</h2>
          <p class="welcome-desc">这是一个基于 Spring Boot + Vue 的简单登录示例系统</p>
          <div class="user-info">
            <p><strong>用户名：</strong>{{ user.username }}</p>
            <p><strong>邮箱：</strong>{{ user.email }}</p>
            <p><strong>登录时间：</strong>{{ currentTime }}</p>
          </div>
        </el-card>
      </div>
    </el-main>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()
const user = ref({})
const currentTime = ref('')

onMounted(() => {
  const storedUser = localStorage.getItem('user')
  if (storedUser) {
    user.value = JSON.parse(storedUser)
  }
  currentTime.value = new Date().toLocaleString('zh-CN')
})

const handleLogout = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('user')
  ElMessage.success('已退出登录')
  router.push('/login')
}
</script>

<style scoped>
.home-container {
  min-height: 100vh;
  background-color: #f5f7fa;
}

.header {
  background-color: #409eff;
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.header-left .logo {
  font-size: 20px;
  font-weight: bold;
}

.header-right .user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  color: white;
}

.main {
  padding: 40px;
}

.welcome-card {
  max-width: 600px;
  margin: 0 auto;
}

.welcome-title {
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 16px;
  color: #333;
}

.welcome-desc {
  font-size: 16px;
  color: #666;
  margin-bottom: 32px;
}

.user-info {
  padding: 20px;
  background-color: #f5f7fa;
  border-radius: 6px;
  line-height: 2;
  color: #333;
}

.user-info p {
  margin: 8px 0;
}

</style>
