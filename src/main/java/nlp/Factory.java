package nlp;

import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.chunker.Chunker;
import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTagger;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

/**
 * Factory for singleton instances of the OpenNLP tools.
 */
public class Factory {

	private static SentenceDetector SENTENCE_DETECTOR;

	private static Tokenizer TOKENIZER;

	private static POSTagger POS_TAGGER;

	private static Chunker CHUNKER;

	/**
	 * Returns a singleton instance of {@link SentenceDetector}.
	 *
	 * @return a singleton instance of {@link SentenceDetector}
	 */
	public static SentenceDetector getSentenceDetector() {
		if (null == SENTENCE_DETECTOR) {
			InputStream stream = null;
			try {
				stream = Factory.class.getResourceAsStream("/nlp/en-sent.bin");
				SentenceModel model = new SentenceModel(stream);
				SENTENCE_DETECTOR = new SentenceDetectorME(model);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (null != stream) {
					try {
						stream.close();
					} catch (IOException ignored) {
					}
				}
			}
		}
		return SENTENCE_DETECTOR;
	}

	/**
	 * Returns a singleton instance of {@link Tokenizer}.
	 *
	 * @return a singleton instance of {@link Tokenizer}
	 */
	public static Tokenizer getTokenizer() {
		if (null == TOKENIZER) {
			InputStream stream = null;
			try {
				stream = Factory.class.getResourceAsStream("/nlp/en-token.bin");
				TokenizerModel model = new TokenizerModel(stream);
				TOKENIZER = new TokenizerME(model);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (null != stream) {
					try {
						stream.close();
					} catch (IOException ignored) {
					}
				}
			}
		}
		return TOKENIZER;
	}

	/**
	 * Returns a singleton instance of {@link POSTagger}.
	 *
	 * @return a singleton instance of {@link POSTagger}
	 */
	public static POSTagger getPOSTagger() {
		if (null == POS_TAGGER) {
			InputStream stream = null;
			try {
				stream = Factory.class.getResourceAsStream("/nlp/en-pos-maxent.bin");
				POSModel model = new POSModel(stream);
				POS_TAGGER = new POSTaggerME(model);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (null != stream) {
					try {
						stream.close();
					} catch (IOException ignored) {
					}
				}
			}
		}
		return POS_TAGGER;
	}

	/**
	 * Returns a singleton instance of {@link Chunker}.
	 *
	 * @return a singleton instance of {@link Chunker}
	 */
	public static Chunker getChunker() {
		if (null == CHUNKER) {
			InputStream stream = null;
			try {
				stream = Factory.class.getResourceAsStream("/nlp/en-chunker.bin");
				ChunkerModel model = new ChunkerModel(stream);
				CHUNKER = new ChunkerME(model);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (null != stream) {
					try {
						stream.close();
					} catch (IOException ignored) {
					}
				}
			}
		}
		return CHUNKER;
	}

}
