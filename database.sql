USE switch;

-- ==============================
-- GRANTS
-- ==============================
DROP TABLE IF EXISTS `grants`;
CREATE TABLE `grants` (
                          `service_id` BIGINT NOT NULL AUTO_INCREMENT,
                          `service_name` VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                          `industry` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                          `location` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                          `service_status` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                          `selection_criteria` TEXT COLLATE utf8mb4_unicode_ci,
                          `required_documents` TEXT COLLATE utf8mb4_unicode_ci,
                          `service_contents` TEXT COLLATE utf8mb4_unicode_ci,
                          `service_type` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                          `service_link` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                          PRIMARY KEY (`service_id`)
) ENGINE=InnoDB AUTO_INCREMENT=268 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==============================
-- PROPERTY
-- ==============================
DROP TABLE IF EXISTS `property_lease_costs`;
DROP TABLE IF EXISTS `property_location`;
DROP TABLE IF EXISTS `property`;

CREATE TABLE `property` (
                            `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                            `store_name` VARCHAR(255) NOT NULL,
                            `industry` VARCHAR(100) NOT NULL,
                            `shop_type` VARCHAR(100) NOT NULL,
                            `transfer_type` VARCHAR(100) NOT NULL,
                            `transfer_date` DATE NULL,
                            `current_floor` INT NOT NULL,
                            `total_floor` INT NOT NULL,
                            `parking_type` VARCHAR(100) NOT NULL,
                            `parking_count` INT NOT NULL,
                            `parking_paid` BOOLEAN NOT NULL,
                            `restroom` VARCHAR(100) NOT NULL,
                            `delivery_level` VARCHAR(100) NOT NULL,
                            `takeout_level` VARCHAR(100) NOT NULL,
                            `supply_area` DECIMAL(10,2) NOT NULL,
                            `exclusive_area` DECIMAL(10,2) NOT NULL,
                            `description` TEXT NOT NULL,
                            `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            `updated_at` DATETIME NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `property_lease_costs` (
                                        `property_id` BIGINT UNSIGNED NOT NULL,
                                        `transaction_type` VARCHAR(50) NOT NULL,
                                        `mgmt_fee` INT NOT NULL,
                                        `premium` INT NOT NULL,
                                        `rent` INT NULL,
                                        `deposit` INT NULL,
                                        `sale_price` INT NULL,
                                        PRIMARY KEY (`property_id`),
                                        CONSTRAINT `fk_property_lease_costs_property`
                                            FOREIGN KEY (`property_id`) REFERENCES `property`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `property_location` (
                                     `property_id` BIGINT UNSIGNED NOT NULL,
                                     `address` TEXT NOT NULL,
                                     `lat` DECIMAL(10,7) NOT NULL,
                                     `lng` DECIMAL(10,7) NOT NULL,
                                     PRIMARY KEY (`property_id`),
                                     CONSTRAINT `fk_property_location_property`
                                         FOREIGN KEY (`property_id`) REFERENCES `property`(`id`) ON DELETE CASCADE,
                                     CONSTRAINT `chk_lat_range` CHECK (`lat` BETWEEN -90 AND 90),
                                     CONSTRAINT `chk_lng_range` CHECK (`lng` BETWEEN -180 AND 180)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==============================
-- USER
-- ==============================
DROP TABLE IF EXISTS chat_message;
DROP TABLE IF EXISTS chat_room;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       contact VARCHAR(20) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==============================
-- CHAT ROOM
-- ==============================
CREATE TABLE chat_room (
                           room_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           buyer_id BIGINT NOT NULL,
                           seller_id BIGINT NOT NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           CONSTRAINT fk_chatroom_buyer FOREIGN KEY (buyer_id) REFERENCES users(user_id) ON DELETE CASCADE,
                           CONSTRAINT fk_chatroom_seller FOREIGN KEY (seller_id) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==============================
-- CHAT MESSAGE
-- ==============================
CREATE TABLE chat_message (
                              chat_message_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              room_id BIGINT NOT NULL,
                              sender_id BIGINT NOT NULL,
                              content TEXT NOT NULL,
                              sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              CONSTRAINT fk_message_room FOREIGN KEY (room_id) REFERENCES chat_room(room_id) ON DELETE CASCADE,
                              CONSTRAINT fk_message_sender FOREIGN KEY (sender_id) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
