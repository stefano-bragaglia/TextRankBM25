package core;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import nlp.Factory;

/**
 * Interface for the parsers needed by TextRank algorithm.
 */
public interface Parser {

	List<String> PUNCTUATION = Arrays.asList(";", ",", ":", ".", "!", "?", "\"", "'");

	List<String> COMMON = Arrays.asList("a", "about", "after", "all", "also", "am", "an", "and",
										"any", "are", "as", "at", "back", "be", "because",
										"been", "but", "by", "came", "can", "cans", "come",
										"comes", "could", "day", "days", "did", "do", "does",
										"even", "first", "for", "from", "gave", "get", "gets",
										"give", "given", "gives", "go", "goes", "good", "goods",
										"got", "had", "has", "have", "he", "her", "him", "his",
										"how", "i", "if", "in", "into", "is", "it", "its",
										"just", "knew", "know", "knows", "like", "liked",
										"likes", "look", "looked", "looks", "made", "make",
										"makes", "me", "most", "my", "new", "no", "not", "now",
										"of", "on", "one", "only", "or", "other", "others",
										"our", "out", "over", "person", "persons", "said", "saw",
										"say", "says", "see", "seen", "sees", "she", "so",
										"some", "take", "taken", "takes", "than", "that", "the",
										"their", "them", "then", "there", "these", "they",
										"think", "thinks", "this", "thought", "time", "times",
										"to", "took", "two", "up", "us", "use", "used", "uses",
										"want", "wanted", "wants", "was", "way", "ways", "we",
										"well", "wells", "went", "were", "what", "when", "which",
										"who", "will", "wills", "with", "work", "worked",
										"works", "would", "year", "years", "you", "your");

	/**
	 * Checks whether the given term is valid or not.
	 *
	 * @param term the term to validate
	 * @return {@code true} if the term is valid, {@code false} otherwise
	 */
	default boolean isValid(String term) {
		term = Objects.requireNonNull(term).trim();
		if (term.isEmpty()) {
			throw new IllegalArgumentException("'term' is empty");
		}

		return !PUNCTUATION.contains(term) && !COMMON.contains(term.toLowerCase());
	}

	/**
	 * Detects the sentences in the given content and returns them as a list of strings.
	 *
	 * @param content the content to analyse
	 * @return the list of sentences detected
	 */
	default List<String> detect(String content) {
		content = Objects.requireNonNull(content).trim();
		if (content.isEmpty()) {
			throw new IllegalArgumentException("'content' is empty");
		}

		return Arrays.asList(Factory.getSentenceDetector().sentDetect(content));
	}

	/**
	 * Parses the given content and returns the terms as a list of strings.
	 *
	 * @param content the content to analyse
	 * @return the list of terms found
	 */
	List<String> parse(String content);

}
