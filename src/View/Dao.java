package View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author 김민진 최민정
 * @serial 2016.06.18 각 ip, protocol, port, domain을 각 텍스트 파일에 넣어준다.
 *        
*/

public class Dao {	
	private static Dao instance = new Dao();
	private Dao() {}
	
	public static Dao getInstance() {
		return instance;
	}
	/**
	* <pre>
	*  메소드명 :ip 추출
	*  작성일 : 2016.06.18
	*  작성자 : 김민진 최민정
	*/
	public static ArrayList<IpDto> getIp() {
		File file = new File(".\\rsc\\ipList.txt");
		FileReader fileReader = null;
		BufferedReader reader = null;
		ArrayList<IpDto> dtos = new ArrayList<IpDto>();
		try {
			fileReader = new FileReader(file);
			reader = new BufferedReader(fileReader);
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				String ip = line;
				IpDto dto = new IpDto(ip);
				dtos.add(dto);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) reader.close();
				if (fileReader != null) fileReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dtos;
	}
	
	/**
	 * <pre>
	 * 메소드명 : 포트와 프로토콜을 얻는 메소드
	 * 작성일 : 2016.06.18
	 * 사용법 : 포트 - Dao.getInstance().getPortProtocol().get(i).getPort();
	 * 		  프로토콜 - Dao.getInstance().getPortProtocol().get(i).getProtocol();
	 * @return 
	 */
	public static ArrayList<PortProtocolDto> getPortProtocol() {
		File file = new File(".\\rsc\\portProtocolList.txt");
		FileReader fileReader = null;
		BufferedReader reader = null;
		ArrayList<PortProtocolDto> dtos = new ArrayList<PortProtocolDto>();
		try {
			fileReader = new FileReader(file);
			reader = new BufferedReader(fileReader);
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] info = line.split(",");
				String protocol = info[0];
				String port = info[1];
				
				PortProtocolDto dto = new PortProtocolDto(port, protocol);
				dtos.add(dto);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) reader.close();
				if (fileReader != null) fileReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dtos;
	}

	
	/**
	 * <pre>
	 * 메소드명 : 포트와 프로토콜을 얻는 메소드
	 * 작성일 : 2016.06.18
	 * 사용법 : Url 추출 - Dao.getInstance().getUrl().get(i).getUrl();
	 * @return 
	 */
	public static ArrayList<UrlDto> getUrl() {
		File file = new File(".\\rsc\\urlList.txt");
		FileReader fileReader = null;
		BufferedReader reader = null;
		ArrayList<UrlDto> dtos = new ArrayList<UrlDto>();
		try {
			fileReader = new FileReader(file);
			reader = new BufferedReader(fileReader);
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				String url = line;
				
				UrlDto dto = new UrlDto(url);
				dtos.add(dto);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) reader.close();
				if (fileReader != null) fileReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dtos;
	}
	
	
	/**
	 * <pre>
	 * 메소드명 : 포트와 프로토콜을 얻는 메소드
	 * 작성일 : 2016.06.18
	 * 사용법 : IP를 ipList.txt에 저장 ;
	 * @return 
	 */
	public static void saveIp(ArrayList<IpDto> dtos) {
		File file = new File(".\\rsc\\ipList.txt");
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			for (IpDto dto : dtos)
			writer.write(dto.getIp() + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		

	}
	
	
	/**
	 * <pre>
	 * 메소드명 : 포트와 프로토콜을 얻는 메소드
	 * 작성일 : 2016.06.18
	 * 사용법 : port번호랑 protocol을 txt에 저장
	 * @return 
	 */
	public static void savePortProtocol(ArrayList<PortProtocolDto> dtos) {
		File file = new File(".\\rsc\\portProtocolList.txt");
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			for (PortProtocolDto dto : dtos)
			writer.write(dto.getPort() + "," + dto.getProtocol() + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		

	}
	
	
	/**
	 * <pre>
	 * 메소드명 : 포트와 프로토콜을 얻는 메소드
	 * 작성일 : 2016.06.18
	 * 사용법 : Url을 txt파일에 저장
	 * @return 
	 */
	public static void saveUrl(ArrayList<UrlDto> dtos) {
		File file = new File(".\\rsc\\urlList.txt");
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			for (UrlDto dto : dtos)
			writer.write(dto.getUrl() + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		

	}
}
