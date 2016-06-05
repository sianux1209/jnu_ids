package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import Packet.Packet_Table;
import jpcap.JpcapCaptor;
import jpcap.packet.ARPPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

/**
 * @author 정찬우
 * @version 1.0
 * @since 2016.05.30
 */

public class PacketCaptureThread extends Thread{
	
	/**
	 * Capturing Packet starts using the respective threads. 
	 */
	
	int i = 0;
	JpcapCaptor captor;
    jpcap.NetworkInterface[] list;
	Packet_Table packet_table;
    int x, nChoice;
    
    /**
     * Thread Start
     */
	public void run(){
		
		Vector<Packet_Table> Capturedpacket = new Vector<Packet_Table>();
		
		Capturedpacket.add(new Packet_Table());
		
		// 사용가능한 디바이스 장치 리스트 검색
        list = JpcapCaptor.getDeviceList();
        System.out.println("Available interfaces: ");
        
        for(x=0; x<list.length; x++) {
            System.out.println(x+" -> "+list[x].description);  
        }
        
        System.out.println("-------------------------\n");
        //nChoice = Integer.parseInt(getInput("Choose interface (0,1..): "));
        nChoice = 0;
        // 장치 선택 후 입력 받음
        System.out.println("Listening on interface -> "+list[nChoice].description);
        System.out.println("-------------------------\n");
        
		/*Setup device listener */
        try {
        	// JpcapCaptor.openDevice(네트워크 인터페이스, 한번에 캡처가능한 byte 수, true면 promisc모드, processPacket()의 Timeout)
        	// openDevice 메소드를 통해 패킷 캡쳐를 활성화 시키고, loopPackt으로 패킷이 캡쳐되었을때 어떠한 행동을 할지 지정이 가능
            
        	captor=JpcapCaptor.openDevice(list[nChoice], 65535, false, 20);
            // listen for TCP/IP only 
            captor.setFilter("", true);
        } catch(IOException ioe) { ioe.printStackTrace(); }
        
		while(true){
			 Packet info = captor.getPacket(); 
	            
	            //불러온 패킷이 널이 아니면 출력
	            if(info != null){
	            	Packet_Table tmp  = new Packet_Table();
	                getPacketText(info, tmp);
	                
	                Capturedpacket.add(tmp);
	                
	                
	                System.out.println("\n");
	                System.out.print("packet_type : " + tmp.getPacketResource("packet_type")
	                		+ "\nsrc_ip : " + tmp.getPacketResource("src_ip")
	                		+ "\nsrc_mac : " + tmp.getPacketResource("src_mac")
	                		+ "\nsrc_port : " + tmp.getPacketResource("src_port")
	                		+ "\ndst_ip : " + tmp.getPacketResource("dst_ip")
	                		+ "\ndst_mac : " + tmp.getPacketResource("dst_mac")
	                		+ "\ndst_port : " + tmp.getPacketResource("dst_port")
	                		+ "\narrival_time : " + tmp.getPacketResource("arrival_time")
	                		+ "\nprotocol_number : " + tmp.getPacketResource("protocol_number")
	                		+ "\nprotocol_name : " + tmp.getPacketResource("protocol_name")
	                		+ "\nevent_number : " + tmp.getPacketResource("event_number")
	                		+ "\nevent_name : " + tmp.getPacketResource("event_name")
	                		+ "\nrisk : " + tmp.getPacketResource("risk\n")
	                		);
	                
	                //System.out.print(sData);
	            }
			
		}
	}
	
	/**
	 * get user input
	 * @param q		input String
	 * @return		into value of String
	 */
    public static String getInput(String q) 
    {
        String input = "";
        System.out.print(q);
        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(System.in));
        try {
            input = bufferedreader.readLine();
        } catch(IOException ioexception) { }
        
      return input;
    }
    
	/**
	 * Each packet are distinguished.
	 * @param pack	Captured Packet
	 * @param tmp	Packet_Table
	 * @return		Packet_Table
	 */
	Packet_Table getPacketText(Packet pack, Packet_Table tmp)
    {   
		
        int i=0,j=0;
        
        // Data (Payload)만 출력
        byte[] bytes=new byte[pack.data.length];
        System.arraycopy(pack.data, 0, bytes, 0, pack.data.length);
        
        //--------------------------------------------------------------------
        // 캐스팅한번하면 Packet  클래스가 깨진다. 왜그런지는 모르겠다. --> 패키지특징내지 버그같음..
        //--------------------------------------------------------------------
        
        if(pack instanceof TCPPacket){
        	SimpleDateFormat d_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
        	
        	if( pack.data.length > 0){ // 데이터가 있을 경우 http
        		String[] mactmp, macttmp, strtmp;
        		String result = "";
        		int[] inttmp;
        		
        		tmp.setPacket("packet_type", "TCPPacket");
        		tmp.setPacket("arrival_time", d_format.format((Calendar.getInstance().getTime())));
        		if(((TCPPacket)pack).src_ip != null)
        			tmp.setPacket("src_ip", ((TCPPacket)pack).src_ip.toString().substring(1, (((TCPPacket)pack).src_ip.toString().length())));
        		tmp.setPacket("src_port", Integer.toString(((TCPPacket)pack).src_port));
        		if(((TCPPacket)pack).dst_ip != null)
        			tmp.setPacket("dst_ip", ((TCPPacket)pack).dst_ip.toString().substring(1, (((TCPPacket)pack).dst_ip.toString().length())));
        		tmp.setPacket("dst_port", Integer.toString(((TCPPacket)pack).dst_port));
        		tmp.setPacket("protocol_number", Short.toString(((TCPPacket)pack).protocol));
        		tmp.setPacket("protocol_name", "TCP");
        		if(tmp.getPacketResource("dst_port").compareTo("80") == 0 | tmp.getPacketResource("src_port").compareTo("80") == 0){
        			tmp.setPacket("protocol_name", "HTTP");
        		}
        		
        		
        		inttmp = new int[((EthernetPacket)pack.datalink).src_mac.length];
        		strtmp = new String[((EthernetPacket)pack.datalink).src_mac.length];
        		mactmp = new String[((EthernetPacket)pack.datalink).src_mac.length];
        		for(i = 0 ; i < ((EthernetPacket)pack.datalink).src_mac.length ; i++ ){
        			inttmp[i] = ((EthernetPacket)pack.datalink).src_mac[i]&0xFF;
        			strtmp[i] = String.format("%02X", inttmp[i]);
        			if(i == inttmp.length-1){
        				mactmp[i] = strtmp[i].toString();
        				break;
        			}
        			mactmp[i] = strtmp[i].toString()+":";
        		}
        		for(i = 0 ; i < inttmp.length ; i++ ){
        			result += mactmp[i];
        		}
        		tmp.setPacket("src_mac", result);
        		
        		result = "";
        		inttmp = new int[((EthernetPacket)pack.datalink).dst_mac.length];
        		strtmp = new String[((EthernetPacket)pack.datalink).dst_mac.length];
        		mactmp = new String[((EthernetPacket)pack.datalink).dst_mac.length];
        		for(i = 0 ; i < ((EthernetPacket)pack.datalink).dst_mac.length ; i++ ){
        			inttmp[i] = ((EthernetPacket)pack.datalink).dst_mac[i]&0xFF;
        			strtmp[i] = String.format("%02X", inttmp[i]);
        			if(i == inttmp.length-1){
        				mactmp[i] = strtmp[i].toString();
        				break;
        			}
        			mactmp[i] = strtmp[i].toString()+":";
        		}
        		for(i = 0 ; i < inttmp.length ; i++ ){
        			result += mactmp[i];
        		}
        		tmp.setPacket("dst_mac", result);
        	}
        	else{
        		String[] mactmp, macttmp, strtmp;
        		String result = "";
        		int[] inttmp;
        		
        		tmp.setPacket("packet_type", "TCPPacket");
        		tmp.setPacket("arrival_time", d_format.format((Calendar.getInstance().getTime())));
        		tmp.setPacket("src_ip", ((TCPPacket)pack).src_ip.toString().substring(1, (((TCPPacket)pack).src_ip.toString().length())));
        		tmp.setPacket("src_port", Integer.toString(((TCPPacket)pack).src_port));
        		tmp.setPacket("dst_ip", ((TCPPacket)pack).dst_ip.toString().substring(1, (((TCPPacket)pack).dst_ip.toString().length())));
        		tmp.setPacket("dst_port", Integer.toString(((TCPPacket)pack).dst_port));
        		tmp.setPacket("protocol_number", Short.toString(((TCPPacket)pack).protocol));
        		tmp.setPacket("protocol_name", "TCP");
        		if(tmp.getPacketResource("dst_port") == "80"){
        			tmp.setPacket("protocol_name", "HTTP");
        		}
        		
        		
        		inttmp = new int[((EthernetPacket)pack.datalink).src_mac.length];
        		strtmp = new String[((EthernetPacket)pack.datalink).src_mac.length];
        		mactmp = new String[((EthernetPacket)pack.datalink).src_mac.length];
        		for(i = 0 ; i < ((EthernetPacket)pack.datalink).src_mac.length ; i++ ){
        			inttmp[i] = ((EthernetPacket)pack.datalink).src_mac[i]&0xFF;
        			strtmp[i] = String.format("%02X", inttmp[i]);
        			if(i == inttmp.length-1){
        				mactmp[i] = strtmp[i].toString();
        				break;
        			}
        			mactmp[i] = strtmp[i].toString()+":";
        		}
        		for(i = 0 ; i < inttmp.length ; i++ ){
        			result += mactmp[i];
        		}
        		tmp.setPacket("src_mac", result);
        		
        		result = "";
        		inttmp = new int[((EthernetPacket)pack.datalink).dst_mac.length];
        		strtmp = new String[((EthernetPacket)pack.datalink).dst_mac.length];
        		mactmp = new String[((EthernetPacket)pack.datalink).dst_mac.length];
        		for(i = 0 ; i < ((EthernetPacket)pack.datalink).dst_mac.length ; i++ ){
        			inttmp[i] = ((EthernetPacket)pack.datalink).dst_mac[i]&0xFF;
        			strtmp[i] = String.format("%02X", inttmp[i]);
        			if(i == inttmp.length-1){
        				mactmp[i] = strtmp[i].toString();
        				break;
        			}
        			mactmp[i] = strtmp[i].toString()+":";
        		}
        		for(i = 0 ; i < inttmp.length ; i++ ){
        			result += mactmp[i];
        		}
        		tmp.setPacket("dst_mac", result);
        	}
        }
        else if(pack instanceof UDPPacket){
        	SimpleDateFormat d_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
        	
        	if( pack.data.length > 0){
        		String[] mactmp, macttmp, strtmp;
        		String result = "";
        		int[] inttmp;
        		
        		
        		tmp.setPacket("packet_type", "UDPPacket");
        		tmp.setPacket("arrival_time", d_format.format((Calendar.getInstance().getTime())));
        		tmp.setPacket("src_ip", ((UDPPacket)pack).src_ip.toString().substring(1, (((UDPPacket)pack).src_ip.toString().length())));
        		tmp.setPacket("src_port", Integer.toString(((UDPPacket)pack).src_port));
        		tmp.setPacket("dst_ip", ((UDPPacket)pack).dst_ip.toString().substring(1, (((UDPPacket)pack).dst_ip.toString().length())));
        		tmp.setPacket("dst_port", Integer.toString(((UDPPacket)pack).dst_port));
        		tmp.setPacket("protocol_number", Short.toString(((UDPPacket)pack).protocol));
        		tmp.setPacket("protocol_name", "UDP");
        		
        		inttmp = new int[((EthernetPacket)pack.datalink).src_mac.length];
        		strtmp = new String[((EthernetPacket)pack.datalink).src_mac.length];
        		mactmp = new String[((EthernetPacket)pack.datalink).src_mac.length];
        		for(i = 0 ; i < ((EthernetPacket)pack.datalink).src_mac.length ; i++ ){
        			inttmp[i] = ((EthernetPacket)pack.datalink).src_mac[i]&0xFF;
        			strtmp[i] = String.format("%02X", inttmp[i]);
        			if(i == inttmp.length-1){
        				mactmp[i] = strtmp[i].toString();
        				break;
        			}
        			mactmp[i] = strtmp[i].toString()+":";
        		}
        		for(i = 0 ; i < inttmp.length ; i++ ){
        			result += mactmp[i];
        		}
        		tmp.setPacket("src_mac", result);
        		
        		result = "";
        		inttmp = new int[((EthernetPacket)pack.datalink).dst_mac.length];
        		strtmp = new String[((EthernetPacket)pack.datalink).dst_mac.length];
        		mactmp = new String[((EthernetPacket)pack.datalink).dst_mac.length];
        		for(i = 0 ; i < ((EthernetPacket)pack.datalink).dst_mac.length ; i++ ){
        			inttmp[i] = ((EthernetPacket)pack.datalink).dst_mac[i]&0xFF;
        			strtmp[i] = String.format("%02X", inttmp[i]);
        			if(i == inttmp.length-1){
        				mactmp[i] = strtmp[i].toString();
        				break;
        			}
        			mactmp[i] = strtmp[i].toString()+":";
        		}
        		for(i = 0 ; i < inttmp.length ; i++ ){
        			result += mactmp[i];
        		}
        		tmp.setPacket("dst_mac", result);
        		
        	}
        }
        else if(pack instanceof ICMPPacket){
        	SimpleDateFormat d_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
        	
        	if( pack.data.length > 0){
        		String[] mactmp, macttmp, strtmp;
        		String result = "";
        		int[] inttmp;
        		
        		tmp.setPacket("packet_type", "ICMPPacket");
        		tmp.setPacket("arrival_time", d_format.format((Calendar.getInstance().getTime())));
        		tmp.setPacket("src_ip", ((ICMPPacket)pack).src_ip.toString().substring(1, (((ICMPPacket)pack).src_ip.toString().length())));
        		tmp.setPacket("dst_ip", ((ICMPPacket)pack).dst_ip.toString().substring(1, (((ICMPPacket)pack).dst_ip.toString().length())));
        		tmp.setPacket("protocol_number", Short.toString(((ICMPPacket)pack).protocol));
        		tmp.setPacket("protocol_name", "ICMP");
        		
        		inttmp = new int[((EthernetPacket)pack.datalink).src_mac.length];
        		strtmp = new String[((EthernetPacket)pack.datalink).src_mac.length];
        		mactmp = new String[((EthernetPacket)pack.datalink).src_mac.length];
        		for(i = 0 ; i < ((EthernetPacket)pack.datalink).src_mac.length ; i++ ){
        			inttmp[i] = ((EthernetPacket)pack.datalink).src_mac[i]&0xFF;
        			strtmp[i] = String.format("%02X", inttmp[i]);
        			if(i == inttmp.length-1){
        				mactmp[i] = strtmp[i].toString();
        				break;
        			}
        			mactmp[i] = strtmp[i].toString()+":";
        		}
        		for(i = 0 ; i < inttmp.length ; i++ ){
        			result += mactmp[i];
        		}
        		tmp.setPacket("src_mac", result);
        		
        		result = "";
        		inttmp = new int[((EthernetPacket)pack.datalink).dst_mac.length];
        		strtmp = new String[((EthernetPacket)pack.datalink).dst_mac.length];
        		mactmp = new String[((EthernetPacket)pack.datalink).dst_mac.length];
        		for(i = 0 ; i < ((EthernetPacket)pack.datalink).dst_mac.length ; i++ ){
        			inttmp[i] = ((EthernetPacket)pack.datalink).dst_mac[i]&0xFF;
        			strtmp[i] = String.format("%02X", inttmp[i]);
        			if(i == inttmp.length-1){
        				mactmp[i] = strtmp[i].toString();
        				break;
        			}
        			mactmp[i] = strtmp[i].toString()+":";
        		}
        		for(i = 0 ; i < inttmp.length ; i++ ){
        			result += mactmp[i];
        		}
        		tmp.setPacket("dst_mac", result);
        	}
        }
        else if(pack instanceof ARPPacket){
        	SimpleDateFormat d_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
        	
        	if( pack.header.length > 0){
        		String[] mactmp, macttmp, strtmp;
        		String result = "";
        		int[] inttmp;
        		
        		tmp.setPacket("packet_type", "ARPPacket");
        		tmp.setPacket("arrival_time", d_format.format((Calendar.getInstance().getTime())));
        		tmp.setPacket("protocol_name", "ARP");
        		
        		
        		inttmp = new int[((EthernetPacket)pack.datalink).src_mac.length];
        		strtmp = new String[((EthernetPacket)pack.datalink).src_mac.length];
        		mactmp = new String[((EthernetPacket)pack.datalink).src_mac.length];
        		for(i = 0 ; i < ((EthernetPacket)pack.datalink).src_mac.length ; i++ ){
        			inttmp[i] = ((EthernetPacket)pack.datalink).src_mac[i]&0xFF;
        			strtmp[i] = String.format("%02X", inttmp[i]);
        			if(i == inttmp.length-1){
        				mactmp[i] = strtmp[i].toString();
        				break;
        			}
        			mactmp[i] = strtmp[i].toString()+":";
        		}
        		for(i = 0 ; i < inttmp.length ; i++ ){
        			result += mactmp[i];
        		}
        		tmp.setPacket("src_mac", result);
        		
        		result = "";
        		inttmp = new int[((EthernetPacket)pack.datalink).dst_mac.length];
        		strtmp = new String[((EthernetPacket)pack.datalink).dst_mac.length];
        		mactmp = new String[((EthernetPacket)pack.datalink).dst_mac.length];
        		for(i = 0 ; i < ((EthernetPacket)pack.datalink).dst_mac.length ; i++ ){
        			inttmp[i] = ((EthernetPacket)pack.datalink).dst_mac[i]&0xFF;
        			strtmp[i] = String.format("%02X", inttmp[i]);
        			if(i == inttmp.length-1){
        				mactmp[i] = strtmp[i].toString();
        				break;
        			}
        			mactmp[i] = strtmp[i].toString()+":";
        		}
        		for(i = 0 ; i < inttmp.length ; i++ ){
        			result += mactmp[i];
        		}
        		tmp.setPacket("dst_mac", result);
        		
        		
        		result = "";
        		inttmp = new int[((ARPPacket) pack).sender_protoaddr.length];
        		strtmp = new String[((ARPPacket) pack).sender_protoaddr.length];
        		mactmp = new String[((ARPPacket) pack).sender_protoaddr.length];
        		for(i = 0 ; i < ((ARPPacket) pack).sender_protoaddr.length ; i++ ){
        			inttmp[i] = ((ARPPacket) pack).sender_protoaddr[i]&0xFF;
        			strtmp[i] = String.format("%d", inttmp[i]);
        			if(i == inttmp.length-1){
        				mactmp[i] = strtmp[i].toString();
        				break;
        			}
        			mactmp[i] = strtmp[i].toString()+".";
        		}
        		for(i = 0 ; i < inttmp.length ; i++ ){
        			result += mactmp[i];
        		}
        		tmp.setPacket("src_ip", result);
        		
        		result = "";
        		inttmp = new int[((ARPPacket) pack).target_protoaddr.length];
        		strtmp = new String[((ARPPacket) pack).target_protoaddr.length];
        		mactmp = new String[((ARPPacket) pack).target_protoaddr.length];
        		for(i = 0 ; i < ((ARPPacket) pack).target_protoaddr.length ; i++ ){
        			inttmp[i] = ((ARPPacket) pack).target_protoaddr[i]&0xFF;
        			strtmp[i] = String.format("%d", inttmp[i]);
        			if(i == inttmp.length-1){
        				mactmp[i] = strtmp[i].toString();
        				break;
        			}
        			mactmp[i] = strtmp[i].toString()+".";
        		}
        		for(i = 0 ; i < inttmp.length ; i++ ){
        			result += mactmp[i];
        		}
        		tmp.setPacket("dst_ip", result);
        	}
        }
        else if(pack instanceof IPPacket){
        	SimpleDateFormat d_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
        	
        	if( pack.data.length > 0){
        		String[] mactmp, macttmp, strtmp;
        		String result = "";
        		int[] inttmp;
        		
        		tmp.setPacket("packet_type", "IPPacket");
        		tmp.setPacket("arrival_time", d_format.format((Calendar.getInstance().getTime())));
        		tmp.setPacket("src_ip", ((IPPacket)pack).src_ip.toString().substring(1, (((IPPacket)pack).src_ip.toString().length())));
        		tmp.setPacket("dst_ip", ((IPPacket)pack).dst_ip.toString().substring(1, (((IPPacket)pack).dst_ip.toString().length())));
        		tmp.setPacket("protocol_number", Short.toString(((IPPacket)pack).protocol));
        		tmp.setPacket("protocol_name", "ICMP");
        		
        		
        		inttmp = new int[((EthernetPacket)pack.datalink).src_mac.length];
        		strtmp = new String[((EthernetPacket)pack.datalink).src_mac.length];
        		mactmp = new String[((EthernetPacket)pack.datalink).src_mac.length];
        		for(i = 0 ; i < ((EthernetPacket)pack.datalink).src_mac.length ; i++ ){
        			inttmp[i] = ((EthernetPacket)pack.datalink).src_mac[i]&0xFF;
        			strtmp[i] = String.format("%02X", inttmp[i]);
        			if(i == inttmp.length-1){
        				mactmp[i] = strtmp[i].toString();
        				break;
        			}
        			mactmp[i] = strtmp[i].toString()+":";
        		}
        		for(i = 0 ; i < inttmp.length ; i++ ){
        			result += mactmp[i];
        		}
        		tmp.setPacket("src_mac", result);
        		
        		result = "";
        		inttmp = new int[((EthernetPacket)pack.datalink).dst_mac.length];
        		strtmp = new String[((EthernetPacket)pack.datalink).dst_mac.length];
        		mactmp = new String[((EthernetPacket)pack.datalink).dst_mac.length];
        		for(i = 0 ; i < ((EthernetPacket)pack.datalink).dst_mac.length ; i++ ){
        			inttmp[i] = ((EthernetPacket)pack.datalink).dst_mac[i]&0xFF;
        			strtmp[i] = String.format("%02X", inttmp[i]);
        			if(i == inttmp.length-1){
        				mactmp[i] = strtmp[i].toString();
        				break;
        			}
        			mactmp[i] = strtmp[i].toString()+":";
        		}
        		for(i = 0 ; i < inttmp.length ; i++ ){
        			result += mactmp[i];
        		}
        		tmp.setPacket("dst_mac", result);
        	}
        }
        return tmp;
    }
	
	/**
	 * It outputs the data of packet to the hexa value.
	 * @param nSize		data of packet size
	 * @param pData		data of packet value
	 * @return			string
	 */
	// Hex 출력
    private String ShowHex(int nSize, byte[] pData)
    {
        final int DISPLAY_COUNT = 16;
        final int MAX_SIZE      = 1024 * 15; // 최대보여지는개수 
        
        String msg      = "";
        String sTmp     = "";
        String sTmp2    = "";
        String sLineNum = "";
     
        int nLineNum = 0;
         
        msg  = "0000:"; 
        
        if(nSize > MAX_SIZE) nSize = MAX_SIZE; 
                
        for(int i = 1; i < nSize + 1; i++){
            sTmp  = sTmp.format("%02X ", pData[ i - 1 ] );
            if(( pData[ i - 1] < 127) && ( pData[ i - 1] > 32)){
                sTmp2 += sTmp2.format("%c", pData[ i - 1 ] );
         
            } else {
                sTmp2 += ".";
            }
            
            if(i % DISPLAY_COUNT  == 0 && i != 1){
                msg += sTmp;
             
                msg += " ";
                msg += sTmp2;
             
                msg += "\r\n";
             
                sTmp2 = "";
                
                msg += sLineNum.format("%04X:", nLineNum += DISPLAY_COUNT); 
                
            } else if(i == nSize && i % DISPLAY_COUNT != 0 ){ 
                int nEmtSpace = DISPLAY_COUNT - (i % DISPLAY_COUNT);
                for(int j = 0; j < nEmtSpace; j++){
                 sTmp += "   "; 
                }
                msg += sTmp;
                msg += " ";
                msg += sTmp2;
                msg += "\r\n";

            } else { 
                msg += sTmp;
            } 
        }
     
        if ( nSize == 0 ) msg = "";
        return msg;
    }
}
