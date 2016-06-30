package entity;

import java.util.ArrayList;
import java.util.Collection;
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
	public static int oldIDAPatamar = 0;
	public static int newIDAPatamar = 0;

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
		this.raiz.level = 0;
	}

	public void buscaBacktracking() {
		List<Node> list = new ArrayList<Node>();
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		hashMap.put(this.raiz.key, 1);
		Node n = this.raiz.buscaBacktracking(hashMap, list);
		printSolution(n, hashMap);
	}

	public void buscaLargura() {
		List<Node> list = new ArrayList<Node>();
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		Node n = this.raiz.buscaLargura(hashMap, list);
		hashMap.put(this.raiz.key, 1);
		printSolution(n, hashMap);
	}

	public void buscaProfundidade() {
		List<Node> list = new ArrayList<Node>();
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		hashMap.put(this.raiz.key, 1);

		Node n = this.raiz.buscaProfundidade(hashMap, list);
		printSolution(n, hashMap);
	}

	public void buscaOrdenada() {
		List<Node> list = new ArrayList<Node>();
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		this.raiz.val = 0;
		hashMap.put(this.raiz.key, 1);
		Node n = this.raiz.buscaOrdenada(hashMap, list);
		printSolution(n, hashMap);
	}

	public void buscaGulosa() {
		List<Node> list = new ArrayList<Node>();
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		this.raiz.val = raiz.getValorHeuristica();
		hashMap.put(this.raiz.key, 1);

		Node n = this.raiz.buscaGulosa(hashMap, list);
		printSolution(n, hashMap);
	}

	public void buscaA() {
		List<Node> list = new ArrayList<Node>();
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		this.raiz.val = raiz.getValorHeuristica();
		hashMap.put(this.raiz.key, 1);

		Node n = this.raiz.buscaA(hashMap, list);

		printSolution(n, hashMap);
	}

	public void buscaIDA() {
		List<Node> list = new ArrayList<Node>();
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();

		this.raiz.val = raiz.getValorHeuristica();
		this.oldIDAPatamar = this.raiz.val;
		this.newIDAPatamar = Integer.MAX_VALUE;
		Node n = null;
		while (true) {
			System.out.println("oldIDAPatamar " + oldIDAPatamar);
			hashMap = new HashMap<String, Integer>();
			hashMap.put(this.raiz.key, 1);
			n = this.raiz.buscaIDA(hashMap, list);
			if (n != null) {
				break;
			}
			if (oldIDAPatamar == newIDAPatamar || newIDAPatamar == Integer.MAX_VALUE) {
				break;
			} else {
				oldIDAPatamar = newIDAPatamar;
			}
			this.newIDAPatamar = Integer.MAX_VALUE;
		}

		printSolution(n, hashMap);

	}

	public void printSolution(Node n, HashMap<String, Integer> hashMap) {
		if (n == null) {
			System.out.println("Nao achou Solucao");
		}
		List<String> caminho = new ArrayList<String>();
		System.out.println("Profundidade: " + n.level);
		System.out.println("Solucao: " + n.key);
		while (n != null) {
			caminho.add(n.key);
			n = n.pai;
		}

		System.out.println("Solucao:");
		for (int i = caminho.size() - 1; i >= 0; i--) {
			System.out.print(caminho.get(i));
			if (i != 0) {
				System.out.print(" -> ");
			}
		}
		System.out.println("");
		int c = 0;
		Collection<Integer> values = hashMap.values();
		for (int value : values) {
			if (value == 1) {
				c++;
			}
		}
		System.out.println("expandidos: " + hashMap.size());
		System.out.println("visitados: " + c);
		System.out.println("do fator de ramificação: " + (float)(hashMap.size()-1)/(c-1));
	}

}
