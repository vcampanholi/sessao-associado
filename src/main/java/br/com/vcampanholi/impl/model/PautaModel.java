package br.com.vcampanholi.impl.model;import lombok.AllArgsConstructor;import lombok.Builder;import lombok.Data;import lombok.NoArgsConstructor;import java.time.LocalDate;@Data@Builder@NoArgsConstructor@AllArgsConstructorpublic class PautaModel {    private Long pautaId;    private String assunto;    private LocalDate dataCadastro;}