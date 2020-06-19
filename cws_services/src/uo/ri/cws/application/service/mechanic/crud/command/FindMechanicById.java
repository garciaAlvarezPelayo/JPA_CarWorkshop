package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class FindMechanicById implements Command<Optional<MechanicDto>> {

	private String id;
	private MechanicRepository repo = Factory.repository.forMechanic();

	public FindMechanicById(String id) {
		this.id = id;
	}

	public Optional<MechanicDto> execute() throws BusinessException {
		Optional<Mechanic> om = repo.findById(id);
		return om.map(m -> DtoAssembler.toDto(m));
	}

}
