package core;

import java.util.List;

/**
 * Interface for the parsers needed by TextRank algorithm.
 */
public interface Parser {

	/**
	 * Detects the sentences in the given content and returns them as a list of strings.
	 *
	 * @param content the content to analyse
	 * @return the list of sentences detected
	 */
	List<String> detect(String content);

	/**
	 * Parses the given content and returns the terms as a list of strings.
	 *
	 * @param content the content to analyse
	 * @return the list of terms found
	 */
	List<String> parse(String content);

}
