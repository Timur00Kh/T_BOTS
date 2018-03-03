import java.io.*;
import java.util.Properties;

public class Config {
    private static final String BOT_CONF_PATH = "./config/bot.properties";

    public static String BOT_TOKEN;
    public static String BOT_NAME;

    public static void load() {

        Properties botSettings = new Properties();
        try {
            InputStream is = new FileInputStream(new File(BOT_CONF_PATH));
            botSettings.load(is);
            System.out.println("Конфиг бота успешно загружен");
            BOT_NAME = botSettings.getProperty("botName");
            BOT_TOKEN = botSettings.getProperty("token");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getBotToken() {
        return BOT_TOKEN;
    }

    public static String getBotName() {
        return BOT_NAME;
    }
}
