import entity.Tree;

/**
 * Created by jm on 5/28/16.
 */
public class main {

	public  static void main(String[] agr){
		String estado_inicial = "001-011";
		String estado_final = "000-111";
		Tree tree = new Tree(estado_final,estado_inicial);
		tree.buscaBacktracking();
	}


}
