package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoiceDto;
import uo.ri.cws.application.util.BusinessCheck;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.WorkOrder;

public class CreateInvoiceFor implements Command<InvoiceDto> {

	private List<String> workOrderIds;
	private InvoiceRepository repoInv = Factory.repository.forInvoice();
	private WorkOrderRepository repoWo = Factory.repository.forWorkOrder();

	public CreateInvoiceFor(List<String> workOrderIds) {
		this.workOrderIds = workOrderIds;
	}

	@Override
	public InvoiceDto execute() throws BusinessException {
		List<WorkOrder> lwo = repoWo.findByIds(workOrderIds);
		BusinessCheck.isFalse(lwo.isEmpty(),
				"The work orders received do not exist in the database.");

		Long invNumber = repoInv.getNextInvoiceNumber();
		BusinessCheck.isTrue(invNumber >= 0,
				"There was an error retrieving the last invoice number.");
		Invoice inv = new Invoice(invNumber, lwo);

		repoInv.add(inv);
		Optional<Invoice> oinv = repoInv.findByNumber(invNumber);
		BusinessCheck.exists(oinv, "The invoice was not created.");
		InvoiceDto dto = DtoAssembler.toDto(oinv.get());

		return dto;
	}

}
