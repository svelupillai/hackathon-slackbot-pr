package mongo.Controller;

import static mongo.Util.Constants.DB_FIELD_USER_ID;

import java.util.Objects;

import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DatabaseController {

	private static final String DATABASE_NAME_PR_PAL = "prPal";
	private static String COLLECTION_NAME_USERS = "users";

	private static String OPERATION_SET = "$set";

	private static MongoClient mongoClient;
	private static MongoDatabase mongoDatabase;
	private static DBCollection mongoCollection;

	public DatabaseController() {
		mongoClient = getClientInstance();
		MongoDatabase database = getDatabaseInstancePrPal();
		DBCollection collection = getCollectionInstanceUsers();
	}

	private MongoClient getClientInstance() {
		return Objects.isNull(mongoClient) ? new MongoClient() : mongoClient;
	}

	private MongoDatabase getDatabaseInstancePrPal() {
		return Objects.isNull(mongoDatabase) ? mongoClient.getDatabase(DATABASE_NAME_PR_PAL) : mongoDatabase;
	}

	private DBCollection getCollectionInstanceUsers() {
		return Objects.isNull(mongoCollection) ? (DBCollection) getDatabaseInstancePrPal().getCollection(COLLECTION_NAME_USERS) : mongoCollection;
	}

	public DBObject getUser(String userId) {
		DBObject query = new BasicDBObject(DB_FIELD_USER_ID, userId);
		DBCursor cursor = getCollectionInstanceUsers().find(query);
		return cursor.one();
	}

	public void updateUser(String userIdToUpdate, DBObject newDocument) {
		DBObject query = new BasicDBObject(DB_FIELD_USER_ID, userIdToUpdate);
		BasicDBObject updateObject = new BasicDBObject(OPERATION_SET, newDocument);
		getDatabaseInstancePrPal().getCollection(COLLECTION_NAME_USERS).updateOne((Bson) query, updateObject);
	}

}
