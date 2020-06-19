package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.Argument;

@Entity
@Table(
		uniqueConstraints = { @UniqueConstraint(
												columnNames = { "SPAREPART_ID",
														"INTERVENTION_ID" }) },
		name = "tsubstitutions")
public class Substitution extends BaseEntity {
	@ManyToOne
	private SparePart sparePart;
	@ManyToOne
	private Intervention intervention;
	private int quantity;

	Substitution() {
	}

	public Substitution(SparePart sparePart, Intervention intervention) {
		Argument.isNotNull(sparePart);
		Argument.isNotNull(intervention);
		Associations.Sustitute.link(sparePart, this, intervention);
	}

	public Substitution(SparePart sparePart, Intervention intervention,
			int quantity) {
		this(sparePart, intervention);
		Argument.isTrue(quantity > 0);
		this.quantity = quantity;
	}

	public double getImporte() {
		return this.sparePart.getPrice() * this.quantity;
	}

	public SparePart getSparePart() {
		return sparePart;
	}

	public Intervention getIntervention() {
		return intervention;
	}

	public int getQuantity() {
		return quantity;
	}

	void _setSparePart(SparePart sparePart) {
		this.sparePart = sparePart;
	}

	void _setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

	@Override
	public String toString() {
		return "Substitution sparePart=" + sparePart + ", intervention="
				+ intervention + ", quantity=" + quantity;
	}

}
