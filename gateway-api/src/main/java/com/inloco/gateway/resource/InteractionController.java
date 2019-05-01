package com.inloco.gateway.resource;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.inloco.gateway.model.UserInteraction;
import com.inloco.gateway.security.SecurityConstants;

@RestController
public class InteractionController {

	@PostMapping("/interaction")
	public ResponseEntity<Object> registerInteraction(@RequestHeader(name = SecurityConstants.HEADER_STRING) String token, @RequestBody UserInteraction userInteraction) {
		final String uri = "http://app:3001/api/interactions";
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Authorization", token);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		HttpEntity<UserInteraction> request = new HttpEntity<UserInteraction>(userInteraction, headers);
		
	    ResponseEntity<Object> result = restTemplate.postForEntity(uri, request, Object.class);
	    return result;
	}
}
