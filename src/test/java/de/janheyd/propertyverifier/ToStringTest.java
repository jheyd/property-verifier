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

	public static class HasToString {
		private int x;

		public HasToString(int x) {
			this.x = x;
		}

		@Override
		public String toString() {
			return "HasToString{" +
					"x=" + x +
					'}';
		}
	}
}
