# GI Assets + 任务管理平台示例

当前仓库新增了一个基于 **Vue3 + Spring Boot** 的任务管理平台示例，支持：

- 用户登录
- 管理员创建用户
- 管理员发放任务
- 任务流转（管理员变更负责人）
- 任务状态更新（用户/管理员）

## 项目结构

- `backend/`：Spring Boot 后端 API
- `frontend/`：Vue3 前端页面

## 快速启动

### 1) 启动后端

```bash
cd backend
mvn spring-boot:run
```

默认端口：`8080`

预置账号：

- 管理员：`admin / admin123`
- 用户：`alice / 123456`
- 用户：`bob / 123456`

### 2) 启动前端

```bash
cd frontend
npm install
npm run dev
```

默认端口：`5173`
