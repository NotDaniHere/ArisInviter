package xyz.aristotel.aristotelInviteSelection.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.aristotel.aristotelInviteSelection.json.JSONObject; //Source code for org.json

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static xyz.aristotel.aristotelInviteSelection.AristotelInviteSelection.logger;
public class WhitelistHandler implements HttpHandler {

    private final JavaPlugin plugin;

    public WhitelistHandler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.info("WhitelistHandler: Received request on /whitelist");

        // We'll wrap in try/catch to ensure we log exceptions
        try {
            // Only accept POST
            if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                String badMethodResponse = "Only POST requests are allowed.";
                exchange.sendResponseHeaders(405, badMethodResponse.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(badMethodResponse.getBytes());
                }
                return;
            }

            // Read request body
            String requestBody;
            try (InputStream in = exchange.getRequestBody()) {
                requestBody = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            }
            logger.info("WhitelistHandler: Received payload: " + requestBody);

            // Parse JSON
            JSONObject json = new JSONObject(requestBody);

            if (!json.has("username")) {
                String noUsernameResponse = "JSON must contain 'username' field.";
                exchange.sendResponseHeaders(400, noUsernameResponse.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(noUsernameResponse.getBytes());
                }
                return;
            }

            String username = json.getString("username");
            logger.info("WhitelistHandler: Whitelisting user: " + username);

            // SCHEDULE the whitelisting on the main server thread
            // to avoid "WhitelistStateUpdateEvent may only be triggered synchronously."
            Bukkit.getScheduler().runTask(plugin, () -> {
                OfflinePlayer targetOffline = Bukkit.getOfflinePlayer(username);
                if (!targetOffline.isWhitelisted()) {
                    targetOffline.setWhitelisted(true);
                }
            });

            // Immediately respond (the actual whitelisting runs asynchronously)
            String successResponse = "User " + username + " scheduled to be whitelisted.";
            exchange.sendResponseHeaders(200, successResponse.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(successResponse.getBytes());
            }

        } catch (Throwable t) {
            // Catch any exception, log it, return 500
            t.printStackTrace();
            String errorResponse = "Internal server error: " + t.getMessage();
            exchange.sendResponseHeaders(500, errorResponse.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(errorResponse.getBytes());
            }
        }
    }
}
