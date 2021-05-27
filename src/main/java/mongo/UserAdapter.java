package mongo;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class UserAdapter {

	private static final String DB_FIELD_USER_ID = "_id";
	private static final String DB_FIELD_SUBSCRIBED_REPO_IDS = "subscribedRepoIds";
	private static final String DB_FIELD_SUBSCRIBED_USER_IDS = "subscribedUserIds";
	private static final String DB_FIELD_INTERVAL_TIME_IN_MIN = "intervalTimeInMinutes";

	public static DBObject userToDBObject(User user) {
		return new BasicDBObject(DB_FIELD_USER_ID, user.getUserId())
			.append(DB_FIELD_SUBSCRIBED_REPO_IDS, user.getSubscribedRepoIds())
			.append(DB_FIELD_SUBSCRIBED_USER_IDS, user.getSubscribedUserIds())
			.append(DB_FIELD_INTERVAL_TIME_IN_MIN, user.getIntervalTimeInMinutes());
	}

	public static User DBObjectToUser(DBObject dbObject) {
		User user = new User();
		user.setUserId((String) dbObject.get(DB_FIELD_USER_ID));
		user.setSubscribedRepoIds((List<String>) dbObject.get(DB_FIELD_SUBSCRIBED_REPO_IDS));
		user.setSubscribedUserIds((List<String>) dbObject.get(DB_FIELD_SUBSCRIBED_USER_IDS));
		user.setIntervalTimeInMinutes((Integer) dbObject.get(DB_FIELD_INTERVAL_TIME_IN_MIN));
		return user;
	}

}
