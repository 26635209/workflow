<template>
  <el-dialog
    v-model="dialogVisible"
    :title="isEdit ? '编辑流程' : '添加流程'"
    width="500px"
    :close-on-click-modal="false"
  >
    <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="流程名称" prop="name">
        <el-input v-model="form.name" placeholder="请输入流程名称" maxlength="100" show-word-limit />
      </el-form-item>
      
      <el-form-item label="流程描述" prop="description">
        <el-input
          v-model="form.description"
          type="textarea"
          :rows="3"
          placeholder="请输入流程描述"
          maxlength="500"
          show-word-limit
        />
      </el-form-item>
      
      <el-form-item label="状态" prop="status">
        <el-select v-model="form.status" placeholder="请选择状态" style="width: 100%">
          <el-option label="草稿" value="draft" />
          <el-option label="进行中" value="active" />
          <el-option label="已暂停" value="paused" />
          <el-option label="已完成" value="completed" />
        </el-select>
      </el-form-item>
      
      <el-form-item label="优先级" prop="priority">
        <el-radio-group v-model="form.priority">
          <el-radio :label="0">普通</el-radio>
          <el-radio :label="1">重要</el-radio>
          <el-radio :label="2">紧急</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" :loading="loading" @click="handleSubmit">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, watch } from 'vue'
import { ElMessage } from 'element-plus'

const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  },
  workflow: {
    type: Object,
    default: null
  }
})

const emit = defineEmits(['update:visible', 'success'])

const dialogVisible = ref(false)
const formRef = ref(null)
const loading = ref(false)

const form = reactive({
  name: '',
  description: '',
  status: 'draft',
  priority: 0,
  creatorId: 1
})

const rules = {
  name: [{ required: true, message: '请输入流程名称', trigger: 'blur' }]
}

const isEdit = ref(false)

watch(() => props.visible, (val) => {
  dialogVisible.value = val
  if (val) {
    if (props.workflow) {
      isEdit.value = true
      Object.assign(form, props.workflow)
    } else {
      isEdit.value = false
      form.name = ''
      form.description = ''
      form.status = 'draft'
      form.priority = 0
    }
  }
})

watch(dialogVisible, (val) => {
  emit('update:visible', val)
})

const handleSubmit = async () => {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  
  loading.value = true
  
  try {
    const storedUser = localStorage.getItem('user')
    if (storedUser) {
      const user = JSON.parse(storedUser)
      form.creatorId = user.id || 1
    }
    
    emit('success', { ...form })
    dialogVisible.value = false
    ElMessage.success(isEdit.value ? '编辑成功' : '添加成功')
  } catch (error) {
    ElMessage.error(error.message || '操作失败')
  } finally {
    loading.value = false
  }
}

defineExpose({ form })
</script>

<style scoped>
</style>
