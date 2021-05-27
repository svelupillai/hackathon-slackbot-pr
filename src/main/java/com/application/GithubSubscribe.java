package com.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.database.Controller.DatabaseController;
import com.database.Model.User;
import com.database.Util.UserAdapter;
import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.response.Response;

public class GithubSubscribe {

	private static DatabaseController databaseController;

	public static Response subscribeToRepository(SlashCommandContext ctx, String repositoryName, String slackUserId) {
		// TODO Call GH API to subscribe to repository and persist in db

		//Persist subscription in database
		databaseController = new DatabaseController();
		User userQueried = UserAdapter.DocumentToUser(databaseController.getUser(slackUserId));

		if(Objects.isNull(userQueried)){
			//Create new user
			User newUser = new User();
			newUser.setUserId(slackUserId);

			List<String> subscribedRepoIds = new ArrayList<>();
			subscribedRepoIds.add(repositoryName);
			newUser.setSubscribedRepoIds(subscribedRepoIds);

			databaseController.createUser(UserAdapter.userToDocument(newUser));
		} else {
			//Get user + Update with new repo name
			List<String> subscribedRepoIds = userQueried.getSubscribedRepoIds();
			subscribedRepoIds = Optional.ofNullable(subscribedRepoIds)
				.orElse(new ArrayList<>());
			subscribedRepoIds.add(repositoryName);
			userQueried.setSubscribedRepoIds(subscribedRepoIds);

			databaseController.updateUser(slackUserId, UserAdapter.userToDocument(userQueried));
		}

		return ctx.ack( "Successfully subscribed to repository " + repositoryName);
	}

	public static Response followUser(SlashCommandContext ctx, String username, String slackUserId) {
		// TODO Call GH API to follow a user's activity and persist in db

		//Persist subscription in database
		databaseController = new DatabaseController();
		User userQueried = UserAdapter.DocumentToUser(databaseController.getUser(slackUserId));

		if(Objects.isNull(userQueried)){
			//Create new user
			User newUser = new User();
			newUser.setUserId(slackUserId);

			List<String> followedUserIds = new ArrayList<>();
			followedUserIds.add(username);
			newUser.setSubscribedUserIds(followedUserIds);

			databaseController.createUser(UserAdapter.userToDocument(newUser));
		} else {
			//Get user + Update with new repo name
			List<String> subscribedUserIds = userQueried.getSubscribedUserIds();
			subscribedUserIds = Optional.ofNullable(subscribedUserIds)
				.orElse(new ArrayList<>());
			subscribedUserIds.add(username);
			userQueried.setSubscribedUserIds(subscribedUserIds);

			databaseController.updateUser(slackUserId, UserAdapter.userToDocument(userQueried));
		}

		return ctx.ack( "Successfully following Github user " + username);
	}
}
