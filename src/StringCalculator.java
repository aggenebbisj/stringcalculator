import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

	private static final String DEFAULT_DELIMITER = ",";
	private static final String CUSTOM_DELIMITER_PATTERN = "//(\\W)\n";
	
	private String delimiter = DEFAULT_DELIMITER;

	public int add(String input) {
		if ("".equals(input))
			return 0;

		String strippedInput = stripCustomDelimiter(input);
		List<Integer> values = explode(strippedInput);
		validateValues(values);
		return sum(values);
	}

	private String stripCustomDelimiter(String input) {
		Matcher delimiterMatcher = Pattern.compile(CUSTOM_DELIMITER_PATTERN).matcher(input);
		while (delimiterMatcher.find()) {
			delimiter = delimiterMatcher.group(0).substring(2, 3);
		}
		return input.replaceAll(CUSTOM_DELIMITER_PATTERN, "");
	}
	
	private void validateValues(List<Integer> values) {
		List<Integer> negatives = new ArrayList<>();
		for (int value : values) {
			if (value < 0)
				negatives.add(value);
		}
		
		if (!negatives.isEmpty()) 
			throw new IllegalArgumentException("negatives not allowed: " + negatives.toString());
	}

	private int sum(List<Integer> values) {
		int result = 0;
		for (int value : values) {
			result += value;
		}
		return result;
	}

	private List<Integer> explode(String values) {
		List<Integer> result = new ArrayList<>();
		try (Scanner scanner = new Scanner(values).useDelimiter(delimiter + "|\n")) {
			while (scanner.hasNext()) {
				result.add(Integer.valueOf(scanner.next().trim()));
			}
			return result;
		}
	}

	

}