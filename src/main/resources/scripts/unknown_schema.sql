INSERT INTO `Company` (`id`,`created_by`,`created_date`,`last_modified_by`,`last_modified_date`,`company_name`,`phone_number`) VALUES (1,'anonymousUser','2021-05-12 17:19:11','anonymousUser','2021-05-12 17:19:11','Unknown','9999999999');

INSERT INTO `Location` (`id`,`city_name`,`created_date`) VALUES (1,'Unknown','2021-05-12 17:20:30');

INSERT INTO `Customer` (`id`,`created_date`,`customer_name`,`phone_number`,`location_id`,`gst_in`) VALUES (1,'2021-05-12 17:25:36','Unknown','9999999999',1,'ABCDABCDABCDABCD');

INSERT INTO `Supplier` (`id`,`created_date`,`phone_number`,`supplier_name`,`company_id`,`location_id`,`gst_in`) VALUES (3,'2021-05-12 17:22:37','9999999999','Unkown',1,1,'ABCDABCDABCDABCD');

INSERT INTO `Category` (`category_id`,`category_desc`,`category_name`,`created_date`) VALUES (1,'Fertilizer desc','Category1','2021-05-12 16:55:52');

INSERT INTO `Product` (`product_id`,`created_date`,`current_price`,`price`,`product_desc`,`product_name`,`qty`,`category_id`,`gst`,`hsn_no`) VALUES (1,'2021-05-12 17:13:12',0,0.0,NULL,'Product1',0,1,18,'asdf');
INSERT INTO `Product` (`product_id`,`created_date`,`current_price`,`price`,`product_desc`,`product_name`,`qty`,`category_id`,`gst`,`hsn_no`) VALUES (2,'2021-05-12 17:13:46',0,0.0,NULL,'Product2',0,1,18,'23sdf');