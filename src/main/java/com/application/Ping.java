package com.application;

import com.application.webhookEvents.BaseEvent;
import com.application.webhookEvents.PullRequest;
import com.application.webhookEvents.PullRequestEvent;
import com.database.Model.User;
import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Ping {

    static Map<String, String> env = System.getenv();
    static String value = env.get("SLACK_BOT_TOKEN");

    public static String pingForReview(SlashCommandContext ctx, String requestNames, String prLink) throws IOException, SlackApiException {
        StringBuilder failedNames = new StringBuilder();

        if (requestNames == null || requestNames.trim().length() == 0){
            return "Provide peoples names.";
        }
        var result = ctx.client().usersList(r -> r.token(value));
        List<String> names = Arrays.asList(requestNames.split(","));
        for(String name : names) {
            System.out.println(name.trim());
            com.slack.api.model.User user = result.getMembers().stream().filter(x-> x.getName().equalsIgnoreCase(name.trim())).findFirst().orElse(null);

            if(user != null) {
                ChatPostMessageResponse response = ctx.client().chatPostMessage(r -> r
                        .channel(user.getId())
                        .text(String.format(":wave: Your review is being requested on %s.", prLink))
                );
            }
            else {
                failedNames.append(name);
            }
        }

        return failedNames.length() > 0 ? "The following user names are invalid: " + failedNames.toString() : failedNames.toString();
    }

    public static void pingNotification(List<User> users, SlackApp slackApp, String type, BaseEvent evnt) throws IOException, SlackApiException {
        for(var user : users) {
            ChatPostMessageResponse response = slackApp.getInstance().client().chatPostMessage(r -> r
                    .token(value)
                    .channel(user.getUserId())
                    .text(String.format(":wave: Github notification: receiving action %s %s from user %s for %s",type, evnt.GetAction(), evnt.GetUser().getLogin(), evnt.GetPRLink()))
            );
        }
    }
}