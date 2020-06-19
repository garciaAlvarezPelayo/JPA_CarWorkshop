package uo.ri.cws.application.service.workorder.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.VehicleRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.workorder.WorkOrderDto;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Vehicle;
import uo.ri.cws.domain.WorkOrder;

public class FindWorkOrdersByPlateNumber
		implements Command<List<WorkOrderDto>> {

	private String plate;
	private VehicleRepository vr = Factory.repository.forVehicle();
	private WorkOrderRepository wr = Factory.repository.forWorkOrder();

	public FindWorkOrdersByPlateNumber(String plate) {
		this.plate = plate;
	}

	@Override
	public List<WorkOrderDto> execute() throws BusinessException {
		BusinessCheck.isNotEmpty(plate, "You have not introduced a plate");
		Optional<Vehicle> v = vr.findByPlate(plate);
		BusinessCheck.exists(v, "That vehicle does not exist");
		List<WorkOrder> list = wr.findByPlateNumber(plate);
		return DtoAssembler.toWorkOrderDtoList(list);
	}

}
