package de.janheyd.propertyverifier;

import java.util.function.Function;
public class PropertyVerifier {

	public <T, U> ConstructablePropertyVerifier<T, U> withConstructor(Function<U, T> constructor) {
		return new ConstructablePropertyVerifier<>(constructor);
	}

}
