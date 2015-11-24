DROP TABLE IF EXISTS sequence;
CREATE TABLE sequence (
name              VARCHAR(50) NOT NULL,
current_value INT NOT NULL,
increment       INT NOT NULL DEFAULT 1,
PRIMARY KEY (name)
) ENGINE=InnoDB;
INSERT INTO sequence VALUES ('Unieap',1,1);
DROP FUNCTION IF EXISTS currval;
DELIMITER $
CREATE FUNCTION currval (seq_name VARCHAR(50))
RETURNS INTEGER
CONTAINS SQL
BEGIN
  DECLARE value INTEGER;
  SET value = 0;
  SELECT current_value INTO value
  FROM sequence
  WHERE name = seq_name;
  RETURN value;
END$
DELIMITER ;
DROP FUNCTION IF EXISTS nextval;
DELIMITER $
CREATE FUNCTION nextval (seq_name VARCHAR(50))
RETURNS INTEGER
CONTAINS SQL
BEGIN
   UPDATE sequence
   SET          current_value = current_value + increment
   WHERE name = seq_name;
   RETURN currval(seq_name);
END$
DELIMITER ;