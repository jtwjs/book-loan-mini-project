-- 회원테이블
CREATE TABLE BOOK_MEMBER(
    ID varchar2(15),
    membership_date date default sysdate,
    name varchar2(8) not null,
    phone char(13) not null,
    email varchar2(20),
    addr varchar(20),
    CONSTRAINT BOOK_MEMBER_PK PRIMARY KEY(ID));

-- 관리자 테이블
CREATE TABLE BOOK_MANAGER(
ID varchar2(15),
PW varchar2(20),
    CONSTRAINTS BOOK_MANAGER_PK PRIMARY KEY(ID));
insert into book_manager values( 'BITCAMP','qlxmzoavm'); 
select count(*) from book_manager where pw = 'qlxmzoavm';
commit;

-- 도서테이블
CREATE TABLE BOOK_INFO(
    claim_symbol varchar2(20),
    title varchar2(22) not null,
    Author varchar2(20) not null,
    publisher varchar2(20) not null,
    publication_date date not null,
    location varchar2(20),
    Inventory_quantity number default 0,
    CONSTRAINTS BOOK_INFO_PK PRIMARY KEY(claim_symbol),
    CONSTRAINTS BOOK_INFO_QUANTITY_CK CHECK(Inventory_quantity >=0)
); 

-- 도서신청 테이블
create table BOOK_application( 
    id varchar2(15),
    book_title varchar2(22) not null,
    publisher varchar2(20) not null,
    application_date date default sysdate,
    application_state varchar2(10) default '처리대기', 
    CONSTRAINTS BOOK_application_PK PRIMARY KEY(id,book_title),
    CONSTRAINTS BOOK_application_FK FOREIGN KEY(id) references book_member(id),
    CONSTRAINTS BOOK_application_CK check(application_state in ('처리완료','처리대기'))
    ); 
    

-- 신청이력 테이블
create table backup_application( 
  no number,
  id varchar2(15),
  book_title varchar2(22) not null,
    publisher varchar2(20) not null,
    application_date date default sysdate,
    application_state varchar2(10) default '처리완료', 
    Constraints BACKUP_APPLICATION_PK Primary key(no),
    Constraints BACKUP_APPLICATION_FK foreign key(ID) references BOOK_MEMBER(ID) );
    
-- 대출테이블
CREATE TABLE BOOK_BORROW(
    ID varchar2(15),
    claim_symbol varchar2(20),
    borrow_title varchar2(22) not null,
    borrow_publisher varchar2(20),
    borrow_date date default sysdate,
    loan_period date default sysdate+14,
    CONSTRAINTS BOOK_BORROW_PK PRIMARY KEY(ID,claim_symbol),
    CONSTRAINTS BOOK_BORROW_FK1 FOREIGN KEY(ID) REFERENCES BOOK_MEMBER(ID),
    CONSTRAINtS BOOK_BORROW_FK2 FOREIGN KEY(claim_symbol) REFERENCES BOOK_INFO(Claim_symbol)
);


    
-- 대출이력 테이블
CREATE TABLE BACKUP_BORROW(
    NO NUMBER,
    ID VARCHAR2(15) not null,
    BOOK_TITLE VARCHAR2(22) not null,
    publisher varchar2(20) not null,
    BORROW_DATE DATE,
    RETURN_DATE DATE default sysdate,
    Constraints BACKUP_BORROW_PK Primary key(no),
    Constraints BACKUP_BORROW_FK foreign key(ID) references BOOK_MEMBER(ID) );
desc backup_borrow;






