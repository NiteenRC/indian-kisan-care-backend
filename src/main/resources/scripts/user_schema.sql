INSERT INTO `Users` (`id`,`email`,`password`,`username`) VALUES (1,'admin@gmail.com','$2a$10$XSleb92FS0jidzZsosNZNeJvgPZXYDKQAMFW2Fdcyn/.zrQb.D.2S','admin');
INSERT INTO `Users` (`id`,`email`,`password`,`username`) VALUES (2,'user@gmail.com','$2a$10$TaXk2cLcaKM3hGDhS1tPZu3qJGDXFKSy4Zb/WY7eEE8Dfh1TB0yM2','user');

INSERT INTO `Roles` (`id`,`name`) VALUES (1,'ROLE_ADMIN');
INSERT INTO `Roles` (`id`,`name`) VALUES (2,'ROLE_USER');

INSERT INTO `user_roles` (`user_id`,`role_id`) VALUES (1,1);
INSERT INTO `user_roles` (`user_id`,`role_id`) VALUES (2,2);

