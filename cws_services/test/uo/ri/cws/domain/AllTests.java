package uo.ri.cws.domain;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	WorkOrderTests.class, 
	InvoiceTests.class, 
	InterventionTest.class, 
	ChargeTests.class,
	SustitutionTests.class 
})

public class AllTests {

}
