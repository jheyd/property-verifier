package de.janheyd.propertyverifier;

import org.junit.Test;

public class GetterAndToStringTest {

	@Test
	public void canVerifyGetterAndToString() throws Exception {
		new PropertyVerifier()
				.withConstructor(Foo::new)
				.withGetter(Foo::getX)
				.withToString()
				.verify(0, 1);
	}

	@Test
	public void canVerifyToStringAndGetter() throws Exception {
		new PropertyVerifier()
				.withConstructor(Foo::new)
				.withToString()
				.withGetter(Foo::getX)
				.verify(0, 1);
	}

	private class Foo {
		private int x;

		public Foo(int x) {
			this.x = x;
		}

		public int getX() {
			return x;
		}

		@Override
		public String toString() {
			return Integer.toString(x);
		}
	}
}
