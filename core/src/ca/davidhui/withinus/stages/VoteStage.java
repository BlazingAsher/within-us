package ca.davidhui.withinus.stages;

import ca.davidhui.withinus.WithinUs;
import ca.davidhui.withinus.actors.PlayerActor;
import ca.davidhui.withinus.actors.ui.InteractButton;
import ca.davidhui.withinus.enums.GameState;
import ca.davidhui.withinus.screens.LevelScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.List;

public class VoteStage extends Stage {
    private final WithinUs game;
    private InteractButton useButton;
    private TextButton reportButton, debugVoteButton;
    private final PlayerActor playerActor;
    private final List<String> otherPlayers;
    private PlayerActor voteChoice;
    private boolean submit = false;

    public VoteStage(Viewport viewport, WithinUs game, SpriteBatch batch, PlayerActor playerActor, List<String> otherPlayers) {
        super(viewport, batch);
        this.game = game;
        this.playerActor = playerActor;
        this.otherPlayers = otherPlayers;
        this.initVotingStage();
    }

    private void initVotingStage() {
        Window window = new Window("Voting", game.skin);
        Table table = new Table();
        table.setFillParent(true);
//        window.setResizable(true);
//        table.setDebug(true);

//        Label playerButton = new Label(playerActor.getUsername(), game.skin, "title");
//        table.add(playerButton);
//        SelectBox<String> choices = new SelectBox<>(game.skin);
//        choices.setItems("Imposter!", "Crewmate!");
//        table.add(choices);
//        table.row();
        if (otherPlayers != null) {
//            for (String player : otherPlayers) {
//                Label label = new Label(player, game.skin, "title");
//                table.add(label);
//                final SelectBox<String> choice = new SelectBox<>(game.skin);
//                choice.setItems("Imposter!", "Crewmate!");
//
//                choice.addListener(new ChangeListener() {
//                    @Override
//                    public void changed(ChangeEvent event, Actor actor) {
//                        System.out.println(choice.getSelected());
//                    }
//                });
//
//                table.add(choice);
//                table.row();
//            }
            TextButton voteButton = new TextButton("Vote", game.skin, "round");
            table.add(voteButton);
            final SelectBox<String> playerSelect = new SelectBox<>(game.skin);
            Array<String> playersAsArray = new Array<>();
            for (String player: otherPlayers) {
                playersAsArray.add(player);
            }
            playerSelect.setItems(playersAsArray);

            voteButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    System.out.println(playerSelect.getSelected());
                    finishVoting();
                }
            });

            table.add(playerSelect);
            table.row();

            TextButton skipButton = new TextButton("Skip", game.skin, "round");
            skipButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    finishVoting();
                }
            });
            table.add(skipButton);
        }
//        window.add(table);
        this.addActor(table);
    }

    private void finishVoting() {
        LevelScreen screen = (LevelScreen) game.getScreen();
        screen.setGameState(GameState.RUNNING);
    }
}
