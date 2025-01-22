package com.zenni.escher.request;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.client.methods.HttpRequestBase;

import com.zenni.escher.EscherRequest;

public class ZenniEscherRequest implements EscherRequest {
	
	private HttpRequestBase httpRequest;
	
	private String body;

	public ZenniEscherRequest(HttpRequestBase httpRequest, String body) {
		this.httpRequest = httpRequest;
		this.body = body;
	}

	@Override
	public String getHttpMethod() {
		return httpRequest.getMethod();
	}

	@Override
	public URI getURI() {
		return httpRequest.getURI();
	}

	@Override
	public List<EscherRequest.Header> getRequestHeaders() {
		return Arrays.asList(httpRequest.getAllHeaders()).stream()
				.map(header -> new EscherRequest.Header(header.getName(), header.getValue()))
				.collect(Collectors.toList());
	}

	@Override
	public void addHeader(String fieldName, String fieldValue) {
		httpRequest.addHeader(fieldName, fieldValue);
	}

	@Override
	public String getBody() {
		return body;
	}

	public HttpRequestBase getHttpRequest() {
		return httpRequest;
	}

}
