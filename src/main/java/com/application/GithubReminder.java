package com.application;

import java.io.IOException;
import java.util.Date;

import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.reminders.RemindersAddRequest;
import com.slack.api.methods.response.reminders.RemindersAddResponse;

public class GithubReminder {

	public static Response remind(SlashCommandContext ctx, String userToken, String prLink, String hours, String minutes) throws SlackApiException, IOException {
		var logger = ctx.logger;

		int mins;
		int hrs;

		try {
			hrs = Integer.parseInt(hours);
			mins = Integer.parseInt(minutes);
		} catch (NumberFormatException e) {
			return ctx.ack(String.format("Error: invalid number [%s]", e.getMessage()));
		}

		RemindersAddResponse response = ctx.client().remindersAdd(RemindersAddRequest.builder()
			.time(String.valueOf(hrs * 60 * 60 + mins * 60)) // time in seconds
			.text(String.format("Reminder to review PR %s!", prLink))
			.token(userToken)
			.build()
		);
		logger.info("result: {}", response);

		if (response.getReminder() == null) {
			return ctx.ack("Error setting reminder");
		}

		Date reminderDate = new Date ((long) response.getReminder().getTime() * 1000);
		return ctx.ack(String.format("You will be reminded to review the PR %s on %s", prLink, reminderDate));
	}
}
