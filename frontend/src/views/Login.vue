<template>
  <div class="login-wrapper">
    <!-- Logo 区域 -->
    <div class="logo-section">
      <div class="logo-icon">
        <svg viewBox="0 0 64 64" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path d="M32 8L8 20V44L32 56L56 44V20L32 8Z" stroke="white" stroke-width="2.5" fill="none"/>
          <path d="M32 8L32 56" stroke="white" stroke-width="2" opacity="0.5"/>
          <path d="M8 20L56 44" stroke="white" stroke-width="2" opacity="0.3"/>
          <path d="M56 20L8 44" stroke="white" stroke-width="2" opacity="0.3"/>
        </svg>
      </div>
      <h1 class="app-name">Workflow</h1>
      <p class="app-tagline">智能工作流引擎</p>
    </div>

    <!-- 登录卡片 -->
    <div class="login-card">
      <div class="card-glow"></div>
      <div class="card-inner">
        <div class="card-header">
          <h2>欢迎回来</h2>
          <p>请登录您的账户</p>
        </div>

        <el-form
          ref="loginFormRef"
          :model="loginForm"
          :rules="loginRules"
          class="login-form"
          @keyup.enter="handleLogin"
          size="large"
        >
          <div class="form-item-wrapper">
            <el-form-item prop="username" class="form-item">
              <div class="input-wrapper">
                <el-icon class="input-icon"><User /></el-icon>
                <el-input
                  v-model="loginForm.username"
                  placeholder="用户名 / Username"
                  prefix-icon="User"
                  clearable
                  @focus="focusedField = 'username'"
                  @blur="focusedField = ''"
                />
              </div>
            </el-form-item>

            <el-form-item prop="password" class="form-item">
              <div class="input-wrapper">
                <el-icon class="input-icon"><Lock /></el-icon>
                <el-input
                  v-model="loginForm.password"
                  type="password"
                  placeholder="密码 / Password"
                  prefix-icon="Lock"
                  show-password
                  clearable
                  @focus="focusedField = 'password'"
                  @blur="focusedField = ''"
                />
              </div>
            </el-form-item>
          </div>

          <div class="form-options">
            <el-checkbox v-model="rememberMe">记住我</el-checkbox>
            <span class="forgot-link">忘记密码？</span>
          </div>

          <el-button
            type="primary"
            class="login-btn"
            :loading="loading"
            :class="{ 'btn-loading': loading }"
            @click="handleLogin"
          >
            <span v-if="!loading" class="btn-content">
              <span>登 录</span>
              <el-icon class="btn-arrow"><ArrowRight /></el-icon>
            </span>
            <span v-else class="loading-dots">
              <span></span><span></span><span></span>
            </span>
          </el-button>
        </el-form>

        <div class="card-footer">
          <span>还没有账户？</span>
          <span class="signup-link">立即注册</span>
        </div>
      </div>
    </div>

    <!-- 底部信息 -->
    <div class="footer-info">
      <p>Workflow System v1.0 · 高效 · 智能 · 可靠</p>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { User, Lock, ArrowRight } from '@element-plus/icons-vue'

const router = useRouter()
const loginFormRef = ref(null)
const loading = ref(false)
const rememberMe = ref(false)
const focusedField = ref('')

const loginForm = reactive({
  username: 'admin',
  password: '123456'
})

const loginRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
  ]
}

const handleLogin = () => {
  loginFormRef.value.validate((valid) => {
    if (valid) {
      loading.value = true
      setTimeout(() => {
        // 模拟登录
        const mockResponse = {
          token: 'mock-jwt-token-' + Date.now(),
          id: 1,
          username: loginForm.username,
          email: loginForm.username + '@workflow.com'
        }
        localStorage.setItem('token', mockResponse.token)
        localStorage.setItem('user', JSON.stringify({
          id: mockResponse.id,
          username: mockResponse.username,
          email: mockResponse.email
        }))
        ElMessage.success('登录成功，欢迎回来！')
        router.push('/')
        loading.value = false
      }, 1200)
    }
  })
}
</script>

<style scoped>
.login-wrapper {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px;
  position: relative;
  z-index: 1;
}

/* ========== Logo Section ========== */
.logo-section {
  text-align: center;
  margin-bottom: 40px;
  animation: slideDown 0.6s ease-out;
}

.logo-icon {
  width: 72px;
  height: 72px;
  margin: 0 auto 16px;
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.6), rgba(118, 75, 162, 0.6));
  border-radius: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.15);
  box-shadow: 0 8px 32px rgba(102, 126, 234, 0.3);
}

.logo-icon svg {
  width: 44px;
  height: 44px;
}

.app-name {
  font-size: 32px;
  font-weight: 800;
  color: #fff;
  letter-spacing: 4px;
  text-transform: uppercase;
  margin-bottom: 6px;
  text-shadow: 0 2px 20px rgba(102, 126, 234, 0.5);
}

.app-tagline {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.6);
  letter-spacing: 2px;
}

/* ========== Login Card ========== */
.login-card {
  width: 420px;
  position: relative;
  animation: slideUp 0.6s ease-out 0.1s both;
}

.card-glow {
  position: absolute;
  inset: -2px;
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.5), rgba(118, 75, 162, 0.5), rgba(102, 126, 234, 0.3));
  border-radius: 24px;
  filter: blur(20px);
  z-index: -1;
  animation: glowPulse 4s ease-in-out infinite;
}

@keyframes glowPulse {
  0%, 100% { opacity: 0.6; }
  50% { opacity: 1; }
}

.card-inner {
  background: rgba(255, 255, 255, 0.08);
  backdrop-filter: blur(24px);
  -webkit-backdrop-filter: blur(24px);
  border: 1px solid rgba(255, 255, 255, 0.12);
  border-radius: 24px;
  padding: 40px 36px;
  box-shadow:
    0 8px 32px rgba(0, 0, 0, 0.3),
    inset 0 1px 0 rgba(255, 255, 255, 0.1);
}

.card-header {
  text-align: center;
  margin-bottom: 36px;
}

.card-header h2 {
  font-size: 26px;
  font-weight: 700;
  color: #fff;
  margin-bottom: 8px;
}

.card-header p {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.5);
}

/* ========== Form ========== */
.login-form {
  margin-top: 0;
}

.form-item-wrapper {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.form-item {
  margin-bottom: 0;
}

:deep(.el-form-item__error) {
  font-size: 12px;
  padding-top: 4px;
  color: #ff6b6b;
}

.input-wrapper {
  position: relative;
  width: 100%;
}

.input-wrapper :deep(.el-input) {
  --el-input-bg-color: rgba(255, 255, 255, 0.06);
  --el-input-border-color: rgba(255, 255, 255, 0.1);
  --el-input-hover-border-color: rgba(255, 255, 255, 0.2);
  --el-input-focus-border-color: rgba(102, 126, 234, 0.8);
  --el-input-text-color: #fff;
  --el-input-placeholder-color: rgba(255, 255, 255, 0.35);
  --el-input-icon-color: rgba(255, 255, 255, 0.4);
}

.input-wrapper :deep(.el-input__wrapper) {
  background: rgba(255, 255, 255, 0.06);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 12px;
  padding: 4px 16px;
  box-shadow: none;
  transition: all 0.3s ease;
  backdrop-filter: blur(10px);
}

.input-wrapper :deep(.el-input__wrapper:hover) {
  border-color: rgba(255, 255, 255, 0.2);
  background: rgba(255, 255, 255, 0.08);
}

.input-wrapper :deep(.el-input__wrapper.is-focus) {
  border-color: rgba(102, 126, 234, 0.8);
  background: rgba(255, 255, 255, 0.1);
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.15);
}

.input-wrapper :deep(.el-input__inner) {
  color: #fff;
  height: 42px;
  font-size: 15px;
}

.input-wrapper :deep(.el-input__inner::placeholder) {
  color: rgba(255, 255, 255, 0.35);
}

.input-icon {
  color: rgba(255, 255, 255, 0.4);
  font-size: 16px;
  margin-right: 4px;
}

/* ========== Form Options ========== */
.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 18px 0 28px;
}

:deep(.el-checkbox__label) {
  color: rgba(255, 255, 255, 0.6) !important;
  font-size: 13px;
}

:deep(.el-checkbox__input.is-checked .el-checkbox__inner) {
  background-color: #667eea;
  border-color: #667eea;
}

.forgot-link {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.5);
  cursor: pointer;
  transition: color 0.3s;
}

.forgot-link:hover {
  color: rgba(255, 255, 255, 0.8);
}

/* ========== Login Button ========== */
.login-btn {
  width: 100%;
  height: 50px;
  border-radius: 12px;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 3px;
  border: none;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: all 0.3s ease;
  box-shadow: 0 4px 20px rgba(102, 126, 234, 0.4);
}

.login-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 30px rgba(102, 126, 234, 0.5);
}

.login-btn:active {
  transform: translateY(0);
}

.login-btn::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.2), transparent);
  transition: left 0.5s;
}

.login-btn:hover::before {
  left: 100%;
}

.btn-content {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.btn-arrow {
  transition: transform 0.3s ease;
}

.login-btn:hover .btn-arrow {
  transform: translateX(4px);
}

/* Loading dots */
.loading-dots {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
}

.loading-dots span {
  width: 8px;
  height: 8px;
  background: #fff;
  border-radius: 50%;
  animation: dotBounce 1.4s ease-in-out infinite;
}

.loading-dots span:nth-child(1) { animation-delay: 0s; }
.loading-dots span:nth-child(2) { animation-delay: 0.2s; }
.loading-dots span:nth-child(3) { animation-delay: 0.4s; }

@keyframes dotBounce {
  0%, 80%, 100% { transform: scale(0.6); opacity: 0.5; }
  40% { transform: scale(1); opacity: 1; }
}

/* ========== Card Footer ========== */
.card-footer {
  text-align: center;
  margin-top: 28px;
  font-size: 13px;
  color: rgba(255, 255, 255, 0.45);
}

.signup-link {
  color: #667eea;
  margin-left: 4px;
  cursor: pointer;
  font-weight: 500;
  transition: color 0.3s;
}

.signup-link:hover {
  color: #8fa0f5;
}

/* ========== Footer ========== */
.footer-info {
  margin-top: 40px;
  text-align: center;
  animation: fadeIn 1s ease-out 0.4s both;
}

.footer-info p {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.3);
  letter-spacing: 1px;
}

/* ========== Animations ========== */
@keyframes slideDown {
  from { opacity: 0; transform: translateY(-30px); }
  to { opacity: 1; transform: translateY(0); }
}

@keyframes slideUp {
  from { opacity: 0; transform: translateY(30px); }
  to { opacity: 1; transform: translateY(0); }
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}
</style>
