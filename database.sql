-- 创建数据库
CREATE DATABASE IF NOT EXISTS workflow_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE workflow_db;

-- 创建用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(50) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入测试用户 (密码: 123456, 使用 BCrypt 加密)
INSERT INTO users (username, password, email)
VALUES ('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'admin@example.com')
ON DUPLICATE KEY UPDATE username=username;

-- 创建流程表
CREATE TABLE IF NOT EXISTS workflows (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '流程名称',
    description TEXT COMMENT '流程描述',
    status VARCHAR(20) NOT NULL DEFAULT 'draft' COMMENT '状态: draft/active/paused/completed',
    priority INT DEFAULT 0 COMMENT '优先级: 0-普通 1-重要 2-紧急',
    creator_id BIGINT NOT NULL COMMENT '创建人ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_creator (creator_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入测试流程
INSERT INTO workflows (name, description, status, priority, creator_id)
VALUES
    ('入职审批流程', '新员工入职审批流程', 'active', 1, 1),
    ('请假申请流程', '员工请假申请审批', 'active', 0, 1),
    ('报销审批流程', '日常费用报销审批', 'draft', 0, 1)
ON DUPLICATE KEY UPDATE name=name;
