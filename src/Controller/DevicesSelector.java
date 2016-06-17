package Controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

class DevicesSelector extends JFrame implements ActionListener {

	PacketCaptureThread devices_pct;
//	int index;
	//JpcapCaptor cap;
	NetworkInterface[] devices;
	String[] dnames;
	
	JList dlist;
	JButton ok;
	JButton cancel;	

	DevicesSelector(PacketCaptureThread main_packetcaptureThread) {
		devices_pct = main_packetcaptureThread;
		devices = JpcapCaptor.getDeviceList();
		
		dnames = new String[devices.length];
		for (int i = 0; i < devices.length; i++)
			dnames[i] = devices[i].description;

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
			//index = dlist.getSelectedIndex();
			devices_pct.setDevice(dlist.getSelectedIndex(), devices);
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