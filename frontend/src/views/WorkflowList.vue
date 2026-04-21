<template>
  <div class="workflow-list">
    <div class="header">
      <h2 class="title">工作流列表</h2>
      <el-button type="primary" @click="goToCreate">
        <el-icon><Plus /></el-icon>
        创建新工作流
      </el-button>
    </div>

    <el-card class="card" shadow="never">
      <div v-if="workflows.length === 0" class="empty">
        <el-empty description="暂无工作流" :image-size="120">
          <el-button type="primary" @click="goToCreate">创建第一个工作流</el-button>
        </el-empty>
      </div>

      <el-table
        v-else
        :data="workflows"
        style="width: 100%"
        :row-key="row => row.id"
        :default-sort="{ prop: 'createdAt', order: 'descending' }"
      >
        <el-table-column prop="id" label="ID" width="120" />
        <el-table-column prop="name" label="名称" min-width="200">
          <template #default="{ row }">
            <el-link type="primary" @click="viewWorkflow(row.id)">
              {{ row.name }}
            </el-link>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述" min-width="250" />
        <el-table-column prop="status" label="状态" width="120">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)" effect="plain">
              {{ getStatusText(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="steps.length" label="步骤数" width="100" />
        <el-table-column prop="createdAt" label="创建时间" width="180" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button-group>
              <el-button
                type="success"
                size="small"
                @click="executeWorkflow(row.id)"
              >
                <el-icon><Play /></el-icon>
                执行
              </el-button>
              <el-button
                type="primary"
                size="small"
                @click="viewWorkflow(row.id)"
              >
                <el-icon><View /></el-icon>
                查看
              </el-button>
              <el-button
                type="danger"
                size="small"
                @click="deleteWorkflow(row.id)"
              >
                <el-icon><Delete /></el-icon>
                删除
              </el-button>
            </el-button-group>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()
const workflows = ref([])

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

const loadWorkflows = async () => {
  try {
    const response = await axios.get('/api/workflows')
    workflows.value = response.data
  } catch (error) {
    console.error('加载工作流失败:', error)
  }
}

const goToCreate = () => {
  router.push('/create')
}

const viewWorkflow = (id) => {
  router.push(`/workflow/${id}`)
}

const executeWorkflow = async (id) => {
  try {
    await axios.post(`/api/workflows/${id}/execute`)
    await loadWorkflows()
    ElMessage.success('工作流执行成功')
  } catch (error) {
    ElMessage.error('工作流执行失败')
  }
}

const deleteWorkflow = async (id) => {
  try {
    await axios.delete(`/api/workflows/${id}`)
    await loadWorkflows()
    ElMessage.success('工作流已删除')
  } catch (error) {
    ElMessage.error('删除失败')
  }
}

onMounted(() => {
  loadWorkflows()
})
</script>

<style>
.workflow-list {
  padding: 20px 0;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
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

.empty {
  padding: 80px 0;
  text-align: center;
}
</style>
