package br.com.so2.golddigger.threads;

import br.com.so2.golddigger.Bosque;
import br.com.so2.golddigger.Pote;
import br.com.so2.golddigger.Principal;

public class CachorroVermelho extends Thread {

    public String cachorroVermelho;
    public boolean aindaJogando;

    public CachorroVermelho() {
        this.cachorroVermelho = "Cachorro Vermelho";
        this.aindaJogando = true;
    }

    public void deixarMoeda(int posicao) {
        System.out.println(cachorroVermelho + " deixando 1 moeda no pote " + posicao);
        Bosque.potes[posicao].deixarMoeda();
    }

    private void vaiPara(Pote pote) {
        System.out.println(cachorroVermelho + " chegou no pote " + pote.getPosicao());
            synchronized (Bosque.potes[pote.getPosicao()]) {
                if (pote.getMoedas() == 0) {
                    deixarMoeda(pote.getPosicao());
                }
                Bosque.potes[pote.getPosicao()].notifyAll();
            }

            if (pote.getPosicao() == 19) {
                try {
                    Thread.sleep(Principal.UNIDADE_DE_TEMPO * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;
            } else {
                vaiPara(Bosque.potes[pote.getPosicao()+1]);

//        int posicaoPote = 0;
//        while (posicaoPote < 20) {
//            synchronized (potes[posicaoPote]) {
//                System.out.println("Cachorro vermelho chegou no pote " + posicaoPote);
//                if (potes[posicaoPote].getMoedas() == 0) {
//                    potes[posicaoPote].deixarMoeda();
//                }
//                Bosque.potes[posicaoPote].notifyAll();
//            }
//
//            if (posicaoPote == 19) {
//                return;
//            } else {
//                posicaoPote++;
//            }
//        }
        }
    }

    @Override
    public void run() {
        while (aindaJogando) {
            vaiPara(Bosque.potes[0]);
        }
    }
}
