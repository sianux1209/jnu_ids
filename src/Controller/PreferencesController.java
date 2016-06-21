//
//
//  @ Project : JNU_IDS
//  @ Date : 2016-05-11
//  @ Author : 채두걸
//
//


package Controller;

public class PreferencesController {

	private static String defaultPassword = "admin";

	public boolean confirmPassword() {

		String userInput = ""; // 입력

		// 암호가 맞으면
		if (defaultPassword == userInput) {

			return true;
		} else {
			return false;
		}

	}

	// 환경설정 파일에 입력
	public void setPreferences() {

	}

	// 환경설정 불러오기
	public void getPreferences() {

	}
}
