package core;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of the TextRank algorithm.
 */
public class TextRank {

	public static final double MIN_EPSILON = 0.001;
	public static final int MAX_ITERATIONS = 200;
	public static final double DEFAULT_DUMPING = 0.85;

	private final double dumping;
	private final double epsilon;
	private final int iterations;
	private final Parser parser;

	/**
	 * Private constructor.
	 * Use a {@link TextRank.Builder} to create an instance of this class.
	 *
	 * @param builder the builder to initialise this instance
	 */
	private TextRank(Builder builder) {
		Objects.requireNonNull(builder);

		this.dumping = builder.dumping;
		this.epsilon = builder.epsilon;
		this.iterations = builder.iterations;
		this.parser = builder.parser;
	}

	/**
	 * Returns the dumping factor.
	 *
	 * @return the dumping factor
	 */
	public double getDumping() {
		return dumping;
	}

	/**
	 * Returns the epsilon.
	 *
	 * @return the epsilon
	 */
	public double getEpsilon() {
		return epsilon;
	}

	/**
	 * Returns the number of iterations.
	 *
	 * @return the number of iterations
	 */
	public int getIterations() {
		return iterations;
	}

	/**
	 * Returns the parser.
	 *
	 * @return the parser
	 */
	public Parser getParser() {
		return parser;
	}

	/**
	 * Returns the list of all the keywords found in the given content, in order of importance.
	 * The content must be not null.
	 *
	 * @param content the content to analyse
	 * @return the list of all the keywords found in the given content, in order of importance
	 */
	public List<String> getKeywords(String content) {
		return getKeywords(content, -1);
	}

	/**
	 * Returns the list of n (if any) most important keywords found in the given content, in order of importance.
	 * The content must be not null. If n is equal to -1, all the keywords are returned. if n is equal to 0,
	 * default number of keywords is used (sqrt(size)).
	 *
	 * @param content the content to analyse
	 * @param num the desired number of keywords (-1 means all and 0 means sqrt(size))
	 * @return the list of first n keywords found in the given content, in order of importance
	 */
	public List<String> getKeywords(String content, int num) {
		Objects.requireNonNull(content);

		List<String> tokens = parser.parse(content);
		if (0 == num) {
			num = Integer.max(1, (int) Math.ceil(Math.sqrt(tokens.size())));
		}

		Map<String, Set<String>> matrix = new HashMap<>();
		List<String> queue = new ArrayList<>();
		for (String token : tokens) {
			if (!matrix.containsKey(token)) {
				matrix.put(token, new HashSet<>());
			}
			queue.add(token);
			if (queue.size() > 5) {
				queue.remove(0);
			}
			for (int i = 0; i < queue.size(); i++) {
				for (int j = 0; j < queue.size(); j++) {
					if (i != j) {
						String iToken = queue.get(i);
						String jToken = queue.get(j);
						matrix.get(iToken).add(jToken);
						matrix.get(jToken).add(iToken);
					}
				}
			}
		}

		double max_diff = 100 * epsilon;
		Map<String, Double> ranks = new HashMap<>();
		for (int i = 0; i < iterations && max_diff > epsilon; ++i) {
			max_diff = 0.0;
			Map<String, Double> current = new HashMap<>();
			for (String token : matrix.keySet()) {
				Set<String> adjacents = matrix.get(token);
				for (String adjacent : adjacents) {
					int size = matrix.get(adjacent).size();
					if (!token.equals(adjacent) && size > 0) {
						double value = dumping * ranks.getOrDefault(adjacent, 0.0) / size;
						current.put(token, value + current.getOrDefault(token, 1.0 - dumping));
					}
				}
				max_diff = Math.max(max_diff, current.getOrDefault(token, 0.0) - ranks.getOrDefault(token, 0.0));
			}
			ranks = current;
		}
		List<Map.Entry<String, Double>> entries = new ArrayList<>(ranks.entrySet());
		Collections.sort(entries, (o1, o2) -> Double.compare(o2.getValue(), o1.getValue()));

		List<String> result = new ArrayList<>();
		for (Map.Entry<String, Double> entry : entries) {
			if (num > -1 && result.size() >= num) {
				break;
			}
			result.add(entry.getKey());
		}
		return result;
	}

	/**
	 * Returns the list of all the sentences found in the given content, in order of importance.
	 * The content must be not null.
	 *
	 * @param content the content to analyse
	 * @return the list of all the sentences found in the given content, in order of importance
	 */
	public List<String> getSentences(String content) {
		return getSentences(content, -1);
	}

	/**
	 * Returns the list of n (if any) most important sentences found in the given content, in order of importance.
	 * The content must be not null. If n is equal to -1, all the sentences are returned. if n is equal to 0,
	 * default number of sentences is used (sqrt(size)).
	 *
	 * @param content the content to analyse
	 * @param num the desired number of sentences (-1 means all and 0 means sqrt(size))
	 * @return the list of first n sentence found in the given content, in order of importance
	 */
	public List<String> getSentences(String content, int num) {
		Objects.requireNonNull(content);

		List<String> sentences = parser.detect(content);
		List<List<String>> document = sentences.stream().map(this::getKeywords).collect(Collectors.toList());
		int size = document.size();
		double[][] weight = new double[size][size];
		double[] weight_sum = new double[size];
		double[] vertex = new double[size];
		if (0 == num) {
			num = Integer.max(1, (int) Math.ceil(Math.sqrt(size)));
		}

		BM25Plus bm25 = new BM25Plus.Builder(document).build();
		for (int i = 0; i < size; i++) {
			double[] scores = bm25.isSimilar(document.get(i));
			weight[i] = scores;
			weight_sum[i] = Arrays.stream(scores).sum() - scores[i];
			vertex[i] = 1.0;
		}

		double max_diff = 100 * epsilon;
		for (int i = 0; i < iterations && max_diff > epsilon; i++) {
			max_diff = 0.0;

			double[] m = new double[size];
			for (int j = 0; j < size; j++) {
				m[j] = 1.0 - size;
				for (int k = 0; k < size; k++) {
					if (j != k && 0 != weight_sum[k]) {
						m[j] += (size * weight[k][j] / weight_sum[k] * vertex[k]);
					}
				}
				double diff = Math.abs(m[j] - vertex[j]);
				if (diff > max_diff) {
					max_diff = diff;
				}
			}
			vertex = m;
		}

		Map<Double, Integer> top = new TreeMap<>(Collections.reverseOrder());
		for (int i = 0; i < size; ++i) {
			top.put(vertex[i], i);
		}

		List<String> result = new ArrayList<>();
		for (Integer pos : top.values()) {
			if (num > -1 && result.size() >= num) {
				break;
			}
			result.add(sentences.get(pos));
		}
		return result;
	}

	/**
	 * An helper class to build a {@link TextRank} instance.
	 */
	public static class Builder {

		private double dumping = DEFAULT_DUMPING;
		private double epsilon = MIN_EPSILON;
		private int iterations = MAX_ITERATIONS;

		private Parser parser;

		public Builder(Parser parser) {
			this.parser = Objects.requireNonNull(parser);
		}

		public TextRank build() {
			return new TextRank(this);
		}

		public Builder setDumping(double dumping) {
			if (dumping < 0.0) {
				throw new IllegalArgumentException("'dumping' is negative: " + dumping);
			}
			this.dumping = dumping;
			return this;
		}

		public Builder setEpsilon(double epsilon) {
			if (epsilon < 0.0) {
				throw new IllegalArgumentException("'epsilon' is negative: " + epsilon);
			}
			this.epsilon = epsilon;
			return this;
		}

		public Builder setIterations(int iterations) {
			if (iterations < 0) {
				throw new IllegalArgumentException("'iterations' is negative: " + iterations);
			}
			this.iterations = iterations;
			return this;
		}

		public Builder setParser(Parser parser) {
			this.parser = Objects.requireNonNull(parser);
			return this;
		}

	}

}
