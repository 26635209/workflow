# 工作流系统 - Spring Boot + Vue

一个简单的 Spring Boot + Vue 登录示例系统

## 技术栈

### 后端
- Spring Boot 3.2.5
- Spring Data JPA
- MySQL 8.0
- Maven

### 前端
- Vue 3
- Vue Router
- Element Plus
- Axios
- Vite

## 快速开始

### 1. 数据库配置

1. 安装 MySQL 8.0
2. 执行 `database.sql` 文件创建数据库和初始用户

```bash
mysql -u root -p < database.sql
```

默认测试用户：
- 用户名：admin
- 密码：123456

3. 修改 `backend/src/main/resources/application.properties` 中的数据库连接信息

```properties
spring.datasource.username=root
spring.datasource.password=your_password
```

### 2. 启动后端

```bash
cd backend
mvn spring-boot:run
```

后端服务将在 http://localhost:8080 启动

### 3. 启动前端

```bash
cd frontend
npm install
npm run dev
```

前端服务将在 http://localhost:5173 启动

## 项目结构

```
workflow/
├── backend/                    # 后端 Spring Boot 项目
│   ├── src/main/java/com/workflow/backend/
│   │   ├── controller/        # 控制器层
│   │   ├── service/           # 服务层
│   │   ├── repository/        # 数据访问层
│   │   ├── model/             # 实体模型
│   │   └── dto/               # 数据传输对象
│   └── pom.xml                # Maven 配置
├── frontend/                   # 前端 Vue 项目
│   ├── src/
│   │   ├── views/             # 页面视图
│   │   ├── router/            # 路由配置
│   │   └── utils/             # 工具函数
│   └── package.json           # 项目依赖
└── database.sql                # 数据库初始化脚本
```

## API 接口

### 登录接口
- **URL**: `/api/auth/login`
- **Method**: `POST`
- **Request Body**:
```json
{
  "username": "admin",
  "password": "123456"
}
```
- **Response**:
```json
{
  "id": 1,
  "username": "admin",
  "email": "admin@example.com",
  "token": "token_1_1713686400000"
}
```

## 注意事项

1. 确保 MySQL 服务已启动
2. 首次运行后端时，Spring Data JPA 会自动创建数据表（`ddl-auto=update`）
3. 生产环境请修改默认密码并配置安全策略
