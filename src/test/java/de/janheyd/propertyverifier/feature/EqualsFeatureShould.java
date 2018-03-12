package de.janheyd.propertyverifier.feature;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;


public class EqualsFeatureShould {

	@Rule
	public ExpectedException expected = ExpectedException.none();

	@Test
	public void produceErrorMessageForEqualPropertyProducesDistinctObjects() {
		expected.expect(AssertionError.class);
		expected.expectMessage(equalTo(
				"Equals verification failed (equal property values should produce equal objects):\n"
						+ "object with property value 0 is not equal to another object with same property value\n"
						+ "object with property value 1 is not equal to another object with same property value"
		));

		new EqualsFeature<HasDefaultEquals, Integer>().verify(HasDefaultEquals::new, asList(0, 1));
	}

	@Test
	public void produceErrorMessageForDistinctPropertiesProduceEqualObjects() {
		expected.expect(AssertionError.class);
		expected.expectMessage(equalTo(
				"Equals verification failed (distinct property values should not produce equal objects):\n"
						+ "object for property value 0 is equal to object for property values [1]\n"
						+ "object for property value 1 is equal to object for property values [0]"
		));

		new EqualsFeature<HasConstantEquals, Integer>().verify(HasConstantEquals::new, asList(0, 1));
	}

	private class HasDefaultEquals {

		public HasDefaultEquals(int x) {
		}
	}

	private class HasConstantEquals {

		public HasConstantEquals(int x) {
		}

		@Override
		public boolean equals(Object obj) {
			return true;
		}
	}
}
