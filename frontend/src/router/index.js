import { createRouter, createWebHistory } from 'vue-router'
import WorkflowList from '../views/WorkflowList.vue'
import WorkflowCreate from '../views/WorkflowCreate.vue'
import WorkflowDetail from '../views/WorkflowDetail.vue'

const routes = [
  {
    path: '/',
    name: 'WorkflowList',
    component: WorkflowList
  },
  {
    path: '/create',
    name: 'WorkflowCreate',
    component: WorkflowCreate
  },
  {
    path: '/workflow/:id',
    name: 'WorkflowDetail',
    component: WorkflowDetail,
    props: true
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
