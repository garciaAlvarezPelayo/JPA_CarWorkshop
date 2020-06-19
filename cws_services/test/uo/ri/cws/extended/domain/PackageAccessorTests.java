package uo.ri.cws.extended.domain;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import uo.ri.cws.domain.Cash;
import uo.ri.cws.domain.Certificate;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.Course;
import uo.ri.cws.domain.CreditCard;
import uo.ri.cws.domain.Dedication;
import uo.ri.cws.domain.Enrollment;
import uo.ri.cws.domain.Intervention;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.PaymentMean;
import uo.ri.cws.domain.SparePart;
import uo.ri.cws.domain.Substitution;
import uo.ri.cws.domain.Vehicle;
import uo.ri.cws.domain.VehicleType;
import uo.ri.cws.domain.Voucher;
import uo.ri.cws.domain.WorkOrder;

public class PackageAccessorTests {

	@Test
	public void test() {
		Class<?>[] classes = {
			Course.class,
			Dedication.class,
			Mechanic.class,
			Enrollment.class,
			VehicleType.class,
			Certificate.class,
			Invoice.class,
			WorkOrder.class,
			Client.class,
			Vehicle.class,
			Intervention.class,
			Substitution.class,
			SparePart.class,
			PaymentMean.class,
			CreditCard.class,
			Voucher.class,
			Cash.class
		};
		
		for(Class<?> clazz : classes) {
			assertNotPublicAssociationAccessors( clazz );
		}
	}

	private void assertNotPublicAssociationAccessors(Class<?> clazz) {
		Set<Method> methods = filter(clazz, "_set", "_get");
		for(Method m: methods) {
			int modifiers = m.getModifiers();
			assertTrue( "bad public modifer on " + clazz.getSimpleName(),
					! Modifier.isPublic(modifiers) 
				);
		}
	}

	private Set<Method> filter(Class<?> clazz, String... prefixes) {
		Set<Method> res = new HashSet<>();

		Method[] methods = clazz.getDeclaredMethods();
		for(Method m: methods) {
			for(String pf: prefixes) {
				if ( m.getName().startsWith( pf ) ) {
					res.add( m );
				}
			}
		}

		return res;
	}

}
