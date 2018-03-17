package br.com.casadocodigo.livro.testes.de.software.leilao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AvaliadorTest {

	private Avaliador leiloeiro;
	private Usuario joao;
	private Usuario jose;
	private Usuario maria;

	@Before
	public void criaAvaliador() {
		this.leiloeiro = new Avaliador();
		this.joao = new Usuario("Jo�o");
		this.jose = new Usuario("Jos�");
		this.maria = new Usuario("Maria");
	}

	@Test
	public void deveEntenderLancesEmOrdemCrescente() {
		Leilao leilao = new CriadorDeLeilao()
				.para("Playstation 3 Novo")
				.lance(joao, 250)
				.lance(jose, 300)
				.lance(maria, 400)
				.constroi();
		
		leiloeiro.avalia(leilao);
		
		assertThat(leiloeiro.getMenorLance(), equalTo(250.0));
		assertEquals(400.0, leiloeiro.getMaiorLance(), 0.00001);
	}

	@Test
	public void deveEntenderLeilaoComApenasUmLance() {
		Leilao leilao = new CriadorDeLeilao()
				.para("Playstation 3 Novo")
				.lance(joao, 1000.0)
				.constroi();

		leiloeiro.avalia(leilao);

		assertEquals(1000, leiloeiro.getMaiorLance(), 0.0001);
		assertEquals(1000, leiloeiro.getMenorLance(), 0.0001);
	}

	@Test
	public void deveEncontrarOsTresMaioresLances() {
		Leilao leilao = new CriadorDeLeilao()
			.para("Playstation 3 Novo")
			.lance(joao, 100)
			.lance(maria, 200)
			.lance(joao, 300)
			.lance(maria, 400)
			.constroi();

		leiloeiro.avalia(leilao);
		
		List<Lance> maiores = leiloeiro.getTresMaiores();
		
		assertThat(maiores, hasItems(
				new Lance(maria, 400),
				new Lance(joao, 300),
				new Lance(maria, 200)
			));
	}

	@Test(expected = RuntimeException.class)
	public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo").constroi();
		leiloeiro.avalia(leilao);
	}

}
