import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

	private static final String REGEX_INPUT = "//((.)|(\\[(.+?)\\]))\n(.*)";
	private static final String REGEX_BRACKETED_DELIMITERS = "\\[(.+?)\\]";
	private static final int REGEX_GROUP_SINGLE_CHAR_DELIMITER = 1;
	private static final int REGEX_GROUP_BRACKETED_DELIMITERS = 3;
	private static final int REGEX_GROUP_NUMBER_STRING = 5;

	private static final String[] DEFAULT_DELIMITERS = new String[] {",", "\n"};
	private static final int NUMBER_UPPER_BOUND = 1000;

	private List<Integer> errors = new ArrayList<>();

	public int add(String input) {
		if (input.isEmpty())
			return 0;

		Matcher m = Pattern.compile(REGEX_INPUT).matcher(input);
		return sum(getNumbers(getNumberString(input, m), getDelimiters(m)));
	}

	private String getNumberString(String numberString, Matcher m) {
		if (m.reset().find()) {
			numberString = m.group(REGEX_GROUP_NUMBER_STRING);
		}
		return numberString;
	}

	private String[] getDelimiters(Matcher m) {
		if (m.reset().find()) {
			return getUserDefinedDelimiter(m);
		}
		return DEFAULT_DELIMITERS;
	}

	private String[] getUserDefinedDelimiter(Matcher m) {	
		return hasBracketedDelimiter(m) ? getBracketedDelimiters(m) : getSingleCharDelimiter(m);
	}
	
	private String[] getSingleCharDelimiter(Matcher m) {
		return new String[] { m.group(REGEX_GROUP_SINGLE_CHAR_DELIMITER) };
	}

	private boolean hasBracketedDelimiter(Matcher m) {
		return m.group(REGEX_GROUP_BRACKETED_DELIMITERS) != null;
	}
	
	private String[] getBracketedDelimiters(Matcher m) {
		String bracketedDelimiters = m.group(REGEX_GROUP_BRACKETED_DELIMITERS);
		Matcher d = Pattern.compile(REGEX_BRACKETED_DELIMITERS).matcher(bracketedDelimiters);
		
		List<String> result = new ArrayList<>();
		while (d.find()) {
			result.add(d.group(1));
		}
		return result.toArray(new String[result.size()]);
	}

	private String[] getNumbers(String input, String[] delimiters) {
		return input.split(implode(delimiters));
	}

	private String implode(String[] delimiters) {
		StringBuilder result = new StringBuilder();
		for (String delim : delimiters) {
			if (result.length() == 0) {
				result.append(Pattern.quote(delim)); 
			} else {
				result.append("|" + Pattern.quote(delim)); 
			}
		}
		return result.toString();
	}

	private int sum(String[] numbers) {
		int sum = 0;
		for (String number : numbers) {
			sum += toInt(number);
		}

		validateForNegativeNumbers();
		return sum;
	}

	private void validateForNegativeNumbers() {
		if (!errors.isEmpty())
			throw new IllegalArgumentException("negatives not allowed " + errors.toString());
	}

	private int toInt(String input) {
		int value = Integer.parseInt(input);

		if (value < 0)
			errors.add(value);

		if (value > NUMBER_UPPER_BOUND)
			return 0;
		
		return value;
	}

}
