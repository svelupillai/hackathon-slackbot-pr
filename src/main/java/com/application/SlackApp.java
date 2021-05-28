package com.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.slack.api.bolt.App;
import com.slack.api.model.event.MessageEvent;

@Configuration
public class SlackApp {
	@Bean
	public App initSlackApp() {
		App app = new App();

		final String USER_TOKEN = System.getenv("SLACK_USER_TOKEN");

		app.command("/prpal", (req, ctx) -> {
			String payload = req.getPayload().getText();

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
					return GithubSubscribe.subscribeToRepository(ctx, repositoryName);
				case Constants.FOLLOW:
					if (commandArgs.length < 2) {
						return ctx.ack("Error: Must provide a Github username as the second argument");
					}
					String githubUsername = commandArgs[1];
					return GithubSubscribe.followUser(ctx, githubUsername);
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
				default:
					return ctx.ack("Invalid command: " + commandArgs[0]);
			}
		});

		app.event(MessageEvent.class, PRLinkRespond::checkForPRLinksAndRespond);

		return app;
	}
}
