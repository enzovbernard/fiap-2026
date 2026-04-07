package br.com.fiap.projeto_musica.model;
 
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
 
@Schema(description = "Essa entidade tem por objetivo representar a tabela artista")
@Entity
@Table(name = "artista")
public class Artista {
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "nome")
	@NotEmpty(message = "O nome do artista é um campo obrigatório")
	@Size(min = 1, max = 65, message = "O nome do artista deve possuir ao menos 1 caracter e, "
			+ "no máximo, 65 caracteres")
	@Schema(description = "Esse atributo representa o nome do artista/banda",
	example = "Capital Inicial")
	private String nome;
	@PastOrPresent(message = "A data de fundação do artista/banda deve ser a atual "
			+ "ou uma data passada")
	@Schema(description = "Esse atributo representa a data de fundação da banda",
	example = "1995-01-01")
	private LocalDate data_fundacao;
	@NotEmpty(message = "O país de origem é um campo obrigatório")
	@Size(min = 1, max = 20, message = "O país de origem deve possuir ao menos 1 caracter e, "
			+ "no máximo, 20 caracteres")
	@Enumerated(EnumType.STRING)
	@Schema(description = "Esse atributo representa o país de origem do artista/banda e, " +
	"obrigatoriamente, deve receber um dos possíveis valores presentes em PaisEnum "
			+ "(BRASIL, EUA, INGLATERRA E AUSTRALIA)", example = "BRASIL")
	private PaisEnum pais_origem;

	private Boolean ativo;
 
	public Artista() {
 
	}
 
	public Artista(Long id, String nome, LocalDate data_fundacao, PaisEnum pais_origem, Boolean ativo) {
		super();
		this.id = id;
		this.nome = nome;
		this.data_fundacao = data_fundacao;
		this.pais_origem = pais_origem;
		this.ativo = ativo;
	}
 
	public Long getId() {
		return id;
	}
 
	public void setId(Long id) {
		this.id = id;
	}
 
	public String getNome() {
		return nome;
	}
 
	public void setNome(String nome) {
		this.nome = nome;
	}
 
	public LocalDate getData_fundacao() {
		return data_fundacao;
	}
 
	public void setData_fundacao(LocalDate data_fundacao) {
		this.data_fundacao = data_fundacao;
	}
 
	public PaisEnum getPais_origem() {
		return pais_origem;
	}
 
	public void setPais_origem(PaisEnum pais_origem) {
		this.pais_origem = pais_origem;
	}
 
	public Boolean getAtivo() {
		return ativo;
	}
 
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
 
}