package br.com.so2.golddigger;

import br.com.so2.golddigger.threads.Cachorro;
import br.com.so2.golddigger.threads.CachorroVermelho;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Bosque {

    public static Pote[] potes = new Pote[20];

    private List<Thread> cachorrosEmJogo = new ArrayList<>();

    private Cachorro cachorroVerde = new Cachorro(this, "verde");
    private Cachorro cachorroAmarelo = new Cachorro(this, "amarelo");
    private Cachorro cachorroAzul = new Cachorro(this, "azul");

    private CachorroVermelho cachorroVermelho = new CachorroVermelho();

    public Bosque() {
        for(int i = 0; i < 20; i++) {
            potes[i] = new Pote(i);
        }

        try {
            populaCampo();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startJogo();
    }

    private void populaCampo() throws InterruptedException {
        addCachorroNoJogo(cachorroAmarelo);
        addCachorroNoJogo(cachorroAzul);
        addCachorroNoJogo(cachorroVerde);
    }

    private void addCachorroNoJogo(Thread cachorro) throws InterruptedException {
        while (cachorrosEmJogo.size() < 3) {
            cachorrosEmJogo.add(cachorro);
        }
    }

    private void startJogo() {
        cachorroAmarelo.start();
        cachorroAzul.start();
        cachorroVerde.start();
        cachorroVermelho.start();
    }

    private void cachorroVermelhoRole() {
        int posicaoPote = 0;
        while (posicaoPote < 20) {
            synchronized (potes[posicaoPote]) {
                System.out.println("Cachorro vermelho chegou no pote " + posicaoPote);
                if (potes[posicaoPote].getMoedas() == 0) {
                    potes[posicaoPote].deixarMoeda();
                }
                Bosque.potes[posicaoPote].notifyAll();
            }

            if (posicaoPote == 19) {
                return;
            } else {
                posicaoPote++;
            }
        }
    }

    public void encerrarJogo(String corCachorro, int moedas) {
        int moedasVerde = cachorroVerde.finalizar();
        int moedasAzul = cachorroAzul.finalizar();
        int moedasAmarelo = cachorroAmarelo.finalizar();
        cachorroVermelho.aindaJogando = false;

        HashMap<Cachorro, Integer> pontuacoes = new HashMap<>();
        pontuacoes.put(cachorroVerde, moedasVerde);
        pontuacoes.put(cachorroAzul, moedasAzul);
        pontuacoes.put(cachorroAmarelo, moedasAmarelo);

        Map<Integer, Cachorro> stringIntegerMap = sortByValue(pontuacoes);

        System.out.println("\n\n Cachorro " + corCachorro + " venceu com " + moedas + "\n\n");

        System.out.println("Placar final:\n\n1o lugar: " + corCachorro + " com " + moedas + " moedas" +
                "\n\n2o lugar: " + stringIntegerMap.get(2).getCacador() + " com " + stringIntegerMap.get(2).getMoedasCachorro() + " moedas " +
                "\n\n3o lugar: " + stringIntegerMap.get(1).getCacador() + " com " + stringIntegerMap.get(1).getMoedasCachorro() + " moedas \n\n\n");

//        System.out.println("\n\n\n" + runnables);
    }

    private Map<Integer, Cachorro> sortByValue(Map<Cachorro, Integer> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<Cachorro, Integer>> list =
                new LinkedList<Map.Entry<Cachorro, Integer>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order


        Collections.sort(list, new Comparator<Map.Entry<Cachorro, Integer>>() {
            public int compare(Map.Entry<Cachorro, Integer> o1,
                               Map.Entry<Cachorro, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<Integer, Cachorro> sortedMap = new LinkedHashMap<Integer, Cachorro>();
        int i = 1;
        for (Map.Entry<Cachorro, Integer> entry : list) {
            sortedMap.put(i, entry.getKey());
            i++;
        }

        /*
        //classic iterator example
        for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext(); ) {
            Map.Entry<String, Integer> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }*/


        return sortedMap;
    }
}
