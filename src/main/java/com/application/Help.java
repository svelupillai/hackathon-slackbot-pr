package com.application;

public class Help {

	public static String getHelpText() {
		return "Usage: `/prpal <command> <args...>`\n\tCommands:\n\t\t" +
			"`subscribe <repository_name>` -> Subscribe to a repository\n\t\t" +
			"`follow <github_username>` -> Follow a Github user\n\t\t" +
			"`ping <comma_separated_list_of_slack_usernames> <link_to_PR>` -> Ping user(s) to review a PR\n\t\t" +
			"`remind <link_to_PR> <hours> <minutes>` -> Set yourself a reminder to review a PR in <hours> hours and <minutes> mins\n\t\t" +
			"`help` -> Display this help text";
	}
}
