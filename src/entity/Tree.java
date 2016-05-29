package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jm on 5/28/16.
 */
public class Tree {
	public Node raiz;
	public static String objetivo;
	public String inicial;
	public List<Integer> estado;

	public Tree(String objetivo, String inicial) {
		this.objetivo = objetivo;
		this.inicial = inicial;
		this.estado = new ArrayList<Integer>();
		int count = 0;
		for (String s : inicial.split("")) {
			if (s.equals("0")) {
				estado.add(0);
			} else if (s.equals("1")) {
				estado.add(1);
			} else {
				if (count == 1) {
					System.err.println(" formato invalido de entrada");
				}
				estado.add(-1);
				count++;
			}
		}
		this.raiz = new Node(null, estado);
	}

	public void buscaLargura() {
		List<Node> list = new ArrayList<Node>();
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		this.raiz.buscaLargura(hashMap, list);
	}
	public void buscaProfundidade() {
		List<Node> list = new ArrayList<Node>();
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		this.raiz.buscaProfundidade(hashMap, list);
	}
}
