//
//
//  @ Project : JNU_IDS
//  @ Date : 2016-05-11
//  @ Author : ä�ΰ�, �����, �迬��, ������, �ֹ���
//
//

package Model;

//���� ��
public class MainModel {
	private static View.MainView mainView;
	private static Controller.MainController mainController;

	
	public static void main(String[] args){
		
		setMainModel();
		
	}
	
	
	public static void setMainModel() {
		View.MainView mainView = new View.MainView();
	}
}
