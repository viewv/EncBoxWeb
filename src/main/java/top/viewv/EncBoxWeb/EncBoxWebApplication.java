package top.viewv.EncBoxWeb;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableAsync
@RestController
public class EncBoxWebApplication {

	@RequestMapping("/")
	public String home() {
		String version = BouncyCastleProvider.PROVIDER_NAME;
		return "Hello world!\nBouncy Castle Provider = " + version;
	}

	public static void main(String[] args) {
		SpringApplication.run(EncBoxWebApplication.class, args);
	}

}
