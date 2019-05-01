CREATE TABLE `parent_task` (
  `parent_id` int(10) NOT NULL AUTO_INCREMENT,
  `parent_task` varchar(255) NOT NULL,
  PRIMARY KEY (`parent_id`,`parent_task`),
  UNIQUE KEY `parent_task` (`parent_task`)
);

CREATE TABLE `project` (
  `project_id` int(10) NOT NULL AUTO_INCREMENT,
  `project` varchar(255) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `priority` int(10) NOT NULL,
  PRIMARY KEY (`project_id`),
  UNIQUE KEY `project` (`project`)
);

CREATE TABLE `user` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `employee_id` int(10) NOT NULL,
  `project_id` int(10) DEFAULT NULL,
  `task_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `employee_id` (`employee_id`)
);

CREATE TABLE `task` (
  `task_id` int(10) NOT NULL AUTO_INCREMENT,
  `parent_id` int(10) NOT NULL,
  `project_id` int(10) NOT NULL,
  `task` varchar(255) NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `priority` int(10) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`task_id`),
  UNIQUE KEY `parent_id` (`parent_id`,`task`,`project_id`),
  CONSTRAINT `task_table_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `parent_task` (`parent_id`)
);