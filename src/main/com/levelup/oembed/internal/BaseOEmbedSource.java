package com.levelup.oembed.internal;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.levelup.oembed.OEmbed;
import com.levelup.oembed.OEmbedRequest;
import com.levelup.oembed.OEmbedSource;

import co.tophe.TopheClient;
import co.tophe.HttpException;
import co.tophe.ServerException;
import co.tophe.UriParams;

public abstract class BaseOEmbedSource implements OEmbedSource {

	private OEmbed oembedData;
	private final String endpoint;
	private final String url;

	protected BaseOEmbedSource(@NonNull String endpoint, @NonNull Uri fromUri) {
		this.endpoint = endpoint;
		this.url = fromUri.toString();
	}
	
	final void assertDataLoaded() throws ServerException, HttpException {
		OEmbedRequest request = getOembedRequest();
		oembedData = TopheClient.parseRequest(request);
	}
	
	@Override
	public final OEmbedRequest getOembedRequest() {
		UriParams params = new UriParams(2);
		params.add("url", url);
		params.add("format", "json");
		return new OEmbedRequestGet(endpoint, params);
	}
	
	@Override
	public String getThumbnail() throws ServerException, HttpException {
		assertDataLoaded();
		
		if (null!=oembedData) {
			String thumbnail = oembedData.getThumbnail();
			if (TextUtils.isEmpty(thumbnail))
				thumbnail = oembedData.getPhotoUrl();
			return thumbnail;
		}
		return null;
	}
}
