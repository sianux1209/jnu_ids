package View;

import java.util.ArrayList;
import java.util.List;
/**
 * @author 김민진 최민정
 * @serial 2016.06.18 port와 protocol 객체의 속성값을 가져온다.
 *        
*/
public class PortProtocolDto {
	private String port;
	private String protocol;
	
	
	public PortProtocolDto(String port, String protocol) {
		this.port = port;
		this.protocol = protocol;
	}
	
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol=protocol;
	}
	
	
}
