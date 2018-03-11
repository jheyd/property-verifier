package de.janheyd.propertyverifier;

import java.util.function.Function;

public class ConstructablePropertyVerifier<T, U> {

	private Function<U, T> constructor;

	public ConstructablePropertyVerifier(Function<U, T> constructor) {
		this.constructor = constructor;
	}

	public VerifiablePropertyVerifier<T, U> withGetter(Function<T, U> getter) {
		return new VerifiablePropertyVerifier<>(constructor, getter);
	}

}
