package de.janheyd.propertyverifier;

import org.junit.Test;

public class ConstructorAndGetterTest {

	@Test
	public void canVerifyConstructorAndGetter() throws Exception {
		new PropertyVerifier()
				.withConstructor(HasConstrutorAndGetter::new)
				.withGetter(HasConstrutorAndGetter::getX)
				.verify(0, 1);
	}

	public static class HasConstrutorAndGetter {
		private int x;

		public HasConstrutorAndGetter(int x) {
			this.x = x;
		}

		public int getX() {
			return x;
		}
	}
}
