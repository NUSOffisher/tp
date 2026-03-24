package seedu.hireshell.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.hireshell.logic.commands.CommandResult;
import seedu.hireshell.logic.commands.exceptions.CommandException;
import seedu.hireshell.logic.parser.exceptions.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private final List<String> commandHistory = new ArrayList<>();
    private int commandHistoryIndex = -1;
    private String latestCommand = "";

    private final CommandExecutor commandExecutor;

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());

        commandTextField.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP -> navigateHistoryUp();
                case DOWN -> navigateHistoryDown();
            }
        });
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        try {
            commandExecutor.execute(commandText);
            commandHistory.add(commandText);
            commandHistoryIndex = -1;
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    private void navigateHistoryUp() {
        if (commandHistory.isEmpty()) {
            return;
        }

        if (commandHistoryIndex == -1) {
            latestCommand = commandTextField.getText();
            commandHistoryIndex = commandHistory.size();
        }

        commandHistoryIndex--;
        if (commandHistoryIndex < 0) {
            commandHistoryIndex = 0;
        }

        commandTextField.setText(commandHistory.get(commandHistoryIndex));
        commandTextField.positionCaret(commandTextField.getText().length());
    }

    private void navigateHistoryDown() {
        if (commandHistory.isEmpty() || commandHistoryIndex == -1) {
            return;
        }

        commandHistoryIndex++;
        if (commandHistoryIndex >= commandHistory.size()) {
            commandTextField.setText(latestCommand);
            commandHistoryIndex = -1;
        } else {
            commandTextField.setText(commandHistory.get(commandHistoryIndex));
        }

        commandTextField.positionCaret(commandTextField.getText().length());
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.hireshell.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
