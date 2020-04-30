import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;

public class Borrow_manage {
	 static Scanner sc = new Scanner(System.in);
	final static String driver = "oracle.jdbc.driver.OracleDriver";
	final static String url =  "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	private String id,claim,book_title,br_date,rt_date,pub,stock;
	static public String select = null;
	
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
		br_date = fm.format(cal.getTime());
		cal.add(Calendar.DATE,14);
		rt_date = fm.format(cal.getTime());
		return true ;
	}
	
	public void borrow_book(String account) {
		
		System.out.println("＃＃＃＃＃＃【대출 정보 입력】＃＃＃＃＃＃");
		id=account;
//		System.out.print("# [청구기호]▶ ");
		System.out.print("# [도 서 명]▶ ");
		book_title = sc.next();
		int size =0;
		int p=0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null; //도서명으로 출력
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		ResultSet rs3 = null;
		
		try {
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","123456");
			pstmt3 = conn.prepareStatement("select count(*) from BOOK_INFO where title = ?");
			pstmt3.setString(1,book_title);
			rs3 = pstmt3.executeQuery();
			if(rs3.next()) {
				size = rs3.getInt(1);}
			System.out.println("#");
			System.out.println("#책제목 '"+book_title+"'에 대한 자료검색 결과이며, 총 "+size+"건이 검색되었습니다.");
			System.out.println("#");
				
			
			pstmt2 = conn.prepareStatement("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select * FROM BOOK_INFO"
					+ " where title = ?"
					+ " order by title asc) A)"
					,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE); // 양방향 
			
			
			pstmt2.setString(1, book_title);
			rs = pstmt2.executeQuery();
			System.out.println("＃＃＃＃＃＃＃＃＃【도서 목록 출력】＃＃＃＃＃＃＃＃＃");
			System.out.println("＃［NO］     [청구기호]  [책이름]  [출판사]  [재고]");
			while(rs.next()) {
			int no = rs.getInt(1);
			claim = rs.getString(2);
			book_title = rs.getString(3);
			 pub = rs.getString(5);
			 stock = rs.getString(8);
			System.out.printf("# [%s] %6s  %6s %6s %6s\n",no,claim,book_title,pub,stock);
			}
			if(size==0){
				System.out.print("# ◀ 뒤로가려면 아무키나 입력하시오 ");
				select = sc.next();
				switch(select) {
				default : return;}
				}
			System.out.println("＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
			while(true) {
			System.out.println("# 대출할 도서의 [NO] 를 입력 하세요.. ");
			System.out.print("# ");
			select = sc.next();
			if(Integer.parseInt(select)>size) {
				System.out.println("# 잘못입력하셨습니다..");
			}
			else{
				 p = Integer.parseInt(select);
				 break;
			}
			}
			
			if(rs.absolute(p)) {
				claim= rs.getString(2);
				book_title = rs.getString(3);
				pub = rs.getString(5);
			}
			pstmt = conn.prepareStatement("Insert into book_borrow(id, claim_symbol, borrow_title, BORROW_PUBLISHER) values"
					+ " (?,?,?,?)");
			pstmt.setString(1,account);
			pstmt.setString(2,claim);
			pstmt.setString(3,book_title);
			pstmt.setString(4,pub);
			int n = pstmt.executeUpdate();
			
			if(n==1) {
				System.out.println("＃ 성공적으로 추가되었습니다... ");
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
				if(pstmt2 != null) pstmt2.close();
				if(pstmt3 != null) pstmt3.close();
			}catch(Exception e) {
			}
			try {
				if(rs != null) rs.close();
				if(rs3 != null) rs3.close();
			}catch(Exception e) {
			}
		}
	}
	
	public void retunr_book(String account) {
		System.out.println("＃＃＃＃＃＃【대출 도서 목록】＃＃＃＃＃＃");
		
		
		int size =0;
		int p=0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null; 
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		ResultSet rs3 = null;
		
		try {
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","123456");
			pstmt3 = conn.prepareStatement("select count(*) from BOOK_borrow where id=?");
			pstmt3.setString(1,account);
			rs3 = pstmt3.executeQuery();
			if(rs3.next()) {
				size = rs3.getInt(1);}
			System.out.println("#");
			System.out.println("#'"+account+"'님 이 대출중인 도서 검색 결과이며, 총 "+size+"건이 검색되었습니다.");
			System.out.println("#");
				
			
			pstmt2 = conn.prepareStatement("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select * FROM BOOK_BORROW"
					+ " where id = ?"
					+ " order by title asc) A)"
					,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE); // 양방향 
			
			
			pstmt2.setString(1, account);
			rs = pstmt2.executeQuery();
			System.out.println("＃＃＃＃＃＃＃＃＃【도서 목록 출력】＃＃＃＃＃＃＃＃＃");
			System.out.println("＃［NO］   [책이름]  [출판사]  [대출날짜]  [반납기간]");
			while(rs.next()) {
			int no = rs.getInt(1);
			String book_title1 = rs.getString(4);
			String pub = rs.getString(5);
			String br_date = rs.getString(6);
			String rt_date = rs.getString(7);
			System.out.printf("# [%s] %6s  %6s %6s %6s\n",no,book_title1,pub,br_date,rt_date);
			}
			if(size==0){
				System.out.print("# ◀ 뒤로가려면 아무키나 입력하시오 ");
				select = sc.next();
				switch(select) {
				default : return;}
				}
			System.out.println("＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
			while(true) {
			System.out.println("# 반납할 도서의 [NO] 를 입력 하세요.. ");
			System.out.print("# ");
			select = sc.next();
			if(Integer.parseInt(select)>size) {
				System.out.println("# 잘못입력하셨습니다..");
			}
			else{
				 p = Integer.parseInt(select);
				 break;
			}
			}
			
			if(rs.absolute(p)) {
				
				id = rs.getString(2);
				claim= rs.getString(3);
		
			}
			pstmt = conn.prepareStatement("Delete From book_borrow"
					+ " where id = ? and claim = ?");
		
			
			pstmt.setString(1,account);
			pstmt.setString(2,claim);

			int n = pstmt.executeUpdate();
			
			if(n==1) {
				System.out.println("＃ 성공적으로 추가되었습니다... ");
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
			}
	
	}
		
	
		
	public  void borrow_list(String account) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","123456");
			pstmt = conn.prepareStatement("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select borrow_title,borrow_publisher,borrow_date,loan_period"
					+ " FROM book_borrow"
					+ " where id =?"
					+ " order by borrow_date asc) A"
					+ ")"
					);//에러나면확인해
			
			pstmt.setString(1,account);
			rs = pstmt.executeQuery();
			System.out.println("＃＃＃＃＃＃＃＃＃【대출 목록 출력】＃＃＃＃＃＃＃＃＃");
			System.out.println("＃［NO］     [책이름]  [출판사]  [대출날짜]  [반납기간]");
			while(rs.next()) {
				String no = rs.getString(1);
				String title = rs.getString(2);
				String pub = rs.getString(3);
				String br_date = rs.getString(4);
				String rt_date = rs.getString(5);
				System.out.printf("# [%s] %6s  %6s %10s %10s\n",no,title,pub,br_date.substring(0,10),rt_date.substring(0,10));
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
	
	public  void borrow_list() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null; 
		ResultSet rs = null;
		ResultSet rs2 = null;
		int denum = 1;
		int size = 0;
		String num = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","123456");
			pstmt2 = conn.prepareStatement("select count(*) from BOOK_BORROW");
			rs2 = pstmt2.executeQuery();
			if(rs2.next()) {
			size = rs2.getInt(1);}
			System.out.println("#");
			System.out.println("#'전체회원의 대출목록'에 대한  결과이며, 총 "+size+"건이 검색되었습니다.");
			System.out.println("#");	

		
			
			pstmt = conn.prepareStatement("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select id,borrow_title,borrow_publisher,borrow_date,loan_period"
					+ " FROM book_borrow"
					+ " order by borrow_date asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?");
			pstmt.setString(1,denum+"");
			pstmt.setString(2,denum+"");
			
			rs = pstmt.executeQuery();
			System.out.println("＃＃＃＃＃＃＃＃＃【전체 대출 목록 출력】＃＃＃＃＃＃＃＃＃");
			System.out.println("＃［NO］     [책이름]  [출판사]  [대출날짜]  [반납기간]");
			while(rs.next()) {
				int no = rs.getInt(1);
				if(no<10)  num = "0"+no;
				else num = no+"";
				String id = rs.getString(2);
				String title = rs.getString(3);
				String pub = rs.getString(4);
				String br_date = rs.getString(5);
				String rt_date = rs.getString(6);
				System.out.printf("# [%s] %6s %6s %6s %10s %10s\n",no,id,title,pub,br_date.substring(0,10),rt_date.substring(0,10));
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
				if(pstmt2 != null) pstmt.close();
			}catch(Exception e) {
			}
			try {
				if(rs != null) rs.close();
				if(rs2 != null)rs2.close();
			}catch(Exception e) {
				
			}
		}
		
	}
	
	
	public  void tot_borrow_list(String account) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2= null;
		ResultSet rs = null;
		ResultSet rs2 =null;
		int denum = 1;
		int size = 0;
		try {
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","123456");
			
			pstmt2 = conn.prepareStatement("select count(*) from back_BORROW where id = ?");
			pstmt2.setString(1, account);
			rs2 = pstmt2.executeQuery();
			if(rs2.next()) {
			size = rs2.getInt(1);}

			
			System.out.println("#");
			System.out.println("#'"+account+"'님 이 대출중인 도서 검색 결과이며, 총 "+size+"건이 검색되었습니다.");
			System.out.println("#");
			
			pstmt = conn.prepareStatement("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select book_title,publisher,borrow_date,return_date"
					+ " FROM backup_borrow"
					+ " where id= ?"
					+ " order by no asc) A"
					+ ")"
					);//에러나면확인해
			
			pstmt.setString(1,account);
			rs = pstmt.executeQuery();
			System.out.println("＃＃＃＃＃＃＃＃＃【대출 이력 출력】＃＃＃＃＃＃＃＃＃");
			System.out.println("＃［NO］     [책이름]  [출판사]  [대출날짜]  [반납날짜]");
			while(rs.next()) {
				String no = rs.getString(1);
				String title = rs.getString(2);
				String pub = rs.getString(3);
				String br_date = rs.getString(4);
				String rt_date = rs.getString(5);
				System.out.printf("# [%s] %6s  %6s %8s %8s\n",no,title,pub,br_date.substring(0,10),rt_date.substring(0,10));
				
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

	public  void tot_return_list() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null; 
		ResultSet rs = null;
		ResultSet rs2 = null;
	
		int denum = 1;
		int size = 0;
		String num = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","123456");
			pstmt2 = conn.prepareStatement("select count(*) from backup_BORROW");
			rs2 = pstmt2.executeQuery();
			if(rs2.next()) {
			size = rs2.getInt(1);}
			System.out.println("#");
			System.out.println("#'전체 대출이력'에 대한  결과이며, 총 "+size+"건이 검색되었습니다.");
			System.out.println("#");	

			
			
			pstmt = conn.prepareStatement("Select * FROM"
					+ " (Select ROWNUM rnum, A.* FROM"
					+ " (Select id,book_title, publisher,borrow_date,return_date"
					+ " FROM backup_borrow"
					+ " order by no asc) A"
					+ " where ROWNUM <= ?+9)"
					+ " where rnum >= ?");
			pstmt.setString(1,denum+"");
			pstmt.setString(2,denum+"");
			
			rs = pstmt.executeQuery();
			System.out.println("＃＃＃＃＃＃＃＃＃【전체 대출 목록 출력】＃＃＃＃＃＃＃＃＃");
			System.out.println("＃［NO］ [ID]    [책이름]  [출판사]  [대출날짜]  [반납기간]");
			while(rs.next()) {
				int no = rs.getInt(1);
				if(no<10)  num = "0"+no;
				else num = no+"";
				String id = rs.getString(2);
				String title = rs.getString(3);
				String pub = rs.getString(4);
				String br_date = rs.getString(5);
				String rt_date = rs.getString(6);
				System.out.printf("# [%s] %6s %6s %6s %10s %10s\n",no,id,title,pub,br_date.substring(0,10),rt_date.substring(0,10));
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
				if(pstmt2 != null) pstmt.close();
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
