package br.com.molar;

import br.com.molar.consumidor.Consumidor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MolarMensagensApplication implements CommandLineRunner {

    @Autowired
    Consumidor consumidor;

    public static void main(String[] args) {
        SpringApplication.run(MolarMensagensApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        consumidor.consumir("ImovelOfertado");
        consumidor.consumir("ImovelDesejado");
    }
}