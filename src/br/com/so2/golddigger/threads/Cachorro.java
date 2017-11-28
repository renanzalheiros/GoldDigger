package br.com.so2.golddigger.threads;

import br.com.so2.golddigger.Bosque;
import br.com.so2.golddigger.Pote;

import java.util.List;
import java.util.Random;

import static br.com.so2.golddigger.Principal.UNIDADE_DE_TEMPO;

public class Cachorro extends Thread {

    private final String cacador;
    private final Bosque bosque;
    private int moedasCachorro;
    private boolean foraDoBosque;

    public Cachorro(Bosque bosque, String cacador) {
        this.bosque = bosque;
        this.cacador = cacador;
        this.moedasCachorro = 0;
        this.foraDoBosque = true;
    }

    @Override
    public void run() {
        if (foraDoBosque) {
            foraDoBosque = false;
            vaiPara(Bosque.potes[0]);
        }
    }

    public String getCacador() {
        return cacador;
    }

    public int getMoedasCachorro() {
        return moedasCachorro;
    }

    private void pegarMoeda(int posicaoPote, int quantidade) {
        Bosque.potes[posicaoPote].pegaMoedas(quantidade);
    }

    private void vaiPara(Pote pote) {
        System.out.println(cacador + " chegou no pote " + pote.getPosicao());

        synchronized (Bosque.potes[pote.getPosicao()]) {
            int qtdMoedas = pote.getMoedas();
            if (qtdMoedas <= 0) {
                try {
                    System.out.println(cacador + " chegou no pote " + pote.getPosicao() + " e dormiu");
                    Bosque.potes[pote.getPosicao()].wait(UNIDADE_DE_TEMPO * 60);
//                    Thread.sleep(UNIDADE_DE_TEMPO * 60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (qtdMoedas > 0 && qtdMoedas < 3) {
                System.out.println(cacador + " pegou " + qtdMoedas + " moedas do pote " + pote.getPosicao());
                pegarMoeda(pote.getPosicao(), qtdMoedas);
                moedasCachorro += qtdMoedas;
            } else if (qtdMoedas >= 3) {
                System.out.println(cacador + " pegou 3 moedas do pote " + pote.getPosicao());
                pegarMoeda(pote.getPosicao(), 3);
                moedasCachorro += 3;
            } else if (qtdMoedas == 0) {
                try {
                    System.out.println("Perdi a corrida e dormi de novo");
                    Bosque.potes[pote.getPosicao()].wait(UNIDADE_DE_TEMPO * 3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(moedasCachorro >= 20) {
                System.out.println("Peguei 20 moedas - " + this.cacador);
                this.bosque.encerrarJogo(cacador, moedasCachorro);
                return;
            }
        }

        List<Integer> potesDestinos = pote.getPotesDestinos(pote.getPosicao());
        int i = new Random().nextInt(potesDestinos.size());
        System.out.println(cacador + " indo para o pote " + i + " com " + moedasCachorro + " moedas acumuladas");
        vaiPara(Bosque.potes[i]);
    }

    public int finalizar() {
        try {
            stop();
            return moedasCachorro;
        } catch (Throwable throwable) {
            return moedasCachorro;
        }
    }
}
