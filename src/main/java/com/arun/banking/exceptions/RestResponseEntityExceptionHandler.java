package com.arun.banking.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Banking Error Handler that intercepts Banking Error thrown and transforms the
 * response accodingly
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Handler Function for Banking Exception
   * 
   * @param ex      The Banking Exception that was thrown
   * @param request the web request that will be augmented for the thrown banking
   *                exeption
   * @return A response entity that will cascade the exception thrown to
   *         requesting system
   */
  @ExceptionHandler(BankingException.class)
  protected ResponseEntity<Object> bankingException(final BankingException ex, WebRequest request) {
    String bodyOfResponse = ex.getBankingError().getMessage() + ":" + ex.getMessage();
    return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), ex.getBankingError().getHttpStatus(),
        request);
  }

}