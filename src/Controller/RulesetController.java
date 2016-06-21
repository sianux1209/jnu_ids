

package Controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.jfree.chart.ChartPanel;
import org.jnetpcap.packet.JPacket;

import Packet.Packet_Table;
import View.IpDto;
import View.MainView;
import View.PortProtocolDto;
import View.TotalPanel;
import View.UrlDto;

/**
 * 
 * 환경설정값을 받아와서 필터링. 이를 토대로 패밋의 위험도와 이벤트를 표시해주며, 
 * mainView의 information과 analysis 패널을 갱신하는 역할
 * 
 * @author 김연수
 * @version 3.0
 *
 */
public class RulesetController extends FileController {
	private String ruleset;
	private ArrayList<IpDto> iplistLoad;
	private ArrayList<PortProtocolDto> portlistLoad;
	private ArrayList<UrlDto> urllistLoad;
	public TotalPanel setting;

	private ArrayList<String> ipList;
	private ArrayList<String> portList;
	private ArrayList<String> protocolList;
	private ArrayList<String> urlList;

	MainView mainView;
	private int riskInfo[];
	String startTime;

	/**
	 * 
	 * RulesetController 생성자
	 * 
	 * @param mainView
	 *            mainView의 패널을 갱신하기 위한 파라미터
	 */
	public RulesetController(MainView mainView) {
		this.mainView = mainView;

		setting = new TotalPanel();
		ipList = new ArrayList<String>();
		portList = new ArrayList<String>();
		protocolList = new ArrayList<String>();
		urlList = new ArrayList<String>();
		// setRuleset();

		riskInfo = new int[5]; // count, notice, warning, critical, information
								// 순서
		riskInfo[0] = 1;
		riskInfo[1] = 1;
		riskInfo[2] = 1;
		riskInfo[3] = 1;
		riskInfo[4] = 1;

		strset();
		// print();

	}

	/**
	 * 환경설정 값을 받아서 필터링을 하기위한 String 리스트에 추가
	 */
	public void strset() {
		setRuleset();
		for (int i = 0; i < iplistLoad.size(); i++) {
			ipList.add(iplistLoad.get(i).getIp());
			// System.out.println(ipList.toString());
		}
		for (int i = 0; i < portlistLoad.size(); i++) {
			portList.add(portlistLoad.get(i).getPort());
			protocolList.add(portlistLoad.get(i).getProtocol());
		}
		for (int i = 0; i < urllistLoad.size(); i++) {
			urlList.add(urllistLoad.get(i).getUrl());
		}

	}

	/**
	 * mainView의 analysis 그래프를 갱신
	 */
	public void print() {
		if (riskInfo[0] == 0)
			mainView.createPieDataset(1, 1, 1);
		else {

			// mainView.cp_analysis = new
			// ChartPanel(mainView.drawPieChart("ANALYSIS",
			// mainView.createPieDataset(riskInfo[4]/riskInfo[0],
			// riskInfo[2]/riskInfo[0], riskInfo[1]/riskInfo[0],
			// riskInfo[3]/riskInfo[0])));

			mainView.panel_analysis.removeAll();
			// mainView.panel_analysis.revalidate();
			mainView.chart_analysis = mainView.drawPieChart("ANALYSIS",
					mainView.createPieDataset(riskInfo[1], riskInfo[2], riskInfo[3]));
			mainView.cp_analysis = new ChartPanel(mainView.chart_analysis);

			mainView.panel_analysisLayout = new javax.swing.GroupLayout(mainView.panel_analysis);
			mainView.panel_analysis.setLayout(mainView.panel_analysisLayout);
			mainView.panel_analysisLayout.setHorizontalGroup(
					mainView.panel_analysisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(mainView.cp_analysis, javax.swing.GroupLayout.DEFAULT_SIZE,
									javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
			mainView.panel_analysisLayout.setVerticalGroup(mainView.panel_analysisLayout
					.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(mainView.cp_analysis, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE));

			for (int i = 0; i < riskInfo.length; i++)
				System.out.print(riskInfo[i] + " ");
			System.out.println();
		}
	}

	/**
	 * 환경설정 값을 받아옴
	 */
	public void setRuleset() {
		iplistLoad = setting.getIpList();
		portlistLoad = setting.getProtocolList();
		urllistLoad = setting.geturlList();
	}

	/*
	 * public String getRisk(JPacket packet, Packet_Table packet_table){
	 * 
	 * 
	 * switch(packet_table.getPacketResource("protocol_name")){ case "HTTP" :
	 * 
	 * break; case "TCP" : case "UDP" : case "ICMP" : case "ARP" : }
	 * 
	 * for(int i=0; i<ipList.size(); i++) if
	 * (packet_table.getPacketResource("data").contains(ipList.get(index))){
	 * packet_table.setPacket("evnet", value+ipList.get(index)+" "); }; for(int
	 * i=0; i<portList.size(); i++){
	 * if(packet_table.getPacketResource("data").contains(portList.get(index)))
	 * packet_table.setPacket("event", value+portList.get(index)+" "); } for(int
	 * i=0; i<urlList.size(); i++){
	 * if(packet_table.getPacketResource("data").contains(urlList.get(index)))
	 * packet_table.setPacket("event", value+portList.get(index)+" "); }
	 * 
	 * 
	 * }
	 * 
	 * return ruleset;
	 */
	/**
	 * PacketCaptureThread에서 가져온 패킷을 필터링하여 위험도,이벤트 정보를 입력
	 * @param packet PacketCaptureThread에서 패킷을 가져옴
	 * @param packet_table PacketCaptureThread에서 packet_table을 가져옴
	 */
	public void getEvent(JPacket packet, Packet_Table packet_table) {
		String value = "";

		for (int index = 0; index < portList.size(); index++) {
			if (packet_table.getPacketResource("src_port").contains(portList.get(index))) {
				value += portList.get(index) + " ";
				packet_table.setPacket("event_name", value);
				packet_table.setPacket("risk", "notice");
				riskInfo[0]++;
				riskInfo[1]++;
				if (whichTop() == 1)
					setLastTime("notice");
			}
		}
		for (int index = 0; index < protocolList.size(); index++) {
			if (packet_table.getPacketResource("protocol_name").contains(protocolList.get(index))) {
				value += protocolList.get(index) + " ";
				packet_table.setPacket("event_name", value);
				packet_table.setPacket("risk", "notice");
				riskInfo[0]++;
				riskInfo[1]++;
				if (whichTop() == 1)
					setLastTime("notice");
			}
		}

		for (int index = 0; index < urlList.size(); index++) {
			if (packet_table.getPacketResource("data").contains(urlList.get(index))) {
				value += urlList.get(index) + " ";
				packet_table.setPacket("event_name", value);
				packet_table.setPacket("risk", "warning");
				riskInfo[0]++;
				riskInfo[2]++;
				if (whichTop() == 2)
					setLastTime("warning");
			}
		}

		for (int index = 0; index < ipList.size(); index++) {
			if (packet_table.getPacketResource("src_ip").contains(ipList.get(index))) {
				value += ipList.get(index) + " ";
				packet_table.setPacket("event_name", value);
				packet_table.setPacket("risk", "critical");
				riskInfo[0]++;
				riskInfo[3]++;
				if (whichTop() == 3)
					setLastTime("critical");
			}
		}

		if (packet_table.getPacketResource("risk").equals("")) {
			packet_table.setPacket("risk", "information");
			riskInfo[0]++;
			riskInfo[4]++;
			// if(whichTop()==4)setLastTime("information");
		}

		if (riskInfo[0] % 200 == 0)
			print();

	}
	/**
	 * mainView의 information패널에 가장 많이 발생한 위험유형을 나타내기 위해 최대값 비교
	 * @return 가장 많이 발생한 위험유형의 index를 넘겨줌
	 */
	private int whichTop() {
		int max = 0;
		int maxindex = 0;
		for (int i = 1; i < 3; i++) {
			if (max < riskInfo[i]) {
				max = riskInfo[i];
				maxindex = i;
			}
		}
		return maxindex;
	}
	/**
	 * mainView의 information 패널에 마지막으로 받은 위험 패킷의 시간을 표시하는 역할
	 * @param risk 가장 많이 발생한 위험유형의 이름을 가져옴
	 */
	private void setLastTime(String risk) {

		mainView.setInformation(startTime, new SimpleDateFormat("yy. M. d. a hh:mm:ss").format(new Date()).toString(),
				risk);
		mainView.panel_information.revalidate();
		mainView.panel_information.repaint();
	}
	/**
	 * mainView의 information 처음으로 받은 패킷의 시간을 표시하는 역할
	 * @param startTime 처음 발생한 패킷의 시간을 가져옴
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/*
	 * public int getriskInfo[0](){ return riskInfo[0]; } public int
	 * getriskInfo[1](){ return riskInfo[1]; } public int getriskInfo[2](){
	 * return riskInfo[2]; } public int getriskInfo[3](){ return riskInfo[3]; }
	 * public int getriskInfo[4](){ return riskInfo[4]; }
	 */

}
