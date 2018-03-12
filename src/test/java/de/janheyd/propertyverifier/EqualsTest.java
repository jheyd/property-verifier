package de.janheyd.propertyverifier;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.equalTo;


public class EqualsTest {

	@Rule
	public ExpectedException expected = ExpectedException.none();

	@Test
	public void canVerifyGoodEquals() throws Exception {
		new PropertyVerifier()
				.withConstructor(HasGoodEquals::new)
				.withEquals()
				.verify(0, 1);
	}

	@Test
	public void canDetectConstantEquals() throws Exception {
		expected.expect(AssertionError.class);
		expected.expectMessage(equalTo(
				"Equals verification failed (distinct property values should not produce equal objects):\n"
						+ "object for property value 0 is equal to object for property values [1]\n"
						+ "object for property value 1 is equal to object for property values [0]"
		));

		new PropertyVerifier()
				.withConstructor(HasBadEquals::new)
				.withEquals()
				.verify(0, 1);
	}

	@Test
	public void canDetectDefaultEquals() throws Exception {
		expected.expect(AssertionError.class);
		expected.expectMessage(equalTo(
				"Equals verification failed (equal property values should produce equal objects):\n"
						+ "object with property value 0 is not equal to another object with same property value\n"
						+ "object with property value 1 is not equal to another object with same property value"
		));

		new PropertyVerifier()
				.withConstructor(HasDefaultEquals::new)
				.withEquals()
				.verify(0, 1);
	}

	private class HasGoodEquals {
		private int x;

		public HasGoodEquals(int x) {
			this.x = x;
		}

		@Override
		public boolean equals(Object o) {
			return x == ((HasGoodEquals) o).x;
		}

	}

	private class HasBadEquals {

		public HasBadEquals(int x) {
		}

		@Override
		public boolean equals(Object o) {
			return true;
		}
	}

	private class HasDefaultEquals {

		public HasDefaultEquals(int x) {
		}
	}
}
