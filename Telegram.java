package TGbot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
public class Telegram {
    public static void main(String[] args) {
        TelegramBot bot = new TelegramBot("5653720217:AAG0nX-zzXM20sdHQ3zZCooxOA_eC6vFr3s");

        bot.setUpdatesListener(updates -> {
            updates.forEach(upd -> {
                System.out.println(upd);
                long chatId = upd.message().chat().id();
                String name = upd.message().from().firstName();
                String name2 = upd.message().from().lastName();
                String name3 = upd.message().from().username();
                String inMessage = upd.message().text();

                String message = name + name2 + " твой ник " + name3 + " сам " + inMessage;
                SendMessage request = new SendMessage(chatId, message);
                bot.execute(request);
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

    }
}
