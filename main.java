import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.security.auth.login.LoginException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class main extends ListenerAdapter {
    static JDA jda;
    static PriceMonitor priceMonitor;
    static String prefix = "!";
    static Map<String, String> faq;
    /*/
    if you know how to format these static variables please do
     */

    public static void main(String[] args) throws LoginException, FileNotFoundException {
        Dotenv dotenv = Dotenv.configure()
                .directory("D:\\grlcfaq\\src\\main\\java\\")
                .load();
        faq = new HashMap<>();

//        BufferedReader br = new BufferedReader(new FileReader("D:\\grlcfaq\\src\\main\\java\\faq.txt"));


        jda = JDABuilder.createDefault(dotenv.get("token"))
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES)
                .setActivity(Activity.watching("!help"))
                .addEventListeners(new main())
                .build();

        priceMonitor = new PriceMonitor();
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (!event.getMessage().getContentRaw().toLowerCase().startsWith(prefix)) {
            return;
        }

        String message = event.getMessage().getContentRaw().toLowerCase().substring(prefix.length());

        EmbedBuilder eb = new EmbedBuilder();

        switch (message) {
            case "help":
                eb.setTitle("Help");
                eb.setDescription("I am a meaningful and well architected FAQ Bot hosted on some person’s computer.\n" +
                        "\n" +
                        "Some core commands include: **!wallet**, **!garlicoin**, **!node**, **!howtobuy**, **!howtosell**, **!howtomine**\n" +
                        "\n" +
                        "[Read more](https://garlic.wiki/)");
                break;

            case "wallet":
            case "wallets":
                eb.setTitle("We have 3 main wallets, 2 offline and 1 online");
                eb.setDescription("[Garlicoin Core](https://garlicoin.io/downloads/) -- Recommended for most users. Requires 8-10 GB of space for the entire blockchain. for more info, check **!core**, **!coresync**\n" +
                        "[Garlium](https://xske.github.io/garlium/) for more info, check **!garlium**\n" +
                        "[Web wallet](https://grlc.eu/!w/) for more info, check **!webwallet**\n" +
                        "Paper wallet -- entirely offline for more info, check **!paperwallet**\n" +
                        "[Android wallet](https://play.google.com/store/apps/details?id=com.garlicwallet)\n" +
                        "IOS Wallet coming soon (Q2 2021)!\n\n[Read more](https://garlic.wiki/)");
                break;

            case "whatis":
            case "garlicoin":
                eb.setDescription("Garlicoin is hot out of the oven and ready to serve you with its buttery goodness.\n" +
                        "Forked from LTC, this decentralized cryptocurrency with memes backing its value will always be there for you.\n" +
                        "This is the coin you never thought you needed, and you probably don’t.\n\n[Read more](https://garlic.wiki/index.php/What_Is_Garlicoin)");
                break;

            case "node":
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
                        "Sell them on [Frei](https://freiexchange.com/) for BTC\n" +
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
            case "whomstbuy":
                eb.setDescription("Garlicoin has XYZ features and a fun community.(insert elevator pitch here)");
                break;

            case "wgrlc":
                eb.setDescription("WGRLC is a BEP20 tokenized version of GRLC on the Binance Smart Chain. Each is backed by 1 GRLC in escrow.");
                break;

            case "mining":
            case "mine":
            case "howtomine":
                eb.setDescription("All you need to mine are a GPU/CPU and electricity! Mining is the process of minting/creating new Garlicoins out of thin air by running the Allium algorithm on your computer." +
                        "\n\n[AMD](https://github.com/fancyIX/sgminer-phi2-branch)**!amd**" +
                        "\n[NVIDIA](https://github.com/lenis0012/ccminer)**!nvidia**" +
                        "\n[CPU](https://github.com/JayDDee/cpuminer-opt)**!cpu**" +
                        "\n\n[Read more](https://garlic.wiki/index.php/How_To_Mine)");
                break;

            case "instant":
            case "dailypayout":
            case "daily":
            case "instantpayout":
                eb.setDescription("Instant payout is evil.  Do not use it if you love garlic.\n\n[Read more](https://garlic.wiki/index.php/FreshGrlc_Instant_Payout_vs_Daily_Payout)");
                break;

            case "garlium":
                eb.setDescription("Garlium is a wallet that does not require downloading the entire blockchain, because a server monitors the blockchain on your behalf. If you are having issues, try a new server and restart Garlium.  If you use Garlium for a mining wallet, it will eventually crash and not sync.\n\nhttps://xske.github.io/garlium/ ");
                break;

            case "core":
                eb.setDescription("Garlicoin core is the safest online wallet because it downloads the entire blockchain (8-10 GB is required to use).\n\nhttps://garlicoin.io/downloads/");
                break;

            case "coresync":
                eb.setDescription("`./garlicoin-cli addnode freshgrlc.net onetry` or run `addnode freshgrlc.net onetry` in the console ");
                break;

            case "amd":
                eb.setTitle("Mining with AMD");
                eb.setDescription("https://github.com/fancyIX/sgminer-phi2-branch");
                break;

            case "nvidia":
                eb.setTitle("Mining with Nvidia");
                eb.setDescription("https://github.com/lenis0012/ccminer");
                break;

            case "cpu":
                eb.setTitle("Mining with CPU");
                eb.setDescription("https://github.com/JayDDee/cpuminer-opt");
                break;

            case "webwallet":
                eb.setDescription("The Garlicoin web wallet is [here](https://grlc.eu/!w/)\n" +
                        "It requires a single password.");
                break;

            case "bork":
                try {
                    bork(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            case "meme":
            case "mememe":
                try {
                    meme(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            case "price":
                price(event);
                return;
        }

        eb.setColor(new Color(242, 201, 76));
        event.getMessage().reply(eb.build()).mentionRepliedUser(false).queue();
    }

    static void price(GuildMessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Prices of Garlicoin");
        eb.setDescription(priceMonitor.getPriceDescription());
        if (PriceMonitor.getusdchange() > 0) {
            eb.setColor(new Color(92, 212, 36));
        } else if (PriceMonitor.getusdchange() < 0) {
            eb.setColor(new Color(212, 48, 36));
        }

        eb.setImage("attachment://price.png");
        event.getMessage().reply(eb.build()).addFile(new File("D:\\grlcfaq\\src\\main\\java\\img\\chart.png"), "price.png").queue();

    }


    static void bork(GuildMessageReceivedEvent event) throws IOException {
        HttpURLConnection url = (HttpURLConnection) new URL("https://dog.ceo/api/breeds/image/random").openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(url.getInputStream()));
        String in = br.readLine();

        JSONObject obj = new JSONObject(in);
        String dog_img = obj.getString("message");
        BufferedImage im = ImageIO.read(new File("D:\\grlcfaq\\src\\main\\java\\img\\garlicoin.png"));
        BufferedImage im2 = ImageIO.read(new URL(dog_img));
        BufferedImage overlayedImage = overlayImages(im2, im);


        writeImage(overlayedImage, "D:\\grlcfaq\\src\\main\\java\\img\\output.jpg", "JPG");
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("here!");
        eb.setImage("attachment://bork.jpg");
        event.getChannel().sendMessage(eb.build())
                .addFile(new File("D:\\grlcfaq\\src\\main\\java\\img\\output.jpg"), "bork.jpg")
                .queue();
    }


    static void meme(GuildMessageReceivedEvent event) throws IOException {
        // GET - get_memes
        HttpURLConnection url1 = (HttpURLConnection) new URL("https://api.imgflip.com/get_memes/").openConnection();
        BufferedReader br1 = new BufferedReader(new InputStreamReader(url1.getInputStream()));
        String in1 = br1.readLine();

        JSONObject obj1 = new JSONObject(in1);
        JSONArray memes = obj1.getJSONObject("data").getJSONArray("memes");

        // GET - get_joke
        HttpURLConnection url2 = (HttpURLConnection) new URL("https://official-joke-api.appspot.com/random_joke").openConnection();
        BufferedReader br2 = new BufferedReader(new InputStreamReader(url2.getInputStream()));
        String in2 = br2.readLine();

        JSONObject obj2 = new JSONObject(in2);
        String setup = obj2.getString("setup");
        String punchline = obj2.getString("punchline");


        //POST - caption_image
        URL url3 = new URL("https://api.imgflip.com/caption_image");
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("template_id", memes.getJSONObject((int) Math.floor(Math.random() * memes.length())).getString("id"));
        params.put("username", "whalegoddess");
        params.put("password", "garlicoinmemes");
        params.put("text0", setup);
        params.put("text1", punchline);

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);

        HttpURLConnection conn = (HttpURLConnection) url3.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        BufferedReader outputreader = new BufferedReader(new InputStreamReader(conn.getInputStream()));


        JSONObject finalJSONOutput = new JSONObject(outputreader.readLine()).getJSONObject("data");

        String outurl = finalJSONOutput.getString("url"); // no need to download because i am cool and hip :sunglasses:

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("here is your meme");
        eb.setImage(outurl);

        event.getMessage().reply(eb.build()).mentionRepliedUser(false).queue();
    }

    public static void writeImage(BufferedImage img, String fileLocation,
                                  String extension) {
        try {
            BufferedImage bi = img;
            File outputfile = new File(fileLocation);
            ImageIO.write(bi, extension, outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static BufferedImage overlayImages(BufferedImage bgImage, BufferedImage fgImage) {

        if (fgImage.getHeight() > bgImage.getHeight()
                || fgImage.getWidth() > fgImage.getWidth()) {
            return null;
        }

        Graphics2D g = bgImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(bgImage, 0, 0, null);


        g.drawImage(fgImage, bgImage.getWidth() - 128, bgImage.getHeight() - 128, null);

        g.dispose();
        return bgImage;
    }
}
