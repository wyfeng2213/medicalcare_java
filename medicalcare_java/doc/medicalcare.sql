-- MySQL dump 10.13  Distrib 5.6.33, for Win64 (x86_64)
--
-- Host: 10.1.1.12    Database: medicalcare
-- ------------------------------------------------------
-- Server version	5.6.25-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `doctors_appointment`
--

DROP TABLE IF EXISTS `doctors_appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctors_appointment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `doctors_id` int(11) DEFAULT NULL COMMENT '医生id',
  `doctors_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '医生名字',
  `doctors_phone` varchar(12) CHARACTER SET utf8 DEFAULT NULL COMMENT '医生手机号',
  `doctors_login_id` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '医生登录id',
  `patient_id` int(11) DEFAULT NULL COMMENT '患者id',
  `patient_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '患者名字',
  `patient_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '患者手机号',
  `patient_login_id` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '患者登录id',
  `secretary_id` int(11) DEFAULT NULL COMMENT '秘书id',
  `secretary_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '秘书名字',
  `secretary_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '秘书电话',
  `secretary_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '秘书登录id',
  `appointment_time` datetime DEFAULT NULL COMMENT '预约时间',
  `appointment_time_start` datetime DEFAULT NULL COMMENT '预约开始时间',
  `appointment_time_end` datetime DEFAULT NULL COMMENT '预约结束时间',
  `type` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '类型，1语音，2视频',
  `content` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '患者留言，咨询类容',
  `state` int(11) DEFAULT NULL COMMENT '状态，0发起，1预约，2完成，3失败',
  `time` int(11) DEFAULT NULL COMMENT '使用时长，单位分钟',
  `launch_type` int(11) DEFAULT NULL COMMENT '发起人，1医生，2患者，3秘书',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='医生预约信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `doctors_followup_plan`
--

DROP TABLE IF EXISTS `doctors_followup_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctors_followup_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '标题',
  `content` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '内容',
  `doctors_id` int(11) DEFAULT NULL COMMENT '医生id',
  `doctors_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '医生姓名',
  `doctors_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '医生电话',
  `doctors_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '医生登录id',
  `operation_time` datetime DEFAULT NULL COMMENT '手术时间',
  `is_remind_doctors` int(11) DEFAULT NULL COMMENT '是否提醒医生，1提醒，0不提醒',
  `is_remind_patient` int(11) DEFAULT NULL COMMENT '是否提醒患者，1提醒，0不提醒',
  `remind_time` int(11) DEFAULT NULL COMMENT '提醒时间，按分钟计算，例如-1为不提醒，0按日程提醒，5提前5分钟提醒',
  `is_patient_visible` int(11) DEFAULT NULL COMMENT '是否患者可见',
  `patient_id` int(11) DEFAULT NULL COMMENT '患者id',
  `patient_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '患者名字',
  `patient_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '患者电话',
  `patient_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '患者登录id',
  `secretary_id` int(11) DEFAULT NULL COMMENT '小秘书id',
  `secretary_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '小秘书名字',
  `secretary_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '小秘书手机号',
  `secretary_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '小秘书登录号',
  `doctors_followup_plan_template_id` int(11) DEFAULT NULL COMMENT '模板id',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='随访计划';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `doctors_followup_plan_point`
--

DROP TABLE IF EXISTS `doctors_followup_plan_point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctors_followup_plan_point` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `doctors_followup_plan_id` int(11) DEFAULT NULL COMMENT '计划id',
  `content` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '随访内容',
  `type` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '随访类型，复诊提醒，用药提醒，换药提醒，手术提醒，问诊表，当选择“问诊表”时，随访内容为“问诊表名称,id”',
  `far_from_last _time` int(11) DEFAULT NULL COMMENT '距上次多少天',
  `plan_time` datetime DEFAULT NULL COMMENT '计划时间',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `doctors_followup_plan_point_template_id` int(11) DEFAULT NULL COMMENT '随访计划时间点模板id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='随访计划时间点';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `doctors_followup_plan_point_template`
--

DROP TABLE IF EXISTS `doctors_followup_plan_point_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctors_followup_plan_point_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `doctors_followup_plan_template_id` int(11) DEFAULT NULL COMMENT '计划模板id',
  `content` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '随访内容',
  `type` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '随访类型，复诊提醒，用药提醒，换药提醒，手术提醒，问诊表，当选择“问诊表”时，随访内容为“问诊表模板名称,模板id”',
  `far_from_last _time` int(11) DEFAULT NULL COMMENT '距上次多少天',
  `plan_time` datetime DEFAULT NULL COMMENT '计划时间',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='随访计划时间点模板';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `doctors_followup_plan_template`
--

DROP TABLE IF EXISTS `doctors_followup_plan_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctors_followup_plan_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '标题',
  `content` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '内容',
  `type` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '类型',
  `doctors_id` int(11) DEFAULT NULL COMMENT '医生id',
  `doctors_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '医生姓名',
  `operation_time` datetime DEFAULT NULL COMMENT '手术时间',
  `is_remind_doctors` int(11) DEFAULT NULL COMMENT '是否提醒医生，1提醒，0不提醒',
  `is_remind_patient` int(11) DEFAULT NULL COMMENT '是否提醒患者，1提醒，0不提醒',
  `remind_time` int(11) DEFAULT NULL COMMENT '提醒时间，按分钟计算，-1为提醒，0按日程提醒，5提前5分钟提醒',
  `is_patient_visible` int(11) DEFAULT NULL COMMENT '是否患者可见',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='随访计划模板';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `doctors_interrogation`
--

DROP TABLE IF EXISTS `doctors_interrogation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctors_interrogation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `doctors_id` int(11) DEFAULT NULL COMMENT '医生id',
  `doctors_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '医生名字',
  `doctors_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '患者手机号',
  `doctors_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '患者登录id',
  `title` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '标题',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `content` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '问诊内容',
  `result` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '问诊结果',
  `patient_id` int(11) DEFAULT NULL COMMENT '患者id',
  `patient_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '患者名字',
  `patient_phone` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '患者手机号',
  `patient_login_id` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '患者登录id',
  `secretary_id` int(11) DEFAULT NULL COMMENT '小秘书id',
  `secretary_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '小秘书名字',
  `secretary_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '小秘书手机号',
  `secretary_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '小秘书登录id',
  `doctors_interrogation_template_id` int(11) DEFAULT NULL COMMENT '模板id',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='问诊表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `doctors_interrogation_question`
--

DROP TABLE IF EXISTS `doctors_interrogation_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctors_interrogation_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `doctors_interrogation` int(11) DEFAULT NULL COMMENT '问诊表id',
  `question` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '问题',
  `question_type` int(11) DEFAULT NULL COMMENT '问题类型',
  `answer` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '回答',
  `answer_time` datetime DEFAULT NULL COMMENT '回答时间',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `doctors_interrogation_question_template_id` int(11) DEFAULT NULL COMMENT '模板id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='问诊表问题';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `doctors_interrogation_question_template`
--

DROP TABLE IF EXISTS `doctors_interrogation_question_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctors_interrogation_question_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `doctors_interrogation_template_id` int(11) DEFAULT NULL COMMENT '问诊表模板id',
  `question` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '问题',
  `question_type` int(11) DEFAULT NULL COMMENT '问题类型',
  `answer` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '回答',
  `answer_time` datetime DEFAULT NULL COMMENT '答题时间',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='问诊表模板问题';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `doctors_interrogation_template`
--

DROP TABLE IF EXISTS `doctors_interrogation_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctors_interrogation_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `doctors_id` int(11) DEFAULT NULL COMMENT '医生id',
  `doctors_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '医生名字',
  `title` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '标题',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `content` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '问诊内容',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='问诊表模板';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `doctors_login_info`
--

DROP TABLE IF EXISTS `doctors_login_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctors_login_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `doctors_id` int(11) DEFAULT NULL COMMENT '用户id',
  `doctors_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户名字',
  `doctors_phone` varchar(12) CHARACTER SET utf8 DEFAULT NULL COMMENT '手机号',
  `doctors_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '登录id',
  `token` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT 'token',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `ip` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '登录IP',
  `type` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '类型',
  `system` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '手机系统',
  `model` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '手机型号',
  `version` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '前端版本',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='医生登录信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `doctors_package`
--

DROP TABLE IF EXISTS `doctors_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctors_package` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(45) DEFAULT NULL COMMENT '套餐名字',
  `doctors_id` int(11) DEFAULT NULL COMMENT '医生id',
  `doctors_name` varchar(45) DEFAULT NULL COMMENT '医生名字',
  `doctors_phone` varchar(12) DEFAULT NULL COMMENT '医生电话',
  `doctors_login_id` varchar(45) DEFAULT NULL COMMENT '医生登录id',
  `online_consultation` varchar(45) DEFAULT NULL COMMENT '在线咨询',
  `remote_diagnosis` varchar(45) DEFAULT NULL COMMENT '远程诊断',
  `telephone_consultation` varchar(45) DEFAULT NULL COMMENT '电话咨询',
  `offline_appointment` varchar(45) DEFAULT NULL COMMENT '现在预约',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='医生套餐';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `doctors_patient_link`
--

DROP TABLE IF EXISTS `doctors_patient_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctors_patient_link` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `doctors_id` int(11) DEFAULT NULL COMMENT '医生id',
  `doctors_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '医生名字',
  `doctors_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '医生手机号',
  `doctors_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '医生登录id',
  `patient_id` int(11) DEFAULT NULL COMMENT '患者id',
  `patient_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '患者名字',
  `patient_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '患者手机号',
  `patient_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '患者登录id',
  `chatroom_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '聊天室ID',
  `group_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '病人分组',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `type` int(11) DEFAULT NULL COMMENT '关系类型：1同意，2拒绝，3未处理',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='医生的患者';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `doctors_team`
--

DROP TABLE IF EXISTS `doctors_team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctors_team` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '团队名称',
  `introduction` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '团队介绍',
  `doctors_id` int(11) DEFAULT NULL,
  `doctors_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '医生名字',
  `doctors_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '医生手机',
  `doctors_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '医生登录id',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='医生团队';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `doctors_team_invite`
--

DROP TABLE IF EXISTS `doctors_team_invite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctors_team_invite` (
  `id` int(11) NOT NULL COMMENT '主键',
  `team_id` int(11) DEFAULT NULL COMMENT '医生团队ID',
  `team_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '医生团队名称',
  `invite_useid` int(11) DEFAULT NULL COMMENT '邀请人ID',
  `invite_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '邀请人名字',
  `invite_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '邀请人手机号',
  `invite_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '邀请人id',
  `invited_id` int(11) DEFAULT NULL COMMENT '被邀请人id',
  `invited_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '被邀请人名字',
  `invited_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '被邀请人手机号',
  `invited_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '被邀请人id',
  `state` int(11) DEFAULT NULL COMMENT '状态',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='医生团队邀请信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `doctors_team_user_link`
--

DROP TABLE IF EXISTS `doctors_team_user_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctors_team_user_link` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `team_id` int(11) DEFAULT NULL COMMENT '团队',
  `team_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '团队名称',
  `doctors_id` int(11) DEFAULT NULL COMMENT '医生ID',
  `doctors_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '医生名字',
  `doctors_phone` varchar(12) CHARACTER SET utf8 DEFAULT NULL COMMENT '医生手机号',
  `doctors_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '登录id',
  `isleader` int(11) DEFAULT NULL COMMENT '是否leader，1是，0不是',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='医生团队与医生关联';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `doctors_user`
--

DROP TABLE IF EXISTS `doctors_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctors_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `password` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '环信密码',
  `login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '登录id，系统或者IM登录的id',
  `phone` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '医生手机号',
  `name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '真实名字',
  `sex` varchar(11) COLLATE utf8_bin DEFAULT NULL COMMENT '性别,',
  `head_url` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `title` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '职称',
  `hospital` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '就职医院',
  `department` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '科室',
  `start_working_time` datetime DEFAULT NULL COMMENT '开始工作时间',
  `certificate_url` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '医生证件地址',
  `department_telephone` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '科室电话',
  `introduction` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '医生简介',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `is_phone_consultation` int(11) DEFAULT NULL COMMENT '是否电话咨询',
  `is_video_consultation` int(11) DEFAULT NULL COMMENT '是否语音咨询',
  `easemob_uuid` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '环信主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='医生用户基本信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `group_doctors_patient`
--

DROP TABLE IF EXISTS `group_doctors_patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_doctors_patient` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '组名',
  `subject` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '主题',
  `group_uuid` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '环信组id',
  `doctors_id` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建医生id',
  `doctors_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建医生名字',
  `doctors_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '创建医生手机号',
  `doctors_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '医生登录id',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='医生患者群组';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `group_doctors_patient_link`
--

DROP TABLE IF EXISTS `group_doctors_patient_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_doctors_patient_link` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `group_id` int(11) DEFAULT NULL COMMENT '组id',
  `group_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '组名字',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户名字',
  `user_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '用户手机号',
  `user_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '用户登录id',
  `type` int(11) DEFAULT NULL COMMENT '用户类型，1群主，2医生，3秘书',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='医生病人分组成员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `message_group_log`
--

DROP TABLE IF EXISTS `message_group_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_group_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `to_group_id` int(11) DEFAULT NULL COMMENT '组id',
  `to_group_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '组名字',
  `to_doctors_id` int(11) DEFAULT NULL COMMENT '医生id，逗号隔开',
  `to_doctors_name` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '医生名字，逗号隔开',
  `to_doctors_phone` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '医生手机号，逗号隔开',
  `to_patient_id` int(11) DEFAULT NULL COMMENT '病人id',
  `to_patient_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '病人名字',
  `to_patient_phone` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '患者手机号',
  `from_user_id` int(11) DEFAULT NULL COMMENT '发送用户',
  `from_user_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '发送用户名字',
  `from_user_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '发送人手机号',
  `from_user_type` int(11) DEFAULT NULL COMMENT '发送用户类型，1医生，2患者',
  `message` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '消息，文字，媒体url，问诊表，随访计划',
  `message_type` int(11) DEFAULT NULL COMMENT '消息类型，1文字，2图片，3语音，4问诊表，5随访计划',
  `sendtime` datetime DEFAULT NULL COMMENT '发送时间',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='会诊信息消息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `message_log`
--

DROP TABLE IF EXISTS `message_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `to_user_id1` int(11) DEFAULT NULL COMMENT '接收用户id',
  `to_user_name1` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '接收用户名字',
  `to_user_phone1` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '接收者用户手机号',
  `to_user_id2` int(11) DEFAULT NULL,
  `to_user_name2` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `to_user_phone2` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `from_user_id` int(11) DEFAULT NULL COMMENT '发送用户id',
  `from_user_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '发送用户名字',
  `from_user_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '发送用户手机号',
  `from_user_type` int(11) DEFAULT NULL COMMENT '发送用户类型，1医生，2患者，3秘书',
  `messageContent` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '消息，文字或者媒体url',
  `message_type` int(11) DEFAULT NULL COMMENT '消息类型，1文字，2图片，3语音，4视频，5问诊表，6随访计划',
  `sendtime` datetime DEFAULT NULL COMMENT '发送时间',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='图文信息日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `patient_blood_pressure`
--

DROP TABLE IF EXISTS `patient_blood_pressure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_blood_pressure` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `high` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '高压',
  `low` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '低压',
  `pulse` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '脉搏',
  `plan_id` int(11) DEFAULT NULL COMMENT '计划id',
  `doctors_id` int(11) DEFAULT NULL COMMENT '医生id',
  `doctors_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '医生名字',
  `doctors_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '医生手机号',
  `patient_id` int(11) DEFAULT NULL COMMENT '患者id',
  `patient_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '患者名字',
  `patient_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '患者手机号',
  `note` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='患者血压';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `patient_blood_sugar`
--

DROP TABLE IF EXISTS `patient_blood_sugar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_blood_sugar` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `high` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '高',
  `low` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '低',
  `plan_id` int(11) DEFAULT NULL COMMENT '计划id',
  `doctors_id` int(11) DEFAULT NULL COMMENT '医生id',
  `doctors_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '医生名字',
  `doctors_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '医生手机号',
  `patient_id` int(11) DEFAULT NULL COMMENT '患者id',
  `patient_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '患者名字',
  `patient_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '患者手机号',
  `note` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='患者血糖';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `patient_doctors_link`
--

DROP TABLE IF EXISTS `patient_doctors_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_doctors_link` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `patient_id` int(11) DEFAULT NULL COMMENT '患者id',
  `patient_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '患者名字',
  `patient_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '患者手机号',
  `patient_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '患者登录id',
  `doctors_id` int(11) DEFAULT NULL COMMENT '医生电话',
  `doctors_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '医生名字',
  `doctors_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '医生手机号',
  `chatroom_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '聊天室ID',
  `doctors_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '医生登录id',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新时间',
  `type` int(11) DEFAULT NULL COMMENT '关系类型：1同意，2拒绝，3未处理',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='患者的医生';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `patient_fasting_weight`
--

DROP TABLE IF EXISTS `patient_fasting_weight`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_fasting_weight` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `weight` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '高压',
  `plan_id` int(11) DEFAULT NULL COMMENT '计划id',
  `doctors_id` int(11) DEFAULT NULL COMMENT '医生id',
  `doctors_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '医生名字',
  `doctors_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '医生手机号',
  `patient_id` int(11) DEFAULT NULL COMMENT '患者id',
  `patient_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '患者名字',
  `patient_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '患者手机号',
  `note` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='空腹体重';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `patient_health_record`
--

DROP TABLE IF EXISTS `patient_health_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_health_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `patient_id` int(11) DEFAULT NULL COMMENT '患者id',
  `patient_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '名字',
  `patient_phone` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '患者手机号',
  `patient_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '患者登录id',
  `height` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '身高',
  `weight` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '体重',
  `smoking_history` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '抽烟史',
  `drinking_history` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '饮酒习惯',
  `sleep_state` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '睡眠状况',
  `stoolurine_is_normal` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '大小便是否正常',
  `drug_history` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '药物历史',
  `allergic_history` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '过敏史',
  `diet_history` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '饮食历史',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='患者健康档案';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `patient_invitation`
--

DROP TABLE IF EXISTS `patient_invitation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_invitation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `from_patient_id` int(11) DEFAULT NULL COMMENT '邀请病人id',
  `from_patient_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '邀请病人名字',
  `from_patient_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '邀请病人手机号',
  `from_patient_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '邀请病人登录id',
  `invite_type` int(11) DEFAULT NULL COMMENT '邀请类型',
  `doctors_id` int(11) DEFAULT NULL COMMENT '邀请医生id',
  `doctors_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '邀请医生名字',
  `doctors_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '医生手机号',
  `doctors_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '医生登录id',
  `to_patient_id` int(11) DEFAULT NULL COMMENT '被邀请人id',
  `to_patient_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '被邀请人名字',
  `to_patient_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '被邀请人手机号',
  `to_patient_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '被邀请人登录id',
  `state` int(11) DEFAULT NULL COMMENT '状态',
  `invitetime` datetime DEFAULT NULL COMMENT '邀请时间',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='患者邀请患者表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `patient_login_info`
--

DROP TABLE IF EXISTS `patient_login_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_login_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `patient_id` int(11) DEFAULT NULL COMMENT '用户id',
  `patient_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户名字',
  `patient_phone` varchar(12) CHARACTER SET utf8 DEFAULT NULL COMMENT '手机号',
  `patient_login_id` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '登录id',
  `token` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT 'token',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `ip` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '登录IP',
  `type` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '类型',
  `system` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '手机系统',
  `model` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '手机型号',
  `version` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '前端版本',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='患者登录信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `patient_medical_record`
--

DROP TABLE IF EXISTS `patient_medical_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_medical_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `patinet_id` int(11) DEFAULT NULL COMMENT '病人id',
  `patinet_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '病人名字',
  `diagnose_date` datetime DEFAULT NULL COMMENT '检查时间',
  `diagnose_content` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '检查内容',
  `diagnose_result` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '检查结果',
  `inspection_report_url` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '检查报告url，多个英文逗号隔开',
  `mechanism` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '检查机构',
  `state` int(11) DEFAULT NULL COMMENT '状态：1已通过，2未通过，3待审核',
  `note` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `secretary_id` int(11) DEFAULT NULL COMMENT '秘书id',
  `secretary_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '秘书名字',
  `launch_type` int(11) DEFAULT NULL COMMENT '发起人，1患者，2秘书',
  `createtime` datetime DEFAULT NULL COMMENT '创时间时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='患者病历';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `patient_package`
--

DROP TABLE IF EXISTS `patient_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_package` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(45) DEFAULT NULL COMMENT '套餐名字',
  `patient_id` int(11) DEFAULT NULL COMMENT '患者id',
  `patient_name` varchar(45) DEFAULT NULL COMMENT '患者名字',
  `patient_phone` varchar(12) DEFAULT NULL COMMENT '患者电话',
  `patient_login_id` varchar(45) DEFAULT NULL COMMENT '患者登录id',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `doctors_id` int(11) DEFAULT NULL COMMENT '医生id',
  `doctors_name` varchar(45) DEFAULT NULL COMMENT '医生名字',
  `doctors_phone` varchar(12) DEFAULT NULL COMMENT '医生电话',
  `doctors_login_id` varchar(45) DEFAULT NULL COMMENT '医生登录id',
  `online_consultation` varchar(45) DEFAULT NULL COMMENT '在线咨询',
  `remote_diagnosis` varchar(45) DEFAULT NULL COMMENT '远程诊断',
  `telephone_consultation` varchar(45) DEFAULT NULL COMMENT '电话咨询',
  `offline_appointment` varchar(45) DEFAULT NULL COMMENT '现在预约',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='患者已订购套餐';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `patient_referral`
--

DROP TABLE IF EXISTS `patient_referral`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_referral` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `patient_id` int(11) DEFAULT NULL COMMENT '病人',
  `patient_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '病人名字',
  `patient_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL,
  `patient_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '患者登录id',
  `from_doctors_id` int(11) DEFAULT NULL COMMENT '发起转诊医生id',
  `from_doctors_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '发起转诊医生名字',
  `from_doctors_phone` varchar(12) CHARACTER SET utf8 DEFAULT NULL COMMENT '发起转诊医生手机',
  `from_doctors_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '发起转诊医生登录id',
  `to_doctors_id` int(11) DEFAULT NULL COMMENT '接收转诊医生id',
  `to_doctors_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '接收转诊医生名字',
  `to_doctors_phone` varchar(12) CHARACTER SET utf8 DEFAULT NULL COMMENT '接收转诊医生手机号',
  `to_doctors_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '接收转诊医生登录id',
  `state` int(11) DEFAULT NULL COMMENT '状态',
  `message` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '转诊留言',
  `referraltime` datetime DEFAULT NULL COMMENT '转诊时间',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='病人转诊信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `patient_user`
--

DROP TABLE IF EXISTS `patient_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `password` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '环信密码',
  `login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '登录id，系统或者IM登录的id',
  `phone` varchar(12) CHARACTER SET utf8 DEFAULT NULL COMMENT '手机号',
  `name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
  `sex` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '性别',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `head_url` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '头像地址',
  `location` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '所在地',
  `occupation` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '职业',
  `visit_state` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '就诊地址',
  `hospital` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '就诊医院',
  `remarks` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `easemob_uuid` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='患者用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `polular_science`
--

DROP TABLE IF EXISTS `polular_science`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `polular_science` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `content` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `image_url` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '分类',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_user_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人名字',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='科普';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `request_log`
--

DROP TABLE IF EXISTS `request_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `request_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户名字',
  `user_phone` varchar(12) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户手机号',
  `user_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '用户登录id',
  `user_type` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '用户类型',
  `acction_name` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '请求名称',
  `class_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '类名',
  `method_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '方法',
  `url` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '地址',
  `request` varchar(1000) CHARACTER SET utf8 DEFAULT NULL COMMENT '请求输入',
  `response` varchar(1000) CHARACTER SET utf8 DEFAULT NULL COMMENT '请求输出',
  `client_ip` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '客户端ip',
  `server_ip` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '服务端ip',
  `version` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '版本',
  `system` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '系统',
  `model` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '机型',
  `state` int(11) DEFAULT NULL COMMENT '状态，0初始，1正常，99异常',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4347 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `secretary_doctors_advice`
--

DROP TABLE IF EXISTS `secretary_doctors_advice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `secretary_doctors_advice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `patient_id` int(11) DEFAULT NULL COMMENT '患者id',
  `patient_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '患者名字',
  `patient_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '患者手机号',
  `patient_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '患者登录id',
  `secretary_id` int(11) DEFAULT NULL COMMENT '秘书id',
  `secretary_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '秘书名字',
  `secretary_phone` varchar(12) CHARACTER SET utf8 DEFAULT NULL COMMENT '秘书手机号',
  `secretary_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '秘书登录id',
  `doctors_id` int(11) DEFAULT NULL COMMENT '医生id',
  `doctors_name` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `doctors_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '医生电话',
  `doctors_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `problem` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '问题',
  `advice` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '建议',
  `file_url` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '附件地址',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='小秘书转医生处理意见';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `secretary_doctors_link`
--

DROP TABLE IF EXISTS `secretary_doctors_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `secretary_doctors_link` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `secretary_id` int(11) DEFAULT NULL COMMENT '秘书id',
  `secretary_login_id` varchar(45) DEFAULT NULL COMMENT '秘书登录id',
  `secretary_name` varchar(45) DEFAULT NULL COMMENT '秘书名字',
  `secretary_phone` varchar(12) DEFAULT NULL COMMENT '秘书电话',
  `doctors_id` int(11) DEFAULT NULL COMMENT '医生id',
  `doctors_name` varchar(45) DEFAULT NULL COMMENT '医生名字',
  `doctors_login_id` varchar(45) DEFAULT NULL COMMENT '医生登录id',
  `doctors_phone` varchar(45) DEFAULT NULL COMMENT '医生电话',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='秘书的医生';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `secretary_doctors_patient_chatroom`
--

DROP TABLE IF EXISTS `secretary_doctors_patient_chatroom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `secretary_doctors_patient_chatroom` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `patient_id` int(11) DEFAULT NULL COMMENT '患者id',
  `patient_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '患者名字',
  `patient_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '患者手机号',
  `patient_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '患者登录id',
  `secretary_id` int(11) DEFAULT NULL COMMENT '秘书id',
  `secretary_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '秘书名字',
  `secretary_phone` varchar(12) CHARACTER SET utf8 DEFAULT NULL COMMENT '秘书手机号',
  `secretary_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '秘书登录id',
  `doctors_id` int(11) DEFAULT NULL COMMENT '医生id',
  `doctors_name` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `doctors_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '医生电话',
  `doctors_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `remark` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',
  `chatroom_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '聊天室id',
  `chatroom_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '聊天室名称',
  `chatroom_description` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '聊天室描述',
  `chatroom_owner` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '聊天室的管理员',
  `chatroom_maxusers` int(255) DEFAULT '3' COMMENT '聊天室成员最大数（包括聊天室创建者）',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='小秘书，医生，患者三者聊天室';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `secretary_interrogation_record`
--

DROP TABLE IF EXISTS `secretary_interrogation_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `secretary_interrogation_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `patient_id` int(11) DEFAULT NULL COMMENT '患者id',
  `patient_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '患者名字',
  `patient_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '患者手机号',
  `patient_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '患者登录id',
  `secretary_id` int(11) DEFAULT NULL COMMENT '秘书id',
  `secretary_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '秘书名字',
  `secretary_phone` varchar(12) CHARACTER SET utf8 DEFAULT NULL COMMENT '秘书手机号',
  `secretary_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '秘书登录id',
  `doctors_id` int(11) DEFAULT NULL COMMENT '医生id',
  `doctors_name` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `doctors_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '医生电话',
  `doctors_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `record` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '记录',
  `file_url` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '附件地址',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='小秘书添加问诊记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `secretary_login_info`
--

DROP TABLE IF EXISTS `secretary_login_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `secretary_login_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `secretary_id` int(11) DEFAULT NULL COMMENT '用户id',
  `secretary_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户名字',
  `secretary_phone` varchar(12) CHARACTER SET utf8 DEFAULT NULL COMMENT '手机号',
  `secretary_login_id` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '登录id',
  `token` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT 'token',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `ip` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '登录IP',
  `type` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '类型',
  `system` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '手机系统',
  `model` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '手机型号',
  `version` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '前端版本',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='秘书登录信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `secretary_notice`
--

DROP TABLE IF EXISTS `secretary_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `secretary_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `patient_id` int(11) DEFAULT NULL COMMENT '患者id',
  `patient_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '患者名字',
  `patient_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '患者手机号',
  `patient_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '患者登录id',
  `secretary_id` int(11) DEFAULT NULL COMMENT '秘书id',
  `secretary_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '秘书名字',
  `secretary_phone` varchar(12) CHARACTER SET utf8 DEFAULT NULL COMMENT '秘书手机号',
  `secretary_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '秘书登录id',
  `doctors_id` int(11) DEFAULT NULL COMMENT '医生id',
  `doctors_name` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `doctors_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '医生电话',
  `doctors_login_id` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `title` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '标题',
  `notice` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '通知',
  `batch_number` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '批次号',
  `file_url` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '附件地址',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='小秘书通知单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `secretary_patient_link`
--

DROP TABLE IF EXISTS `secretary_patient_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `secretary_patient_link` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `secretary_id` int(11) DEFAULT NULL COMMENT '秘书id',
  `secretary_login_id` varchar(45) DEFAULT NULL COMMENT '秘书登录id',
  `secretary_name` varchar(45) DEFAULT NULL COMMENT '秘书名字',
  `secretary_phone` varchar(12) DEFAULT NULL COMMENT '秘书电话',
  `patient_id` int(11) DEFAULT NULL COMMENT '患者id',
  `patient_name` varchar(45) DEFAULT NULL COMMENT '患者名字',
  `patient_login_id` varchar(45) DEFAULT NULL COMMENT '患者登录id',
  `patient_phone` varchar(45) DEFAULT NULL COMMENT '患者电话',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='秘书和医生的患者';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `secretary_user`
--

DROP TABLE IF EXISTS `secretary_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `secretary_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `login_id` varchar(45) DEFAULT NULL COMMENT '登陆id',
  `email` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL COMMENT '名字',
  `password` varchar(80) DEFAULT NULL,
  `phone` varchar(12) DEFAULT NULL COMMENT '电话',
  `hospital` varchar(45) DEFAULT NULL COMMENT '医院',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `easemob_pwd` varchar(45) DEFAULT NULL COMMENT '环信登陆密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='秘书用户信息';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-02 10:38:29
