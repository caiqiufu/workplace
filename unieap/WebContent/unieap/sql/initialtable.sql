CREATE DATABASE  IF NOT EXISTS `unieap` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `unieap`;
-- MySQL dump 10.13  Distrib 5.6.18, for Win32 (x86)
--
-- Host: localhost    Database: unieap
-- ------------------------------------------------------
-- Server version	5.6.19-enterprise-commercial-advanced

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
-- Table structure for table `dic_data`
--

DROP TABLE IF EXISTS `dic_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dic_data` (
  `group_id` int(11) NOT NULL,
  `dic_id` int(11) NOT NULL,
  `dic_code` varchar(45) NOT NULL,
  `dic_name` varchar(45) NOT NULL,
  `seq` int(2) NOT NULL,
  `create_date` datetime NOT NULL,
  `modify_date` datetime DEFAULT NULL,
  `modify_by` varchar(45) DEFAULT NULL,
  `create_by` varchar(45) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`dic_id`),
  UNIQUE KEY `dic_id_UNIQUE` (`dic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dic_data`
--

/*!40000 ALTER TABLE `dic_data` DISABLE KEYS */;
INSERT  IGNORE INTO `dic_data` VALUES (1003,0,'0','new',1,'2014-05-10 04:24:44',NULL,'','unieap','');
INSERT  IGNORE INTO `dic_data` VALUES (1003,1,'1','assign',2,'2014-05-10 04:25:17',NULL,'','unieap','');
INSERT  IGNORE INTO `dic_data` VALUES (1003,2,'2','confirmed',2,'2014-05-10 04:25:32',NULL,'','unieap','');
INSERT  IGNORE INTO `dic_data` VALUES (1003,3,'3','pending fix',3,'2014-05-10 04:25:47',NULL,'','unieap','');
INSERT  IGNORE INTO `dic_data` VALUES (1003,4,'4','reject',4,'2014-05-10 04:25:59',NULL,'','unieap','');
INSERT  IGNORE INTO `dic_data` VALUES (1003,5,'5','pending retest',5,'2014-05-10 04:26:15',NULL,'','unieap','');
INSERT  IGNORE INTO `dic_data` VALUES (1003,6,'6','resolve',6,'2014-05-10 04:26:28',NULL,'','unieap','');
INSERT  IGNORE INTO `dic_data` VALUES (1003,7,'7','close',7,'2014-05-10 04:26:28',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1006,518,'0','SFT Streat',0,'2014-06-08 01:07:10',NULL,'','unieap','');
INSERT  IGNORE INTO `dic_data` VALUES (1006,519,'1','SIT Streat',1,'2014-06-08 01:07:26',NULL,'','unieap','');
INSERT  IGNORE INTO `dic_data` VALUES (1006,520,'2','IOT Stream',2,'2014-06-08 01:07:49',NULL,'','unieap','');
INSERT  IGNORE INTO `dic_data` VALUES (1007,523,'0','CRM',1,'2014-06-08 01:28:25',NULL,'','unieap','');
INSERT  IGNORE INTO `dic_data` VALUES (1007,524,'1','CBS',2,'2014-06-08 01:28:35','2014-06-08 01:28:43','unieap','unieap','');
INSERT  IGNORE INTO `dic_data` VALUES (1007,525,'2','Mediation',2,'2014-06-08 01:29:00',NULL,'','unieap','');
INSERT  IGNORE INTO `dic_data` VALUES (1005,526,'0','CV1',1,'2014-06-08 01:29:51',NULL,'','unieap','');
INSERT  IGNORE INTO `dic_data` VALUES (1005,527,'1','CV2',2,'2014-06-08 01:30:00',NULL,'','unieap','');
INSERT  IGNORE INTO `dic_data` VALUES (1005,528,'3','Patch1',2,'2014-06-08 01:30:13',NULL,'','unieap','');
INSERT  IGNORE INTO `dic_data` VALUES (1013,529,'issue_view','View Issue',1,'2014-06-08 01:30:29',NULL,'','unieap','');
INSERT  IGNORE INTO `dic_data` VALUES (1013,530,'issue_edit','Edit Issue',2,'2014-06-08 01:30:39',NULL,'','unieap','');
INSERT  IGNORE INTO `dic_data` VALUES (1013,531,'2','View',3,'2014-06-08 01:30:39',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1008,549,'0','CV1',1,'2014-06-08 02:03:41',NULL,'','unieap','');
INSERT  IGNORE INTO `dic_data` VALUES (1008,550,'1','CV2',2,'2014-06-08 02:03:57',NULL,'','unieap','');
INSERT  IGNORE INTO `dic_data` VALUES (1008,551,'2','IOT',3,'2014-06-08 02:04:09','2014-06-29 01:03:53','unieap','unieap','');
INSERT  IGNORE INTO `dic_data` VALUES (1014,574,'1','Altel SIT',2,'2014-06-08 02:23:20',NULL,'','unieap','');
INSERT  IGNORE INTO `dic_data` VALUES (1001,2010,'1','Yes',1,'2014-05-10 04:47:25',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1001,2011,'0','No',2,'2014-05-10 04:47:25',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1002,2020,'0','Low',1,'2014-05-10 04:47:25',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1002,2021,'1','Medium',2,'2014-05-10 04:47:25','2014-06-08 11:15:58','unieap','unieap','555');
INSERT  IGNORE INTO `dic_data` VALUES (1002,2022,'2','High',3,'2014-05-10 04:47:25',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1002,2023,'3','Critical',4,'2014-05-10 04:47:25',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1004,2040,'4','Low',1,'2014-05-10 04:47:25',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1004,2041,'5','Medium',2,'2014-05-10 04:47:25',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1004,2042,'6','High',3,'2014-05-10 04:47:25',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1004,2043,'7','Critical',4,'2014-05-10 04:47:25',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1009,2090,'0','Move Project',1,'2014-05-10 04:47:25',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1009,2092,'2','Assign',3,'2014-05-10 04:47:25',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1009,2096,'6','Update Priority',7,'2014-05-10 04:47:25',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1009,2097,'7','Update Status',8,'2014-05-10 04:47:25',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1009,2098,'8','Add Note',9,'2014-05-10 04:47:25',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1009,2099,'5','Update Severity',5,'2014-05-10 04:47:25',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1010,2100,'0','View',0,'2014-05-10 04:47:25',NULL,NULL,'unieap','Can only browse and view bug listing');
INSERT  IGNORE INTO `dic_data` VALUES (1010,2101,'1','Developer',1,'2014-05-10 04:47:25',NULL,NULL,'unieap','Who analysis, study the defect and fix ');
INSERT  IGNORE INTO `dic_data` VALUES (1010,2102,'2','Management',2,'2014-05-10 04:47:25',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1010,2103,'3','Repoter',3,'2014-05-10 04:47:25',NULL,NULL,'unieap','Can report new bugs and add bugnotes,He/She can edit and reopen own issue only');
INSERT  IGNORE INTO `dic_data` VALUES (1011,2111,'0','Failed',0,'2014-05-10 04:47:25',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1011,2112,'1','Successfull',1,'2014-05-10 04:47:25',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1011,2113,'2','Pending',2,'2014-05-10 04:47:25',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1014,2140,'0','Altel SFT',1,'2014-05-10 04:47:25','2014-06-08 02:22:50','unieap','unieap','');
INSERT  IGNORE INTO `dic_data` VALUES (1014,2141,'-1','All',0,'2014-05-10 04:47:25',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1015,2151,'1','Normal',1,'2014-05-10 04:47:25',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1015,2152,'2','Pass',2,'2014-05-10 04:47:25',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1015,2153,'3','Failed',3,'2014-05-10 04:47:25',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1015,2154,'4','Block',4,'2014-05-10 04:47:25',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1015,2155,'5','Closed',5,'2014-05-10 04:47:25',NULL,NULL,'unieap',NULL);
INSERT  IGNORE INTO `dic_data` VALUES (1012,2201,'main','Main',0,'2014-05-10 04:47:25',NULL,NULL,'unieap','toFrame(\'main\')');
INSERT  IGNORE INTO `dic_data` VALUES (1012,2202,'myview','My View',1,'2014-05-10 04:47:25',NULL,NULL,'unieap','toFrame(\'myview\')');
INSERT  IGNORE INTO `dic_data` VALUES (1012,2203,'viewissues','View Issues',2,'2014-05-10 04:47:25',NULL,NULL,'unieap','toFrame(\'viewissues\')');
INSERT  IGNORE INTO `dic_data` VALUES (1012,2204,'reportissue','Report Issue',3,'2014-05-10 04:47:25',NULL,NULL,'unieap','toFrame(\'reportissue\')');
INSERT  IGNORE INTO `dic_data` VALUES (1012,2206,'summary','Summary',5,'2014-05-10 04:47:25',NULL,NULL,'unieap','toFrame(\'summary\')');
INSERT  IGNORE INTO `dic_data` VALUES (1012,2207,'myaccount','My Account',6,'2014-05-10 04:47:25',NULL,NULL,'unieap','toFrame(\'myaccount\')');
INSERT  IGNORE INTO `dic_data` VALUES (1012,2208,'logout','Logout',7,'2014-05-10 04:47:25',NULL,NULL,'unieap','logout()');
INSERT  IGNORE INTO `dic_data` VALUES (1012,2209,'management','Management',8,'2014-05-10 04:47:25',NULL,NULL,'unieap','toManaement()');
INSERT  IGNORE INTO `dic_data` VALUES (1012,2210,'testcase','Test Case',9,'2014-05-10 04:47:25',NULL,NULL,'unieap','toFrame(\'testcases\')');
INSERT  IGNORE INTO `dic_data` VALUES (1012,2211,'importtc','Import Test Case',10,'2014-05-10 04:47:25',NULL,NULL,'unieap','toFrame(\'importtc\')');
/*!40000 ALTER TABLE `dic_data` ENABLE KEYS */;

--
-- Table structure for table `dic_group`
--

DROP TABLE IF EXISTS `dic_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dic_group` (
  `group_id` int(11) NOT NULL,
  `group_name` varchar(45) NOT NULL,
  `create_date` datetime NOT NULL,
  `modify_date` datetime DEFAULT NULL,
  `create_by` varchar(45) NOT NULL,
  `modify_by` varchar(45) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`group_id`),
  UNIQUE KEY `id_UNIQUE` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dic_group`
--

/*!40000 ALTER TABLE `dic_group` DISABLE KEYS */;
INSERT  IGNORE INTO `dic_group` VALUES (1001,'Enable','2014-05-10 04:46:59',NULL,'unieap',NULL,'enable');
INSERT  IGNORE INTO `dic_group` VALUES (1002,'Severity','2014-05-10 04:46:59',NULL,'unieap',NULL,NULL);
INSERT  IGNORE INTO `dic_group` VALUES (1003,'Defect Status','2014-05-10 04:46:59',NULL,'unieap',NULL,NULL);
INSERT  IGNORE INTO `dic_group` VALUES (1004,'Priority','2014-05-10 04:46:59',NULL,'unieap',NULL,NULL);
INSERT  IGNORE INTO `dic_group` VALUES (1005,'Product Version','2014-05-10 04:46:59',NULL,'unieap',NULL,NULL);
INSERT  IGNORE INTO `dic_group` VALUES (1006,'Test Streams','2014-05-10 04:46:59',NULL,'unieap',NULL,NULL);
INSERT  IGNORE INTO `dic_group` VALUES (1007,'Sub Stream','2014-05-10 04:46:59',NULL,'unieap',NULL,NULL);
INSERT  IGNORE INTO `dic_group` VALUES (1008,'Test Plan','2014-05-10 04:46:59',NULL,'unieap',NULL,NULL);
INSERT  IGNORE INTO `dic_group` VALUES (1009,'Batch Update','2014-05-10 04:46:59',NULL,'unieap',NULL,NULL);
INSERT  IGNORE INTO `dic_group` VALUES (1010,'Role','2014-05-10 04:46:59',NULL,'unieap',NULL,NULL);
INSERT  IGNORE INTO `dic_group` VALUES (1011,'Task Status','2014-05-10 04:46:59',NULL,'unieap',NULL,NULL);
INSERT  IGNORE INTO `dic_group` VALUES (1012,'Menu ','2014-05-10 04:46:59',NULL,'unieap',NULL,NULL);
INSERT  IGNORE INTO `dic_group` VALUES (1013,'Button','2014-05-10 04:46:59',NULL,'unieap',NULL,NULL);
INSERT  IGNORE INTO `dic_group` VALUES (1014,'Project','2014-05-10 04:46:59',NULL,'unieap',NULL,NULL);
INSERT  IGNORE INTO `dic_group` VALUES (1015,'Test Status','2014-05-10 04:46:59',NULL,'unieap',NULL,NULL);
/*!40000 ALTER TABLE `dic_group` ENABLE KEYS */;

--
-- Table structure for table `email_send`
--

DROP TABLE IF EXISTS `email_send`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_send` (
  `send_id` int(11) NOT NULL,
  `email` varchar(1024) NOT NULL,
  `content` blob,
  `create_date` datetime NOT NULL,
  `send_date` datetime DEFAULT NULL,
  `status` varchar(45) NOT NULL,
  `description` varchar(512) DEFAULT NULL,
  `times` int(11) DEFAULT NULL,
  `subject` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`send_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email_send`
--
--
-- Table structure for table `handler_config`
--

DROP TABLE IF EXISTS `handler_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `handler_config` (
  `handler_id` int(11) NOT NULL,
  `handler_name` varchar(45) NOT NULL,
  `class_name` varchar(256) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  `active_flag` varchar(45) NOT NULL,
  PRIMARY KEY (`handler_id`),
  UNIQUE KEY `idhandler_id_UNIQUE` (`handler_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `handler_config`
--

/*!40000 ALTER TABLE `handler_config` DISABLE KEYS */;
INSERT  IGNORE INTO `handler_config` VALUES (1,'Defect All','com.unieap.mantis.bo.DefectExportHandler','Defect All','Y');
INSERT  IGNORE INTO `handler_config` VALUES (2,'Import TC ','com.unieap.mantis.bo.TestCaseImportHandler','Import TC ','Y');
/*!40000 ALTER TABLE `handler_config` ENABLE KEYS */;

--
-- Table structure for table `m_atta`
--

DROP TABLE IF EXISTS `m_atta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_atta` (
  `atta_id` int(11) NOT NULL,
  `defect_id` int(11) NOT NULL,
  `file_name` varchar(128) NOT NULL,
  `file_size` varchar(45) NOT NULL,
  `file_path` varchar(512) NOT NULL,
  `create_date` datetime NOT NULL,
  `file_cont` blob,
  `file_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`atta_id`),
  UNIQUE KEY `atta_id_UNIQUE` (`atta_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_atta`
--


--
-- Table structure for table `m_chglog`
--

DROP TABLE IF EXISTS `m_chglog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_chglog` (
  `log_id` int(11) NOT NULL,
  `user_name` varchar(45) NOT NULL,
  `modify_date` datetime NOT NULL,
  `modify_type` varchar(45) NOT NULL,
  `display_name` varchar(45) NOT NULL,
  `old_value` varchar(45) DEFAULT NULL,
  `new_value` varchar(45) DEFAULT NULL,
  `field_name` varchar(45) DEFAULT NULL,
  `record_id` int(11) NOT NULL,
  PRIMARY KEY (`log_id`),
  UNIQUE KEY `log_id_UNIQUE` (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chglog`
--
DROP TABLE IF EXISTS `chglog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chglog` (
  `log_id` int(11) NOT NULL,
  `user_name` varchar(45) NOT NULL,
  `modify_date` datetime NOT NULL,
  `modify_type` varchar(45) NOT NULL,
  `display_name` varchar(45) NOT NULL,
  `old_value` varchar(45) DEFAULT NULL,
  `new_value` varchar(45) DEFAULT NULL,
  `field_name` varchar(45) DEFAULT NULL,
  `record_id` int(11) NOT NULL,
  PRIMARY KEY (`log_id`),
  UNIQUE KEY `log_id_UNIQUE` (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `m_defect`
--

DROP TABLE IF EXISTS `m_defect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_defect` (
  `defect_id` int(11) NOT NULL,
  `prod_version` varchar(45) DEFAULT NULL,
  `test_stream` varchar(45) NOT NULL,
  `severity` varchar(45) DEFAULT NULL,
  `priority` varchar(45) DEFAULT NULL,
  `sub_stream` varchar(45) NOT NULL,
  `test_plan` varchar(45) NOT NULL,
  `tc_id` varchar(45) NOT NULL,
  `title` varchar(255) NOT NULL,
  `descpt` varchar(512) NOT NULL,
  `remark` varchar(512) DEFAULT NULL,
  `steps` varchar(255) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `modify_date` datetime DEFAULT NULL,
  `modify_by` varchar(45) DEFAULT NULL,
  `create_by` varchar(45) NOT NULL,
  `status` varchar(45) DEFAULT NULL,
  `assignto` varchar(45) DEFAULT NULL,
  `target_date` datetime DEFAULT NULL,
  `project` varchar(45) NOT NULL,
  `dts` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`defect_id`),
  UNIQUE KEY `defect_id_UNIQUE` (`defect_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_defect`
--



--
-- Table structure for table `m_note`
--

DROP TABLE IF EXISTS `m_note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_note` (
  `note_id` int(11) NOT NULL,
  `defect_id` int(11) NOT NULL,
  `description` blob NOT NULL,
  `create_date` datetime NOT NULL,
  `modify_date` datetime DEFAULT NULL,
  `modify_by` varchar(45) DEFAULT NULL,
  `create_by` varchar(45) NOT NULL,
  `enable` varchar(45) NOT NULL,
  PRIMARY KEY (`note_id`),
  UNIQUE KEY `note_id_UNIQUE` (`note_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_note`
--



--
-- Table structure for table `m_testcase`
--

DROP TABLE IF EXISTS `m_testcase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_testcase` (
  `tc_id` int(11) NOT NULL,
  `tc_code` varchar(45) NOT NULL,
  `tc_name` varchar(256) DEFAULT NULL,
  `tc_description` blob,
  `test_stream` varchar(45) DEFAULT NULL,
  `sub_stream` varchar(45) DEFAULT NULL,
  `tester` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `test_result` blob,
  `remark` varchar(512) DEFAULT NULL,
  `create_by` varchar(45) NOT NULL,
  `create_date` datetime NOT NULL,
  `modify_by` varchar(45) DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `project` varchar(45) NOT NULL,
  PRIMARY KEY (`tc_id`),
  UNIQUE KEY `testcase_id_UNIQUE` (`tc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_testcase`
--


--
-- Table structure for table `r_exelog`
--

DROP TABLE IF EXISTS `r_exelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_exelog` (
  `id` int(11) NOT NULL,
  `execute_date` datetime NOT NULL,
  `biz_date` varchar(45) DEFAULT NULL,
  `type_code` varchar(45) NOT NULL,
  `type_name` varchar(45) DEFAULT NULL,
  `result` varchar(45) DEFAULT NULL,
  `resultdesc` blob,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_exelog`
--


--
-- Table structure for table `r_status`
--

DROP TABLE IF EXISTS `r_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_status` (
  `id` int(11) NOT NULL,
  `datetime` date NOT NULL,
  `status` varchar(45) NOT NULL,
  `amount` int(8) NOT NULL,
  `project` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_status`
--



--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL,
  `role_code` varchar(45) NOT NULL,
  `role_name` varchar(45) NOT NULL,
  `create_date` datetime NOT NULL,
  `create_by` varchar(45) NOT NULL,
  `modify_date` datetime DEFAULT NULL,
  `modify_by` varchar(45) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_id_UNIQUE` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT  IGNORE INTO `role` VALUES (1,'admin','admin','2014-06-18 00:09:30','unieap','2014-06-18 00:09:45','unieap','22556');
INSERT  IGNORE INTO `role` VALUES (23,'Project SIT','Project SIT','2014-06-28 23:22:18','unieap',NULL,'','');
INSERT  IGNORE INTO `role` VALUES (27,'Normal Menu','Normal Menu','2014-06-28 23:31:42','unieap',NULL,'','');
INSERT  IGNORE INTO `role` VALUES (43,'Normal Dic data','Normal Dic data','2014-06-28 23:47:05','unieap',NULL,'','');
INSERT  IGNORE INTO `role` VALUES (319,'ManagerMenu','Manager Menu','2014-07-13 13:16:57','unieap',NULL,'','');
INSERT  IGNORE INTO `role` VALUES (330,'viewdefect','Veiw Defect','2014-07-13 13:26:31','unieap',NULL,'','');
INSERT  IGNORE INTO `role` VALUES (331,'defectManager','Defect Manager','2014-07-13 13:26:56','unieap',NULL,'','');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;

--
-- Table structure for table `role_resource`
--

DROP TABLE IF EXISTS `role_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_resource` (
  `role_resource_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `resource_id` varchar(45) NOT NULL,
  `resource_type` varchar(45) NOT NULL,
  `category` varchar(45) DEFAULT NULL,
  `resource_attr1` varchar(45) DEFAULT NULL,
  `resource_attr2` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`role_resource_id`),
  UNIQUE KEY `role_resource_id_UNIQUE` (`role_resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_resource`
--

/*!40000 ALTER TABLE `role_resource` DISABLE KEYS */;
INSERT  IGNORE INTO `role_resource` VALUES (26,23,'1','0','1014',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (28,27,'logout','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (29,27,'myaccount','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (30,27,'viewissues','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (32,27,'summary','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (33,27,'myview','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (34,27,'reportissue','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (44,43,'5','0','1009',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (45,43,'6','0','1009',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (46,43,'8','0','1009',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (47,43,'2','0','1009',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (48,43,'7','0','1009',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (49,43,'0','0','1009',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (50,43,'2','0','1013',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (51,43,'issue_edit','0','1013',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (52,43,'issue_view','0','1013',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (53,43,'7','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (54,43,'4','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (55,43,'1','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (56,43,'6','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (57,43,'3','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (58,43,'0','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (59,43,'5','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (60,43,'2','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (61,43,'0','0','1001',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (62,43,'1','0','1001',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (63,43,'7','0','1004',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (64,43,'4','0','1004',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (65,43,'6','0','1004',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (66,43,'5','0','1004',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (67,43,'3','0','1005',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (68,43,'1','0','1005',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (69,43,'0','0','1005',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (70,43,'2','0','1002',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (71,43,'1','0','1002',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (72,43,'3','0','1002',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (73,43,'0','0','1002',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (74,43,'2','0','1007',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (75,43,'1','0','1007',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (76,43,'0','0','1007',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (77,43,'1','0','1011',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (78,43,'0','0','1011',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (79,43,'2','0','1011',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (80,43,'2','0','1008',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (81,43,'1','0','1008',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (82,43,'0','0','1008',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (83,43,'2','0','1006',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (84,43,'1','0','1006',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (85,43,'0','0','1006',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (86,43,'0','0','1014',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (87,43,'1','0','1014',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (120,1,'testcase','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (130,1,'importtc','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (194,1,'issue_edit','0','1013',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (195,1,'issue_view','0','1013',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (196,1,'5','0','1009',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (197,1,'6','0','1009',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (198,1,'8','0','1009',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (199,1,'2','0','1009',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (200,1,'7','0','1009',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (201,1,'0','0','1009',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (202,1,'2','0','1013',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (203,1,'7','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (204,1,'4','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (205,1,'1','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (206,1,'6','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (207,1,'3','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (208,1,'0','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (209,1,'5','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (210,1,'2','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (211,1,'1','0','1001',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (212,1,'0','0','1001',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (213,1,'main','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (214,1,'7','0','1004',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (215,1,'4','0','1004',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (216,1,'6','0','1004',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (217,1,'5','0','1004',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (218,1,'1','0','1005',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (219,1,'0','0','1005',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (220,1,'3','0','1005',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (221,1,'0','0','1014',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (222,1,'1','0','1014',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (223,1,'-1','0','1014',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (224,1,'2','0','1010',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (225,1,'1','0','1010',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (226,1,'3','0','1010',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (228,1,'1','0','1002',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (229,1,'3','0','1002',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (230,1,'0','0','1002',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (231,1,'2','0','1002',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (232,1,'1','0','1007',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (233,1,'0','0','1007',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (234,1,'2','0','1007',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (235,1,'0','0','1011',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (236,1,'2','0','1011',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (237,1,'1','0','1011',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (238,1,'1','0','1008',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (239,1,'0','0','1008',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (240,1,'2','0','1008',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (241,1,'2','0','1006',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (242,1,'1','0','1006',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (243,1,'0','0','1006',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (313,1,'0','0','1010',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (314,1,'3','0','1015',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (315,1,'5','0','1015',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (316,1,'2','0','1015',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (317,1,'4','0','1015',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (318,1,'1','0','1015',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (320,319,'myview','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (321,319,'summary','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (322,319,'management','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (323,319,'importtc','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (324,319,'logout','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (325,319,'reportissue','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (326,319,'main','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (327,319,'testcase','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (328,319,'myaccount','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (329,319,'viewissues','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (332,331,'8','0','1009',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (333,331,'2','0','1009',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (334,331,'7','0','1009',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (335,331,'0','0','1009',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (336,331,'5','0','1009',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (337,331,'6','0','1009',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (338,331,'issue_view','0','1013',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (339,331,'2','0','1013',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (340,331,'issue_edit','0','1013',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (341,331,'6','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (342,331,'3','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (343,331,'0','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (344,331,'5','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (345,331,'2','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (346,331,'7','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (347,331,'4','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (348,331,'1','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (349,331,'0','0','1001',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (350,331,'1','0','1001',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (351,331,'management','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (352,331,'summary','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (353,331,'myview','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (354,331,'importtc','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (355,331,'logout','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (356,331,'reportissue','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (358,331,'testcase','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (359,331,'myaccount','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (360,331,'viewissues','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (361,331,'6','0','1004',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (362,331,'5','0','1004',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (363,331,'7','0','1004',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (364,331,'4','0','1004',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (365,331,'0','0','1005',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (366,331,'3','0','1005',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (367,331,'1','0','1005',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (368,331,'1','0','1014',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (369,331,'-1','0','1014',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (370,331,'0','0','1014',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (371,331,'3','0','1010',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (373,331,'2','0','1010',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (374,331,'1','0','1010',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (375,331,'3','0','1002',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (376,331,'0','0','1002',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (377,331,'2','0','1002',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (378,331,'1','0','1002',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (379,331,'0','0','1007',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (380,331,'2','0','1007',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (381,331,'1','0','1007',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (382,331,'2','0','1011',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (383,331,'1','0','1011',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (384,331,'0','0','1011',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (385,331,'0','0','1008',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (386,331,'2','0','1008',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (387,331,'1','0','1008',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (388,331,'4','0','1015',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (389,331,'1','0','1015',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (390,331,'3','0','1015',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (391,331,'5','0','1015',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (392,331,'2','0','1015',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (394,331,'2','0','1006',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (395,331,'1','0','1006',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (396,330,'8','0','1009',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (397,330,'2','0','1009',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (398,330,'7','0','1009',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (399,330,'0','0','1009',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (400,330,'5','0','1009',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (401,330,'6','0','1009',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (402,330,'issue_view','0','1013',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (403,330,'2','0','1013',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (404,330,'issue_edit','0','1013',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (405,330,'6','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (406,330,'3','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (407,330,'0','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (408,330,'5','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (409,330,'2','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (410,330,'7','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (411,330,'4','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (412,330,'1','0','1003',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (413,330,'0','0','1001',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (414,330,'1','0','1001',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (415,330,'myview','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (416,330,'logout','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (417,330,'main','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (418,330,'myaccount','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (419,330,'viewissues','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (420,330,'6','0','1004',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (421,330,'5','0','1004',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (422,330,'7','0','1004',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (423,330,'4','0','1004',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (424,330,'0','0','1005',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (425,330,'3','0','1005',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (426,330,'1','0','1005',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (427,330,'1','0','1014',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (428,330,'-1','0','1014',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (429,330,'0','0','1014',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (430,330,'3','0','1010',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (431,330,'0','0','1010',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (432,330,'2','0','1010',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (433,330,'1','0','1010',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (434,330,'3','0','1002',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (435,330,'0','0','1002',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (436,330,'2','0','1002',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (437,330,'1','0','1002',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (438,330,'0','0','1007',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (439,330,'2','0','1007',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (440,330,'1','0','1007',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (441,330,'2','0','1011',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (442,330,'1','0','1011',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (443,330,'0','0','1011',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (444,330,'0','0','1008',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (445,330,'2','0','1008',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (446,330,'1','0','1008',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (447,330,'4','0','1015',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (448,330,'1','0','1015',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (449,330,'3','0','1015',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (450,330,'5','0','1015',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (451,330,'2','0','1015',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (452,330,'0','0','1006',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (453,330,'2','0','1006',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (454,330,'1','0','1006',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (455,331,'main','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (456,331,'0','0','1010',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (2488,1,'viewissues','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (2489,1,'summary','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (2491,1,'reportissue','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (2492,1,'myview','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (2496,1,'logout','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (2498,1,'management','0','1012',NULL,NULL);
INSERT  IGNORE INTO `role_resource` VALUES (2499,1,'myaccount','0','1012',NULL,NULL);
/*!40000 ALTER TABLE `role_resource` ENABLE KEYS */;

--
-- Table structure for table `seq`
--

DROP TABLE IF EXISTS `seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `seq` (
  `seq_name` varchar(45) NOT NULL,
  `val` int(11) NOT NULL,
  PRIMARY KEY (`seq_name`),
  UNIQUE KEY `seq_name_UNIQUE` (`seq_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seq`
--

/*!40000 ALTER TABLE `seq` DISABLE KEYS */;
INSERT  IGNORE INTO `seq` VALUES ('defect',10000);
INSERT  IGNORE INTO `seq` VALUES ('unieap',1);
/*!40000 ALTER TABLE `seq` ENABLE KEYS */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `user_code` varchar(45) NOT NULL,
  `user_name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `enable` varchar(16) NOT NULL,
  `expired` varchar(16) NOT NULL,
  `locked` varchar(16) NOT NULL,
  `create_date` datetime NOT NULL,
  `modify_date` datetime DEFAULT NULL,
  `modify_by` varchar(45) DEFAULT NULL,
  `create_by` varchar(45) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT  IGNORE INTO `user` VALUES (1,'unieap','unieap1','1','1','0','0','2014-05-03 02:26:36','2014-06-14 01:04:37','unieap','unieap','55','caiqiufu@gmail.com');
INSERT  IGNORE INTO `user` VALUES (417,'test','test','altel123','1','0','0','2014-06-05 23:42:15',NULL,'','unieap','','caiqiufu@sohu.com');
INSERT  IGNORE INTO `user` VALUES (575,'quzengfeng','quzengfeng','altel123','1','0','0','2014-06-08 02:24:37',NULL,'','unieap','','quzengfen@huawei.com');
INSERT  IGNORE INTO `user` VALUES (624,'wushengfang','wushengfang','altel123','1','0','0','2014-06-08 02:24:57','2014-06-18 02:05:32','unieap','unieap','4454','wushengfang@gmail.com');
INSERT  IGNORE INTO `user` VALUES (673,'huangfatao','huangfatao','altel123','1','0','0','2014-06-08 02:25:18',NULL,'','unieap','','huangfatao@gmail.com');
INSERT  IGNORE INTO `user` VALUES (722,'yangbo','yangbo','altel123','1','0','0','2014-06-08 02:25:39',NULL,'','unieap','','yangbo@gmail.com');
INSERT  IGNORE INTO `user` VALUES (2287,'fangfang','fangfang','fangfang','1','0','0','2014-06-14 01:46:46',NULL,'','unieap1','','caiqiufu@gmail.com');
INSERT  IGNORE INTO `user` VALUES (2335,'fangfang1','qufangfang','fangfang1','1','0','0','2014-06-14 01:51:44',NULL,'','unieap1','','caiqiufu@gmail.com');
INSERT  IGNORE INTO `user` VALUES (2558,'4355556','56566767','4355556','1','0','0','2014-06-18 02:05:51',NULL,'','unieap','','676@huawei.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `user_role_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_role_id`),
  UNIQUE KEY `user_role_id_UNIQUE` (`user_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT  IGNORE INTO `user_role` VALUES (1,1,1);
INSERT  IGNORE INTO `user_role` VALUES (35,722,27);
INSERT  IGNORE INTO `user_role` VALUES (36,722,23);
INSERT  IGNORE INTO `user_role` VALUES (88,722,43);
INSERT  IGNORE INTO `user_role` VALUES (312,575,23);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;

--
-- Table structure for table `visit_log`
--

DROP TABLE IF EXISTS `visit_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `visit_log` (
  `log_id` int(11) NOT NULL,
  `user_code` varchar(45) NOT NULL,
  `remote_ip` varchar(45) NOT NULL,
  `login_date` datetime DEFAULT NULL,
  `logout_date` datetime DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visit_log`
--



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-07-13 21:24:06
