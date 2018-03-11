package de.janheyd.propertyverifier;

import de.janheyd.propertyverifier.feature.ToStringFeature;

import java.util.function.Function;

import static java.util.Arrays.asList;

public class ConstructablePropertyVerifier<T, U> {

	private Function<U, T> constructor;

	public ConstructablePropertyVerifier(Function<U, T> constructor) {
		this.constructor = constructor;
	}

	public VerifiablePropertyVerifier<T, U> withGetter(Function<T, U> getter) {
		return new VerifiablePropertyVerifier<>(constructor, getter);
	}

	public VerifiablePropertyVerifier<T, U> withToString() {
		return new VerifiablePropertyVerifier<T, U>(constructor, asList(new ToStringFeature<>()));
	}
}
