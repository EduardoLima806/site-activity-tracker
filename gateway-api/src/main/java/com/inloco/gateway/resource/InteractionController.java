package com.inloco.gateway.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.inloco.gateway.model.UserInteraction;

@RestController
public class InteractionController {

	@PostMapping("/interaction")
	public ResponseEntity<Object> registerInteraction(@RequestBody UserInteraction userInteraction) {
		final String uri = "http://app:3001/api/interactions";
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<Object> result = restTemplate.postForEntity(uri, userInteraction, Object.class);
	    return result;
	}
}
