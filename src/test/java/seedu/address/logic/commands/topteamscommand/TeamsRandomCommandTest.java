package seedu.address.logic.commands.topteamscommand;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.ArrayList;
import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.Comparators;
import seedu.address.commons.exceptions.AlfredException;
import seedu.address.model.Model;
import seedu.address.model.entity.SubjectName;
import seedu.address.model.entity.Team;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.TypicalTeams;

/**
 * Tests whether the {@link TopTeamsRandomCommand} works as expected with regards to
 * different scenarios and is capable of handling errors appropriately.
 */
class TeamsRandomCommandTest {

    private static final int VALID_TOP_TEAMS = 5;
    private Model model;
    private Model expectedModel;
    private Model emptyModel = new ModelManagerStub();
    private ArrayList<Comparator<Team>> comparators = new ArrayList<>();

    @BeforeEach
    public void setUp() throws AlfredException {
        model = new ModelManagerStub();
        expectedModel = new ModelManagerStub();
        for (Team t : TypicalTeams.getTypicalTeams()) {
            model.addTeam(t);
            expectedModel.addTeam(t);
        }
    }

    @Test
    void execute_nonEmptyTeamList_commandSuccess() {
        // Non-empty team list and no comparators and no subject.
        assertCommandSuccess(new TopTeamsRandomCommand(VALID_TOP_TEAMS, comparators, null), model,
                String.format(TopTeamsRandomCommand.MESSAGE_SUCCESS, VALID_TOP_TEAMS), expectedModel);

        // Non-empty team list and no comparators and with subject - teams with subject exist.
        assertCommandSuccess(new TopTeamsRandomCommand(VALID_TOP_TEAMS, comparators, SubjectName.HEALTH), model,
                String.format(TopTeamsRandomCommand.MESSAGE_SUCCESS, VALID_TOP_TEAMS), expectedModel);

        // Non-empty team list with comparators and no subject.
        comparators.add(Comparators.rankByIdAscending());
        comparators.add(Comparators.rankByParticipantsDescending());
        assertCommandSuccess(new TopTeamsRandomCommand(VALID_TOP_TEAMS, comparators, null), model,
                String.format(TopTeamsRandomCommand.MESSAGE_SUCCESS, VALID_TOP_TEAMS), expectedModel);

        // Non-empty team list with comparators and subject - teams with subject exist.
        assertCommandSuccess(new TopTeamsRandomCommand(VALID_TOP_TEAMS, comparators, SubjectName.HEALTH), model,
                String.format(TopTeamsRandomCommand.MESSAGE_SUCCESS, VALID_TOP_TEAMS), expectedModel);
    }

    @Test
    void execute_emptyTeamList_commandFailure() {
        // Empty team list no subject specified
        assertCommandFailure(new TopTeamsRandomCommand(VALID_TOP_TEAMS, comparators, null), emptyModel,
                TopTeamsCommand.MESSAGE_NO_TEAM);

        // Empty team list subject specified
        assertCommandFailure(new TopTeamsRandomCommand(VALID_TOP_TEAMS, comparators, SubjectName.SOCIAL), emptyModel,
                TopTeamsCommand.MESSAGE_NO_TEAM);

        // Non-Empty team list - subject specified does not have teams
        assertCommandFailure(new TopTeamsRandomCommand(VALID_TOP_TEAMS, comparators, SubjectName.SOCIAL), model,
                String.format(TopTeamsCommand.MESSAGE_NO_TEAM_SUBJECT, SubjectName.SOCIAL.toString()));
    }
}
