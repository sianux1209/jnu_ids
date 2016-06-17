//
//
//  @ Project : JNU_IDS
//  @ Date : 2016-05-11
//  @ Author : 채두걸, 김민진, 김연수, 정찬우, 최민정
//
//


package View;

import java.awt.*;
import javax.swing.*;
/**
 * 
 * @author 채두걸
 * @since 2016.06.18
 * @version 3.0
 *
 */
public class AnalysesView {
	private JFrame jFrame;
	private Container container;
	/**
	 * AnalysesView 생성자
	 */
	public void setFrame() {

	}
	/**
	 * getImageResource
	 * @param imageIndex
	 * 					input String
	 * @return
	 * 					return ImageIcon
	 */
	public ImageIcon getImageResource(String imageIndex) {

		ImageIcon image = new ImageIcon(imageIndex);

		return image;
	}
}
