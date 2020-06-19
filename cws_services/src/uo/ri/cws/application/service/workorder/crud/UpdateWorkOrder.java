package uo.ri.cws.application.service.workorder.crud;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.workorder.WorkOrderDto;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.WorkOrder;

public class UpdateWorkOrder implements Command<Void> {

	private WorkOrderDto dto;
	private WorkOrderRepository wr = Factory.repository.forWorkOrder();

	public UpdateWorkOrder(WorkOrderDto dto) {
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		checkDto(dto);
		Optional<WorkOrder> ow = wr.findById(dto.id);
		BusinessCheck.exists(ow, "The work order does not exist");

		WorkOrder w = ow.get();
		w.setDescription(dto.description);

		return null;
	}

	private void checkDto(WorkOrderDto dto) throws BusinessException {
		BusinessCheck.isNotEmpty(dto.description,
				"You have not introduced a description");
		BusinessCheck.isNotEmpty(dto.id,
				"You have not introduced a work order id");
	}

}
