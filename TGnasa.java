import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import org.jsoup.Jsoup;

import java.io.IOException;

public class TGnasa {
    public static void main(String[] args) throws IOException {
        TelegramBot bot = new TelegramBot("5690121563:AAGZr4m3eP9HLnO4YpwEvGcPpIUBnDFd52A");
        bot.setUpdatesListener(updates -> {
            updates.forEach(upd -> {

                try {
                    System.out.println(upd);
                    long chatId = upd.message().chat().id();
                    String inMessage = upd.message().text();
                    String result = "";
                    if (inMessage.equals("/start")) {
                        result = "Привет, напиши дату в формате yyyy-mm-dd";
                    } else {
                        String date = inMessage;
                        String jsonString = Jsoup.connect("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY&date=" + date)
                                .ignoreContentType(true).execute().body();
                        ObjectMapper obma = new ObjectMapper();
                        var jsonNode = obma.readTree(jsonString);
                        String imageUrl = jsonNode.get("url").asText();
                        String explanation = jsonNode.get("explanation").asText();
                        result = imageUrl;
                    }
                    SendMessage request = new SendMessage(chatId, result);
                    bot.execute(request);
                } catch (Exception exep) {
                    exep.printStackTrace();
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
