import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

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
}
