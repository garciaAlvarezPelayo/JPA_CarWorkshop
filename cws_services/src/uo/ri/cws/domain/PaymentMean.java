package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tpaymentmeans")
public abstract class PaymentMean extends BaseEntity {
	private double accumulated = 0;

	@ManyToOne
	private Client client;
	@OneToMany(mappedBy = "paymentMean")
	private Set<Charge> charges = new HashSet<>();

	public void pay(double importe) {
		this.accumulated += importe;
	}

	public Client getClient() {
		return client;
	}

	void _setClient(Client client) {
		this.client = client;
	}

	public double getAccumulated() {
		return accumulated;
	}

	public Set<Charge> getCharges() {
		return new HashSet<>(charges);
	}

	Set<Charge> _getCharges() {
		return charges;
	}

	@Override
	public String toString() {
		return "PaymentMean accumulated=" + accumulated;
	}

}
