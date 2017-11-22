package br.com.so2.golddigger;

import br.com.so2.golddigger.threads.Cachorro;

public class Cacador {

    private final String cor;
    private Cachorro cachorro1;
    private Cachorro cachorro2;

    public Cacador(String cor) {
        this.cor = cor;
        this.cachorro1 = new Cachorro(cor);
        this.cachorro2 = new Cachorro(cor);
    }

    public String getCor() {
        return cor;
    }

    public Cachorro getCachorro1() {
        return cachorro1;
    }

    public Cachorro getCachorro2() {
        return cachorro2;
    }
}
