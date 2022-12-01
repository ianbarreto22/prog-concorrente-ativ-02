import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * Banheiro que será compartilhado pelas threads.
 * 
 * @author Ian Jerônimo Nobre Barreto
 * @author Victor Gabriel Sousa de Castro
 * */
public class Banheiro {
	/** Capacidade do banheiro*/
	private int capacidade;
	
	/** Objeto lock para controlar a exclusão mútua */
	private Lock lock;
	
	/** Variável de condição para sinalizar quando o banheiro está cheio */
	private Condition estaCheio;
	
	/** Lista de pessoas que estão utilizando o banheiro */
	private ArrayList<Pessoa> pessoas;
	
	/** Sexo das pessoas que estão utilizando o banheiro no momento */
	private Sexo sexoAtual = null;
	
	/**
	 * Construtor parametrizado
	 * @param capacidade Capacidade do banheiro
	 * */
	public Banheiro(int capacidade) {
		this.capacidade = capacidade;
		lock = new ReentrantLock();
		estaCheio = lock.newCondition();
		pessoas = new ArrayList<Pessoa>();
	}
	
	/**
	 * 
	 * Método para uma pessoa entrar no banheiro. <br/>
	 * Quando o banheiro atingir a capacidade máxima, a thread será
	 * suspensa pela variável de condição.
	 * 
	 * @param p Pessoa que entrará no banheiro
	 * */
	public void entrarNoBanheiro(Pessoa p) {
		this.lock.lock();
		try {
			while(pessoas.size() == this.capacidade) {
				System.out.println("O Banheiro está cheio\n");
				estaCheio.await();
			}
			
			if(pessoas.size() == 0) {
				this.sexoAtual = p.getSexo();
			}
			
			if(this.sexoAtual == p.getSexo()) {
				pessoas.add(p);
				p.setFoiNoBanheiro(true);
				System.out.println(p.getNome() + " entrou no banheiro.\n");
			} else {
				System.out.println(p.getNome() + " não pode entrar no banheiro agora, pois não é do sexo "
				 + this.sexoAtual + ".\n");
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * Método para uma pessoa sair do banheiro. <br/>
	 * As threads que estavam suspensas serão notificadas para voltarem a execução.
	 * */
	public void sairDoBanheiro(Pessoa p) {
		lock.lock();
		
		this.pessoas.remove(p);
		System.out.println(p.getNome() + " saiu do banheiro. ");
		System.out.println("O banheiro está com " + pessoas.size() + " do sexo " + this.sexoAtual + "\n");
		estaCheio.signal();
		
		lock.unlock();
	}
	
	
}
