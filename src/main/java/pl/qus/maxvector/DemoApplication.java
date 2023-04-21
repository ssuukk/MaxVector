package pl.qus.maxvector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//"""https://docs.spring.io/spring-graphql/docs/current/reference/html/"""
//		"""https://spring.io/guides/gs/graphql-server/"""

// korutynki: https://docs.spring.io/spring-framework/docs/current/reference/html/languages.html#coroutines

// tu jest gql:
// http://localhost:8080/graphiql?path=/graphql

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
