import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Member_manage {
	static Scanner sc = new Scanner(System.in);
	private String id,mb_date,name,phone,email,addr;
	final static public String driver = "oracle.jdbc.driver.OracleDriver";
	final static public String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	static public String select=null;
	static String str = null;
	
	public void inputData() {
		System.out.println();
		System.out.println("��������������ȸ�� ���� �Է¡�������������");
		System.out.print("# [I��   D]��  ");
		id = sc.next();
		System.out.print("# [�̡���]�� ");
		name = sc.next();
		System.out.print("# [����ȣ]�� ");//1.����ȣ ��ľ˰��� �ۼ�
		phone = sc.next();
		System.out.print("# [�̸���]�� ");	
		email = sc.next();
		System.out.print("# [�ּ���]�� ");
		addr = sc.next();
		
	
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try { 
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","123456");//*
			pstmt = conn.prepareStatement("INSERT INTO BOOK_MEMBER(id,name,phone,email,addr)"
					+ " VALUES(?,?,?,?,?)");
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, phone);
			pstmt.setString(4, email);
			pstmt.setString(5, addr);
			int n = pstmt.executeUpdate();
			if(n == 1) 
				System.out.println("�� ���������� �߰��Ǿ����ϴ�... ");
			else 
				System.out.println("�� ID�� �ߺ��Ǿ����ϴ�..."); //�����ؾ���
			
		}catch(Exception e) {
			System.out.println("�� DB ���ῡ �����߽��ϴ�...");
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
		}
	}
	
	public void outputOrder(){
		while(true) {
			System.out.println();
			System.out.println("��������������ȸ�� ��� �˻���������������");
			System.out.println("�������� ����[1]ID �������� ��  ��������");
			System.out.println("�������� ����[2]�̸� ����������    ��������");
			System.out.println("�������� ����[3]�� �˻���    ��������");
			System.out.println("�������� ����[4]���� �޴�   ����������  ��");
			System.out.println("������������������������������������������");
			System.out.print("�� �޴� ���� �� ");
			int select = sc.nextInt(); //�������� select
			switch(select) {
				case 1: outputData("Select * FROM"
						+ " (Select ROWNUM rnum, A.* FROM"
						+ " (Select ID,NAME FROM BOOK_MEMBER"
						+ " order by id asc) A"
						+ " where ROWNUM <= ?+9)"
						+ " where rnum >= ?"); str="ID ��������"; break;
				case 2: outputData("Select * FROM"
						+ " (Select ROWNUM rnum, A.* FROM"
						+ " (Select ID,NAME FROM BOOK_MEMBER"
						+ " order by name asc) A"
						+ " where ROWNUM <= ?+9)"
						+ " where rnum >= ?");str="�̸� ��������"; break;
				case 3: search_menu(); break;
				case 4: return;
				default:  System.out.println("# �߸� �Է��ϼ̽��ϴ�..."); break;
						}
					}
	}
	
	public static void search_menu() {
		
		
		while(true) {
			System.out.println("������������������  �˻�����������������");
			System.out.println("���������� ��[1]ID �˻�����������������");
			System.out.println("���������� ��[2]�̸� �˻�����������������");
			System.out.println("���������� ��[3]����ȣ �˻�����������������");
			System.out.println("���������� ��[4]�̸��� �˻�����������������");
			System.out.println("������ ������[5]�ּ� �˻�����������������");
			System.out.println("���������� ��[6]�����޴� �������� ��   ����");
			System.out.println("������������������������������������������");
			System.out.print("�� �޴� ���� �� ");
			int select = sc.nextInt();
			switch(select) {
				case 1: searchData(1); break;
				case 2: searchData(2); break;
				case 3: searchData(3); break;
				case 4: searchData(4); break;
				case 5: searchData(5); break;
				case 6: return;
				default : System.out.println("# �߸� �Է��ϼ̽��ϴ�..."); break;
						}
			

				}
		}
	public void outputData(String col) {
		System.out.println();
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null; 
		ResultSet rs = null;
		ResultSet rs2 = null;
		int denum = 1;
		int size = 0;
		String num = null;
		
		
		
		while(true) {
			
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","123456");
			pstmt2 = conn.prepareStatement("select count(*) from BOOK_MEMBER");
			rs2 = pstmt2.executeQuery();
			if(rs2.next()) {
			size = rs2.getInt(1);}
			System.out.println("#");
			System.out.println("#'"+str+"'�� ���� �ڷ�˻� ����̸�, �� "+size+"���� �˻��Ǿ����ϴ�.");
			System.out.println("#");

			pstmt = conn.prepareStatement(col);
			
			
			pstmt.setString(1,denum+"");
			pstmt.setString(2,denum+"");
			
			rs = pstmt.executeQuery();
			
			System.out.println("��������������ȸ�� ��� ��¡�������������");
			System.out.println("����NO��     [ID]      [�̸�]    ");
			while(rs.next()) {
				int no = rs.getInt(1);
				if(no<10)  num = "0"+no;
				else num = no+"";
				id = rs.getString(2);
				name = rs.getString(3);
				System.out.printf("# [%s] %6s  %6s\n",num,id,name);	
			}
			
			System.out.println("��������������[1]&[2]����������[EXIT]");
			System.out.print("�� ");
			select = sc.next();
			switch(select) {
			case "1" : if(denum==1) {
						System.out.println();
						System.out.println("�� �����Ͱ� ����ֽ��ϴ�..");

							} 
						else denum-=10; break;
			case "2" : if(denum+9<size) denum+=10; 
						else { 
								System.out.println();
								System.out.println("�� �����Ͱ� ����ֽ��ϴ�..");
						}
								break;	
			case "EXIT" : return;
			case "exit" : return;
			default : System.out.println("�� �ٽ� �Է����ּ���...");
			}
			
		}catch(Exception e) {System.out.println("�� DB ���ῡ �����߽��ϴ�...");
		System.out.println("�� Error - "+e.getMessage());
		return;
	}finally {
		try {
			if(conn != null) conn.close();
		}catch(Exception e) {
		}
		try {
			if(pstmt != null) pstmt.close();
			if(pstmt2 != null) pstmt2.close();
		}catch(Exception e) {
		}
		try {
			if(rs != null) rs.close();
			if(rs2 != null) rs2.close();
		}catch(Exception e) {
		}
			
		}
		
	}//while��	
	}
	
	public void searchData(String account) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			id = account;
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","123456");
			pstmt = conn.prepareStatement("Select * From BOOK_MEMBER Where ID = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			System.out.println("��������������ȸ�� ��� ��¡�������������");
			
			while(rs.next()) {
				id = rs.getString(1);
				mb_date = rs.getString(2);
				name = rs.getString(3);
				phone = rs.getString(4);
				email = rs.getString(5);
				addr = rs.getString(6);
				
				System.out.println("# [I��   D]�� " +id);
				System.out.println("# [������]�� " +mb_date.substring(0,10));
				System.out.println("# [�̡���]�� " +name);
				System.out.println("# [����ȣ]�� " +phone);
				System.out.println("# [�̸���]�� " +email);
				System.out.println("# [�ּ���]�� " +addr);
				System.out.println("������������������������������������������");
			}
				System.out.print("# �� �ڷΰ����� �ƹ�Ű�� �Է��Ͻÿ� ");
				select = sc.next();
				switch(select) {
				default : return;
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
	}
	public static void searchData(int select) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql= null;
		String column = null;
		switch(select) {
		case 1: sql = "Select * From BOOK_MEMBER Where ID = ?"; 
						System.out.print("# ��ȸ�� ID �Է� �� ");
						column=sc.next();break;
		case 2: sql = "Select * From BOOK_MEMBER Where name = ?";
						System.out.print("# ��ȸ�� �̸� �Է� �� ");
						column=sc.next();break;
		case 3: sql = "Select * From BOOK_MEMBER Where phone = ?";
						System.out.print("# ��ȸ�� ����ȣ �Է� �� ");
						column=sc.next();break;
		case 4: sql = "Select * From BOOK_MEMBER Where email = ?"; 
						System.out.print("# ��ȸ�� �̸��� �Է� �� ");
						column=sc.next();break;
		case 5: sql = "Select * From BOOK_MEMBER Where addr = ?"; 
						System.out.print("# ��ȸ�� �ּ� �Է� �� ");
						column=sc.next();break;
		}
		try {
			
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","123456");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, column);
			rs = pstmt.executeQuery();
			System.out.println("��������������ȸ�� ��� ��¡�������������");
			
			while(rs.next()) {
				String id = rs.getString(1);
				String mb_date = rs.getString(2);
				String name = rs.getString(3);
				String phone = rs.getString(4);
				String email = rs.getString(5);
				String addr = rs.getString(6);
				
				System.out.println("# [I��   D]�� " +id);
				System.out.println("# [������]�� " +mb_date.substring(0,10));
				System.out.println("# [�̡���]�� " +name);
				System.out.println("# [����ȣ]�� " +phone);
				System.out.println("# [�̸���]�� " +email);
				System.out.println("# [�ּ���]�� " +addr);
				System.out.println("������������������������������������������");
			}
				System.out.print("# �� �ڷΰ����� �ƹ�Ű�� �Է��Ͻÿ� ");
				String sel = sc.next();
				switch(sel) {
				default : return;
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
	}
	public void updateData(String account) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			System.out.println("��������������ȸ�� ���� ������������������");
			id = account;
			System.out.print("# [����ȣ]�� ");//1.����ȣ ��ľ˰��� �ۼ�
			phone = sc.next();
			System.out.print("# [�̸���]�� ");
			email = sc.next();
			System.out.print("# [�ּ���]�� ");
			addr = sc.next();
			System.out.println("������������������������������������������");
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","123456");
			pstmt = conn.prepareStatement("Update BOOK_MEMBER "
										+ " Set phone = ?, email = ?, addr = ?"
										+ " Where ID = ?");
			pstmt.setString(1, phone);
			pstmt.setString(2, email);
			pstmt.setString(3, addr);
			pstmt.setString(4, id);
			int n = pstmt.executeUpdate();
			if( n==1 ) 
				System.out.println("�� ���������� �����Ǿ����ϴ�... ");
			else
				System.out.println("�� �Է��Ͻ� ID�� ���� ID�Դϴ�... ");
			
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
			
		}
	}
	
	public void updateData() {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			System.out.println();
			System.out.println("��������������ȸ�� ���� ������������������");
			System.out.print("# [������ ȸ���� ID]��  ");
			id = sc.next();
			System.out.print("# [�̡���]�� ");
			name = sc.next();
			System.out.print("# [����ȣ]�� ");
			phone = sc.next();
			System.out.print("# [�̸���]�� ");
			email = sc.next();
			System.out.print("# [�ּ���]�� ");
			addr = sc.next();
			System.out.println("������������������������������������������");
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","123456");
			pstmt = conn.prepareStatement("Update BOOK_MEMBER "
										+ " Set name = ?,phone = ?, email = ?, addr = ?"
										+ " Where ID = ?");
			pstmt.setString(1, name);
			pstmt.setString(2, phone);
			pstmt.setString(3, email);
			pstmt.setString(4, addr);
			pstmt.setString(5, id);
			int n = pstmt.executeUpdate();
			if( n==1 ) 
				System.out.println("�� ���������� �����Ǿ����ϴ�... ");
			else
				System.out.println("�� �Է��Ͻ� ID�� ���� ID�Դϴ�... ");
			
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
			
		}
	}
	public void deleteData(String account) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			while(true) {
			System.out.print("�� ���� ���� �Ͻðڽ��ϱ�..? [Y/N]");
			select = sc.next();
			if(select.equalsIgnoreCase("y")) {
				break;
			}
			else if(select.equalsIgnoreCase("n")) {
				return;
			}
			else {
				System.out.println("# �߸� �Է��ϼ̽��ϴ�..");
			}
			}
			id = account;
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","123456");
			pstmt = conn.prepareStatement("Delete From BOOK_MEMBER Where ID = ?");
			pstmt.setString(1, id);
			int n = pstmt.executeUpdate();
			if( n==1 )
				System.out.println("�� ���������� �����Ǿ����ϴ�... ");
			else
				System.out.println("�� �Է��Ͻ� ID�� ���� ID�Դϴ�... ");
			
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
			
		}
		
	}
	
	public void deleteData() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		System.out.println("��������������ȸ�� ���� ������������������");
		System.out.print("# [������ ȸ���� ID]��  ");
		id = sc.next();
		
		try {
			
			
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","123456");
			pstmt = conn.prepareStatement("Delete From BOOK_MEMBER Where ID = ?");
			pstmt.setString(1, id);
			int n = pstmt.executeUpdate();
			if( n==1 )
				System.out.println("�� ���������� �����Ǿ����ϴ�... ");
			else
				System.out.println("�� �Է��Ͻ� ID�� ���� ID�Դϴ�... ");
			
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
			
		}
		
	}
}