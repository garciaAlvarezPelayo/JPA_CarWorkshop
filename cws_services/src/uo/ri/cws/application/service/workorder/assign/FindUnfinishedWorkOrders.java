package uo.ri.cws.application.service.workorder.assign;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.workorder.WorkOrderDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.cws.domain.WorkOrder.WorkOrderStatus;

public class FindUnfinishedWorkOrders implements Command<List<WorkOrderDto>> {

	private WorkOrderRepository wr = Factory.repository.forWorkOrder();

	@Override
	public List<WorkOrderDto> execute() throws BusinessException {
		List<WorkOrder> list = new ArrayList<>();
		for (WorkOrder workOrder : wr.findAll()) {
			if (workOrder.getStatus().equals(WorkOrderStatus.OPEN)
					|| workOrder.getStatus().equals(WorkOrderStatus.ASSIGNED)) {
				list.add(workOrder);
			}
		}

		return DtoAssembler.toWorkOrderDtoList(list);
	}

}
