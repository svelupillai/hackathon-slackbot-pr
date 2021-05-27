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

	public static Response getSubscribedRepos(SlashCommandContext ctx, String userId) {
		User user = UserAdapter.DocumentToUser(databaseController.getUser(userId));
		if(user == null || user.getSubscribedRepoIds() == null) {
			return ctx.ack("You are not subscribed to any repos.");
		}
		return ctx.ack(String.format("You are subscribed to the following repos: %s", String.join(",", user.getSubscribedRepoIds())));
	}

	public static Response getFollowedUsers(SlashCommandContext ctx, String userId) {
		User user = UserAdapter.DocumentToUser(databaseController.getUser(userId));
		if(user == null || user.getSubscribedUserIds() == null) {
			return ctx.ack("You are not following any users.");
		}
		return ctx.ack(String.format("You are following these users: %s", String.join(",", user.getSubscribedUserIds())));
	}

	public static Response unSubscribeRepo(SlashCommandContext ctx, String userId, String repo){
		User user = UserAdapter.DocumentToUser(databaseController.getUser(userId));
		if(user == null || user.getSubscribedRepoIds() == null) {
			return ctx.ack("You are not subscribed to any repos.");
		}
		List<String> subscribedRepos = user.getSubscribedRepoIds();
		if(!subscribedRepos.contains(repo)){
			return ctx.ack(String.format("You are not subscribed to %s.",repo));
		}
		subscribedRepos.remove(repo);
		databaseController.updateUser(userId, UserAdapter.userToDocument(user));
		return ctx.ack(String.format("You have successfully unsubscribed to %s",repo));

	}

	public static Response unFollowUser(SlashCommandContext ctx, String userId, String userToUnfollow){
		User user = UserAdapter.DocumentToUser(databaseController.getUser(userId));
		if(user == null || user.getSubscribedUserIds() == null) {
			return ctx.ack("You are not following any users.");
		}
		List<String> followedUsers = user.getSubscribedUserIds();
		if(!followedUsers.contains(userToUnfollow)){
			return ctx.ack(String.format("You are not following %s.",userToUnfollow));
		}
		followedUsers.remove(userToUnfollow);
		databaseController.updateUser(userId, UserAdapter.userToDocument(user));
		return ctx.ack(String.format("You have successfully unfollowed to %s",userToUnfollow));

	}
}
