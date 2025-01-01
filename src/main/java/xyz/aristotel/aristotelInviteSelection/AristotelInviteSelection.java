package xyz.aristotel.aristotelInviteSelection;

import com.sun.net.httpserver.HttpServer;
import xyz.aristotel.aristotelInviteSelection.commands.InviteCommand;
import xyz.aristotel.aristotelInviteSelection.http.WhitelistHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

public class AristotelInviteSelection extends JavaPlugin {

    private HttpServer httpServer;
    public static Logger logger;

    @Override
    public void onEnable() {
        logger = getLogger();
        logger.info("ArisInviter Plugin Enabled.");

        // Register our /invite command executor
        if (getCommand("invite") != null) {
            getCommand("invite").setExecutor(new InviteCommand());
        } else {
            logger.warning("Could not find /invite in plugin.yml!");
        }

        // Start an embedded HTTP server on port 8080
        try {
            httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
            // Pass 'this' to WhitelistHandler so we can schedule tasks on the main thread
            httpServer.createContext("/whitelist", new WhitelistHandler(this));
            httpServer.start();
            logger.info("HTTP server started on port 8080 for whitelisting requests.");
        } catch (IOException e) {
            logger.severe("Failed to start embedded HTTP server: " + e.getMessage());
        }
    }

    @Override
    public void onDisable() {
        logger.info("ArisInviter Plugin Disabling...");

        // Stop the HTTP server if running
        if (httpServer != null) {
            httpServer.stop(0);
            logger.info("HTTP server stopped.");
        }
    }
}
