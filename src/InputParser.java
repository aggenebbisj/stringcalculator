import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InputParser {

	private static final String REGEX_INPUT = "//((.)|(\\[(.+?)\\]))\n(.*)";
	private static final String REGEX_BRACKETED_DELIMITERS = "\\[(.+?)\\]";
	private static final int REGEX_GROUP_SINGLE_CHAR_DELIMITER = 1;
	private static final int REGEX_GROUP_BRACKETED_DELIMITERS = 3;
	private static final int REGEX_GROUP_NUMBER_STRING = 5;
	private static final String[] DEFAULT_DELIMITERS = new String[] {",", "\n"};
	
	private String input;
	private Matcher inputMatcher;
	
	public String[] getNumbers(String input) {
		this.input = input;
		this.inputMatcher = Pattern.compile(REGEX_INPUT).matcher(input);
		return getNumberString().split(implode(getDelimiters()));
	}
	
	private String getNumberString() {
		if (inputMatcher.reset().find()) {
			return inputMatcher.group(REGEX_GROUP_NUMBER_STRING);
		}
		return input;
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
	
	private String[] getDelimiters() {
		if (inputMatcher.reset().find()) {
			return getUserDefinedDelimiter();
		}
		return DEFAULT_DELIMITERS;
	}

	private String[] getUserDefinedDelimiter() {	
		return hasBracketedDelimiter() ? getBracketedDelimiters() : getSingleCharDelimiter();
	}
	
	private String[] getSingleCharDelimiter() {
		return new String[] { inputMatcher.group(REGEX_GROUP_SINGLE_CHAR_DELIMITER) };
	}

	private boolean hasBracketedDelimiter() {
		return inputMatcher.group(REGEX_GROUP_BRACKETED_DELIMITERS) != null;
	}
	
	private String[] getBracketedDelimiters() {
		String bracketedDelimiters = inputMatcher.group(REGEX_GROUP_BRACKETED_DELIMITERS);
		Matcher d = Pattern.compile(REGEX_BRACKETED_DELIMITERS).matcher(bracketedDelimiters);
		
		List<String> result = new ArrayList<>();
		while (d.find()) {
			result.add(d.group(1));
		}
		return result.toArray(new String[result.size()]);
	}
	
}
