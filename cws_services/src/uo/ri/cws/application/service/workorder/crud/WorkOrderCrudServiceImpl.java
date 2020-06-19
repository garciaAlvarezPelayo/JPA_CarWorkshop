package uo.ri.cws.application.service.workorder.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService;
import uo.ri.cws.application.service.workorder.WorkOrderDto;
import uo.ri.cws.application.util.command.CommandExecutor;

public class WorkOrderCrudServiceImpl implements WorkOrderCrudService {

	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public WorkOrderDto registerNew(WorkOrderDto dto) throws BusinessException {
		return executor.execute(new RegisterNew(dto));
	}

	@Override
	public void updateWorkOrder(WorkOrderDto dto) throws BusinessException {
		executor.execute(new UpdateWorkOrder(dto));
	}

	@Override
	public void deleteWorkOrder(String id) throws BusinessException {
		executor.execute(new DeleteWorkOrder(id));
	}

	@Override
	public Optional<WorkOrderDto> findWorkOrderById(String woId)
			throws BusinessException {
		return executor.execute(new FindWorkOrderById(woId));
	}

	@Override
	public List<WorkOrderDto> findWorkOrdersByVehicleId(String id)
			throws BusinessException {
		return null;
	}

	@Override
	public List<WorkOrderDto> findWorkOrdersByPlateNumber(String plate)
			throws BusinessException {
		return executor.execute(new FindWorkOrdersByPlateNumber(plate));
	}

}
