package br.com.so2.golddigger;

import br.com.so2.golddigger.threads.Cachorro;
import br.com.so2.golddigger.threads.CachorroVermelho;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Bosque {

    public static final String cacadorVerde = "Cacador verde";
    public static final String cacadorAzul = "Cacador azul";
    public static final String cacadorAmarelo = "Cacador amarelo";

    public static Pote[] potes = new Pote[20];
    private List<Cacador> cacadores = new Vector<>();
    private List<Thread> cachorrosEmJogo = new ArrayList<>();
    private CachorroVermelho cachorroVemelho = new CachorroVermelho();

    public Bosque() {
        for(int i = 0; i < 20; i++) {
            potes[i] = new Pote(i);
        }

        for (int i = 1; i <=3; i++) {
            switch (i) {
                case 1:
                    cacadores.add(new Cacador(cacadorVerde));
                    break;
                case 2:
                    cacadores.add(new Cacador(cacadorAzul));
                    break;
                case 3:
                    cacadores.add(new Cacador(cacadorAmarelo));
                    break;
            }
        }

        try {
            populaCampo();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startJogo();
    }

    private void populaCampo() throws InterruptedException {
        addCachorroNoJogo(cacadores.get(0).getCachorro1());
        addCachorroNoJogo(cacadores.get(1).getCachorro1());
        addCachorroNoJogo(cacadores.get(2).getCachorro1());
        addCachorroNoJogo(cacadores.get(0).getCachorro2());
        addCachorroNoJogo(cacadores.get(1).getCachorro2());
        addCachorroNoJogo(cacadores.get(2).getCachorro2());
    }

    private void addCachorroNoJogo(Cachorro cachorro) throws InterruptedException {
        while (cachorrosEmJogo.size() < 3) {
            cachorrosEmJogo.add(new Thread(cachorro));
        }
//            cachorrosEmJogo.wait();
    }

    private void startJogo() {
        cachorrosEmJogo.get(0).start();
        cachorrosEmJogo.get(1).start();
        cachorrosEmJogo.get(2).start();
    }
}
