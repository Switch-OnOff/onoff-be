use switch;

-- GRANTS
DROP TABLE IF EXISTS `grants`;
CREATE TABLE `grants` (
                          `service_id` bigint NOT NULL AUTO_INCREMENT,
                          `service_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                          `industry` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                          `location` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                          `service_status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                          `selection_criteria` text COLLATE utf8mb4_unicode_ci,
                          `required_documents` text COLLATE utf8mb4_unicode_ci,
                          `service_contents` text COLLATE utf8mb4_unicode_ci,
                          `service_type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                          `service_link` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                          PRIMARY KEY (`service_id`)
) ENGINE=InnoDB AUTO_INCREMENT=268 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- PROPERTY
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
                            `restroom`  VARCHAR(100)  NOT NULL,
                            `delivery_level` VARCHAR(100) NOT NULL,
                            `takeout_level`  VARCHAR(100) NOT NULL,
                            `supply_area`   DECIMAL(10,2) NOT NULL,
                            `exclusive_area` DECIMAL(10,2) NOT NULL,
                            `description` TEXT NOT NULL,
                            `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            `updated_at` DATETIME NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- LEASE COSTS (PK=FK, 식별 1:1)
DROP TABLE IF EXISTS `property_lease_costs`;
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
                                            FOREIGN KEY (`property_id`) REFERENCES `property`(`id`)
                                                ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- LOCATION (PK=FK, 식별 1:1)
DROP TABLE IF EXISTS `property_location`;
CREATE TABLE `property_location` (
                                     `property_id` BIGINT UNSIGNED NOT NULL,
                                     `address` TEXT NOT NULL,
                                     `lat` DECIMAL(10,7) NOT NULL,
                                     `lng` DECIMAL(10,7) NOT NULL,
                                     PRIMARY KEY (`property_id`),
                                     CONSTRAINT `fk_property_location_property`
                                         FOREIGN KEY (`property_id`) REFERENCES `property`(`id`)
                                             ON DELETE CASCADE,
                                     CONSTRAINT `chk_lat_range` CHECK (`lat` BETWEEN -90 AND 90),
                                     CONSTRAINT `chk_lng_range` CHECK (`lng` BETWEEN -180 AND 180)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
