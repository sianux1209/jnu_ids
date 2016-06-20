package View;

import java.util.ArrayList;

import javax.swing.JPanel;

public class TotalPanel extends JPanel {
	private IpPanel ipPanel;
	private UrlPanel urlPanel;
	private PortProtocolPanel inputPanel;
	private ArrayList<IpDto> ipDtos;
	private ArrayList<PortProtocolDto> portProtocolDtos;
	private ArrayList<UrlDto> urlDtos;

	/**
	 * Create the panel.
	 */
	public TotalPanel() {
		ipDtos = Dao.getInstance().getIp();
		urlDtos = Dao.getInstance().getUrl();
		portProtocolDtos = Dao.getInstance().getPortProtocol();

		ipPanel = new IpPanel(ipDtos);
		urlPanel = new UrlPanel(urlDtos);
		inputPanel = new PortProtocolPanel(portProtocolDtos);
		setSize(981, 624);
		setLayout(null);

		ipPanel.setBounds(0, 0, 486, 585);
		add(ipPanel);

		urlPanel.setBounds(486, 0, 479, 296);
		add(urlPanel);

		inputPanel.setBounds(486, 298, 479, 287);
		add(inputPanel);
	}

	public ArrayList<IpDto> getIpList() {
		return ipDtos;
	}

	public ArrayList<PortProtocolDto> getProtocolList() {
		return portProtocolDtos;
	}

	public ArrayList<UrlDto> geturlList() {
		return urlDtos;
	}

}
