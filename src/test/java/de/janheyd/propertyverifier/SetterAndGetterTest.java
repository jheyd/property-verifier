package de.janheyd.propertyverifier;

import org.junit.Test;

public class SetterAndGetterTest {

	@Test
	public void canVerifyConstructorAndGetter() throws Exception {
		new PropertyVerifier()
				.withSetter(HasSetterAndGetter::setX, HasSetterAndGetter::new)
				.withGetter(HasSetterAndGetter::getX)
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
}
