import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class StringCalculatorTest {

	private StringCalculator calculator = new StringCalculator();

	@Test
	public void should_return_zero_for_empty_string() {
		assertThat(calculator.add(""), is(0));
	}

	@Test
	public void should_return_the_number_for_string_with_single_number() {
		assertThat(calculator.add("1"), is(1));
	}

	@Test
	public void should_return_sum_for_two_numbers() {
		assertThat(calculator.add("1,2"), is(3));
	}

	@Test
	public void should_return_sum_for_any_amount_of_numbers() {
		assertThat(calculator.add("1,2,3,4"), is(10));
	}

	@Test
	public void should_handle_newline_as_delimiter() {
		assertThat(calculator.add("1,2\n3"), is(6));
	}

	@Test
	public void should_support_custom_delimiter() {
		assertThat(calculator.add("//;\n1;2;3"), is(6));
	}

	@Test
	public void should_support_reserved_character_as_custom_delimiter() {
		assertThat(calculator.add("//$\n1$2$3"), is(6));
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_throw_exception_for_negative_number() {
		calculator.add("-1");
	}

	@Test
	public void should_list_negative_number_in_exception() {
		try {
			calculator.add("-1");
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("-1"));
		}
	}

	@Test
	public void should_list_all_negative_numbers_in_exception() {
		try {
			calculator.add("-1,2,-3,4");
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("-1"));
			assertTrue(e.getMessage().contains("-3"));
		}
	}

	@Test
	public void should_ignore_numbers_larger_than_1000() {
		assertThat(calculator.add("2,1001"), is(2));
	}

	@Test
	public void should_support_multi_character_delimiters() {
		assertThat(calculator.add("//[***]\n1***2***3"), is(6));
	}

	@Test
	public void should_support_multiple_single_character_delimiters() {
		assertThat(calculator.add("//[*][%]\n1*2%3"), is(6));
	}
	
	@Test
	public void should_support_multiple_multi_character_delimiters() {
		assertThat(calculator.add("//[***][%%%]\n1***2%%%3"), is(6));
	}
	
}
