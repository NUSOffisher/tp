package seedu.hireshell.testutil;

import static seedu.hireshell.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.hireshell.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.hireshell.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.hireshell.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.hireshell.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.hireshell.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.hireshell.logic.commands.CommandTestUtil.VALID_RATING_AMY;
import static seedu.hireshell.logic.commands.CommandTestUtil.VALID_RATING_BOB;
import static seedu.hireshell.logic.commands.CommandTestUtil.VALID_REFERRAL_STATUS_AMY;
import static seedu.hireshell.logic.commands.CommandTestUtil.VALID_REFERRAL_STATUS_BOB;
import static seedu.hireshell.logic.commands.CommandTestUtil.VALID_ROLE_FRIEND;
import static seedu.hireshell.logic.commands.CommandTestUtil.VALID_ROLE_HUSBAND;
import static seedu.hireshell.logic.commands.CommandTestUtil.VALID_STATUS_AMY;
import static seedu.hireshell.logic.commands.CommandTestUtil.VALID_STATUS_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.hireshell.model.AddressBook;
import seedu.hireshell.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").withRating("8.0")
            .withRoles("friends")
            .withReferralStatus("yes")
            .withDetail("Met at career fair")
            .build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432").withRating("8.5")
            .withRoles("owesMoney", "friends")
            .withReferralStatus("yes")
            .withDetail("Met at career fair")
            .build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withRating("7.0").withEmail("heinz@example.com").withAddress("wall street")
            .withReferralStatus("yes").withDetail("Met at career fair").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withRating("5.0").withEmail("cornelia@example.com")
            .withAddress("10th street").withRoles("friends").withReferralStatus("yes")
            .withDetail("Met at career fair").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withRating("9.5").withEmail("werner@example.com").withAddress("michegan ave")
            .withReferralStatus("yes").withDetail("Met at career fair").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withRating("6.0").withEmail("lydia@example.com").withAddress("little tokyo")
            .withReferralStatus("yes").withDetail("Met at career fair").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withRating("8.5").withEmail("anna@example.com").withAddress("4th street")
            .withReferralStatus("yes").withDetail("Met at career fair").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withRating("5.0").withEmail("stefan@example.com").withAddress("little india")
            .withReferralStatus("yes").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withRating("4.0").withEmail("hans@example.com").withAddress("chicago ave")
            .withReferralStatus("yes").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withRating(VALID_RATING_AMY).withAddress(VALID_STATUS_AMY)
            .withRoles(VALID_ROLE_FRIEND).withReferralStatus(VALID_REFERRAL_STATUS_AMY).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withRating(VALID_RATING_BOB).withAddress(VALID_STATUS_BOB)
            .withRoles(VALID_ROLE_HUSBAND, VALID_ROLE_FRIEND).withReferralStatus(VALID_REFERRAL_STATUS_BOB)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
