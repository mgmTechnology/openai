package technology.mgm.demo;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import technology.mgm.demo.beans.Person;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@SpringBootApplication
public class DemoApplication {
	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
	public static final String API_KEY = "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";
	public static final String URL = "https://api.openai.com/v1/completions";
	public static void main(String[] args) {
		//SpringApplication.run(DemoApplication.class, args);
		ApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
		for (String name : ctx.getBeanDefinitionNames()) {
			logger.debug(name);
		}

		Person person = ctx.getBean(Person.class);
		String personIdentification = person.identify();
		logger.info(personIdentification);
		Person secondPerson = Person.getSinglePerson();
		logger.info(secondPerson.identify());
		String prompt = "Welcher Wochentag war der 24. September 1966?";
		boolean quit = false;
		do {
			System.out.print("Welche Frage hast du? --> ");
			Scanner scanner = new Scanner(System.in);
			prompt = scanner.nextLine();
			String generatedText = null;
			if (!"keine".equalsIgnoreCase(prompt)) {
				try {
					generatedText = getAnswerFromOpenAIWebservice(prompt);
					logger.info(generatedText);
				} catch (IOException e) {
					logger.warn("Fehler: " + e.getMessage());
					logger.warn(String.valueOf(e.getStackTrace()));
				}
			} else {
				quit = true;
			}
		} while (!quit);
	}

	public static String getAnswerFromOpenAIWebservice(String prompt) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(URL).openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Authorization", "Bearer " + API_KEY);

		// documentation https://beta.openai.com/docs/api-reference/completions/create
		JsonObject json = new JsonObject();
		json.addProperty("prompt", prompt);
		// json.addProperty("model", "text-davinci-002");
		json.addProperty("model", "text-davinci-003");
		// alternative: json.addProperty("model", "text-curie-001-de");
		// json.addProperty("model", "text-davinci-002-de");
		//festlegen der sprache
		//json.addProperty("language", "de");
		// setting temperature controls the randomness of the generated text.
		json.addProperty("temperature", 0.7);
		// GPT-3 has token-limits! you will get error 400 if exceeding allowed limits
		// text-davinci-003: 4000
		// text-curie-001: 2048
		// text-babbage-001: 2048
		// text-ada-001: 2048
		json.addProperty("max_tokens", 4000);

		try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {
			out.writeBytes(json.toString());
			out.flush();
		}

		StringBuilder response = new StringBuilder();
		try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			String line;
			while ((line = in.readLine()) != null) {
				response.append(line);
			}
		}

		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);
		return jsonObject.getAsJsonArray("choices").get(0).getAsJsonObject().get("text").getAsString();
	}

}
