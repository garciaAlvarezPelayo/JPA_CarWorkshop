package uo.ri.cws.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import alb.util.assertion.Argument;
import alb.util.date.Dates;

@Entity
@Table(name = "tcreditcards")
public class CreditCard extends PaymentMean {
	@Column(unique = true)
	private String number;
	private String type;
	@Temporal(TemporalType.DATE)
	private Date validThru;

	CreditCard() {
	}

	public CreditCard(String number) {
		Argument.isNotEmpty(number);
		this.number = number;
	}

	public CreditCard(String number, String type, Date validThru) {
		this(number);
		Argument.isNotEmpty(type);
		Argument.isNotNull(validThru);
		this.type = type;
		this.validThru = new Date(validThru.getTime());
	}

	@Override
	public void pay(double importe) {
		if (Dates.isBefore(validThru, Dates.today()))
			throw new IllegalStateException("The credit card is out of date");
		super.pay(importe);
	}

	public String getNumber() {
		return number;
	}

	public String getType() {
		return type;
	}

	public Date getValidThru() {
		return new Date(validThru.getTime());
	}

	@Override
	public String toString() {
		return "CreditCard number=" + number + ", type=" + type + ", validThru="
				+ validThru + ", toString()=" + super.toString();
	}

}
