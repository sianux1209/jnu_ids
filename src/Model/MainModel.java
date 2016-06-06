//
//
//  @ Project : JNU_IDS
//  @ Date : 2016-05-11
//  @ Author : Ã¤µÎ°É, ±è¹ÎÁø, ±è¿¬¼ö, Á¤Âù¿ì, ÃÖ¹ÎÁ¤
//
//


package Model;

//¸ŞÀÎ ¸ğµ¨
public class MainModel {
	private static View.MainView mainView;

	
	public static void main(String[] args){
		
		setMainModel();
		
	}
	
	
	public static void setMainModel() {
		View.MainView mainView = new View.MainView();
	}
}
