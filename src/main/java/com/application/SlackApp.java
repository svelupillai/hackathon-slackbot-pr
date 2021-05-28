package com.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.slack.api.bolt.App;

@Configuration
public class SlackApp {

	private static App app;
	@Bean
	public App initSlackApp() {
		app = new App();

		final String USER_TOKEN = System.getenv("SLACK_USER_TOKEN");

		app.command("/prpal", (req, ctx) -> {
			String payload = req.getPayload().getText();
			String slackUserId = req.getPayload().getUserId();

			if (payload == null) {
				return ctx.ack("Error: Must provide at least one argument to PrPal");
			}

			String[] commandArgs = req.getPayload().getText().split(" ");
			System.out.println(commandArgs[0].toUpperCase());
			switch(commandArgs[0].toUpperCase()) {

				case Constants.SUBSCRIBE:
					if (commandArgs.length < 2) {
						return ctx.ack("Error: Must provide the repository name as the second argument");
					}
					String repositoryName = commandArgs[1];
					return GithubSubscribe.subscribeToRepository(ctx, repositoryName, slackUserId);
				case Constants.FOLLOW:
					if (commandArgs.length < 2) {
						return ctx.ack("Error: Must provide a Github username as the second argument");
					}
					String githubUsername = commandArgs[1];
					return GithubSubscribe.followUser(ctx, githubUsername, slackUserId);
				case Constants.HELP:
					return ctx.ack(Help.getHelpText());
				case Constants.PING:
					if (commandArgs.length < 3) {
						return ctx.ack("Error: Must provide a comma separated list of names and link to PR");
					}

					String result = Ping.pingForReview(ctx, commandArgs[1], commandArgs[2]);
					if(result.length() > 0){
						return ctx.ack(result);
					}
					return ctx.ack();
				case Constants.REMIND:
					if (commandArgs.length < 4) {
						return ctx.ack("Error: Must provide command in the format: remind <PR_LINK> <hours> <minutes>");
					}
					return GithubReminder.remind(ctx, USER_TOKEN, commandArgs[1], commandArgs[2], commandArgs[3]);
				case Constants.REPOS:
					return GithubSubscribe.getSubscribedRepos(ctx, req.getPayload().getUserId());
				case Constants.USERS:
					return GithubSubscribe.getFollowedUsers(ctx, req.getPayload().getUserId());
				case Constants.UNSUBSCRIBE:
					if (commandArgs.length < 2) {
						return ctx.ack("Error: Must provide the repository name as the second argument");
					}
					return GithubSubscribe.unSubscribeRepo(ctx, req.getPayload().getUserId(), commandArgs[1]);
				case Constants.UNFOLLOW:
					if (commandArgs.length < 2) {
						return ctx.ack("Error: Must provide a Github username as the second argument");
					}
					return GithubSubscribe.unFollowUser(ctx, req.getPayload().getUserId(), commandArgs[1]);
				default:
					return ctx.ack("Invalid command: " + commandArgs[0]);
			}
		});
		return app;
	}

	 public App getInstance(){
		return app == null ? initSlackApp() : app;
	}
}
