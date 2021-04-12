package top.viewv.EncBoxWeb;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class EncBoxWebApplication {

	@RequestMapping("/")
	public String home() {
		String version = BouncyCastleProvider.PROVIDER_NAME;
		return "Hello world! Bouncy Castle Provider = " + version;
	}

	public static void main(String[] args) {
		SpringApplication.run(EncBoxWebApplication.class, args);
	}

}
