package de.janheyd.propertyverifier;

import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;

public class ToStringPropertyVerifier<T, U> {

	private final Function<U, T> constructor;

	public ToStringPropertyVerifier(Function<U, T> constructor) {
		this.constructor = constructor;
	}

	public void verify(U value1, U value2) {
		List<U> values = asList(value1, value2);
		if (!(sameValueProducesSameToString(constructor, values)
				&& differentValuesProduceDifferentToString(constructor, values)))
			throw new RuntimeException("Constructor <=> ToString verification failed");
	}

	private boolean sameValueProducesSameToString(Function<U, T> constructor, List<U> values) {
		Function<U, String> function = constructor.andThen(Object::toString);
		return values.stream().allMatch(value -> function.apply(value).equals(function.apply(value)));
	}

	private boolean differentValuesProduceDifferentToString(Function<U, T> constructor, List<U> values) {
		long uniqueToStringValues = values.stream()
				.map(constructor)
				.map(Object::toString)
				.distinct().count();
		return uniqueToStringValues == values.size();
	}
}
