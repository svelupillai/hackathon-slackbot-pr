package mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class UserAdapter {

	public static DBObject userToDBObject(User user) {
		return new BasicDBObject("_id", user.getUserId())
			.append("subscribedRepoIds", user.getSubscribedRepoIds())
			.append("subscribedUserIds", user.getSubscribedUserIds())
			.append("intervalTimeInMinutes", user.getIntervalTimeInMinutes());
	}

}
