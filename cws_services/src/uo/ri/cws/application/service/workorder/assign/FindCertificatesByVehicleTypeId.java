package uo.ri.cws.application.service.workorder.assign;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.VehicleTypeRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CertificateDto;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Certificate;
import uo.ri.cws.domain.VehicleType;

public class FindCertificatesByVehicleTypeId
		implements Command<List<CertificateDto>> {

	private String tid;
	private VehicleTypeRepository tr = Factory.repository.forVehicleType();

	public FindCertificatesByVehicleTypeId(String id) {
		this.tid = id;
	}

	@Override
	public List<CertificateDto> execute() throws BusinessException {
		BusinessCheck.isNotEmpty(tid, "You have not introduced a vehicle type");
		Optional<VehicleType> t = tr.findById(tid);
		BusinessCheck.exists(t, "That vehicle type does not exist");

		List<Certificate> list = new ArrayList<>(t.get().getCertificates());
		return DtoAssembler.toCertificateDtoList(list);
	}

}
