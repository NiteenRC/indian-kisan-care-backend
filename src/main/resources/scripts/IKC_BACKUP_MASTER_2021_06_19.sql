INSERT INTO `bank_account` VALUES (1,'0192000100051701','KARNATAKA BANK','Athani 591304','KAB0000019','Praveen Bajantri');
INSERT INTO `users` VALUES (1,'PRAVEEN TRADERS','bajantrichetan33@gmail.com','29BAJPB4499Q1ZS',NULL,'BAJPB4499Q','$2a$10$y7D99J7.BaD4dkPsDuZgQu6zHmNoYrPVPSBerddTZ0ytrFxLuKORy','7338393431/9480157575','chetan',1),(2,'INDIAN KISAN CARE','rakesh@gmail.com','NA',NULL,'NA','$2a$10$njJ6RxxUdrVQSTUYkUmuE.Q/4mZpbNL7hLxG4gdLdBXo9PaYjO96W',NULL,'rakesh',NULL);
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');
INSERT INTO `user_roles` VALUES (1,1),(2,2);

INSERT INTO `category` VALUES (1,NULL,'FERTILIZER','2021-06-19 03:45:24.805000'),(2,NULL,'PESTICIDE','2021-06-19 03:45:37.429000'),(3,NULL,'SEED','2021-06-19 03:45:45.065000'),(4,NULL,'HERBICIDE','2021-06-19 04:56:41.450000');
INSERT INTO `product` VALUES (2,NULL,0,5,'31021000',0,NULL,'10:26:26-MAHADHAN',0,0,1),(3,NULL,0,5,'31021000',0,NULL,'24:24:0-MAHADHAN MAHA POWER 50KG',0,0,1),(4,'2021-06-19 04:54:00.586000',0,5,'NA',0,NULL,'AMMONIUM SULPHATE-FACT',0,0,1),(5,'2021-06-19 04:54:15.226000',0,5,'NA',0,NULL,'DAP-GODAVARI',0,0,1),(6,NULL,0,12,'28332100',0,NULL,'MAGNASIUM SULPHATE-JAI KISAN (25KG)',0,0,1),(7,NULL,0,5,'31042000',0,NULL,'MOP(RED) IPL',0,0,1),(8,'2021-06-19 04:55:09.217000',0,5,'NA',0,NULL,'SOIL MASTER',0,0,1),(9,'2021-06-19 04:56:41.480000',0,5,'NA',0,NULL,'2-4-D',0,0,4),(10,'2021-06-19 04:57:00.739000',0,5,'NA',0,NULL,'ATRAZINE',0,0,4),(11,'2021-06-19 04:57:12.805000',0,5,'NA',0,NULL,'CORAZINE',0,0,4),(12,NULL,0,18,'NA',0,NULL,'MERA 71 100G',0,0,4),(13,NULL,0,18,'NA',0,NULL,'MERA 71 1KG',0,0,4),(14,'2021-06-19 04:57:48.392000',0,5,'NA',0,NULL,'METRIBUZIN',0,0,4),(15,NULL,0,18,'38089199',0,NULL,'ROUND UP(1LTR)',0,0,4),(16,'2021-06-19 04:58:10.511000',0,5,'NA',0,NULL,'TAMAR',0,0,4),(17,'2021-06-19 04:58:23.118000',0,5,'NA',0,NULL,'TATA TAFGOR',0,0,4),(18,'2021-06-19 04:58:37.560000',0,5,'NA',0,NULL,'TYNZER',0,0,4),(19,'2021-06-19 07:34:56.475000',0,5,'3105',0,NULL,'SAMRAT 18:46:00-JAI KISAN',0,0,1),(20,'2021-06-19 07:46:53.838000',0,5,'31021000',0,NULL,'UREA KISAN NEEM IMP(45KG)',0,0,1),(21,'2021-06-19 08:03:34.160000',0,18,'38089390',0,NULL,'ADAMA TAMAR(1KG)',0,0,4),(22,'2021-06-19 08:04:11.897000',0,18,'2833',0,NULL,'WEED STROKE(1 LTR)',0,0,4),(23,'2021-06-19 08:05:09.094000',0,18,'38089390',0,NULL,'TROPICAL DEZIRE 70% WDP(250 GRAM)',0,0,4),(24,'2021-06-19 08:06:36.635000',0,18,'38089390',0,NULL,'BASF TYNZER 30+300ML+FLUX50% WP(500GM)',0,0,4),(25,'2021-06-19 08:07:10.835000',0,18,'38089390',0,NULL,'ADAMA ATRANEX(500 GRAM)',0,0,4),(26,'2021-06-19 08:08:03.188000',0,18,'38089199',0,NULL,'BASF VESTICOR 20% SC',0,0,4),(27,'2021-06-19 08:08:56.990000',0,18,'38089199',0,NULL,'TATA TAFGOR(250ML)',0,0,4),(28,'2021-06-19 08:09:59.823000',0,5,'31052000',0,NULL,'FACT AMMONIUM SULPHATE(50 KG)',0,0,1);

INSERT INTO `location` VALUES (1,'LAKSHMI GUDI','2021-06-19 05:05:04.218000'),(2,'SHINAL','2021-06-19 05:05:15.057000'),(3,'TANGADI','2021-06-19 05:05:22.591000'),(4,'HULAGABALI','2021-06-19 05:13:19.723000'),(5,'KATRAL','2021-06-19 05:13:24.849000'),(7,'WADRATTI','2021-06-19 05:31:24.723000'),(8,'DURGI','2021-06-19 05:52:43.894000'),(9,'KEMPAWAD','2021-06-19 07:32:56.779000'),(10,'ATHANI','2021-06-19 08:01:18.267000'),(11,'DESARAHATTI','2021-06-19 09:59:03.751000');
INSERT INTO `company` VALUES (1,'anonymousUser','2021-06-19 07:33:25.215000','anonymousUser','2021-06-19 07:33:25.215000','KRISHNA AGRO SERVICES',NULL);

INSERT INTO `customer` VALUES (1,'2021-06-19 05:06:25.935000','VARDAMAN TERDAL',NULL,'8105-876-021',1),(2,'2021-06-19 05:07:58.257000','SHIVAJI PATIL',NULL,NULL,2),(3,'2021-06-19 05:08:50.515000','DAMODAR PATIL',NULL,'8277-577-878',2),(4,NULL,'VITTAL CHOUGALA',NULL,NULL,2),(5,'2021-06-19 05:09:47.755000','SHAHAJI PATIL',NULL,NULL,2),(6,'2021-06-19 05:10:04.883000','DONGARE',NULL,NULL,2),(7,'2021-06-19 05:10:23.402000','RAMAN PATIL',NULL,NULL,2),(8,'2021-06-19 05:10:57.983000','TATYA SAHEB DEVAKATE',NULL,NULL,2),(9,'2021-06-19 05:11:09.327000','SADU PATIL',NULL,NULL,3),(10,'2021-06-19 05:11:37.941000','JAYADEEP SAVANT',NULL,NULL,2),(11,'2021-06-19 05:11:53.213000','BANAJWAD',NULL,NULL,NULL),(12,'2021-06-19 05:12:13.496000','MALAPPA CHOUGALA',NULL,NULL,2),(13,'2021-06-19 05:12:33.924000','BHIRU CHOUGALA',NULL,NULL,2),(14,NULL,'SHIVU',NULL,NULL,4),(15,NULL,'ANIKET RASAL',NULL,NULL,5),(16,'2021-06-19 05:22:40.811000','MANIK VANJOLI',NULL,NULL,2),(17,NULL,'ASHOK KAMBLE',NULL,NULL,7),(18,'2021-06-19 05:31:04.694000','ANIL PATIL',NULL,'9591-949-353',2),(19,'2021-06-19 05:35:59.569000','PAVAN CHOUGALA',NULL,NULL,2),(20,'2021-06-19 05:37:32.509000','MUDAKA VAGHAMARE',NULL,NULL,2),(21,'2021-06-19 05:49:31.228000','SADASHIV DOEPHODE',NULL,NULL,2),(22,'2021-06-19 05:52:59.247000','NAMADEV DURGI',NULL,NULL,8),(23,'2021-06-19 06:03:25.234000','RAJENDRA PATIL',NULL,NULL,2),(24,'2021-06-19 06:03:48.436000','SANJU CHOUGALA',NULL,NULL,2),(25,'2021-06-19 06:04:05.573000','BADAGI',NULL,NULL,NULL),(26,'2021-06-19 06:04:32.236000','SHASHIKANT KAMBLE',NULL,NULL,2),(27,'2021-06-19 06:04:50.090000','NAMADEV SAVANT',NULL,NULL,2),(28,'2021-06-19 06:05:24.371000','MURASIDDHA MAGADUM',NULL,NULL,3),(29,'2021-06-19 06:05:43.418000','TANAJI PATIL',NULL,NULL,2),(30,'2021-06-19 06:25:52.224000','ROHAN SALAGARE',NULL,NULL,5),(31,'2021-06-19 10:01:51.079000','L B ARTAL',NULL,'9449-944-999',11);
INSERT INTO `supplier` VALUES (1,NULL,'29AAOFK6582H1ZN',NULL,'KRISHNA AGRO SERVICES',1,9),(2,NULL,'29ACWFS0752K1ZC',NULL,'SHRI SHIVASHANKAR KRUSHI SEVA KENDRA',NULL,10),(3,'2021-06-19 10:29:14.361000','29AAAFG6118P1Z0','7204153663','G N KUMBAR',NULL,10);