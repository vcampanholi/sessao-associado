package br.com.vcampanholi.domain.sessao.service;

import br.com.vcampanholi.api.v1.sessao.model.request.SessaoRequest;
import br.com.vcampanholi.api.v1.sessao.model.response.SessaoResponse;
import br.com.vcampanholi.domain.pauta.service.PautaService;
import br.com.vcampanholi.domain.sessao.enumerations.SituacaoEnum;
import br.com.vcampanholi.domain.sessao.mapper.SessaoMapper;
import br.com.vcampanholi.domain.sessao.repository.SessaoRepository;
import br.com.vcampanholi.domain.sessao.repository.entity.SessaoEntity;
import br.com.vcampanholi.exception.GenericException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SessaoService {

    private PautaService pautaService;
    private SessaoRepository sessaoRepository;

    public SessaoService(PautaService pautaService, SessaoRepository sessaoRepository) {
        this.pautaService = pautaService;
        this.sessaoRepository = sessaoRepository;
    }

    public SessaoResponse criarSessao(Long pautaId, SessaoRequest sessao) {
        var entity = SessaoMapper.mapToEntity(sessao);
        entity.setPauta(pautaService.buscarPauta(pautaId));
        entity.setSituacao(SituacaoEnum.ABERTA);
        return SessaoMapper.mapToResponse(this.gravar(entity));
    }

    public SessaoEntity buscarSessao(Long sessaoId) {
        return sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new GenericException(
                        "Nenhuma sessão encontrada com id: ".concat(sessaoId.toString()),
                        HttpStatus.NOT_FOUND));
    }

    public SessaoEntity gravar(SessaoEntity sessao) {
        return sessaoRepository.save(sessao);
    }
}