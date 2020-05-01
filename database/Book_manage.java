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
		  
		System.out.println("＃＃＃＃＃＃【도서 정보 입력】＃＃＃＃＃＃");
		System.out.print("# [청구기호]▶ ");
		claim = sc.next();
		System.out.print("# [도 서 명]▶ ");
		title = sc.next();
		System.out.print("# [작 가 명]▶ ");
		author = sc.next();
		
		System.out.print("# [출 판 사]▶ ");
		pub = sc.next();
		while(true) {
			System.out.print("# [출 간 일]▶ ");
			try {
				if(date_format(sc.next())) break;
			}catch(Exception e) {
				System.out.println("# 날짜 형식이 잘못 입력 되엇습니다..(ex:19950307)");
			}
			}
		System.out.print("# [자 료 실]▶ ");
		loc = sc.next();
		System.out.print("# [수  량]▶ ");
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
				System.out.println("＃ 성공적으로 추가되었습니다... ");
			
			
		}catch(Exception e) {
			System.out.println("＃ DB 연결에 실패했습니다...");
			System.out.println("＃ Error - "+e.getMessage());
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
	
	public boolean date_format(String date) {  //날짜 포맷 확인 몌쏘드
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
			System.out.println("# 날짜 형식이 잘못 입력 되엇습니다..(ex:19950307)");
			return false;

		}else if(int_mm<1||int_mm>12||int_dd<1||int_dd>31) {
			System.out.println("# 날짜 형식이 잘못 입력 되엇습니다..(ex:19950307)");
			return false;
		}else if(int_yyyy>realYear&&int_mm>realMonth&&int_dd>realDay) {
			System.out.println("# 날짜 형식이 잘못 입력 되엇습니다..(ex:19950307)");
			return false;
		}
//		
		pub_date = fm.format(cal.getTime());
		return true ;
	}
	
	//도서 출력
	public void outputOrder(){
		while(true) {
			
			System.out.println("＃＃＃＃＃＃【모든 도서 검색】＃＃＃＃＃＃");
			System.out.println("＃　　　 　　[1]책이름 오름차순   　　  ＃");
			System.out.println("＃　　　 　　[2]주제별로 검색　    　　　＃");
			System.out.println("＃　　　 　　[3]위치별로 검색　    　　　＃");
			System.out.println("＃　　　 　　[4]재고 내림차순　    　　　＃");
			System.out.println("＃　　　 　　[5]상위 메뉴   　　　　　  ＃");
			System.out.println("＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
			System.out.print("＃ 메뉴 선택 ▶ ");
			int select = sc.nextInt(); //지역변수 select
			switch(select) {
				case 1: 
						str = "책이름 오름차순";
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
						str = "재고 내림차순";
						con ="";
						outputData("Select * FROM"
						+ " (Select ROWNUM rnum, A.* FROM"
						+ " (Select title,publisher,location,inventory_quantity"
						+ " FROM BOOK_INFO"
						+ " order by inventory_quantity desc) A"
						+ " where ROWNUM <= ?+9)"
						+ " where rnum >= ?",con); break;
				case 5: return;
				default:  System.out.println("# 잘못 입력하셨습니다..."); break;
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
			System.out.println("#'"+str+"'에 대한 자료검색 결과이며, 총 "+size+"건이 검색되었습니다.");
			System.out.println("#");
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setString(1,denum+"");
			pstmt.setString(2,denum+"");
			
			rs = pstmt.executeQuery();
			
			System.out.println("＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃【도서 목록 출력】＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
		
			System.out.println("＃［NO］     [책이름] 		  [출판사]	 [위치]  [재고]");
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
			
			System.out.println("＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃◀[1]&[2]▶＃＃＃＃＃＃＃＃＃＃＃＃[EXIT]");
			System.out.print("＃ ");
			select = sc.next();
			switch(select) {
			case "1" : if(denum==1) {
						
						System.out.println("＃ 데이터가 비어있습니다..");

							} 
						else denum-=10; break;
			case "2" : if(denum+9<size) denum+=10; 
						else { 
								
								System.out.println("＃ 데이터가 비어있습니다..");
						}
								break;
			case "EXIT" : return;
			case "exit" : return;
			default : System.out.println("＃ 다시 입력해주세요...");
			}
			
		}catch(Exception e) {System.out.println("＃ DB 연결에 실패했습니다...");
		System.out.println("＃ Error - "+e.getMessage());
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
		
	}//while문	
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
		
		System.out.println("＃＃＃＃＃＃＃＃＃【주제별 도서 목록】＃＃＃＃＃＃＃＃");
		System.out.println("＃　　　 　　[0] 총　류 〔000 ~ 090〕　　  　  　＃");
		System.out.println("＃　　　 　　[1] 철　학 〔100 ~ 190〕　　  　  　＃");
		System.out.println("＃　　　 　　[2] 종　교 〔200 ~ 290〕　　  　  　＃");
		System.out.println("＃　　　 　　[3]사회과학〔300 ~ 390〕　　  　  　＃");
		System.out.println("＃　　　 　　[4]순수과학〔400 ~ 490〕　　  　  　＃");
		System.out.println("＃　　　 　　[5]기술과학〔500 ~ 590〕　　  　  　＃");
		System.out.println("＃　　　 　　[6] 예　술 〔600 ~ 690〕　　  　  　＃");
		System.out.println("＃　　　 　　[7] 언　어 〔700 ~ 790〕　　  　  　＃");
		System.out.println("＃　　　 　　[8] 문　학 〔800 ~ 890〕　　  　  　＃");
		System.out.println("＃　　　 　　[9] 역　사 〔900 ~ 990〕　　  　  　＃");
		System.out.println("＃　　　 　　[10]상위 메뉴   　　　　　　　  　  　 ＃");
		System.out.println("＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
		System.out.print("＃ 메뉴 선택 ▶ ");
		int select = sc.nextInt(); //지역변수 select
		switch(select) {
			case 0:
				 	str = "총류 도서";
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
					str = "철학 도서";
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
					str = "종교 도서";
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
					str = "사회과학 도서";
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
				 	str = "순수기술 도서";
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
					str = "기술과학 도서";
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
					str = "예술 도서";
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
					str = "언어 도서";
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
					str = "문학 도서";
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
					str = "역사 도서";
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
			default:  System.out.println("# 잘못 입력하셨습니다..."); break;
					}
				}
}
public void menu_loc() {
	while(true) {
		
		System.out.println("＃＃＃＃＃＃【위치별 도서 목록】＃＃＃＃＃");
		System.out.println("＃　　　 　　[0]어린이자료실　　　     　　＃");	
		System.out.println("＃　　　 　　[1]제 1 자료실　　　     　　＃");
		System.out.println("＃　　　 　　[2]제 2 자료실　    　　　 　＃");
		System.out.println("＃　　　 　　[3]상위 메뉴   　　　　　  ＃");
		System.out.println("＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
		System.out.print("＃ 메뉴 선택 ▶ ");
		int select = sc.nextInt(); //지역변수 select
		switch(select) {
			case 0: 
					str = "어린이자료실 도서";
					con = " where location = '어린이자료실'";
					outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where location = '어린이자료실'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?",con);break;
			case 1: 
				str = "제1자료실 도서";
				con = " where location = '제1자료실'";
				outputData("Select * FROM"
				+ " (Select ROWNUM rnum, A.* FROM"
				+ " (Select title,publisher,location,inventory_quantity"
				+ " FROM BOOK_INFO"
				+ " where location = '제1자료실'"
				+ " order by title asc) A"
				+ " where ROWNUM <= ?+9)"
				+ " where rnum >= ?",con);break;
			case 2: 
					str = "제2자료실 도서";
					con = " where location = '제2자료실'";
					outputData("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select title,publisher,location,inventory_quantity"
					+ " FROM BOOK_INFO"
					+ " where location = '제2자료실'"
					+ " order by title asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?",con); break;
			case 3: return;
			default:  System.out.println("# 잘못 입력하셨습니다..."); break;
					}
				}
}
	public void searchOrder() {
		while(true) {
			
			System.out.println("＃＃＃＃＃＃【도서 목록 검색】＃＃＃＃＃＃");
			System.out.println("＃　　　 　　[1]모든 도서 검색  　    　   ＃");
			System.out.println("＃　　　 　　[2]책 제목으로 검색  　　    ＃");
			System.out.println("＃　　　 　　[3]상위 메뉴   　　　　　  ＃");
			System.out.println("＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
			System.out.print("＃ 메뉴 선택 ▶ ");
			int select = sc.nextInt(); //지역변수 select
			switch(select) {
			case 1: outputOrder();break;
				case 2: searchData("Select * FROM"
						+ " (Select ROWNUM rnum, A.* FROM"
						+ " (Select * FROM BOOK_INFO"
						+ " where title like ?"
						+ " order by title asc) A)"
						+ " where rnum = ?"); break;
				case 3: return;
				default:  System.out.println("# 잘못 입력하셨습니다..."); break;
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
			System.out.print("＃ 책 제목 입력 : ");
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
				System.out.println("#책제목 '"+title+"'에 대한 자료검색 결과이며, 총 "+size+"건이 검색되었습니다.");
				System.out.println("#");
					while(rs.next()) {
						System.out.println("＃＃＃＃＃＃【도서 상세 조회】＃＃＃＃＃＃");
						claim = rs.getString(2);
						String book_title = rs.getString(3);
						author = rs.getString(4);
						pub= rs.getString(5);
						pub_date = rs.getString(6);
						loc= rs.getString(7);
						stock= rs.getString(8);
						System.out.println("# [청구기호]▶ " +claim);
						System.out.println("# [책  제목 ]▶ " +book_title);
						System.out.println("# [ 저  자  ]▶ " +author);
						System.out.println("# [출 판 사 ]▶ " +pub);
						System.out.println("# [출 간 일 ]▶ " +pub_date.substring(0,10));
						System.out.println("# [ 위  치  ]▶ " +loc);
						System.out.println("# [ 재  고  ]▶ " +stock);
					}
						System.out.println("＃＃＃＃＃＃◀[1]&[2]▶＃＃＃＃[EXIT]");
						System.out.print("＃ ");
						select = sc.next();
						switch(select) {
						case "1" : if(denum==1) {
									
									System.out.println("＃ 데이터가 비어있습니다..");
										} 
									else denum-=1; break;
						case "2" : if(denum<size) denum++; 
									else { 
											
											System.out.println("＃ 데이터가 비어있습니다..");
									}
											break;	
						case "EXIT" :
						case "exit" : return;
						default : System.out.println("＃ 다시 입력해주세요...");
						}
			
				
			
					
			}catch(Exception e) {System.out.println("＃ DB 연결에 실패했습니다...");
			System.out.println("＃ Error - "+e.getMessage());
			
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
			
			System.out.println("＃＃＃＃＃＃【도서 정보 수정】＃＃＃＃＃＃");
			System.out.print("# [수정할 도서의 청구기호]▶  ");
			claim = sc.next();
			System.out.print("# [위　치]▶ ");
			loc = sc.next();
			System.out.print("# [재　고]▶ ");
			stock = sc.next();
			System.out.println("＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
			
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
				System.out.println("＃ 성공적으로 수정되었습니다... ");
			else
				System.out.println("＃ 입력하신 청구기호는 없는 도서입니다... ");
			
		}catch(Exception e) {System.out.println("＃ DB 연결에 실패했습니다...");
		System.out.println("＃ Error - "+e.getMessage());
		
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
			System.out.println("＃＃＃＃＃＃【회원 정보 삭제】＃＃＃＃＃＃");
			System.out.print("# [삭제할 도서의 청구기호]▶  ");
			claim = sc.next();
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","123456");
			pstmt = conn.prepareStatement("Delete From BOOK_INFO Where claim_symbol = ?");
			pstmt.setString(1, claim);
			int n = pstmt.executeUpdate();
			if( n==1 )
				System.out.println("＃ 성공적으로 삭제되었습니다... ");
			else
				System.out.println("＃ 입력하신 청구기호는 없는  도서입니다... ");
			
		}catch(Exception e) {System.out.println("＃ DB 연결에 실패했습니다...");
		System.out.println("＃ Error - "+e.getMessage());
		
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
		System.out.println("＃＃＃＃＃＃【신청 도서 정보 입력】＃＃＃＃＃＃");
		System.out.print("# [도 서 명]▶ ");
		title = sc.next();
		System.out.print("# [출 판 사]▶ ");
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
				System.out.println("＃ 도서 신청이 완료되었습니다... ");
		}catch(Exception e) {System.out.println("＃ DB 연결에 실패했습니다...");
		System.out.println("＃ Error - "+e.getMessage());
		
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
					);//에러나면확인해
			
			pstmt.setString(1,account);
			rs = pstmt.executeQuery();
			System.out.println("＃＃＃＃＃＃＃＃＃＃＃＃【도서 신청 목록】＃＃＃＃＃＃＃＃＃＃＃＃");
			System.out.println("＃［NO］[책이름]  \t[출판사]  [신청날짜]  [진행상태]");
			while(rs.next()) {
				String no = rs.getString(1);
				String title = rs.getString(2);
				String pub = rs.getString(3);
				String app_date = rs.getString(4);
				String app_state = rs.getString(5);
				System.out.printf("# [%s] %-10s  %-6s %-6s %-6s\n",no,title,pub,app_date.substring(0,10),app_state);
				
			}
			System.out.println("＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
			System.out.print("# ◀ 뒤로가려면 아무키나 입력하시오 ");
			select = sc.next();
			switch(select) {
			default : return;
			}

			
		}catch(Exception e) {
			System.out.println("＃ DB 연결에 실패했습니다...");
			System.out.println("＃ Error - "+e.getMessage());
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
			System.out.println("#'처리대기 도서목록'에 대한 자료검색 결과이며, 총 "+size+"건이 검색되었습니다.");
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
			
			System.out.println("＃＃＃＃＃＃＃＃＃【도서 신청 목록】＃＃＃＃＃＃＃＃＃");
			System.out.println("＃［NO］ [ID]    [책이름]  [출판사]  [신청날짜]  [진행상태]");
			while(rs.next()) {
				String no = rs.getString(1);
				String id = rs.getString(2);
				String title = rs.getString(3);
				String pub = rs.getString(4);
				String app_date = rs.getString(5);
				String app_state = rs.getString(6);
				System.out.printf("# [%s] %6s %6s %6s %6s %6s\n",no,id,title,pub,app_date.substring(0,10),app_state);
			}
			
			System.out.println("＃＃＃＃＃＃＃＃＃＃◀[1]&[2]▶＃＃＃＃＃＃[EXIT]");
			System.out.print("＃ ");
			select = sc.next();
			switch(select) {
			case "1" : if(denum==1) {
						
						System.out.println("＃ 데이터가 비어있습니다..");

							} 
						else denum-=10; break;
			case "2" : if(denum+9<size) denum+=10; 
						else { 
								
								System.out.println("＃ 데이터가 비어있습니다..");
						}
								break;
			case "EXIT" : return;
			case "exit" : return;
			default : System.out.println("＃ 다시 입력해주세요...");
			}
			
		}catch(Exception e) {System.out.println("＃ DB 연결에 실패했습니다...");
		System.out.println("＃ Error - "+e.getMessage());
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
		
	}//while문	

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
					);//에러나면확인해
			
			pstmt.setString(1,account);
			rs = pstmt.executeQuery();
			System.out.println("＃＃＃＃＃＃＃＃＃【신청 이력 출력】＃＃＃＃＃＃＃＃＃");
			System.out.println("＃［NO］     [책이름]  [출판사]  [접수날짜]  [접수상태]");
			while(rs.next()) {
				String no = rs.getString(1);
				String title = rs.getString(2);
				String pub = rs.getString(3);
				String app_date = rs.getString(4);
				String app_state = rs.getString(5);
				System.out.printf("# [%s] %6s  %6s %8s %8s\n",no,title,pub,app_date.substring(0,10),app_state);
			
			}
			System.out.println("＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
			System.out.print("# ◀ 뒤로가려면 아무키나 입력하시오 ");
			select = sc.next();
			switch(select) {
			default : return;
			}

			
		}catch(Exception e) {
			System.out.println("＃ DB 연결에 실패했습니다...");
			System.out.println("＃ Error - "+e.getMessage());
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
			System.out.println("#'처리완료'된 신청도서에 대한 자료검색 결과이며, 총 "+size+"건이 검색되었습니다.");
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
			
			System.out.println("＃＃＃＃＃＃＃＃＃【신청 이력 출력】＃＃＃＃＃＃＃＃＃");
			System.out.println("＃［NO］ [id]   [책이름]  [출판사]  [접수날짜]  [접수상태]");
			while(rs.next()) {
				String no = rs.getString(1);
				String id = rs.getString(2);
				String title = rs.getString(3);
				String pub = rs.getString(4);
				String app_date = rs.getString(5);
				String app_state = rs.getString(6);
				System.out.printf("# [%s] %6s %6s %6s %8s %8s\n",no,id,title,pub,app_date.substring(0,10),app_state);
			
			}
			
			System.out.println("＃＃＃＃＃＃＃＃＃＃◀[1]&[2]▶＃＃＃＃＃＃[EXIT]");
			System.out.print("＃ ");
			select = sc.next();
			switch(select) {
			case "1" : if(denum==1) {
						
						System.out.println("＃ 데이터가 비어있습니다..");

							} 
						else denum-=10; break;
			case "2" : if(denum+9<size) denum+=10; 
						else { 
								
								System.out.println("＃ 데이터가 비어있습니다..");
						}
								break;
			case "EXIT" : return;
			case "exit" : return;
			default : System.out.println("＃ 다시 입력해주세요...");
			}
			
		}catch(Exception e) {System.out.println("＃ DB 연결에 실패했습니다...");
		System.out.println("＃ Error - "+e.getMessage());
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
		
	}//while문	

	}
	

}
