CREATE TABLE `users` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`username` varchar(255) NOT NULL,
	`password` varchar(255) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `countries` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`flag` blob NOT NULL,
	`country` varchar(255) NOT NULL,
	`difficulty` INT(255) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `games_scores` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`id_user_one` INT NOT NULL,
	`id_user_two` INT NOT NULL,
	`score_user_one` INT NOT NULL,
	`score_user_two` INT NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `messages` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`id_sender` INT NOT NULL,
	`id_receiver` INT NOT NULL,
	`message` TEXT NOT NULL,
	PRIMARY KEY (`id`)
);

ALTER TABLE `games_scores` ADD CONSTRAINT `games_scores_fk0` FOREIGN KEY (`id_user_one`) REFERENCES `users`(`id`);

ALTER TABLE `games_scores` ADD CONSTRAINT `games_scores_fk1` FOREIGN KEY (`id_user_two`) REFERENCES `users`(`id`);

ALTER TABLE `messages` ADD CONSTRAINT `messages_fk0` FOREIGN KEY (`id_sender`) REFERENCES `users`(`id`);

ALTER TABLE `messages` ADD CONSTRAINT `messages_fk1` FOREIGN KEY (`id_receiver`) REFERENCES `users`(`id`);





