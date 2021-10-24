-- WebSecurity defines admin user
-- users can be defined in both places
-- encoded password for 123 is $2a$10$kizM8612TbIiLwTqzcHIleBnXTlAyiQxOyflnLSmK4tUEkA2MKBQe
INSERT IGNORE INTO `users` VALUES (NULL, 'root', '$2a$10$kizM8612TbIiLwTqzcHIleBnXTlAyiQxOyflnLSmK4tUEkA2MKBQe', '1');
INSERT IGNORE INTO `users` VALUES (NULL, 'guest', '$2a$10$kizM8612TbIiLwTqzcHIleBnXTlAyiQxOyflnLSmK4tUEkA2MKBQe', '1');
INSERT IGNORE INTO `authorities` VALUES (NULL, 'root', 'write');
INSERT IGNORE INTO `authorities` VALUES (NULL, 'root', 'read');
INSERT IGNORE INTO `authorities` VALUES (NULL, 'guest', 'read');