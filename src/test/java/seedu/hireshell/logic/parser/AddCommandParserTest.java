package seedu.hireshell.logic.parser;

import static seedu.hireshell.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hireshell.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.hireshell.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.hireshell.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.hireshell.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.hireshell.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.hireshell.logic.commands.CommandTestUtil.INVALID_RATING_DESC;
import static seedu.hireshell.logic.commands.CommandTestUtil.INVALID_REFERRAL_STATUS_DESC;
import static seedu.hireshell.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.hireshell.logic.commands.CommandTestUtil.INVALID_STATUS_DESC;
import static seedu.hireshell.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.hireshell.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.hireshell.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.hireshell.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.hireshell.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.hireshell.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.hireshell.logic.commands.CommandTestUtil.RATING_DESC_AMY;
import static seedu.hireshell.logic.commands.CommandTestUtil.RATING_DESC_BOB;
import static seedu.hireshell.logic.commands.CommandTestUtil.REFERRAL_STATUS_DESC_AMY;
import static seedu.hireshell.logic.commands.CommandTestUtil.REFERRAL_STATUS_DESC_BOB;
import static seedu.hireshell.logic.commands.CommandTestUtil.ROLE_DESC_FRIEND;
import static seedu.hireshell.logic.commands.CommandTestUtil.ROLE_DESC_HUSBAND;
import static seedu.hireshell.logic.commands.CommandTestUtil.STATUS_DESC_AMY;
import static seedu.hireshell.logic.commands.CommandTestUtil.STATUS_DESC_BOB;
import static seedu.hireshell.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.hireshell.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.hireshell.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.hireshell.logic.commands.CommandTestUtil.VALID_RATING_BOB;
import static seedu.hireshell.logic.commands.CommandTestUtil.VALID_ROLE_FRIEND;
import static seedu.hireshell.logic.commands.CommandTestUtil.VALID_ROLE_HUSBAND;
import static seedu.hireshell.logic.commands.CommandTestUtil.VALID_STATUS_BOB;
import static seedu.hireshell.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.hireshell.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.hireshell.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.hireshell.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.hireshell.logic.parser.CliSyntax.PREFIX_REFERRAL_STATUS;
import static seedu.hireshell.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.hireshell.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.hireshell.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.hireshell.testutil.TypicalPersons.AMY;
import static seedu.hireshell.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.hireshell.logic.Messages;
import seedu.hireshell.logic.commands.AddCommand;
import seedu.hireshell.model.person.Email;
import seedu.hireshell.model.person.Name;
import seedu.hireshell.model.person.Person;
import seedu.hireshell.model.person.Phone;
import seedu.hireshell.model.person.Rating;
import seedu.hireshell.model.person.ReferralStatus;
import seedu.hireshell.model.person.Status;
import seedu.hireshell.model.role.Role;
import seedu.hireshell.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withRoles(VALID_ROLE_FRIEND).withDetail("").build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + RATING_DESC_BOB + STATUS_DESC_BOB + ROLE_DESC_FRIEND + REFERRAL_STATUS_DESC_BOB,
                new AddCommand(expectedPerson));


        // multiple roles - all accepted
        Person expectedPersonMultipleRoles = new PersonBuilder(BOB)
                .withRoles(VALID_ROLE_FRIEND, VALID_ROLE_HUSBAND)
                .withDetail("")
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + RATING_DESC_BOB
                + STATUS_DESC_BOB + ROLE_DESC_HUSBAND + ROLE_DESC_FRIEND + REFERRAL_STATUS_DESC_BOB,
                new AddCommand(expectedPersonMultipleRoles));
    }

    @Test
    public void parse_repeatedNonRoleValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + RATING_DESC_BOB + STATUS_DESC_BOB + ROLE_DESC_FRIEND + REFERRAL_STATUS_DESC_BOB;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple ratings
        assertParseFailure(parser, RATING_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RATING));

        // multiple statuses
        assertParseFailure(parser, STATUS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STATUS));

        // multiple referral statuses
        assertParseFailure(parser, REFERRAL_STATUS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REFERRAL_STATUS));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + STATUS_DESC_AMY
                        + RATING_DESC_AMY + REFERRAL_STATUS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_PHONE,
                        PREFIX_EMAIL, PREFIX_RATING, PREFIX_STATUS, PREFIX_REFERRAL_STATUS));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid rating
        assertParseFailure(parser, INVALID_RATING_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RATING));

        // invalid status
        assertParseFailure(parser, INVALID_STATUS_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STATUS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid rating
        assertParseFailure(parser, validExpectedPersonString + INVALID_RATING_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RATING));

        // invalid status
        assertParseFailure(parser, validExpectedPersonString + INVALID_STATUS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STATUS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero Roles
        Person expectedPerson = new PersonBuilder(AMY).withRoles().withDetail("").build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + RATING_DESC_AMY
                        + STATUS_DESC_AMY + REFERRAL_STATUS_DESC_AMY, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + RATING_DESC_BOB
                        + STATUS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + RATING_DESC_BOB
                        + STATUS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + RATING_DESC_BOB
                        + STATUS_DESC_BOB,
                expectedMessage);

        // missing rating prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_RATING_BOB
                        + STATUS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + RATING_DESC_BOB
                        + VALID_STATUS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB
                + VALID_RATING_BOB + VALID_STATUS_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + RATING_DESC_BOB
                + STATUS_DESC_BOB + ROLE_DESC_HUSBAND + ROLE_DESC_FRIEND + REFERRAL_STATUS_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + RATING_DESC_BOB
                + STATUS_DESC_BOB + ROLE_DESC_HUSBAND + ROLE_DESC_FRIEND + REFERRAL_STATUS_DESC_BOB,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + RATING_DESC_BOB
                + STATUS_DESC_BOB + ROLE_DESC_HUSBAND + ROLE_DESC_FRIEND + REFERRAL_STATUS_DESC_BOB,
                Email.MESSAGE_CONSTRAINTS);

        // invalid rating
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_RATING_DESC
                + STATUS_DESC_BOB + ROLE_DESC_HUSBAND + ROLE_DESC_FRIEND + REFERRAL_STATUS_DESC_BOB,
                Rating.MESSAGE_CONSTRAINTS);

        // invalid status
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + RATING_DESC_BOB
                + INVALID_STATUS_DESC + ROLE_DESC_HUSBAND + ROLE_DESC_FRIEND + REFERRAL_STATUS_DESC_BOB,
                Status.MESSAGE_CONSTRAINTS);

        // invalid role
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + RATING_DESC_BOB
                + STATUS_DESC_BOB + INVALID_ROLE_DESC + VALID_ROLE_FRIEND + REFERRAL_STATUS_DESC_BOB,
                Role.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + RATING_DESC_BOB
                + INVALID_STATUS_DESC + REFERRAL_STATUS_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + RATING_DESC_BOB + STATUS_DESC_BOB + ROLE_DESC_HUSBAND + ROLE_DESC_FRIEND
                        + REFERRAL_STATUS_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // invalid referral status
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + RATING_DESC_BOB
                + STATUS_DESC_BOB + ROLE_DESC_HUSBAND + ROLE_DESC_FRIEND + INVALID_REFERRAL_STATUS_DESC,
                ReferralStatus.MESSAGE_CONSTRAINTS);
    }
}
