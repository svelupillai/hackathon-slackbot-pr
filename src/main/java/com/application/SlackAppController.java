package com.application;

import com.slack.api.bolt.App;
import com.slack.api.bolt.servlet.SlackAppServlet;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

import javax.servlet.annotation.WebServlet;

@WebServlet("/slack/events")
public class SlackAppController extends SlackAppServlet {
    public SlackAppController(App app) {
        super(app);
    }
}