package seedu.address.model.entity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * Represents an Entity's name in the address book.
 * Guarantees: details is present and not null,
 * field values is  validated as declared in {@link #isValidName(String)}, immutable.
 */
public class Name {

    //Constants
    private static final String SPECIAL_CHARACTERS = ",-.'";

    public static final String MESSAGE_CONSTRAINTS =
            "Names should adhere to the following constraints:\n"
                    + "1. The first character is an alphabet.\n"
                    + "2. It should contain alphabets, spaces, and these special characters, excluding "
                    + "the parentheses (" + SPECIAL_CHARACTERS + "). \n"
                    + "3.Contain at least one character";

    private static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum}" + SPECIAL_CHARACTERS + " ]*";

    // Data fields
    public final String fullName;
    //Constants
    private final Logger logger = LogsCenter.getLogger(Name.class);
    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        if (isValidName(name) == false) {
            logger.severe("Name is not valid:" + name);
        }
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns if a given string is a valid name.
     *
     * @param test Name.
     * @return boolean whether test is in valid name format.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    /**
     * Returns string representation of object.
     *
     * @return Name in string format.
     */
    @Override
    public String toString() {
        return fullName;
    }

    /**
     * Returns string representation of object, for storage.
     *
     * @return Name in string format.
     */
    public String toStorageValue() {
        return this.toString();
    }

    /**
     * Returns true if both Name objects have the same data fields.
     * This defines a stronger notion of equality between two Name object.
     *
     * @param other Other Name object.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && fullName.equals(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

    /**
     * Returns a deep copy of the Name object
     * @return a deep copy of the Name object
     */
    public Name copy() {
        return new Name(this.fullName);
    }
}
