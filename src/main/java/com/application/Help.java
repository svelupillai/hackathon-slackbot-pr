package com.application;

public class Help {

	public static String getHelpText() {
		return "Usage: `/prpal <command> <args...>`\n\tCommands:\n\t\t" +
			"`subscribe <repository_name>` -> Subscribe to a repository\n\t\t" +
			"`follow <github_username>` -> Follow a Github user\n\t\t" +
			"`unsubscribe <repository_name>` -> Unsubscribe to a repository\n\t\t" +
			"`unfollow <github_username>` -> Unfollow a user\n\t\t" +
			"`repos` -> List all repositories you are subscribed to\n\t\t" +
			"`users` -> List all users you are following\n\t\t" +
			"`ping <comma_separated_list_of_slack_usernames> <link_to_PR>` -> Ping user(s) to review a PR, e.g. ping john.smith,mary.jones https://github.com/foo/foo-repo/pulls/1\n\t\t" +
			"`remind <link_to_PR> <reminder_text>` -> Set yourself a reminder to review a PR, e.g. remind https://github.com/foo/foo-repo/pulls/1 in 5 minutes\n\t\t" +
			"`reminders` -> List all your PR review reminders\n\t\t" +
			"`clear_reminders` -> Clear all your PR review reminders\n\t\t" +
			"`help` -> Display this help text";
	}
}
