package view;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.EmartMallVO;
import model.PersonVO;

public class View {
	Scanner sc=new Scanner(System.in);
	Connection conn;
	PreparedStatement pstmt;
	EmartMallVO evo=null;
	PersonVO pvo = null;
	public int action;
	
	public void inputDB(EmartMallVO vo,int i) { // DB에 크롤링 자료 넣기 i는 카테고리 번호(101~501)
		conn=model.JDBCUtill.connect();
		String sql_insert="INSERT INTO EPRODUCT VALUES((SELECT NVL(MAX(EID),0)+1 FROM EPRODUCT),?,?,?,"+i+",?)";
		
		try {
			pstmt=conn.prepareStatement(sql_insert);
			pstmt.setString(1,vo.geteName()); // 이름
			pstmt.setInt(2,vo.getePrice()); // 금액
			pstmt.setInt(3,vo.geteReview()); // 리뷰수
			pstmt.setString(4, vo.geteImg()); // 이미지
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			model.JDBCUtill.disConnect(pstmt, conn);
		}

	}
	public void Crawling(String http,int i2) { // url과 카테고리 번호 입력
		final String url=http;
		Document doc=null;
		try {
			doc=Jsoup.connect(url).get();
		} catch (IOException e) {

			e.printStackTrace();
		}

		Elements eles=doc.select("ul>li.cunit_t232>div>div>div>a>em.tx_ko"); // 이름
		Elements eles2=doc.select("ul>li.cunit_t232>div>div>div>em.ssg_price"); // 금액
		Elements eles3=doc.select("ul>li.cunit_t232>div>div>div>span>em"); // 리뷰수
		Elements eles4=doc.select("ul>li.cunit_t232>div>div.thmb>a>img.i1"); // 이미지
		Iterator<Element> itr=eles.iterator(); //이름
		Iterator<Element> itr2=eles2.iterator(); //금액
		Iterator<Element> itr3=eles3.iterator(); //리뷰수
		Iterator<Element> itr4=eles4.iterator(); //이미지
		for(int i=0;i<20;i++) {
			String str=itr.next().text();
			String str2=itr2.next().text();
			String str3=itr3.next().text();
			String str4=itr4.next().attr("src");

			EmartMallVO vo=new EmartMallVO();
			System.out.println(str);
			System.out.println(str2);
			System.out.println(str3);
			System.out.println(str4);
			vo.seteName(str); //이름
			vo.setePrice(Integer.parseInt(str2.replaceAll("[^0-9]", ""))); //금액
			vo.seteReview(Integer.parseInt(str3.replaceAll("[^0-9]", ""))); //리뷰수
			vo.seteImg(str4); // 이미지
			//			vo.setName(str);
			//			vo.setPrice(Integer.parseInt(str2.replaceAll("[^0-9]", "")));
			//			vo.setGrade(Float.parseFloat(str3.replaceAll("[^0-9.]", "")));
			//			vo.setReview(Integer.parseInt(str4.replaceAll("[^0-9]", "")));
			this.inputDB(vo,i2); // insert에 vo 입력
		}
//		System.out.println("로그: DB에 저장완료!");


	}
	public void startView() { // 첫 화면 출력
		while(true) {
			System.out.println("====Emart Mall에 오신것을 환영합니다!====");
			System.out.println("1.로그인");
			System.out.println("2.회원가입");
			System.out.println("3.종료");
			System.out.print("입력 :");
			try {
				action=sc.nextInt(); // action은 멤버변수를 사용하면 됨
				if(action>=1 && action<=3) { // 입력값 제한
					return;
				}else {
					System.out.println("범위 외 입력입니다. 다시 입력해주세요.");
				}
			}catch(Exception e) {
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			}
		}
	}
	public void logIn() { // 로그인 알림
		System.out.println("====로그인====");
	}
	public void join() { // 회원가입 알림
		System.out.println("====회원가입====");
	}
	public String joinName() { //이름 입력, 입력한 이름 String으로 반환
		System.out.print("이름 :");
		String name=sc.next();
		return name;
	}
	public String inputID() { //ID입력,String으로 반환
		System.out.print("ID :");
		String id=sc.next();
		return id;
	}

	public void chId() { //아이디가 존재하지 않을 시 
		System.out.println("회원정보가 없습니다. 다시 로그인해주세요.");
		System.out.println();
	}
	
	public void exId() {
		System.out.println("존재하는 id입니다. 다시 입력해주세요.");
	}

	public String inpuPw() { //비밀번호 입력 및 체크, 체크 후 String으로 반환,체크안되면 null
		System.out.print("비밀번호 :");
		String pw=sc.next();
		System.out.print("비밀번호 확인 :");
		String chPw=sc.next();
		if(pw.equals(chPw)) {
			return pw;
		}
		this.checkPw();
		return null;
	}

	public void checkPw() { // 비밀번호 일치하지 않을시 출력
		System.out.println("비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
	}

	public void successJoin() { // 회원가입 완료 안내
		System.out.println("회원가입이 완료되었습니다!");
	}

	public String loginPw() { //비밀번호 입력,String으로 반환
		System.out.print("비밀번호 : ");
		String pw=sc.next();
		return pw;
	}

	public void exit() { //프로그램 종료
		System.out.print("프로그램을 종료합니다");
		for (int i = 0; i < 5; i++) {
			System.out.print(".");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println();
		System.out.println("이용해주셔서 감사합니다.");
	}

	public void welcome(PersonVO vo) {
        System.out.println(vo.getName()+"님 어서오세요!");
    }
	
	public void mainView() { // 로그인 후 메인화면
		while(true) {
			System.out.println("===EmartMall에 오신것을 환영합니다!===");
			System.out.println("1.상품목록");
			System.out.println("2.상품검색");
			System.out.println("3.마이페이지");
			System.out.println("4.로그아웃");
			System.out.print(">> ");
			try {
				action=sc.nextInt();
				if(action>=1&&action<=4) { //입력값 제한
					return;
				}
				System.out.println("범위 외 입력입니다. 다시 입력해주세요.");
			}catch(Exception e) {
				System.out.println("잘못된 입력입니다.다시입력해주세요.");
			}
		}
	}

	public void printList(ArrayList<EmartMallVO> datas) { //리스트의 모든 상품 출력
		System.out.println("====상품 목록====");
		for (int i = 0; i < datas.size(); i++) {
			System.out.println("번호 :"+datas.get(i).geteId()+"  상품명 :"+datas.get(i).geteName()+"  가격 :"+datas.get(i).getePrice()+
					"  구매횟수 :"+datas.get(i).geteReview()+"  카테고리 :"+datas.get(i).getCategory());
		}
	}

	public void printOne(EmartMallVO vo) { // 상품 1개 출력
		System.out.println("번호 :"+vo.geteId()+"  상품명 :"+vo.geteName()+"  가격 :"+vo.getePrice()+
				"  구매횟수 :"+vo.geteReview()+"  카테고리 :"+vo.getCategory());
	}

	public void searchView() { //상품검색 안내
		System.out.println("====상품검색====");
	}

	public int inputPrice() { //금액입력,int로 반환
		System.out.print("금액입력 :");
		int price=sc.nextInt();
		return price;
	}

	public int inputNum() { //번호입력,int로 반환
		System.out.print("번호입력 :");
		int num=sc.nextInt();
		return num;
	}

	public String YorN() { //Y or N 모든 입력을 대문자로 바꿔서 검사
		while(true) {
			System.out.print("구매하시겠습니까?(Y/N) :");
			try {
				String ans=sc.next();
				if(!(ans.toUpperCase().equals("Y")||ans.toUpperCase().equals("N"))) {
					System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
					continue;
				}
				return ans.toUpperCase();

			}catch(Exception e) {
				System.out.println("문자열만 입력 가능합니다.");
			}
		}
	}

	public void mypageView(PersonVO pvo) { // 마이페이지
		while(true) {
			System.out.println(pvo.getName()+"님 어서오세요.");
			System.out.println("====마이페이지====");
			System.out.println("1.구매상품");
			System.out.println("2.비밀번호변경");
			System.out.println("3.회원탈퇴");
			System.out.println("4.나가기");
			System.out.print(">> ");
			try {
				action=sc.nextInt();
				if(action>=1&&action<=4) { //입력값 제한
					return;
				}
				System.out.println("범위 외 입력입니다. 다시 입력해주세요.");
			}catch(Exception e) {
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			}
		}
	}
	public void myProduct() { // 내가 구매한 상품
		System.out.println("===구매상품===");
		// 장바구니 시스템인데.. 
	}
	public void buyZero() {
		System.out.println("구매한 목록 없음...");
	}
	// 출력 로직 추가
	public void buyList(ArrayList<EmartMallVO> vo) {
		for (int i = 0; i < vo.size(); i++) {
			System.out.println(vo.get(i));
			
		}
	}
	public void changePw() { // 비밀번호 변경
		System.out.println("===비밀번호변경===");
	}
	public void deleteUser() { // 회원탈퇴
		System.out.println("===회원탈퇴===");
	}
	public void success() { // 성공
		System.out.println("수행성공!");
	}
	public void fail() { // 실패
		System.out.println("수행실패..");
	}
	public void insSucc() {
        System.out.println("구매목록에 등록되었습니다.");
    }
    public void insFail() {
        System.out.println("구매목록 등록에 실패하였습니다.");
    }

}
