import java.util.Scanner;

public class Main {
	static Scanner sc = new Scanner(System.in);
	static int select;
	public static void main(String[] args) {
	while(true) {
		System.out.println("��������������������  �޴�����������������");
		System.out.println("���������� ��[1]ȸ�� ��������������������");
		System.out.println("���������� ��[2]���� ��������������������");
		System.out.println("������ ������[3]���� ��������������������");
		System.out.println("���������� ��[4] ��  �� �������� ��   ����");
		System.out.println("������������������������������������������");
		System.out.print("�� �޴� ���� �� ");
		select = sc.nextInt();
		switch(select) {
			case 1: member_info(); break;
			case 2: book_info(); break;
			case 3: borrow_info(); break;
			case 4: System.out.println("# ����Ǿ����ϴ�...");return;
			default : System.out.println("# �߸� �Է��ϼ̽��ϴ�..."); break;
					}
		System.out.println();

			}
	}
	
	public static void member_info() {
		Member_management mem = new Member_management();
		
		while(true) {
			System.out.println();
			System.out.println("��������������ȸ�� ���� ������������������");
			System.out.println("������ ����[1]ȸ�� ���� �Է� ������������");
			System.out.println("������ ����[2]ȸ�� ���� ��¡�  ����������");
			System.out.println("������ ����[3]ȸ�� ���� ��ȸ��  ����������");
			System.out.println("������ ����[4]ȸ�� ���� ����   ���� ������");
			System.out.println("������ ����[5]ȸ�� ���� ����   ������ ����");
			System.out.println("������ ����[6]���� �޴�   ����������  ����");
			System.out.println("������������������������������������������");
			System.out.print("�� �޴� ���� �� ");
			select = sc.nextInt();
			switch(select) {
				case 1:mem.inputData(); break;
				case 2:mem.outputData(); break;
				case 3:mem.searchData(); break;
				case 4:mem.updateData(); break;
				case 5:mem.deleteData(); break;
				case 6: return;
				default:  System.out.println("# �߸� �Է��ϼ̽��ϴ�..."); break;
						}
					}
		}
	
	public static void book_info() {
		System.out.println();
		while(true) {
			System.out.println("������������������ ���� ������������������");
			System.out.println("������ ����[1]���� ���� �Է� ������     ��");
			System.out.println("������ ����[2]���� ���� ��¡�  ������   ��");
			System.out.println("������ ����[3]���� ���� ��ȸ��  ������   ��");
			System.out.println("������ ����[4]���� ���� ����   ������   ��");
			System.out.println("������ ����[5]���� ���� ������   ������  ��");
			System.out.println("������ ����[6]���� �޴� ��  ���������� ��");
			System.out.println("������������������������������������������");
			System.out.print("�� �޴� ���� �� ");
			select = sc.nextInt();
			switch(select) {
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6: return;
				default:  System.out.println("# �߸� �Է��ϼ̽��ϴ�..."); break;
						}
					}
	}
	public static void borrow_info() {
		System.out.println();
		while(true) {
			System.out.println("������������������ ���� ������������������");
			System.out.println("�������� ����[1]���� ���� ������ ��������");
			System.out.println("�������� ����[2]���� �ݳ�������    ��������");
			System.out.println("�������� ����[3]���� �޴�   ����������  ��");
			System.out.println("������������������������������������������");
			System.out.print("�� �޴� ���� �� ");
			select = sc.nextInt();
			switch(select) {
				case 1:
				case 2:
				case 3: return;
				default:  System.out.println("# �߸� �Է��ϼ̽��ϴ�..."); break;
						}
					}
	}

}
