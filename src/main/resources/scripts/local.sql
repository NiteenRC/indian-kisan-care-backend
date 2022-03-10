INSERT INTO roles
VALUES (1, 'ROLE_SUPER_ADMIN'),
       (2, 'ROLE_ADMIN'),
       (3, 'ROLE_USER');

INSERT INTO bank_account
VALUES (1, 'ACCNOXXXXXXXXXX', 'HDFC', 'MG ROADXXXXXXX', 'MY SHOP XXXXXXXXX',
        'IFSCXXXXXXXXXX', 'GSTXXXXXXXXX', 'PANNOXXXXXXX', '90365XXXXXXXXX', 'superadmin@gmail.com', null);

INSERT INTO category
VALUES (1, NULL, 'mobile', '2022-03-05 05:44:09.482000'),
       (2, NULL, 'laptop', '2022-03-05 05:45:25.835000');

INSERT INTO product
VALUES (1, '2022-03-05 05:44:09.492000', 50000, 0, '100231', 0, NULL, 'IPHONE X', 0, 0, 1),
       (2, '2022-03-05 05:44:51.376000', 25000, 0, '100234', 0, NULL, 'SAMSUNG GALAXY', 0, 0, 1),
       (3, '2022-03-05 05:45:25.840000', 60000, 0, '1002342', 0, NULL, 'LENOVO 410', 0, 0, 2);

INSERT INTO location
VALUES (1, 'SHINAL', '2022-03-05 06:16:30.020000'),
       (2, 'ATHANI', '2022-03-05 06:16:30.020000'),
       (3, 'TANGADI', '2022-03-05 06:16:30.020000');

INSERT INTO customer
VALUES (1, NULL, 'ANAND GOYAL', '', '9345345', 1),
       (2, NULL, 'PRAKASH JAIN', '', '9345345', 2),
       (3, NULL, 'GURU PATIL', '', '9345345', 3);

INSERT INTO supplier
VALUES (1, '2022-03-05 05:48:50.935000', 'AHPXXXXXX', '982342342', 'RELIANCE', NULL, 2);


