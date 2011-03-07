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

package ia.travelagency;

import ia.travelagency.ontology.FlightDetail;
import ia.travelagency.ontology.TravelDetail;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class FlightAgent extends Agent {
	// Put agent initializations here
	protected void setup() {
		// Register the book-selling service in the yellow pages
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("flight-agent");
		sd.setName("JADE-flight-agent");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}

		// Add the behaviour serving queries from buyer agents
		addBehaviour(new OfferRequestsServer());

		// Add the behaviour serving purchase orders from buyer agents
		addBehaviour(new PurchaseOrdersServer());
	}

	// Put agent clean-up operations here
	protected void takeDown() {
		// Deregister from the yellow pages
		try {
			DFService.deregister(this);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		// Printout a dismissal message
		System.out.println("takeDown():: flight-agent "+getAID().getName()+" terminating.");
	}
	
	private ArrayList<ArrayList<FlightDetail>> getFlightDetails(TravelDetail travelDetail) {
		ArrayList<FlightDetail> flightDetailList = new ArrayList<FlightDetail>();
		ArrayList<FlightDetail> retFlightDetailList = new ArrayList<FlightDetail>();
		ArrayList<ArrayList<FlightDetail>> flightDetails = null;

		try {
			Class.forName("org.sqlite.JDBC");
		    Connection conn = DriverManager.getConnection("jdbc:sqlite:data/travelagency.sqlite3");
		    Statement stat = conn.createStatement();
                    String classOfFlight = travelDetail.getClassOfFlight();
                    String stmt_class = "";
                    if(classOfFlight != null && classOfFlight.length() > 0)
                        stmt_class = " and class like '%"+classOfFlight+"%' ";

                    String stmt = "select * from flight_details where " +
			    " ("
                            + " to_address like '%"+ travelDetail.getToAddress() + "%' "+
			    " and " +
			    " from_address like '%" + travelDetail.getFromAddress() + "%'" +
                            stmt_class +
                            " ) " +
			    " or " +
			    " (" +
                            "from_address like '%"+ travelDetail.getToAddress() + "%' "+
			    " and " +
			    " to_address like '%" + travelDetail.getFromAddress() + "%'" +
                            stmt_class +
                            ") ";


		    System.out.println("Sql statement - " + stmt);
		    ResultSet rs = stat.executeQuery(stmt);
		    while (rs.next()) {
		    	FlightDetail flightDetail = new FlightDetail();
		    	flightDetail.setFlightName(rs.getString("name"));
		    	flightDetail.setFlightCode(rs.getString("code"));
		    	flightDetail.setFlightID(rs.getInt("id"));
		    	flightDetail.setFlightFrom(rs.getString("from_address"));
		    	flightDetail.setFlightTo(rs.getString("to_address"));
		    	flightDetail.setFlightCost(rs.getInt("cost"));
                        flightDetail.setFlightClass(rs.getString("class"));
		    	retFlightDetailList.add(flightDetail);
		    	flightDetailList.add(flightDetail);
		    }
		    rs.close();
		    conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(flightDetailList.size()>0 || retFlightDetailList.size()>0) {
			flightDetails = new ArrayList<ArrayList<FlightDetail>>();
			flightDetails.add(flightDetailList);
			flightDetails.add(retFlightDetailList);
		}

		return flightDetails;
	}

	private boolean bookFlight(TravelDetail travelDetail) {
		FlightDetail flightDetail = travelDetail.getFlightDetail();
		FlightDetail retflightDetail = travelDetail.getRetFlightDetail();
		boolean retVal = true;

		try {
		    long fromDate = TravelDetail.getLongDate(travelDetail.getFromDate());
		    long toDate = TravelDetail.getLongDate(travelDetail.getToDate());

			Class.forName("org.sqlite.JDBC");
		    Connection conn = DriverManager.getConnection("jdbc:sqlite:data/travelagency.sqlite3");
		    Statement stat = conn.createStatement();
		    String stmt = "insert into flight_details_booked(id, date, count, reverse) " +
			    " values ( " + flightDetail.getFlightID() + ", " 
			    + fromDate + ", " 
			    + travelDetail.getGuestCount() + ", " 
			    + "0 )";
		    stat.addBatch(stmt);
		    stmt = "insert into flight_details_booked(id, date, count, reverse) " +
		    " values ( " + retflightDetail.getFlightID() + ", " 
		    + toDate + ", " 
		    + travelDetail.getGuestCount() + ", " 
		    + "1 )";
		    stat.addBatch(stmt);
		    stat.executeBatch();
		    conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			retVal = false;
		}
		return retVal;
	}

	/**
	   Inner class OfferRequestsServer.
	   This is the behaviour used by flight-agent agents to serve incoming requests 
	   for offer from travel agents.
	   If the requested book is in the local catalog the flight agent replies 
	   with a PROPOSE message specifying the price. Otherwise a REFUSE message is
	   sent back.
	 */
	private class OfferRequestsServer extends CyclicBehaviour {
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {
				// CFP Message received. Process it
				TravelDetail travelDetail = new TravelDetail();
				try {
					travelDetail = (TravelDetail) msg.getContentObject();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ACLMessage reply = msg.createReply();
				ArrayList<ArrayList<FlightDetail>> flightDetails = getFlightDetails(travelDetail);

				if (flightDetails != null) {
					// The requested book is available for sale. Reply with the price
					reply.setPerformative(ACLMessage.PROPOSE);
					try {
						reply.setContentObject(flightDetails);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					// The requested book is NOT available for sale.
					reply.setPerformative(ACLMessage.REFUSE);
				}
				myAgent.send(reply);
			} else {
				block();
			}
		}
	}  // End of inner class OfferRequestsServer

	/**
	   Inner class PurchaseOrdersServer.
	   This is the behaviour used by flight-agent agents to serve incoming 
	   offer acceptances (i.e. purchase orders) from travel-agent agents.
	   The flight-agent books the room, i.e adds entry to a database 
	   and replies with an INFORM message to notify the buyer that the
	   purchase has been successfully completed.
	 */
	private class PurchaseOrdersServer extends CyclicBehaviour {
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {
				// ACCEPT_PROPOSAL Message received. Process it
				// CFP Message received. Process it
				try {
					TravelDetail travelDetail = (TravelDetail) msg.getContentObject();
					ACLMessage reply = msg.createReply();
					System.out.println("Informing travel agent, Flight booked by flight-agent :"+msg.getSender().getName());
	
					boolean retVal = bookFlight(travelDetail);
					if (retVal) {
						reply.setPerformative(ACLMessage.INFORM);
					}
					else {
						// The requested book has been sold to another buyer in the meanwhile .
						reply.setPerformative(ACLMessage.FAILURE);
					}
					myAgent.send(reply);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				block();
			}
		}
	}  // End of inner class PurchaseOrdersServer
}
