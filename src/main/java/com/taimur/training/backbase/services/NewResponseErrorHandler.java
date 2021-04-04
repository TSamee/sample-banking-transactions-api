package com.taimur.training.backbase.services;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import com.taimur.training.backbase.api.controller.TransactionServiceController;

public class NewResponseErrorHandler implements ResponseErrorHandler {

	Logger log = LoggerFactory.getLogger(TransactionServiceController.class);

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return (response.getStatusCode()
				.series() == HttpStatus.Series.CLIENT_ERROR
				|| response.getStatusCode()
						.series() == HttpStatus.Series.SERVER_ERROR);
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		String errorMessage = response.getStatusCode().value() + " -- "
				+ response.getStatusText();

		if (response.getStatusCode()
				.series() == HttpStatus.Series.SERVER_ERROR) {
			log.error("External API Server error: " + errorMessage);
			throw new IllegalStateException(
					"External API server error: " + errorMessage);
		} else if (response.getStatusCode()
				.series() == HttpStatus.Series.CLIENT_ERROR) {
			log.error("Client error: " + errorMessage);
			throw new RestClientException("Bad Request");
		}
	}

}
