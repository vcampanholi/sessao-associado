package br.com.vcampanholi.stubs.entity;import br.com.vcampanholi.domain.sessaovoto.repository.entity.SessaoVotoEntity;public class SessaoVotoEntityStub {    public static SessaoVotoEntity sessaoVotoEntity() {        SessaoVotoEntity sessaoVotoEntity = new SessaoVotoEntity();        sessaoVotoEntity.setVoto(Boolean.TRUE);        sessaoVotoEntity.setCpfAssociado("18979793049");        return sessaoVotoEntity;    }}