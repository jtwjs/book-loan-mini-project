

-- BOOK_BORROW.LORN_PERIOD(반납기한)자동계산 트리거 -> 자바에서 설정하는걸로~ 

--CREATE OR REPLACE TRIGGER LORN_TRIGGER
--AFTER INSERT ON BOOK_BORROW 
--WHEN 

-- 도서 대출시 재고수량 변경 


-- BOOK_BORROW.LORN_PERIOD(반납기한)자동계산 트리거 -> 자바에서 설정하는걸로~ 

--CREATE OR REPLACE TRIGGER LORN_TRIGGER
--AFTER INSERT ON BOOK_BORROW 
--WHEN 

-- 도서 대출시 재고수량 변경 
CREATE OR REPLACE TRIGGER BORROW_TRIGGER
after insert on book_borrow
for each row
begin
    update book_info
    set inventory_quantity = inventory_quantity - 1
    where claim_symbol = :new.claim_symbol;
end;
/

CREATE OR REPLACE TRIGGER return_TRIGGER
after insert on book_borrow
for each row
begin
    update book_info
    set inventory_quantity = inventory_quantity + 1
    where claim_symbol = :new.claim_symbol;
end;
/                                

create or replace trigger backup_trigger -- 대출이력 생성트리거
after delete on book_borrow
for each row
begin
    insert into backup_borrow(NO,ID,BOOK_TITLE,Publisher,borrow_date)
    values(BACKUP_BORROW_SEQ.nextval,:old.ID,:old.borrow_title,:old.borrow_publisher,:old.borrow_date);
end;
/

CREATE or replace trigger bakcup_app_trigger -- 신청도서 중 처리완료 한것들
after delete on book_application
for each row
begin
    insert into backup_application(no,id,book_title,publisher,application_date,application_state)
    values(backup_application_SEq.nextval,:old.id,:old.book_title,:old.publisher,sysdate,'처리완료');
end;
/

desc book_borrow;

create or replace trigger application_trigger -- 신청도서 접수 트리거
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
    
desc book_application;
desc book_info;
desc backup_application;
desc book_member;



desc backup_borrow;
ORA-01400: NULL을 ("SCOTT"."BACKUP_BORROW"."NO") 안에 삽입할 수 없습니다
ORA-06512: "SCOTT.BACKUP_TRIGGER",  2행
ORA-04088: 트리거 'SCOTT.BACKUP_TRIGGER'의 수행시 오류
select * from book_info;