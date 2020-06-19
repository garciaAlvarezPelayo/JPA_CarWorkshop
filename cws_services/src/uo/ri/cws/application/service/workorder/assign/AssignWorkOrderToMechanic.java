package uo.ri.cws.application.service.workorder.assign;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.WorkOrder;

public class AssignWorkOrderToMechanic implements Command<Void> {

	private String woId;
	private String mechanicId;
	private WorkOrderRepository wr = Factory.repository.forWorkOrder();
	private MechanicRepository mr = Factory.repository.forMechanic();

	public AssignWorkOrderToMechanic(String woId, String mechanicId) {
		this.woId = woId;
		this.mechanicId = mechanicId;
	}

	@Override
	public Void execute() throws BusinessException {
		BusinessCheck.isNotEmpty(woId, "You have not introduced a work order");
		BusinessCheck.isNotEmpty(mechanicId,
				"You have not introduced a mechanic");
		Optional<WorkOrder> w = wr.findById(woId);
		Optional<Mechanic> m = mr.findById(mechanicId);
		BusinessCheck.exists(w, "The work order does not exist");
		BusinessCheck.exists(m, "the mechanic does not exist");
		BusinessCheck.isTrue(
				m.get().isCertifiedFor(w.get().getVehicle().getVehicleType()),
				"that mechanic is not certified for that vehicle type");

		try {
			w.get().assignTo(m.get());
		} catch (IllegalStateException e) {
			throw new BusinessException(e.getMessage());
		}

		return null;
	}

}
