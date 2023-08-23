package shadowverse.relics;


import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import shadowverse.powers.EpitaphPower;
import shadowverse.powers.WrathPower;


public class BerserkRelic
        extends CustomRelic {
    public static final String ID = "shadowverse:BerserkRelic";
    public static final String IMG = "img/relics/BerserkRelic.png";
    public static final String OUTLINE_IMG = "img/relics/outline/BerserkRelic_Outline.png";

    public BerserkRelic() {
        super(ID, ImageMaster.loadImage(IMG), RelicTier.RARE, LandingSound.FLAT);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return (AbstractRelic) new BerserkRelic();
    }


    @Override
    public void onPlayerEndTurn() {
        if (!this.grayscale && (AbstractDungeon.player.hasPower(WrathPower.POWER_ID) || AbstractDungeon.player.hasPower(EpitaphPower.POWER_ID))){
            addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 7));
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.grayscale = true;
        }
    }

    @Override
    public void atBattleStart() {
        this.grayscale = false;
    }
}


