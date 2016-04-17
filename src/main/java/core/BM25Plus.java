package core;

import java.util.*;

/**
 * Implementation of the BM25+ algorithm.
 */
public class BM25Plus {

	public static final double K1 = 1.5D;
	public static final double B = 0.75D;
	private final double avgdLength;
	private final List<List<String>> document;
	private final List<Map<String, Integer>> f;
	private final Map<String, Double> idf;
	private final double b;
	private final double k1;

	/**
	 * Private constructor.
	 * Use a {@link BM25Plus.Builder} to create an instance of this class.
	 *
	 * @param builder the builder to initialise this instance
	 */
	private BM25Plus(Builder builder) {
		Objects.requireNonNull(builder);

		this.b = builder.b;
		this.document = builder.document;
		this.k1 = builder.k1;

		this.f = new ArrayList<>();
		Map<String, Integer> df = new HashMap<>();
		this.idf = new HashMap<>();

		if (!document.isEmpty()) {
			double total = 0.0;
			int size = document.size();
			for (int i = 0; i < size; i++) {
				List<String> sentence = document.get(i);
				total += sentence.size();
				Map<String, Integer> tf = new HashMap<>();
				for (String word : sentence) {
					tf.put(word, 1 + tf.getOrDefault(word, 0));
				}
				f.add(tf);
				for (String word : tf.keySet()) {
					df.put(word, 1 + df.getOrDefault(word, 0));
				}
			}
			double avg = total / size;
			this.avgdLength = Double.isNaN(avg) ? Double.MIN_VALUE : avg;
			for (String word : df.keySet()) {
				int freq = df.get(word);
				double val = Math.log(size - freq + 0.5) - Math.log(freq + 0.5);
				this.idf.put(word, Double.isNaN(val) ? Double.MIN_VALUE : val);
			}
		} else {
			this.avgdLength = 0.0;
		}
	}

	/**
	 * Returns the B factor.
	 *
	 * @return the B factor
	 */
	public double getB() {
		return b;
	}

	/**
	 * Returns the content of the indexed document as a list of sentences, where sentences are lists of terms.
	 *
	 * @return the content of the indexed document
	 */
	public List<List<String>> getDocument() {
		return document;
	}

	/**
	 * Returns the K1 factor.
	 *
	 * @return the K1 factor
	 */
	public double getK1() {
		return k1;
	}

	/**
	 * Returns the averaged number of terms among the sentences of the indexed document.
	 *
	 * @return the averaged number of terms among the sentences of the indexed document
	 */
	public double avgdLength() {
		return avgdLength;
	}

	/**
	 * Returns the number of sentences in the indexed document.
	 *
	 * @return the number of sentences in the indexed document
	 */
	public int size() {
		return document.size();
	}

	/**
	 * Returns the similarity score (in [0,1]) of a sentence (a list of terms) with respect to the sentence of the
	 * indexed document at position 'index'. The sentence must be not null, and the index included in the [0, size).
	 *
	 * @param sentence the sentence to match
	 * @param index    the index of the reference sentence of the indexed document
	 * @return the similarity score
	 */
	public double isSimilar(List<String> sentence, int index) {
		Objects.requireNonNull(sentence);

		double score = 0.0;
		Map<String, Integer> tf = f.get(index);
		for (String word : sentence) {
			if (tf.containsKey(word)) {
				int d = document.get(index).size();
				Integer wf = tf.get(word);
				double val = (idf.get(word) * wf * (k1 + 1) / (wf + k1 * (1 - b + b * d / avgdLength)));
				score += Double.isNaN(val) ? Double.MIN_VALUE : val;
			}
		}
		return score;
	}

	/**
	 * Returns the array of similarity scores (in [0,1]) of a sentence (a list of terms) with respect to all the
	 * sentences of the indexed document. The sentence must be not null.
	 *
	 * @param sentence the sentence to match
	 * @return the array of similarity scores
	 */
	public double[] isSimilar(List<String> sentence) {
		Objects.requireNonNull(sentence);

		double[] scores = new double[this.document.size()];
		for (int i = 0; i < document.size(); i++) {
			scores[i] = this.isSimilar(sentence, i);
		}
		return scores;
	}

	/**
	 * An helper class to build a {@link BM25Plus} instance.
	 */
	public static class Builder {

		private double k1 = K1;

		private double b = B;

		private List<List<String>> document = null;

		public Builder(List<List<String>> document) {
			this.document = Objects.requireNonNull(document);
		}

		public BM25Plus build() {
			return new BM25Plus(this);
		}

		/**
		 * Sets B to 0, making BM25+ to degenerate in BM11.
		 *
		 * @return this builder
		 */
		public Builder setBM11() {
			this.b = 0.0;
			return this;
		}

		/**
		 * Sets B to 1, making BM25+ to degenerate in BM15.
		 *
		 * @return this builder
		 */
		public Builder setBM15() {
			this.b = 1.0;
			return this;
		}

		public Builder setB(double b) {
			if (b < 0.0 || b > 1.0) {
				throw new IndexOutOfBoundsException("'b' is not in [0.0,1.0]: " + b);
			}
			this.b = b;
			return this;
		}

		public Builder setDocument(List<List<String>> document) {
			this.document = Objects.requireNonNull(document);
			return this;
		}

		public Builder setK1(double k1) {
			if (k1 <= 0.0) {
				throw new IndexOutOfBoundsException("'k1' is negative or zero: " + k1);
			}
			this.k1 = k1;
			return this;
		}

	}

}
