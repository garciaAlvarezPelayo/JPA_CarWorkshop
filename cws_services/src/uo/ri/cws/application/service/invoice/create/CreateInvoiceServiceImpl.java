package uo.ri.cws.application.service.invoice.create;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.CreateInvoiceService;
import uo.ri.cws.application.service.invoice.InvoiceDto;
import uo.ri.cws.application.service.invoice.create.command.CreateInvoiceFor;
import uo.ri.cws.application.service.workorder.WorkOrderDto;
import uo.ri.cws.application.util.command.CommandExecutor;

public class CreateInvoiceServiceImpl implements CreateInvoiceService {

	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public InvoiceDto createInvoiceFor(List<String> woIds)
			throws BusinessException {

		return executor.execute(new CreateInvoiceFor(woIds));
	}

	@Override
	public List<WorkOrderDto> findWorkOrdersByClientDni(String dni)
			throws BusinessException {
		return null;
	}

}
