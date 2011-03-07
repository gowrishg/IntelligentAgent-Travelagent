package ia.travelagency.ontology;

import jade.content.Concept;
import jade.content.ContentElement;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.io.Serializable;


public class HotelDetail implements Serializable, Concept {
	private String hotelType, hotelAddr, hotelName, hotelClass;
	private int hotelCost = 0, hotelCapacity = 0, hotelID = 0;
	private AID hostAgentID;

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getHotelClass() {
		return hotelClass;
	}

	public void setHotelClass(String hotelClass) {
		this.hotelClass = hotelClass;
	}

	public int getHotelCapacity() {
		return hotelCapacity;
	}

	public void setHotelCapacity(int hotelCapacity) {
		this.hotelCapacity = hotelCapacity;
	}

	public void setHotelType(String hotelType) {
		this.hotelType = hotelType;
	}

	public String getHotelType() {
		return hotelType;
	}

	public void setHotelCost(int hotelCost) {
		this.hotelCost = hotelCost;
	}

	public int getHotelCost() {
		return hotelCost;
	}

	public void setHotelAddr(String hotelAddr) {
		this.hotelAddr = hotelAddr;
	}

	public String getHotelAddr() {
		return hotelAddr;
	}
	
	@Override
	public String toString() {
		return "HotelDetail [hostAgentID=" + hostAgentID + ", hotelAddr="
				+ hotelAddr + ", hotelCapacity=" + hotelCapacity
				+ ", hotelClass=" + hotelClass + ", hotelCost=" + hotelCost
				+ ", hotelName=" + hotelName + ", hotelType=" + hotelType + "]";
	}

	public void setHostAgentID(AID hostAgentID) {
		this.hostAgentID = hostAgentID;
	}

	public AID getHostAgentID() {
		return hostAgentID;
	}

	public void setHotelID(int hotelID) {
		this.hotelID = hotelID;
	}

	public int getHotelID() {
		return hotelID;
	}
	


}
