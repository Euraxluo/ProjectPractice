-- phpMyAdmin SQL Dump
-- version 4.4.15.10
-- https://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: 2019-11-22 17:21:28
-- 服务器版本： 5.5.62-log
-- PHP Version: 7.0.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `blog`
--

-- --------------------------------------------------------

--
-- 表的结构 `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `id` int(11) NOT NULL COMMENT '自增ID',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `auth_key` varchar(32) NOT NULL COMMENT '自动登录key',
  `password_hash` varchar(255) NOT NULL COMMENT '加密密码',
  `password_reset_token` varchar(255) DEFAULT NULL COMMENT '重置密码token',
  `email_validate_token` varchar(255) DEFAULT NULL COMMENT '邮箱验证token',
  `email` varchar(255) NOT NULL COMMENT '邮箱',
  `role` smallint(6) NOT NULL DEFAULT '10' COMMENT '角色等级',
  `status` smallint(6) NOT NULL DEFAULT '10' COMMENT '状态',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `vip_lv` int(11) DEFAULT '0' COMMENT 'vip等级',
  `created_at` int(11) NOT NULL COMMENT '创建时间',
  `updated_at` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员表';

-- --------------------------------------------------------

--
-- 表的结构 `cats`
--

CREATE TABLE IF NOT EXISTS `cats` (
  `id` int(11) NOT NULL COMMENT '自增ID',
  `cat_name` varchar(255) DEFAULT NULL COMMENT '分类名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分类表';

-- --------------------------------------------------------

--
-- 表的结构 `feeds`
--

CREATE TABLE IF NOT EXISTS `feeds` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `content` varchar(255) NOT NULL COMMENT '内容',
  `created_at` int(11) NOT NULL COMMENT '创建时间'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='聊天信息表';

-- --------------------------------------------------------

--
-- 表的结构 `posts`
--

CREATE TABLE IF NOT EXISTS `posts` (
  `id` int(11) NOT NULL COMMENT '自增ID',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `summary` varchar(255) DEFAULT NULL COMMENT '摘要',
  `content` text COMMENT '内容',
  `label_img` varchar(255) DEFAULT NULL COMMENT '标签图',
  `cat_id` int(11) DEFAULT NULL COMMENT '分类id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `is_valid` tinyint(1) DEFAULT '0' COMMENT '是否有效：0-未发布 1-已发布',
  `created_at` int(11) DEFAULT NULL COMMENT '创建时间',
  `updated_at` int(11) DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章主表';

-- --------------------------------------------------------

--
-- 表的结构 `post_extends`
--

CREATE TABLE IF NOT EXISTS `post_extends` (
  `id` int(11) NOT NULL COMMENT '自增ID',
  `post_id` int(11) DEFAULT NULL COMMENT '文章id',
  `browser` int(11) DEFAULT '0' COMMENT '浏览量',
  `collect` int(11) DEFAULT '0' COMMENT '收藏量',
  `praise` int(11) DEFAULT '0' COMMENT '点赞',
  `comment` int(11) DEFAULT '0' COMMENT '评论'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章扩展表';

-- --------------------------------------------------------

--
-- 表的结构 `relation_post_tags`
--

CREATE TABLE IF NOT EXISTS `relation_post_tags` (
  `id` int(11) NOT NULL COMMENT '自增ID',
  `post_id` int(11) DEFAULT NULL COMMENT '文章ID',
  `tag_id` int(11) DEFAULT NULL COMMENT '标签ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章和标签关系表';

-- --------------------------------------------------------

--
-- 表的结构 `tags`
--

CREATE TABLE IF NOT EXISTS `tags` (
  `id` int(11) NOT NULL COMMENT '自增ID',
  `tag_name` varchar(255) DEFAULT NULL COMMENT '标签名称',
  `post_num` int(11) DEFAULT '0' COMMENT '关联文章数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签表';

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL COMMENT '自增ID',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `auth_key` varchar(32) NOT NULL COMMENT '自动登录key',
  `password_hash` varchar(255) NOT NULL COMMENT '加密密码',
  `password_reset_token` varchar(255) DEFAULT NULL COMMENT '重置密码token',
  `email_validate_token` varchar(255) DEFAULT NULL COMMENT '邮箱验证token',
  `email` varchar(255) NOT NULL COMMENT '邮箱',
  `role` smallint(6) NOT NULL DEFAULT '10' COMMENT '角色等级',
  `status` smallint(6) NOT NULL DEFAULT '10' COMMENT '状态',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `vip_lv` int(11) DEFAULT '0' COMMENT 'vip等级',
  `created_at` int(11) NOT NULL COMMENT '创建时间',
  `updated_at` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员表';

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cats`
--
ALTER TABLE `cats`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `feeds`
--
ALTER TABLE `feeds`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `posts`
--
ALTER TABLE `posts`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_cat_valid` (`cat_id`,`is_valid`) USING BTREE;

--
-- Indexes for table `post_extends`
--
ALTER TABLE `post_extends`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `relation_post_tags`
--
ALTER TABLE `relation_post_tags`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `post_id` (`post_id`,`tag_id`) USING BTREE;

--
-- Indexes for table `tags`
--
ALTER TABLE `tags`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `tag_name` (`tag_name`) USING BTREE;

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID';
--
-- AUTO_INCREMENT for table `cats`
--
ALTER TABLE `cats`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID';
--
-- AUTO_INCREMENT for table `feeds`
--
ALTER TABLE `feeds`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `posts`
--
ALTER TABLE `posts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID';
--
-- AUTO_INCREMENT for table `post_extends`
--
ALTER TABLE `post_extends`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID';
--
-- AUTO_INCREMENT for table `relation_post_tags`
--
ALTER TABLE `relation_post_tags`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID';
--
-- AUTO_INCREMENT for table `tags`
--
ALTER TABLE `tags`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID';
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID';
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
