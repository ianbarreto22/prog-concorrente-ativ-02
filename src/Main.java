import java.util.ArrayList;
import java.util.Random;

/**
 * Solução concorrente para o problema do Banheiro Unissex
 * utilizando lock e variáveis de condição.
 * 
 * @author Ian Jerônimo Nobre Barreto
 * @author Victor Gabriel Sousa de Castro
 * */
public class Main {
	/** Capacidade do banheiro */
	private static final int CAPACIDADE = 10;
	
	/** Número de pessoas (threads) que irão utilizar o banheiro */
	private static final int NUM_THREADS = 100;
	
	
	/**
	 * Método Main
	 * @param args Parâmetros de linha de comando
	 * */
	public static void main(String args[]) {
		Banheiro banheiro = new Banheiro(CAPACIDADE);
		ArrayList<Pessoa> pessoas = new ArrayList<Pessoa>();
		
		for(int i=0; i < NUM_THREADS; i++) {
			if(new Random().nextInt(2) == 0) {
				pessoas.add(new Pessoa("Pessoa " + i, Sexo.MASCULINO, banheiro));
			} else {
				pessoas.add(new Pessoa("Pessoa " + i, Sexo.FEMININO, banheiro));
			}
		}
		
		for(Pessoa p : pessoas) {
			p.start();
		}
		
		try {
			for(Pessoa p : pessoas) {
				p.join();
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}
