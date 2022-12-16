CREATE TABLE `users` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`username` varchar(255) NOT NULL,
	`password` varchar(255) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `country` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`flags` longblob NOT NULL,
	`country` varchar(255) NOT NULL,
	`difficulty` INT(255) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `games_score` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`id_users_one` INT NOT NULL,
	`id_users_two` INT NOT NULL,
	`score_users_one` INT NOT NULL,
	`score_users_two` INT NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `messages` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`id_senders` INT NOT NULL,
	`id_receiver` INT NOT NULL,
	`message` TEXT NOT NULL,
	PRIMARY KEY (`id`)
);

ALTER TABLE `games_score` ADD CONSTRAINT `games_score_fk0` FOREIGN KEY (`id_users_one`) REFERENCES `users`(`id`);

ALTER TABLE `games_score` ADD CONSTRAINT `games_score_fk1` FOREIGN KEY (`id_users_two`) REFERENCES `users`(`id`);

ALTER TABLE `messages` ADD CONSTRAINT `messages_fk0` FOREIGN KEY (`id_senders`) REFERENCES `users`(`id`);

ALTER TABLE `messages` ADD CONSTRAINT `messages_fk1` FOREIGN KEY (`id_receiver`) REFERENCES `users`(`id`);





