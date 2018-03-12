package de.janheyd.propertyverifier;

import org.junit.Test;

public class EqualsTest {

	@Test
	public void canVerifyGoodEquals() throws Exception {
		new PropertyVerifier()
				.withConstructor(HasGoodEquals::new)
				.withEquals()
				.verify(0, 1);
	}

	@Test(expected = AssertionError.class)
	public void canDetectConstantEquals() throws Exception {
		new PropertyVerifier()
				.withConstructor(HasBadEquals::new)
				.withEquals()
				.verify(0, 1);
	}

	@Test(expected = AssertionError.class)
	public void canDetectDefaultEquals() throws Exception {
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
