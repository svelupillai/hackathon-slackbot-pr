package com.database.Controller;

import static com.database.Util.Constants.*;
import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.database.Model.User;
import com.mongodb.MongoClientSettings;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DatabaseController {

	private static MongoClient mongoClient;
	private static MongoDatabase mongoDatabase;
	private static CodecRegistry pojoCodecRegistry;

	public DatabaseController() {
		pojoCodecRegistry = org.bson.codecs.configuration.CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), org.bson.codecs.configuration.CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		mongoClient = getClientInstance();
		mongoDatabase = getDatabaseInstancePrPal();
	}

	private MongoClient getClientInstance() {
		return Objects.isNull(mongoClient) ? new MongoClient() : mongoClient;
	}

	private MongoDatabase getDatabaseInstancePrPal() {
		return Objects.isNull(mongoDatabase) ? mongoClient.getDatabase(DATABASE_NAME_PR_PAL).withCodecRegistry(pojoCodecRegistry) : mongoDatabase;
	}

	@NotNull
	private MongoCollection<User> getUsersCollection() {
		return getDatabaseInstancePrPal().getCollection(COLLECTION_NAME_USERS, User.class);
	}

	public void createUser(User user) {
		getUsersCollection().insertOne(user);
	}

	public User getUser(String userId) {
		Bson filter = eq(DB_FIELD_USER_ID, userId);
		return getUsersCollection().find(filter).first();
	}

	public List<User> getInterestedUsers(String userName, String repo, boolean shouldBeSameUser, boolean sendToOwner) {
		Bson userFilter = shouldBeSameUser ? eq(DB_FIELD_GITHUB_USERNAME, userName) : in(DB_FIELD_SUBSCRIBED_USER_IDS, userName);
		Bson repoFilter = in(DB_FIELD_SUBSCRIBED_REPO_IDS, repo);
		Bson ownerFilter = eq(DB_FIELD_GITHUB_USERNAME, userName);
		Bson filter = sendToOwner? or(or(userFilter, repoFilter), ownerFilter) : or(userFilter, repoFilter);
		return getUsersCollection().find(filter).into(new ArrayList<>());
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
