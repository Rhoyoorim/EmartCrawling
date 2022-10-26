package view;

public class Test {
	public static void main(String[] args) {
		String str1="https://emart.ssg.com/category/main.ssg?dispCtgId=6000095739"; //과일
		String str2="https://emart.ssg.com/category/main.ssg?dispCtgId=6000095740";//채소
		String str3="https://emart.ssg.com/category/main.ssg?dispCtgId=6000095499"; //정육
		String str4="https://emart.ssg.com/category/main.ssg?dispCtgId=6000095500"; //수산물
		String str5="https://emart.ssg.com/category/main.ssg?dispCtgId=6000095505"; //과자
		
		View view=new View();

//	
			view.Crawling(str1,101);	
			view.Crawling(str2,201);
			view.Crawling(str3,301);
			view.Crawling(str4,401);
			view.Crawling(str5,501);

	}
}
