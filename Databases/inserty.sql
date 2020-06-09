-- DODAWANIE NOWEGO PRZEPISU:

-- -- 1. Jeżeli brak oczekiwanej kategorii, to:                (idCategory autoincrementowane)
-- INSERT INTO CATEGORY (category)
-- VALUES ('DRUGIE SNIADANIE');

-- 2 Jeżeli nowa pozycja w DISH, to dodać powiązania z kategoriami.       (imo po stronie serwera to śmiga)
INSERT INTO RECIPE_CATEGORY (idDish, idCategory)
VALUES ('1', '1');

-- 3. Faktyczny insert do RECIPE:                       (idRecipe autoincrementowane)
INSERT INTO RECIPE (name, preparationTime, cookingTime, origin, serves, difficulty, content)
VALUES ("schaboszczak", 10, 20, '\N', 5, 1, 'unique_name.txt');

-- 4. Powiązanie produktów z przepisem:
INSERT INTO RAW_PRODUCT_USE (idRecipe, idRawProduct, amount, idUnit)
VALUES (,,,,,)

