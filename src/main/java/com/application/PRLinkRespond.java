package com.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.slack.api.app_backend.events.payload.EventsApiPayload;
import com.slack.api.bolt.context.builtin.EventContext;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.conversations.ConversationsInfoRequest;
import com.slack.api.methods.request.conversations.ConversationsMembersRequest;
import com.slack.api.methods.request.users.UsersInfoRequest;
import com.slack.api.methods.response.conversations.ConversationsInfoResponse;
import com.slack.api.methods.response.conversations.ConversationsMembersResponse;
import com.slack.api.methods.response.users.UsersInfoResponse;
import com.slack.api.model.event.MessageEvent;

public class PRLinkRespond {

	// Parses messages for GitHub PR links and notifies everyone mentioned
	public static Response checkForPRLinksAndRespond(EventsApiPayload<MessageEvent> payload, EventContext ctx) throws SlackApiException, IOException {
		MessageEvent event = payload.getEvent();

		String text = event.getText();

		if (text == null) {
			return ctx.ack();
		}

		// Links and @mentions in a Slack message are always surrounded by <>
		// e.g. <https://github.com/svelupillai/hackathon-slackbot-pr/pull/11>
		// If the link is embedded in text, it will show as:
		// <https://github.com/svelupillai/hackathon-slackbot-pr/pull/11|my PR>
		String[] maybeLinksOrMentions = text.split("<");

		ConversationsMembersResponse channelMembersResponse = ctx.client()
			.conversationsMembers(ConversationsMembersRequest.builder()
				.channel(event.getChannel())
				.build());

		ConversationsInfoResponse channelInfoResponse = ctx.client()
			.conversationsInfo(ConversationsInfoRequest.builder()
				.channel(event.getChannel())
				.build());

		UsersInfoResponse usersInfoResponse = ctx.client()
			.usersInfo(UsersInfoRequest.builder()
				.user(event.getUser())
				.build());

		// There seems to be a bug where it can't retrieve channel info and members for DMs, so just do nothing in this case
		if (!channelInfoResponse.isOk() || !channelMembersResponse.isOk()) {
			return ctx.ack();
		}

		Set<String> membersToPing = new HashSet<>();
		List<String> linksPosted = new ArrayList<>();

		for (String word : maybeLinksOrMentions) {
			if (isGithubPRLink(word)) {
				linksPosted.add(word.substring(0, getEndOfLink(word)));
			} else if (isAtMention(word, channelMembersResponse.getMembers())) {
				membersToPing.add(text.substring(1, text.indexOf(">")));
			} else if (isAtHereOrAtChannel(word)) {
				membersToPing.addAll(channelMembersResponse.getMembers());
			}
		}

		for (String member: membersToPing) {
			for (String link : linksPosted) {
				ctx.client().chatPostMessage(r -> r
					.channel(member)
					.text(String.format("Hey! %s just posted a PR link in #%s: %s",
						usersInfoResponse.getUser().getRealName(),
						channelInfoResponse.getChannel().getName(),
						link))
				);
			}
		}

		return ctx.ack();
	}

	// matches a URL ending with >, optionally with |<text> before the >, optionally with other text after, e.g:
	// https://github.com/svelupillai/hackathon-slackbot-pr/pull/11|This is my link text> Some other text after
	private static final Pattern urlPattern = Pattern.compile(
		"https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)(\\|.*)?>(.*)?",
		Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

	private static boolean isGithubPRLink(String text) {
		Matcher matcher = urlPattern.matcher(text);
		return matcher.matches() && text.contains("pull") && text.contains("github.com");
	}

	private static int getEndOfLink(String link) {
		if (link.contains("|")) {
			return link.indexOf("|");
		} else {
			return link.indexOf(">");
		}
	}

	private static boolean isAtMention(String text, List<String> channelMembers) {
		return text.length() > 0
			&& '@' == text.charAt(0)
			&& text.contains(">")
			&& channelMembers.stream().anyMatch(member -> text.substring(1, text.indexOf(">")).equals(member));
	}

	private static boolean isAtHereOrAtChannel(String text) {
		return text.contains("!here") || text.contains("!channel");
	}
}
