CREATE TABLE IF NOT EXISTS accounts_type (
    `id` int AUTO_INCREMENT PRIMARY KEY,
    `account_type_name` varchar(100) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS accounts (
    `account_id` int AUTO_INCREMENT  PRIMARY KEY,
    `email_address` varchar(200) NOT NULL,
    `username` varchar(200) NOT NULL,
    `account_type_id` int NOT NULL,
    `created_at` date NOT NULL,
    `created_by` varchar(20) NOT NULL,
    `updated_at` date DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL,
    FOREIGN KEY (`account_type_id`) REFERENCES `accounts_type` (`id`)
    );

CREATE TABLE IF NOT EXISTS gift_formats (
    `id` int AUTO_INCREMENT PRIMARY KEY,
    `format_name` varchar(100) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS gift_registry (
    `id` int AUTO_INCREMENT PRIMARY KEY,
--     gift_format_id is a foreign key to gift_formats table
    `gift_format_id` int NOT NULL,
--     name of celebrate or cruise
    `name` varchar(100) NOT NULL,
--     date of departure (if cruise), else its null
    `departure_date` date DEFAULT NULL,
--    date of cruise debarkation (if cruise), else its null
    `debarkation_date` date DEFAULT NULL,
--    date of celebration (if celebration), else its null
    `birth_date` date DEFAULT NULL,

    -- admin of registry (a single account
    `admin_account_id` int NOT NULL,


-- registry expiry date (max 1 year from creation date)
    `expiry_date` date NOT NULL,

    FOREIGN KEY (`gift_format_id`) REFERENCES `gift_formats` (`id`),
    FOREIGN KEY (`admin_account_id`) REFERENCES `accounts` (`account_id`)
);

CREATE TABLE IF NOT EXISTS registry_members (
    `gift_registry_id` int NOT NULL,
    `member_id` int NOT NULL,
    PRIMARY KEY (`gift_registry_id`, `member_id`),
    FOREIGN KEY (`gift_registry_id`) REFERENCES `gift_registry` (`id`),
    FOREIGN KEY (`member_id`) REFERENCES `accounts` (`account_id`)
);

INSERT INTO `accounts_type` (`account_type_name`) VALUES
                                                      ('Organiser'),
                                                      ('Member'),
                                                      ('Admin'),
                                                      ('N/A');

INSERT INTO `gift_formats` (`format_name`) VALUES
                                               ('Celebration'),
                                               ('Cruise');
