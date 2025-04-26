-- 创建购物车表
CREATE TABLE IF NOT EXISTS `shopping_cart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `product_type` varchar(50) DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `contact_person` varchar(100) DEFAULT NULL,
  `contact_number` varchar(20) DEFAULT NULL,
  `id_card_number` varchar(20) DEFAULT NULL,
  `payment_amount` decimal(10,2) DEFAULT NULL,
  `payment_status` varchar(20) DEFAULT NULL,
  `payment_time` datetime DEFAULT NULL,
  `creation_time` datetime DEFAULT NULL,
  `coupon_bool` tinyint(1) DEFAULT 0,
  `coupon_used` int DEFAULT NULL,
  `discount_applied` varchar(50) DEFAULT NULL,
  `quantity` int DEFAULT 1,
  `cover_image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4; 