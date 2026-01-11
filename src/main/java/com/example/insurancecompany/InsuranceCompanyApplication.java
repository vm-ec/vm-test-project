package com.example.insurancecompany;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InsuranceCompanyApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(InsuranceCompanyApplication.class, args);
		} catch (Throwable t) {
			// Print full stacktrace to help diagnose runtime initialization errors
			t.printStackTrace();
			// Re-throw to maintain non-zero exit code and visibility in tooling
			if (t instanceof RuntimeException exception) {
				throw exception;
			}
			throw new RuntimeException(t);
		}
	}

}
