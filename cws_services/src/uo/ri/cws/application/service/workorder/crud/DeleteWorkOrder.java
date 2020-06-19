package uo.ri.cws.application.service.workorder.crud;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.WorkOrder;

public class DeleteWorkOrder implements Command<Void> {

	private String wid;
	private WorkOrderRepository wr = Factory.repository.forWorkOrder();

	public DeleteWorkOrder(String id) {
		this.wid = id;
	}

	@Override
	public Void execute() throws BusinessException {
		BusinessCheck.isNotEmpty(wid, "You have not introduced a work order");
		Optional<WorkOrder> w = wr.findById(wid);
		BusinessCheck.exists(w, "That work order does not exist");
		BusinessCheck.isTrue(w.get().getInterventions().isEmpty(),
				"That work order can not be deleted");
		wr.remove(w.get());
		return null;
	}

}
