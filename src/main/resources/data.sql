CREATE TABLE IF NOT EXISTS coin
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    code             VARCHAR(255),
    currency_name    VARCHAR(255),
    rate             VARCHAR(255),
    rate_float       DECIMAL(19, 2),
    description      VARCHAR(255),
    last_modify_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO COIN (code, currency_name, rate, rate_float, description, last_modify_time)
VALUES ('USD', '美元', '51,583.726', 51583.7261, 'United States Dollar', CURRENT_TIMESTAMP);
INSERT INTO COIN (code, currency_name, rate, rate_float, description, last_modify_time)
VALUES ('GBP', '英鎊', '40,794.577', 40794.5771, 'British Pound Sterling', CURRENT_TIMESTAMP);
INSERT INTO COIN (code, currency_name, rate, rate_float, description, last_modify_time)
VALUES ('EUR', '歐元', '47,615.442', 47615.4417, 'Euro', CURRENT_TIMESTAMP);
