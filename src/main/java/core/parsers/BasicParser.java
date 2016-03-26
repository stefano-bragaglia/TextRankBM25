package core.parsers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import core.Parser;
import nlp.Factory;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.tokenize.Tokenizer;

/**
 * Basic parser. Extracts words, ignoring punctuation and common words.
 */
public class BasicParser implements Parser {

	private static final SentenceDetector DETECTOR = Factory.getSentenceDetector();
	private static final Tokenizer TOKENIZER = Factory.getTokenizer();

	private static final List<String> EXCLUDE = Arrays.asList(";", ",", ":", ".", "!", "?", "\"", "'");

	private static final List<String> COMMON = Arrays.asList("a", "about", "after", "all", "also", "am", "an", "and",
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

	@Override
	public List<String> detect(String content) {
		content = Objects.requireNonNull(content).trim();
		if (content.isEmpty()) {
			throw new IllegalArgumentException("'content' is empty");
		}

		return Arrays.asList(DETECTOR.sentDetect(content));
	}

	@Override
	public List<String> parse(String content) {
		content = Objects.requireNonNull(content).trim();
		if (content.isEmpty()) {
			throw new IllegalArgumentException("'content' is empty");
		}

		List<String> result = new ArrayList<>();
		for (String term : TOKENIZER.tokenize(content)) {
			if (!EXCLUDE.contains(term) && !COMMON.contains(term.toLowerCase())) {
				result.add(term);
			}
		}
		return result;
	}
}
