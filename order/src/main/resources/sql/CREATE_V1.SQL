
-- 撤销日志
--如果后面的语句返回true则忽略指定n条sql语句
$IF$IGN$1$ select count(*) > 0 from pg_class where relname = 'seq_undo_log_id';

create sequence seq_undo_log_id;

$IF$IGN$2$ select count(*) > 0 from pg_class where relname = 'undo_log';

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

create sequence seq_order_tbl_id;

CREATE TABLE order_tbl
(
    id               int4              not null default nextval('seq_order_tbl_id'::regclass),
    account_id       varchar(255)      not null,
    commodity_code   varchar(255)      not null,
    count            int4              not null,
    money            int4              not null,
    constraint pk_order_tbl primary key (id)
);
