package de.janheyd.propertyverifier;

import de.janheyd.propertyverifier.feature.GetterFeature;
import de.janheyd.propertyverifier.feature.ObjectFeature;

import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;

public class VerifiablePropertyVerifier<T, U> {

	private final Function<U, T> constructor;
	private List<ObjectFeature<T, U>> features;

	public VerifiablePropertyVerifier(Function<U, T> constructor, Function<T, U> getter) {
		this(constructor, asList(new GetterFeature<>(getter)));
	}

	public VerifiablePropertyVerifier(Function<U, T> constructor, List<ObjectFeature<T, U>> features) {
		this.constructor = constructor;
		this.features = features;
	}

	public void verify(U value1, U value2){
		List<U> values = asList(value1, value2);
		features.forEach(feature -> feature.verify(constructor, values));
	}

}
