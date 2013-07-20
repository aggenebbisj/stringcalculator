import java.util.ArrayList;
import java.util.List;

public class StringCalculator {

	private static final int NUMBER_UPPER_BOUND = 1000;

	private List<Integer> errors = new ArrayList<>();
	private InputParser parser = new InputParser();

	public int add(String input) {
		if (input.isEmpty())
			return 0;

		return sum(parser.getNumbers(input));
	}
	
	private int sum(String[] numbers) {
		int sum = 0;
		for (String number : numbers) {
			sum += parse(number);
		}

		validateForNegativeNumbers();
		return sum;
	}

	private int parse(String number) {
		int value = Integer.parseInt(number);

		if (value < 0)
			errors.add(value);

		if (value > NUMBER_UPPER_BOUND)
			return 0;
		
		return value;
	}

	private void validateForNegativeNumbers() {
		if (!errors.isEmpty())
			throw new IllegalArgumentException("negatives not allowed " + errors.toString());
	}
}
