package br.com.vcampanholi.domain.enumerations;import lombok.AllArgsConstructor;import lombok.Getter;@Getter@AllArgsConstructorpublic enum StatusPautaEnum {    ABERTA(1),    ENCERRADA(2);    private Integer value;    public static StatusPautaEnum status(Integer value) {        StatusPautaEnum statusPauta = null;        for (StatusPautaEnum situation : StatusPautaEnum.values()) {            if (situation.value.equals(value))                statusPauta = situation;        }        return statusPauta;    }}