package seedu.hireshell.logic.parser;

import static seedu.hireshell.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hireshell.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.hireshell.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.hireshell.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.hireshell.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.hireshell.logic.commands.FilterCommand;
import seedu.hireshell.model.person.PersonMatchesFiltersPredicate;
import seedu.hireshell.model.person.PersonMatchesFiltersPredicate.RatingFilter;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // rating only
        PersonMatchesFiltersPredicate predicateRating = new PersonMatchesFiltersPredicate(
                new RatingFilter(RatingFilter.Operator.GREATER_THAN_OR_EQUAL, 7.0),
                null);
        FilterCommand expectedFilterCommandRating = new FilterCommand(predicateRating);
        assertParseSuccess(parser, " " + PREFIX_RATING + ">= 7", expectedFilterCommandRating);

        // status only
        PersonMatchesFiltersPredicate predicateStatus = new PersonMatchesFiltersPredicate(
                null,
                "Interviewing");
        FilterCommand expectedFilterCommandStatus = new FilterCommand(predicateStatus);
        assertParseSuccess(parser, " " + PREFIX_STATUS + "Interviewing", expectedFilterCommandStatus);

        // both rating and status
        PersonMatchesFiltersPredicate predicateBoth = new PersonMatchesFiltersPredicate(
                new RatingFilter(RatingFilter.Operator.LESS_THAN, 5.5),
                "Rejected");
        FilterCommand expectedFilterCommandBoth = new FilterCommand(predicateBoth);
        assertParseSuccess(parser, " " + PREFIX_RATING + "< 5.5 " + PREFIX_STATUS + "Rejected",
                expectedFilterCommandBoth);
    }

    @Test
    public void parse_invalidRating_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_RATING + ">= abc",
                "Ratings should be a number between 0 and 10 (decimals allowed).");
    }
}
