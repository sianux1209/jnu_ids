package Packet;

import java.util.HashMap;

/**
 * 
 * @author Á¤Âù¿ì
 * @version 1.0
 * @since 2016.05.30
 */


public class Packet_Table {
	
	/**
	 * JNU_IDS_Packet_Table
	 */
	
	HashMap<String, String> packet;
	String reString;
	
	/**
	 * Packet_Table_name	value
	 * packet_type			"ARPPACKET"
	 * src_ip				"168.131.44.241"
	 * src_mac				"00:01:F4:E0:76:59"
	 * dst_ip				"255.255.255.255"
	 * dst_mac				"FF:FF:FF:FF:FF:FF"
	 * arrival_time			"2016-05-30 11:15:29:992"
	 * protocol_number		"1"
	 * protocol_name		"ARP"
	 * src_port				""
	 * dst_port				""
	 * event_number			""
	 * event_name			""
	 * risk					null
	 */
	public Packet_Table()
	{
		reString = new String();
		packet = new HashMap<String, String>();
		
		packet.put("packet_type", "");
		packet.put("src_ip", "");
		packet.put("src_mac", "");
		packet.put("dst_ip", "");
		packet.put("dst_mac", "");
		packet.put("arrival_time", "");
		packet.put("protocol_number", "");
		packet.put("protocol_name", "");
		packet.put("src_port", "");
		packet.put("dst_port", "");
		packet.put("event_number", "");
		packet.put("event_name", "");
		packet.put("risk", "0");
		
		
	}
	
	/**
	 * @param hashmap_name	PacketTableName
	 * @param value			Value
	 */
	public void setPacket(String hashmap_name, String value ) {
		packet.put(hashmap_name, value);
	}
	
	/**
	 * @param hashmap_name	PacketTableName
	 * @return				Value Of PacketTableName
	 */
	public String getPacketResource(String hashmap_name) {
		reString = packet.get(hashmap_name);

		return reString;
	}
}
