package com.database.Controller;

import static com.database.Util.Constants.COLLECTION_NAME_USERS;
import static com.database.Util.Constants.DATABASE_NAME_PR_PAL;
import static com.database.Util.Constants.OPERATION_SET;
import static com.mongodb.client.model.Filters.eq;
import static com.database.Util.Constants.DB_FIELD_USER_ID;

import java.util.Objects;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DatabaseController {

	private static MongoClient mongoClient;
	private static MongoDatabase mongoDatabase;

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

	public void createUser(Document newDocument) {
		getUsersCollection().insertOne(newDocument);
	}

	public Document getUser(String userId) {
		Bson filter = eq(DB_FIELD_USER_ID, userId);
		return getUsersCollection().find(filter).first();
	}

	public void updateUser(String userIdToUpdate, Document newDocument) {
		Document query = new Document(DB_FIELD_USER_ID, userIdToUpdate);
		Document updateObject = new Document(OPERATION_SET, newDocument);
		getUsersCollection().updateOne(query, updateObject);
	}

	public void deleteUser(String userIdToDelete) {
		Bson filter = eq(DB_FIELD_USER_ID, userIdToDelete);
		getUsersCollection().deleteOne(filter);
	}

}
