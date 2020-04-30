import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {
	static Scanner sc = new Scanner(System.in);
	static int select;
	final static String driver = "oracle.jdbc.driver.OracleDriver";
	final static String url =  "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	static String account;
	
	public static void main(String[] args) {
		Member_manage mem = new Member_manage();
	while(true) {
		System.out.println("�����������������α���  �޴�����������������");
		System.out.println("���������� ��[1]ȸ�� ���ԡ���������������");
		System.out.println("���������� ��[2]�α��Ρ���������������");
		System.out.println("������ ������[3]�����ڷ� �α��Ρ���������������");
		System.out.println("���������� ��[4]��  �� �������� ��   ����");
		System.out.println("������������������������������������������");
		System.out.print("�� �޴� ���� �� ");
		select = sc.nextInt();
		switch(select) {
			case 1: mem.inputData(); break;
			case 2: login(0); break;
			case 3: login(1); break;
			case 4: System.out.println("# ����Ǿ����ϴ�...");return;
			default : System.out.println("# �߸� �Է��ϼ̽��ϴ�..."); break;
					}
		

			}
	}
	public static void login(int ab) {
		
		System.out.println("���������������α��Ρ�������������");
		if(ab==0) {
			String sql = "Select count(*) from book_member where id = ?";
			System.out.print("# [I��   D]��  ");
			String id = sc.next();
			boolean ox=loginRead(id,sql);
			if(ox == true) {
				System.out.println("# �α����� ���������� �Ϸ����ϴ�...");
				account = id;
				main_menu();
			}else {
				System.out.println("# ���ԵǾ����� ���� ID�Դϴ�..."); return;
			}
		}
		else if(ab==1) {
			String sql ="Select count(*) from book_manager where id = ?";
			String sql2 = "Select count(*) from book_manager where pw = ?";
				System.out.print("# [I��   D]��  ");
			boolean ox=loginRead(sc.next(),sql);
				System.out.print("# [P��   W]��  ");
			boolean ox2=loginRead(sc.next(),sql2);
			if(ox == false) {
				System.out.println("# ���ԵǾ����� ���� ID�Դϴ�..."); return;
			}
			else if(ox2 == false) {
				System.out.println("# ��й�ȣ�� �߸� �Է��ϼ̽��ϴ�..."); return;
			}
			else {
				System.out.println("# �����ڰ������� �α��� �Ǿ����ϴ�...");manage_menu();
			}
		}
	}
	
	public static boolean loginRead(String id,String sql) { //id�� ���ԵǾ����� Ȯ�� 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","123456");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,id);
			rs = pstmt.executeQuery();
			rs.next(); 
			int ox = rs.getInt(1);
			if(ox != 0) {
				return true;
			}
			
		}catch(Exception e) {System.out.println("�� DB ���ῡ �����߽��ϴ�...");
		System.out.println("�� Error - "+e.getMessage());
	}finally {
		try {
			if(conn != null) conn.close();
		}catch(Exception e) {
		}
		try {
			if(pstmt != null) pstmt.close();
		}catch(Exception e) {
		}
		try {
			if(rs != null) rs.close();
		}catch(Exception e) {
		}
			
		}
		return false;
	}
	
	
	public static void main_menu() {
		
	while(true) {
		System.out.println("��������������������  �޴�����������������");
		System.out.println("���������� ��[1]�� ���硡��������������");
		System.out.println("���������� ��[2]���� ��������������������");
//		System.out.println("������ ������[3]���� ��������������������");
		System.out.println("���������� ��[3]�α׾ƿ� �������� ��   ����");
		System.out.println("������������������������������������������");
		System.out.print("�� �޴� ���� �� ");
		select = sc.nextInt();
		switch(select) {
			case 1: member_info(); break;
			case 2: book_info(); break;
			case 3: System.out.println("# �α׾ƿ� �Ǿ����ϴ�...");return;
			default : System.out.println("# �߸� �Է��ϼ̽��ϴ�..."); break;
					}
		

			}
	}
	
	public static void member_info() {
		Member_manage mem = new Member_manage();
		Book_manage book = new Book_manage();
		Borrow_manage bor = new Borrow_manage();
		while(true) {
			
			System.out.println("���������������� ���硽������������");
			System.out.println("������ ����[1]�������� ����   �������� ����");
			System.out.println("������ ����[2]��û���� ���� ���   �������� ����");
			System.out.println("������ ����[3]�������� ���� ���   �������� ����");
			System.out.println("������ ����[4]���� ��û �̷� ����   �������� ����");
			System.out.println("������ ����[5]���� ���� �̷� ����   �������� ����");
			System.out.println("������ ����[6]���� �ݳ��ϱ�   �������� ����");
			System.out.println("������ ����[7]ȸ�� ���� ����   ���� ������");
			System.out.println("������ ����[8]ȸ�� Ż��   �������� ����");
			System.out.println("������ ����[9]���� �޴�   ����������  ����");
			System.out.println("������������������������������������������");
			System.out.print("�� �޴� ���� �� ");
			select = sc.nextInt();
			switch(select) {	
				case 1:mem.searchData(account); break;
				case 2:book.application_list(account); break;
				case 3:bor.borrow_list(account); break;
				case 4:book.tot_application_list(account); break;
				case 5:bor.tot_borrow_list(account); break;
				case 6:bor.retunr_book(account);; break;
				case 7:mem.updateData(account); break;
				case 8:mem.deleteData(account); break;
				case 9: return;
				default:  System.out.println("# �߸� �Է��ϼ̽��ϴ�..."); break;
						}
					}
		}
	
	public static void book_info() {
		Book_manage book = new Book_manage();
		Borrow_manage bor = new Borrow_manage();
		while(true) {
			
			System.out.println("������������������ ������������������");
			System.out.println("������ ����[1]���� ��û �ϱ� ������     ��");
			System.out.println("������ ����[2]���� ���� ��ȸ��  ������   ��");
			System.out.println("������ ����[3]���� ����  �ϱ� ��  ���������� ��");
			System.out.println("������ ����[4]���� �޴� ��  ���������� ��");
			System.out.println("������������������������������������������");
			System.out.print("�� �޴� ���� �� ");
			select = sc.nextInt();
			switch(select) {
				case 1: book.application(account); break;
				case 2: book.searchOrder(); break;
				case 3: bor.borrow_book(account); break;
				case 4: return;
				default:  System.out.println("# �߸� �Է��ϼ̽��ϴ�..."); break;
						}
					}
	}
	public static void borrow_info() {
		
		while(true) {
			System.out.println("������������������ ���� ������������������");
			System.out.println("�������� ����[1]���� ���� ������ ��������");
			System.out.println("�������� ����[2]���� �ݳ�������    ��������");
			System.out.println("�������� ����[3]���� ���   ����������  ��");
			System.out.println("�������� ����[4]���� �޴�   ����������  ��");
			System.out.println("������������������������������������������");
			System.out.print("�� �޴� ���� �� ");
			select = sc.nextInt();
			switch(select) {
				case 1:
				case 2:
				case 3:
				case 4: return;
				default:  System.out.println("# �߸� �Է��ϼ̽��ϴ�..."); break;
						}
					}
	}

	
	public static void manage_menu() {
		
		
		while(true) {
			System.out.println("��������������������  �޴�����������������");
			System.out.println("���������� ��[1]ȸ�� ��������������������");
			System.out.println("���������� ��[2]���� ��������������������");
			System.out.println("������ ������[3]���� ��������������������");
			System.out.println("���������� ��[4]�α׾ƿ� �������� ��   ����");
			System.out.println("������������������������������������������");
			System.out.print("�� �޴� ���� �� ");
			select = sc.nextInt();
			switch(select) {
				case 1: manage_member(); break;
				case 2: manage_book(); break;
				case 3: manage_borrow(); break;
				case 4: System.out.println("# �α׾ƿ� �Ǿ����ϴ�...");return;
				default : System.out.println("# �߸� �Է��ϼ̽��ϴ�..."); break;
						}
			

				}
		}

	public static void manage_member() {
		Member_manage mem = new Member_manage();
		
		while(true) {
			System.out.println("����������������ȸ��  ��������������������");
			System.out.println("���������� ��[1]ȸ�� ��ϡ���������������");
			System.out.println("���������� ��[2]ȸ�� ��������������������");
			System.out.println("������ ������[3]ȸ�� ��������������������");
			System.out.println("���������� ��[4]�����޴� �������� ��   ����");
			System.out.println("������������������������������������������");
			System.out.print("�� �޴� ���� �� ");
			select = sc.nextInt();
			switch(select) {
				case 1: mem.outputOrder(); break;
				case 2: mem.updateData(); break;
				case 3: mem.deleteData(); break;
				case 4: return;
				default : System.out.println("# �߸� �Է��ϼ̽��ϴ�..."); break;
						}
			

				}
		}
	
	public static void manage_book() {
		Book_manage book = new Book_manage();
		while(true) {
			System.out.println("��������������������  ��������������������");
			System.out.println("���������� ��[1]���� ���� �߰�����������������");
			System.out.println("���������� ��[2]���� ���� ���⡡��������������");
			System.out.println("������ ������[3]���� ���� ��������������������");
			System.out.println("���������� ��[4]���� ���� ������������ ��   ����");
			System.out.println("���������� ��[5]��û ���� ������������ ��   ����");
			System.out.println("���������� ��[6]�����޴��������� ��   ����");
			System.out.println("������������������������������������������");
			System.out.print("�� �޴� ���� �� ");
			select = sc.nextInt();
			switch(select) {
				case 1: book.inputData(); break;
				case 2: book.outputOrder(); break;
				case 3: book.updateData(); break;
				case 4: book.deleteData(); break;
				case 5: application_manage(); break;
				case 6: return;
				default : System.out.println("# �߸� �Է��ϼ̽��ϴ�..."); break;
						}
			

				}
		}

	public static void application_manage() {
		Book_manage book = new Book_manage();
		while(true) {
			System.out.println("������������������û ���� ��������������������");
			System.out.println("���������� ��[1]ó����� ���� ��ϡ���������������");
			System.out.println("���������� ��[2]ó���Ϸ� ���� ��ϡ���������������");
			System.out.println("���������� ��[3]�����޴� �������� ��   ����");
			System.out.println("������������������������������������������");
			System.out.print("�� �޴� ���� �� ");
			select = sc.nextInt();
			switch(select) {
				case 1: book.application_list(); break;
				case 2: book.tot_application_list(); break;
				case 3: return;
				default : System.out.println("# �߸� �Է��ϼ̽��ϴ�..."); break;
						}
			

				}
		
	}
	public static void manage_borrow() {
		Borrow_manage bor = new Borrow_manage();
		while(true) {
			System.out.println("�������������������� ��������������������");
			System.out.println("���������� ��[1]���� ���� ��ϡ���������������");
			System.out.println("���������� ��[2]�ݳ� ���� ��ϡ���������������");
			System.out.println("���������� ��[3]�����޴� �������� ��   ����");
			System.out.println("������������������������������������������");
			System.out.print("�� �޴� ���� �� ");
			select = sc.nextInt();
			switch(select) {
				case 1: bor.borrow_list();; break;
				case 2: bor.tot_return_list(); break;
				case 3: return;
				default : System.out.println("# �߸� �Է��ϼ̽��ϴ�..."); break;
						}
			

				}
	}
}
