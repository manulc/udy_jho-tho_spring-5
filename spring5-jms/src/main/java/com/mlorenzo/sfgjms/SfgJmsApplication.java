package com.mlorenzo.sfgjms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SfgJmsApplication {

	public static void main(String[] args) throws Exception {
		// Se comenta porque en vez de usar un servidor embebido, ahora vamos a usar un servidor Apache ActiveMQ Artemis ejecutándose en Docker
		// Embedded Apache ActiveMQ Artemis Server(JMS) Configuration
		/* ActiveMQServer server = ActiveMQServers.newActiveMQServer(new ConfigurationImpl()
				.setPersistenceEnabled(false)
				.setJournalDirectory("target/data/journal")
				.setSecurityEnabled(false)
				.addAcceptorConfiguration("invm","vm://0"));*/
		
		// Run embedded Apache ActiveMQ Artemis Server(JMS)
		// server.start();
		SpringApplication.run(SfgJmsApplication.class, args);
	}

}
