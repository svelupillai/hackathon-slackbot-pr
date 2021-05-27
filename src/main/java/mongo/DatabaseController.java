package mongo;

import static com.mongodb.client.model.Filters.eq;
import static mongo.Util.Constants.DB_FIELD_USER_ID;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DatabaseController {

	private static final String DATABASE_NAME_PR_PAL = "prPal";
	private static String COLLECTION_NAME_USERS = "users";

	private static String OPERATION_SET = "$set";

	private static MongoClient mongoClient;
	private static MongoDatabase mongoDatabase;
	private static MongoCollection<Document> mongoCollection;

	public DatabaseController() {
		mongoClient = getClientInstance();
		mongoDatabase = getDatabaseInstancePrPal();
	}

	private MongoClient getClientInstance() {
		return Objects.isNull(mongoClient) ? new MongoClient() : mongoClient;
	}

	private MongoDatabase getDatabaseInstancePrPal() {
		return Objects.isNull(mongoDatabase) ? mongoClient.getDatabase(DATABASE_NAME_PR_PAL) : mongoDatabase;
	}

	@NotNull
	private MongoCollection<Document> getUsersCollection() {
		return getDatabaseInstancePrPal().getCollection(COLLECTION_NAME_USERS);
	}

	public void saveUser() {
		List<Integer> books = Arrays.asList(27464, 747854);
		Document person = new Document("_id", "hi");
		person
			.append("name", "Jo Bloggs")
			.append("address", new BasicDBObject("street", "123 Fake St")
				.append("city", "Faketon")
				.append("state", "MA")
				.append("zip", 12345))
			.append("books", books);
		getUsersCollection().insertOne(person);
	}

	public void deleteUser(String key) {
		MongoCollection<Document> collection = getUsersCollection();
		Bson filter = eq(DB_FIELD_USER_ID, key);
		collection.deleteOne(filter);
	}

	public Document getUser(String userId) {
		Bson filter = eq(DB_FIELD_USER_ID, userId);
		return getUsersCollection().find(filter).first();
	}

	public void updateUser(String userIdToUpdate, Document newDocument) {
		Document query = new Document(DB_FIELD_USER_ID, userIdToUpdate);
		Document updateObject = new Document(OPERATION_SET, newDocument);
		getUsersCollection().updateOne((Bson) query, updateObject);
	}

}
