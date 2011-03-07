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

import jade.content.AgentAction;
import jade.core.AID;


public class Pay implements AgentAction {
	private AID buyer;
	private TravelDetail travelDetail;
	private CreditCard creditCard;
        private String status = "";
	
	public Pay() {
	}
	
	public Pay(AID buyer, TravelDetail travelDetail, CreditCard cc) {
		setBuyer(buyer);
		setTravelDetail(travelDetail);
		setCreditCard(cc);
	}
	
	public AID getBuyer() {
		return buyer;
	}
	
	public void setBuyer(AID id) {
		buyer = id;
	}
	
	public TravelDetail getTravelDetail() {
		return travelDetail;
	}

	public void setTravelDetail(TravelDetail travelDetail) {
		this.travelDetail = travelDetail;
	}
	
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
}
