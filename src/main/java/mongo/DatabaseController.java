package mongo;

import java.util.Objects;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DatabaseController {

	private static MongoClient mongoClient;

	public DatabaseController() {
		mongoClient = getClientInstance();

		MongoDatabase database = mongoClient.getDatabase("prPal");
		MongoCollection collection = database.getCollection("Users");
	}

	public MongoClient getClientInstance() {
		return Objects.isNull(mongoClient) ? new MongoClient() : mongoClient;
	}


}
