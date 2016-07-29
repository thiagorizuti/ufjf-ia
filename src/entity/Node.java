package entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jm on 5/28/16.
 */
public class Node implements Comparable<Node> {
	public String key;
	public List<Integer> estado;
	public Node pai;
	public List<Node> filhos;
	public int possitionNull;
	public int level;
	public int val = 0;

	public Node(Node pai, List<Integer> estado) {
		this.pai = pai;
		this.estado = estado;
		this.key = createKey();
		possitionNull = estado.indexOf(-1);
		this.filhos = new ArrayList<Node>();
		this.level = pai != null ? pai.level + 1 : 0;
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
				return true;
			}

		} else if (regra == 1 && possitionNull > 0) {
			list_aux = createEstado(possitionNull, possitionNull - 1);
			keyaux = createKey(list_aux);
			if (!hashMap.containsKey(keyaux)) {
				this.filhos.add(new Node(this, createEstado(possitionNull, possitionNull - 1)));
				return true;

			}
		} else if (regra == 2 && possitionNull < estado.size() - 1) {
			list_aux = createEstado(possitionNull, possitionNull + 1);
			keyaux = createKey(list_aux);
			if (!hashMap.containsKey(keyaux)) {
				this.filhos.add(new Node(this, createEstado(possitionNull, possitionNull + 1)));
				return true;
			}
		} else if (regra == 3 && possitionNull < estado.size() - 2) {
			list_aux = createEstado(possitionNull, possitionNull + 2);
			keyaux = createKey(list_aux);
			if (!hashMap.containsKey(keyaux)) {
				this.filhos.add(new Node(this, createEstado(possitionNull, possitionNull + 2)));
				return true;
			}
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

	public Node buscaLargura(HashMap<String, Integer> hashMap, List<Node> list) {
		if (this.key.equals(Tree.objetivo)) {
			return this;
		}
		criarAll(hashMap);
		for (Node n : filhos) {
			list.add(n);
			hashMap.put(n.key, 0);
		}
		if (list.size() == 0) {
			return null;
		}
		Node n = list.remove(0);
		hashMap.put(n.key,1);
		return n.buscaLargura(hashMap, list);
	}

	public Node buscaBacktracking(HashMap<String, Integer> hashMap, List<Node> list) {
		printNode();
		if (this.key.equals(Tree.objetivo)) {
			return this;
		}
		int c=-1;
		Node n=null;
		for (int i = 0; i < 4; i++) {
			if(criarFilhos(i, hashMap)){
				c++;
				hashMap.put(filhos.get(c).key, 1);
				n = filhos.get(c).buscaBacktracking(hashMap, list);
				if( n!=null ){
					return n;
				}
			}
		}
		return null;
	}
	public Node buscaProfundidade(HashMap<String, Integer> hashMap, List<Node> list) {
		printNode();
		if (this.key.equals(Tree.objetivo)) {
			return this;
		}
		criarAll(hashMap);
		for (Node n : filhos) {
			if (!hashMap.containsKey(n.key)) {
				list.add(n);
				hashMap.put(n.key, 0);
			}
		}
		if (list.size() == 0) {
			return null;
		}
		Node n = list.remove(list.size() - 1);
		hashMap.put(n.key,1);
		return n.buscaProfundidade(hashMap, list);
	}

	public Node buscaOrdenada(HashMap<String, Integer> hashMap, List<Node> list) {
		printNode();
		if (this.key.equals(Tree.objetivo)) {
			return this;
		}
		criarAll(hashMap);
		for (Node n : filhos) {
			if (!hashMap.containsKey(n.key)) {
				n.val = n.pai.val + 1;
				list.add(n);
				hashMap.put(n.key, 0);
			}
		}
		if (list.size() == 0) {
			return null;
		}
		Collections.sort(list);
		Node n = list.remove(0);
		hashMap.put(n.key,1);
		return n.buscaOrdenada(hashMap, list);
	}

	public Node buscaA(HashMap<String, Integer> hashMap, List<Node> list) {
		printNode();
		if (this.key.equals(Tree.objetivo)) {
			return this;
		}
		criarAll(hashMap);
		for (Node n : filhos) {
			if (!hashMap.containsKey(n.key)) {
				n.val = n.level + n.getValorHeuristica();
				list.add(n);
				hashMap.put(n.key, 0);
			}
		}
		if (list.size() == 0) {
			return null;
		}
		Collections.sort(list);
		Node n = list.remove(0);
		hashMap.put(n.key,1);
		return n.buscaA(hashMap, list);
	}

	public Node buscaIDA(HashMap<String, Integer> hashMap, List<Node> list) {
		printNode();
		if (this.key.equals(Tree.objetivo)) {
			return this;
		}
		criarAll(hashMap);
		for (Node n : filhos) {
			n.val = n.level + n.getValorHeuristica();
			if (n.val <= Tree.oldIDAPatamar ) {
				list.add(n);
				hashMap.put(n.key, 0);
			}else if(n.val<Tree.newIDAPatamar){
				Tree.newIDAPatamar=n.val;
			}
		}
		if (list.size() == 0) {
			return null;
		}
		Collections.sort(list);
		Node n = list.remove(0);
		hashMap.put(n.key,1);
		return n.buscaIDA(hashMap, list);
	}

	public Node buscaGulosa(HashMap<String, Integer> hashMap, List<Node> list) {
		printNode();
		if (this.key.equals(Tree.objetivo)) {
			return this;
		}
		criarAll(hashMap);
		for (Node n : filhos) {
			if (!hashMap.containsKey(n.key)) {
				n.val = n.getValorHeuristica();
				list.add(n);
				hashMap.put(n.key, 0);
			}
		}
		if (list.size() == 0) {
			System.out.println("NAO ACHOU");
			return null;
		}
		Collections.sort(list);
		Node n = list.remove(0);
		hashMap.put(n.key,1);
		return n.buscaGulosa(hashMap, list);
	}

	public int getValorHeuristica() {
		int v = 0;
		char[] treearray = Tree.objetivo.toCharArray();
		char[] nodearray = this.key.toCharArray();
		for (int i = 0; i < nodearray.length; i++) {
                    if(nodearray[i]=='1' && i<= nodearray.length/2){
                        v+=nodearray.length/2-i+1;
                    }
                    if(nodearray[i]=='0' && i>= nodearray.length/2){
                        v+=i-nodearray.length/2+1;
                    }
                    
		}
		return v;
	}

	@Override
	public int compareTo(Node o) {
		return Integer.compare(this.val, o.val);
	}

	public void printNode(){
		String s = "";
		for( int i =0 ;i< level ;i++){
			s+= " | ";
		}
		s+=key;
		System.out.println(s);
	}
}
