package seedu.hireshell.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.hireshell.model.person.PersonMatchesFiltersPredicate.DateFilter;
import seedu.hireshell.model.person.PersonMatchesFiltersPredicate.RatingFilter;

public class PersonMatchesFiltersPredicateTest {

    @Test
    public void test_matchesRating() {
        // GREATER_THAN_OR_EQUAL
        PersonMatchesFiltersPredicate predicate = new PersonMatchesFiltersPredicate(
                new RatingFilter(RatingFilter.Operator.GREATER_THAN_OR_EQUAL, 7.0),
                null, null);

        Person personWithRating7 = new Person(new Name("Alice"), new Phone("12345678"),
                new Email("alice@example.com"), new Rating("7.0"), new Status("Applied"),
                new HashSet<>(), ReferralStatus.fromString("yes"), new Details("valid details"));
        assertTrue(predicate.test(personWithRating7));

        // GREATER_THAN
        predicate = new PersonMatchesFiltersPredicate(
                new RatingFilter(RatingFilter.Operator.GREATER_THAN, 7.0),
                null, null);
        assertFalse(predicate.test(personWithRating7));
        Person personWithRating8 = new Person(new Name("Bob"), new Phone("87654321"),
                new Email("bob@example.com"), new Rating("8.5"), new Status("Applied"),
                new HashSet<>(), ReferralStatus.fromString("yes"), new Details("valid details"));
        assertTrue(predicate.test(personWithRating8));

        // LESS_THAN
        predicate = new PersonMatchesFiltersPredicate(
                new RatingFilter(RatingFilter.Operator.LESS_THAN, 5.0),
                null, null);
        Person personWithRating4 = new Person(new Name("Charlie"), new Phone("11111111"),
                new Email("charlie@example.com"), new Rating("4.5"), new Status("Applied"),
                new HashSet<>(), ReferralStatus.fromString("yes"), new Details("valid details"));
        assertTrue(predicate.test(personWithRating4));
        assertFalse(predicate.test(personWithRating7));

        // LESS_THAN_OR_EQUAL
        predicate = new PersonMatchesFiltersPredicate(
                new RatingFilter(RatingFilter.Operator.LESS_THAN_OR_EQUAL, 5.0),
                null, null);
        Person personWithRating5 = new Person(new Name("David"), new Phone("22222222"),
                new Email("david@example.com"), new Rating("5.0"), new Status("Applied"),
                new HashSet<>(), ReferralStatus.fromString("yes"), new Details("valid details"));
        assertTrue(predicate.test(personWithRating5));
        assertTrue(predicate.test(personWithRating4));

        // EQUAL
        predicate = new PersonMatchesFiltersPredicate(
                new RatingFilter(RatingFilter.Operator.EQUAL, 8.5),
                null, null);
        assertTrue(predicate.test(personWithRating8));
        assertFalse(predicate.test(personWithRating7));
    }

    @Test
    public void test_matchesStatus() {
        PersonMatchesFiltersPredicate predicate = new PersonMatchesFiltersPredicate(
                null,
                "Interviewing", null);

        Person personInterviewing = new Person(new Name("Alice"), new Phone("12345678"),
                new Email("alice@example.com"), new Rating("7.0"), new Status("Interviewing"),
                new HashSet<>(), ReferralStatus.fromString("yes"), new Details("valid details"));
        assertTrue(predicate.test(personInterviewing));

        Person personApplied = new Person(new Name("Bob"), new Phone("87654321"),
                new Email("bob@example.com"), new Rating("8.5"), new Status("Applied"),
                new HashSet<>(), ReferralStatus.fromString("yes"), new Details("valid details"));
        assertFalse(predicate.test(personApplied));
    }

    @Test
    public void test_matchesDate() {
        LocalDate filterDate = LocalDate.of(2026, 4, 1);
        PersonMatchesFiltersPredicate predicate = new PersonMatchesFiltersPredicate(
                null, null,
                new DateFilter(DateFilter.Operator.BEFORE, filterDate));

        Person personBefore = new Person(new Name("Alice"), new Phone("12345678"),
                new Email("alice@example.com"), new Rating("7.0"), new Status("Applied"),
                new HashSet<>(), ReferralStatus.fromString("yes"), new Details("valid details"),
                LocalDateTime.of(2026, 3, 31, 23, 59));
        assertTrue(predicate.test(personBefore));

        Person personSameDay = new Person(new Name("Bob"), new Phone("87654321"),
                new Email("bob@example.com"), new Rating("8.5"), new Status("Applied"),
                new HashSet<>(), ReferralStatus.fromString("yes"), new Details("valid details"),
                LocalDateTime.of(2026, 4, 1, 0, 0));
        assertFalse(predicate.test(personSameDay));

        predicate = new PersonMatchesFiltersPredicate(
                null, null,
                new DateFilter(DateFilter.Operator.AFTER, filterDate));
        assertTrue(predicate.test(new Person(new Name("Charlie"), new Phone("11111111"),
                new Email("charlie@example.com"), new Rating("4.5"), new Status("Applied"),
                new HashSet<>(), ReferralStatus.fromString("yes"), new Details("valid details"),
                LocalDateTime.of(2026, 4, 2, 0, 0))));
    }

    @Test
    public void test_matchesAll() {
        PersonMatchesFiltersPredicate predicate = new PersonMatchesFiltersPredicate(
                new RatingFilter(RatingFilter.Operator.GREATER_THAN, 8.0),
                "Accepted",
                new DateFilter(DateFilter.Operator.AFTER, LocalDate.of(2026, 1, 1)));

        Person personMatch = new Person(new Name("Alice"), new Phone("12345678"),
                new Email("alice@example.com"), new Rating("8.5"), new Status("Accepted"),
                new HashSet<>(), ReferralStatus.fromString("yes"), new Details("valid details"),
                LocalDateTime.of(2026, 2, 1, 0, 0));
        assertTrue(predicate.test(personMatch));

        // Rating mismatch
        Person personRatingMismatch = new Person(new Name("Bob"), new Phone("87654321"),
                new Email("bob@example.com"), new Rating("7.5"), new Status("Accepted"),
                new HashSet<>(), ReferralStatus.fromString("yes"), new Details("valid details"),
                LocalDateTime.of(2026, 2, 1, 0, 0));
        assertFalse(predicate.test(personRatingMismatch));

        // Status mismatch
        Person personStatusMismatch = new Person(new Name("Charlie"), new Phone("11111111"),
                new Email("charlie@example.com"), new Rating("8.5"), new Status("Applied"),
                new HashSet<>(), ReferralStatus.fromString("yes"), new Details("valid details"),
                LocalDateTime.of(2026, 2, 1, 0, 0));
        assertFalse(predicate.test(personStatusMismatch));

        // Date mismatch
        Person personDateMismatch = new Person(new Name("David"), new Phone("22222222"),
                new Email("david@example.com"), new Rating("8.5"), new Status("Accepted"),
                new HashSet<>(), ReferralStatus.fromString("yes"), new Details("valid details"),
                LocalDateTime.of(2025, 12, 31, 23, 59));
        assertFalse(predicate.test(personDateMismatch));
    }

    @Test
    public void test_equals() {
        RatingFilter ratingFilter = new RatingFilter(RatingFilter.Operator.EQUAL, 5.0);
        DateFilter dateFilter = new DateFilter(DateFilter.Operator.BEFORE, LocalDate.of(2026, 1, 1));

        // DateFilter equals
        DateFilter dateFilterSame = new DateFilter(DateFilter.Operator.BEFORE, LocalDate.of(2026, 1, 1));
        DateFilter dateFilterDiff = new DateFilter(DateFilter.Operator.AFTER, LocalDate.of(2026, 1, 1));
        assertEquals(dateFilter, dateFilter);
        assertEquals(dateFilter, dateFilterSame);
        assertNotEquals(dateFilter, dateFilterDiff);
        assertNotEquals(dateFilter, null);
        assertNotEquals(dateFilter, "string");

        PersonMatchesFiltersPredicate predicate1 = new PersonMatchesFiltersPredicate(
                ratingFilter, "status", dateFilter);
        PersonMatchesFiltersPredicate predicate2 = new PersonMatchesFiltersPredicate(
                ratingFilter, "status", dateFilter);
        PersonMatchesFiltersPredicate predicate3 = new PersonMatchesFiltersPredicate(null, "status", dateFilter);
        PersonMatchesFiltersPredicate predicate4 = new PersonMatchesFiltersPredicate(ratingFilter, null, dateFilter);
        PersonMatchesFiltersPredicate predicate5 = new PersonMatchesFiltersPredicate(ratingFilter, "status", null);

        // PersonMatchesFiltersPredicate equals
        assertEquals(predicate1, predicate1);
        assertEquals(predicate1, predicate2);
        assertNotEquals(predicate1, predicate3);
        assertNotEquals(predicate1, predicate4);
        assertNotEquals(predicate1, predicate5);
        assertNotEquals(predicate1, null);
        assertNotEquals(predicate1, "string");
    }

    @Test
    public void test_toString() {
        RatingFilter ratingFilter = new RatingFilter(RatingFilter.Operator.EQUAL, 5.0);
        DateFilter dateFilter = new DateFilter(DateFilter.Operator.BEFORE, LocalDate.of(2026, 1, 1));
        PersonMatchesFiltersPredicate predicate = new PersonMatchesFiltersPredicate(ratingFilter, "status", dateFilter);

        String expected = PersonMatchesFiltersPredicate.class.getCanonicalName()
                + "{ratingFilter=" + ratingFilter + ", statusFilter=status, dateFilter=" + dateFilter + "}";
        assertEquals(expected, predicate.toString());
    }
}
