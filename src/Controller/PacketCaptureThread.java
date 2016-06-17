package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import org.jnetpcap.JCaptureHeader;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.JPacketHandler;
import org.jnetpcap.protocol.lan.Ethernet;
import org.jnetpcap.protocol.network.Arp;
import org.jnetpcap.protocol.network.Icmp;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.network.Ip6;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;

import Packet.Packet_Table;
import View.MainView;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
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
 * 
 * @author 정찬우
 * @version 3.0
 * @since 2016.06.17
 */

public class PacketCaptureThread extends Thread {

	/**
	 * Capturing Packet starts using the respective threads.
	 */

	int i = 0;
	int x, index;
	// NetworkInterface[] devices;
	// JpcapCaptor captor;

	Pcap pcap;
	StringBuilder errbuf;
	List<PcapIf> alldevs;

	ArrayList<Packet_Table> Capturedpacket;
	MainView mainView;
	HashSet<String> nodeSet;

	/**
	 * PacketCaptureThread 생성자
	 * 
	 * @param mainView
	 */
	public PacketCaptureThread(MainView mainView) {
		this.mainView = mainView;
		nodeSet = new HashSet<>();
	}
	
	/**
	 * ArrayList return
	 * @return
	 */
	public ArrayList<Packet_Table> getCapturedpacket() {
		return Capturedpacket;
	}

	/**
	 * Thread Start
	 */
	public void run() {

		//////// here

		JPacketHandler<String> jpacketHandler = new JPacketHandler<String>() {
			@Override
			/**
			 * nextPacket
			 */
			public void nextPacket(JPacket packet, String user) {
				// TODO Auto-generated method stub

				Capturedpacket = new ArrayList<Packet_Table>();

				Packet_Table packet_table = new Packet_Table();
				Http http = new Http();
				Tcp tcp = new Tcp();
				Udp udp = new Udp();
				Icmp icmp = new Icmp();
				Arp arp = new Arp();
				JPacket this_pack;

				final JCaptureHeader header = packet.getCaptureHeader();

				System.out.printf("packet caplen=%d wiredlen=%dn", header.caplen(), header.wirelen());
				// System.out.println(packet.toString());

				// FRAME 모든 패킷에 포함됨
				SimpleDateFormat dt1 = new SimpleDateFormat("HH:MM:SS");
				Date date = new Date(packet.getCaptureHeader().timestampInMillis());
				packet_table.setPacket("arrival_time", dt1.format(date));

				// HTTP
				if (packet.hasHeader(http)) {
					// 패킷 데이터를 출력하고 저장
					this_pack = packet.getHeader(http).getPacket();
					System.out.println(this_pack.toString());
					packet_table.setPacket("data", this_pack.toString());

					gettcp_info(packet, packet_table); // sport, dport
					getip_info(packet, packet_table); // sip, dip
					geteth_info(packet, packet_table); // smac, dmac

					packet_table.setPacket("protocol_name", "HTTP");
					
					find_url(packet_table, "naver");
				}

				// TCP
				else if (packet.hasHeader(tcp)) {
					// 패킷 데이터를 출력하고 저장
					this_pack = packet.getHeader(tcp).getPacket();
					System.out.println(this_pack.toString());
					packet_table.setPacket("data", this_pack.toString());

					gettcp_info(packet, packet_table); // sport, dport
					getip_info(packet, packet_table); // sip, dip
					geteth_info(packet, packet_table); // smac, dmac

					packet_table.setPacket("protocol_name", "TCP");
					
					find_port(packet_table, "443");
				}

				// UDP
				else if (packet.hasHeader(udp)) {
					// 패킷 데이터를 출력하고 저장
					this_pack = packet.getHeader(udp).getPacket();
					System.out.println(this_pack.toString());
					packet_table.setPacket("data", this_pack.toString());

					geteth_info(packet, packet_table); // smac, dmac

					packet_table.setPacket("protocol_name", "UDP");
					
					find_port(packet_table, "443");
				}

				// ICMP
				else if (packet.hasHeader(icmp)) {
					// 패킷 데이터를 출력하고 저장
					this_pack = packet.getHeader(icmp).getPacket();
					System.out.println(this_pack.toString());
					packet_table.setPacket("data", this_pack.toString());

					getip_info(packet, packet_table); // sip, dip
					geteth_info(packet, packet_table); // smac, dmac
					
					packet_table.setPacket("protocol_name", "ICMP");
					
					find_ip(packet_table, "168.");
				}

				// ARP
				else if (packet.hasHeader(arp)) {
					// 패킷 데이터를 출력하고 저장
					this_pack = packet.getHeader(arp).getPacket();
					System.out.println(this_pack.toString());
					packet_table.setPacket("data", this_pack.toString());

					geteth_info(packet, packet_table); // smac, dmac

					packet_table.setPacket("protocol_name", "ARP");
				}
				/*
				 * System.out.println(); System.out.println("arrival_time : " +
				 * packet_table.getPacketResource("arrival_time"));
				 * System.out.println("src_mac : " +
				 * packet_table.getPacketResource("src_mac"));
				 * System.out.println("src_ip : " +
				 * packet_table.getPacketResource("src_ip"));
				 * System.out.println("src_port : " +
				 * packet_table.getPacketResource("src_port"));
				 * System.out.println("dst_mac : "
				 * +packet_table.getPacketResource("dst_mac"));
				 * System.out.println("dst_ip : "
				 * +packet_table.getPacketResource("dst_ip"));
				 * System.out.println("dst_port : "
				 * +packet_table.getPacketResource("dst_port"));
				 * System.out.println("protocol_name : " +
				 * packet_table.getPacketResource("protocol_name"));
				 * //System.out.println("data : "
				 * +packet_table.getPacketResource("data")); System.out.println(
				 * "event : " + packet_table.getPacketResource("event"));
				 * System.out.println("risk : "
				 * +packet_table.getPacketResource("risk"));
				 * System.out.println();
				 */

				Capturedpacket.add(packet_table);

				// MainView table 셋팅

				mainView.tableModel.insertRow(0, setTableRow(packet_table));

				// mainView.tableModel.setDataVector(dataVector,
				// columnIdentifiers);

				// IPTree 셋팅

				nodeSet.add(packet_table.getPacketResource("src_ip"));
				setTreeNode();
			}

		};

		/***************************************************************************
		 * Fifth we enter the loop and tell it to capture 10 packets. We pass in
		 * the dumper created in step 3
		 **************************************************************************/

		pcap.loop(999999999, jpacketHandler, "jpacket test"); // 패킷 데이터 출력

		// File file = new File(ofile);
		// System.out.printf("%s file has %d bytes in it!\n", ofile,
		// file.length());

		/***************************************************************************
		 * Last thing to do is close the dumper and pcap handles
		 **************************************************************************/
		// dumper.close(); // Won't be able to delete without explicit close
		//pcap.close();
		/////////////////////////////////////////////////////////////////////////////////////
	}

	// 디바이스 설정값 가져옴
	/*
	 * void setDevice(int index, NetworkInterface[] devices){ this.index =
	 * index; this.devices = devices; }
	 */
	//
	
	/**
	 * setDevice Pcap
	 * @param tmp
	 */
	void setDevice(Pcap tmp) {
		this.pcap = tmp;
	}

	/**
	 * stopCapture 
	 */
	void stopCapture() {
		// pcap.close();
		// captor.breakLoop();
		pcap.close();
	}

	/**
	 * setTreeNode
	 */
	private void setTreeNode() {

		mainView.rootNode.removeAllChildren();
		nodeSet.stream().forEach(node -> mainView.rootNode.add(new DefaultMutableTreeNode(node)));
		mainView.treeModel.reload();
		mainView.tree_ip.revalidate();

	}

	/**
	 * setTableRow 
	 * @param packet
	 * @return
	 */
	private Object[] setTableRow(Packet_Table packet) {

		String tempPacket[] = { packet.getPacketResource("risk"), packet.getPacketResource("arrival_time"),
				packet.getPacketResource("protocol_name"), packet.getPacketResource("src_ip"),
				packet.getPacketResource("dst_ip"), packet.getPacketResource("event_name"), };
		return (Object[]) tempPacket;
	}

	/**
	 * get user input
	 * 
	 * @param q
	 *            input String
	 * @return into value of String
	 */
	public static String getInput(String q) {
		String input = "";
		System.out.print(q);
		BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(System.in));
		try {
			input = bufferedreader.readLine();
		} catch (IOException ioexception) {
		}

		return input;
	}

	/**
	 * getip_info
	 * @param pack
	 *				input JPacket
	 * @param pk_table
	 * 				input Packet_Table
	 */
	void getip_info(JPacket pack, Packet_Table pk_table) {

		Ip4 ip = new Ip4();
		Ip4.Timestamp ts = new Ip4.Timestamp();
		Ip4.LooseSourceRoute lsroute = new Ip4.LooseSourceRoute();
		Ip4.StrictSourceRoute ssroute = new Ip4.StrictSourceRoute();

		if (pack.hasHeader(ip)) {

			byte[] sIP = new byte[4];
			byte[] dIP = new byte[4];
			int a, b, c, d;
			String ttmp;

			sIP = pack.getHeader(ip).source();
			dIP = pack.getHeader(ip).destination();

			a = Byte.toUnsignedInt(sIP[0]);
			b = Byte.toUnsignedInt(sIP[1]);
			c = Byte.toUnsignedInt(sIP[2]);
			d = Byte.toUnsignedInt(sIP[3]);

			ttmp = (Integer.toString(a) + "." + Integer.toString(b) + "." + Integer.toString(c) + "."
					+ Integer.toString(d));

			pk_table.setPacket("src_ip", ttmp);

			a = Byte.toUnsignedInt(dIP[0]);
			b = Byte.toUnsignedInt(dIP[1]);
			c = Byte.toUnsignedInt(dIP[2]);
			d = Byte.toUnsignedInt(dIP[3]);

			ttmp = (Integer.toString(a) + "." + Integer.toString(b) + "." + Integer.toString(c) + "."
					+ Integer.toString(d));

			pk_table.setPacket("dst_ip", ttmp);

		}
	}
	
	/**
	 * geteth_info
	 * @param pack
	 * 				input JPacket
	 * @param pk_table
	 * 				input Packet_Table
	 */
	void geteth_info(JPacket pack, Packet_Table pk_table) {

		Ethernet ethernet = new Ethernet();

		if (pack.hasHeader(ethernet)) {

			byte[] sIP;
			byte[] dIP;
			int leng;
			String result = "";
			int[] inttmp;

			leng = pack.getHeader(ethernet).source().length;
			sIP = pack.getHeader(ethernet).source();

			inttmp = new int[leng];

			for (int i = 0; i < leng; i++) {
				inttmp[i] = Byte.toUnsignedInt(sIP[i]) & 0xFF;
				if (i == 5)
					result += String.format("%02X", inttmp[i]);
				else
					result += String.format("%02X", inttmp[i]) + ":";
			}

			pk_table.setPacket("src_mac", result);

			result = "";
			leng = pack.getHeader(ethernet).destination().length;
			dIP = pack.getHeader(ethernet).destination();

			inttmp = new int[leng];

			for (int i = 0; i < leng; i++) {
				inttmp[i] = Byte.toUnsignedInt(sIP[i]) & 0xFF;
				if (i == 5)
					result += String.format("%02X", inttmp[i]);
				else
					result += String.format("%02X", inttmp[i]) + ":";
			}

			pk_table.setPacket("dst_mac", result);

		}
	}

	/**
	 * gettcp_info
	 * @param pack
	 * 				input JPacket
	 * @param pk_table
	 * 				input Packet_Table
	 */
	void gettcp_info(JPacket pack, Packet_Table pk_table) {

		Tcp tcp = new Tcp();

		if (pack.hasHeader(tcp)) {

			pk_table.setPacket("src_port", Integer.toString(pack.getHeader(tcp).source()));

			pk_table.setPacket("dst_port", Integer.toString(pack.getHeader(tcp).destination()));

			// pk_table.setPacket("protocol_name","TCP");
		}
	}

	/**
	 * getudp_info
	 * @param pack
	 * 				input JPacket
	 * @param pk_table
	 * 				input Packet_Table
	 */
	void getudp_info(JPacket pack, Packet_Table pk_table) {

		Udp udp = new Udp();

		if (pack.hasHeader(udp)) {

			pk_table.setPacket("src_port", Integer.toString(pack.getHeader(udp).source()));

			pk_table.setPacket("dst_port", Integer.toString(pack.getHeader(udp).destination()));

		}
	}
	
	/**
	 * gethttp_info
	 * @param pack
	 * 				input JPacket
	 * @param pk_table
	 * 				input Packet_Table
	 */
	void gethttp_info(JPacket pack, Packet_Table pk_table) {

		Http http = new Http();

		if (pack.hasHeader(http)) {

			Date date = new Date(pack.getCaptureHeader().timestampInMillis());
			pk_table.setPacket("arrival_time", date.toString());

			JPacket this_pack = pack.getHeader(http).getPacket();

			System.out.println(this_pack.toString());

			pk_table.setPacket("data", pack.getHeader(http).getPacket().toString());

			pk_table.setPacket("data", pack.getHeader(http).toString());

			pk_table.setPacket("protocol_name", "HTTP");
		}
	}

	/**
	 * getarp_info
	 * @param pack
	 * 				input JPacket
	 * @param pk_table
	 * 				input Packet_Table
	 */
	void getarp_info(JPacket pack, Packet_Table pk_table) {

		Arp arp = new Arp();

		if (pack.hasHeader(arp)) {

			Date date = new Date(pack.getCaptureHeader().timestampInMillis());
			pk_table.setPacket("arrival_time", date.toString());

			JPacket this_pack = pack.getHeader(arp).getPacket();

			System.out.println(this_pack.toString());

			byte[] sIP;
			byte[] dIP;
			int leng;
			String result = "";
			int[] inttmp;

			leng = pack.getHeader(arp).sha().length;
			sIP = pack.getHeader(arp).sha();

			inttmp = new int[leng];

			for (int i = 0; i < leng; i++) {
				inttmp[i] = Byte.toUnsignedInt(sIP[i]) & 0xFF;
				if (i == 5)
					result += String.format("%02X", inttmp[i]);
				else
					result += String.format("%02X", inttmp[i]) + ":";
			}

			pk_table.setPacket("src_mac", result);

			result = "";
			leng = pack.getHeader(arp).spa().length;
			sIP = pack.getHeader(arp).spa();

			inttmp = new int[leng];

			for (int i = 0; i < leng; i++) {
				inttmp[i] = Byte.toUnsignedInt(sIP[i]);
				if (i == 3)
					result += Integer.toString(inttmp[i]);
				else
					result += Integer.toString(inttmp[i]) + ".";
			}

			pk_table.setPacket("src_ip", result);

			result = "";
			leng = pack.getHeader(arp).tha().length;
			dIP = pack.getHeader(arp).tha();

			inttmp = new int[leng];

			for (int i = 0; i < leng; i++) {
				inttmp[i] = Byte.toUnsignedInt(dIP[i]) & 0xFF;
				if (i == 5)
					result += String.format("%02X", inttmp[i]);
				else
					result += String.format("%02X", inttmp[i]) + ":";
			}

			pk_table.setPacket("dst_mac", result);

			result = "";
			leng = pack.getHeader(arp).tpa().length;
			dIP = pack.getHeader(arp).tpa();

			inttmp = new int[leng];

			for (int i = 0; i < leng; i++) {
				inttmp[i] = Byte.toUnsignedInt(dIP[i]) & 0xFF;
				if (i == 3)
					result += Integer.toString(inttmp[i]);
				else
					result += Integer.toString(inttmp[i]) + ".";
			}

			pk_table.setPacket("dst_ip", result);

			pk_table.setPacket("protocol_name", "ARP");
		}
	}

	/**
	 * find_url
	 * @param pk_table
	 * 					input Packet_Table
	 * @param url
	 * 					input String
	 */
	void find_url(Packet_Table pk_table, String url) {

		boolean tmp = pk_table.getPacketResource("data").contains(url);
		if (tmp) {
			int a = Integer.valueOf(pk_table.getPacketResource("risk"));
			a += 20;
			pk_table.setPacket("risk", Integer.toString(a));
		}
	}
	
	/**
	 * find_port
	 * @param pk_table
	 * 					input Packet_Table
	 * @param port
	 * 					input String
	 */
	void find_port(Packet_Table pk_table, String port) {

		boolean tmp = pk_table.getPacketResource("data").contains(port);
		if (tmp) {
			pk_table.setPacket("risk", "warning");
		}
	}
	
	/**
	 * find_ip
	 * @param pk_table
	 * 					input Packet_Table
	 * @param ip
	 * 					input String
	 */
	void find_ip(Packet_Table pk_table, String ip) {

		boolean tmp = pk_table.getPacketResource("data").contains(ip);
		if (tmp) {
			pk_table.setPacket("risk", "critical");
		}
	}
	
}
