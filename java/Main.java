import java.util.Scanner;

public class Main {
	static Scanner sc = new Scanner(System.in);
	static int select;
	public static void main(String[] args) {
	while(true) {
		System.out.println("＃＃＃＃＃＃＃【메인  메뉴】＃＃＃＃＃＃＃");
		System.out.println("＃　　　　 　[1]회원 정보　　　　　　　＃");
		System.out.println("＃　　　　 　[2]도서 정보　　　　　　　＃");
		System.out.println("＃　　 　　　[3]대출 정보　　　　　　　＃");
		System.out.println("＃　　　　 　[4] 종  료 　　　　 　   　＃");
		System.out.println("＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
		System.out.print("＃ 메뉴 선택 ▶ ");
		select = sc.nextInt();
		switch(select) {
			case 1: member_info(); break;
			case 2: book_info(); break;
			case 3: borrow_info(); break;
			case 4: System.out.println("# 종료되었습니다...");return;
			default : System.out.println("# 잘못 입력하셨습니다..."); break;
					}
		System.out.println();

			}
	}
	
	public static void member_info() {
		Member_management mem = new Member_management();
		
		while(true) {
			System.out.println();
			System.out.println("＃＃＃＃＃＃【회원 정보 관리】＃＃＃＃＃＃");
			System.out.println("＃　　 　　[1]회원 정보 입력 　　　　　＃");
			System.out.println("＃　　 　　[2]회원 정보 출력　  　　　　＃");
			System.out.println("＃　　 　　[3]회원 정보 조회　  　　　　＃");
			System.out.println("＃　　 　　[4]회원 정보 수정   　　 　　＃");
			System.out.println("＃　　 　　[5]회원 정보 삭제   　　　 　＃");
			System.out.println("＃　　 　　[6]상위 메뉴   　　　　　  　＃");
			System.out.println("＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
			System.out.print("＃ 메뉴 선택 ▶ ");
			select = sc.nextInt();
			switch(select) {
				case 1:mem.inputData(); break;
				case 2:mem.outputData(); break;
				case 3:mem.searchData(); break;
				case 4:mem.updateData(); break;
				case 5:mem.deleteData(); break;
				case 6: return;
				default:  System.out.println("# 잘못 입력하셨습니다..."); break;
						}
					}
		}
	
	public static void book_info() {
		System.out.println();
		while(true) {
			System.out.println("＃＃＃＃＃＃【도서 정보 관리】＃＃＃＃＃＃");
			System.out.println("＃　　 　　[1]도서 정보 입력 　　　     ＃");
			System.out.println("＃　　 　　[2]도서 정보 출력　  　　　   ＃");
			System.out.println("＃　　 　　[3]도서 정보 조회　  　　　   ＃");
			System.out.println("＃　　 　　[4]도서 정보 수정   　　　   ＃");
			System.out.println("＃　　 　　[5]도서 정보 삭제　   　　　  ＃");
			System.out.println("＃　　 　　[6]상위 메뉴 　  　　　　　 ＃");
			System.out.println("＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
			System.out.print("＃ 메뉴 선택 ▶ ");
			select = sc.nextInt();
			switch(select) {
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6: return;
				default:  System.out.println("# 잘못 입력하셨습니다..."); break;
						}
					}
	}
	public static void borrow_info() {
		System.out.println();
		while(true) {
			System.out.println("＃＃＃＃＃＃【도서 대출 관리】＃＃＃＃＃＃");
			System.out.println("＃　　　 　　[1]도서 대출 　　　 　　　＃");
			System.out.println("＃　　　 　　[2]도서 반납　　　    　　　＃");
			System.out.println("＃　　　 　　[3]상위 메뉴   　　　　　  ＃");
			System.out.println("＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃");
			System.out.print("＃ 메뉴 선택 ▶ ");
			select = sc.nextInt();
			switch(select) {
				case 1:
				case 2:
				case 3: return;
				default:  System.out.println("# 잘못 입력하셨습니다..."); break;
						}
					}
	}

}
