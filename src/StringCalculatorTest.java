import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class StringCalculatorTest {

	private StringCalculator stringCalculator = new StringCalculator();

	@Test
	public void should_return_zero_for_empty_string() {
		assertThat(stringCalculator.add(""), is(0));
	}

	@Test
	public void should_return_number_for_string_with_one_number() {
		assertThat(stringCalculator.add("1"), is(1));
	}

	@Test
	public void should_return_sum_for_string_with_two_numbers() {
		assertThat(stringCalculator.add("1,2"), is(3));
	}

	@Test
	public void should_handle_newline_as_delimiter_as_well() {
		assertThat(stringCalculator.add("1\n2,3"), is(6));
	}

	@Test
	public void should_support_different_delimiters() {
		assertThat(stringCalculator.add("//;\n1;2"), is(3));
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_throw_exception_with_negatives() {
		stringCalculator.add("-1");
	}

	@Test
	public void should_list_all_negative_values_in_exception() {
		try {
			stringCalculator.add("-1, 2, 4, -8, 0");
			fail();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			assertTrue(e.getMessage().contains("-1"));
			assertTrue(e.getMessage().contains("-8"));
		}
	}
}
