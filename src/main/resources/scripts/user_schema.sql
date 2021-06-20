INSERT INTO `bank_account` (`id`,`user_name`,`account_no`,`bank_name`,`branch_name`,`ifsc_code`) VALUES (1,'Praveen Bajantri','0192000100051701','KARNATAKA BANK','Athani 591304','KAB0000019');
INSERT INTO `bank_account` (`id`,`user_name`,`account_no`,`bank_name`,`branch_name`,`ifsc_code`) VALUES (2,'Praveen Bajantri','0192000100051701','KARNATAKA BANK','Athani 591304','KAB0000019');

#INSERT INTO `users` (`id`,`email`,`gst_no`,`pan_no`,`password`,`phone_number`,`username`,`brand_name`,`bank_account_id`) VALUES (1,'bajantrichetan33@gmail.com','29BAJPB4499Q1ZS','BAJPB4499Q','$2a$10$XSleb92FS0jidzZsosNZNeJvgPZXYDKQAMFW2Fdcyn/.zrQb.D.2S','7338393431/9480157575','chetan','PRAVEEN TRADERS',1);
#INSERT INTO `users` (`id`,`email`,`gst_no`,`pan_no`,`password`,`phone_number`,`username`,`brand_name`,`bank_account_id`) VALUES (2,'user@gmail.com',NULL,NULL,'$2a$10$TaXk2cLcaKM3hGDhS1tPZu3qJGDXFKSy4Zb/WY7eEE8Dfh1TB0yM2',NULL,'user',NULL,NULL);

INSERT INTO `users` (`id`,`brand_name`,`email`,`gst_no`,`image`,`pan_no`,`password`,`phone_number`,`username`,`bank_account_id`) VALUES (1,'PRAVEEN TRADERS','bajantrichetan33@gmail.com','29BAJPB4499Q1ZS',NULL,'BAJPB4499Q','$2a$10$y7D99J7.BaD4dkPsDuZgQu6zHmNoYrPVPSBerddTZ0ytrFxLuKORy','7338393431/9480157575','chetan',1);
INSERT INTO `users` (`id`,`brand_name`,`email`,`gst_no`,`image`,`pan_no`,`password`,`phone_number`,`username`,`bank_account_id`) VALUES (2,'INDIAN KISAN CARE','rakesh@gmail.com','NA',NULL,'NA','$2a$10$njJ6RxxUdrVQSTUYkUmuE.Q/4mZpbNL7hLxG4gdLdBXo9PaYjO96W',NULL,'rakesh',2);

INSERT INTO `roles` (`id`,`name`) VALUES (1,'ROLE_ADMIN');
INSERT INTO `roles` (`id`,`name`) VALUES (2,'ROLE_USER');

INSERT INTO `user_roles` (`user_id`,`role_id`) VALUES (1,1);
INSERT INTO `user_roles` (`user_id`,`role_id`) VALUES (2,2);
