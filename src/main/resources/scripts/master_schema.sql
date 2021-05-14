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

