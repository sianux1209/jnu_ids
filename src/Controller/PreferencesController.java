//
//
//  @ Project : JNU_IDS
//  @ Date : 2016-05-11
//  @ Author : ä�ΰ�, �����, �迬��, ������, �ֹ���
//
//


package Controller;

public class PreferencesController {

	private static String defaultPassword = "admin";

	public boolean confirmPassword() {

		String userInput = ""; // �Է�

		// ��ȣ�� ������
		if (defaultPassword == userInput) {

			return true;
		} else {
			return false;
		}

	}

	// ȯ�漳�� ���Ͽ� �Է�
	public void setPreferences() {

	}

	// ȯ�漳�� �ҷ�����
	public void getPreferences() {

	}
}
