package br.com.so2.golddigger.threads;

import br.com.so2.golddigger.Bosque;
import br.com.so2.golddigger.Pote;

import java.util.List;
import java.util.Random;

public class Cachorro implements Runnable {

    private final String cacador;
    private int moedasCachorro;
    private boolean foraDoBosque;

    public Cachorro(String cacador) {
        this.cacador = cacador;
        this.moedasCachorro = 0;
        this.foraDoBosque = true;
    }

    @Override
    public void run() {
        if (foraDoBosque) {
            foraDoBosque = false;
            vaiPara(Bosque.potes.get(0));
        }
    }

    public String getCacador() {
        return cacador;
    }

    public int getMoedasCachorro() {
        return moedasCachorro;
    }

    public void setMoedasCachorro(int moedas) {
        this.moedasCachorro = moedas;
    }

    public int verQuantasMoedasTemNoPote(Pote pote) {
        return pote.getMoedas();
    }

    private void pegarMoeda(Pote pote,int quantidade) {
        Bosque.potes.get(pote.getPosicao()).pegaMoedas(quantidade);
    }

    public void vaiPara(Pote pote) {

        int moedas = -1;
        synchronized (pote) {
            moedas = verQuantasMoedasTemNoPote(pote);
        }
        while (moedas == 0) {
            try {
                dormeAteQueODogVermelhoVenha();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        synchronized (pote) {
            retiraMoedasPote(pote, moedas);
            List<Integer> potesDestinos = pote.getPotesDestinos(pote.getPosicao());
            Random random = new Random(potesDestinos.size());
            vaiPara(Bosque.potes.get(potesDestinos.get(random.nextInt())));
        }
    }

    private void dormeAteQueODogVermelhoVenha() throws InterruptedException {
        this.wait();
    }

    private void retiraMoedasPote(Pote pote, int moedas) {
        if(moedas > 0 && moedas > 3) {
            pegarMoeda(pote, 3);
        } else if (moedas > 0 && moedas < 3){
            pegarMoeda(pote, moedas);
        }
    }
}
