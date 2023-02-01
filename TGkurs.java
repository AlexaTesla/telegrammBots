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
        TelegramBot bot = new TelegramBot("5652981825:AAEmYbc80YU0OlWIdXwLmmj-8Hlc-s8P0Os");
        bot.setUpdatesListener(updates -> {
            updates.forEach(upd -> {

                try {
                    System.out.println(upd);
                    long chatId = upd.message().chat().id();
                    String inMessage = upd.message().text();
                    Document doc = Jsoup.connect("https://cbr.ru/scripts/XML_daily.asp?date_req=" + inMessage).get();
                    System.out.println(doc.title());
                    Elements valutes = doc.select("Valute");
                    String result = "";

                    for (Element valute : valutes) {
                        if (valute.attr("ID").equals("R01235")) {
                            result = valute.select("Value").text();
                            System.out.println(result);
                        }
                    }

                    SendMessage request = new SendMessage(chatId, result);
                    bot.execute(request);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
