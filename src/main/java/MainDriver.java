import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;

public class MainDriver {

    public static void main(String[] args) throws Exception {
        var app = new App();

        // All the room in the world for your code

        var server = new SlackAppServer(app);
        server.start();
    }
}
