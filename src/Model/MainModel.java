//
//
//  @ Project : JNU_IDS
//  @ Date : 2016-05-11
//  @ Author : 채두걸, 김민진, 김연수, 정찬우, 최민정
//
//


package Model;

//메인 모델
public class MainModel {
	private static View.MainView mainView;

	
	public static void main(String[] args){
		
		setMainModel();
		
	}
	
	
	public static void setMainModel() {
		View.MainView mainView = new View.MainView();
	}
}
