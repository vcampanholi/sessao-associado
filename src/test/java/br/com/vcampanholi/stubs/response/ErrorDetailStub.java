package br.com.vcampanholi.stubs.response;import br.com.vcampanholi.exception.constants.ErrorsEnum;import br.com.vcampanholi.exception.handler.response.ErrorDetail;public class ErrorDetailStub {    public static ErrorDetail errorDetail(String message, ErrorsEnum erroEnum) {        return ErrorDetail.builder()                .message(message)                .type(erroEnum.name())                .build();    }}