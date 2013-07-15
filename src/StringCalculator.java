import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

	private String DEFAULT_DELIMITERS = ",|\n";

	public int add(String input) {
		String numberString = input;

		Matcher m = Pattern.compile("^//(.)\n(.)*").matcher(input);
		if (m.find()) {
			DEFAULT_DELIMITERS = m.group(1);
			numberString = m.group(2);
			System.out.println(DEFAULT_DELIMITERS);
			System.out.println(numberString);
		}
		return sum(numberString);
	}

	private int sum(String numbers) {
		int sum = 0;
		for (String numberString : splitNumberString(numbers)) {
			sum += toInt(numberString);
		}
		return sum;
	}

	private String[] splitNumberString(String numbers) {
		if (numbers.isEmpty())
			return new String[0];
		
		return numbers.split(DEFAULT_DELIMITERS);
	}

	private int toInt(String number) {
		return Integer.parseInt(number);
	}

}
