-- ȸ�����̺�
CREATE TABLE BOOK_MEMBER(
    ID varchar2(15),
    membership_date date default sysdate,
    name varchar2(8) not null,
    phone char(13) not null,
    email varchar2(20),
    addr varchar(20),
    CONSTRAINT BOOK_MEMBER_PK PRIMARY KEY(ID));
desc book_member;
-- ������ ���̺�
CREATE TABLE BOOK_MANAGER(
ID varchar2(15),
PW varchar2(20),
    CONSTRAINTS BOOK_MANAGER_PK PRIMARY KEY(ID));
insert into book_manager values( 'BITCAMP','qlxmzoavm'); //������ ������
select count(*) from book_manager where pw = 'qlxmzoavm';
commit;
-- �������̺�


CREATE TABLE BOOK_INFO(
    claim_symbol varchar2(20),
    title varchar2(20) not null,
    Author varchar2(20) not null,
    publisher varchar2(20) not null,
    publication_date date not null,
    location varchar2(20),
    Inventory_quantity number default 0,
    CONSTRAINTS BOOK_INFO_PK PRIMARY KEY(claim_symbol),
    CONSTRAINTS BOOK_INFO_QUANTITY_CK CHECK(Inventory_quantity >=0)
);   
create table BOOK_application( --������û ���̺�
    id varchar2(15),
    book_title varchar2(20) not null,
    publisher varchar2(20) not null,
    application_date date default sysdate,
    application_state varchar2(10) default 'ó�����', 
    CONSTRAINTS BOOK_application_PK PRIMARY KEY(id,book_title),
    CONSTRAINTS BOOK_application_FK FOREIGN KEY(id) references book_member(id),
    CONSTRAINTS BOOK_application_CK check(application_state in ('ó���Ϸ�','ó�����'))
    ); 
    
   CREATE SEQUENCE BACKUP_application_SEQ -- ��û�̷� ������
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 1000
    NOCYCLE; 
    
     create table backup_application( --��û�̷� ���̺�
  no number,
  id varchar2(15),
  book_title varchar2(20) not null,
    publisher varchar2(20) not null,
    application_date date default sysdate,
    application_state varchar2(10) default 'ó���Ϸ�', 
    Constraints BACKUP_APPLICATION_PK Primary key(no),
    Constraints BACKUP_APPLICATION_FK foreign key(ID) references BOOK_MEMBER(ID) );
  
--�������̺�
CREATE TABLE BOOK_BORROW(
    ID varchar2(15),
    claim_symbol varchar2(20),
    borrow_title varchar2(20) not null,
    borrow_publisher varchar2(20),
    borrow_date date default sysdate,
    loan_period date default sysdate+14,
    CONSTRAINTS BOOK_BORROW_PK PRIMARY KEY(ID,claim_symbol),
    CONSTRAINTS BOOK_BORROW_FK1 FOREIGN KEY(ID) REFERENCES BOOK_MEMBER(ID),
    CONSTRAINtS BOOK_BORROW_FK2 FOREIGN KEY(claim_symbol) REFERENCES BOOK_INFO(Claim_symbol)
);
desc book_borrow;
-- �����̷����̺� �� ������ 
CREATE SEQUENCE BACKUP_BORROW_SEQ
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 1000
    NOCYCLE;
--�����̷� ���̺�
CREATE TABLE BACKUP_BORROW(
    NO NUMBER,
    ID VARCHAR2(15) not null,
    BOOK_TITLE VARCHAR2(20) not null,
    publisher varchar2(20) not null,
    BORROW_DATE DATE,
    RETURN_DATE DATE default sysdate,
    Constraints BACKUP_BORROW_PK Primary key(no),
    Constraints BACKUP_BORROW_FK foreign key(ID) references BOOK_MEMBER(ID) );
desc backup_borrow;




insert into book_info values ('E001','CODE',
desc book_info;
insert into book_member values(9829,'���¿�','010-2379-9829','��⵵ ������');

desc book_borrow;
insert into book_borrow(mem_no,book_code,borrow_title) values('9829','E001','Deep Work');
INSERT INTO BOOK_INFO VALUES ('E001','Deep Work','Calvin C.newport','������',SYSDATE,1,'E8-1');

select * from book_borrow;
select * from book_info;
select * from book_member;
select * from user_cons_columns where table_name like 'BOOK%';
-- ����� ���� ������ -1 �Ǵ°ű��� ��


select * from book_info;
select * from book_member;
select * from book_borrow;
select * from backup_borrow;

desc book_member;
desc book_info;
desc book_borrow;
desc backup_borrow;
-- Ȯ���Ұ� 3����
--1.����� ������ -1 ,
--2.�ݳ��� ������ +1,
--3.�ݳ��� ������̺� �������߰�

insert into book_member 
values ('asdf9503',sysdate,'���¿�','010-2379-9829','xodnd9503@gmail.com','��⵵ ������');
insert into book_info
values ('9791155812174','�����ƾ���','������ ���� ����','����',2019,'��1�����ڷ��(2��)','843-��271-1',2);
insert into book_borrow(ID,ISBN,BORROW_TITLE,borrow_publisher)
values ('asdf9503','9791155812174','�����ƾ���','����');


select * from user_conn_column;
select count(*) from book_member where id = 'asdf';


    select * from Book_application;
desc book_borrow;
desc book_application;
desc backup_borrow;
desc book_info;
select * from book_info;

insert into book_borrow(id,claim_symbol,borrow_title,BORROW_PUBLISHER)
values ('asdf9503','449','RIDE','AMEBA');
select * from book_borrow;
delete from book_Borrow;
desc book_info;
insert into book_info values ('440','RIDE','���¿�','����',TO_DATE('20051212','YYYY-MM-DD'),'��1�ڷ��',5);
select count(*) from Book_info where title = 'RIDE';
select * from (select rownum rnum, A.* 
                from(select * from book_info
                where title = 'RIDE'
                order by title asc) A)
                where rnum = 1;
                commit;
     \
select* from (select Rownum rnum, A.* 
                from (select id,book_title,publisher,
                    application_date,application_state
                        from book_application
                        where id ='asdf9503'
                        order by application_date asc) A
                    );
                    desc book_application;
                    
                    
        delete backup_borrow;
        commit;