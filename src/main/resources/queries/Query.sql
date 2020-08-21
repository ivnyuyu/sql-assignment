CREATE TABLE `solution`
(
    `user_id`       int(11) NOT NULL,
    `assignment_id` int(11) NOT NULL,
    answer          text,
    answer_date     datetime,
    PRIMARY KEY (user_id, assignment_id),
    KEY `fk_user` (`user_id`),
    KEY `fk_assignment` (`assignment_id`),
    CONSTRAINT `fk_assignment` FOREIGN KEY (`assignment_id`) REFERENCES `assignment` (`id`),
    CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);