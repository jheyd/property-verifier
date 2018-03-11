package de.janheyd.propertyverifier;

import java.util.function.Function;

public class ConstructablePropertyVerifier<T, U> {

	private Function<U, T> constructor;

	public ConstructablePropertyVerifier(Function<U, T> constructor) {
		this.constructor = constructor;
	}

	public VerifiablePropertyVerifier<T, U> withGetter(Function<T, U> getter) {
		return new VerifiablePropertyVerifier<>(constructor).withGetter(getter);
	}

	public VerifiablePropertyVerifier<T, U> withToString() {
		return new VerifiablePropertyVerifier<>(constructor).withToString();
	}

	public VerifiablePropertyVerifier<T, U> withEquals() {
		return new VerifiablePropertyVerifier<>(constructor).withEquals();
	}
}
