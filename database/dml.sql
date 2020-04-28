

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
    where book_code = :new.book_code;
end;
/

