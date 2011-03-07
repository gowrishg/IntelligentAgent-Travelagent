/**
 * ***************************************************************
 * JADE - Java Agent DEvelopment Framework is a framework to develop
 * multi-agent systems in compliance with the FIPA specifications.
 * Copyright (C) 2000 CSELT S.p.A.
 * 
 * GNU Lesser General Public License
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation,
 * version 2.1 of the License.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307, USA.
 * **************************************************************
 */
package ia.travelagency.ontology;

import java.util.ArrayList;

import jade.core.AID;

public interface TravelAgencyVocabulary {
	  public static final String TRAVEL_DETAIL = "TRAVELDETAIL";
	  public static final String TRAVEL_DETAIL_FROMADDR = "fromAddress";
	  public static final String TRAVEL_DETAIL_TOADDR = "toAddress";
	  public static final String TRAVEL_DETAIL_FROMDATE = "fromDate";
	  public static final String TRAVEL_DETAIL_TODATE = "toDate";
	  public static final String TRAVEL_DETAIL_GUESTCOUNT = "guestCount";
	  public static final String TRAVEL_DETAIL_CLASSOFFLIGHT = "classOfFlight";
	  public static final String TRAVEL_DETAIL_CLASSOFHOTEL = "classOfHotel";
	  public static final String TRAVEL_DETAIL_TYPEOFHOTEL = "typeOfHotel";
	  public static final String TRAVEL_DETAIL_AMOUNT = "amount";
	  public static final String TRAVEL_DETAIL_HOTELDETAIL = "hoteldetail";

	  public static final String FLIGHT_DETAIL = "FLIGHTDETAIL";
	  public static final String FLIGHT_DETAIL_FLIGHTFROM = "flightFrom";
	  public static final String FLIGHT_DETAIL_FLIGHTTO = "flightTo";
	  public static final String FLIGHT_DETAIL_FLIGHTNAME = "flightName";
	  public static final String FLIGHT_DETAIL_FLIGHTCODE = "flightCode";
	  public static final String FLIGHT_DETAIL_FLIGHTCLASS = "flightClass";
	  public static final String FLIGHT_DETAIL_FLIGHTCOST = "flightCost";
	  public static final String FLIGHT_DETAIL_FLIGHTID = "flightID";

	  public static final String HOTEL_DETAIL = "HOTELDETAIL";
	  public static final String HOTEL_DETAIL_HOTELTYPE = "hotelType";
	  public static final String HOTEL_DETAIL_HOTELCLASS = "hotelClass";
	  public static final String HOTEL_DETAIL_HOTELADDR = "hotelAddr";
	  public static final String HOTEL_DETAIL_HOTELNAME = "hotelName";
	  public static final String HOTEL_DETAIL_HOTELCOST = "hotelCost";
	  public static final String HOTEL_DETAIL_HOTELCAPACITY = "hotelCapacity";
	  public static final String HOTEL_DETAIL_HOTELID = "hotelID";
	  
	  public static final String CREDIT_CARD = "CREDITCARD";
	  public static final String CREDIT_CARD_TYPE = "type";
	  public static final String CREDIT_CARD_NUMBER = "number";
	  public static final String CREDIT_CARD_EXPIRATION_DATE = "expirationdate";
	  
	  public static final String PAY = "PAY";
	  public static final String PAY_BUYER = "buyer";
	  public static final String PAY_TRAVEL_DETAIL = "traveldetail";
	  public static final String PAY_CREDIT_CARD = "creditcard";
          public static final String PAY_STATUS = "status";
}  
