package shadowverse.orbs;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import shadowverse.action.MinionBuffAction;
import shadowverse.action.MinionSummonAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import shadowverse.action.RemoveMinionAction;

public class QueenHemera extends Minion {

    // Standard ID/Description
    public static final String ORB_ID = "shadowverse:QueenHemera";
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    private static final int ATTACK = 1;
    private static final int DEFENSE = 4;

    public QueenHemera() {
        // The passive/evoke description we pass in here, specifically, don't matter
        // You can ctrl+click on CustomOrb from the `extends CustomOrb` above.
        // You'll see below we override CustomOrb's updateDescription function with our own, and also, that's where the passiveDescription and evokeDescription
        // parameters are used. If your orb doesn't use any numbers/doesn't change e.g "Evoke: shuffle your draw pile."
        // then you don't need to override the update description method and can just pass in the parameters here.
        this.ID = ORB_ID;
        this.img = ImageMaster.loadImage("img/orbs/QueenHemera.png");
        this.name = orbString.NAME;
        this.attack = this.baseAttack = ATTACK;
        this.defense = this.baseDefense = DEFENSE;
        this.updateDescription();
    }

    @Override
    public void updateDescription() { // Set the on-hover description of the orb
        description = DESCRIPTIONS[0] + "3*" + this.attack + "=" + 3 * this.attack + DESCRIPTIONS[1];
    }


    @Override
    public void onEvoke() {
        AbstractDungeon.actionManager.addToTop(new SFXAction(this.ID.replace("shadowverse:", "") + "_Atk"));
        for (int i = 0; i < defense; i++) {
            this.effect();
        }
        this.defense = 0;
        AbstractDungeon.actionManager.addToBottom(new RemoveMinionAction());
    }

    @Override
    public void onEndOfTurn() {
        if (this.defense > 0) {
            AbstractDungeon.actionManager.addToTop(new SFXAction(this.ID.replace("shadowverse:", "") + "_Atk"));
            this.effect();
            AbstractDungeon.actionManager.addToBottom(new MinionBuffAction(0, -1, this));
            this.updateDescription();
        }
    }

    @Override
    public AbstractOrb makeCopy() {
        return new QueenHemera();
    }

    @Override
    public void effect() {
        AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.attack * 3));
        AbstractDungeon.actionManager.addToBottom(new MinionSummonAction(new Knight()));
    }
}
