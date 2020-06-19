package uo.ri.cws.application.service.workorder.crud;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.VehicleRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.workorder.WorkOrderDto;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Vehicle;
import uo.ri.cws.domain.WorkOrder;

public class RegisterNew implements Command<WorkOrderDto> {

	private WorkOrderDto dto;
	private WorkOrderRepository wr = Factory.repository.forWorkOrder();
	private VehicleRepository vr = Factory.repository.forVehicle();

	public RegisterNew(WorkOrderDto dto) {
		this.dto = dto;
	}

	@Override
	public WorkOrderDto execute() throws BusinessException {
		checkValidData(dto);
		Optional<Vehicle> v = vr.findById(dto.vehicleId);
		BusinessCheck.exists(v, "That vehicle does not exist");
		WorkOrder w = new WorkOrder(v.get(), dto.description);
		wr.add(w);

		dto.id = w.getId();
		dto.date = w.getDate();
		dto.status = w.getStatus().toString();

		return dto;
	}

	private void checkValidData(WorkOrderDto dto) throws BusinessException {
		BusinessCheck.isNotEmpty(dto.description,
				"The DNI for the mechanic cannot be empty.");
		BusinessCheck.isNotEmpty(dto.vehicleId,
				"The name for the mechanic cannot be empty.");
	}

}
