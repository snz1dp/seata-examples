
CREATE SEQUENCE SEQ_STORAGE_TBL_ID;

CREATE TABLE STORAGE_TBL (
   id                   INT4                 not null default nextval('SEQ_STORAGE_TBL_ID'::regclass),
   commodity_code       VARCHAR(255)         null,
   count                INT4                 null,
   CONSTRAINT PK_STORAGE_TBL PRIMARY KEY (id)
);

