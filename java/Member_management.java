import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Member_management {
	Scanner sc = new Scanner(System.in);
	private String id,mb_date,name,phone,email,addr;
	static public String driver = "oracle.jdbc.driver.OracleDriver";
	static public String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	static public String select=null;
	
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
				System.out.println("�� ID�� �ߺ��Ǿ����ϴ�...");
			
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
	public void outputData() {
		System.out.println();
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null; 
		ResultSet rst = null;
		ResultSet rst2 = null;
		int denum = 1;
		int size = 0;
		String num = null;
		while(true) {
			
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","123456");
			pstmt2 = conn.prepareStatement("select count(*) from BOOK_MEMBER");
			rst2 = pstmt2.executeQuery();
			if(rst2.next()) {
			size = rst2.getInt(1);}

			pstmt = conn.prepareStatement("Select X.* FROM"
					+ " (Select ROWNUM rnum, A.ID,A.NAME FROM BOOK_MEMBER A"
					+ " order by A.NAME asc) X"
					+ " where rnum between ? and ?+9"
					+ " order by rnum asc");
			pstmt.setString(1,denum+"");
			pstmt.setString(2,denum+"");
			rst = pstmt.executeQuery();
			
			System.out.println("��������������ȸ�� ��� ��¡�������������");
			System.out.println("����NO��     [ID]      [NAME]    ");
			while(rst.next()) {
				int no = rst.getInt(1);
				if(no<10)  num = "0"+no;
				else num = no+"";
				id = rst.getString(2);
				name = rst.getString(3);
				System.out.printf("# [%s] %6s  %6s\n",num,id,name);	
			}
			System.out.println("������������������������������������������");
			System.out.println("�� ��[1] & [2]��           ����[EXIT]��");
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
			if(rst != null) rst.close();
			if(rst2 != null) rst2.close();
		}catch(Exception e) {
		}
			
		}
		
	}//while��	
	}
	
	public void searchData() {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		
		try {
			System.out.print("�� ID �Է� : ");
			id = sc.next();
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","123456");
			pstmt = conn.prepareStatement("Select * From BOOK_MEMBER Where ID = ?");
			pstmt.setString(1, id);
			rst = pstmt.executeQuery();
			System.out.println("��������������ȸ�� ��� ��¡�������������");
			
			while(rst.next()) {
				id = rst.getString(1);
				mb_date = rst.getString(2);
				name = rst.getString(3);
				phone = rst.getString(4);
				email = rst.getString(5);
				addr = rst.getString(6);
				
				System.out.println("# [I��   D]�� " +id);
				System.out.println("# [������]�� " +mb_date);
				System.out.println("# [�̡���]�� " +name);
				System.out.println("# [����ȣ]�� " +phone);
				System.out.println("# [�̸���]�� " +email);
				System.out.println("# [�ּ���]�� " +addr);
				System.out.println("������������������������������������������");
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
			if(rst != null) rst.close();
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
			System.out.print("# [I��   D]��  ");
			id = sc.next();
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
	public void deleteData() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			System.out.println("��������������ȸ�� ���� ������������������");
			System.out.print("# [I��   D]��  ");
			id = sc.next();
			
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