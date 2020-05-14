package br.com.vcampanholi.api.v1.sessaovoto;import br.com.vcampanholi.api.v1.sessao.model.response.SessaoResponse;import br.com.vcampanholi.api.v1.sessaovoto.model.request.SessaoVotoRequest;import br.com.vcampanholi.domain.sessaovoto.service.SessaoVotoService;import io.swagger.annotations.*;import org.springframework.http.HttpStatus;import org.springframework.http.ResponseEntity;import org.springframework.web.bind.annotation.*;import javax.validation.Valid;@RestController@RequestMapping("sessao-associado/v1/pauta")@Api("Serviço para operações de votos na sessão.")public class SessaoVotoController {    private SessaoVotoService sessaoVotoService;    public SessaoVotoService getSessaoVotoService() {        return sessaoVotoService;    }    public void setSessaoVotoService(SessaoVotoService sessaoVotoService) {        this.sessaoVotoService = sessaoVotoService;    }    @PostMapping("/{pauta_id}/sessao/{sessao_id}/voto-sessao")    @ApiOperation(value = "Computa um voto do asscociado para a sessão.")    @ApiResponses(value = {            @ApiResponse(code = 200, message = "OK", response = SessaoResponse.class),    })    public ResponseEntity<Void> votar(@ApiParam(value = "Identificador da pauta.", required = true)                                      @PathVariable("pauta_id") Long pautaId,                                      @ApiParam(value = "Identificador da sessão.", required = true)                                      @PathVariable("sessao_id") Long sessaoId,                                      @ApiParam(value = "Informações da sessão da pauta.", required = true)                                      @Valid @RequestBody SessaoVotoRequest sessaoVotoRequest) {        sessaoVotoService.registarVoto(pautaId, sessaoId, sessaoVotoRequest);        return ResponseEntity.status(HttpStatus.CREATED).build();    }}