package uo.ri.cws.application.service.invoice;

public abstract class PaymentMeanDto {
	public String id;
	public Long version;

	public String clientId;
	public Double accumulated;
}
