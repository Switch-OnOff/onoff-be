-- 0. 외래키 제약 무시
SET FOREIGN_KEY_CHECKS = 0;

-- 1. DB 생성
CREATE DATABASE IF NOT EXISTS switch CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 2. root 계정 패스워드 확인/설정
ALTER USER 'root'@'localhost' IDENTIFIED BY '1234';

-- 3. 권한 부여
GRANT ALL PRIVILEGES ON switch.* TO 'root'@'localhost';
FLUSH PRIVILEGES;

-- 4. 사용할 DB 선택
USE switch;

-- 5. 기존 테이블 삭제 (순서 상관 없이 외래키 무시)
DROP TABLE IF EXISTS chat_message;
DROP TABLE IF EXISTS chat_room;
DROP TABLE IF EXISTS property_location;
DROP TABLE IF EXISTS property_lease_costs;
DROP TABLE IF EXISTS property;
DROP TABLE IF EXISTS grants;
DROP TABLE IF EXISTS users;

-- ==============================
-- USERS 테이블
-- ==============================
CREATE TABLE users (
                       user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       name VARCHAR(10) NOT NULL,
                       contact VARCHAR(20) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==============================
-- GRANTS 테이블
-- ==============================
CREATE TABLE grants (
                        service_id BIGINT NOT NULL AUTO_INCREMENT,
                        service_name VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                        industry VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                        location VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                        service_status VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                        selection_criteria TEXT COLLATE utf8mb4_unicode_ci,
                        required_documents TEXT COLLATE utf8mb4_unicode_ci,
                        service_contents TEXT COLLATE utf8mb4_unicode_ci,
                        service_type VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                        service_link VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                        PRIMARY KEY (service_id)
) ENGINE=InnoDB AUTO_INCREMENT=268 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==============================
-- PROPERTY 테이블
-- ==============================
CREATE TABLE property (
                          id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                          user_id Long NOT NULL,
                          store_name VARCHAR(255) NOT NULL,
                          industry VARCHAR(100) NOT NULL,
                          shop_type VARCHAR(100) NOT NULL,
                          transfer_type VARCHAR(100) NOT NULL,
                          transfer_date DATE NULL,
                          current_floor INT NOT NULL,
                          total_floor INT NOT NULL,
                          parking_type VARCHAR(100) NOT NULL,
                          parking_count INT NOT NULL,
                          parking_paid BOOLEAN NOT NULL,
                          restroom VARCHAR(100) NOT NULL,
                          delivery_level VARCHAR(100) NOT NULL,
                          takeout_level VARCHAR(100) NOT NULL,
                          supply_area DECIMAL(10,2) NOT NULL,
                          exclusive_area DECIMAL(10,2) NOT NULL,
                          description TEXT NOT NULL,
                          created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at DATETIME NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                          PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==============================
-- PROPERTY_LEASE_COSTS 테이블
-- ==============================
CREATE TABLE property_lease_costs (
                                      property_id BIGINT UNSIGNED NOT NULL,
                                      transaction_type VARCHAR(50) NOT NULL,
                                      mgmt_fee INT NOT NULL,
                                      premium INT NOT NULL,
                                      rent INT NULL,
                                      deposit INT NULL,
                                      sale_price INT NULL,
                                      PRIMARY KEY (property_id),
                                      CONSTRAINT fk_property_lease_costs_property
                                          FOREIGN KEY (property_id) REFERENCES property(id)
                                              ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==============================
-- PROPERTY_LOCATION 테이블
-- ==============================
CREATE TABLE property_location (
                                   property_id BIGINT UNSIGNED NOT NULL,
                                   address TEXT NOT NULL,
                                   lat DECIMAL(10,7) NOT NULL,
                                   lng DECIMAL(10,7) NOT NULL,
                                   PRIMARY KEY (property_id),
                                   CONSTRAINT fk_property_location_property
                                       FOREIGN KEY (property_id) REFERENCES property(id)
                                           ON DELETE CASCADE,
                                   CONSTRAINT chk_lat_range CHECK (lat BETWEEN -90 AND 90),
                                   CONSTRAINT chk_lng_range CHECK (lng BETWEEN -180 AND 180)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==============================
-- CHAT_ROOM 테이블
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
-- CHAT_MESSAGE 테이블
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

-- 9. 외래키 제약 다시 활성화
SET FOREIGN_KEY_CHECKS = 1;
