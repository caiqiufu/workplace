SELECT * FROM unieap.role_resource;
SET SQL_SAFE_UPDATES = 0;
delete from role_resource;
insert into role_resource (role_resource_id,role_id,resource_id,resource_type,category,active_flag,create_by,create_date)
select (SELECT NEXTVAL('unieap')) as role_resource_id, '1' as role_id, dd.dic_code as resource_id, 
dd.dic_type as resource_type,dd.parent_id as category,
'Y' as active_flag, 'unieap' as create_by,'2015-11-08 10:12:51' as create_date from dic_data_tree dd;