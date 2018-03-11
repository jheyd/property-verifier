package de.janheyd.propertyverifier;

import org.junit.Test;

public class SetterAndGetterTest {

	@Test
	public void canVerifySetterAndGetter() throws Exception {
		new PropertyVerifier()
				.withSetter(HasSetterAndGetter::setX, HasSetterAndGetter::new)
				.withGetter(HasSetterAndGetter::getX)
				.verify(0, 1);
	}

	@Test(expected = RuntimeException.class)
	public void canDetectConstantGetter() throws Exception {
		new PropertyVerifier()
				.withSetter(HasConstantGetter::setX, HasConstantGetter::new)
				.withGetter(HasConstantGetter::getX)
				.verify(0, 1);
	}

	public static class HasSetterAndGetter {
		private int x;

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}
	}

	private class HasConstantGetter {
		public void setX(int x) {
		}

		public int getX() {
			return 0;
		}
	}
}
