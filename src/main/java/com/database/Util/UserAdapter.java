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
}
