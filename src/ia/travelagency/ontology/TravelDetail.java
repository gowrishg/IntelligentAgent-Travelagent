package ia.travelagency.ontology;

import jade.content.Concept;
import jade.content.ContentElement;
import jade.content.onto.basic.Action;
import jade.core.Agent;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TravelDetail implements Serializable, Concept {

	private String fromAddress, toAddress;
	private String fromDate, toDate;
	private int guestCount = 0, amount = 0;
	private ArrayList<TourPackageDetail> tourPackageList = new ArrayList<TourPackageDetail>();
	private HotelDetail hotelDetail = new HotelDetail();
	private FlightDetail flightDetail = new FlightDetail();
	private FlightDetail retFlightDetail = new FlightDetail();
	private String classOfFlight, typeOfHotel, classOfHotel;

	public HotelDetail getHotelDetail() {
		return hotelDetail;
	}

	public void setHotelDetail(HotelDetail hotelDetail) {
		this.hotelDetail = hotelDetail;
	}

	public FlightDetail getFlightDetail() {
		return flightDetail;
	}

	public void setFlightDetail(FlightDetail flightDetail) {
		this.flightDetail = flightDetail;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public int getGuestCount() {
		return guestCount;
	}

	public void setGuestCount(int guestCount) {
		this.guestCount = guestCount;
	}

	public static long getLongDate(String str_date) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
		Date date = (Date) formatter.parse(str_date);
		long sec = 86400000;
		long longDate = date.getTime() / sec;
		return longDate;
	}

	public void setTourPackageList(ArrayList<TourPackageDetail> tourPackageList) {
		this.tourPackageList = tourPackageList;
	}

	public ArrayList<TourPackageDetail> getTourPackageList() {
		return tourPackageList;
	}

	@Override
	public String toString() {
		return "TravelDetail [amount=" + amount + ", flightDetail="
				+ flightDetail + ", fromAddress=" + fromAddress + ", fromDate="
				+ fromDate + ", guestCount=" + guestCount + ", hotelDetail="
				+ hotelDetail + ", retFlightDetail=" + retFlightDetail
				+ ", toAddress=" + toAddress + ", toDate=" + toDate
				+ ", tourPackageList=" + tourPackageList + "]";
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	public void setRetFlightDetail(FlightDetail retFlightDetail) {
		this.retFlightDetail = retFlightDetail;
	}

	public FlightDetail getRetFlightDetail() {
		return retFlightDetail;
	}

	public void setClassOfFlight(String classOfFlight) {
		this.classOfFlight = classOfFlight;
	}

	public String getClassOfFlight() {
		return classOfFlight;
	}

	public void setTypeOfHotel(String typeOfHotel) {
		this.typeOfHotel = typeOfHotel;
	}

	public String getTypeOfHotel() {
		return typeOfHotel;
	}

	public void setClassOfHotel(String classOfHotel) {
		this.classOfHotel = classOfHotel;
	}

	public String getClassOfHotel() {
		return classOfHotel;
	}
	
	public void calculateAmount() {
		HotelDetail hotelDetail = getHotelDetail();
		int hotelCost = hotelDetail.getHotelCost();
		
		FlightDetail flightDetail = getFlightDetail();
		int flightCost = flightDetail.getFlightCost();

		FlightDetail retFlightDetail = getRetFlightDetail();
		int retFlightCost = retFlightDetail.getFlightCost();

		int totalCost = (hotelCost + flightCost + retFlightCost) * getGuestCount();
		setAmount(totalCost);
	}
	

}

