import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

	private static final String REGEX_SINGLE_CHAR_DELIMITER = "^//(.)\n";
	private static final String REGEX_MULTI_CHAR_DELIMITER = "^//\\[(.+?)\\]\n";
	
	private static final String REGEX_SINGLE = REGEX_SINGLE_CHAR_DELIMITER + "(.*)";
	private static final String REGEX_MULTI = REGEX_MULTI_CHAR_DELIMITER + "(.*)";
	
	private List<Integer> errors = new ArrayList<>();

	public int add(String input) {
		Matcher m = Pattern.compile(REGEX_INPUT).matcher(input);
		return sum(splitNumberString(getNumberString(input, m), getDelimiters(m)));
	}

	private String getNumberString(String numberString, Matcher m) {
		if (m.reset().find()) {
			return m.group(2);
		}
		return numberString;
	}

	private String getDelimiters(Matcher m) {
		if (m.reset().find()) {
			String result = m.group(1);
			System.out.println(result);
			return Pattern.quote(result);
		}
		return ",|\n";
	}

	private int sum(String[] numbers) {
		int sum = 0;
		for (String numberString : numbers) {
			sum += toInt(numberString);
		}

		if (!errors.isEmpty())
			throw new IllegalArgumentException("negatives not allowed " + errors.toString());
		
		return sum;
	}

	private String[] splitNumberString(String numbers, String delimiters) {
		if (numbers.isEmpty())
			return new String[0];

		return numbers.split(delimiters);
	}

	private int toInt(String number) {
		int result = Integer.parseInt(number);
		validateForNegatives(result);
		return ignoreValuesGreaterThan1000(result);
	}

	private int ignoreValuesGreaterThan1000(int result) {
		if (result > 1000)
			result = 0;
		return result;
	}

	private void validateForNegatives(int result) {
		if (result < 0)
			errors.add(result);
	}

}
