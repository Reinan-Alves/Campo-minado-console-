package br.reinan.cm;

import br.reinan.cm.modelo.Tabuleiro;
import br.reinan.cm.visao.TabuleiroConsole;

public class Aplicacao {
	public static void main(String[] args) {
		
		Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);
		
		new TabuleiroConsole(tabuleiro);
		
	}	

}
