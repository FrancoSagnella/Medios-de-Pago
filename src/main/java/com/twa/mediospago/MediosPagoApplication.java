package com.twa.mediospago;

import com.mercadopago.MercadoPagoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MediosPagoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediosPagoApplication.class, args);
	}

	@Autowired
	void loadCert(@Value("${mercadopago.token}") String tokenMP) {
		MercadoPagoConfig.setAccessToken(tokenMP);
	}
}
