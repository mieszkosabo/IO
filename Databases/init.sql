DROP DATABASE IF EXISTS projectdb;

CREATE DATABASE projectdb DEFAULT CHARACTER SET utf8 COLLATE utf8_polish_ci;
USE projectdb;

CREATE TABLE CATEGORY (
    idCategory          INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    category            VARCHAR(50) NOT NULL
);

CREATE TABLE DISH (
    idDish              INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    dish                VARCHAR(100) NOT NULL,
    idCategory          INT NOT NULL,

    FOREIGN KEY (idCategory) REFERENCES CATEGORY (idCategory)
);

CREATE TABLE RATE (
    idRate              INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    one                 INT DEFAULT 0,
    two                 INT DEFAULT 0,
    three               INT DEFAULT 0,
    four                INT DEFAULT 0,
    five                INT DEFAULT 0
);

CREATE TABLE DIFFICULTY (
    idDifficulty        INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    difficulty               VARCHAR(20) NOT NULL
);

CREATE TABLE RECIPE (
    idRecipe            INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    idDish              INT NOT NULL,
    preparationTime     INT,
    cookingTime         INT,
    origin              VARCHAR(20),
    serves              INT,
    idDifficulty        INT,
    idRate              INT NOT NULL,
    content             TEXT NOT NULL,

    FOREIGN KEY (idDish) REFERENCES DISH (idDish),
    FOREIGN KEY (idRate) REFERENCES RATE (idRate),
    FOREIGN KEY (idDifficulty) REFERENCES DIFFICULTY (idDifficulty)
);

#ALTER TABLE RECIPE MODIFY content TEXT CHARACTER SET utf8;

CREATE TABLE DIET (
    idDiet              INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    diet                VARCHAR(20) NOT NULL
);
/* w 100 gramach */
CREATE TABLE GOODNESS (
    idGoodness          INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    kcal                FLOAT,
    energy              FLOAT,
    fats                FLOAT,
    saturates           FLOAT,
    carbohydrates       FLOAT,
    sugars              FLOAT,
    proteins            FLOAT,
    salts               FLOAT
);

CREATE TABLE PRODUCT (
    EAN                 VARCHAR(20) NOT NULL PRIMARY KEY,
    product             VARCHAR(100) NOT NULL,
    idGoodness          INT NOT NULL,

    FOREIGN KEY (idGoodness) REFERENCES GOODNESS (idGoodness)
);

CREATE TABLE UNIT (
    idUnit              INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    unit                VARCHAR(20)
);

CREATE TABLE RAW_PRODUCT (
    idRawProduct        INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    rawProduct          VARCHAR(50),
    idDiet              INT NOT NULL,

    FOREIGN KEY (idDiet) REFERENCES DIET (idDiet)
);

CREATE TABLE INGREDIENTS (
    EAN                 VARCHAR(20) NOT NULL,
    idRawProduct        INT NOT NULL,
    amount              INT,
    idUnit              INT,

    FOREIGN KEY (EAN) REFERENCES PRODUCT (EAN),
    FOREIGN KEY (idRawProduct) REFERENCES RAW_PRODUCT(idRawProduct),
    FOREIGN KEY (idUnit) REFERENCES UNIT(idUnit)
);

CREATE TABLE PRODUCT_USE (
    idRecipe            INT NOT NULL,
    EAN                 VARCHAR(20) NOT NULL,
    amount              VARCHAR(20) NOT NULL,
    idUnit              INT,

    FOREIGN KEY (idRecipe)  REFERENCES RECIPE (idRecipe),
    FOREIGN KEY (EAN) REFERENCES PRODUCT (EAN),
    FOREIGN KEY (idUnit) REFERENCES UNIT (idUnit)
);

CREATE TABLE USER (
    idUser              INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username            VARCHAR(20) NOT NULL UNIQUE,
    password            VARCHAR(100) NOT NULL,
    date                DATETIME DEFAULT CURRENT_TIMESTAMP,
    idDiet              INT,

    FOREIGN KEY (idDiet) REFERENCES DIET (idDiet)
);

CREATE TABLE ALLERGY (
    idUser              INT NOT NULL,
    idRawProduct        INT NOT NULL,

    FOREIGN KEY (idUser)    REFERENCES USER (idUser),
    FOREIGN KEY (idRawProduct) REFERENCES RAW_PRODUCT (idRawProduct)
);

CREATE TABLE USER_DISH (
    idUser              INT NOT NULL,
    idRecipe            INT NOT NULL,
    date                DATE NOT NULL,
    rate                INT NOT NULL,

    FOREIGN KEY (idUser) REFERENCES USER (idUser),
    FOREIGN KEY (idRecipe) REFERENCES RECIPE (idRecipe)
);

                            /******TRIGGERY******/

DROP TRIGGER IF EXISTS RATE_BEFORE_INSERT;
DELIMITER $$

CREATE TRIGGER RATE_BEFORE_INSERT
    BEFORE INSERT ON RATE
    FOR EACH ROW
    BEGIN
        DECLARE bad_data BOOLEAN DEFAULT FALSE;
        SET bad_data = FALSE;
        IF NEW.five < 0 THEN
            SET NEW.five = 0;
            SET bad_data := TRUE;
        END IF;
        IF NEW.four < 0 THEN
            SET NEW.four = 0;
            SET bad_data = TRUE;
        END IF;
        IF NEW.three < 0 THEN
            SET NEW.three = 0;
            SET bad_data = TRUE;
        END IF;
        IF NEW.two < 0 THEN
            SET NEW.two = 0;
            SET bad_data = TRUE;
        END IF;
        IF NEW.one < 0 THEN
            SET NEW.one = 0;
            SET bad_data = TRUE;
        END IF;
        IF bad_data = TRUE THEN
            SIGNAL SQLSTATE '45000' set message_text='Invalid data in RATE!';
        END IF;
    END;
$$


DROP TRIGGER IF EXISTS RECIPE_BEFORE_INSERT;

CREATE TRIGGER RECIPE_BEFORE_INSERT
    BEFORE INSERT ON RECIPE
    FOR EACH ROW
    BEGIN
        DECLARE bad_data BOOLEAN DEFAULT FALSE;
        SET bad_data = FALSE;
        IF NEW.cookingTime < 0 THEN
            SET NEW.cookingTime = 0;
            SET bad_data = TRUE;
        END IF;
        IF NEW.preparationTime < 0 THEN
            SET NEW.preparationTime = 0;
            SET bad_data = TRUE;
        END IF;
        IF NEW.serves < 0 THEN
            SET NEW.serves = 0;
            SET bad_data = TRUE;
        END IF;
        IF bad_data = TRUE THEN
            SIGNAL SQLSTATE '45000' set message_text = 'Invalid data in RECIPE!';
        END IF;
    END;
$$



DROP TRIGGER IF EXISTS USER_DISH_BEFORE_INSERT;

CREATE TRIGGER USER_DISH_BEFORE_INSERT
    BEFORE INSERT ON USER_DISH
    FOR EACH ROW
    BEGIN
        DECLARE bad_data BOOLEAN DEFAULT FALSE;
        SET bad_data = FALSE;
        IF DATEDIFF(NEW.date, CURDATE()) > 0 THEN
            SET NEW.date = CURDATE();
            SET bad_data := TRUE;
        END IF;
        IF NEW.rate < 0 || NEW.rate > 5 THEN
            SET NEW.rate = 0;
            SET bad_data = TRUE;
        END IF;
        IF bad_data = TRUE THEN
            SIGNAL SQLSTATE '45000' set message_text = 'Invalid data in RECIPE!';
        END IF;
    END;
$$

DELIMITER ;


                                      /********LOADY*******/


LOAD DATA LOCAL INFILE 'csv/categories.csv'
    INTO TABLE CATEGORY
    FIELDS TERMINATED BY ';'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;
/*
LOAD DATA LOCAL INFILE 'csv/dish.csv'
    INTO TABLE DISH
    FIELDS TERMINATED BY ';'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'csv/rate.csv'
    INTO TABLE RATE
    FIELDS TERMINATED BY ';'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'csv/difficulty.csv'
    INTO TABLE DIFFICULTY
    FIELDS TERMINATED BY ';'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'csv/recipe.csv'
    INTO TABLE RECIPE
    FIELDS TERMINATED BY ';'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;
*/

LOAD DATA LOCAL INFILE 'csv/unit.csv'
    INTO TABLE UNIT
    FIELDS TERMINATED BY ';'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'csv/diet.csv'
    INTO TABLE DIET
    FIELDS TERMINATED BY ';'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'csv/raw_product.csv'
    INTO TABLE RAW_PRODUCT
    FIELDS TERMINATED BY ';'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'csv/goodness.csv'
    INTO TABLE GOODNESS
    FIELDS TERMINATED BY ';'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'csv/product.csv'
    INTO TABLE PRODUCT
    FIELDS TERMINATED BY ';'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'csv/ingredients.csv'
    INTO TABLE INGREDIENTS
    FIELDS TERMINATED BY ';'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

/*
LOAD DATA LOCAL INFILE 'csv/product_use.csv'
    INTO TABLE PRODUCT_USE
    FIELDS TERMINATED BY ';'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'csv/user.csv'
    INTO TABLE PRODUCT_USE
    FIELDS TERMINATED BY ';'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'csv/allergy.csv'
    INTO TABLE PRODUCT_USE
    FIELDS TERMINATED BY ';'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'csv/user_dish.csv'
    INTO TABLE PRODUCT_USE
    FIELDS TERMINATED BY ';'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;*/
