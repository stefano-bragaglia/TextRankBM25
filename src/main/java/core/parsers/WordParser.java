package core.parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import core.Parser;
import edu.washington.cs.knowitall.morpha.MorphaStemmer;
import nlp.Factory;
import opennlp.tools.postag.POSTagger;
import opennlp.tools.tokenize.Tokenizer;

/**
 * TODO Add some meaningful class description...
 */
public class WordParser implements Parser {
	private static final POSTagger TAGGER = Factory.getPOSTagger();
	private static final Tokenizer TOKENIZER = Factory.getTokenizer();

	@Override
	public List<String> parse(String content) {
		content = Objects.requireNonNull(content).trim();
		if (content.isEmpty()) {
			throw new IllegalArgumentException("'content' is empty");
		}

		List<String> words = new ArrayList<>();
		for (String sentence : detect(content)) {
			String[] tokens = TOKENIZER.tokenize(sentence);
			String[] tags = TAGGER.tag(tokens);
			for (int i = 0; i < tokens.length; i++) {
				if (tags[i].startsWith("NN") || tags[i].startsWith("JJ") ||
						tags[i].startsWith("VB") || tags[i].startsWith("CD") ||
						tags[i].startsWith("RD")) {
					String lemma = MorphaStemmer.stemToken(tokens[i], tags[i]);
					if (isValid(lemma)) {
						if (!tags[i].startsWith("NNP")) {
							lemma = lemma.toLowerCase();
						}
						words.add(lemma);
					}
				}
			}
		}
		return words;
	}
}
