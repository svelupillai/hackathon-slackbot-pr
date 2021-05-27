package mongo;

import java.util.Objects;

import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DatabaseController {

	private static final String DATABASE_NAME_PR_PAL = "prPal";
	private static String COLLECTION_NAME_USERS = "users";

	private static MongoClient mongoClient;
	private static MongoDatabase mongoDatabase;
	private static 	MongoCollection mongoCollection;

	public DatabaseController() {
		mongoClient = getClientInstance();
		MongoDatabase database = getDatabaseInstancePrPal();
		MongoCollection collection = getCollectionInstanceUsers();
	}

	private MongoClient getClientInstance() {
		return Objects.isNull(mongoClient) ? new MongoClient() : mongoClient;
	}

	private MongoDatabase getDatabaseInstancePrPal() {
		return Objects.isNull(mongoDatabase) ? mongoClient.getDatabase(DATABASE_NAME_PR_PAL) : mongoDatabase;
	}

	private MongoCollection getCollectionInstanceUsers() {
		return Objects.isNull(mongoCollection) ? getDatabaseInstancePrPal().getCollection(COLLECTION_NAME_USERS) : mongoCollection;
	}

}
