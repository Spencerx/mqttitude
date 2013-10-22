CREATE TABLE `Location` (
  `id`       INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `topic`    TEXT             NOT NULL,
  `username` TEXT             NOT NULL,
  `device`   TEXT             NOT NULL,
  `lat`      VARCHAR(15)      NOT NULL,
  `long`     VARCHAR(15)      NOT NULL,
  `tst`      DATETIME         NOT NULL,
  `acc`      VARCHAR(15)      NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8;