package org.open.software.utils.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Options utility which allows to handle application arguments in convenient form.
 * <pre>
 * 
 *public void static main(String[] args) {
 * Options options = new Options(args);
 * 
 * if(options.has("optionA")) {
 * 	Integer optionA = options.get("optionA", Integer.class);
 * }
 * 
 * if(options.has("optionB")) {
 * 	List<String> optionBList = options.list("optionB", String.class);
 * }
 *}
 * </pre>
 * 
 * The format of properties which are supported:
 *  - simple options, the options starting with - or / and can be followed by equal sign and value or space and value
 *  	<pre>
 *  cmd -A /B 10 "/C=A" -D="HH" --> resulting options {A, B="10", C="A", D="HH"}
 *  	</pre>
 *  - list of options. Multiple options with same name groups as list, or property value separated by comma signs
 *  	<pre>
 *  cmd -A 10 -A 20 --> resulting options {A=["10","20"]}
 *  cmd -A=10,20 -A "OK" --> resulting options {A=["20","20","OK"]}
 *  cmd -A="Some text",20,"Blue,yellow" --> resulting options {A=["Some text","20","Blue, yellow"]}
 *  	</pre>
 *  
 * The quote can be applied to complete option value part, or just for value
 * 
 * The values of options can be one of Integer, Long, Float, Boolean or String type.
 * 
 * <pre>
 * // consider {A=[20,20,OK]}
 * options.list("A", String.value); // returns "20", "20", "OK"
 * options.list("A", Integer.value); // returns 20, 20
 * <pre>
 * 
 * @author andriyv
 *
 */
public class Options {

	private List<Option> optionList;

	public Options(String[] options) {
		this.optionList = new ArrayList<>();

		Arrays.asList(options).forEach(o -> {

			if (o.startsWith("\"") && o.endsWith("\"")) {
				o = o.substring(1, o.length() - 1);
			}

			if (o.startsWith("/") || o.startsWith("-")) {
				optionList.add(new Option(o.substring(1)));
			} else {
				Option lastOption = null;

				if (optionList.isEmpty() == false) {
					lastOption = optionList.get(optionList.size() - 1);
				}

				if (lastOption == null || lastOption.isComplete()) {
					optionList.add(new Option(o));
				} else {
					lastOption.set(o);
				}
			}
		});
	}

	/**
	 * Checks if option defined
	 *
	 * @param optionName the option name
	 * @return true, if successful
	 */
	public boolean has(String optionName) {
		return optionList.stream().anyMatch(c -> c.getName().equals(optionName));
	}

	/**
	 * Gets the option value. If multiple values exists the first matching one will be returned
	 *
	 * @param <T> the generic type
	 * @param optionName the option name
	 * @param optionType the option type
	 * @return the t
	 */
	public <T> T get(String optionName, Class<T> optionType) {
		return optionList.stream().filter(o -> o.getName().equals(optionName)).filter(o -> {
			return o.get(optionType) != null;
		}).map(o -> o.get(optionType)).findFirst().orElse(null);
	}

	/**
	 * List the matching option values for the type
	 *
	 * @param <T> the generic type
	 * @param optionName the option name
	 * @param optionType the option type
	 * @return the list
	 */
	public <T> List<T> list(String optionName, Class<T> optionType) {
		return optionList.stream().filter(o -> o.getName().equals(optionName)).flatMap(o -> {
			List<T> list = o.list(optionType);
			return (Stream<T>)(list != null ? list.stream() : new ArrayList().stream());
		}).collect(Collectors.toList());
	}
}
