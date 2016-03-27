package core.parsers;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import core.Parser;
import nlp.Factory;
import opennlp.tools.tokenize.Tokenizer;

/**
 * Basic parser. Extracts words, ignoring punctuation and common words.
 */
public class BasicParser implements Parser {

	private static final Tokenizer TOKENIZER = Factory.getTokenizer();

	@Override
	public List<String> parse(String content) {
		content = Objects.requireNonNull(content).trim();
		if (content.isEmpty()) {
			throw new IllegalArgumentException("'content' is empty");
		}

		return Arrays.stream(TOKENIZER.tokenize(content))
				.filter(this::isValid)
				.collect(Collectors.toList());
	}
}
