package core;

import java.text.Normalizer;
import java.util.*;

/**
 * TODO Add some meaningful class description...
 */
public class Utils {

	private Utils() {
	}

	public static String normalise(String text) {
		text = Objects.requireNonNull(text).trim();
		text = Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		text = text.replaceAll("[¶❡†‡¦|]", ""); // MEMENTO get rid of them?

		text = text.replaceAll("[❪]", "(").replaceAll("[❫]", ")");
		text = text.replaceAll("[【〔〖❲]", "[").replaceAll("[】〕〗❳]", "]");
		text = text.replaceAll("[❴]", "{").replaceAll("[❵]", "}");

		text = text.replaceAll("[‹›«»⟨⟩⟪⟫❮❯❬❭]", "\"");
		text = text.replaceAll("[“”‟„\"″˝¨❝❞]", "\"");
		text = text.replaceAll("[‘’‛'′´`˙︎❛❜]", "'").replaceAll("''", "\"");
		text = text.replaceAll("[°¸˛˚ªºˉˆ˘ˇ]", "°");
		text = text.replaceAll("[¯\u00AD–‑—⁊\\-‒]", "-");
		text = text.replaceAll("[∎⦁⧫∙•・◦●○◎◉⦿⁃-■□☐▪︎▫︎◼︎◘◆◇❖▶︎▷‣▸▹►▻◀︎◁▼▽▾▿▴▵▲△☞☛➢➣➤]+", "*");
		text = text.replaceAll("[‼⁉❢]+", "!");
		text = text.replaceAll("[︎⁇⁈❣]+", "?");
		text = text.replaceAll("…", "...");

		text = text.replaceAll("[\\t\\f ]+", " ");
		text = text.replaceAll("(\\r\\n)", "\n").replaceAll("[\\n]{2}", "\n\n");
		String[] split = text.split("\\n");
		for (int i = 0; i < split.length; i++) {
			split[i] = split[i].trim();
			if (!split[i].isEmpty() && !split[i].endsWith("!") && !split[i].endsWith("?") &&
					!split[i].endsWith(",") && !split[i].endsWith(";") &&
					!split[i].endsWith(".") && !split[i].endsWith(":")) {
				split[i] += ".";
			} else if (split[i].endsWith("\"")) {
				String content = split[i].substring(0, split[i].length() - 1).trim();
				if (!content.isEmpty() && !content.endsWith("!") && !content.endsWith("?") &&
						!content.endsWith(",") && !content.endsWith(";") &&
						!content.endsWith(".") && !content.endsWith(":")) {
					split[i] = content + ".\"";
				}
			}
		}
		text = String.join("\n", split);
		text = text.replaceAll(" ,", ",").replaceAll(" ;", ";").replaceAll(" :", ":").replaceAll(" \\.", ".")
				.replaceAll(" !", "!").replaceAll(" \\?", "?");
		return text;
	}

	public static Collection<String> reorder(String text, List<String> sentences) {
		Objects.requireNonNull(text);
		Objects.requireNonNull(sentences);

		Map<Integer, String> entries = new TreeMap<>();
		for (String sentence : sentences) {
			int pos = text.indexOf(sentence);
			if (pos < 0) {
				throw new IllegalArgumentException("'sentence' not found: '" + sentence + "'");
			}
			entries.put(pos, sentence);
		}
		return entries.values();
	}

}
