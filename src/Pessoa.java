import java.util.Random;
import java.util.concurrent.TimeUnit;
/**
 * @author Ian Jerônimo Nobre Barreto
 * @author Victor Gabriel Sousa de Castro
 * */
public class Pessoa extends Thread {
	/** Nome da pessoa */
	private String nome;
	
	/** Sexo da pessoa */
	private Sexo sexo;
	
	/** Verdadeiro quando uma pessoa já utilizou o banheiro, falso caso contrário */
	private boolean foiNoBanheiro;
	
	/** Banheiro compartilhado entre as threads */
	private Banheiro banheiro;
	
	/**
	 * Construtor parametrizado
	 * @param nome Nome da pessoa
	 * @param sexo Sexo da pessoa
	 * @param banheiro Banheiro que a pessoa irá utilizar
	 * */
	public Pessoa(String nome, Sexo sexo, Banheiro banheiro) {
		this.nome = nome;
		this.sexo = sexo;
		this.banheiro = banheiro;
		this.foiNoBanheiro = false;
	}

	/**
	 * @return Nome da pessoa
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Altera o nome da pessoa
	 * 
	 * @param nome Nome da pessoa
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return Sexo da pessoa
	 */
	public Sexo getSexo() {
		return sexo;
	}

	/**
	 * Altera o sexo da pessoa
	 * @param sexo Sexo da pessoa
	 */
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	
	/**
	 * Determina se uma pessoa foi ou não no banheiro
	 * @param foiNoBanheiro Se uma pessoa já foi no banheiro
	 */
	public void setFoiNoBanheiro(boolean foiNoBanheiro) {
		this.foiNoBanheiro = foiNoBanheiro;
	}

	/**
	 * Faz com que uma pessoa utilize o banheiro
	 */
	@Override
	public void run() {
		while(!foiNoBanheiro) {
			banheiro.entrarNoBanheiro(this);
			try {
				TimeUnit.SECONDS.sleep((new Random()).nextInt(4) + 1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			};
			if(foiNoBanheiro) {
				banheiro.sairDoBanheiro(this);
			}
		}
	}
	
	
}
