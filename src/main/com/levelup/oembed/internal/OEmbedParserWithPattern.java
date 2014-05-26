package com.levelup.oembed.internal;

import java.util.regex.Pattern;

import android.net.Uri;

public abstract class OEmbedParserWithPattern implements OEmbedParser {

	private final Pattern pattern;
	private final String endpoint;

	public OEmbedParserWithPattern(String pattern, String endpoint) {
		if (null==pattern) throw new NullPointerException();
		if (null==endpoint) throw new NullPointerException();
		this.pattern = Pattern.compile(pattern);
		this.endpoint = endpoint;
	}

	@Override
	public OEmbedSource getSource(Uri fromUri) {
		if (pattern.matcher(fromUri.toString()).find()) {
			return new OEmbedSource(fromUri);
		}
		return null;
	}

	private class OEmbedSource extends BaseOEmbedSource {
		public OEmbedSource(Uri fromUri) {
			super(OEmbedParserWithPattern.this.endpoint, fromUri);
		}
	}
}
