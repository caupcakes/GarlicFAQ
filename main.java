import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class main extends ListenerAdapter {
    static JDA jda;
    static String prefix = "!";
    public static void main(String[] args) throws LoginException {
        jda = JDABuilder.createDefault("your token here").enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES).setActivity(Activity.watching("!help")).addEventListeners(new main()).build();
    } // make sure your intents are enabled on discord developer if the bot is not working. Intents are enabled just in case this data is needed.

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (!event.getMessage().getContentRaw().toLowerCase().startsWith(prefix)) {
            return;
        }

        String message = event.getMessage().getContentRaw().toLowerCase().substring(prefix.length());

        EmbedBuilder eb = new EmbedBuilder();

        switch (message){
            case "wallet":
            case "wallets":
                eb.setTitle("We have 3 main wallets, 2 offline and 1 online");
                eb.setDescription("Garlicoin Core -- Recommended for most users. Requires 8-10 GB of space for the entire blockchain.\n" +
                        "[Garlium](https://xske.github.io/garlium/)\n" +
                        "[Web wallet](https://grlc.eu/!w/) \n" +
                        "Paper wallet -- entirely offline \n" +
                        "Mobile Wallet coming soon (Q2 2021)!\n\n[Read more](https://garlic.wiki/)");
                break;
            case "whatis":
            case "garlicoin":
                eb.setDescription("Garlicoin is hot out of the oven and ready to serve you with its buttery goodness.\n" +
                        "Forked from LTC, this decentralized cryptocurrency with memes backing its value will always be there for you.\n" +
                        "This is the coin you never thought you needed, and you probably don’t.");
                break;
            case "garlicoinnode":
            case "fullnode":
                eb.setTitle("Why host a full node?");
                eb.setDescription("Build a snazzy Garlicoin application like a wallet or a faucet\n" +
                        "Bragging rights\n\n[Read more](https://garlic.wiki/index.php/How_To_Host_A_Garlicoin_Full_Node)");
                break;
            case "howtobuy":
            case "buy":
                eb.setTitle("So you wanna buy some garlic?");
                eb.setDescription("Buy LTC/DOGE at your favoured exchange\n" +
                        "Deposit those coins on [Frei](https://freiexchange.com/)\n" +
                        "Sell them on Frei for BTC\n" +
                        "buy garlic\n" +
                        "Profit?\n\n[Read more](https://garlic.wiki/index.php/Buying_Garlicoin)");
                break;
            case "howtosell":
            case "sell":
                eb.setDescription("[Read more](https://garlic.wiki/index.php/Selling_Garlicoin_(for_Fiat))");
                break;
            case "revive":
            case "restore":
                eb.setDescription("[Read more](https://garlic.wiki/index.php/Reviving_Garlicoin_Wallet_(from_.dat_file))");
                break;
            case "why":
            case "whybuy":
            case "whomst":
                eb.setDescription("Garlicoin has XYZ features and a fun community.(insert elevator pitch here)");
                break;
        }

        event.getMessage().reply(eb.build()).mentionRepliedUser(false).queue();
    }
}
