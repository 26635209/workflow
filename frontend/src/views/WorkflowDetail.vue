<template>
  <div class="workflow-detail">
    <div class="header">
      <el-button type="primary" text @click="router.back()">
        <el-icon><ArrowLeft /></el-icon>
        返回
      </el-button>
      <h2 class="title">工作流详情</h2>
    </div>

    <el-card class="card" shadow="never">
      <div v-if="!workflow" class="loading">
        <el-skeleton :rows="8" />
      </div>

      <div v-else>
        <div class="info-section">
          <h3 class="section-title">基本信息</h3>
          <div class="info-grid">
            <el-descriptions :column="2" border>
              <el-descriptions-item label="ID">{{ workflow.id }}</el-descriptions-item>
              <el-descriptions-item label="名称">{{ workflow.name }}</el-descriptions-item>
              <el-descriptions-item label="状态" :span="2">
                <el-tag :type="getStatusType(workflow.status)" effect="plain">
                  {{ getStatusText(workflow.status) }}
                </el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="描述" :span="2">{{ workflow.description }}</el-descriptions-item>
              <el-descriptions-item label="创建时间">{{ formatTime(workflow.createdAt) }}</el-descriptions-item>
              <el-descriptions-item label="更新时间">{{ formatTime(workflow.updatedAt) }}</el-descriptions-item>
            </el-descriptions>
          </div>
        </div>

        <div class="steps-section">
          <div class="steps-header">
            <h3 class="section-title">步骤列表</h3>
            <el-button
              type="primary"
              @click="executeWorkflow"
              :loading="executing"
            >
              <el-icon><Play /></el-icon>
              执行工作流
            </el-button>
          </div>

          <div class="steps-list">
            <div
              v-for="(step, index) in workflow.steps"
              :key="index"
              class="step-card"
            >
              <div class="step-number">{{ index + 1 }}</div>
              <div class="step-content">
                <div class="step-title">
                  <h4>{{ step.name }}</h4>
                  <el-tag
                    :type="getStepStatusType(step.status)"
                    size="small"
                  >
                    {{ getStepStatusText(step.status) }}
                  </el-tag>
                </div>
                <p class="step-action">{{ step.action }}</p>
                <p class="step-desc">{{ step.description }}</p>
                <div class="step-time" v-if="step.executedAt">
                  执行时间: {{ formatTime(step.executedAt) }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import axios from 'axios'

const router = useRouter()
const route = useRoute()

const workflow = ref(null)
const executing = ref(false)

const getStatusType = (status) => {
  const types = {
    draft: 'info',
    running: 'warning',
    completed: 'success',
    failed: 'danger'
  }
  return types[status] || 'info'
}

const getStatusText = (status) => {
  const texts = {
    draft: '草稿',
    running: '运行中',
    completed: '已完成',
    failed: '失败'
  }
  return texts[status] || status
}

const getStepStatusType = (status) => {
  const types = {
    pending: 'info',
    executing: 'warning',
    completed: 'success',
    failed: 'danger'
  }
  return types[status] || 'info'
}

const getStepStatusText = (status) => {
  const texts = {
    pending: '待执行',
    executing: '执行中',
    completed: '已完成',
    failed: '失败'
  }
  return texts[status] || status
}

const formatTime = (time) => {
  if (!time) return '-'
  return new Date(time).toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit'
  })
}

const loadWorkflow = async () => {
  try {
    const id = route.params.id
    const response = await axios.get(`/api/workflows/${id}`)
    workflow.value = response.data
  } catch (error) {
    ElMessage.error('加载工作流失败')
    router.push('/')
  }
}

const executeWorkflow = async () => {
  try {
    executing.value = true
    await axios.post(`/api/workflows/${route.params.id}/execute`)
    await loadWorkflow()
    ElMessage.success('工作流执行成功')
  } catch (error) {
    ElMessage.error('工作流执行失败')
  } finally {
    executing.value = false
  }
}

onMounted(() => {
  loadWorkflow()
})
</script>

<style>
.workflow-detail {
  padding: 20px 0;
}

.header {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 24px;
}

.title {
  font-size: 24px;
  font-weight: 600;
  color: #303133;
}

.card {
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
}

.info-section,
.steps-section {
  margin-bottom: 32px;
}

.section-title {
  font-size: 18px;
  font-weight: 600;
  color: #303133;
  margin-bottom: 16px;
}

.info-grid {
  background: #f9fafc;
  border-radius: 8px;
  padding: 24px;
}

.steps-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.steps-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.step-card {
  display: flex;
  gap: 16px;
  padding: 20px;
  background: #f9fafc;
  border-radius: 8px;
  border: 1px solid #e4e7ed;
  transition: all 0.3s;
}

.step-card:hover {
  border-color: #667eea;
  box-shadow: 0 2px 8px rgba(102, 126, 234, 0.1);
}

.step-number {
  width: 36px;
  height: 36px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 600;
  font-size: 16px;
  flex-shrink: 0;
}

.step-content {
  flex: 1;
}

.step-title {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 8px;
}

.step-title h4 {
  font-size: 16px;
  font-weight: 600;
  color: #303133;
}

.step-action {
  font-family: 'Courier New', monospace;
  color: #606266;
  margin: 4px 0;
  padding: 8px;
  background: #fff;
  border-radius: 4px;
  border-left: 3px solid #667eea;
}

.step-desc {
  color: #909399;
  margin: 4px 0;
}

.step-time {
  color: #909399;
  font-size: 12px;
  margin-top: 8px;
}
</style>
