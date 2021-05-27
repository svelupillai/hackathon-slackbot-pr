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

	private static DatabaseController databaseController = new DatabaseController();;

	public static Response subscribeToRepository(SlashCommandContext ctx, String repositoryName, String slackUserId) {
		User userQueried = UserAdapter.DocumentToUser(databaseController.getUser(slackUserId));

		if(Objects.isNull(userQueried)){
			//Create new user
			User newUser = new User();
			newUser.setUserId(slackUserId);

			newUser.setSubscribedRepoIds(List.of(repositoryName));

			databaseController.createUser(UserAdapter.userToDocument(newUser));
		} else {
			//Get user + Update with new repo name
			List<String> subscribedRepoIds = Optional.ofNullable(userQueried.getSubscribedRepoIds())
				.orElse(new ArrayList<>());
			subscribedRepoIds.add(repositoryName);
			userQueried.setSubscribedRepoIds(subscribedRepoIds);

			databaseController.updateUser(slackUserId, UserAdapter.userToDocument(userQueried));
		}

		return ctx.ack( "Successfully subscribed to repository " + repositoryName);
	}

	public static Response followUser(SlashCommandContext ctx, String username, String slackUserId) {
		User userQueried = UserAdapter.DocumentToUser(databaseController.getUser(slackUserId));

		if(Objects.isNull(userQueried)){
			//Create new user
			User newUser = new User();
			newUser.setUserId(slackUserId);
			newUser.setSubscribedUserIds(List.of(username));

			databaseController.createUser(UserAdapter.userToDocument(newUser));
		} else {
			//Get user + Update with new repo name
			List<String> subscribedUserIds = Optional.ofNullable(userQueried.getSubscribedUserIds())
				.orElse(new ArrayList<>());
			subscribedUserIds.add(username);
			userQueried.setSubscribedUserIds(subscribedUserIds);

			databaseController.updateUser(slackUserId, UserAdapter.userToDocument(userQueried));
		}

		return ctx.ack( "Successfully following Github user " + username);
	}
}
