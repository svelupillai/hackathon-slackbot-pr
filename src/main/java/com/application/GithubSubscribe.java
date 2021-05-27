package com.application;

import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.response.Response;

public class GithubSubscribe {

	public static Response subscribeToRepository(SlashCommandContext ctx, String repositoryName) {
		// TODO Call GH API to subscribe to repository and persist in db
		return ctx.ack( "Successfully subscribed to repository " + repositoryName);
	}

	public static Response followUser(SlashCommandContext ctx, String username) {
		// TODO Call GH API to follow a user's activity and persist in db
		return ctx.ack( "Successfully following Github user " + username);
	}
}
