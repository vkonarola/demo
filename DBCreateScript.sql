delimiter $$

DROP DATABASE IF EXISTS taskmanager$$
CREATE DATABASE taskmanager$$
USE taskmanager$$

DROP TABLE IF EXISTS `task`$$
DROP TABLE IF EXISTS `task_master`$$
DROP TABLE IF EXISTS `project_user`$$
DROP TABLE IF EXISTS `project_details`$$
DROP TABLE IF EXISTS `project`$$
DROP TABLE IF EXISTS `tuser`$$
DROP TABLE IF EXISTS `role`$$

CREATE TABLE `role` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `createdon` datetime(6) NOT NULL,
  `role` varchar(50) NOT NULL,
  `updatedon` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `UK_bjxn5ii7v7ygwx39et0wawu0q` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci$$

INSERT INTO `role` (`role_id`, `createdon`, `role`) VALUES ('3', '2020-12-15 16:54:35.965000', 'Admin')$$
INSERT INTO `role` (`role_id`, `createdon`, `role`) VALUES ('57', '2020-12-15 16:54:35.965000', 'Employee')$$
INSERT INTO `role` (`role_id`, `createdon`, `role`) VALUES ('58', '2020-12-15 16:54:35.965000', 'Team Leader')$$
INSERT INTO `role` (`role_id`, `createdon`, `role`) VALUES ('72', '2020-12-15 16:54:35.965000', 'Project Manager')$$
INSERT INTO `role` (`role_id`, `createdon`, `role`) VALUES ('107', '2020-12-15 16:54:35.965000', 'Project Manager Prernatemp & ChiragL')$$
INSERT INTO `role` (`role_id`, `createdon`, `role`) VALUES ('112', '2020-12-15 16:54:35.965000', 'Director of operations')$$
INSERT INTO `role` (`role_id`, `createdon`, `role`) VALUES ('113', '2020-12-15 16:54:35.965000', 'Technical Leader')$$
INSERT INTO `role` (`role_id`, `createdon`, `role`) VALUES ('117', '2020-12-15 16:54:35.965000', 'Project Manager - Shaishav')$$

CREATE TABLE `tuser` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `createdon` datetime(6) NOT NULL,
  `easy_collab_id` int DEFAULT NULL,
  `email_id` varchar(50) NOT NULL,
  `is_thirdparty_user` bit(1) NOT NULL,
  `name` varchar(50) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `updatedon` datetime(6) DEFAULT NULL,
  `role_id` int NOT NULL,
  `spark_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_fygcja7eyxxfqaglprsu6pfmv` (`email_id`),
  KEY `FKba4ujwut9hkolc2f5unc7lslb` (`role_id`),
  CONSTRAINT `FKba4ujwut9hkolc2f5unc7lslb` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci$$

INSERT INTO `tuser` (`user_id`, `createdon`, `email_id`, `is_thirdparty_user`, `name`, `role_id`) VALUES ('1', '2020-12-15 16:54:50.834000', 'system@narola.email', 0, 'system', '3')$$

CREATE TABLE `project` (
  `id` int NOT NULL AUTO_INCREMENT,
  `project_name` varchar(100) NOT NULL,
  `easy_collab_proj_id` varchar(255) DEFAULT NULL,
  `easy_collab_reference_id` varchar(255) DEFAULT NULL,
  `created_by` int DEFAULT NULL,
  `updatedon` datetime(6) DEFAULT NULL,
  `createdon` datetime NOT NULL,
  `google_spread_sheet_id` varchar(255) DEFAULT NULL,
  `google_sheet_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pbfm0xrdqw1andx15423o4ltf` (`project_name`),
  KEY `FKs0excyjj7uno5puciid11vl4u` (`created_by`),
  CONSTRAINT `FKs0excyjj7uno5puciid11vl4u` FOREIGN KEY (`created_by`) REFERENCES `tuser` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci$$

CREATE TABLE `project_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `client_emails` varchar(255) DEFAULT NULL,
  `client_name` varchar(255) DEFAULT NULL,
  `createdon` datetime(6) NOT NULL,
  `tl_email` varchar(255) DEFAULT NULL,
  `tl_name` varchar(255) DEFAULT NULL,
  `updatedon` datetime(6) DEFAULT NULL,
  `project_id` int NOT NULL,
  `resources` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK570nn95xef5c5edaa97sash7w` (`project_id`),
  CONSTRAINT `FK570nn95xef5c5edaa97sash7w` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci$$

CREATE TABLE `project_user` (
  `project_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`project_id`,`user_id`),
  KEY `FK5v1e1rxjr4r5o0cftp1aihmmc` (`user_id`),
  CONSTRAINT `FK4ug72llnm0n7yafwntgdswl3y` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `FK5v1e1rxjr4r5o0cftp1aihmmc` FOREIGN KEY (`user_id`) REFERENCES `tuser` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci$$

CREATE TABLE `task_master` (
  `id` int NOT NULL AUTO_INCREMENT,
  `createdon` datetime(6) NOT NULL,
  `easy_collab_task_id` varchar(255) DEFAULT NULL,
  `task_title` varchar(255) NOT NULL,
  `updatedon` datetime(6) DEFAULT NULL,
  `created_by` int NOT NULL,
  `project_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrd538088pvk9d1q2ok9jox3sp` (`created_by`),
  KEY `FKgvt4rajpnjecopnfew6g7dsjj` (`project_id`),
  KEY `FKkgxitwyvdlnrkocuxd3r4pk0` (`user_id`),
  CONSTRAINT `FKgvt4rajpnjecopnfew6g7dsjj` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `FKkgxitwyvdlnrkocuxd3r4pk0` FOREIGN KEY (`user_id`) REFERENCES `tuser` (`user_id`),
  CONSTRAINT `FKrd538088pvk9d1q2ok9jox3sp` FOREIGN KEY (`created_by`) REFERENCES `tuser` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci$$

CREATE TABLE `task` (
  `id` int NOT NULL AUTO_INCREMENT,
  `actual_hr` double DEFAULT NULL,
  `createdon` datetime(6) NOT NULL,
  `est_hr` double NOT NULL,
  `is_eod_update` bit(1) NOT NULL DEFAULT b'0',
  `is_planned` bit(1) NOT NULL,
  `is_submitted_to_easycollab` bit(1) NOT NULL DEFAULT b'0',
  `is_traker_used` bit(1) NOT NULL DEFAULT b'0',
  `status` int DEFAULT NULL,
  `task_desc` varchar(255) NOT NULL,
  `updatedon` datetime(6) DEFAULT NULL,
  `task_master_id` int NOT NULL,
  `is_submitted_to_googlesheet` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `FKnl7n9yp8ffj4l9khhm2felddd` (`task_master_id`),
  CONSTRAINT `FKnl7n9yp8ffj4l9khhm2felddd` FOREIGN KEY (`task_master_id`) REFERENCES `task_master` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci$$