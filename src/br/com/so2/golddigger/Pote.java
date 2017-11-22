package br.com.so2.golddigger;

import br.com.so2.golddigger.threads.Cachorro;

import java.util.*;

public class Pote {

    private final int posicao;
    private int moedas;
    private Map<Integer, List<Integer>> potesDestinos;
    private List<Cachorro> cachorrosDormindo = new ArrayList<Cachorro>();

    public Pote(int posicao) {
        this.posicao = posicao;
        this.moedas = 4;
        potesDestinos = new HashMap<Integer, List<Integer>>();
        populaDestinos();
    }

    public int getPosicao() {
        return posicao;
    }

    public int getMoedas() {
        return moedas;
    }

    public synchronized void pegaMoedas(int quantidade) {
        this.moedas -= quantidade;
    }

    private void populaDestinos() {
        potesDestinos.put(1, Arrays.asList(2, 15));
        potesDestinos.put(2, Arrays.asList(1, 3, 4, 5));
        potesDestinos.put(3, Arrays.asList(2, 9));
        potesDestinos.put(4, Arrays.asList(2, 9, 10));
        potesDestinos.put(5, Arrays.asList(2, 6));
        potesDestinos.put(6, Arrays.asList(5, 7, 8));
        potesDestinos.put(7, Arrays.asList(6));
        potesDestinos.put(8, Arrays.asList(6));
        potesDestinos.put(9, Arrays.asList(3, 4, 15, 18));
        potesDestinos.put(10, Arrays.asList(4, 12));
        potesDestinos.put(11, Arrays.asList(12, 14, 17));
        potesDestinos.put(12, Arrays.asList(10, 11, 13));
        potesDestinos.put(13, Arrays.asList(12));
        potesDestinos.put(14, Arrays.asList(11, 16));
        potesDestinos.put(15, Arrays.asList(1, 9));
        potesDestinos.put(16, Arrays.asList(14, 17, 18, 20));
        potesDestinos.put(17, Arrays.asList(11, 16));
        potesDestinos.put(18, Arrays.asList(9, 16, 19));
        potesDestinos.put(19, Arrays.asList(18, 20));
        potesDestinos.put(20, Arrays.asList(16, 19));
    }

    public List<Integer> getPotesDestinos(Integer posicaoPote) {
        return potesDestinos.get(posicaoPote);
    }
}

