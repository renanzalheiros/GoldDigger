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

    private List<Pote> potes = new Vector<Pote>();
    private List<Cacador> cacadores = new Vector<Cacador>();
    private List<Cachorro> cachorrosEmJogo = new ArrayList<Cachorro>();
    private CachorroVermelho cachorroVemelho = new CachorroVermelho();

    public Bosque() {

        for(int i = 1; i <= 20; i++) {
            potes.add(new Pote(i));
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
            startJogo();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startJogo() throws InterruptedException {
        addCachorroNoJogo(cacadores.get(0).getCachorro1());
        addCachorroNoJogo(cacadores.get(1).getCachorro1());
        addCachorroNoJogo(cacadores.get(2).getCachorro1());
    }

    public void addCachorroNoJogo(Cachorro cachorro) throws InterruptedException {
        if (cachorrosEmJogo.size() > 2) {
            this.wait();
        } else {
            cachorrosEmJogo.add(cachorro);
        }
    }
}
