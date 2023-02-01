package TGbot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class TGkurs {
    public static void main(String[] args) throws IOException {
        TelegramBot bot = new TelegramBot("5831914935:AAH-wshbk9IXnKTGahRWHNLijeIU0AVjucY");
        bot.setUpdatesListener(updates -> {
            updates.forEach(upd -> {

                try {
                    System.out.println(upd);
                    long chatId = upd.message().chat().id();
                    String inMessage = upd.message().text();

                    String results = "";
                    if (inMessage.equals("/start")) {
                        results = "Привет, напиши дату через /...";

                    } else {
                        Document doc = Jsoup.connect("https://cbr.ru/scripts/XML_daily.asp?date_req=" + inMessage).get();
                        System.out.println(doc.title());
                        Elements valutes = doc.select("Valute");
                        String result = null;
                        for (Element valute : valutes) {
                            if (valute.attr("ID").equals("R01235")) {
                                result = valute.select("Value").text();
                                System.out.println(result);
                            }
                        }
                    }
                    SendMessage request = new SendMessage(chatId, results);
                    bot.execute(request);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

    }
}
