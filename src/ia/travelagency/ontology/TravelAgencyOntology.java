/*****************************************************************
JADE - Java Agent DEvelopment Framework is a framework to develop
multi-agent systems in compliance with the FIPA specifications.
Copyright (C) 2000 CSELT S.p.A. 

GNU Lesser General Public License

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation, 
version 2.1 of the License. 

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the
Free Software Foundation, Inc., 59 Temple Place - Suite 330,
Boston, MA  02111-1307, USA.
 *****************************************************************/

package ia.travelagency.ontology;

import jade.content.onto.*;
import jade.content.schema.*;
import jade.core.AID;

public class TravelAgencyOntology extends Ontology implements TravelAgencyVocabulary {
	// NAME
	public static final String ONTOLOGY_NAME = "Travel-agency-ontology";

	private static Ontology theInstance = new TravelAgencyOntology();

	public static Ontology getInstance() {
		return theInstance;
	}

	private TravelAgencyOntology() {
		// Adds the roles of the basic ontology (ACTION, AID,...)
		super(ONTOLOGY_NAME, BasicOntology.getInstance());
		try {
	    	add(new ConceptSchema(CREDIT_CARD), CreditCard.class);
	    	add(new ConceptSchema(TRAVEL_DETAIL), TravelDetail.class);
	    	add(new ConceptSchema(HOTEL_DETAIL), HotelDetail.class);
	    	add(new ConceptSchema(FLIGHT_DETAIL), FlightDetail.class);
	    	add(new AgentActionSchema(PAY), Pay.class);
	    	
	    	ConceptSchema cs = (ConceptSchema) getSchema(CREDIT_CARD);
	    	cs.add(CREDIT_CARD_TYPE, (PrimitiveSchema) getSchema(BasicOntology.STRING)); 
	    	cs.add(CREDIT_CARD_NUMBER, (PrimitiveSchema) getSchema(BasicOntology.INTEGER)); 
	    	cs.add(CREDIT_CARD_EXPIRATION_DATE, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
	    	
	    	cs = (ConceptSchema) getSchema(TRAVEL_DETAIL);
	    	cs.add(TRAVEL_DETAIL_AMOUNT, (PrimitiveSchema) getSchema(BasicOntology.INTEGER), ObjectSchema.OPTIONAL);
	    	cs.add(TRAVEL_DETAIL_CLASSOFFLIGHT, (PrimitiveSchema) getSchema(BasicOntology.STRING) , ObjectSchema.OPTIONAL);
	    	cs.add(TRAVEL_DETAIL_CLASSOFHOTEL, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
	    	cs.add(TRAVEL_DETAIL_FROMADDR, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
	    	cs.add(TRAVEL_DETAIL_FROMDATE, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
	    	cs.add(TRAVEL_DETAIL_GUESTCOUNT, (PrimitiveSchema) getSchema(BasicOntology.INTEGER), ObjectSchema.OPTIONAL);
	    	cs.add(TRAVEL_DETAIL_TOADDR, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
	    	cs.add(TRAVEL_DETAIL_TODATE, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
	    	cs.add(TRAVEL_DETAIL_TYPEOFHOTEL, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
	    	cs.add(TRAVEL_DETAIL_HOTELDETAIL, (ConceptSchema) getSchema(HOTEL_DETAIL), ObjectSchema.OPTIONAL);
	    	
	    	cs = (ConceptSchema) getSchema(HOTEL_DETAIL);
	    	cs.add(HOTEL_DETAIL_HOTELADDR, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
	    	cs.add(HOTEL_DETAIL_HOTELCAPACITY, (PrimitiveSchema) getSchema(BasicOntology.INTEGER), ObjectSchema.OPTIONAL);
	    	cs.add(HOTEL_DETAIL_HOTELCLASS, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
	    	cs.add(HOTEL_DETAIL_HOTELCOST, (PrimitiveSchema) getSchema(BasicOntology.INTEGER), ObjectSchema.OPTIONAL);
	    	cs.add(HOTEL_DETAIL_HOTELID, (PrimitiveSchema) getSchema(BasicOntology.INTEGER), ObjectSchema.OPTIONAL);
	    	cs.add(HOTEL_DETAIL_HOTELNAME, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
	    	cs.add(HOTEL_DETAIL_HOTELTYPE, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);

	    	cs = (ConceptSchema) getSchema(FLIGHT_DETAIL);
	    	cs.add(FLIGHT_DETAIL_FLIGHTCLASS, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
	    	cs.add(FLIGHT_DETAIL_FLIGHTCODE, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
	    	cs.add(FLIGHT_DETAIL_FLIGHTCOST, (PrimitiveSchema) getSchema(BasicOntology.INTEGER), ObjectSchema.OPTIONAL);
	    	cs.add(FLIGHT_DETAIL_FLIGHTFROM, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
	    	cs.add(FLIGHT_DETAIL_FLIGHTID, (PrimitiveSchema) getSchema(BasicOntology.INTEGER), ObjectSchema.OPTIONAL);
	    	cs.add(FLIGHT_DETAIL_FLIGHTNAME, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
	    	cs.add(FLIGHT_DETAIL_FLIGHTTO, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);

	    	AgentActionSchema as = (AgentActionSchema) getSchema(PAY);
	    	as.add(PAY_STATUS, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
	    	as.add(PAY_BUYER, (ConceptSchema) getSchema(BasicOntology.AID));
	    	as.add(PAY_CREDIT_CARD, (ConceptSchema) getSchema(CREDIT_CARD)); 
	    	as.add(PAY_TRAVEL_DETAIL, (ConceptSchema) getSchema(TRAVEL_DETAIL)); 
	    	
	    	//useConceptSlotsAsFunctions();

		} catch (OntologyException oe) {
			oe.printStackTrace();
		}
	}

}
