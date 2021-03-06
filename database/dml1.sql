
-- 재고수량 변경트리거 (도서 대출시 재고 -1)
CREATE OR REPLACE TRIGGER BORROW_TRIGGER
after insert on book_borrow
for each row
begin
    update book_info
    set inventory_quantity = inventory_quantity - 1
    where claim_symbol = :new.claim_symbol;
end;
/

-- 재고수량 변경트리거 (도서 반납시 재고 +1)
CREATE OR REPLACE TRIGGER return_TRIGGER
after insert on book_borrow
for each row
begin
    update book_info
    set inventory_quantity = inventory_quantity + 1
    where claim_symbol = :new.claim_symbol;
end;
/                                

-- 대출 백업테이블에 쓸 시퀸스 
CREATE SEQUENCE BACKUP_BORROW_SEQ
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 1000
    NOCYCLE;
    
-- 대출 백업테이블 생성 트리거
create or replace trigger backup_trigger
after delete on book_borrow
for each row
begin
    insert into backup_borrow(NO,ID,BOOK_TITLE,Publisher,borrow_date)
    values(BACKUP_BORROW_SEQ.nextval,:old.ID,:old.borrow_title,:old.borrow_publisher,:old.borrow_date);
end;
/

 -- 신청 백업 테이블에 쓸 시퀸스
CREATE SEQUENCE BACKUP_application_SEQ
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 1000
    NOCYCLE; 
    
-- 신청 백업테이블 생성 트리거
CREATE or replace trigger bakcup_app_trigger 
after delete on book_application
for each row
begin
    insert into backup_application(no,id,book_title,publisher,application_date,application_state)
    values(backup_application_SEq.nextval,:old.id,:old.book_title,:old.publisher,sysdate,'처리완료');
end;
/

-- 신청도서 접수트리거
create or replace trigger application_trigger 
after insert on book_info
for each row
declare 
boolean number;
begin  
    select count(*) 
    into boolean
    from book_application 
    where book_title = :new.title and publisher = :new.publisher;
    
    if boolean >0 then 
    delete from book_application 
    where book_title = :new.title and publisher = :new.publisher;
    end if;
end;
/

-- SELECT
Select count(*) from book_member where id = ? -- ID 존재여부확인
Select count(*) from book_manager where id = ?-- 관리자ID 존재여부확인
Select count(*) from book_manager where pw = ?-- 관리자PW 존재여부확인
select count(*) from BOOK_MEMBER               -- 전체 회원 수 검색
select count(*) from BOOK_INFO                  -- 전체 도서 수량 검색
Select * FROM Select ROWNUM rnum, A.*         --  ID 오름차순 검색
    FROM (Select ID,NAME FROM BOOK_MEMBER
    order by id asc) A
    where ROWNUM <= ?+9)
    where rnum >= ?
Select * FROM (Select ROWNUM rnum, A.*        -- 이름 오름차순 검색
    FROM(Select ID,NAME FROM BOOK_MEMBER order by name asc) A
    where ROWNUM <= ?+9)
    where rnum >= ?
Select * FROM(Select ROWNUM rnum, A.*           -- 도서테이블 행 10개씩 출력
    FROM(Select title,publisher,location,inventory_quantity
        FROM BOOK_INFO
        order by title asc) A
    where ROWNUM <= ?+9)
  where rnum >= ?

Select * FROM(Select ROWNUM rnum, A.*                   -- 신청이력 번호붙여 조회
    FROM (Select book_title, publisher, application_date, application_state
          FROM BOOK_applicationwhere id = ? 
          order by application_date asc) A
          ) 
select count(*) from BOOK_INFO where title like ? and inventory_quantity >0  -- 재고 1개이상인 도서 검색  

Select * From BOOK_MEMBER Where name = ?        -- 이름으로 회원 검색      
Select * From BOOK_MEMBER Where phone = ?       -- 폰번으로 회원 검색
Select * From BOOK_MEMBER Where email = ?       -- 이메일로 회원 검색
Select * From BOOK_MEMBER Where addr = ?         -- 주소로 회원 검색



--INSERT
INSERT INTO BOOK_MEMBER(id,name,phone,email,addr) VALUES(?,?,?,?,?)--회원추가
Insert into Book_info values (?, ?, ?, ?, TO_DATE(?,'YYYY-MM-DD'), ?, ?) --도서 추가 
Insert into book_application(id,book_title,publisher) values( ?,?,?) --신청도서 추가
Insert into book_borrow(id, claim_symbol, borrow_title, BORROW_PUBLISHER) values(?,?,?,?) -- 대출도서 추가
-- UPDATE
Update BOOK_MEMBER Set phone = ?, email = ?, addr = ? Where ID = ? -- 회원정보수정
Update BOOK_INFO Set location = ?, inventory_quantity = ? Where claim_symbol = ? -- 도서정보수정


-- DELETE
Delete From BOOK_MEMBER Where ID = ?                -- 회원정보 삭제
Delete From BOOK_INFO Where claim_symbol = ?        -- 도서정보 삭제