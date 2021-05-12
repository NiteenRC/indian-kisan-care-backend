INSERT INTO `Category` (`category_id`,`category_desc`,`category_name`,`created_date`) VALUES (1,'Fertilizer desc','Fertilizer','2021-05-12 16:55:52');
INSERT INTO `Category` (`category_id`,`category_desc`,`category_name`,`created_date`) VALUES (2,'Seed desc','Seed','2021-05-12 16:56:07');
INSERT INTO `Category` (`category_id`,`category_desc`,`category_name`,`created_date`) VALUES (3,'Pesticide desc','Pesticide','2021-05-12 16:56:20');

INSERT INTO `Product` (`product_id`,`created_date`,`current_price`,`price`,`product_desc`,`product_name`,`qty`,`category_id`) VALUES (1,'2021-05-12 17:13:12',0,270,NULL,'Urea',450,1);
INSERT INTO `Product` (`product_id`,`created_date`,`current_price`,`price`,`product_desc`,`product_name`,`qty`,`category_id`) VALUES (2,'2021-05-12 17:13:46',0,1700,NULL,'Mico',250,2);
INSERT INTO `Product` (`product_id`,`created_date`,`current_price`,`price`,`product_desc`,`product_name`,`qty`,`category_id`) VALUES (3,'2021-05-12 17:14:08',0,260,NULL,'24D',300,3);
INSERT INTO `Product` (`product_id`,`created_date`,`current_price`,`price`,`product_desc`,`product_name`,`qty`,`category_id`) VALUES (4,'2021-05-12 17:14:54',0,170,NULL,'Ultrazen',240,3);
INSERT INTO `Product` (`product_id`,`created_date`,`current_price`,`price`,`product_desc`,`product_name`,`qty`,`category_id`) VALUES (5,'2021-05-12 17:15:26',0,1200,NULL,'DAP',300,1);

INSERT INTO `Company` (`id`,`created_by`,`created_date`,`last_modified_by`,`last_modified_date`,`company_name`,`phone_number`) VALUES (1,'anonymousUser','2021-05-12 17:18:05','anonymousUser','2021-05-12 17:18:05','Jai Kisan','9038902342');
INSERT INTO `Company` (`id`,`created_by`,`created_date`,`last_modified_by`,`last_modified_date`,`company_name`,`phone_number`) VALUES (2,'anonymousUser','2021-05-12 17:18:31','anonymousUser','2021-05-12 17:18:31','Mangala','9823343432');
INSERT INTO `Company` (`id`,`created_by`,`created_date`,`last_modified_by`,`last_modified_date`,`company_name`,`phone_number`) VALUES (3,'anonymousUser','2021-05-12 17:18:44','anonymousUser','2021-05-12 17:18:44','Daneshwari','7234234232');
INSERT INTO `Company` (`id`,`created_by`,`created_date`,`last_modified_by`,`last_modified_date`,`company_name`,`phone_number`) VALUES (4,'anonymousUser','2021-05-12 17:18:54','anonymousUser','2021-05-12 17:18:54','Shakti','7543534457');
INSERT INTO `Company` (`id`,`created_by`,`created_date`,`last_modified_by`,`last_modified_date`,`company_name`,`phone_number`) VALUES (5,'anonymousUser','2021-05-12 17:19:11','anonymousUser','2021-05-12 17:19:11','Mangam','4952346456');

INSERT INTO `Location` (`id`,`city_name`,`created_date`) VALUES (1,'Shinal','2021-05-12 17:19:25');
INSERT INTO `Location` (`id`,`city_name`,`created_date`) VALUES (2,'Tangadi','2021-05-12 17:19:37');
INSERT INTO `Location` (`id`,`city_name`,`created_date`) VALUES (3,'Athani','2021-05-12 17:19:41');
INSERT INTO `Location` (`id`,`city_name`,`created_date`) VALUES (4,'Murgundi','2021-05-12 17:19:49');
INSERT INTO `Location` (`id`,`city_name`,`created_date`) VALUES (5,'Hulgabali','2021-05-12 17:19:55');
INSERT INTO `Location` (`id`,`city_name`,`created_date`) VALUES (6,'Katral','2021-05-12 17:20:01');
INSERT INTO `Location` (`id`,`city_name`,`created_date`) VALUES (7,'Mole','2021-05-12 17:20:07');
INSERT INTO `Location` (`id`,`city_name`,`created_date`) VALUES (8,'Wadratti','2021-05-12 17:20:30');

INSERT INTO `Customer` (`id`,`created_date`,`customer_name`,`phone_number`,`location_id`) VALUES (1,'2021-05-12 17:24:16','Dadasaheb','9786786734',1);
INSERT INTO `Customer` (`id`,`created_date`,`customer_name`,`phone_number`,`location_id`) VALUES (2,'2021-05-12 17:24:30','Sadashiv','8676232342',2);
INSERT INTO `Customer` (`id`,`created_date`,`customer_name`,`phone_number`,`location_id`) VALUES (3,'2021-05-12 17:25:20','Ganapati','7234235423',2);
INSERT INTO `Customer` (`id`,`created_date`,`customer_name`,`phone_number`,`location_id`) VALUES (4,'2021-05-12 17:25:36','Ankush Khare','8782342345',5);

INSERT INTO `Supplier` (`id`,`created_date`,`phone_number`,`supplier_name`,`company_id`,`location_id`) VALUES (1,'2021-05-12 17:21:54','9786823445','Badakambe',3,3);
INSERT INTO `Supplier` (`id`,`created_date`,`phone_number`,`supplier_name`,`company_id`,`location_id`) VALUES (2,'2021-05-12 17:22:20','8729342345','JN Kumbar',1,3);
INSERT INTO `Supplier` (`id`,`created_date`,`phone_number`,`supplier_name`,`company_id`,`location_id`) VALUES (3,'2021-05-12 17:22:37','9876776565','Ghatge',4,3);

INSERT INTO `Users` (`id`,`email`,`password`,`username`) VALUES (1,'admin@gmail.com','$2a$10$XSleb92FS0jidzZsosNZNeJvgPZXYDKQAMFW2Fdcyn/.zrQb.D.2S','admin');
INSERT INTO `Users` (`id`,`email`,`password`,`username`) VALUES (2,'user@gmail.com','$2a$10$TaXk2cLcaKM3hGDhS1tPZu3qJGDXFKSy4Zb/WY7eEE8Dfh1TB0yM2','user');

INSERT INTO `Roles` (`id`,`name`) VALUES (1,'ROLE_ADMIN');
INSERT INTO `Roles` (`id`,`name`) VALUES (2,'ROLE_USER');

INSERT INTO `user_roles` (`user_id`,`role_id`) VALUES (1,1);
INSERT INTO `user_roles` (`user_id`,`role_id`) VALUES (2,2);

