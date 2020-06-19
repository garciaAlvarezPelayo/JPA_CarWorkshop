package uo.ri.cws.extended;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	uo.ri.cws.extended.domain.PackageAccessorTests.class,
	uo.ri.cws.extended.course.ConstructorValidationTests.class,
	uo.ri.cws.extended.course.AddDedicationsTests.class,
	uo.ri.cws.extended.course.ClearDedicationsTests.class,
	uo.ri.cws.extended.course.DedicationConstructorTests.class,
	uo.ri.cws.extended.certificate.ConstructorTests.class,
	uo.ri.cws.extended.enrollment.AttendancePassedTests.class,
	uo.ri.cws.extended.enrollment.GetAttendedHoursForTests.class,
	uo.ri.cws.extended.mechanic.GetEnrollmentsForTests.class,
	uo.ri.cws.extended.mechanic.IsCertifiedForTests.class
})
public class AllTests { }
