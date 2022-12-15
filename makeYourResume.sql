--계정 생성
CREATE USER resumedba 
IDENTIFIED BY 1234 
DEFAULT TABLESPACE USERS
QUOTA UNLIMITED ON USERS;
    -- GRANT
GRANT DBA TO resumedba;

-- 테이블 생성
create table MEMBERS(
ME_EMAIL   NVARCHAR2(50),
ME_PHONE    NVARCHAR2(11),
ME_PASSWORD   NVARCHAR2(50)
)tablespace users;

--테이블 시노님
create public synonym ME for MEMBERS;

--테이블 제약조건
alter table MEMBERS
ADD constraint ME_EMAIL_PHONE_PW_PK primary key(ME_EMAIL, ME_PHONE);

alter table MEMBERS
modify ME_PASSWORD not null;


