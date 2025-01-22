package com.jwt.token;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateJWToken {
	
	public static String generateToken(String profileId, Boolean status, long tokenExpiry, String secretKey,
			String secretKeywithoutProfileId) throws JsonProcessingException {
	
		//String secretKey = "ATG4817A5397DE815EC486FBD4DDB867";
		//String secretKeywithoutProfileId = "MULE4015A5IR7DE815EC486UTH4YDB056";
		
		Algorithm algorithm = Algorithm.HMAC256((profileId != null && profileId != "") ? secretKey:secretKeywithoutProfileId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id","test4");
		map.put("email","test4@gmail.com");
		map.put("firstName","test1");
		map.put("lastName","test2");
		map.put("zipCode","94903");
		map.put("optInStatus","YES");
		map.put("status","ACTIVE");
		map.put("phone","123-123-4567");
		map.put("birthDate","1981-10-21");
		map.put("anniversaryDate","2010-08-23");
		String payload = null;
		if(map != null && map.size() > 0) {
			JSONObject object = new JSONObject(map);
			ObjectMapper mapper = new ObjectMapper();
			payload =  mapper.writeValueAsString(object);
		}
		System.out.println("***********************************************************");
		System.out.println("Raw Payload "+payload);
		
		String encodedRequest = Base64.encodeBase64String(payload.getBytes());
		
		byte[] bytes = algorithm.sign(encodedRequest.getBytes());
		String hmac = Base64.encodeBase64String(bytes);
		System.out.println("Payload  HMAC " +hmac);	
		/*Expires after 10 Minutes*/
		
		long date = System.currentTimeMillis() + tokenExpiry;
		String token="";
		if(profileId != null && profileId != "") {
			 System.out.println("Profile ID is : " + profileId);
			 System.out.println("################secretKeywithProfileId########### :: " + secretKey);
			 token = JWT.create().withSubject(profileId).withClaim("status", status).withClaim("prn", profileId).withExpiresAt(new Date(date)).sign(algorithm);
		} else if(profileId == null) {
			System.out.println("Profile ID is null : " + profileId);
			System.out.println("####secretKeywithoutProfileId##### :: " + secretKeywithoutProfileId);
			token = JWT.create().withExpiresAt(new Date(date)).sign(algorithm);
		} else {
			token ="";
		}
		
	   // String token = JWT.create().withSubject("profile49806733").withClaim("status", "false").withClaim("hmac", hmac).withIssuedAt(new Date()).withExpiresAt(new Date(date)).sign(algorithm);
		//String token = JWT.create().withSubject("rahulkumar200").withClaim("status", false).withExpiresAt(new Date(date)).sign(algorithm);
		//String token = JWT.create().withSubject("").withClaim("status", false).withExpiresAt(new Date(date)).sign(algorithm);
		//String token = JWT.create().withClaim("status", false).withExpiresAt(new Date(date)).sign(algorithm);
	     
	    System.out.println("Token      "+token);
	    System.out.println("***********************************************************");
	  return token;
	}
}
