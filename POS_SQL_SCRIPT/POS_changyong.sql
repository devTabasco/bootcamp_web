/* TABLE 생성 */
create table ORDERS(
OS_STCODE   NCHAR(10),
OS_SECODE    NCHAR(3),
OS_DATE   DATE,
OS_GCCODE   NCHAR(16),
OS_GCSGCODE NCHAR(8),
OS_TACODE   NUMBER(3)
)tablespace users;

create public synonym OS for POSDBA2.ORDERS;

create table ORDERSDETAILS(
OD_OSSTCODE   NCHAR(10),
OD_OSSECODE    NCHAR(3),
OD_OSDATE   DATE,
OD_OSGCCODE   NCHAR(16),
OD_GCSGCODE NCHAR(8),
OD_OSTACODE   NUMBER(3),
OD_SMCODE   NCHAR(10),
OD_DATE DATE,
OD_QUANTITY NVARCHAR2(2)
)tablespace users;

create public synonym OD for POSDBA2.ORDERSDETAILS;

create table SALESDETAILS(
SD_OSSTCODE   NCHAR(10),
SD_OSSECODE    NCHAR(3),
SD_OSDATE   DATE,
SD_OSGCCODE   NCHAR(16),
SD_GCSGCODE NCHAR(8),
SD_OSTACODE   NUMBER(3),
SD_DATE   NCHAR(10),
SD_APPROVALNUM NCHAR(9),
SD_PRICE NVARCHAR2(10),
SD_INSTALLMENT  NVARCHAR2(2),
SD_CHARDNUM NVARCHAR2(16)
)tablespace users;

create public synonym SD for POSDBA2.SALESDETAILS;

/* CONSTRAINT */

/* DEV 계정생성 */
create user keonho
IDENTIFIED BY "1234"
default tablespace users
quota 20M on users;

create user jyj
IDENTIFIED BY "1234"
default tablespace users
quota 20M on users;

create user jhw
IDENTIFIED BY "1234"
default tablespace users
quota 20M on users;

create user changyong
IDENTIFIED BY "1234"
default tablespace users
quota 20M on users;

/* 권한 부여 */
GRANT CREATE SESSION, resource TO keonho;
GRANT CREATE SESSION, resource TO jyj;
GRANT CREATE SESSION, resource TO jhw;
GRANT CREATE SESSION, resource TO changyong;

grant alter any table to DBA2;
grant alter any table to keonho;
grant alter any table to jyj;
grant alter any table to jhw;
grant alter any table to changyong;

grant select, insert, update on STORES to keonho;
grant select, insert, update on STORES to jyj;
grant select, insert, update on STORES to jhw;
grant select, insert, update on STORES to changyong;

grant select, insert, update on STOREIMAGES to keonho;
grant select, insert, update on STOREIMAGES to jyj;
grant select, insert, update on STOREIMAGES to jhw;
grant select, insert, update on STOREIMAGES to changyong;

grant select, insert, update on STOREGROUP to keonho;
grant select, insert, update on STOREGROUP to jyj;
grant select, insert, update on STOREGROUP to jhw;
grant select, insert, update on STOREGROUP to changyong;

grant select, insert, update on STOREEMPLOYEES to keonho;
grant select, insert, update on STOREEMPLOYEES to jyj;
grant select, insert, update on STOREEMPLOYEES to jhw;
grant select, insert, update on STOREEMPLOYEES to changyong;

grant select, insert, update on STORECATEGORIES to keonho;
grant select, insert, update on STORECATEGORIES to jyj;
grant select, insert, update on STORECATEGORIES to jhw;
grant select, insert, update on STORECATEGORIES to changyong;

grant select, insert, update on STORECATEGORIES to keonho;
grant select, insert, update on STORECATEGORIES to jyj;
grant select, insert, update on STORECATEGORIES to jhw;
grant select, insert, update on STORECATEGORIES to changyong;

grant select, insert, update on STOREMENUES to keonho;
grant select, insert, update on STOREMENUES to jyj;
grant select, insert, update on STOREMENUES to jhw;
grant select, insert, update on STOREMENUES to changyong;

grant select, insert, update on STORETABLES to keonho;
grant select, insert, update on STORETABLES to jyj;
grant select, insert, update on STORETABLES to jhw;
grant select, insert, update on STORETABLES to changyong;

grant select, insert, update on ACCESSLOG to keonho;
grant select, insert, update on ACCESSLOG to jyj;
grant select, insert, update on ACCESSLOG to jhw;
grant select, insert, update on ACCESSLOG to changyong;

grant select, insert, update on COSTPRICEMGR to keonho;
grant select, insert, update on COSTPRICEMGR to jyj;
grant select, insert, update on COSTPRICEMGR to jhw;
grant select, insert, update on COSTPRICEMGR to changyong;

grant select, insert, update on GROUPCUSTOMERS to keonho;
grant select, insert, update on GROUPCUSTOMERS to jyj;
grant select, insert, update on GROUPCUSTOMERS to jhw;
grant select, insert, update on GROUPCUSTOMERS to changyong;

/* 그룹코드 시퀀스 생성 */
CREATE SEQUENCE SG_CODE_SEQUENCE
       INCREMENT BY 1
       START WITH 0000001
       MINVALUE 0000001
       MAXVALUE 9999999
       NOCYCLE
       NOCACHE
       NOORDER;
       
/* 제약 조건 */
add constraint PK_CP_SMSTCODE_SMCODE_DATE primary key(CP_SMSTCODE, CP_SMCODE, CP_DATE);
Add constraint COSTPRICEMGR foreign key () references ();

CONSTRAINT pk_id PRIMARY KEY

alter table changyong.ACCESSHISTORY
modify AH_DATE DEFAULT sysdate
modify AH_STATE not null;

alter table changyong.TODO
modify TD_FEEDBACK DEFAULT 'NONE'
modify TD_STATE DEFAULT 'B'
modify TD_ACTIVATION DEFAULT 'A';
commit;

