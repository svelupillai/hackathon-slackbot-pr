import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;

public class MainDriver {

	public static void main(String[] args) throws Exception {
		var app = new App();

		app.command("/prpal", (req, ctx) -> {
			String payload = req.getPayload().getText();

			if (payload == null) {
				return ctx.ack("Error: Must provide at least one argument to PrPal");
			}

			String[] commandArgs = req.getPayload().getText().split(" ");

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
				default:
					return ctx.ack("Invalid command: " + commandArgs[0]);
			}
		});

		var server = new SlackAppServer(app);
		server.start();
	}
}
