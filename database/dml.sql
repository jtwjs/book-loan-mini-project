

-- BOOK_BORROW.LORN_PERIOD(�ݳ�����)�ڵ���� Ʈ����
--CREATE OR REPLACE TRIGGER LORN_TRIGGER
--AFTER INSERT ON BOOK_BORROW 
--WHEN 

-- ���� ����� ������ ���� 
CREATE OR REPLACE TRIGGER BORROW_TRIGGER
after insert on book_borrow
for each row
begin
    update book_info
    set inventory_quantity = inventory_quantity - 1
    where book_code = :new.book_code;
end;
/
-- �������� ������ Ʈ���� ���� ��
