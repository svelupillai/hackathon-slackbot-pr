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
			.time(reminderText)
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

		// No reminders at all
		if (remindersListResponse.getReminders().isEmpty()) {
			return ctx.ack("You have no PR review reminders set up.");
		}

		List<String> prReviewReminders = new ArrayList<>();

		for (Reminder reminder : remindersListResponse.getReminders()) {
			if (reminder.getText() == null || !reminder.getText().contains("Review the PR")) {
				continue;
			}

			if (!reminder.isRecurring()) {
				String reminderText = String.format("%s on %s", reminder.getText(), getDateFromTimestamp(reminder.getTime()));
				if (reminder.getCompleteTs() > 0) {
					reminderText += String.format(" *Marked complete on %s*", getDateFromTimestamp(reminder.getCompleteTs()));
				}
				prReviewReminders.add(reminderText);
			} else {
				prReviewReminders.add(String.format("Recurring reminder: %s", reminder.getText()));
			}
		}

		// Have other reminders but no PR review ones
		if (prReviewReminders.isEmpty()) {
			return ctx.ack("You have no PR review reminders set up.");
		}

		return ctx.ack("Here are your PR review reminders:\n" + String.join("\n", prReviewReminders));
	}

	public static Response deleteAllReminders(SlashCommandContext ctx, String userToken) throws SlackApiException, IOException {
		RemindersListResponse remindersListResponse = ctx.client().remindersList(RemindersListRequest.builder().token(userToken).build());

		if (!remindersListResponse.isOk()) {
			return ctx.ack("Error getting reminders");
		}

		// No reminders at all
		if (remindersListResponse.getReminders().isEmpty()) {
			return ctx.ack("You have no PR review reminders set up.");
		}

		int deleted = 0;

		for (Reminder reminder : remindersListResponse.getReminders()) {
			if (reminder.getText() == null || !reminder.getText().contains("Review the PR")) {
				continue;
			}
			ctx.client().remindersDelete(RemindersDeleteRequest.builder()
				.reminder(reminder.getId())
				.token(userToken).build());
			deleted++;
		}

		// Have other reminders but no PR review ones
		if (deleted == 0) {
			return ctx.ack("You have no PR review reminders set up.");
		}

		return ctx.ack("Successfully cleared PR review reminders");
	}

	private static Date getDateFromTimestamp(Integer timestamp) {
		return new Date ((long) timestamp * 1000);
	}
}
