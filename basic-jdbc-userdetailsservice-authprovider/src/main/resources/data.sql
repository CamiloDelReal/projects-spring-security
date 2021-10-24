-- encoded password for 123 is $2a$10$kizM8612TbIiLwTqzcHIleBnXTlAyiQxOyflnLSmK4tUEkA2MKBQe

INSERT INTO `users` (`username`, `password`, `role`)
VALUES ('root', '$2a$10$kizM8612TbIiLwTqzcHIleBnXTlAyiQxOyflnLSmK4tUEkA2MKBQe', 'admin');

INSERT INTO `users` (`username`, `password`, `role`)
VALUES ('guest', '$2a$10$kizM8612TbIiLwTqzcHIleBnXTlAyiQxOyflnLSmK4tUEkA2MKBQe', 'guest');
