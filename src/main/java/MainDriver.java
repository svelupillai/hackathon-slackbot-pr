import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainDriver {

    static Map<String, String> env = System.getenv();
    static String value = env.get("SLACK_BOT_TOKEN");
    public static void main(String[] args) throws Exception {

        var app = new App();

        // All the room in the world for your code
        processSlashCommands(app);
        var server = new SlackAppServer(app);
        server.start();
    }


    public static void processSlashCommands(App app){
        app.command("/pingforreview", (req, ctx) -> {
            var text = req.getPayload().getText();

            if (text == null || text.trim().length() == 0){
                return ctx.ack("Provide peoples names.");
            }
            var result = ctx.client().usersList(r -> r.token(value));
            List<String> names = Arrays.asList(text.split(","));
            for(String name : names) {
                List<User> rr = result.getMembers();
                for(User u : rr){
                    if(u.getName().equals(name))
                    {
                        ChatPostMessageResponse response = ctx.client().chatPostMessage(r -> r
                                .channel(u.getId())
                                .username(name)
                                .text(":wave: Your review is being requested.")
                        );


                    }
                }
            }
            return ctx.ack();
        });
    }

}
