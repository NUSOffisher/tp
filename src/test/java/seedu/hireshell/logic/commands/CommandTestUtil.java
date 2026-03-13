package seedu.hireshell.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireshell.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.hireshell.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.hireshell.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.hireshell.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.hireshell.logic.parser.CliSyntax.PREFIX_REFERRAL_STATUS;
import static seedu.hireshell.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.hireshell.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.hireshell.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.hireshell.commons.core.index.Index;
import seedu.hireshell.logic.commands.exceptions.CommandException;
import seedu.hireshell.model.AddressBook;
import seedu.hireshell.model.Model;
import seedu.hireshell.model.person.NameContainsKeywordsPredicate;
import seedu.hireshell.model.person.Person;
import seedu.hireshell.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_RATING_AMY = "5.0";
    public static final String VALID_RATING_BOB = "9.5";
    public static final String VALID_STATUS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_STATUS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_ROLE_HUSBAND = "husband";
    public static final String VALID_ROLE_FRIEND = "friend";
    public static final String VALID_REFERRAL_STATUS_AMY = "yes";
    public static final String VALID_REFERRAL_STATUS_BOB = "no";


    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String RATING_DESC_AMY = " " + PREFIX_RATING + VALID_RATING_AMY;
    public static final String RATING_DESC_BOB = " " + PREFIX_RATING + VALID_RATING_BOB;
    public static final String STATUS_DESC_AMY = " " + PREFIX_STATUS + VALID_STATUS_AMY;
    public static final String STATUS_DESC_BOB = " " + PREFIX_STATUS + VALID_STATUS_BOB;
    public static final String ROLE_DESC_FRIEND = " " + PREFIX_ROLE + VALID_ROLE_FRIEND;
    public static final String ROLE_DESC_HUSBAND = " " + PREFIX_ROLE + VALID_ROLE_HUSBAND;
    public static final String REFERRAL_STATUS_DESC_AMY = " " + PREFIX_REFERRAL_STATUS + VALID_REFERRAL_STATUS_AMY;
    public static final String REFERRAL_STATUS_DESC_BOB = " " + PREFIX_REFERRAL_STATUS + VALID_REFERRAL_STATUS_BOB;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_RATING_DESC = " " + PREFIX_RATING + "11";
    public static final String INVALID_STATUS_DESC = " " + PREFIX_STATUS; // empty string not allowed for statuses
    public static final String INVALID_ROLE_DESC = " " + PREFIX_ROLE + "hubby*"; // '*' not allowed in roles
    public static final String INVALID_REFERRAL_STATUS_DESC = " " + PREFIX_REFERRAL_STATUS
            + "Maybe"; // 'not' not allowed in referral statuses

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_STATUS_AMY)
                .withRating(VALID_RATING_AMY).withRoles(VALID_ROLE_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_STATUS_BOB)
                .withRating(VALID_RATING_BOB).withRoles(VALID_ROLE_HUSBAND, VALID_ROLE_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
