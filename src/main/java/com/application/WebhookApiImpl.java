package com.application;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Slf4j
@Controller
public class WebhookApiImpl {

	@RequestMapping(value = "/payload",
			produces = { "application/json" },
			consumes = { "application/json" },
			method = RequestMethod.POST)
	public ResponseEntity<String> payload(Object body) {
		// do some magic!
		log.info("OKKKK");
		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
