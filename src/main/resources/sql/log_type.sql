CREATE TABLE `log_type` (
  `log_type_id` varchar(5) NOT NULL,
  `log_name` varchar(50) NOT NULL,
  `log_image` varchar(5000) NOT NULL,
  `log_class` varchar(100) NOT NULL,
  PRIMARY KEY (`log_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=big5;
