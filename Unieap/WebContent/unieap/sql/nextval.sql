
-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- create sequence function
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`unieap`@`192.168.0.11` FUNCTION `nextval`(seqname VARCHAR(50)) RETURNS int(11)
BEGIN
   UPDATE seq SET val=last_insert_id(val+1) WHERE seq_name=seqname;  
   RETURN last_insert_id();  
END