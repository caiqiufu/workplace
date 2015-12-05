show create table unieap.seq;
select version();
/**********************
 * update max connection, max update, max user connection limit 
 */
select * from MYSQL.USER;

SELECT * FROM unieap.role_resource;
SET SQL_SAFE_UPDATES = 0;
update user_role set create_date = current_timestamp()