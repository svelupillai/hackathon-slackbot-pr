import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;

public class MainDriver {

	public static void main(String[] args) throws Exception {
		var app = new App();

		final String USER_TOKEN = System.getenv("SLACK_USER_TOKEN");

		app.command("/prpal2", (req, ctx) -> {
			String payload = req.getPayload().getText();

			if (payload == null) {
				return ctx.ack("Error: Must provide at least one argument to PrPal");
			}

			String[] commandArgs = payload.split(" ");

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
				case Constants.REMIND:
					if (commandArgs.length < 4) {
						return ctx.ack("Error: Must provide command in the format: remind <PR_LINK> <hours> <minutes>");
					}
					return GithubReminder.remind(ctx, USER_TOKEN, commandArgs[1], commandArgs[2], commandArgs[3]);
				default:
					return ctx.ack("Invalid command: " + commandArgs[0]);
			}
		});

		var server = new SlackAppServer(app);
		server.start();
	}
}
