import java.sql.Connection;	
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;

public class Book_manage {
	Scanner sc = new Scanner(System.in);
	private String claim,title,author,pub,pub_date,loc,stock;
	static public String driver = "oracle.jdbc.driver.OracleDriver";
	static public String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	static public String select=null;
	
	
	public void inputData()  {
		System.out.println();
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
		int realYear = time.get(Calendar.YEAR);
		int realMonth = time.get(Calendar.MONTH);
		int realDay = time.get(Calendar.DATE);
		String yyyy = date.substring(0,4);
		String mm = date.substring(4,6);
		String dd = date.substring(6,8);
		int int_yyyy = Integer.parseInt(yyyy);
		int int_mm = Integer.parseInt(mm);
		int int_dd = Integer.parseInt(dd);
		
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
		pub_date = yyyy+"-"+mm+"-"+dd;
		return true ;
	}
	
	//���� ���
	public void outputOrder(){
		while(true) {
			System.out.println();
			System.out.println("������������������ ��� �˻���������������");
			System.out.println("�������� ����[1]å�̸� ��������   ����  ��");
			System.out.println("�������� ����[2]�������� �˻���    ��������");
			System.out.println("�������� ����[3]��ġ���� �˻���    ��������");
			System.out.println("�������� ����[4]��� ����������    ��������");
			System.out.println("�������� ����[5]���� �޴�   ����������  ��");
			System.out.println("������������������������������������������");
			System.out.print("�� �޴� ���� �� ");
			int select = sc.nextInt(); //�������� select
			switch(select) {
				case 1: outputData("Select * FROM"
						+ " (Select ROWNUM rnum, A.* FROM"
						+ " (Select title,publisher,location,inventory_quantity"
						+ " FROM BOOK_INFO"
						+ " order by title asc) A"
						+ " where ROWNUM <= ?+9)"
						+ " where rnum >= ?"); break;
				case 2: menu_subject(); break;
				case 3: menu_loc();  break;
				case 4: outputData("Select * FROM"
						+ " (Select ROWNUM rnum, A.* FROM"
						+ " (Select title,publisher,location,inventory_quantity"
						+ " FROM BOOK_INFO"
						+ " order by inventory_quantity desc) A"
						+ " where ROWNUM <= ?+9)"
						+ " where rnum >= ?"); break;
				case 5: return;
				default:  System.out.println("# �߸� �Է��ϼ̽��ϴ�..."); break;
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
			pstmt2 = conn.prepareStatement("select count(*) from BOOK_INFO");
			rs2 = pstmt2.executeQuery();
			if(rs2.next()) {
			size = rs2.getInt(1);}

			pstmt = conn.prepareStatement(col);
			
			
			pstmt.setString(1,denum+"");
			pstmt.setString(2,denum+"");
			
			rs = pstmt.executeQuery();
			
			System.out.println("������������������������ ��� ��¡�������������������");
			System.out.println("����NO��     [å�̸�]  [���ǻ�]  [��ġ]  [���]");
			while(rs.next()) {
				int no = rs.getInt(1);
				if(no<10)  num = "0"+no;
				else num = no+"";
				title = rs.getString(2);
				pub = rs.getString(3);
				loc = rs.getString(4);
				stock = rs.getString(5);
				System.out.printf("# [%s] %6s  %6s %10s %4s\n",num,title,pub,loc,stock);	
			}
			
			System.out.println("����������������������[1]&[2]��������������[EXIT]");
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
	
	/*	outputData("Select * FROM"
	+ " (Select ROWNUM rnum, A.* FROM"
	+ " (Select title,publisher,location,inventory_quantity"
	+ " FROM BOOK_INFO"
	+ " order by inventory_quantity desc) A"
	+ " where ROWNUM <= ?+9)"
	+ " where rnum >= ?"); break;*/
	
public void menu_subject() {
	while(true) {
		System.out.println();
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
			case 0: outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where claim_symbol LIKE '0%'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?"); break;
			case 1: outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where claim_symbol LIKE '1%'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?"); break;
			case 2:  outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where claim_symbol LIKE '2%'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?"); break;
			case 3:  outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where claim_symbol LIKE '3%'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?"); break;
			case 4:  outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where claim_symbol LIKE '4%'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?"); break;
			case 5:  outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where claim_symbol LIKE '5%'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?"); break;
			case 6:  outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where claim_symbol LIKE '6%'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?"); break;
			case 7:  outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where claim_symbol LIKE '7%'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?"); break;
			case 8:  outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where claim_symbol LIKE '8%'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?"); break;
			case 9:  outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where claim_symbol LIKE '9%'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?"); break;
			case 10: return;
			default:  System.out.println("# �߸� �Է��ϼ̽��ϴ�..."); break;
					}
				}
}
public void menu_loc() {
	while(true) {
		System.out.println();
		System.out.println("����������������ġ�� ���� ��ϡ�����������");
		System.out.println("�������� ����[1]��1 �ڷ�ǡ�����     ������");
		System.out.println("�������� ����[2]��2 �ڷ�ǡ�    ������ ����");
		System.out.println("�������� ����[3]��3 �ڷ�ǡ�    �������� ��");
		System.out.println("�������� ����[4]���� �޴�   ����������  ��");
		System.out.println("������������������������������������������");
		System.out.print("�� �޴� ���� �� ");
		int select = sc.nextInt(); //�������� select
		switch(select) {
			case 1: outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where location = '��1�ڷ��'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?"); break;
			case 2: outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where location = '��2�ڷ��'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?"); break;
			case 3: outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where location = '��3�ڷ��'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?"); break;
			case 4: return;
			default:  System.out.println("# �߸� �Է��ϼ̽��ϴ�..."); break;
					}
				}
}
	public void searchOrder() {
		while(true) {
			System.out.println();
			System.out.println("������������������ ��� �˻���������������");
			System.out.println("�������� ����[1]å �������� �˻�  ����  ��");
			System.out.println("�������� ����[2]���ڷ� �˻� �������� ����");
			System.out.println("�������� ����[3]���� �޴�   ����������  ��");
			System.out.println("������������������������������������������");
			System.out.print("�� �޴� ���� �� ");
			int select = sc.nextInt(); //�������� select
			switch(select) {
				case 1: searchData("Select * From BOOK_INFO Where title = ?",1); break;
				case 2: searchData("Select * From BOOK_INFO Where author = ?",2); break;
				case 3: return;
				default:  System.out.println("# �߸� �Է��ϼ̽��ϴ�..."); break;
						}
					}
	}

	public void searchData(String col,int search) {

			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				Class.forName(driver);
				conn = DriverManager.getConnection(url,"scott","123456");
				pstmt = conn.prepareStatement(col);
				if(search == 1) {
					System.out.print("�� å ���� �Է� : ");
					title = sc.next();
					pstmt.setString(1, title);
				}
				else if(search == 2) {
					System.out.print("# ���� �Է� : ");
					author = sc.next();
					pstmt.setString(1, author);
				}
				rs = pstmt.executeQuery();
				
				rs.next();
					while(true) {
						System.out.println();
						System.out.println("������������������ �� ��ȸ��������������");
						claim = rs.getString(1);
						title = rs.getString(2);
						author = rs.getString(3);
						pub= rs.getString(4);
						pub_date = rs.getString(5);
						loc= rs.getString(6);
						stock= rs.getString(7);
						System.out.println("# [û����ȣ]�� " +claim);
						System.out.println("# [å  ���� ]�� " +title);
						System.out.println("# [ ��  ��  ]�� " +author);
						System.out.println("# [�� �� �� ]�� " +pub);
						System.out.println("# [�� �� �� ]�� " +pub_date);
						System.out.println("# [ ��  ġ  ]�� " +loc);
						System.out.println("# [ ��  ��  ]�� " +stock);
						System.out.println("��������������[1]&[2]����������[EXIT]");
						System.out.print("�� ");
						select = sc.next();
						switch(select) {
						case "1": if(rs.previous()==false) {
							
										System.out.println();
										System.out.println("�� �����Ͱ� ����ֽ��ϴ�.."); 
										//����  //rownum �Ἥ ������������
									} break;
						case "2": if(rs.next()==false) {
										System.out.println();
										System.out.println("�� �����Ͱ� ����ֽ��ϴ�..");
									} break;			
						case "EXIT" : 
						case "exit" : return;
						default : System.out.println("�� �ٽ� �Է����ּ���...");
					}
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
	public void updateData() {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			System.out.println();
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

	}
