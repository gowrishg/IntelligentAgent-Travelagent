package ia.travelagency.ontology;

import jade.content.Concept;
import java.io.Serializable;

public class TourPackageDetail implements Serializable, Concept {
	int id;
	String desc;
	int cost;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "TourPackageDetail [cost=" + cost + ", desc=" + desc + ", id="
				+ id + "]";
	}
}