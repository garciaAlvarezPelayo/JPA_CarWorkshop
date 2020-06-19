package uo.ri.cws.extended.course;

import static org.junit.Assert.assertFalse;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Test;

import uo.ri.cws.domain.Dedication;

public class DedicationConstructorTests {

	/**
	 * Dedication constructors must have package visibility
	 * 
	 * Dedication objects are only constructed from Course objects. 
	 * Course forms an aggregate of Dedication(s).
	 */
	@Test
	public void testDedicationConstructorPackageVisible() {
		Constructor<?>[] cons = Dedication.class.getConstructors();
		for(Constructor<?> c: cons) {
			int modifiers = c.getModifiers();
			assertFalse( Modifier.isPublic(modifiers) ); 
		}
	}

}
