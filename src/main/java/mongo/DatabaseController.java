package mongo;

import static com.mongodb.client.model.Filters.eq;

import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DatabaseController {

	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> userCollection;

	public DatabaseController() {
		mongoClient = new MongoClient();

		this.database = mongoClient.getDatabase("prPal");
		this.userCollection = database.getCollection("User");
	}

	public MongoDatabase getMongoDatabase() {
		return database;
	}

	public MongoCollection getUserCollection() {
		return userCollection;
	}

	public void save(String dbName) {
		MongoCollection<Document> collection = database.getCollection(dbName);
		List<Integer> books = Arrays.asList(27464, 747854);
		Document person = new Document("_id", "hi");
		person
			.append("name", "Jo Bloggs")
			.append("address", new BasicDBObject("street", "123 Fake St")
				.append("city", "Faketon")
				.append("state", "MA")
				.append("zip", 12345))
			.append("books", books);
		collection.insertOne(person);
	}

	public void delete(String key) {
		Bson filter = eq("_id", key);
		this.getUserCollection().deleteOne(filter);
	}
}
