package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import alb.util.assertion.Argument;

@Entity
@Table(name = "tcashes")
public class Cash extends PaymentMean {

	Cash() {
	}

	public Cash(Client client) {
		Argument.isNotNull(client);
		Associations.Pay.link(this, client);
	}

	@Override
	public String toString() {
		return "Cash " + super.toString();
	}

}
