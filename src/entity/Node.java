package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jm on 5/28/16.
 */
public class Node {
	public String key;
	public List<Integer> estado;
	public Node pai;
	public List<Node> filhos;
	public int possitionNull;
	public int level;

	public Node(Node pai, List<Integer> estado) {
		this.pai = pai;
		this.estado = estado;
		this.key = createKey();
		possitionNull = estado.indexOf(-1);
		this.filhos = new ArrayList<Node>();
	}

	/**
	 * Regras para os filhos
	 * 0: ab-cd -> -bacd
	 * 1: ab-cd -> a-bcd
	 * 2: ab-cd -> abc-d
	 * 3: ab-cd -> abdc-
	 */
	public boolean criarFilhos(int regra) {
		if (regra == 0 && possitionNull > 1) {
			this.filhos.add(new Node(this, createEstado(possitionNull, possitionNull - 2)));
			return true;
		} else if (regra == 1 && possitionNull > 0) {
			this.filhos.add(new Node(this, createEstado(possitionNull, possitionNull - 1)));
			return true;
		} else if (regra == 2 && possitionNull < estado.size() - 1) {
			this.filhos.add(new Node(this, createEstado(possitionNull, possitionNull + 1)));
			return true;
		} else if (regra == 3 && possitionNull < estado.size() - 2) {
			this.filhos.add(new Node(this, createEstado(possitionNull, possitionNull + 2)));
			return true;
		}
		return false;
	}

	public boolean criarFilhos(int regra, HashMap<String, Integer> hashMap) {
		List<Integer> list_aux;
		String keyaux;
		if (regra == 0 && possitionNull > 1) {
			list_aux = createEstado(possitionNull, possitionNull - 2);
//			System.out.println(list_aux);
			keyaux = createKey(list_aux);
			if (!hashMap.containsKey(keyaux)) {
				this.filhos.add(new Node(this, createEstado(possitionNull, possitionNull - 2)));
			}
			return true;
		} else if (regra == 1 && possitionNull > 0) {
			list_aux = createEstado(possitionNull, possitionNull - 1);
			keyaux = createKey(list_aux);
			if (!hashMap.containsKey(keyaux)) {
				this.filhos.add(new Node(this, createEstado(possitionNull, possitionNull - 1)));
			}
			return true;
		} else if (regra == 2 && possitionNull < estado.size() - 1) {
			list_aux = createEstado(possitionNull, possitionNull + 1);
			keyaux = createKey(list_aux);
			if (!hashMap.containsKey(keyaux)) {
				this.filhos.add(new Node(this, createEstado(possitionNull, possitionNull + 1)));
			}
			return true;
		} else if (regra == 3 && possitionNull < estado.size() - 2) {
			list_aux = createEstado(possitionNull, possitionNull + 2);
			keyaux = createKey(list_aux);
			if (!hashMap.containsKey(keyaux)) {
				this.filhos.add(new Node(this, createEstado(possitionNull, possitionNull + 2)));
			}
			return true;
		}
		return false;
	}

	public void criarAll() {
		for (int i = 0; i < 4; i++) {
			criarFilhos(i);
		}
	}

	public void criarAll(HashMap<String, Integer> hashMap) {
		for (int i = 0; i < 4; i++) {
			criarFilhos(i, hashMap);
		}
	}

	public String createKey() {
		String key = "";
		for (Integer i : estado) {
			if (i == -1) {
				key += "-";
			} else {
				key += i;
			}
		}
		return key;
	}

	public String createKey(List<Integer> list_aux) {
		String key = "";
		for (Integer i : list_aux) {
			if (i == -1) {
				key += "-";
			} else {
				key += i;
			}
		}
		return key;
	}

	public List<Integer> createEstado(int i, int j) {
		List<Integer> list = new ArrayList<Integer>();
		for (Integer e : estado) {
			list.add(e);
		}
		int aux = list.get(i);
		list.set(i, list.get(j));
		list.set(j, aux);
		return list;
	}

	public void buscaLargura(HashMap<String, Integer> hashMap, List<Node> list) {
		System.out.println(key);
		if (this.key.equals(Tree.objetivo)) {
			System.out.println("achou");
			return;
		}
		criarAll(hashMap);
		for (Node n : filhos) {
			list.add(n);
			hashMap.put(n.key, 0);
		}
		Node n = list.remove(0);
		n.buscaLargura(hashMap, list);
	}


	public void buscaProfundidade(HashMap<String, Integer> hashMap, List<Node> list) {
		System.out.println(key);
		if (this.key.equals(Tree.objetivo)) {
			System.out.println("achou");
			return;
		}
		criarAll(hashMap);
		for (Node n : filhos) {
			list.add(n);
			hashMap.put(n.key, 0);
		}
		Node n = list.remove(list.size() - 1);
		n.buscaProfundidade(hashMap, list);
	}
}
