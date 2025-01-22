package com.zenni.escher.request;

import java.io.IOException;
import java.util.Arrays;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;

import com.zenni.escher.Escher;
import com.zenni.escher.EscherException;


public class AntavoRequestHeaders {

	static PropertyAccess propertyAccess = new PropertyAccess();
	static String CREDENTIAL_SCOPE = propertyAccess.getProperty("antavo.api.credential.scope");
	
	public static Header[] get(String pURL,String host, String contentType) throws EscherException, ClientProtocolException, IOException {
		HttpRequestBase request = new HttpGet(pURL);
		
		request.addHeader("host", host);
		request.setHeader("Content-type", contentType);
		request = signGetRequest(request);
		return request.getAllHeaders();
	}
	
	private static HttpRequestBase signGetRequest(HttpRequestBase request) throws EscherException {
		String credentialScope = CREDENTIAL_SCOPE;
		ZenniEscherRequest escherRequest = new ZenniEscherRequest(request, "");
		Escher escher = new Escher(credentialScope).setAuthHeaderName("Authorization").setDateHeaderName("date")
				.setAlgoPrefix("ANTAVO");
		escher.signRequest(escherRequest, propertyAccess.getProperty("antavo.api.key"), propertyAccess.getProperty("antavo.api.secret"), Arrays.asList("date", "host"));
		return escherRequest.getHttpRequest();
	}
	
	public static Header[] post(String pUrl, String pRequestBody, String host, String accept, String contentType)
			throws EscherException, ClientProtocolException, IOException {
		
		HttpRequestBase request = new HttpPost(pUrl);
		request.addHeader("host", host);
		request.setHeader("Accept", accept);
		request.setHeader("Content-type", contentType);
		StringEntity entity = new StringEntity(pRequestBody);
		((HttpPost) request).setEntity(entity);
		request = signPostRequest(request,pRequestBody);
		return request.getAllHeaders();

	}

	private static HttpRequestBase signPostRequest(HttpRequestBase request,String pRequestBody) throws EscherException {
		String credentialScope = CREDENTIAL_SCOPE;
		ZenniEscherRequest escherRequest = new ZenniEscherRequest(request, pRequestBody);
		Escher escher = new Escher(credentialScope).setAuthHeaderName("Authorization").setDateHeaderName("date")
				.setAlgoPrefix("ANTAVO");
        escher.signRequest(escherRequest, propertyAccess.getProperty("antavo.api.key"), propertyAccess.getProperty("antavo.api.secret"), Arrays.asList("date", "host"));
        return escherRequest.getHttpRequest();
    }
}
