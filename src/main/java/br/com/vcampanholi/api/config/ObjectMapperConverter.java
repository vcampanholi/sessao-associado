package br.com.vcampanholi.api.config;import br.com.vcampanholi.exception.GenericException;import com.fasterxml.jackson.annotation.JsonInclude;import com.fasterxml.jackson.databind.ObjectMapper;import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;import lombok.AccessLevel;import lombok.NoArgsConstructor;import lombok.extern.slf4j.Slf4j;import org.springframework.http.HttpStatus;@Slf4j@lombok.Generated@NoArgsConstructor(access = AccessLevel.PRIVATE)public class ObjectMapperConverter {    public static String convertToString(final Object obj) {        try {            ObjectMapper mapper = new ObjectMapper();            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);            mapper.registerModule(new JavaTimeModule());            return mapper.writeValueAsString(obj);        } catch (Exception e) {            log.error(String.valueOf(e));            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);        }    }}