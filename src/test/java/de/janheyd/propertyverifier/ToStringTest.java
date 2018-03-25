package de.janheyd.propertyverifier;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.equalTo;


public class ToStringTest {

	@Rule
	public ExpectedException expected = ExpectedException.none();

	@Test
	public void canVerifyConstructorAndToString() throws Exception {
		new PropertyVerifier()
				.withConstructor(HasToString::new)
				.withToString()
				.verify(0, 1);
	}

	@Test
	public void canDetectConstantToString() throws Exception {
		expected.expect(AssertionError.class);
		// TODO improve this message
		expected.expectMessage(equalTo("Constructor <=> ToString verification failed"));

		new PropertyVerifier()
				.withConstructor(HasConstantToString::new)
				.withToString()
				.verify(0, 1);
	}

	@Test
	public void canDetectDefaultToString() throws Exception {
		expected.expect(AssertionError.class);
		expected.expectMessage(equalTo(
				"ToString verification failed (equal property values should produce equal toString values):\n"
						+ "toString of object with property value 0 is not equal to toString of another object with "
						+ "same property value\n"
						+ "toString of object with property value 1 is not equal to toString of another object with "
						+ "same property value"
		));

		new PropertyVerifier()
				.withConstructor(HasDefaultToString::new)
				.withToString()
				.verify(0, 1);
	}

	public static class HasToString {
		private int x;

		public HasToString(int x) {
			this.x = x;
		}

		@Override
		public String toString() {
			return Integer.toString(x);
		}
	}

	public static class HasConstantToString {
		public HasConstantToString(int x) {
		}

		@Override
		public String toString() {
			return "";
		}
	}

	public static class HasDefaultToString {
		public HasDefaultToString(int x) {
		}
	}
}
