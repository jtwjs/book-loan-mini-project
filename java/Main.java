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
		System.out.println("＃＃＃＃＃＃＃【로그인  메뉴】＃＃＃＃＃＃＃");
		System.out.println("＃　　　　 　[1]회원 가입　　　　　　　＃");
		System.out.println("＃　　　　 　[2]로그인　　　　　　　＃");
		System.out.println("＃　　 　　　[3]관리자로 로그인　　　　　　　＃");
		System.out.println("＃　　　　 　[4]종  료 　　　　 　   　＃");
		System.out.println("＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
		System.out.print("＃ 메뉴 선택 ▶ ");
		select = sc.nextInt();
		switch(select) {
			case 1: mem.inputData(); break;
			case 2: login(0); break;
			case 3: login(1); break;
			case 4: System.out.println("# 종료되었습니다...");return;
			default : System.out.println("# 잘못 입력하셨습니다..."); break;
					}
		

			}
	}
	public static void login(int ab) {
		
		System.out.println("＃＃＃＃＃＃【로그인】＃＃＃＃＃＃");
		if(ab==0) {
			String sql = "Select count(*) from book_member where id = ?";
			System.out.print("# [I　   D]▶  ");
			String id = sc.next();
			boolean ox=loginRead(id,sql);
			if(ox == true) {
				System.out.println("# 로그인이 성공적으로 완료됬습니다...");
				account = id;
				main_menu();
			}else {
				System.out.println("# 가입되어있지 않은 ID입니다..."); return;
			}
		}
		else if(ab==1) {
			String sql ="Select count(*) from book_manager where id = ?";
			String sql2 = "Select count(*) from book_manager where pw = ?";
				System.out.print("# [I　   D]▶  ");
			boolean ox=loginRead(sc.next(),sql);
				System.out.print("# [P　   W]▶  ");
			boolean ox2=loginRead(sc.next(),sql2);
			if(ox == false) {
				System.out.println("# 가입되어있지 않은 ID입니다..."); return;
			}
			else if(ox2 == false) {
				System.out.println("# 비밀번호를 잘못 입력하셨습니다..."); return;
			}
			else {
				System.out.println("# 관리자계정으로 로그인 되었습니다...");manage_menu();
			}
		}
	}
	
	public static boolean loginRead(String id,String sql) { //id가 가입되었는지 확인 
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
		try {
			if(rs != null) rs.close();
		}catch(Exception e) {
		}
			
		}
		return false;
	}
	
	
	public static void main_menu() {
		
	while(true) {
		System.out.println("＃＃＃＃＃＃＃【메인  메뉴】＃＃＃＃＃＃＃");
		System.out.println("＃　　　　 　[1]내 서재　　　　　　　＃");
		System.out.println("＃　　　　 　[2]도서 정보　　　　　　　＃");
//		System.out.println("＃　　 　　　[3]대출 정보　　　　　　　＃");
		System.out.println("＃　　　　 　[3]로그아웃 　　　　 　   　＃");
		System.out.println("＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
		System.out.print("＃ 메뉴 선택 ▶ ");
		select = sc.nextInt();
		switch(select) {
			case 1: member_info(); break;
			case 2: book_info(); break;
			case 3: System.out.println("# 로그아웃 되었습니다...");return;
			default : System.out.println("# 잘못 입력하셨습니다..."); break;
					}
		

			}
	}
	
	public static void member_info() {
		Member_manage mem = new Member_manage();
		Book_manage book = new Book_manage();
		Borrow_manage bor = new Borrow_manage();
		while(true) {
			
			System.out.println("＃＃＃＃＃＃【내 서재】＃＃＃＃＃＃");
			System.out.println("＃　　 　　[1]개인정보 보기   　　　　 　＃");
			System.out.println("＃　　 　　[2]신청중인 도서 목록   　　　　 　＃");
			System.out.println("＃　　 　　[3]대출중인 도서 목록   　　　　 　＃");
			System.out.println("＃　　 　　[4]도서 신청 이력 보기   　　　　 　＃");
			System.out.println("＃　　 　　[5]도서 대출 이력 보기   　　　　 　＃");
			System.out.println("＃　　 　　[6]도서 반납하기   　　　　 　＃");
			System.out.println("＃　　 　　[7]회원 정보 수정   　　 　　＃");
			System.out.println("＃　　 　　[8]회원 탈퇴   　　　　 　＃");
			System.out.println("＃　　 　　[9]상위 메뉴   　　　　　  　＃");
			System.out.println("＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
			System.out.print("＃ 메뉴 선택 ▶ ");
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
				default:  System.out.println("# 잘못 입력하셨습니다..."); break;
						}
					}
		}
	
	public static void book_info() {
		Book_manage book = new Book_manage();
		Borrow_manage bor = new Borrow_manage();
		while(true) {
			
			System.out.println("＃＃＃＃＃＃【도서 정보】＃＃＃＃＃＃");
			System.out.println("＃　　 　　[1]도서 신청 하기 　　　     ＃");
			System.out.println("＃　　 　　[2]도서 정보 조회　  　　　   ＃");
			System.out.println("＃　　 　　[3]도서 대출  하기 　  　　　　　 ＃");
			System.out.println("＃　　 　　[4]상위 메뉴 　  　　　　　 ＃");
			System.out.println("＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
			System.out.print("＃ 메뉴 선택 ▶ ");
			select = sc.nextInt();
			switch(select) {
				case 1: book.application(account); break;
				case 2: book.searchOrder(); break;
				case 3: bor.borrow_book(account); break;
				case 4: return;
				default:  System.out.println("# 잘못 입력하셨습니다..."); break;
						}
					}
	}
	public static void borrow_info() {
		
		while(true) {
			System.out.println("＃＃＃＃＃＃【도서 대출 관리】＃＃＃＃＃＃");
			System.out.println("＃　　　 　　[1]도서 대출 　　　 　　　＃");
			System.out.println("＃　　　 　　[2]도서 반납　　　    　　　＃");
			System.out.println("＃　　　 　　[3]대출 목록   　　　　　  ＃");
			System.out.println("＃　　　 　　[4]상위 메뉴   　　　　　  ＃");
			System.out.println("＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
			System.out.print("＃ 메뉴 선택 ▶ ");
			select = sc.nextInt();
			switch(select) {
				case 1:
				case 2:
				case 3:
				case 4: return;
				default:  System.out.println("# 잘못 입력하셨습니다..."); break;
						}
					}
	}

	
	public static void manage_menu() {
		
		
		while(true) {
			System.out.println("＃＃＃＃＃＃＃【관리  메뉴】＃＃＃＃＃＃＃");
			System.out.println("＃　　　　 　[1]회원 관리　　　　　　　＃");
			System.out.println("＃　　　　 　[2]도서 관리　　　　　　　＃");
			System.out.println("＃　　 　　　[3]대출 관리　　　　　　　＃");
			System.out.println("＃　　　　 　[4]로그아웃 　　　　 　   　＃");
			System.out.println("＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
			System.out.print("＃ 메뉴 선택 ▶ ");
			select = sc.nextInt();
			switch(select) {
				case 1: manage_member(); break;
				case 2: manage_book(); break;
				case 3: manage_borrow(); break;
				case 4: System.out.println("# 로그아웃 되었습니다...");return;
				default : System.out.println("# 잘못 입력하셨습니다..."); break;
						}
			

				}
		}

	public static void manage_member() {
		Member_manage mem = new Member_manage();
		
		while(true) {
			System.out.println("＃＃＃＃＃＃＃【회원  관리】＃＃＃＃＃＃＃");
			System.out.println("＃　　　　 　[1]회원 목록　　　　　　　＃");
			System.out.println("＃　　　　 　[2]회원 수정　　　　　　　＃");
			System.out.println("＃　　 　　　[3]회원 삭제　　　　　　　＃");
			System.out.println("＃　　　　 　[4]상위메뉴 　　　　 　   　＃");
			System.out.println("＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
			System.out.print("＃ 메뉴 선택 ▶ ");
			select = sc.nextInt();
			switch(select) {
				case 1: mem.outputOrder(); break;
				case 2: mem.updateData(); break;
				case 3: mem.deleteData(); break;
				case 4: return;
				default : System.out.println("# 잘못 입력하셨습니다..."); break;
						}
			

				}
		}
	
	public static void manage_book() {
		Book_manage book = new Book_manage();
		while(true) {
			System.out.println("＃＃＃＃＃＃＃【도서  관리】＃＃＃＃＃＃＃");
			System.out.println("＃　　　　 　[1]도서 정보 추가　　　　　　　＃");
			System.out.println("＃　　　　 　[2]도서 정보 보기　　　　　　　＃");
			System.out.println("＃　　 　　　[3]도서 정보 수정　　　　　　　＃");
			System.out.println("＃　　　　 　[4]도서 정보 삭제　　　　 　   　＃");
			System.out.println("＃　　　　 　[5]신청 도서 관리　　　　 　   　＃");
			System.out.println("＃　　　　 　[6]상위메뉴　　　　 　   　＃");
			System.out.println("＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
			System.out.print("＃ 메뉴 선택 ▶ ");
			select = sc.nextInt();
			switch(select) {
				case 1: book.inputData(); break;
				case 2: book.outputOrder(); break;
				case 3: book.updateData(); break;
				case 4: book.deleteData(); break;
				case 5: application_manage(); break;
				case 6: return;
				default : System.out.println("# 잘못 입력하셨습니다..."); break;
						}
			

				}
		}

	public static void application_manage() {
		Book_manage book = new Book_manage();
		while(true) {
			System.out.println("＃＃＃＃＃＃＃【신청 도서 관리】＃＃＃＃＃＃＃");
			System.out.println("＃　　　　 　[1]처리대기 도서 목록　　　　　　　＃");
			System.out.println("＃　　　　 　[2]처리완료 도서 목록　　　　　　　＃");
			System.out.println("＃　　　　 　[3]상위메뉴 　　　　 　   　＃");
			System.out.println("＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
			System.out.print("＃ 메뉴 선택 ▶ ");
			select = sc.nextInt();
			switch(select) {
				case 1: book.application_list(); break;
				case 2: book.tot_application_list(); break;
				case 3: return;
				default : System.out.println("# 잘못 입력하셨습니다..."); break;
						}
			

				}
		
	}
	public static void manage_borrow() {
		Borrow_manage bor = new Borrow_manage();
		while(true) {
			System.out.println("＃＃＃＃＃＃＃【대출 관리】＃＃＃＃＃＃＃");
			System.out.println("＃　　　　 　[1]대출 도서 목록　　　　　　　＃");
			System.out.println("＃　　　　 　[2]반납 도서 목록　　　　　　　＃");
			System.out.println("＃　　　　 　[3]상위메뉴 　　　　 　   　＃");
			System.out.println("＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
			System.out.print("＃ 메뉴 선택 ▶ ");
			select = sc.nextInt();
			switch(select) {
				case 1: bor.borrow_list();; break;
				case 2: bor.tot_return_list(); break;
				case 3: return;
				default : System.out.println("# 잘못 입력하셨습니다..."); break;
						}
			

				}
	}
}
