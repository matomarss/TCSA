--len modifikacia TCSA.sql
UPDATE passengers
SET passengers_num = 10
WHERE time = 7 AND orderID = 1 AND line_name = '2';