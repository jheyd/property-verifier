package de.janheyd.propertyverifier;

import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;

public class VerifiablePropertyVerifier<T, U> {

	private final Function<U, T> constructor;
	private final Function<T, U> getter;

	public VerifiablePropertyVerifier(Function<U, T> constructor, Function<T, U> getter) {
		this.constructor = constructor;
		this.getter = getter;
	}

	public void verify(U value1, U value2){
		List<U> values = asList(value1, value2);
		if(!values.stream().allMatch(value -> constructor.andThen(getter).apply(value).equals(value)))
			throw new RuntimeException("Constructor <=> Getter verification failed");
	}
}
