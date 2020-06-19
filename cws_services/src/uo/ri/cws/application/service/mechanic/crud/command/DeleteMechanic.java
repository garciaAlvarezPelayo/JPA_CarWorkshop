package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class DeleteMechanic implements Command<Void> {

	private String mechanicId;
	private MechanicRepository repo = Factory.repository.forMechanic();

	public DeleteMechanic(String idMecanico) {
		this.mechanicId = idMecanico;
	}

	public Void execute() throws BusinessException {
		Optional<Mechanic> om = repo.findById(mechanicId);
		BusinessCheck.exists(om, "The mechanic does not exist.");

		Mechanic m = om.get();
		BusinessCheck.isTrue(m.getAssigned().isEmpty(),
				"The mechanic has work assigned.");
		BusinessCheck.isTrue(m.getInterventions().isEmpty(),
				"The mechanic has interventions.");
		BusinessCheck.isTrue(m.getCertificates().isEmpty(),
				"The mechanic has certificates.");
		BusinessCheck.isTrue(m.getEnrollments().isEmpty(),
				"The mechanic has enrollments.");

		repo.remove(m);

		return null;
	}

}
