

package Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import View.MainView;

/**
 * @author 정찬우
 * @version 1.0
 * @serial 2016.05.30
 */

public class MainController {

	/**
	 * @ MainController @ added main method
	 */
/*
	private static boolean condition;

	public static boolean getCondition() {
		return condition;
	}

	public static void setCondition(boolean condition) {
		MainController.condition = condition;
	}
*/
	MainView mainView;

	public PacketCaptureThread main_packetcaptureThread;
	public RulesetController main_rulesetController;

	public MainController(View.MainView mainView) {
		this.mainView = mainView;
//		main_packetcaptureThread = new PacketCaptureThread(mainView);

	}

	/**
	 * @author 정찬우
	 * @since 2016.05.30
	 * @param contdition
	 *            시작과 정지를 컨트롤할 플래그
	 */
	public void main(boolean contdition) {
		if (contdition){

			startCapture();
		}
		else
			stopCapture();
	}

	/**
	 * Start Packet Capture Set condition value is true
	 * 
	 * @return Value Of String
	 */
	public String startCapture() {

		main_rulesetController = new RulesetController(mainView);
		main_packetcaptureThread = new PacketCaptureThread(mainView, main_rulesetController);
		new DevicesSelector(main_packetcaptureThread);// 디바이스 선택

		String value = "";
		return value;
	}

	/**
	 * Stop Packet Capture Set condition value is false
	 * 
	 * @return Value Of String
	 */
	public String stopCapture() {
		
		main_packetcaptureThread.stopCapture();
		main_packetcaptureThread.interrupt();
		String value = "";
		return value;
	}
	
	public PacketCaptureThread get_main() {

		return main_packetcaptureThread;
	}
}
