package de.janheyd.propertyverifier.feature;

import java.util.List;
import java.util.function.Function;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;


public class ToStringFeature<T, U> implements ObjectFeature<T, U> {

	@Override
	public void verify(Function<U, T> constructor, List<U> values) {
		verifySameValueProducesSameToString(constructor, values);
		verifyDifferentValuesProduceDifferentToString(constructor, values);
	}

	private void verifySameValueProducesSameToString(Function<U, T> constructor, List<U> values) {
		Function<U, String> function = constructor.andThen(Object::toString);
		List<U> brokenValues = values.stream()
				.filter(value -> !function.apply(value).equals(function.apply(value)))
				.collect(toList());
		if (!brokenValues.isEmpty()) {
			String explanation = "equal property values should produce equal toString values";
			String header = "ToString verification failed (" + explanation + "):\n";
			String template = "toString of object with property value %s is not equal to toString of another object "
					+ "with same property value";
			String details = brokenValues.stream()
					.map(value -> format(template, value))
					.collect(joining("\n"));
			throw new AssertionError(header + details);
		}
	}

	private void verifyDifferentValuesProduceDifferentToString(Function<U, T> constructor, List<U> values) {
		long uniqueToStringValues = values.stream()
				.map(constructor)
				.map(Object::toString)
				.distinct().count();
		if (uniqueToStringValues != values.size())
			throw new AssertionError("Constructor <=> ToString verification failed");
	}

}
