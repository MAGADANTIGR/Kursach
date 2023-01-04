SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';



-- -----------------------------------------------------
-- Schema airp
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `airpo` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `airpo` ;

-- -----------------------------------------------------
-- Table `airp`.`users`   checked
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airpo`.`users` (
  `idUsers` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `isBlocked` VARCHAR(45) NOT NULL DEFAULT 'no',
  PRIMARY KEY (`idUsers`))
ENGINE = InnoDB
AUTO_INCREMENT = 20
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


insert into  `airpo`.`users`(`idUsers`, `username`, `password`)
values(1, 'admin', 'admin');
-- ----------------------------------------
-- -----------------------------------------------------
-- Table `airp`.`admin`    checked
-- -----------------------------------------------------


-- -----------------------------------------------------
-- Table `airport`.`client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airpo`.`client` (
  `idclient` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `sex` VARCHAR(45) NOT NULL,
  `country` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idclient`),
  CONSTRAINT `idUserClFK`
    FOREIGN KEY (`idclient`)
    REFERENCES `airpo`.`users` (`idUsers`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `airpo`.`admin` (
  `idadmin` INT(11) NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idadmin`),
  CONSTRAINT `idUsersFK`
    FOREIGN KEY (`idadmin`)
    REFERENCES `airpo`.`users` (`idUsers`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


insert into  `airpo`.`admin` (`idadmin`, `status`)
values(1, 'a');
-- -----------------------------------------------------
-- Table `airp`.`maindata`  checked
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airpo`.`maindata` (
  `idmd` INT(11) NOT NULL AUTO_INCREMENT,
  `airport` VARCHAR(45) NOT NULL,
  `date` VARCHAR(45) NOT NULL,
  `outtime` VARCHAR(45) NOT NULL,
  `intime` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idmd`))
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `airport`.`flight`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airpo`.`flight` (
  `idF` INT(11) NOT NULL AUTO_INCREMENT,
  `price` INT(11) NOT NULL,
  `idMD` INT(11) NOT NULL,
  `seatsAmount` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`idF`),
  INDEX `FlMainDataFK_idx` (`idMD` ASC) VISIBLE,
  CONSTRAINT `FlMainDataFK`
    FOREIGN KEY (`idMD`)
    REFERENCES `airpo`.`maindata` (`idmd`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `airp`.`userdata` checked
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airpo`.`userdata` (
  `idUD` INT(11) NOT NULL AUTO_INCREMENT,
  `idu` INT(11) NOT NULL,
  `laggage` VARCHAR(45) NULL DEFAULT NULL,
  `age` VARCHAR(45) NULL DEFAULT NULL,
  `idf` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`idUD`),
  INDEX `iduidUFK_idx` (`idu` ASC) VISIBLE,
  INDEX `idfidFFK_idx` (`idf` ASC) VISIBLE,
  CONSTRAINT `idfidFFK`
    FOREIGN KEY (`idf`)
    REFERENCES `airpo`.`flight` (`idF`),
  CONSTRAINT `iduidUFK`
    FOREIGN KEY (`idu`)
    REFERENCES `airpo`.`users` (`idUsers`))
ENGINE = InnoDB
AUTO_INCREMENT = 97
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `airpo`.`laggage` (
  `iduserdata` INT(11) NOT NULL,
  `lprice` INT(11) NOT NULL,
  INDEX `lafk_idx` (`iduserdata` ASC) VISIBLE,
  CONSTRAINT `lafk`
    FOREIGN KEY (`iduserdata`)
    REFERENCES `airpo`.`userdata` (`idUD`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `airp`.`sales`   checked
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airpo`.`sales` (
  `idud` INT(11) NOT NULL,
  `agesale` INT(11) NOT NULL DEFAULT '0',
  `lprice` INT(11) NOT NULL,
  INDEX `salefk_idx` (`idud` ASC) VISIBLE,
  CONSTRAINT `salefk`
    FOREIGN KEY (`idud`)
    REFERENCES `airpo`.`userdata` (`idUD`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `airport`.`ticket`  checked
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airpo`.`ticket` (
  `idticket` INT(11) NOT NULL AUTO_INCREMENT,
  `idus` INT(11) NOT NULL,
  `generalPrice` INT(11) NOT NULL,
  PRIMARY KEY (`idticket`),
  INDEX `index4` (`generalPrice` ASC) VISIBLE,
  INDEX `idudidUDFK_idx` (`idus` ASC) VISIBLE,
  CONSTRAINT `idudidUDFK`
    FOREIGN KEY (`idus`)
    REFERENCES `airpo`.`users` (`idUsers`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `airpo`.`agesales` (
  `idud` INT(11) NOT NULL,
  `sale` INT(11) NOT NULL DEFAULT '0',
  INDEX `salefkk_idx` (`idud` ASC) VISIBLE,
  CONSTRAINT `salefkk`
    FOREIGN KEY (`idud`)
    REFERENCES `airpo`.`userdata` (`idUD`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
