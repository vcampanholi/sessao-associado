package br.com.vcampanholi.stubs.response;import br.com.vcampanholi.api.v1.pauta.model.response.PautaResponse;import java.time.LocalDate;public class PautaResponseStub {    public static PautaResponse pautaResponse() {        return PautaResponse.builder()                .id(1L)                .assunto("Estratégia dos associados.")                .dataCadastro(LocalDate.now())                .build();    }}