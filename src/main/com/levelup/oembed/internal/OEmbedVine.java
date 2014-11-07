package com.levelup.oembed.internal;

import java.util.List;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.levelup.oembed.OEmbed;
import com.levelup.oembed.OEmbedRequest;
import com.levelup.oembed.OEmbedSource;

import co.tophe.HttpException;
import co.tophe.ServerException;
import co.tophe.TopheClient;

public class OEmbedVine implements OEmbedParser {

	public static final OEmbedVine instance = new OEmbedVine();

	private OEmbedVine() {
	}
	
	@Override
	public OEmbedSource getSource(@NonNull Uri fromUri) {
		if (fromUri.getHost().endsWith("vine.co")) {
			List<String> path = fromUri.getPathSegments();
			if (path.size() > 1) {
				if ("v".equals(path.get(0))) {
					return new OEmbedSourceVine(path.get(1));
				}
			}
		}
		return null;
	}

	private static class OEmbedSourceVine implements OEmbedSource {
		private final String vineId;

		private OEmbed oembedData;

		OEmbedSourceVine(@NonNull String vineId) {
			this.vineId = vineId;
		}

		final void assertDataLoaded() throws HttpException, ServerException {
			OEmbedRequest request = getOembedRequest();
			oembedData = TopheClient.parseRequest(request);
		}

		@Override
		public String getThumbnail() throws HttpException, ServerException {
			assertDataLoaded();

			if (null!=oembedData) {
				String thumbnail = oembedData.getThumbnail();
				if (TextUtils.isEmpty(thumbnail))
					thumbnail = oembedData.getPhotoUrl();
				return thumbnail;
			}
			return null;
		}

		@Override
		public OEmbedRequest getOembedRequest() {
			return new OEmbedRequestGet("https://vine.co/oembed/"+vineId+".json", null);
		}
	}
}
