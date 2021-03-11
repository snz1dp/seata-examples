
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

create table account_tbl (
    account_id       varchar(255) default null,
    account_money    int4      default 0,
    constraint pk_account_tbl primary key (account_id)
);

insert into account_tbl (account_id, account_money) values ('1001', 10000);
insert into account_tbl (account_id, account_money) values ('1002', 10000);
