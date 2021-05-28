package com.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.reminders.RemindersAddRequest;
import com.slack.api.methods.request.reminders.RemindersDeleteRequest;
import com.slack.api.methods.request.reminders.RemindersListRequest;
import com.slack.api.methods.response.reminders.RemindersAddResponse;
import com.slack.api.methods.response.reminders.RemindersListResponse;
import com.slack.api.model.Reminder;

public class GithubReminder {

	public static Response remind(SlashCommandContext ctx, String userToken, String prLink, String reminderText) throws SlackApiException, IOException {
		var logger = ctx.logger;
		RemindersAddResponse response = ctx.client().remindersAdd(RemindersAddRequest.builder()
			.time(reminderText) // time in seconds
			.text(String.format("Review the PR %s", prLink))
			.token(userToken)
			.build()
		);
		logger.info("result: {}", response);

		if (response.getReminder() == null) {
			return ctx.ack("Error setting reminder");
		}

		if (response.getReminder().isRecurring()) {
			return ctx.ack(String.format("You will be reminded to review the PR %s %s", prLink, reminderText));
		} else if (response.getReminder().getTime() != null) {
			Date reminderDate = getDateFromTimestamp(response.getReminder().getTime());
			return ctx.ack(String.format("You will be reminded to review the PR %s on %s", prLink, reminderDate));
		}

		return ctx.ack(String.format("You will be reminded to review the PR %s %s", prLink, reminderText));
	}

	public static Response listReminders(SlashCommandContext ctx, String userToken) throws SlackApiException, IOException {
		RemindersListResponse remindersListResponse = ctx.client().remindersList(RemindersListRequest.builder().token(userToken).build());

		if (!remindersListResponse.isOk()) {
			return ctx.ack("Error getting reminders");
		}

		if (remindersListResponse.getReminders().isEmpty()) {
			return ctx.ack("You have no reminders set up.");
		}

		List<String> reminders = new ArrayList<>();

		for (Reminder reminder : remindersListResponse.getReminders()) {
			if (!reminder.isRecurring()) {
				String reminderText = String.format("%s on %s", reminder.getText(), getDateFromTimestamp(reminder.getTime()));
				if (reminder.getCompleteTs() > 0) {
					reminderText += String.format(" *Marked complete on %s*", getDateFromTimestamp(reminder.getCompleteTs()));
				}
				reminders.add(reminderText);
			} else {
				reminders.add(String.format("Recurring reminder: %s", reminder.getText()));
			}
		}

		return ctx.ack("Here are your PR review reminders:\n" + String.join("\n", reminders));
	}

	public static Response deleteAllReminders(SlashCommandContext ctx, String userToken) throws SlackApiException, IOException {
		RemindersListResponse remindersListResponse = ctx.client().remindersList(RemindersListRequest.builder().token(userToken).build());

		if (!remindersListResponse.isOk()) {
			return ctx.ack("Error getting reminders");
		}

		if (remindersListResponse.getReminders().isEmpty()) {
			return ctx.ack("You have no reminders set up.");
		}

		for (Reminder reminder : remindersListResponse.getReminders()) {
			ctx.client().remindersDelete(RemindersDeleteRequest.builder()
				.reminder(reminder.getId())
				.token(userToken).build());
		}
		return ctx.ack("Successfully cleared reminders");
	}

	private static Date getDateFromTimestamp(Integer timestamp) {
		return new Date ((long) timestamp * 1000);
	}
}
