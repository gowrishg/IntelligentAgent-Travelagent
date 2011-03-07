package ia.travelagency.ontology;

import jade.content.Concept;
import jade.core.AID;

import java.io.Serializable;

public class FlightDetail implements Serializable, Concept {
	private String flightFrom, flightTo, flightName, flightCode, flightClass;
	private int flightCost = 0, flightID = 0;
	private AID flightAgentID;

	public String getFlightFrom() {
		return flightFrom;
	}

	public void setFlightFrom(String flightFrom) {
		this.flightFrom = flightFrom;
	}

	public String getFlightTo() {
		return flightTo;
	}

	public void setFlightTo(String flightTo) {
		this.flightTo = flightTo;
	}

	public void setFlightID(int flightID) {
		this.flightID = flightID;
	}

	public int getFlightID() {
		return flightID;
	}

	public void setFlightCost(int flightCost) {
		this.flightCost = flightCost;
	}

	public int getFlightCost() {
		return flightCost;
	}

	public void setFlightClass(String flightClass) {
		this.flightClass = flightClass;
	}

	public String getFlightClass() {
		return flightClass;
	}

	@Override
	public String toString() {
		return "FlightDetail [flightAgentID=" + flightAgentID + ", flightCode="
				+ flightCode + ", flightCost=" + flightCost + ", flightFrom="
				+ flightFrom + ", flightID=" + flightID + ", flightName="
				+ flightName + ", flightTo=" + flightTo + "]";
	}

	public void setFlightAgentID(AID flightAgentID) {
		this.flightAgentID = flightAgentID;
	}

	public AID getFlightAgentID() {
		return flightAgentID;
	}

	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}

	public String getFlightName() {
		return flightName;
	}

	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}

	public String getFlightCode() {
		return flightCode;
	}
}
