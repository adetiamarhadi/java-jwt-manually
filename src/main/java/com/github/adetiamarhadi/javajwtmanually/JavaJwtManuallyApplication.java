package com.github.adetiamarhadi.javajwtmanually;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class JavaJwtManuallyApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaJwtManuallyApplication.class, args);
	}

	@PostConstruct
	public void run() {

		JwtJjwtGenerator jjwt = new JwtJjwtGenerator();
		JwtManualGenerator jwtManual = new JwtManualGenerator();

		try {
			// 1. create jjwt - verify jjwt
			System.out.println("==================================");
			System.out.println("1. create jjwt - verify jjwt");
			String jwt = jjwt.jwt();
			jjwt.verifyJwt(jwt);
			// 2. create manual - verify manual
			System.out.println("==================================");
			System.out.println("2. create manual - verify manual");
			String jwt1 = jwtManual.jwt();
			jwtManual.verifyJwt(jwt1);
			// 3. create jjwt - verify manual
			System.out.println("==================================");
			System.out.println("3. create jjwt - verify manual");
			String jwt2 = jjwt.jwt();
			jwtManual.verifyJwt(jwt2);
			// 4. create manual - verify jjwt
			System.out.println("==================================");
			System.out.println("4. create manual - verify jjwt");
			String jwt3 = jwtManual.jwt();
			jjwt.verifyJwt(jwt3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
