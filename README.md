# ArisInviter

**ArisInviter** is a powerful and flexible Spigot plugin designed to enhance community engagement by introducing a robust invitation system for your Minecraft server. Players can invite others to join the server, from virtually anywhere!

## Features

- **Whitelist for all!**: Allow already whitelisted players to invite new players onto your server!
- **Whitelist outside of the game!**: An HTTP Server runs and when configured with a frontend, can allow players to whitelist on a website, discord bot or even from their terminals!

## Installation

1. Download the latest release of **ArisInviter** from the [Releases](https://github.com/YourRepo/ArisInviter/releases) section.
2. Place the `.jar` file into the `plugins` folder of your Spigot server.
3. Restart the server to generate the default configuration files.
4. Configure the plugin to your needs in the `ArisInviter/config.yml` file. (Work in progress)

## Commands

| Command               | Description                                | Permission           |
|-----------------------|--------------------------------------------|----------------------|
| `/invite [playername]`| Whitelists a new player!                   | `arisinviter.use`    |


## HTTP Server Interactions

In a new terminal window, run the following command:
```console
curl -X POST -H "Content-Type: application/json" \
     -d '{"username":"Notch"}' \
      http://[server-ip]:8080/whitelist
```

In order to whitelist a player (In this example, Notch)


## Development

This project is open-source! Contributions, bug reports, and feature requests are welcome. Feel free to fork the repository and submit a pull request.

## Issues

If you encounter any issues or have suggestions, please open an issue in the [Issues](https://github.com/YourRepo/ArisInviter/issues) section.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Credits

- Developed by [Your Name](https://github.com/NotDaniHere).
- Powered by the Spigot API.

## Support

For support or inquiries, reach out to me@danihere.xyz.
