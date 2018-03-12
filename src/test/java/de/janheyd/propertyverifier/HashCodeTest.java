package de.janheyd.propertyverifier;

import org.junit.Test;

public class HashCodeTest {

	@Test
	public void verifyGoodHashCode() throws Exception {
		new PropertyVerifier()
				.withConstructor(HasGoodHashCode::new)
				.withHashCode()
				.verify(0, 1);
	}

	@Test(expected = RuntimeException.class)
	public void detectDistinctHashCodeForEqualObjects() throws Exception {
		new PropertyVerifier()
				.withConstructor(HasDefaultHashCode::new)
				.withHashCode()
				.verify(0, 1);
	}

	@Test(expected = RuntimeException.class)
	public void detectEqualHashCodeForGuaranteedDistinctObjects() throws Exception {
		new PropertyVerifier()
				.withConstructor(HasConstantHashCode::new)
				.withHashCodeDistinct()
				.verify(0, 1);
	}

	@Test
	public void verifyGoodGuaranteedDistinctHashcCode() throws Exception {
		new PropertyVerifier()
				.withConstructor(HasGoodHashCode::new)
				.withHashCodeDistinct()
				.verify(0, 1);
	}

	private class HasGoodHashCode {
		private int x;

		public HasGoodHashCode(int x) {
			this.x = x;
		}

		@Override
		public int hashCode() {
			return x;
		}
	}

	private class HasDefaultHashCode {
		public HasDefaultHashCode(int x) {
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}
	}

	private class HasConstantHashCode {
		public HasConstantHashCode(int x) {
		}

		@Override
		public int hashCode() {
			return 0;
		}
	}
}
