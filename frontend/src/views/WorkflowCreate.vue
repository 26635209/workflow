<template>
  <div class="workflow-create">
    <div class="header">
      <el-button type="primary" text @click="router.back()">
        <el-icon><ArrowLeft /></el-icon>
        返回
      </el-button>
      <h2 class="title">创建工作流</h2>
    </div>

    <el-card class="card" shadow="never">
      <el-form
        ref="formRef"
        :model="workflow"
        :rules="rules"
        label-width="120px"
        class="form"
      >
        <el-form-item label="工作流名称" prop="name">
          <el-input
            v-model="workflow.name"
            placeholder="请输入工作流名称"
            :maxlength="50"
          />
        </el-form-item>

        <el-form-item label="描述" prop="description">
          <el-input
            v-model="workflow.description"
            type="textarea"
            :rows="3"
            placeholder="请输入工作流描述"
            :maxlength="200"
          />
        </el-form-item>

        <el-form-item label="步骤配置">
          <div class="steps-container">
            <div
              v-for="(step, index) in workflow.steps"
              :key="index"
              class="step-item"
            >
              <div class="step-header">
                <span class="step-index">{{ index + 1 }}</span>
                <el-input
                  v-model="step.name"
                  placeholder="步骤名称"
                  class="step-name"
                />
                <el-button
                  type="danger"
                  text
                  @click="removeStep(index)"
                >
                  <el-icon><Delete /></el-icon>
                </el-button>
              </div>
              <div class="step-body">
                <el-input
                  v-model="step.action"
                  placeholder="执行动作"
                  class="step-action"
                />
                <el-input
                  v-model="step.description"
                  type="textarea"
                  :rows="2"
                  placeholder="步骤描述"
                  class="step-desc"
                />
              </div>
            </div>

            <el-button
              type="primary"
              plain
              @click="addStep"
              class="add-step-btn"
            >
              <el-icon><Plus /></el-icon>
              添加步骤
            </el-button>
          </div>
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            @click="submitForm"
            :loading="submitting"
            class="submit-btn"
          >
            保存工作流
          </el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()
const formRef = ref(null)
const submitting = ref(false)

const workflow = reactive({
  name: '',
  description: '',
  steps: [
    {
      name: '步骤1',
      action: '',
      description: ''
    }
  ]
})

const rules = {
  name: [
    { required: true, message: '请输入工作流名称', trigger: 'blur' },
    { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
  ]
}

const addStep = () => {
  workflow.steps.push({
    name: `步骤${workflow.steps.length + 1}`,
    action: '',
    description: ''
  })
}

const removeStep = (index) => {
  if (workflow.steps.length > 1) {
    workflow.steps.splice(index, 1)
  }
}

const submitForm = async () => {
  if (!formRef.value) return

  try {
    await formRef.value.validate()
    submitting.value = true

    await axios.post('/api/workflows', workflow)
    
    ElMessage.success('工作流创建成功')
    router.push('/')
  } catch (error) {
    if (error.response?.data?.message) {
      ElMessage.error(error.response.data.message)
    } else {
      ElMessage.error('创建失败')
    }
  } finally {
    submitting.value = false
  }
}

const resetForm = () => {
  formRef.value?.resetFields()
  workflow.steps = [
    {
      name: '步骤1',
      action: '',
      description: ''
    }
  ]
}
</script>

<style>
.workflow-create {
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

.form {
  max-width: 800px;
}

.steps-container {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.step-item {
  border: 1px solid #e4e7ed;
  border-radius: 8px;
  padding: 16px;
  background: #f9fafc;
  transition: all 0.3s;
}

.step-item:hover {
  border-color: #667eea;
  box-shadow: 0 2px 8px rgba(102, 126, 234, 0.1);
}

.step-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
}

.step-index {
  width: 28px;
  height: 28px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 600;
  font-size: 14px;
}

.step-name {
  flex: 1;
}

.step-body {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.step-action {
  width: 100%;
}

.step-desc {
  width: 100%;
}

.add-step-btn {
  margin-top: 12px;
  width: 100%;
}

.submit-btn {
  width: 100%;
}
</style>
