package br.com.vcampanholi.exception.handler;

import br.com.vcampanholi.exception.GenericException;
import br.com.vcampanholi.exception.constants.ErrorsEnum;
import br.com.vcampanholi.exception.handler.response.ErrorDetail;
import br.com.vcampanholi.exception.handler.response.ErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RestControllerAdvice
public class ControllerAdviceConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAdviceConfiguration.class);

    private static final String DEFAULT_LANGUAGE_TAG = "pt-BR";

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorInfo> genericException(GenericException error, HttpServletRequest httpServletRequest) {
        LOGGER.error("ControllerAdviceConfiguration.genericException ", error);
        List<ErrorDetail> errors = List.of(new ErrorDetail(error.getStatus().name(), error.getMessage()));
        var errorInfo = ErrorInfo.create()
                .errors(errors)
                .language(DEFAULT_LANGUAGE_TAG)
                .namespace(httpServletRequest.getServletPath())
                .build();
        return ResponseEntity.status(error.getStatus()).body(errorInfo);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorInfo httpMessageNotReadableException(HttpMessageNotReadableException error,
                                                     HttpServletRequest httpServletRequest) {
        LOGGER.error("ControllerAdviceConfiguration.httpMessageNotReadableException ", error);

        List<ErrorDetail> errors = List.of(new ErrorDetail(ErrorsEnum.HTTP_MESSAGE_NOT_READABLE.name(), "O corpo da requisição não pôde ser lido"));
        return ErrorInfo.create()
                .namespace(httpServletRequest.getServletPath())
                .language(DEFAULT_LANGUAGE_TAG)
                .errors(errors)
                .build();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorInfo methodArgumentNotValidException(MethodArgumentNotValidException e,
                                                     HttpServletRequest httpServletRequest) {
        LOGGER.error("ControllerAdviceConfiguration.methodArgumentNotValidException ", e);
        List<ErrorDetail> errors = new ArrayList<>();
        var fieldErrors = e.getBindingResult().getFieldErrors();
        if (!ObjectUtils.isEmpty(fieldErrors)) {
            errors = fieldErrors.stream()
                    .map(this::buildFieldErrorInfo)
                    .collect(Collectors.toList());
        }
        return ErrorInfo.create()
                .namespace(httpServletRequest.getServletPath())
                .language(DEFAULT_LANGUAGE_TAG)
                .errors(errors)
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorInfo constraintViolationException(ConstraintViolationException error,
                                                  HttpServletRequest httpServletRequest) {
        LOGGER.error("ControllerAdviceConfiguration.constraintViolationException ", error);
        var errors = error.getConstraintViolations().stream()
                .map(violation -> new ErrorDetail(ErrorsEnum.METHOD_ARGUMENT_NOT_VALID.name(), violation.getMessage()))
                .collect(Collectors.toList());
        return ErrorInfo.create()
                .namespace(httpServletRequest.getServletPath())
                .language(DEFAULT_LANGUAGE_TAG)
                .errors(errors)
                .build();
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorInfo bindException(BindException error, HttpServletRequest httpServletRequest) {
        LOGGER.error("ControllerAdviceConfiguration.bindException ", error);
        var errors = error.getAllErrors().stream()
                .map(violation -> new ErrorDetail(violation.getDefaultMessage(), ErrorsEnum.METHOD_ARGUMENT_NOT_VALID.name()))
                .collect(Collectors.toList());
        return ErrorInfo.create()
                .namespace(httpServletRequest.getServletPath())
                .language(DEFAULT_LANGUAGE_TAG)
                .errors(errors)
                .build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorInfo httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException error,
                                                            HttpServletRequest httpServletRequest) {
        LOGGER.error("ControllerAdviceConfiguration.httpRequestMethodNotSupportedException ", error);
        List<ErrorDetail> errors = List.of(new ErrorDetail(ErrorsEnum.METHOD_ARGUMENT_NOT_VALID.name(), "Método http não é suportado para a requisição"));
        return ErrorInfo.create()
                .namespace(httpServletRequest.getServletPath())
                .language(DEFAULT_LANGUAGE_TAG)
                .errors(errors)
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorInfo exceptionHandler(Exception error, HttpServletRequest httpServletRequest) {
        LOGGER.error("ControllerAdviceConfiguration.exceptionHandler ", error);
        List<ErrorDetail> errors = List.of(new ErrorDetail(ErrorsEnum.INTERNAL_SERVER_ERROR.name(), "Ocorreu um erro inesperado na aplicação"));
        return ErrorInfo.create()
                .namespace(httpServletRequest.getServletPath())
                .language(DEFAULT_LANGUAGE_TAG)
                .errors(errors)
                .build();
    }

    private ErrorDetail buildFieldErrorInfo(FieldError error) {
        return new ErrorDetail(ErrorsEnum.METHOD_ARGUMENT_NOT_VALID.name(), error.getDefaultMessage());
    }
}