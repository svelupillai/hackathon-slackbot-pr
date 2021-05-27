package com.database.Util;

import static com.database.Util.Constants.*;

import java.util.List;
import java.util.Objects;

import org.bson.Document;

import com.database.Model.User;

public class UserAdapter {

	public static Document userToDocument(User user) {
		if(Objects.isNull(user)) {
			return null;
		}
		return new Document(DB_FIELD_USER_ID, user.getUserId())
			.append(DB_FIELD_SUBSCRIBED_REPO_IDS, user.getSubscribedRepoIds())
			.append(DB_FIELD_SUBSCRIBED_USER_IDS, user.getSubscribedUserIds())
			.append(DB_FIELD_INTERVAL_TIME_IN_MIN, user.getIntervalTimeInMinutes());
	}

	public static User DocumentToUser(Document document) {
		if(Objects.isNull(document)) {
			return null;
		}
		User user = new User();
		user.setUserId((String) document.get(DB_FIELD_USER_ID));
		user.setSubscribedRepoIds((List<String>) document.get(DB_FIELD_SUBSCRIBED_REPO_IDS));
		user.setSubscribedUserIds((List<String>) document.get(DB_FIELD_SUBSCRIBED_USER_IDS));
		user.setIntervalTimeInMinutes((Integer) document.get(DB_FIELD_INTERVAL_TIME_IN_MIN));
		return user;
	}

}
