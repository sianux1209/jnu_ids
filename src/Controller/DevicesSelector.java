package Controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

/**
 * 패킷 캡쳐를 시작하기전에 네트워크 장치를 선택하는 역할
 * @author 김연수
 * @version 3.0
 */
class DevicesSelector extends JFrame implements ActionListener {

	PacketCaptureThread devices_pct;
	int x;
	
	NetworkInterface[] devices;
	String[] dnames;
	
	List<PcapIf> alldevs;
	StringBuilder errbuf;
	
	JList dlist;
	JButton ok;
	JButton cancel;	
	
	/**
	 * 네트워크 장치 선택 화면 생성자
	 * @param main_packetcaptureThread main_packetcaptureThread의 메소드를 호출하기 위함
	 */
	DevicesSelector(PacketCaptureThread main_packetcaptureThread) {
		
		
//////////////////		
		alldevs = new ArrayList<PcapIf>(); // Will be filled with NICs  
	    errbuf = new StringBuilder();     // For any error msgs  
	  
	    /*************************************************************************** 
	     * First get a list of devices on this system 
	     **************************************************************************/  
	    int r = Pcap.findAllDevs(alldevs, errbuf);  
	    
	    if (r == Pcap.NOT_OK || alldevs.isEmpty()) {  
	      System.err.printf("Can't read list of devices, error is %s\n",   
	        errbuf.toString());  
	      return;  
	    }  
	    
///////////////	    
		
		devices_pct = main_packetcaptureThread;
//		devices = JpcapCaptor.getDeviceList();
		
//		dnames = new String[devices.length];
		dnames = new String[alldevs.size()];
		for (int i = 0; i < alldevs.size(); i++)
//			dnames[i] = devices[i].description;
			dnames[i] = alldevs.get(i).getDescription();

		setTitle("장치 선택");
		
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		
		dlist = new JList(dnames);
		ok = new JButton("Choose");
		cancel = new JButton("Cancel");
		
		c.add(dlist);
		c.add(ok);
		c.add(cancel);
		
		ok.addActionListener(this);
		cancel.addActionListener(this);		
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension di = tk.getScreenSize();
		Dimension di1 = this.getSize();
		this.setLocation((int) (di.getWidth() / 2 - di1.getWidth() / 2),
				(int) (di.getHeight() / 2 - di1.getHeight() / 2));
		
		setSize(300,300);
		setVisible(true);
	}

	/**
	 * 선택한 장치의 index를 가지고 패킷캡쳐를 위한 Jnetpcap의 장치연결
	 */
	public void actionPerformed(ActionEvent e) {
		
		
		JButton b = (JButton)e.getSource();
		
		if (b.getText().equals("Choose")) {
			int index = dlist.getSelectedIndex();
//			devices_pct.setDevice(dlist.getSelectedIndex(), devices);
			PcapIf device = alldevs.get(index);
			int snaplen = 64 * 1024;           
		    int flags = Pcap.MODE_PROMISCUOUS; 
		    int timeout = 10 * 1000;           
		    Pcap pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);  
		    if (pcap == null) {  
		      System.err.printf("Error while opening device for capture: %s\n",   
		        errbuf.toString());  
		      return;  
		    }  
			
			devices_pct.setDevice(pcap);
			devices_pct.start();
//			devices_pct.run();
			setVisible(false);	
		} 
		
		else {
			setVisible(false);
			dispose();
		}
	}
	

}