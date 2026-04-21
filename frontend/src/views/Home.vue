<template>
  <div class="home-wrapper">
    <!-- 顶部导航 -->
    <header class="top-nav">
      <div class="nav-left">
        <div class="nav-logo">
          <svg viewBox="0 0 64 64" fill="none">
            <path d="M32 8L8 20V44L32 56L56 44V20L32 8Z" stroke="white" stroke-width="2.5" fill="none"/>
            <path d="M32 8L32 56" stroke="white" stroke-width="2" opacity="0.5"/>
            <path d="M8 20L56 44" stroke="white" stroke-width="2" opacity="0.3"/>
            <path d="M56 20L8 44" stroke="white" stroke-width="2" opacity="0.3"/>
          </svg>
          <span class="logo-text">Workflow</span>
        </div>
      </div>

      <div class="nav-right">
        <div class="nav-time">
          <el-icon><Clock /></el-icon>
          <span>{{ currentTime }}</span>
        </div>
        <div class="nav-user" @click="showUserMenu = !showUserMenu">
          <div class="user-avatar">{{ userInitials }}</div>
          <span class="user-name">{{ user.username }}</span>
          <el-icon class="chevron"><DArrowRight /></el-icon>

          <transition name="dropdown">
            <div v-if="showUserMenu" class="user-dropdown" @click.stop>
              <div class="dropdown-header">
                <div class="user-avatar-lg">{{ userInitials }}</div>
                <div class="dropdown-user-info">
                  <span class="name">{{ user.username }}</span>
                  <span class="email">{{ user.email }}</span>
                </div>
              </div>
              <div class="dropdown-divider"></div>
              <div class="dropdown-item" @click="handleLogout">
                <el-icon><SwitchButton /></el-icon>
                <span>退出登录</span>
              </div>
            </div>
          </transition>
        </div>
      </div>
    </header>

    <!-- 主内容区 -->
    <main class="main-content">
      <!-- 欢迎区块 -->
      <div class="welcome-section">
        <div class="welcome-text">
          <h1>下午好，<span class="highlight">{{ user.username }}</span></h1>
          <p>今天是您使用 Workflow 系统的第 <span class="highlight">{{ daysActive }}</span> 天，祝您工作愉快！</p>
        </div>
        <div class="welcome-actions">
          <div class="action-btn primary" @click="showTip = !showTip">
            <el-icon><MagicStick /></el-icon>
            <span>快捷操作</span>
          </div>
        </div>
      </div>

      <!-- 统计卡片 -->
      <div class="stats-grid">
        <div v-for="(stat, index) in stats" :key="stat.label" class="stat-card" :style="{ animationDelay: index * 0.08 + 's' }">
          <div class="stat-icon" :style="{ background: stat.gradient }">
            <el-icon :size="22"><component :is="stat.icon" /></el-icon>
          </div>
          <div class="stat-info">
            <span class="stat-value">{{ stat.value }}</span>
            <span class="stat-label">{{ stat.label }}</span>
          </div>
          <div class="stat-trend" :class="stat.trend > 0 ? 'up' : 'down'">
            <el-icon v-if="stat.trend > 0"><Top /></el-icon>
            <el-icon v-else><Bottom /></el-icon>
            <span>{{ Math.abs(stat.trend) }}%</span>
          </div>
        </div>
      </div>

      <!-- 内容区域 -->
      <div class="content-grid">
        <!-- 快捷操作面板 -->
        <div class="panel shortcuts-panel">
          <div class="panel-header">
            <h3>
              <el-icon><Grid /></el-icon>
              快捷操作
            </h3>
          </div>
          <div class="panel-body">
            <div class="shortcut-grid">
              <div v-for="shortcut in shortcuts" :key="shortcut.label" class="shortcut-item">
                <div class="shortcut-icon" :style="{ background: shortcut.bg }">
                  <el-icon :size="20"><component :is="shortcut.icon" /></el-icon>
                </div>
                <span>{{ shortcut.label }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- 用户信息面板 -->
        <div class="panel user-panel">
          <div class="panel-header">
            <h3>
              <el-icon><User /></el-icon>
              账户信息
            </h3>
          </div>
          <div class="panel-body">
            <div class="user-detail-list">
              <div class="detail-item">
                <span class="detail-label">用户名</span>
                <span class="detail-value">{{ user.username }}</span>
              </div>
              <div class="detail-item">
                <span class="detail-label">邮箱</span>
                <span class="detail-value">{{ user.email }}</span>
              </div>
              <div class="detail-item">
                <span class="detail-label">账户状态</span>
                <span class="detail-value status">
                  <span class="status-dot active"></span>
                  正常
                </span>
              </div>
              <div class="detail-item">
                <span class="detail-label">登录时间</span>
                <span class="detail-value">{{ loginTime }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- 系统公告 -->
        <div class="panel notice-panel">
          <div class="panel-header">
            <h3>
              <el-icon><Bell /></el-icon>
              系统公告
            </h3>
            <span class="badge">3</span>
          </div>
          <div class="panel-body">
            <div class="notice-list">
              <div v-for="notice in notices" :key="notice.id" class="notice-item">
                <div class="notice-dot" :class="notice.type"></div>
                <div class="notice-content">
                  <span class="notice-title">{{ notice.title }}</span>
                  <span class="notice-time">{{ notice.time }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import service from '../utils/request'
import {
  Clock, DArrowRight, SwitchButton, MagicStick, Top, Bottom,
  Grid, User, Bell, Document, Setting, DataLine, Message, Folder, WarnTriangleFilled
} from '@element-plus/icons-vue'

const router = useRouter()
const showUserMenu = ref(false)
const showTip = ref(false)
const currentTime = ref('')
const loginTime = ref('')
const daysActive = ref(1)

let timeInterval = null

const user = ref({
  username: 'Admin',
  email: 'admin@workflow.com'
})

onMounted(() => {
  const storedUser = localStorage.getItem('user')
  if (storedUser) {
    user.value = JSON.parse(storedUser)
  }

  updateTime()
  timeInterval = setInterval(updateTime, 1000)

  loginTime.value = new Date().toLocaleString('zh-CN', {
    year: 'numeric', month: '2-digit', day: '2-digit',
    hour: '2-digit', minute: '2-digit'
  })

  document.addEventListener('click', () => { showUserMenu.value = false })
})

onUnmounted(() => {
  if (timeInterval) clearInterval(timeInterval)
  document.removeEventListener('click', () => {})
})

const updateTime = () => {
  currentTime.value = new Date().toLocaleString('zh-CN', {
    hour: '2-digit', minute: '2-digit', second: '2-digit'
  })
}

const userInitials = computed(() => {
  return user.value.username?.charAt(0).toUpperCase() || 'U'
})

const stats = ref([
  { label: '工作流任务', value: '128', icon: 'DataLine', gradient: 'linear-gradient(135deg, #667eea, #764ba2)', trend: 12 },
  { label: '已完成', value: '96', icon: 'Folder', gradient: 'linear-gradient(135deg, #4facfe, #00f2fe)', trend: 8 },
  { label: '进行中', value: '24', icon: 'MagicStick', gradient: 'linear-gradient(135deg, #f093fb, #f5576c)', trend: -3 },
  { label: '消息通知', value: '8', icon: 'Message', gradient: 'linear-gradient(135deg, #fa709a, #fee140)', trend: 5 }
])

const shortcuts = [
  { label: '新建任务', icon: 'Plus', bg: 'rgba(102, 126, 234, 0.2)' },
  { label: '审批流程', icon: 'Document', bg: 'rgba(79, 172, 254, 0.2)' },
  { label: '数据报表', icon: 'DataLine', bg: 'rgba(240, 147, 251, 0.2)' },
  { label: '系统设置', icon: 'Setting', bg: 'rgba(250, 112, 154, 0.2)' },
  { label: '消息中心', icon: 'Bell', bg: 'rgba(254, 225, 64, 0.2)' },
  { label: '帮助文档', icon: 'WarnTriangleFilled', bg: 'rgba(99, 102, 241, 0.2)' }
]

const notices = ref([
  { id: 1, title: '系统将于本周六凌晨进行维护升级', time: '2小时前', type: 'warning' },
  { id: 2, title: '新版工作流设计器已上线，欢迎体验', time: '1天前', type: 'info' },
  { id: 3, title: '恭喜以下同事获得本周最佳员工称号', time: '3天前', type: 'success' }
])

const handleLogout = async () => {
  try {
    await service.post('/auth/logout')
  } catch (e) {
    // 即使接口失败也继续退出
  }
  localStorage.removeItem('token')
  localStorage.removeItem('user')
  ElMessage.success('已安全退出，期待再次相见！')
  router.push('/login')
}
</script>

<style scoped>
.home-wrapper {
  min-height: 100vh;
  position: relative;
  z-index: 1;
  color: #fff;
}

/* ========== Top Navigation ========== */
.top-nav {
  position: sticky;
  top: 0;
  z-index: 100;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 32px;
  height: 68px;
  background: rgba(15, 12, 41, 0.7);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border-bottom: 1px solid rgba(255, 255, 255, 0.06);
}

.nav-left {
  display: flex;
  align-items: center;
}

.nav-logo {
  display: flex;
  align-items: center;
  gap: 10px;
}

.nav-logo svg {
  width: 32px;
  height: 32px;
}

.logo-text {
  font-size: 18px;
  font-weight: 700;
  letter-spacing: 2px;
  color: #fff;
}

.nav-right {
  display: flex;
  align-items: center;
  gap: 24px;
}

.nav-time {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 13px;
  color: rgba(255, 255, 255, 0.5);
}

.nav-user {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
  padding: 6px 12px;
  border-radius: 12px;
  transition: background 0.3s;
  position: relative;
}

.nav-user:hover {
  background: rgba(255, 255, 255, 0.06);
}

.user-avatar {
  width: 34px;
  height: 34px;
  border-radius: 10px;
  background: linear-gradient(135deg, #667eea, #764ba2);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  font-weight: 700;
  color: #fff;
}

.user-avatar-lg {
  width: 44px;
  height: 44px;
  border-radius: 12px;
  background: linear-gradient(135deg, #667eea, #764ba2);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  font-weight: 700;
  color: #fff;
  flex-shrink: 0;
}

.user-name {
  font-size: 14px;
  font-weight: 500;
  color: rgba(255, 255, 255, 0.85);
}

.chevron {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.4);
  transition: transform 0.3s;
}

.nav-user:hover .chevron {
  transform: rotate(90deg);
}

/* ========== User Dropdown ========== */
.user-dropdown {
  position: absolute;
  top: calc(100% + 8px);
  right: 0;
  width: 260px;
  background: rgba(15, 12, 41, 0.95);
  backdrop-filter: blur(24px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 16px;
  padding: 16px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.5);
  z-index: 200;
}

.dropdown-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
}

.dropdown-user-info {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.dropdown-user-info .name {
  font-size: 15px;
  font-weight: 600;
  color: #fff;
}

.dropdown-user-info .email {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.4);
}

.dropdown-divider {
  height: 1px;
  background: rgba(255, 255, 255, 0.08);
  margin: 12px 0;
}

.dropdown-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px 12px;
  border-radius: 10px;
  cursor: pointer;
  font-size: 14px;
  color: rgba(255, 255, 255, 0.7);
  transition: all 0.3s;
}

.dropdown-item:hover {
  background: rgba(255, 255, 255, 0.06);
  color: #fff;
}

.dropdown-enter-active,
.dropdown-leave-active {
  transition: all 0.25s ease;
}

.dropdown-enter-from,
.dropdown-leave-to {
  opacity: 0;
  transform: translateY(-8px) scale(0.95);
}

/* ========== Main Content ========== */
.main-content {
  padding: 32px 40px;
  max-width: 1400px;
  margin: 0 auto;
}

/* ========== Welcome Section ========== */
.welcome-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 36px;
  animation: fadeSlideUp 0.6s ease-out;
}

.welcome-text h1 {
  font-size: 28px;
  font-weight: 700;
  color: #fff;
  margin-bottom: 6px;
}

.welcome-text p {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.5);
}

.highlight {
  background: linear-gradient(135deg, #667eea, #764ba2);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  font-weight: 700;
}

.welcome-actions {
  display: flex;
  gap: 12px;
}

.action-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  border-radius: 12px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s;
  border: 1px solid rgba(255, 255, 255, 0.1);
  background: rgba(255, 255, 255, 0.05);
  color: rgba(255, 255, 255, 0.8);
}

.action-btn.primary {
  background: linear-gradient(135deg, #667eea, #764ba2);
  border-color: transparent;
  color: #fff;
  box-shadow: 0 4px 20px rgba(102, 126, 234, 0.3);
}

.action-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.3);
}

.action-btn.primary:hover {
  box-shadow: 0 8px 30px rgba(102, 126, 234, 0.4);
}

/* ========== Stats Grid ========== */
.stats-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin-bottom: 28px;
}

.stat-card {
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(16px);
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 18px;
  padding: 22px;
  display: flex;
  align-items: center;
  gap: 16px;
  transition: all 0.3s ease;
  animation: fadeSlideUp 0.5s ease-out both;
  position: relative;
  overflow: hidden;
}

.stat-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 1px;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.15), transparent);
}

.stat-card:hover {
  background: rgba(255, 255, 255, 0.08);
  transform: translateY(-3px);
  border-color: rgba(255, 255, 255, 0.12);
  box-shadow: 0 12px 40px rgba(0, 0, 0, 0.3);
}

.stat-icon {
  width: 50px;
  height: 50px;
  border-radius: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  color: #fff;
}

.stat-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.stat-value {
  font-size: 26px;
  font-weight: 800;
  color: #fff;
  line-height: 1;
}

.stat-label {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.45);
  margin-top: 4px;
}

.stat-trend {
  display: flex;
  align-items: center;
  gap: 2px;
  font-size: 12px;
  font-weight: 600;
  padding: 4px 8px;
  border-radius: 6px;
}

.stat-trend.up {
  color: #4ade80;
  background: rgba(74, 222, 128, 0.1);
}

.stat-trend.down {
  color: #f87171;
  background: rgba(248, 113, 113, 0.1);
}

/* ========== Content Grid ========== */
.content-grid {
  display: grid;
  grid-template-columns: 1fr 340px;
  grid-template-rows: auto auto;
  gap: 20px;
}

.shortcuts-panel {
  grid-row: 1 / 3;
}

/* ========== Panels ========== */
.panel {
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(16px);
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 18px;
  overflow: hidden;
  animation: fadeSlideUp 0.5s ease-out 0.2s both;
}

.panel-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px 24px 16px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.06);
}

.panel-header h3 {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 15px;
  font-weight: 600;
  color: #fff;
}

.panel-body {
  padding: 20px 24px;
}

.badge {
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: #fff;
  font-size: 11px;
  font-weight: 700;
  padding: 2px 8px;
  border-radius: 10px;
}

/* ========== Shortcut Grid ========== */
.shortcut-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 14px;
}

.shortcut-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  padding: 20px 12px;
  border-radius: 14px;
  cursor: pointer;
  transition: all 0.3s;
  border: 1px solid transparent;
}

.shortcut-item:hover {
  background: rgba(255, 255, 255, 0.05);
  border-color: rgba(255, 255, 255, 0.08);
  transform: translateY(-2px);
}

.shortcut-icon {
  width: 48px;
  height: 48px;
  border-radius: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
}

.shortcut-item span {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.6);
  text-align: center;
}

/* ========== User Detail List ========== */
.user-detail-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.detail-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.detail-label {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.4);
}

.detail-value {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.85);
  font-weight: 500;
}

.detail-value.status {
  display: flex;
  align-items: center;
  gap: 6px;
}

.status-dot {
  width: 7px;
  height: 7px;
  border-radius: 50%;
}

.status-dot.active {
  background: #4ade80;
  box-shadow: 0 0 6px rgba(74, 222, 128, 0.6);
}

/* ========== Notice List ========== */
.notice-list {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.notice-item {
  display: flex;
  align-items: flex-start;
  gap: 12px;
  cursor: pointer;
  padding: 10px;
  border-radius: 10px;
  transition: background 0.3s;
}

.notice-item:hover {
  background: rgba(255, 255, 255, 0.03);
}

.notice-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  margin-top: 5px;
  flex-shrink: 0;
}

.notice-dot.warning { background: #fbbf24; box-shadow: 0 0 6px rgba(251, 191, 36, 0.5); }
.notice-dot.info { background: #60a5fa; box-shadow: 0 0 6px rgba(96, 165, 250, 0.5); }
.notice-dot.success { background: #4ade80; box-shadow: 0 0 6px rgba(74, 222, 128, 0.5); }

.notice-content {
  display: flex;
  flex-direction: column;
  gap: 3px;
}

.notice-title {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.75);
  line-height: 1.4;
}

.notice-time {
  font-size: 11px;
  color: rgba(255, 255, 255, 0.3);
}

/* ========== Animations ========== */
@keyframes fadeSlideUp {
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>
