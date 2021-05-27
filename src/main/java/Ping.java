import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.User;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Ping {

    static Map<String, String> env = System.getenv();
    static String value = env.get("SLACK_BOT_TOKEN");

    public static String pingForReview(SlashCommandContext ctx, String requestNames) throws IOException, SlackApiException {
        StringBuilder failedNames = new StringBuilder();

        if (requestNames == null || requestNames.trim().length() == 0){
            return "Provide peoples names.";
        }
        var result = ctx.client().usersList(r -> r.token(value));
        List<String> names = Arrays.asList(requestNames.split(","));
        for(String name : names) {
            System.out.println(name.trim());
            User user = result.getMembers().stream().filter(x-> x.getName().equalsIgnoreCase(name.trim())).findFirst().orElse(null);

            if(user != null) {
                ChatPostMessageResponse response = ctx.client().chatPostMessage(r -> r
                        .channel(user.getId())
                        .username(name)
                        .text(":wave: Your review is being requested.")
                );
            }
            else {
                failedNames.append(name);
            }
        }

        return failedNames.length() > 0 ? "The following user names are invalid: " + failedNames.toString() : failedNames.toString();
    }
}