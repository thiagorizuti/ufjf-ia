import entity.Tree;

/**
 * Created by jm on 5/28/16.
 */
public class main {

	public  static void main(String[] agr){
		String estado_inicial = "10-0101";
		String estado_final = "000-111";
		Tree tree = new Tree(estado_final,estado_inicial);
		long tempoInicial = System.currentTimeMillis();
                tree.buscaLargura();
                long tempoFinal = System.currentTimeMillis();
                System.out.println(tempoFinal - tempoInicial + "ms");
	}


}
