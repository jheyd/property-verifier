package de.janheyd.propertyverifier;

import org.junit.Test;

public class HashCodeTest {

	@Test
	public void verifyGoodHashCode() throws Exception {
		new PropertyVerifier()
				.withConstructor(Foo::new)
				.withHashCode()
				.verify(0, 1);
	}

	@Test(expected = RuntimeException.class)
	public void detectDistinctHashCodeForEqualObjects() throws Exception {
		new PropertyVerifier()
				.withConstructor(Foo2::new)
				.withHashCode()
				.verify(0, 1);
	}

	private class Foo {
		private int x;

		public Foo(int x) {
			this.x = x;
		}

		@Override
		public int hashCode() {
			return x;
		}
	}

	private class Foo2 {
		public Foo2(int x) {
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}
	}
}
