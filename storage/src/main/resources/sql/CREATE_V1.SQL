
-- 撤销日志
--如果后面的语句返回true则忽略指定n条sql语句
$if$ign$1$ select count(*) > 0 from pg_class where relname = 'seq_undo_log_id';

create sequence seq_undo_log_id;

$if$ign$2$ select count(*) > 0 from pg_class where relname = 'undo_log';

create table undo_log (
   id                int8              not null default nextval('seq_undo_log_id'::regclass),
   branch_id         int8              not null,
   xid               varchar(100)      not null,
   context           varchar(128)      not null,
   rollback_info     bytea             not null,
   log_status        int4              not null,
   log_created       timestamp         not null,
   log_modified      timestamp         not null,
   constraint pk_undo_log primary key (id)
);

create unique index idx_undo_log_cid on undo_log(
   xid, branch_id
);

-- 业务表

create sequence seq_storage_tbl_id;

create table storage_tbl (
   id                   int4           not null default nextval('seq_storage_tbl_id'::regclass),
   commodity_code       varchar(255)   null,
   count                int4           null,
   constraint pk_storage_tbl primary key (id)
);

create unique index idx_storage_tbl_cid on storage_tbl(
   commodity_code
);

insert into storage_tbl (commodity_code, count) values (1, '2001', 1000);
