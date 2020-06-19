package uo.ri.cws.application.service.training;

import java.util.Date;

import uo.ri.cws.application.service.mechanic.MechanicDto;
import uo.ri.cws.application.service.vehicletype.VehicleTypeDto;

public class CertificateDto {

	public String id;
	public Long version;

	public MechanicDto mechanic;
	public VehicleTypeDto vehicleType;
	public Date obtainedAt;

}
