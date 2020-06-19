package uo.ri.cws.application.service.training.report;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.VehicleTypeRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.TrainingHoursRow;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Enrollment;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.VehicleType;

public class FindTrainingByVehicleTypeAndMechanic
		implements Command<List<TrainingHoursRow>> {

	private VehicleTypeRepository tr = Factory.repository.forVehicleType();
	private MechanicRepository mr = Factory.repository.forMechanic();

	@Override
	public List<TrainingHoursRow> execute() throws BusinessException {
		List<TrainingHoursRow> rows = new ArrayList<>();

		List<VehicleType> types = tr.findAll();
		List<Mechanic> mechanics = mr.findAll();

		for (VehicleType t : types) {
			for (Mechanic m : mechanics) {
				int total = 0;
				for (Enrollment enrollment : m.getEnrollmentsFor(t)) {
					total += enrollment.getAttendedHoursFor(t);
				}
				if (total != 0) {
					TrainingHoursRow row = new TrainingHoursRow();
					row.vehicleTypeName = t.getName();
					row.mechanicFullName = m.getName() + " " + m.getSurname();
					row.enrolledHours = total;
					rows.add(row);
				}
			}
		}

		return rows;
	}

}
