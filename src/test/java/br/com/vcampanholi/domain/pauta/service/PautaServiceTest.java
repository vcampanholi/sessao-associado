package br.com.vcampanholi.domain.pauta.service;import br.com.vcampanholi.api.v1.stubs.PautaEntityStub;import br.com.vcampanholi.api.v1.stubs.PautaRequestStub;import br.com.vcampanholi.domain.pauta.repository.PautaRepository;import br.com.vcampanholi.exception.GenericException;import org.junit.jupiter.api.Test;import org.junit.jupiter.api.extension.ExtendWith;import org.mockito.InjectMocks;import org.mockito.Mock;import org.springframework.http.HttpStatus;import org.springframework.test.context.junit.jupiter.SpringExtension;import java.util.Optional;import static org.junit.jupiter.api.Assertions.assertEquals;import static org.junit.jupiter.api.Assertions.assertThrows;import static org.mockito.ArgumentMatchers.any;import static org.mockito.Mockito.when;@ExtendWith(SpringExtension.class)class PautaServiceTest {    @Mock    private PautaRepository pautaRepository;    @InjectMocks    private PautaService pautaService;    @Test    void deveRetornarUmaPautaGravada() {        when(pautaRepository.save(any())).thenReturn(PautaEntityStub.entityToReturn());        var request = pautaService.criarPauta(PautaRequestStub.pautaRequest());        assertEquals(request, PautaEntityStub.entityToReturn());    }    @Test    void deveRetornarUmaPautaAoBuscar() {        when(pautaRepository.findById(any())).thenReturn(Optional.of(PautaEntityStub.entityToReturn()));        var entity = pautaService.buscarPauta(1L);        assertEquals(entity, PautaEntityStub.entityToReturn());    }    @Test    public void deveRetornarUmaExceptionQuandoNaoEncontrarPauta() {        when(pautaRepository.findById(any())).thenReturn(Optional.empty());        var thrown = assertThrows(GenericException.class, () ->                pautaService.buscarPauta(2L)        );        assertEquals("Nenhuma pauta encontrada para a consulta.", thrown.getMessage());        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());    }}