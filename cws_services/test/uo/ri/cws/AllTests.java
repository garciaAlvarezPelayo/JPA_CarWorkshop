package uo.ri.cws;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	uo.ri.cws.associations.AllTests.class,
	uo.ri.cws.domain.AllTests.class,
	uo.ri.cws.persistence.PersistenceTest.class,
	uo.ri.cws.extended.AllTests.class
})
public class AllTests { }
