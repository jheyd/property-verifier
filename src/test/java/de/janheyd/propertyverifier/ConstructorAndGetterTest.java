package de.janheyd.propertyverifier;

import org.junit.Test;

public class ConstructorAndGetterTest {

	@Test
	public void canVerifyCorrectConstructorAndGetter() throws Exception {
		new PropertyVerifier()
				.withConstructor(HasConstrutorAndGetter::new)
				.withGetter(HasConstrutorAndGetter::getX)
				.verify(0, 1);
	}

	@Test(expected = RuntimeException.class)
	public void canDetectIncorrectGetter() throws Exception {
		new PropertyVerifier()
				.withConstructor(HasConstantGetter::new)
				.withGetter(HasConstantGetter::getX)
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

	private class HasConstantGetter {
		public HasConstantGetter(int x) {
		}

		public int getX() {
			return 0;
		}
	}
}
