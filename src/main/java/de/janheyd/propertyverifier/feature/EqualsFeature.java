package de.janheyd.propertyverifier.feature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;


public class EqualsFeature<T, U> implements ObjectFeature<T, U> {

	@Override
	public void verify(Function<U, T> constructor, List<U> values) {
		verifyObjectWithEqualPropertiesAreEqual(constructor, values);
		verifyObjectsWithDistinctPropertiesAreDistinct(constructor, values);
	}

	private void verifyObjectsWithDistinctPropertiesAreDistinct(Function<U, T> constructor, List<U> values) {
		List<T> objects = values.stream().map(constructor).collect(toList());
		List<T> distinct = distinct(objects);
		if (distinct.size() != values.size()) {
			String header = "Equals verification failed (distinct property values should not produce equal objects):\n";
			String template = "object for property value %s is equal to object for property values %s";
			String details = equalPairs(constructor, values).entrySet().stream()
					.map(entry -> String.format(template, entry.getKey(), entry.getValue()))
					.collect(joining("\n"));
			throw new RuntimeException(header + details);
		}
	}

	private Map<U, List<U>> equalPairs(Function<U, T> constructor, List<U> values) {
		Map<U, List<U>> equalValues = new HashMap<>();
		for (U value : values) {
			List<U> collect = values.stream()
					.filter(value2 -> !value.equals(value2))
					.filter(value2 -> constructor.apply(value).equals(constructor.apply(value2)))
					.collect(toList());
			if (!collect.isEmpty()) {
				equalValues.put(value, collect);
			}
		}
		return equalValues;
	}

	private void verifyObjectWithEqualPropertiesAreEqual(Function<U, T> constructor, List<U> values) {
		List<U> failedValues = values.stream()
				.filter(value -> !constructor.apply(value).equals(constructor.apply(value)))
				.collect(toList());
		if (!failedValues.isEmpty()) {
			String header = "Equals verification failed (equal property values should produce equal objects):\n";
			String template = "object with property value %s is not equal to another object with same property value";
			String details = failedValues.stream()
					.map(value -> String.format(template, value))
					.collect(joining("\n"));
			throw new RuntimeException(header + details);
		}
	}

	/*
	 * Since we only verfiy equals and not hashCode we have to write our own method to count distinct elements
	 * instead of relying on java built-ins like Stream::distinct (which uses hashCode)
	 */
	private List<T> distinct(List<T> objects) {
		List<T> distinct = new ArrayList<>();
		for (T t : objects) {
			if (distinct.stream().noneMatch(t::equals)) {
				distinct.add(t);
			}
		}
		return distinct;
	}

}
