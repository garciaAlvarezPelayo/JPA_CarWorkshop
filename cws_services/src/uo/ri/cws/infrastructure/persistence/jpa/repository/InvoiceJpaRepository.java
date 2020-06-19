package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.Optional;

import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class InvoiceJpaRepository extends BaseJpaRepository<Invoice>
		implements InvoiceRepository {

	@Override
	public Optional<Invoice> findByNumber(Long numero) {
		return Jpa.getManager()
				.createNamedQuery("Invoice.findByNumber", Invoice.class)
				.setParameter(1, numero).getResultStream().findFirst();
	}

	@Override
	public Long getNextInvoiceNumber() {
		Optional<Long> i = Jpa.getManager()
				.createNamedQuery("Invoice.getNextInvoiceNumber", Long.class)
				.getResultStream().findFirst();
		Long l = i.get();
		return l;
	}

}
