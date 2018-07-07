package todos.web;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

@Named
final class Server {

    private Vertx vertx = Vertx.vertx();

    @PostConstruct
    final void init() {

        var router = Router.router(vertx);

        router.get("/michele").handler(ctx -> {

            var request = ctx.request();
            var response = ctx.response();

            response.putHeader("content-type", "text/simple").end("Hello squido!");
        });

        router.get("/1234").handler(ctx -> {

            var request = ctx.request();
            var colour = request.getParam("colour");
            var greeting = request.getParam("greeting");

            System.out.println("Colour: " + colour + ", greeting: " + greeting);

            if (greeting == null) {
                System.out.println("No greeting chosen, defaulting to \"Hello\".");
                greeting = "Hello";
            }

            var allowedColours = Stream.of("pink", "blue", "red", "yellow", "green", "orange", "purple").collect(toSet());

            if (!allowedColours.contains(colour)) {
                var defaultColour = "orange";
                System.out.println("Unsupported colour \"" + colour + "\", defaulting to \"" + defaultColour + "\". Supported values are: " + allowedColours.stream().collect(joining(", ", "[", "]"))  + ".");
                colour = defaultColour;
            }

            var response = ctx.response();

            var html = new StringBuilder();

            html.append("<html>");
            html.append("<h1>");
            html.append(greeting + " squida!");
            html.append("</h1>");
            html.append("<p>");
            html.append("<font color=\"" + colour + "\">");
            html.append("Baby is awesome!");
            html.append("</font>");
            html.append("</p>");
            html.append("</html>");

//            response.putHeader("content-type", "text/simple").end(html.toString());
            response.putHeader("content-type", "text/html").end(html.toString());
        });

        var options = new HttpServerOptions().setPort(8080);
        var server = vertx.createHttpServer(options);

        server.requestHandler(router::accept);

        server.listen();
    }
}
