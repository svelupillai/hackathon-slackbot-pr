package mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DatabaseController {

	private MongoClient mongoClient;

	public DatabaseController() {
		mongoClient = new MongoClient();

		MongoDatabase database = mongoClient.getDatabase("prPal");
		MongoCollection collection = database.getCollection("Users");
	}

}
