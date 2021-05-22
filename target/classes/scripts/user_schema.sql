INSERT INTO `bank_account` (`id`,`user_name`,`account_no`,`bank_name`,`branch_name`,`ifsc_code`) VALUES (1,'Praveen Bajantri','0192000100051701','KARNATAKA BANK','Athani 591304','KAB0000019');

INSERT INTO `users` (`id`,`email`,`gst_no`,`pan_no`,`password`,`phone_number`,`username`,`brand_name`,`bank_account_id`) VALUES (1,'bajantrichetan33@gmail.com','29BAJPB4499Q1ZS','BAJPB4499Q','$2a$10$XSleb92FS0jidzZsosNZNeJvgPZXYDKQAMFW2Fdcyn/.zrQb.D.2S','7338393431/9480157575','chetan','PRAVEEN TRADERS',1);
INSERT INTO `users` (`id`,`email`,`gst_no`,`pan_no`,`password`,`phone_number`,`username`,`brand_name`,`bank_account_id`) VALUES (2,'user@gmail.com',NULL,NULL,'$2a$10$TaXk2cLcaKM3hGDhS1tPZu3qJGDXFKSy4Zb/WY7eEE8Dfh1TB0yM2',NULL,'user',NULL,NULL);

INSERT INTO `roles` (`id`,`name`) VALUES (1,'ROLE_ADMIN');
INSERT INTO `roles` (`id`,`name`) VALUES (2,'ROLE_USER');

INSERT INTO `user_roles` (`user_id`,`role_id`) VALUES (1,1);
INSERT INTO `user_roles` (`user_id`,`role_id`) VALUES (2,2);
