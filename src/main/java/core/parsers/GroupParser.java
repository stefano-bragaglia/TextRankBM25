package core.parsers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import core.Parser;
import edu.washington.cs.knowitall.morpha.MorphaStemmer;
import nlp.Factory;
import opennlp.tools.chunker.Chunker;
import opennlp.tools.postag.POSTagger;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.util.Span;

/**
 * TODO Add some meaningful class description...
 */
public class GroupParser implements Parser {
	private static final SentenceDetector DETECTOR = Factory.getSentenceDetector();
	private static final Chunker CHUNKER = Factory.getChunker();
	private static final POSTagger TAGGER = Factory.getPOSTagger();
	private static final Tokenizer TOKENIZER = Factory.getTokenizer();

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

		List<String> topics = new ArrayList<>();
		for (String sentence : detect(content)) {
			String[] tokens = TOKENIZER.tokenize(sentence);
			String[] tags = TAGGER.tag(tokens);
			for (Span chunk : CHUNKER.chunkAsSpans(tokens, tags)) {
				List<String> lemmas = new ArrayList<>();
				switch (chunk.getType()) {
					case "NP":
						for (int i = chunk.getStart(); i < chunk.getEnd(); i++) {
							if (tags[i].startsWith("NN") || tags[i].startsWith("JJ") ||
									tags[i].startsWith("VB") || tags[i].startsWith("CD")) {
								String lemma = MorphaStemmer.stemToken(tokens[i], tags[i]);
								if (!tags[i].startsWith("NNP")) {
									lemma = lemma.toLowerCase();
								}
								lemmas.add(lemma);
							}
						}
						if (!lemmas.isEmpty()) {
							String term = String.join(" ", lemmas);
							if (isValid(term)) {
								topics.add(String.join(" ", lemmas));
							}
						}
						break;
					case "VP":
						for (int i = chunk.getStart(); i < chunk.getEnd(); i++) {
							if (tags[i].startsWith("VB")) {
								lemmas.add(MorphaStemmer.stemToken(tokens[i], tags[i]).toLowerCase());
								if (lemmas.size() > 2) {
									String first = lemmas.get(1);
									if (first.equalsIgnoreCase("be") || first.equalsIgnoreCase("have")) {
										lemmas.remove(1);
									}
								}
							}
						}
						if (!lemmas.isEmpty()) {
							String term = String.join(" ", lemmas);
							if (isValid(term)) {
								topics.add(String.join(" ", lemmas));
							}
						}
						break;
				}
			}
		}
		return topics;
	}

}
