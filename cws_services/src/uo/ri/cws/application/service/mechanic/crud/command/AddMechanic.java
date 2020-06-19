package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicDto;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class AddMechanic implements Command<MechanicDto> {

	private MechanicDto dto;
	private MechanicRepository repo = Factory.repository.forMechanic();

	public AddMechanic(MechanicDto mecanico) {
		this.dto = mecanico;
	}

	@Override
	public MechanicDto execute() throws BusinessException {
		checkValidData(dto);
		checkDoesNotExist(dto.dni);
		Mechanic m = new Mechanic(dto.dni, dto.name, dto.surname);
		repo.add(m);

		dto.id = m.getId();
		return dto;
	}

	private void checkValidData(MechanicDto dto) throws BusinessException {
		BusinessCheck.isNotEmpty(dto.dni,
				"The DNI for the mechanic cannot be empty.");
		BusinessCheck.isNotEmpty(dto.name,
				"The name for the mechanic cannot be empty.");
		BusinessCheck.isNotEmpty(dto.surname,
				"The surname for the mechanic cannot be empty.");
	}

	private void checkDoesNotExist(String dni) throws BusinessException {
		Optional<Mechanic> om = repo.findByDni(dni);
		BusinessCheck.isFalse(om.isPresent(), "The mechanic already exists.");
	}

}
