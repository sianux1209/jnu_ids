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

class DevicesSelector extends JFrame implements ActionListener {

	PacketCaptureThread devices_pct;
	int x;
	
//	int index;
	//JpcapCaptor cap;
	NetworkInterface[] devices;
	String[] dnames;
	
	List<PcapIf> alldevs;
	StringBuilder errbuf;
	
	JList dlist;
	JButton ok;
	JButton cancel;	

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

		setTitle("디바이스 선택");
		
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


	public void actionPerformed(ActionEvent e) {
		
		
		JButton b = (JButton)e.getSource();
		
		if (b.getText().equals("Choose")) {
			int index = dlist.getSelectedIndex();
//			devices_pct.setDevice(dlist.getSelectedIndex(), devices);
			PcapIf device = alldevs.get(index);
			int snaplen = 64 * 1024;           // Capture all packets, no trucation 한번에 캡처가능한 byte 수 
		    int flags = Pcap.MODE_PROMISCUOUS; // capture all packets, NIC가 promiscuous mode로 동작할 지 여부
		    int timeout = 10 * 1000;           // 10 seconds in millis, millisecond 단위의 read timeout
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