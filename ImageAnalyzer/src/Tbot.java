import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Tbot extends TelegramLongPollingBot {
    final String[] searchLinks = {
            "http://images.google.com/searchbyimage?image_url=",
            "https://yandex.ru/images/search?rpt=imageview&cbird=5&url=",
            "https://whatanime.ga/?url="
    };

    final String[] searchNames = {
            "Google SearchLink",
            "Yandex SearchLink",
            "WhatAnimeIsThis SearchLink"
    };


    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            SendMessage newMessage = new SendMessage().setChatId(chat_id);

            if (messageText.contains("http") || messageText.contains("jpg")) {
                if (imageLinkChecker(messageText)) {


                    newMessage.setText(messageText);
                    InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                    List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                    for (int i = 0; i < 2; i++) {
                        List<InlineKeyboardButton> rowInline = new ArrayList<>();
                        rowInline.add(new InlineKeyboardButton().setText(searchNames[i]).setUrl(searchLinks[i] + messageText));
                        rowsInline.add(rowInline);
                    }
                    // Add it to the message
                    markupInline.setKeyboard(rowsInline);
                    newMessage.setReplyMarkup(markupInline);
                } else {
                    newMessage.setText("invalid link or image isn't public accessible");
                }
                try {
                    execute(newMessage); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }

    @Override
    public String getBotUsername() {
        return Config.getBotName();
    }

    @Override
    public String getBotToken() {
        return Config.getBotToken();
    }

    public boolean imageLinkChecker(String s) {
        Image image = null;
        try {
            URL url = new URL(s);
            image = ImageIO.read(url);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
