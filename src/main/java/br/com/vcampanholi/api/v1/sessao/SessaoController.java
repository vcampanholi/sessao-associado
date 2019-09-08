package br.com.vcampanholi.api.v1.sessao;import br.com.vcampanholi.api.v1.sessao.facade.SessaoFacade;import br.com.vcampanholi.api.v1.sessao.model.request.SessaoRequest;import br.com.vcampanholi.api.v1.sessao.model.response.SessaoResponse;import io.swagger.annotations.*;import lombok.AllArgsConstructor;import org.springframework.web.bind.annotation.*;import javax.validation.Valid;@RestController@RequestMapping("sessao-associado/v1/pauta")@Api("Serviço para operações na sessão de votação.")@AllArgsConstructorpublic class SessaoController {    private SessaoFacade sessaoFacade;    @PostMapping("/{pauta_id}/sessao")    @ApiOperation(value = "Cria uma sessão para a pauta.", response = SessaoResponse.class)    @ApiResponses(value = {            @ApiResponse(code = 200, message = "OK", response = SessaoResponse.class),    })    public SessaoResponse criarSessao(@ApiParam(value = "Identificador da pauta.", required = true)                                      @PathVariable("pauta_id") Long pautaId,                                      @ApiParam(value = "Informações da sessão da pauta.", required = true)                                      @Valid @RequestBody SessaoRequest sessaoRequest) {        return sessaoFacade.criarSessao(pautaId, sessaoRequest);    }}