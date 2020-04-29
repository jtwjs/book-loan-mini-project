-- 회원테이블
CREATE TABLE BOOK_MEMBER(
    ID varchar2(15),
    membership_date date ,
    name varchar2(8) not null,
    phone char(13) not null,
    email varchar2(20),
    addr varchar(20),
    CONSTRAINT BOOK_MEMBER_PK PRIMARY KEY(ID));
desc book_member;
-- 도서테이블


CREATE TABLE BOOK_INFO(
    ISBN varchar2(13),
    title varchar2(20) not null,
    Author varchar2(20) not null,
    publisher varchar2(20) not null,
    publication_date date not null,
    location varchar2(20),
    claim_symbol varchar2(20),
    Inventory_quantity number default 0,
    CONSTRAINTS BOOK_INFO_PK PRIMARY KEY(ISBN),
    CONSTRAINTS BOOK_INFO_QUANTITY_CK CHECK(Inventory_quantity >=0)
);   
desc book_info;
--대출테이블
CREATE TABLE BOOK_BORROW(
    ID varchar2(15),
    ISBN varchar2(13),
    borrow_title varchar2(20) not null,
    borrow_publisher varchar2(20),
    borrow_date date default sysdate,
    loan_period date default sysdate+14,
    CONSTRAINTS BOOK_BORROW_PK PRIMARY KEY(ID,ISBN),
    CONSTRAINTS BOOK_BORROW_FK1 FOREIGN KEY(ID) REFERENCES BOOK_MEMBER(ID),
    CONSTRAINtS BOOK_BORROW_FK2 FOREIGN KEY(ISBN) REFERENCES BOOK_INFO(ISBN)
);
desc book_borrow;
-- 대출이력테이블에 쓸 시퀸스 
CREATE SEQUENCE BACKUP_BORROW_SEQ
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 1000
    NOCYCLE;
--대출이력 테이블
CREATE TABLE BACKUP_BORROW(
    NO NUMBER,
    ID VARCHAR2(15) not null,
    BOOK_TITLE VARCHAR2(20) not null,
    publisher varchar2(20) not null,
    BORROW_DATE DATE,
    RETURN_DATE DATE,
    Constraints BACKUP_BORROW_PK Primary key(no),
    Constraints BACKUP_BORROW_FK foreign key(ID) references BOOK_MEMBER(ID) );
desc backup_borrow;




insert into book_info values ('E001','CODE',
desc book_info;
insert into book_member values(9829,'정태웅','010-2379-9829','경기도 성남시');

desc book_borrow;
insert into book_borrow(mem_no,book_code,borrow_title) values('9829','E001','Deep Work');
INSERT INTO BOOK_INFO VALUES ('E001','Deep Work','Calvin C.newport','민음사',SYSDATE,1,'E8-1');

select * from book_borrow;
select * from book_info;
select * from book_member;
select * from user_cons_columns where table_name like 'BOOK%';
-- 대출시 도서 재고수량 -1 되는거까지 함


select * from book_info;
select * from book_member;
select * from book_borrow;
select * from backup_borrow;

desc book_member;
desc book_info;
desc book_borrow;
desc backup_borrow;
-- 확인할것 3가지
--1.대출시 재고수량 -1 ,
--2.반납시 재고수량 +1,
--3.반납시 백업테이블 데이터추가

insert into book_member 
values ('asdf9503',sysdate,'정태웅','010-2379-9829','xodnd9503@gmail.com','경기도 성남시');
insert into book_info
values ('9791155812174','작은아씨들','루이자 메이 올컷','월북',2019,'제1종합자료실(2층)','843-ㄱ271-1',2);
insert into book_borrow(ID,ISBN,BORROW_TITLE,borrow_publisher)
values ('asdf9503','9791155812174','작은아씨들','월북');


select * from user_conn_column;