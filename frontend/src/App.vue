<template>
  <div id="app">
    <!-- 动态粒子背景 -->
    <div class="bg-animation">
      <div class="bg-gradient"></div>
      <div class="particles">
        <div v-for="i in 20" :key="i" class="particle" :style="getParticleStyle(i)"></div>
      </div>
      <div class="floating-shapes">
        <div class="shape shape-1"></div>
        <div class="shape shape-2"></div>
        <div class="shape shape-3"></div>
      </div>
    </div>

    <!-- 路由视图 -->
    <router-view v-slot="{ Component, route }">
      <transition :name="route.meta.transition || 'fade'" mode="out-in">
        <component :is="Component" />
      </transition>
    </router-view>
  </div>
</template>

<script setup>
const getParticleStyle = (i) => {
  const size = Math.random() * 6 + 2
  const left = Math.random() * 100
  const delay = Math.random() * 15
  const duration = Math.random() * 20 + 15
  return {
    width: `${size}px`,
    height: `${size}px`,
    left: `${left}%`,
    animationDelay: `${delay}s`,
    animationDuration: `${duration}s`
  }
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  overflow-x: hidden;
}

#app {
  min-height: 100vh;
  position: relative;
}

/* ========== 动态背景 ========== */
.bg-animation {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 0;
  overflow: hidden;
}

.bg-gradient {
  position: absolute;
  inset: 0;
  background: linear-gradient(135deg, #0f0c29 0%, #302b63 50%, #24243e 100%);
  animation: gradientShift 12s ease infinite;
}

@keyframes gradientShift {
  0%, 100% { opacity: 1; }
  50% { opacity: 0.85; }
}

/* 粒子 */
.particles {
  position: absolute;
  inset: 0;
}

.particle {
  position: absolute;
  bottom: -10px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.6);
  animation: floatUp linear infinite;
  box-shadow: 0 0 6px rgba(255, 255, 255, 0.4);
}

@keyframes floatUp {
  0% {
    transform: translateY(0) scale(1);
    opacity: 0;
  }
  10% {
    opacity: 1;
  }
  90% {
    opacity: 1;
  }
  100% {
    transform: translateY(-100vh) scale(0.5);
    opacity: 0;
  }
}

/* 浮动形状 */
.floating-shapes {
  position: absolute;
  inset: 0;
}

.shape {
  position: absolute;
  border-radius: 50%;
  filter: blur(80px);
  opacity: 0.4;
  animation: floatShape 20s ease-in-out infinite;
}

.shape-1 {
  width: 500px;
  height: 500px;
  background: radial-gradient(circle, #667eea, #764ba2);
  top: -100px;
  left: -100px;
  animation-duration: 25s;
}

.shape-2 {
  width: 400px;
  height: 400px;
  background: radial-gradient(circle, #f093fb, #f5576c);
  bottom: -80px;
  right: -80px;
  animation-duration: 20s;
  animation-delay: -7s;
}

.shape-3 {
  width: 300px;
  height: 300px;
  background: radial-gradient(circle, #4facfe, #00f2fe);
  top: 50%;
  left: 60%;
  animation-duration: 18s;
  animation-delay: -12s;
}

@keyframes floatShape {
  0%, 100% { transform: translate(0, 0) scale(1); }
  33% { transform: translate(30px, -30px) scale(1.05); }
  66% { transform: translate(-20px, 20px) scale(0.95); }
}

/* ========== 页面专属过渡 ========== */
.login-fade-enter-active,
.login-fade-leave-active {
  transition: all 0.5s cubic-bezier(0.4, 0, 0.2, 1);
}
.login-fade-enter-from {
  opacity: 0;
  transform: scale(0.95) translateY(20px);
}
.login-fade-leave-to {
  opacity: 0;
  transform: scale(1.05) translateY(-20px);
}

.home-fade-enter-active,
.home-fade-leave-active {
  transition: all 0.4s ease;
}
.home-fade-enter-from {
  opacity: 0;
  transform: translateX(20px);
}
.home-fade-leave-to {
  opacity: 0;
  transform: translateX(-20px);
}

/* ========== 全局滚动条 ========== */
::-webkit-scrollbar {
  width: 6px;
}

::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0.05);
}

::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.2);
  border-radius: 3px;
}

::-webkit-scrollbar-thumb:hover {
  background: rgba(255, 255, 255, 0.3);
}
</style>
