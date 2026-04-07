package br.com.fiap.projeto_musica.control;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.projeto_musica.dto.MusicaDTO;
import br.com.fiap.projeto_musica.model.Musica;
import br.com.fiap.projeto_musica.projection.MusicaProjection;
import br.com.fiap.projeto_musica.repository.MusicaRepository;
import br.com.fiap.projeto_musica.service.MusicaCachingService;
import br.com.fiap.projeto_musica.service.MusicaPaginacaoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/musicas")
public class MusicaController {

	@Autowired
	private MusicaRepository repM;
	
	@Autowired
	private MusicaPaginacaoService paginacaoM;
	
	@Autowired
	private MusicaCachingService cacheM;
	
	@Operation(description = "Este endpoint realiza a busca de músicas em forma de "
			+ "páginas de MusicasDTO (relacionadas aos valores de page e size)",
			summary = "Retornar páginas de MúsicasDTO",
			tags = "Retorno de Informações")
	@GetMapping(value = "/paginadas")
	public ResponseEntity<Page<MusicaDTO>> paginar(
		@RequestParam(name = "page", defaultValue = "0")	Integer page,
		@RequestParam(name = "size", defaultValue = "2")	Integer size){	
		PageRequest pr = PageRequest.of(page,size);
		Page<MusicaDTO> musicas_dto_paginadas = paginacaoM.paginar(pr);
		return ResponseEntity.ok(musicas_dto_paginadas);
	}
	
	@Operation(description = "Este endpoint retornar todas as músicas (via cache)",
			summary = "Retornar todas as músicas",
			tags = "Retorno de Informações")
	@GetMapping(value = "/todas")
	public List<Musica> retornarTodasMusicas() {
		return cacheM.findAll();
	}
	
	@Operation(description = "Este endpoint retorna músicas por ID",
			summary = "Retornar músicas por ID",
			tags = "Retorno de Informações")
	@GetMapping(value = "/{id}")
	public Musica retornarMusicaPorID(@PathVariable Long id) {

		Optional<Musica> op = cacheM.findById(id);

		if (op.isPresent()) {
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}
	
	@Operation(description = "Este endpoint retorna as projeções de músicas "
			+ "(atributos de interesse presentes na entidade Música) com base "
			+ "em uma busca por substring (via Caching)",
			summary = "Retornar projeções de músicas via substring (caching)",
			tags = "Retorno de Informações")
	@GetMapping(value = "/substring_caching")
	public List<MusicaProjection> 
	retornarMusicasPorSubstringCaching(@RequestParam String substring){
		return cacheM.retornarMusicasPorSubstring(substring);
	}
	
	@Operation(description = "Este endpoint retorna as projeções de músicas "
			+ "(atributos de interesse presentes na entidade Música) com base "
			+ "em uma busca por substring",
			summary = "Retornar projeções de músicas via substring",
			tags = "Retorno de Informações")
	@GetMapping(value = "/substring")
	public List<MusicaProjection> 
	retornarMusicasPorSubstring(@RequestParam String substring){
		return repM.retornarMusicasPorSubstring(substring);
	}
	
	@Operation(description = "Este endpoint retorna uma lista de músicas por duração "
			+ "(otimizado por consuta JPQL/SQL)",
			summary = "Retornar músicas por duração (otimizado)",
			tags = "Retorno de Informações")
	@GetMapping(value = "/duracao_otimizado")
	public List<Musica> retornarMusicasPorDuracaoOtimizado(@RequestParam Double x){
		return cacheM.retornarMusicasPorDuracao(x);
	}
	
	@Operation(description = "Este endpoint retorna uma lista de músicas "
			+ "por duração",
			summary = "Retornar músicas por duração",
			tags = "Retorno de Informações")
	@GetMapping(value = "/duracao")
	public List<Musica> retornarMusicasPorDuracao(@RequestParam Double x) {

		List<Musica> todas = cacheM.findAll();
		List<Musica> validas = new ArrayList<Musica>();

		for (Musica musica : todas) {
			if (musica.getDuracao() >= x) {
				validas.add(musica);
			}
		}

		return validas;

	}
	
	@Operation(description = "Este endpoint retorna projeções de músicas por data "
			+ "de lançamento (otimizado por consulta SQL)",
			summary = "Retornar projeções de música por data (otimizado)",
			tags = "Retorno de Informações")
	@GetMapping(value = "/data_lancamento_otimizado")
	public List<MusicaProjection> retornarMusicasPorDataOtimizado(@RequestParam LocalDate x){
		return repM.retornarMusicasPorData(x);
	}
	
	@Operation(description = "Este endpoint retorna projeções de músicas por "
			+ "data de lançamento",
			summary = "Retornar projeções de músicas por data",
			tags = "Retorno de Informações")
	@GetMapping(value = "/data_lancamento")
	public List<Musica> retornarMusicasPorData(@RequestParam LocalDate x) {

		List<Musica> todas = cacheM.findAll();
		List<Musica> validas = new ArrayList<Musica>();

		for (Musica musica : todas) {
			if (musica.getData_lancamento().isBefore(x) || musica.getData_lancamento().isEqual(x)) {
				validas.add(musica);
			}
		}

		return validas;

	}
	
	@Operation(description = "Este endpoint tem como objetivo inserir novas músicas",
			summary = "Inserir nova música",
			tags = "Persistência de Informações")
	@PostMapping(value = "/inserir")
	public Musica inserirMusica(@RequestBody @Valid Musica musica) {
		repM.save(musica);
		cacheM.removerCache();
		return musica;
	}
	@Operation(description = "Este endpoint realiza a remoção de músicas",
			summary = "Remover música",
			tags = "Remoção de Informações")
	@DeleteMapping(value = "/{id}")
	public Musica removerMusica(@PathVariable Long id) {
		Optional<Musica> op = cacheM.findById(id);

		if (op.isPresent()) {
			repM.delete(op.get());
			cacheM.removerCache();
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}
	@Operation(description = "Este endpoint realiza a atualização de músicas",
			summary = "Atualizar música",
			tags = "Atualização de Informações")
	@PutMapping(value = "/{id}")
	public Musica atualizarMusica(@PathVariable Long id, @RequestBody @Valid Musica musica) {
		Optional<Musica> op = cacheM.findById(id);

		if (op.isPresent()) {
			Musica musica_banco = op.get();
			musica_banco.transferirMusica(musica);
			repM.save(musica_banco);
			cacheM.removerCache();
			return musica_banco;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

}
