
CREATE TABLE `sitters` (
  `Sitter_Id` int(11) NOT NULL AUTO_INCREMENT,
  `Sitter_Name` varchar(255) NOT NULL,
  `Sitter_Image` varchar(2083) DEFAULT NULL,
  `Sitter_Phone` varchar(20) DEFAULT NULL,
  `Sitter_Email` varchar(1024) DEFAULT NULL,
  `Sitter_Score` int(11) DEFAULT NULL,
  PRIMARY KEY (`Sitter_Id`),
  UNIQUE KEY `Sitter_Id_UNIQUE` (`Sitter_Id`));

CREATE TABLE `rover`.`pets` (
  `Pet_Id` INT NOT NULL AUTO_INCREMENT,
  `Owener_Id` INT NOT NULL,
  `Pet_Name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`Pet_Id`),
  UNIQUE INDEX `Pet_Id_UNIQUE` (`Pet_Id` ASC));


CREATE TABLE `users` (
  `User_Id` int(11) NOT NULL AUTO_INCREMENT,
  `User_Name` varchar(255) NOT NULL,
  `User_Image` varchar(2083) DEFAULT NULL,
  `User_Phone` varchar(20) DEFAULT NULL,
  `User_Email` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`User_Id`),
  UNIQUE KEY `User_Id_UNIQUE` (`User_Id`));

  CREATE TABLE `rover`.`sittings` (
  `Sitting_Id` INT NOT NULL AUTO_INCREMENT,
  `User_Id` INT NOT NULL,
  `Sitter_Id` INT NOT NULL,
  `Sitting_StartDate` DATE NOT NULL,
  `Sitting_EndDate` DATE NOT NULL,
  `Sitting_Text` TEXT NULL,
  `Sitting_Rate` INT NULL,
  PRIMARY KEY (`Sitting_Id`),
  UNIQUE INDEX `Ssitting_Id_UNIQUE` (`Sitting_Id` ASC));


CREATE TABLE `rover`.`sitting_pet_list` (
  `Sitting_Id` INT NOT NULL,
  `Pet_Id` INT NOT NULL,
  PRIMARY KEY (`Sitting_Id`));
