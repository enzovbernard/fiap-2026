package br.com.fiap.projeto_musica.model;

public enum PaisEnum {
	
	BRASIL("Brasil"),INGLATERRA("Inglaterra"),EUA("Estados Unidos"),AUSTRALIA("Australia");
	
	private String descricao;
	
	PaisEnum(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricacao() {
		return this.descricao;
	}
}
