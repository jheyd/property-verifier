package de.janheyd.propertyverifier;

import de.janheyd.propertyverifier.feature.EqualsFeature;
import de.janheyd.propertyverifier.feature.GetterFeature;
import de.janheyd.propertyverifier.feature.HashCodeFeature;
import de.janheyd.propertyverifier.feature.ObjectFeature;
import de.janheyd.propertyverifier.feature.ToStringFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class VerifiablePropertyVerifier<T, U> {

	private final Function<U, T> constructor;
	private List<ObjectFeature<T, U>> features;

	public VerifiablePropertyVerifier(Function<U, T> constructor) {
		this(constructor, emptyList());
	}

	private VerifiablePropertyVerifier(Function<U, T> constructor, List<ObjectFeature<T, U>> features) {
		this.constructor = constructor;
		this.features = features;
	}

	public void verify(U value1, U value2){
		List<U> values = asList(value1, value2);
		features.forEach(feature -> feature.verify(constructor, values));
	}

	public VerifiablePropertyVerifier<T, U> withToString() {
		List<ObjectFeature<T, U>> features = new ArrayList<>(this.features);
		features.add(new ToStringFeature<>());
		return new VerifiablePropertyVerifier<>(constructor, features);
	}

	public VerifiablePropertyVerifier<T, U> withGetter(Function<T, U> getter) {
		List<ObjectFeature<T, U>> features = new ArrayList<>(this.features);
		features.add(new GetterFeature<>(getter));
		return new VerifiablePropertyVerifier<>(constructor, features);
	}

	public VerifiablePropertyVerifier<T,U> withEquals() {
		List<ObjectFeature<T, U>> features = new ArrayList<>(this.features);
		features.add(new EqualsFeature<>());
		return new VerifiablePropertyVerifier<>(constructor, features);
	}

	public VerifiablePropertyVerifier<T, U> withHashCode() {
		List<ObjectFeature<T, U>> features = new ArrayList<>(this.features);
		features.add(new HashCodeFeature<>());
		return new VerifiablePropertyVerifier<>(constructor, features);
	}

	public VerifiablePropertyVerifier<T, U> withHashCodeDistinct() {
		List<ObjectFeature<T, U>> features = new ArrayList<>(this.features);
		features.add(new HashCodeFeature<>(true));
		return new VerifiablePropertyVerifier<>(constructor, features);
	}
}
