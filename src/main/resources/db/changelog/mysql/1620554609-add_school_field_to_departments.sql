-- liquibase formatted sql

-- changeset 1620554609:add_school_field_to_departments
ALTER TABLE departments ADD tinh_thanh varchar(255) default null;
ALTER TABLE departments ADD quan_huyen varchar(255) default null;
ALTER TABLE departments ADD xa_phuong varchar(255) default null;
ALTER TABLE departments ADD address varchar(255) default null;
ALTER TABLE departments ADD email varchar(255) default null;
ALTER TABLE departments ADD phone varchar(255) default null;
ALTER TABLE departments ADD website varchar(255) default null;
ALTER TABLE departments ADD tax varchar(255) default null;
ALTER TABLE departments ADD school_funding_type varchar(255) default null;
ALTER TABLE departments ADD school_level varchar(255) default null;
ALTER TABLE departments ADD school_principal varchar(255) default null;
-- rollback
