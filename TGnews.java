package TGbot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;

public class TGnews {
    public static void main(String[] args) throws IOException {
        TelegramBot bot = new TelegramBot("5814950958:AAH_M83SfpsGAOpLTPwzmAOb-QqV-_pjgy4");

        bot.setUpdatesListener(updates -> {
            updates.forEach(upd -> {
                try {
                    System.out.println(upd);
                    long chatId = upd.message().chat().id();
                    String inMessage = upd.message().text();

                    String result;
                    if (inMessage.equals("/start")) {
                        result = "Привет, напиши номер новости...";

                    } else {
                        int number = Integer.parseInt(inMessage);
                        Document doc = Jsoup.connect("https://lenta.ru/rss").get();
                        int index = number - 1;
                        Element news = doc.select("item").get(index);
                        String category = news.select("category").text();
                        String title = news.select("title").text();
                        String link = news.select("link").text();
                        String description = news.select("description").text();
                        result = category + "\n" + title + "\n" + description + "\n" + link;
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
