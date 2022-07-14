package br.reinan.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.reinan.cm.execao.ExplosaoException;

public class Tabuleiro {
	private int quantidadeLinha;
	private int quantidadeColuna;
	private int quantidadeMinas;

	private final List<Campo> campos = new ArrayList<>();

	public Tabuleiro(int quantidadeLinha, int quantidadeColuna, int quantidadeMinas) {

		this.quantidadeLinha = quantidadeLinha;
		this.quantidadeColuna = quantidadeColuna;
		this.quantidadeMinas = quantidadeMinas;

		gerarCampos();
		associarOsVizinhos();
		sortearMinas();
	}

	public void abrir(int linha, int coluna) {
		try {
			campos.stream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna).findFirst()
					.ifPresent(c -> c.abrir());
		} catch (ExplosaoException e) {
			campos.forEach(c -> c.setAberto(true));
			throw e;
		}
	}

	public void alternarMarcacao(int linha, int coluna) {
		campos.stream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna).findFirst()
				.ifPresent(c -> c.alternarMarcacao());
	}

	private void gerarCampos() {
		for (int l = 0; l < quantidadeLinha; l++) {
			for (int c = 0; c < quantidadeColuna; c++) {

				campos.add(new Campo(l, c));
			}
		}
	}

	private void associarOsVizinhos() {

		for (Campo c1 : campos) {
			for (Campo c2 : campos) {
				c1.adicionarVizinho(c2);
			}
		}
	}

	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();

		do {

			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(minado).count();
		} while (minasArmadas < quantidadeMinas);
	}

	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}

	public void reiniciar() {

		campos.forEach(c -> c.reiniciar());
		sortearMinas();

	}

	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("  ");
			for (int c = 0; c < quantidadeColuna; c++) {
				sb.append(" ");
				sb.append(c);
				sb.append(" ");
			}
		sb.append("\n");
		
		int i = 0;
		for (int l = 0; l < quantidadeLinha; l++) {
			sb.append(l);
			sb.append(" ");
			for (int c = 0; c < quantidadeColuna; c++) {
				sb.append(" ");
				sb.append(campos.get(i));
				sb.append(" ");
				i++;
			}

			sb.append("\n");
		}

		return sb.toString();
	}

}
