import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;

public class Book_manage {
	static Scanner sc = new Scanner(System.in);
	private String claim,title,author,pub,pub_date,loc,stock;
	final static public String driver = "oracle.jdbc.driver.OracleDriver";
	final static public String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	static public String select=null;
	static String str = null;
	static String con = null;
	
	public void inputData()  {
		  
		System.out.println("������������������ ���� �Է¡�������������");
		System.out.print("# [û����ȣ]�� ");
		claim = sc.next();
		System.out.print("# [�� �� ��]�� ");
		title = sc.next();
		System.out.print("# [�� �� ��]�� ");
		author = sc.next();
		
		System.out.print("# [�� �� ��]�� ");
		pub = sc.next();
		while(true) {
			System.out.print("# [�� �� ��]�� ");
			try {
				if(date_format(sc.next())) break;
			}catch(Exception e) {
				System.out.println("# ��¥ ������ �߸� �Է� �Ǿ����ϴ�..(ex:19950307)");
			}
			}
		System.out.print("# [�� �� ��]�� ");
		loc = sc.next();
		System.out.print("# [��  ��]�� ");
		stock = sc.next();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","123456");
			pstmt = conn.prepareStatement("Insert into Book_info values"
					+ " (?, ?, ?, ?, TO_DATE(?,'YYYY-MM-DD'), ?, ?)");
			pstmt.setString(1, claim);
			pstmt.setString(2, title);
			pstmt.setString(3, author);
			pstmt.setString(4, pub);
			pstmt.setString(5, pub_date);
			pstmt.setString(6, loc);
			pstmt.setString(7, stock);
			int n = pstmt.executeUpdate();
			if( n==1 ) 
				System.out.println("�� ���������� �߰��Ǿ����ϴ�... ");
			
			
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
	
	public boolean date_format(String date) {  //��¥ ���� Ȯ�� ����
		Calendar time = new GregorianCalendar(Locale.KOREA);
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		int realYear = time.get(Calendar.YEAR);
		int realMonth = time.get(Calendar.MONTH);
		int realDay = time.get(Calendar.DATE);
		String yyyy = date.substring(0,4);
		String mm = date.substring(4,6);
		
		String dd = date.substring(6,8);
		int int_yyyy = Integer.parseInt(yyyy);
		int int_mm = Integer.parseInt(mm);
		int int_dd = Integer.parseInt(dd);
		cal.set(Calendar.YEAR, int_yyyy);
		cal.set(Calendar.MONTH,int_mm-1);
		cal.set(Calendar.DATE,int_dd);
		
		
		if(date.length() != 8) {
			System.out.println("# ��¥ ������ �߸� �Է� �Ǿ����ϴ�..(ex:19950307)");
			return false;

		}else if(int_mm<1||int_mm>12||int_dd<1||int_dd>31) {
			System.out.println("# ��¥ ������ �߸� �Է� �Ǿ����ϴ�..(ex:19950307)");
			return false;
		}else if(int_yyyy>realYear&&int_mm>realMonth&&int_dd>realDay) {
			System.out.println("# ��¥ ������ �߸� �Է� �Ǿ����ϴ�..(ex:19950307)");
			return false;
		}
//		
		pub_date = fm.format(cal.getTime());
		return true ;
	}
	
	//���� ���
	public void outputOrder(){
		while(true) {
			
			System.out.println("����������������� ���� �˻���������������");
			System.out.println("�������� ����[1]å�̸� ��������   ����  ��");
			System.out.println("�������� ����[2]�������� �˻���    ��������");
			System.out.println("�������� ����[3]��ġ���� �˻���    ��������");
			System.out.println("�������� ����[4]��� ����������    ��������");
			System.out.println("�������� ����[5]���� �޴�   ����������  ��");
			System.out.println("������������������������������������������");
			System.out.print("�� �޴� ���� �� ");
			int select = sc.nextInt(); //�������� select
			switch(select) {
				case 1: 
						str = "å�̸� ��������";
						con ="";
						outputData("Select * FROM"
						+ " (Select ROWNUM rnum, A.* FROM"
						+ " (Select title,publisher,location,inventory_quantity"
						+ " FROM BOOK_INFO"
						+ " order by title asc) A"
						+ " where ROWNUM <= ?+9)"
						+ " where rnum >= ?",con); break;
				case 2: menu_subject(); break;
				case 3: menu_loc();  break;
				case 4:
						str = "��� ��������";
						con ="";
						outputData("Select * FROM"
						+ " (Select ROWNUM rnum, A.* FROM"
						+ " (Select title,publisher,location,inventory_quantity"
						+ " FROM BOOK_INFO"
						+ " order by inventory_quantity desc) A"
						+ " where ROWNUM <= ?+9)"
						+ " where rnum >= ?",con); break;
				case 5: return;
				default:  System.out.println("# �߸� �Է��ϼ̽��ϴ�..."); break;
						}
					}
		
	}
	public void outputData(String sql,String condition) {
		
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
			pstmt2 = conn.prepareStatement("select count(*) from BOOK_INFO "+con);
			rs2 = pstmt2.executeQuery();
			if(rs2.next()) {
			size = rs2.getInt(1);}
			System.out.println("#");
			System.out.println("#'"+str+"'�� ���� �ڷ�˻� ����̸�, �� "+size+"���� �˻��Ǿ����ϴ�.");
			System.out.println("#");
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setString(1,denum+"");
			pstmt.setString(2,denum+"");
			
			rs = pstmt.executeQuery();
			
			System.out.println("������������������������������������ ��� ��¡�������������������������������");
		
			System.out.println("����NO��     [å�̸�] 		  [���ǻ�]	 [��ġ]  [���]");
			while(rs.next()) {
				int no = rs.getInt(1);
				if(no<10)  num = "0"+no;
				else num = no+"";
				title = rs.getString(2);
				pub = rs.getString(3);
				loc = rs.getString(4);
				stock = rs.getString(5);
				System.out.printf("# [%s] %-15s  %-20s %-4s %4s\n",num,title,pub,loc,stock);	
			}
			
			System.out.println("����������������������������������[1]&[2]��������������������������[EXIT]");
			System.out.print("�� ");
			select = sc.next();
			switch(select) {
			case "1" : if(denum==1) {
						
						System.out.println("�� �����Ͱ� ����ֽ��ϴ�..");

							} 
						else denum-=10; break;
			case "2" : if(denum+9<size) denum+=10; 
						else { 
								
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
	
	/*	outputData("Select * FROM"
	+ " (Select ROWNUM rnum, A.* FROM"
	+ " (Select title,publisher,location,inventory_quantity"
	+ " FROM BOOK_INFO"
	+ " order by inventory_quantity desc) A"
	+ " where ROWNUM <= ?+9)"
	+ " where rnum >= ?"); break;*/
	
public void menu_subject() {
	while(true) {
		
		System.out.println("�������������������������� ���� ��ϡ�����������������");
		System.out.println("�������� ����[0] �ѡ��� ��000 ~ 090������  ��  ����");
		System.out.println("�������� ����[1] ö���� ��100 ~ 190������  ��  ����");
		System.out.println("�������� ����[2] ������ ��200 ~ 290������  ��  ����");
		System.out.println("�������� ����[3]��ȸ���С�300 ~ 390������  ��  ����");
		System.out.println("�������� ����[4]�������С�400 ~ 490������  ��  ����");
		System.out.println("�������� ����[5]������С�500 ~ 590������  ��  ����");
		System.out.println("�������� ����[6] ������ ��600 ~ 690������  ��  ����");
		System.out.println("�������� ����[7] �𡡾� ��700 ~ 790������  ��  ����");
		System.out.println("�������� ����[8] ������ ��800 ~ 890������  ��  ����");
		System.out.println("�������� ����[9] ������ ��900 ~ 990������  ��  ����");
		System.out.println("�������� ����[10]���� �޴�   ��������������  ��  �� ��");
		System.out.println("������������������������������������������������������");
		System.out.print("�� �޴� ���� �� ");
		int select = sc.nextInt(); //�������� select
		switch(select) {
			case 0:
				 	str = "�ѷ� ����";
				 	con = " where claim_symbol LIKE '0%'";
				 	outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where claim_symbol LIKE '0%'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?",con); break;
			case 1: 
					str = "ö�� ����";
					con = " where claim_symbol LIKE '1%'";
					outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where claim_symbol LIKE '1%'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?",con); break;
			case 2:  
					str = "���� ����";
					con = " where claim_symbol LIKE '2%'";
					outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where claim_symbol LIKE '2%'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?",con); break;
			case 3:  
					str = "��ȸ���� ����";
					con = " where claim_symbol LIKE '3%'";
					outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where claim_symbol LIKE '3%'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?",con); break;
			case 4:
				 	str = "������� ����";
				 	con = " where claim_symbol LIKE '4%'";
					outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where claim_symbol LIKE '4%'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?",con);break;
			case 5: 
					str = "������� ����";
					con = " where claim_symbol LIKE '5%'";
					outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where claim_symbol LIKE '5%'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?",con); break;
			case 6:
					str = "���� ����";
					con = " where claim_symbol LIKE '6%'";
					outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where claim_symbol LIKE '6%'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?",con); break;
			case 7: 
					str = "��� ����";
					con = " where claim_symbol LIKE '7%'";
					outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where claim_symbol LIKE '7%'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?",con); break;
			case 8: 
					str = "���� ����";
					con = " where claim_symbol LIKE '8%'";
					outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where claim_symbol LIKE '8%'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?",con);break;
			case 9: 
					str = "���� ����";
					con = " where claim_symbol LIKE '9%'";
					outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where claim_symbol LIKE '9%'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?",con);break;
			case 10: return;
			default:  System.out.println("# �߸� �Է��ϼ̽��ϴ�..."); break;
					}
				}
}
public void menu_loc() {
	while(true) {
		
		System.out.println("����������������ġ�� ���� ��ϡ�����������");
		System.out.println("�������� ����[0]����ڷ�ǡ�����     ������");	
		System.out.println("�������� ����[1]�� 1 �ڷ�ǡ�����     ������");
		System.out.println("�������� ����[2]�� 2 �ڷ�ǡ�    ������ ����");
		System.out.println("�������� ����[3]���� �޴�   ����������  ��");
		System.out.println("������������������������������������������");
		System.out.print("�� �޴� ���� �� ");
		int select = sc.nextInt(); //�������� select
		switch(select) {
			case 0: 
					str = "����ڷ�� ����";
					con = " where location = '����ڷ��'";
					outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where location = '����ڷ��'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?",con);break;
			case 1: 
				str = "��1�ڷ�� ����";
				con = " where location = '��1�ڷ��'";
				outputData("Select * FROM"
				+ " (Select ROWNUM rnum, A.* FROM"
				+ " (Select title,publisher,location,inventory_quantity"
				+ " FROM BOOK_INFO"
				+ " where location = '��1�ڷ��'"
				+ " order by title asc) A"
				+ " where ROWNUM <= ?+9)"
				+ " where rnum >= ?",con);break;
			case 2: 
					str = "��2�ڷ�� ����";
					con = " where location = '��2�ڷ��'";
					outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where location = '��2�ڷ��'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?",con); break;
			case 3: return;
			default:  System.out.println("# �߸� �Է��ϼ̽��ϴ�..."); break;
					}
				}
}
	public void searchOrder() {
		while(true) {
			
			System.out.println("������������������ ��� �˻���������������");
			System.out.println("�������� ����[1]��� ���� �˻�  ��    ��   ��");
			System.out.println("�������� ����[2]å �������� �˻�  ����    ��");
			System.out.println("�������� ����[3]���� �޴�   ����������  ��");
			System.out.println("������������������������������������������");
			System.out.print("�� �޴� ���� �� ");
			int select = sc.nextInt(); //�������� select
			switch(select) {
			case 1: outputOrder();break;
				case 2: searchData("Select * FROM"
						+ " (Select ROWNUM rnum, A.* FROM"
						+ " (Select * FROM BOOK_INFO"
						+ " where title like ?"
						+ " order by title asc) A)"
						+ " where rnum = ?"); break;
				case 3: return;
				default:  System.out.println("# �߸� �Է��ϼ̽��ϴ�..."); break;
						}
					}
	}

	public void searchData(String col) {

			Connection conn = null;
			PreparedStatement pstmt = null;
			PreparedStatement pstmt2 = null;
			ResultSet rs = null;
			ResultSet rs2 = null;
			int denum = 1;
			int size = 0;
			System.out.print("�� å ���� �Է� : ");
			title = sc.next();
			while(true) {
			try {
				Class.forName(driver);
				conn = DriverManager.getConnection(url,"scott","123456");
				pstmt = conn.prepareStatement(col);
	
					
					pstmt2 = conn.prepareStatement("select count(*) from Book_info where title like ?");
					pstmt.setString(1, "%"+title+"%");
					pstmt.setString(2, denum+"");
					pstmt2.setString(1,"%"+title+"%");
			
				
				rs2 = pstmt2.executeQuery();
				rs = pstmt.executeQuery();
				
				if(rs2.next()) {
					size = rs2.getInt(1);}
				System.out.println("#");
				System.out.println("#å���� '"+title+"'�� ���� �ڷ�˻� ����̸�, �� "+size+"���� �˻��Ǿ����ϴ�.");
				System.out.println("#");
					while(rs.next()) {
						System.out.println("������������������ �� ��ȸ��������������");
						claim = rs.getString(2);
						String book_title = rs.getString(3);
						author = rs.getString(4);
						pub= rs.getString(5);
						pub_date = rs.getString(6);
						loc= rs.getString(7);
						stock= rs.getString(8);
						System.out.println("# [û����ȣ]�� " +claim);
						System.out.println("# [å  ���� ]�� " +book_title);
						System.out.println("# [ ��  ��  ]�� " +author);
						System.out.println("# [�� �� �� ]�� " +pub);
						System.out.println("# [�� �� �� ]�� " +pub_date.substring(0,10));
						System.out.println("# [ ��  ġ  ]�� " +loc);
						System.out.println("# [ ��  ��  ]�� " +stock);
					}
						System.out.println("��������������[1]&[2]����������[EXIT]");
						System.out.print("�� ");
						select = sc.next();
						switch(select) {
						case "1" : if(denum==1) {
									
									System.out.println("�� �����Ͱ� ����ֽ��ϴ�..");
										} 
									else denum-=1; break;
						case "2" : if(denum<size) denum++; 
									else { 
											
											System.out.println("�� �����Ͱ� ����ֽ��ϴ�..");
									}
											break;	
						case "EXIT" :
						case "exit" : return;
						default : System.out.println("�� �ٽ� �Է����ּ���...");
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
				if(pstmt2 != null) pstmt2.close();
			}catch(Exception e) {
			}
			try {
				if(rs != null) rs.close();
				if(rs2 != null)rs2.close();
			}catch(Exception e) {
			}	
			}
			}
	}
	public void updateData() {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			
			System.out.println("������������������ ���� ������������������");
			System.out.print("# [������ ������ û����ȣ]��  ");
			claim = sc.next();
			System.out.print("# [����ġ]�� ");
			loc = sc.next();
			System.out.print("# [�硡��]�� ");
			stock = sc.next();
			System.out.println("������������������������������������������");
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","123456");
			pstmt = conn.prepareStatement("Update BOOK_INFO "
										+ " Set location = ?, inventory_quantity = ?"
										+ " Where claim_symbol = ?");
			pstmt.setString(1, loc);
			pstmt.setString(2, stock);
			pstmt.setString(3, claim);
			int n = pstmt.executeUpdate();
			if( n==1 ) 
				System.out.println("�� ���������� �����Ǿ����ϴ�... ");
			else
				System.out.println("�� �Է��Ͻ� û����ȣ�� ���� �����Դϴ�... ");
			
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
			System.out.print("# [������ ������ û����ȣ]��  ");
			claim = sc.next();
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","123456");
			pstmt = conn.prepareStatement("Delete From BOOK_INFO Where claim_symbol = ?");
			pstmt.setString(1, claim);
			int n = pstmt.executeUpdate();
			if( n==1 )
				System.out.println("�� ���������� �����Ǿ����ϴ�... ");
			else
				System.out.println("�� �Է��Ͻ� û����ȣ�� ����  �����Դϴ�... ");
			
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
	public void application(String account) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		System.out.println("����������������û ���� ���� �Է¡�������������");
		System.out.print("# [�� �� ��]�� ");
		title = sc.next();
		System.out.print("# [�� �� ��]�� ");
		pub = sc.next();
		
		try {	
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","123456");
			pstmt = conn.prepareStatement("Insert into book_application(id,book_title,publisher)"
					+ " values( ?,?,?)");
			pstmt.setString(1, account);
			pstmt.setString(2, title);
			pstmt.setString(3, pub);
			
			int n = pstmt.executeUpdate();
			if(n==1)
				System.out.println("�� ���� ��û�� �Ϸ�Ǿ����ϴ�... ");
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
	public static void application_list(String account) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","123456");
			pstmt = conn.prepareStatement("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select book_title, publisher, application_date, application_state"
					+ " FROM BOOK_application"
					+ " where id = ?"
					+ " order by application_date asc) A"
					+ ")"
					);//��������Ȯ����
			
			pstmt.setString(1,account);
			rs = pstmt.executeQuery();
			System.out.println("������������������������������ ��û ��ϡ�������������������������");
			System.out.println("����NO��[å�̸�]  \t[���ǻ�]  [��û��¥]  [�������]");
			while(rs.next()) {
				String no = rs.getString(1);
				String title = rs.getString(2);
				String pub = rs.getString(3);
				String app_date = rs.getString(4);
				String app_state = rs.getString(5);
				System.out.printf("# [%s] %-10s  %-6s %-6s %-6s\n",no,title,pub,app_date.substring(0,10),app_state);
				
			}
			System.out.println("������������������������������������������������������������������");
			System.out.print("# �� �ڷΰ����� �ƹ�Ű�� �Է��Ͻÿ� ");
			select = sc.next();
			switch(select) {
			default : return;
			}

			
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
			try {
				if(rs != null) rs.close();
			}catch(Exception e) {
				
			}
		}	
	}
	
	public static void application_list() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null; 
		ResultSet rs = null;
		ResultSet rs2 = null;
		int denum = 1;
		int size = 0;
		
		
		
		
		while(true) {
			
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","123456");
			pstmt2 = conn.prepareStatement("select count(*) from BOOK_application");
			rs2 = pstmt2.executeQuery();
			if(rs2.next()) {
			size = rs2.getInt(1);}
			System.out.println("#");
			System.out.println("#'ó����� �������'�� ���� �ڷ�˻� ����̸�, �� "+size+"���� �˻��Ǿ����ϴ�.");
			System.out.println("#");
			pstmt = conn.prepareStatement("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select id,book_title, publisher, application_date, application_state"
					+ " FROM BOOK_application"
					+ " order by application_date asc) A"
					+ "	where ROWNUM <= ?+9)"
					+ " where rnum >=?");
						
//			"Select * FROM"
//			+ " (Select ROWNUM rnum, A.* FROM"
//			+ " (Select title,publisher,location,inventory_quantity"
//			+ " FROM BOOK_INFO"
//			+ " order by inventory_quantity desc) A"
//			+ " where ROWNUM <= ?+9)"
//			+ " where rnum >= ?");
			
			
			pstmt.setString(1,denum+"");
			pstmt.setString(2,denum+"");
			
			rs = pstmt.executeQuery();
			
			System.out.println("������������������������ ��û ��ϡ�������������������");
			System.out.println("����NO�� [ID]    [å�̸�]  [���ǻ�]  [��û��¥]  [�������]");
			while(rs.next()) {
				String no = rs.getString(1);
				String id = rs.getString(2);
				String title = rs.getString(3);
				String pub = rs.getString(4);
				String app_date = rs.getString(5);
				String app_state = rs.getString(6);
				System.out.printf("# [%s] %6s %6s %6s %6s %6s\n",no,id,title,pub,app_date.substring(0,10),app_state);
			}
			
			System.out.println("����������������������[1]&[2]��������������[EXIT]");
			System.out.print("�� ");
			select = sc.next();
			switch(select) {
			case "1" : if(denum==1) {
						
						System.out.println("�� �����Ͱ� ����ֽ��ϴ�..");

							} 
						else denum-=10; break;
			case "2" : if(denum+9<size) denum+=10; 
						else { 
								
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
	
	public static void tot_application_list(String account) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","123456");
			pstmt = conn.prepareStatement("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select book_title,publisher,application_date,application_state"
					+ " FROM backup_application"
					+ " where id= ?"
					+ " order by no asc) A"
					+ ")"
					);//��������Ȯ����
			
			pstmt.setString(1,account);
			rs = pstmt.executeQuery();
			System.out.println("����������������������û �̷� ��¡�������������������");
			System.out.println("����NO��     [å�̸�]  [���ǻ�]  [������¥]  [��������]");
			while(rs.next()) {
				String no = rs.getString(1);
				String title = rs.getString(2);
				String pub = rs.getString(3);
				String app_date = rs.getString(4);
				String app_state = rs.getString(5);
				System.out.printf("# [%s] %6s  %6s %8s %8s\n",no,title,pub,app_date.substring(0,10),app_state);
			
			}
			System.out.println("������������������������������������������");
			System.out.print("# �� �ڷΰ����� �ƹ�Ű�� �Է��Ͻÿ� ");
			select = sc.next();
			switch(select) {
			default : return;
			}

			
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
			try {
				if(rs != null) rs.close();
			}catch(Exception e) {
				
			}
		}
		
	}

	public static void tot_application_list() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null; 
		ResultSet rs = null;
		ResultSet rs2 = null;
		int denum = 1;
		int size = 0;
		
		
		
		
		while(true) {
			
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","123456");
			pstmt2 = conn.prepareStatement("select count(*) from backup_application");
			rs2 = pstmt2.executeQuery();
			if(rs2.next()) {
			size = rs2.getInt(1);}
			System.out.println("#");
			System.out.println("#'ó���Ϸ�'�� ��û������ ���� �ڷ�˻� ����̸�, �� "+size+"���� �˻��Ǿ����ϴ�.");
			System.out.println("#");
			pstmt = conn.prepareStatement("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select id,book_title, publisher, application_date, application_state"
					+ " FROM backup_application"
					+ " order by application_date asc) A"
					+ "	where ROWNUM <= ?+9)"
					+ " where rnum >=?");
						
				//			"Select * FROM"
				//+ " (Select ROWNUM rnum, A.* FROM"
				//+ " (Select book_title,publisher,application_date,application_state"
				//+ " FROM backup_application"
				//+ " where id= ?"
				//+ " order by no asc) A"
				//+ ")"
				//);
			
			pstmt.setString(1,denum+"");
			pstmt.setString(2,denum+"");
			
			rs = pstmt.executeQuery();
			
			System.out.println("����������������������û �̷� ��¡�������������������");
			System.out.println("����NO�� [id]   [å�̸�]  [���ǻ�]  [������¥]  [��������]");
			while(rs.next()) {
				String no = rs.getString(1);
				String id = rs.getString(2);
				String title = rs.getString(3);
				String pub = rs.getString(4);
				String app_date = rs.getString(5);
				String app_state = rs.getString(6);
				System.out.printf("# [%s] %6s %6s %6s %8s %8s\n",no,id,title,pub,app_date.substring(0,10),app_state);
			
			}
			
			System.out.println("����������������������[1]&[2]��������������[EXIT]");
			System.out.print("�� ");
			select = sc.next();
			switch(select) {
			case "1" : if(denum==1) {
						
						System.out.println("�� �����Ͱ� ����ֽ��ϴ�..");

							} 
						else denum-=10; break;
			case "2" : if(denum+9<size) denum+=10; 
						else { 
								
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
	

}
