package uo.ri.cws.application.service.workorder;

import java.util.Date;

public class WorkOrderDto {

	public String id;
	public Long version;

	public String vehicleId;
	public String description;
	public Date date;
	public double total;
	public String status;

	// might be null
	public String mechanicId;
	public String invoiceId;

}
