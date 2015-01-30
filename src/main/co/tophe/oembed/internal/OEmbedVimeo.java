package co.tophe.oembed.internal;


public class OEmbedVimeo extends OEmbedParserWithPattern {

	public static final OEmbedVimeo INSTANCE = new OEmbedVimeo();

	private OEmbedVimeo() {
		super("http://vimeo.com/*", "http://vimeo.com/api/oembed.json");
	}
}
