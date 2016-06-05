//
//
//  @ Project : JNU_IDS
//  @ Date : 2016-05-11
//  @ Author : 채두걸, 김민진, 김연수, 정찬우, 최민정
//
//

package Controller;

/**
 * @author 정찬우
 * @version 1.0
 * @serial 2016.05.30
 */

public class MainController {
	
	/**
	 * @ MainController
	 * @ added main method 
	 */
	
	private boolean condition;
	
	private PacketCaptureThread main_packetcaptureThread = new PacketCaptureThread();
	
	
	/**
	 * @author 정찬우
	 * @since 2016.05.30
	 * @param contdition	시작과 정지를 컨트롤할 플래그
	 */
	public void main(boolean contdition){
		if(contdition)
			startCapture();
		else
			stopCapture();
	}

	/**
	 * Start Packet Capture
	 * Set condition value is true
	 * @return	Value Of String
	 */
	public String startCapture() {

		condition = true;
		
		main_packetcaptureThread.start();
		
		String value = "";
		return value;
	}

	
	/**
	 * Stop Packet Capture
	 * Set condition value is false
	 * @return	Value Of String
	 */
	public String stopCapture() {

		condition = false;
		
		main_packetcaptureThread.stop();
		
		String value = "";
		return value;
	}
}
