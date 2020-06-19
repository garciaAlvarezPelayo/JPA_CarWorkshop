package uo.ri.cws.application.service.workorder.crud;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.workorder.WorkOrderDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.WorkOrder;

public class FindWorkOrderById implements Command<Optional<WorkOrderDto>> {

	private String wid;
	private WorkOrderRepository wr = Factory.repository.forWorkOrder();

	public FindWorkOrderById(String woId) {
		this.wid = woId;
	}

	@Override
	public Optional<WorkOrderDto> execute() throws BusinessException {
		Optional<WorkOrder> om = wr.findById(wid);
		return om.map(m -> DtoAssembler.toDto(m));
	}

}
