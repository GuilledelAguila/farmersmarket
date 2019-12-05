-- USER ACTIONS:
-- BUYER BUYS A CERTAIN POST:
USE farmersmarket;
DELIMITER //
DROP PROCEDURE item_bought;
CREATE PROCEDURE item_bought(
    IN bid INT,
    IN postingid INT
)
BEGIN
INSERT INTO buyer_to_posting VALUES (bid, postingid, curdate());
END //
DELIMITER ;

-- call item_bought(1,2);

select * from buyer_to_posting;

-- BUYER WANTS TO SEE THEIR BUYING HISTORY

DELIMITER //
DROP PROCEDURE buyer_history;
CREATE PROCEDURE buyer_history(
    IN inbid INT
)
BEGIN
SELECT date_sold AS date, quantity, CONCAT(first_name, ' ', last_name) AS seller, produce_name AS product, farm_name as farm
 FROM (SELECT * FROM buyer_to_posting WHERE bid = inbid) a
NATURAL JOIN posting
NATURAL JOIN produce
NATURAL JOIN seller
NATURAL JOIN catalog
NATURAL JOIN farm;
END //
DELIMITER ;

call buyer_history(1);

-- FARMER WANTS TO SEE THEIR PRODUCE HISTORY

DELIMITER //
DROP PROCEDURE farmer_history;
CREATE PROCEDURE farmer_history(
    IN inbid INT
)
BEGIN
SELECT pid, produce_name,  sid, CONCAT(first_name, ' ', last_name) AS seller
	FROM (SELECT * FROM farm WHERE fid = inbid) a
NATURAL JOIN posting
NATURAL JOIN produce
NATURAL JOIN seller
NATURAL JOIN catalog
NATURAL JOIN farm;
END //
DELIMITER ;

call farmer_history(8);

-- BUYER WANTS TO SEARCH FOR POSTINGS WITH FILTERS


DROP PROCEDURE search_post;
DELIMITER //

CREATE PROCEDURE search_post(
    IN farm_name VARCHAR(50),
    IN seller_name VARCHAR(100),
    IN product_name VARCHAR(50),
    IN price_lower INT,
    IN price_upper INT
)
BEGIN
DECLARE farm_filter VARCHAR(200);
DECLARE seller_filter VARCHAR(200);
DECLARE product_filter VARCHAR(200);
DECLARE price_filter VARCHAR(200);
DECLARE not_sold VARCHAR(200);
DECLARE count INT;
SET @dynamic_sql = "
    SELECT quantity, CONCAT(first_name, ' ', last_name) AS seller, produce_name AS product, farm_name as farm, cost, date_posted from posting
		NATURAL JOIN produce
		NATURAL JOIN seller
		NATURAL JOIN catalog
		NATURAL JOIN farm
        WHERE posting.postingid not in(SELECT DISTINCT postingid from buyer_to_posting)
	";
IF farm_name IS NULL AND seller_name IS NULL AND product_name IS NULL AND price_lower IS NULL AND price_upper IS NULL THEN
	PREPARE search_posting FROM @dynamic_sql;
ELSE
	SET @dynamic_sql = CONCAT(@dynamic_sql, " HAVING ");
    SET count = 0;
	IF farm_name IS NOT NULL THEN
		SET farm_filter = CONCAT(" farm = ", "'" ,farm_name, "'");
		SET @dynamic_sql = CONCAT(@dynamic_sql, farm_filter);
        SET count = count + 1;
	END IF;
    
	IF seller_name IS NOT NULL THEN
		SET seller_filter = CONCAT("seller = ", "'", seller_name, "'");
        IF  count > 0 THEN 
			SET @dynamic_sql = CONCAT(@dynamic_sql, " AND ", seller_filter);
            SET count = count + 1;
        ELSE
			SET @dynamic_sql = CONCAT(@dynamic_sql, seller_filter);
            SET count = count + 1;
		END IF;
	END IF;
    
	IF product_name IS NOT NULL THEN
		SET product_filter = CONCAT("product = ", "'", product_name, "'");
		IF  count > 0 THEN 
			SET @dynamic_sql = CONCAT(@dynamic_sql, " AND ", product_filter);
            SET count = count + 1;
        ELSE
			SET @dynamic_sql = CONCAT(@dynamic_sql, product_filter);
            SET count = count + 1;
		END IF;
	END IF;
	SELECT @dynamic_sql;
	IF price_lower IS NOT NULL  AND price_upper IS NOT NULL THEN
		SET price_filter = CONCAT(" cost BETWEEN ", price_lower, " AND ", price_upper);
		IF  count > 0 THEN 
			SET @dynamic_sql = CONCAT(@dynamic_sql, " AND ", price_filter);
            SET count = count + 1;
        ELSE
			SET @dynamic_sql = CONCAT(@dynamic_sql, price_filter);
            SET count = count + 1;
		END IF; 
	END IF;
    
    IF price_lower IS NOT NULL  AND price_upper IS NULL THEN
    		SET price_filter = CONCAT(" cost >= ", price_lower);
		IF  count > 0 THEN 
			SET @dynamic_sql = CONCAT(@dynamic_sql, " AND ", price_filter);
            SET count = count + 1;
        ELSE
			SET @dynamic_sql = CONCAT(@dynamic_sql, price_filter);
            SET count = count + 1;
		END IF; 
	END IF;
    
	IF price_lower IS NULL  AND price_upper IS NOT NULL THEN
    		SET price_filter = CONCAT(" cost <= ", price_upper);
		IF  count > 0 THEN 
			SET @dynamic_sql = CONCAT(@dynamic_sql, " AND ", price_filter);
            SET count = count + 1;
        ELSE
			SET @dynamic_sql = CONCAT(@dynamic_sql, price_filter);
            SET count = count + 1;
		END IF; 
	END IF;
    SELECT @dynamic_sql;
	PREPARE search_posting FROM @dynamic_sql;
END IF;
    EXECUTE search_posting;
	DEALLOCATE PREPARE search_posting;
END //
DELIMITER ;

call search_post('Shrute', 'Barack Obama' ,NULL,NULL, NULL);
call search_post(NULL, NULL ,NULL,NULL, NULL);

-- updates seller_to_farm table after a seller partners with a farmer

DELIMITER //
CREATE PROCEDURE farmer_partner(
    IN sid INT,
    IN fid INT
)
BEGIN
INSERT INTO seller_to_farm VALUES (sid, fid);
END //
DELIMITER ;

CALL farmer_partner(3, 1);

-- deletes a posting

DELIMITER //
CREATE PROCEDURE delete_posting(
	IN postid INT
)
BEGIN
DELETE FROM posting WHERE postingid = postid;
END //
DELIMITER ;

CALL delete_posting(10);

