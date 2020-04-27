-- 회원테이블
CREATE TABLE BOOK_MEMBER(
    MEM_NO varchar2(8),
    name varchar2(8) not null,
    phone char(13),
    addr varchar(20),
    CONSTRAINT BOOK_MEMBER_PK PRIMARY KEY(MEM_NO));

-- 도서테이블
CREATE TABLE BOOK_INFO(
    BOOK_CODE varchar2(10),
    title varchar2(20) not null,
    Author varchar2(10) not null,
    publisher varchar2(20) not null,
    publication_date date not null,
    Inventory_quantity number default 0,
    location varchar2(8),
    CONSTRAINTS BOOK_INFO_PK PRIMARY KEY(BOOK_CODE),
    CONSTRAINTS BOOK_INFO_QUANTITY_CK CHECK(Inventory_quantity >=0)
);  
--대출테이블
CREATE TABLE BOOK_BORROW(
    MEM_NO varchar2(8),
    BOOK_CODE varchar2(10),
    borrow_title varchar2(20) not null,
    borrow_date date default sysdate,
    loan_period date default sysdate+14,
    CONSTRAINTS BOOK_BORROW_PK PRIMARY KEY(mem_no,book_code),
    CONSTRAINTS BOOK_BORROW_FK1 FOREIGN KEY(mem_no) REFERENCES BOOK_MEMBER(MEM_NO),
    CONSTRAINtS BOOK_BORROW_FK2 FOREIGN KEY(book_code) REFERENCES BOOK_INFO(BOOK_CODE)
);

insert into book_info values ('E001','CODE',
desc book_info;
insert into book_member values(9829,'정태웅','010-2379-9829','경기도 성남시');

desc book_borrow;
insert into book_borrow(mem_no,book_code,borrow_title) values('9829','E001','Deep Work');
INSERT INTO BOOK_INFO VALUES ('E001','Deep Work','Calvin C.newport','민음사',SYSDATE,1,'E8-1');

select * from book_borrow;
select * from book_info;
select * from user_cons_columns where table_name like 'BOOK%';
-- 대출시 도서 재고수량 -1 되는거까지 함