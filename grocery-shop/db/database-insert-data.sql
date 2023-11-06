-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия на сървъра:            10.5.18-MariaDB - mariadb.org binary distribution
-- ОС на сървъра:                Win64
-- HeidiSQL Версия:              11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Дъмп данни за таблица grocery-shop.deals: ~2 rows (приблизително)
/*!40000 ALTER TABLE `deals` DISABLE KEYS */;
INSERT INTO `deals` (`deal_id`, `deal_enum`) VALUES
	(1, 'BUY_ONE_GET_ONE_HALF_PRICE'),
	(2, 'TWO_FOR_THREE');
/*!40000 ALTER TABLE `deals` ENABLE KEYS */;

-- Дъмп данни за таблица grocery-shop.deals_products: ~4 rows (приблизително)
/*!40000 ALTER TABLE `deals_products` DISABLE KEYS */;
INSERT INTO `deals_products` (`deal_id`, `product_id`) VALUES
	(2, 1),
	(2, 2),
	(2, 3),
	(1, 4);
/*!40000 ALTER TABLE `deals_products` ENABLE KEYS */;

-- Дъмп данни за таблица grocery-shop.products: ~4 rows (приблизително)
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` (`product_id`, `name`, `price`) VALUES
	(1, 'apple', 50),
	(2, 'banana', 40),
	(3, 'tomato', 30),
	(4, 'potato', 26);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;

-- Дъмп данни за таблица grocery-shop.users: ~2 rows (приблизително)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`user_id`, `first_name`, `last_name`, `password`, `username`) VALUES
	(1, 'Emily', 'Johnson', 'user1password', 'ejohnson92'),
	(2, 'Michael', 'Torres', 'user2password', 'mtorres88');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

-- Дъмп данни за таблица grocery-shop.users_products: ~7 rows (приблизително)
/*!40000 ALTER TABLE `users_products` DISABLE KEYS */;
INSERT INTO `users_products` (`user_id`, `product_id`) VALUES
	(1, 1),
	(1, 2),
	(1, 2),
	(1, 4),
	(1, 3),
	(1, 2),
	(1, 4);
/*!40000 ALTER TABLE `users_products` ENABLE KEYS */;

-- Дъмп данни за таблица grocery-shop.users_seq: ~1 rows (приблизително)
/*!40000 ALTER TABLE `users_seq` DISABLE KEYS */;
INSERT INTO `users_seq` (`next_val`) VALUES
	(1);
/*!40000 ALTER TABLE `users_seq` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
