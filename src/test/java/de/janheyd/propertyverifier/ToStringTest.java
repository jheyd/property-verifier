package de.janheyd.propertyverifier;

import org.junit.Test;

public class ToStringTest {

	@Test
	public void canVerifyConstructorAndGetter() throws Exception {
		new PropertyVerifier()
				.withConstructor(HasToString::new)
				.withToString()
				.verify(0, 1);
	}

	@Test(expected = RuntimeException.class)
	public void canDetectConstantToString() throws Exception {
		new PropertyVerifier()
				.withConstructor(HasConstantToString::new)
				.withToString()
				.verify(0, 1);
	}

	@Test(expected = RuntimeException.class)
	public void canDetectDefaultToString() throws Exception {
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
