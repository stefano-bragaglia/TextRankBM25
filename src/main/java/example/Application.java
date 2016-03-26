package example;

import java.util.List;

import core.Parser;
import core.TextRank;
import core.parsers.BasicParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Example of usage.
 */
public class Application {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static String CONTENT = "blogspot.co.uk\n\n" +
			"David Beckham becomes Brand Ambassador for Jaguar Cars in China.\n\n" +
			"British sporting icon David Beckham to front new marketing campaign across China.\n" +
			"Jaguar commissions Peter Lindbergh for new Beckham campaign photography.\n\n" +
			"David Beckham has joined Jaguar as a brand ambassador and" +
			" will support significant forthcoming Jaguar product launches in China.\n" +
			"British sporting icon Beckham will feature in above-the-line marketing campaigns for the brand in one " +
			"of its most significant international markets.\n" +
			"Bob Grace, Jaguar Land Rover China Managing Director, said: \"We are absolutely thrilled that David " +
			"will be an ambassador for Jaguar in China.\n" +
			"David Beckham is a Briton whose achievements mean he has global popularity and he is someone who " +
			"embodies" +
			" contemporary British style and sophistication.\n" +
			"He is a truly modern, British individual with a genuine passion for design, innovation, performance " +
			"and driving.\"\n" +
			"As part of a long-term partnership, former international footballer Beckham will star in a series of " +
			"print adverts and TV commercials exclusively in China.\n\n" +
			"David's role will initially see him feature alongside the new Jaguar F-TYPE Coupé, a sports car that " +
			"is already regarded as a modern classic and one that is the perfect expression of Jaguar's vision of " +
			"modern British style and elegance.\n" +
			"His role will develop and will see him feature in many Jaguar brand and product communication " +
			"campaigns in the coming years, including those for the Jaguar XJ, the company's award winning large " +
			"luxury saloon.\n" +
			"On the start of this new partnership, David commented: \"I've always been an admirer of Jaguar – from " +
			"the styling and design to the feel and roar of the engine, every element feels uniquely British.\n" +
			"I love the classic E-Type of the 60s and today this legacy is alive in the phenomenal F-TYPE Coupé, a " +
			"car that epitomises British craftsmanship.\n" +
			"It's an honour to support a brand with such an amazing heritage and I look forward to being part of " +
			"Jaguar's exciting future in China.\"\n" +
			"The print creative material to be used in this ambitious new marketing campaign is revealed " +
			"exclusively today.\n" +
			"Renowned fashion photographer and filmmaker Peter Lindbergh was commissioned by Jaguar to produce " +
			"exclusive campaign imagery.\n" +
			"Peter has worked closely with David Beckham in the past and their involvement in the Jaguar campaign " +
			"will see the two figures continue their collaborative relationship.\n" +
			"Recognised for the distinctive cinematic quality of his work, Peter has also shot iconic images of " +
			"some of the world's most famous cultural icons.\n" +
			"The new F-TYPE Coupé will be a prominent feature of the first phase of the campaign.\n" +
			"The new F-TYPE Coupé is the most dynamically capable, performance-focused, production Jaguar ever made " +
			"and is headlined by the F-TYPE R Coupé, a 550 PS/680 Nm 5.0-litre V8 supercharged sports car like no " +
			"other.\n" +
			"The all-aluminium F-TYPE Coupé is the embodiment of the uncompromised design vision of the " +
			"award-winning C-X16 concept.\n" +
			"Other Jaguar models, including XF and XJ saloons will also feature in the Beckham campaign to present " +
			"the full brand line-up to the Chinese consumer.";

	public static void main(String[] args) {
		Parser parser = new BasicParser();
		TextRank textrank = new TextRank.Builder(parser).setEpsilon(0.1).build();
		String content = CONTENT.replaceAll("[\\t\\f]+", " ");

		System.out.println("CONTENT");
		System.out.println("-------");
		System.out.println(content);
		System.out.println();

		content = content.replaceAll("\\s+", " ");

		System.out.println("KEYWORDS");
		System.out.println("--------");
		List<String> keywords = textrank.getKeywords(content, 0);
		System.out.println(String.join(", ", keywords));
		System.out.println();

		System.out.println("SUMMARY");
		System.out.println("-------");
		List<String> sentences = textrank.getSentences(content, 0);
		System.out.println(String.join("\n", sentences));
		System.out.println();

		logger.info("Done.");
	}
}
