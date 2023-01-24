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
	`id_user` INT NOT NULL,
	`score` INT NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `messages` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`id_user` INT NOT NULL,
	`message` TEXT NOT NULL,
	PRIMARY KEY (`id`)
);

ALTER TABLE `games_scores` ADD CONSTRAINT `games_scores_fk` FOREIGN KEY (`id_user`) REFERENCES `users`(`id`);

ALTER TABLE `messages` ADD CONSTRAINT `messages_fk` FOREIGN KEY (`id_user`) REFERENCES `users`(`id`);





