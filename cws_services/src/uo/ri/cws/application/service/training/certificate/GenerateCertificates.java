package uo.ri.cws.application.service.training.certificate;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.CertificateRepository;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.VehicleTypeRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Certificate;
import uo.ri.cws.domain.Enrollment;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.VehicleType;

public class GenerateCertificates implements Command<Integer> {

	private MechanicRepository mr = Factory.repository.forMechanic();
	private VehicleTypeRepository vr = Factory.repository.forVehicleType();
	private CertificateRepository cr = Factory.repository.forCertificate();

	@Override
	public Integer execute() throws BusinessException {
		Integer numOfCertificates = 0;

		List<Mechanic> mechanics = mr.findAll();
		List<VehicleType> vehicleTypes = vr.findAll();
		for (Mechanic mechanic : mechanics) {
			for (VehicleType vehicleType : vehicleTypes) {
				if (getPassedHours(mechanic, vehicleType) >= vehicleType
						.getMinTrainingHours()
						&& !mechanic.isCertifiedFor(vehicleType)) {
					Certificate certificate = new Certificate(mechanic,
							vehicleType);
					numOfCertificates++;
					cr.add(certificate);
				}
			}
		}

		return numOfCertificates;
	}

	private double getPassedHours(Mechanic mechanic, VehicleType vehicleType) {
		double result = 0;
		for (Enrollment enroll : mechanic.getEnrollmentsFor(vehicleType)) {
			if (enroll.isPassed()) {
				result += enroll.getAttendedHoursFor(vehicleType);
			}
		}
		return result;
	}

}
