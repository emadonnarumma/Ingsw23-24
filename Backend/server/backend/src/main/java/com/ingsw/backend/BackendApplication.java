package com.ingsw.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class BackendApplication {

    public static void main(String[] args) {
        System.out.println("entro a gamba tesa, leccandoti il ciolone, urla la tua resa cazzo voglio il tuo cazzone. sto prendendo cazzi come non fosse domani, cazzi gandi, cazzi mosci, fra cazzi, isolani. Pisello molto grande, braccio di Vegeta, salto su una ciola è dura no, non è di seta, cazzo, quello grande e un pò rugoso., ciola, quella grande piena e viola.");
        SpringApplication.run(BackendApplication.class, args);
    }

}