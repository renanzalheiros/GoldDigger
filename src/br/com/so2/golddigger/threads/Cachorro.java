package br.com.so2.golddigger.threads;

import br.com.so2.golddigger.Pote;

public class Cachorro implements Runnable {

    private final String cacador;

    public Cachorro(String cacador) {
        this.cacador = cacador;
    }

    @Override
    public void run() {

    }

    public String getCacador() {
        return cacador;
    }

    public int verQuantasMoedasTemNoPote(Pote pote) {
        return pote.getMoedas();
    }

    public void pegarMoeda(Pote pote,int quantidade) {

    }
}
