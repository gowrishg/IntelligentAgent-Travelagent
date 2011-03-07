package ia.travelagency;

import ia.travelagency.ontology.CreditCard;
import ia.travelagency.ontology.Pay;
import ia.travelagency.ontology.TravelAgencyOntology;

import java.util.ArrayList;

import javax.media.j3d.Behavior;

import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Done;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PayGatewayAgent extends Agent {

	private ContentManager manager = (ContentManager) getContentManager();
	// This agent "speaks" the SL language
	private Codec codec = new SLCodec();
	// This agent "knows" the TravelAgencyOntology
	private Ontology ontology = TravelAgencyOntology.getInstance();

	ArrayList<CreditCard> creditCardList;

	// #CUSTOM_EXCLUDE_BEGIN
	private jade.content.ContentManager theContentManager = null;

	/**
	 * Retrieves the agent's content manager
	 * 
	 * @return The content manager.
	 */
	public jade.content.ContentManager getContentManager() {
		if (theContentManager == null) {
			theContentManager = new jade.content.ContentManager();
		}
		return theContentManager;
	}

	// Put agent initializations here
	protected void setup() {
		manager.registerLanguage(codec);
		manager.registerOntology(ontology);
		
		// Create the hotel list catalog
		creditCardList = new ArrayList<CreditCard>();


		// Register the book-selling service in the yellow pages
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("paygateway-agent");
		sd.setName("JADE-paygateway-agent");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}

		// Add the behaviour serving queries from buyer agents
		addBehaviour(new HandleRequestBehaviour(this));

	}

        private boolean checkCCValid(CreditCard cc) {
            boolean retVal = false;
            		try {
			String code = getAID().getLocalName();
			Class.forName("org.sqlite.JDBC");
		    Connection conn = DriverManager.getConnection("jdbc:sqlite:data/travelagency.sqlite3");
		    Statement stat = conn.createStatement();
		    String stmt = "select count(*) as count from cc_details "+
		    		" where " +
		    		" type like '%"+cc.getType()+"%' " +
		    		" and number="+cc.getNumber() +
		    		" and expiry like '"+cc.getExpirationDate()+"' ";
                    System.out.println(" Payment check stmt : " + stmt);
		    ResultSet rs = stat.executeQuery(stmt);
		    while (rs.next()) {
                        int count = rs.getInt("count");
                        if(count>0)
                            retVal = true;
                        else
                            retVal = false;
		    }
		    rs.close();
		    conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

            return retVal;
        }

	// SELLER handles requests from BUYER
	class HandleRequestBehaviour extends CyclicBehaviour {

		public HandleRequestBehaviour(Agent a) {
			super(a);
		}

		public void action() {
			ACLMessage msg = receive(MessageTemplate
					.MatchPerformative(ACLMessage.REQUEST));
			if (msg != null) {
				try {
					System.out.println("PayGateway: Received request from Travel Agent to process payment.");
					Action a = (Action) manager.extractContent(msg);
					Pay pay = (Pay) a.getAction();

                                        CreditCard cc = pay.getCreditCard();
                                        boolean success = checkCCValid(cc);

					// Do the action. Not implemented as it is out of the scope
					// of this example
      					addBehaviour(new InformDoneBehaviour(myAgent, a, success));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} 
			else {
				block();
			}
		}
	}

	// SELLER informs BUYER that a given action has been completed
	class InformDoneBehaviour extends OneShotBehaviour {
		private Action act;
		private Agent myAgent;
                private boolean success;
                
		public InformDoneBehaviour(Agent a, Action act, boolean success) {
			super(a);
			myAgent = a;
			this.act = act;
                        this.success = success;
		}

		public void action() {
			try {

				// Prepare the message
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				AID receiver = new AID("travelagent", false);
				

				msg.setSender(getAID());
				msg.addReceiver(receiver);
				msg.setLanguage(codec.getName());
				msg.setOntology(ontology.getName());

                                Pay pay = (Pay) act.getAction();
                                if(success){
                                    pay.setStatus(CreditCard.SUCCESS);
                                    System.out.println("Credit card company will inform the SUCCESSFULL payment, Receiver-"+receiver);
                                } else {
                                    pay.setStatus(CreditCard.FAILURE);
                                    System.out.println("Credit card company will inform payment FAILURE, Receiver-"+receiver);
                                }
                                
				// Fill the content
				Done d = new Done(act);
				manager.fillContent(msg, d);
				send(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
