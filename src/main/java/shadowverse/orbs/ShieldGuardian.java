package shadowverse.orbs;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class ShieldGuardian  extends Minion {

    // Standard ID/Description
    public static final String ORB_ID = "shadowverse:ShieldGuardian";
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    private static final int ATTACK = 1;
    private static final int DEFENSE = 1;

    public ShieldGuardian() {
        // The passive/evoke description we pass in here, specifically, don't matter
        // You can ctrl+click on CustomOrb from the `extends CustomOrb` above.
        // You'll see below we override CustomOrb's updateDescription function with our own, and also, that's where the passiveDescription and evokeDescription
        // parameters are used. If your orb doesn't use any numbers/doesn't change e.g "Evoke: shuffle your draw pile."
        // then you don't need to override the update description method and can just pass in the parameters here.
        this.ID = ORB_ID;
        this.img = ImageMaster.loadImage("img/orbs/ShieldGuardian.png");
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
    public AbstractOrb makeCopy() {
        return new ShieldGuardian();
    }

    @Override
    public void effect() {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.attack * 3));
    }
}
