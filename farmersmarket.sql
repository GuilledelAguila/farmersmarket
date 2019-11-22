-- Farmer's Market
-- Ian Quain, Nick Harper, Guillermo Del Aguila Ferrandis

-- CREATE DATABASE
DROP DATABASE IF EXISTS farmersmarket;
CREATE DATABASE farmersmarket;

-- USE DATABASE
USE farmersmarket;

-- CREATE TABLES
CREATE TABLE farm (
    fid INT PRIMARY KEY AUTO_INCREMENT,
    farm_name VARCHAR(50) NOT NULL,
    location VARCHAR(50) NOT NULL,
    farmer VARCHAR(50) NOT NULL
);

CREATE TABLE seller (
    sid INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL
);

CREATE TABLE seller_to_farm (
    sid INT NOT NULL,
    fid INT NOT NULL,
    CONSTRAINT PRIMARY KEY (sid , fid),
    CONSTRAINT seller_to_farm_fk_seller FOREIGN KEY (sid)
        REFERENCES seller (sid),
    CONSTRAINT seller_to_farm_fk_farm FOREIGN KEY (fid)
        REFERENCES farm (fid)
);

CREATE TABLE catalog (
    cid INT PRIMARY KEY,
    produce_name VARCHAR(50) NOT NULL
);

CREATE TABLE produce (
    pid INT PRIMARY KEY,
    quantity INT NOT NULL,
    cid INT NOT NULL,
    fid INT NOT NULL,
    CONSTRAINT produce_fk_catalog FOREIGN KEY (cid)
        REFERENCES catalog (cid),
    CONSTRAINT produce_fk_farm FOREIGN KEY (fid)
        REFERENCES farm (fid)
);

CREATE TABLE buyer (
    bid INT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL
);

CREATE TABLE courier (
    courid INT PRIMARY KEY,
    courier_name VARCHAR(50) NOT NULL,
    courier_type VARCHAR(50) NOT NULL,
    fee DECIMAL NOT NULL
);

CREATE TABLE posting (
    postingid INT PRIMARY KEY,
    sid INT NOT NULL,
    pid INT NOT NULL,
    courid INT NOT NULL,
    cost DECIMAL NOT NULL,
    date_posted DATE NOT NULL,
    CONSTRAINT posting_fk_seller FOREIGN KEY (sid)
        REFERENCES seller (sid),
    CONSTRAINT posting_fk_produce FOREIGN KEY (pid)
        REFERENCES produce (pid),
    CONSTRAINT posting_fk_courier FOREIGN KEY (courid)
        REFERENCES courier (courid)
);

CREATE TABLE buyer_to_posting (
    bid INT NOT NULL,
    postingid INT NOT NULL,
    date_sold DATE NOT NULL,
    CONSTRAINT PRIMARY KEY (bid , postingid),
    CONSTRAINT buyer_to_posting_fk_buyer FOREIGN KEY (bid)
        REFERENCES buyer (bid),
    CONSTRAINT buyer_to_posting_fk_posting FOREIGN KEY (postingid)
        REFERENCES posting (postingid)
);

CREATE TABLE review (
    rid INT PRIMARY KEY AUTO_INCREMENT,
    bid INT NOT NULL,
    fid INT NOT NULL,
    review VARCHAR(250) NOT NULL,
    CONSTRAINT review_fk_buyer FOREIGN KEY (bid)
        REFERENCES buyer (bid),
    CONSTRAINT review_fk_farm FOREIGN KEY (fid)
        REFERENCES farm (fid)
);
    
    
