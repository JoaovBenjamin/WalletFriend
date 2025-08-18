package com.joao.WalletFriend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Wallet Friend",
				summary = "API do app Wallet Friend",
				description = "Organizador de financeiro",
				version = "1.0.0",
				contact = @Contact(name = "João Benjamin", email = "joaovvbenjamin@hotmail.com")
		)
)
public class WalletFriendApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletFriendApplication.class, args);
	}

}
