package example;

import java.util.List;

import core.Parser;
import core.TextRank;
import core.Utils;
import core.parsers.GroupParser;
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

	public static String MORE = "Ecuador earthquake: 10,000 troops deployed as rescue begins\n" +
			"\n" +
			"Amateur video shows the moment the earthquake struck\n" +
			"Rescue efforts are under way in Ecuador after the country suffered its worst earthquake in decades, with" +
			" " +
			"at least 77 people confirmed dead.\n" +
			"\n" +
			"Some 10,000 troops and 3,500 police are being deployed in the affected areas.\n" +
			"\n" +
			"The powerful magnitude 7.8 quake struck early on Saturday evening and was felt as far away as " +
			"neighbouring Colombia.\n" +
			"\n" +
			"Coastal areas in the north-west were closest to the epicentre and officials say the death toll is likely" +
			" " +
			"to rise as information begins to come in.\n" +
			"\n" +
			"\"These are very difficult moments,\" Ecuador's Vice-President Jorge Glas said.\n" +
			"\n" +
			"\"We have information that there are injured people who are trapped [under rubble] in different " +
			"districts" +
			" and we are getting ready to rescue them.\"\n" +
			"\n" +
			"In pictures: Ecuador earthquake\n" +
			"Ecuador quake - your stories\n" +
			"History of deadly earthquakes\n" +
			"Can quakes be predicted?\n" +
			"Helicopters and buses are ferrying troops north but have been hampered by landslides.\n" +
			"\n" +
			"Mr Glas said food and other essentials were being handed out and international aid was also beginning, " +
			"with the first coming from Venezuela and Mexico.\n" +
			"\n" +
			"President Rafael Correa, who is flying back from a trip to Italy, has decreed a state of emergency, " +
			"calling on his country to be \"calm and united\".\n" +
			"\n" +
			" Map\n" +
			"At least 500 people were injured in the quake, which was felt across the country.\n" +
			"\n" +
			"Widespread severe damage is reported, with a bridge destroyed as far south as Guayaquil about 300km (190" +
			" " +
			"miles) away.\n" +
			"\n" +
			"Gabriel Alcivar, mayor of Pedernales, close to the epicentre, said the \"entire town\" had been " +
			"flattened" +
			".\n" +
			"\n" +
			"\"We're trying to do the most we can but there's almost nothing we can do,\" he added, warning that " +
			"looting had broken out.\n" +
			"\n" +
			" Rubble in the Ecuadorian city of Manta after an earthquakeAFP\n" +
			"There are fears the death toll could rise with the worst hit areas yet to be reached\n" +
			" Rescue workers examine a destroyed car after the collapse of a bridge in Guayaquil, EcuadorAFP\n" +
			"A road bridge collapsed in Guayaquil, about 300km south of the epicentre of the quake\n" +
			" Quake damage in Pedernales, Ecuador, 17 AprilAP\n" +
			"Looking for survivors in Pedernales. Its mayor said the whole town had collapsed\n" +
			"In badly hit Manta, one woman said: \"The third floor collapsed on top of us.\n" +
			"\n" +
			"\"They are all there, my family, my sister, my children. They are all there, there are a lot of people." +
			" " +
			"My God, may the help arrive.\"\n" +
			"\n" +
			"Cristian Ibarra Santillan was in the capital Quito when the quake struck.\n" +
			"\n" +
			"\"There had been some small tremors going on for about two or three months and I thought it was one of " +
			"those but after about 20, 30 seconds it started to get really strong,\" he told the BBC.\n" +
			"\n" +
			"\"And I grabbed my dog and I hid under the table. But then I realised that it wasn't going away so I " +
			"just" +
			" ran with him outside.\"\n" +
			"\n" +
			"Stephan Kuffner, who lives in Quito, says he felt the vibrations for minutes after the quake struck\n" +
			"A man was killed when his car was crushed beneath a collapsed bridge in Guayaquil\n" +
			"The quake is Ecuador's largest since 1979. More than 130 aftershocks have followed.\n" +
			"\n" +
			"The US Geological Survey said the earthquake struck at a fairly shallow depth of 19.2km (11.9 miles), " +
			"about 27km from Muisne in a sparsely populated area.\n" +
			"\n" +
			"David Rothery, a professor of geosciences at The Open University, said Ecuador's quake was about six " +
			"times as powerful as the earthquake that struck southern Japan on Saturday.\n" +
			"\n" +
			"The quake was also felt in Colombia, where patients in a clinic in the city of Cali were evacuated from" +
			" " +
			"the building.\n" +
			"\n" +
			"Analysis: Jonathan Amos, BBC science correspondent\n" +
			"\n" +
			"Ecuador is well used to earthquakes. There have been seven magnitude-7.0 or greater events within 250km" +
			" " +
			"of this latest tremor since 1900. And some of these have resulted in very considerable loss of life, not" +
			" " +
			"just from the shaking but also from tsunami waves.\n" +
			"\n" +
			"The country sits on the so-called \"Ring of Fire\" - the arc of high seismic activity that extends right" +
			" " +
			"around the Pacific basin. At its location, Ecuador fronts the boundary between the Nazca and South " +
			"American tectonic plates.\n" +
			"\n" +
			"These are vast slabs of the Earth's surface that grind past each other at a rate of about 65mm per year." +
			" " +
			"The Nazca plate, which makes up the Pacific Ocean floor in this region, is being pulled down (subducted)" +
			" " +
			"and under the South American coast.\n" +
			"\n" +
			"It is a process that has helped build the Andes and Ecuador's many volcanoes, including the mighty " +
			"Chimborazo.\n" +
			"\n" +
			"Models that try to forecast the likely casualty numbers from the nature of the quake and local building" +
			" " +
			"construction methods indicate this event could be very serious, with the number of deaths running into " +
			"the hundreds.\n" +
			"\n" +
			"Are you in Ecuador? Have you been affected by the earthquake? Email haveyoursay@bbc.co.uk with your " +
			"experience.\n" +
			"\n" +
			"Please include a contact number if you are willing to speak to a BBC journalist. You can contact us in " +
			"the following ways:\n" +
			"\n" +
			"Whatsapp: +44 7525 900971\n" +
			"Send pictures/video to yourpics@bbc.co.uk\n" +
			"Or Upload your pictures/video here\n" +
			"Tweet: @BBC_HaveYourSay\n" +
			"Send an SMS or MMS to 61124 or +44 7624 800 100";

	public static void main(String[] args) {
		Parser parser;
//		parser = new BasicParser();
		parser = new GroupParser();
		TextRank textrank = new TextRank.Builder(parser).setEpsilon(0.1).build();
		String content = Utils.normalise(MORE);

		System.out.println("CONTENT");
		System.out.println("-------");
		System.out.println(content);
		System.out.println();

		content = content.replaceAll("\\s+", " ");

		System.out.println("KEYWORDS");
		System.out.println("--------");
		List<String> keywords = textrank.getKeywords(content, Integer.MAX_VALUE);
		System.out.println(String.join(", ", keywords));
		System.out.println();

		System.out.println("SUMMARY");
		System.out.println("-------");
		List<String> sentences = textrank.getSentences(content, 0);
		System.out.println(String.join("\n", Utils.reorder(content, sentences)));
		System.out.println();

		logger.info("Done.");
	}
}
