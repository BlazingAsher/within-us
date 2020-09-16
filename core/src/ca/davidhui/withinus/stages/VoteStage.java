package ca.davidhui.withinus.stages;

import ca.davidhui.withinus.WithinUs;
import ca.davidhui.withinus.actors.PlayerActor;
import ca.davidhui.withinus.actors.ui.InteractButton;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.List;

public class VoteStage extends Stage {
    private final WithinUs game;
    private InteractButton useButton;
    private TextButton reportButton, debugVoteButton;
    private final PlayerActor playerActor;
    private final List<PlayerActor> otherPlayers;

    public VoteStage(Viewport viewport, WithinUs game, SpriteBatch batch, PlayerActor playerActor, List<PlayerActor> otherPlayers) {
        super(viewport, batch);
        this.game = game;
        this.playerActor = playerActor;
        this.otherPlayers = otherPlayers;
        this.initVotingStage();
    }

    private void initVotingStage() {
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);

        TextButton playerLabel = new TextButton(playerActor.getName(), game.skin, "round");
        table.add(playerLabel);
        table.row();

        if (otherPlayers != null) {
            for (PlayerActor playerActor : otherPlayers) {
                Label nameLabel = new Label(playerActor.getName(), game.skin);
                table.add(nameLabel);
                table.row();
            }
        }

        this.addActor(table);
    }
}
