package mongo.Util;

import static mongo.Util.Constants.*;

import java.util.List;

import org.bson.Document;

import mongo.Model.User;

public class UserAdapter {

	public static Document userToDocument(User user) {
		return new Document(DB_FIELD_USER_ID, user.getUserId())
			.append(DB_FIELD_SUBSCRIBED_REPO_IDS, user.getSubscribedRepoIds())
			.append(DB_FIELD_SUBSCRIBED_USER_IDS, user.getSubscribedUserIds())
			.append(DB_FIELD_INTERVAL_TIME_IN_MIN, user.getIntervalTimeInMinutes());
	}

	public static User DBObjectToUser(Document document) {
		User user = new User();
		user.setUserId((String) document.get(DB_FIELD_USER_ID));
		user.setSubscribedRepoIds((List<String>) document.get(DB_FIELD_SUBSCRIBED_REPO_IDS));
		user.setSubscribedUserIds((List<String>) document.get(DB_FIELD_SUBSCRIBED_USER_IDS));
		user.setIntervalTimeInMinutes((Integer) document.get(DB_FIELD_INTERVAL_TIME_IN_MIN));
		return user;
	}

}
