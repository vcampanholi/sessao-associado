package br.com.vcampanholi.domain.pauta.mapper;import br.com.vcampanholi.stubs.PautaEntityStub;import br.com.vcampanholi.stubs.PautaRequestStub;import br.com.vcampanholi.stubs.PautaResponseStub;import org.junit.jupiter.api.Assertions;import org.junit.jupiter.api.Test;import org.junit.jupiter.api.extension.ExtendWith;import org.springframework.test.context.junit.jupiter.SpringExtension;import static org.junit.jupiter.api.Assertions.assertEquals;import static org.junit.jupiter.api.Assertions.assertNull;@ExtendWith(SpringExtension.class)class PautaMapperTest {    @Test    void deveRetornarNullSeParametroNaoForInformado() {        assertNull(PautaMapper.mapToEntity(null));        assertNull(PautaMapper.mapToResponse(null));    }    @Test    void deveRetornarUmaEntidadeParaPersistencia() {        var entity = PautaMapper.mapToEntity(PautaRequestStub.pautaRequest());        Assertions.assertEquals(entity, PautaEntityStub.entityToSave());    }    @Test    void deveRetornarUmaEntidadeComAResponse() {        var response = PautaMapper.mapToResponse(PautaEntityStub.entityToReturn());        assertEquals(response, PautaResponseStub.pautaResponse());    }}