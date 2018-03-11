package de.janheyd.propertyverifier.feature;

import java.util.List;
import java.util.function.Function;

public class ToStringFeature<T, U> implements ObjectFeature<T, U> {

	@Override
	public void verify(Function<U, T> constructor, List<U> values) {
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
