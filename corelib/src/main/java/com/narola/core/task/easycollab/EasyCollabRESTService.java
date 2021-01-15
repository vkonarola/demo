package com.narola.core.task.easycollab;

import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.narola.core.exception.BusinessException;
import com.narola.core.task.easycollab.payload.request.EasyCollabTimeSheet;

@Component
public class EasyCollabRESTService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String API_RESPONSE_PARAM_MESSAGE = "Message";
	private static final String API_SUCESSFULL_MSG = "Succussfully added Timesheet.";

	@Value("${app.easycollab.timeSheetSubmitUrl}")
	private String timeSheetSubmitUrl;

	@Value("${app.easycollab.timeSheetSubmitUrlToken}")
	private String timeSheetSubmitUrlToken;

	public boolean timeSheetSubmitAPI(EasyCollabTimeSheet easyCollabTimeSheet) {
		this.logger.debug("timeSheetSubmitAPI request:{}", easyCollabTimeSheet);

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<EasyCollabTimeSheet> request = new HttpEntity<>(easyCollabTimeSheet, headers);
		ResponseEntity<Map> response = restTemplate.postForEntity(timeSheetSubmitUrl, request, Map.class);
		this.logger.debug("timeSheetSubmitAPI response:{}", response.getBody());
		if (response.getStatusCode() != HttpStatus.OK) {
			throw new BusinessException("Oops something went wrong. Please contact to admin.");
		}
		if (response.getBody().get(API_RESPONSE_PARAM_MESSAGE).equals(API_SUCESSFULL_MSG)) {
			return true;
		}
		return false;
	}

	public String getTimeSheetSubmitUrlToken() {
		return timeSheetSubmitUrlToken;
	}

}
