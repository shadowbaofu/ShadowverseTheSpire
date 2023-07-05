package shadowverse.relics;


import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class DragonQueenCastle
        extends CustomRelic {
    public static final String ID = "shadowverse:DragonQueenCastle";
    public static final String IMG = "img/relics/DragonQueenCastle.png";
    public static final String OUTLINE_IMG = "img/relics/outline/DragonQueenCastle_Outline.png";
    private boolean activated = true;

    public DragonQueenCastle() {
        super(ID, ImageMaster.loadImage(IMG), RelicTier.SHOP, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK && ((card.costForTurn >= 3 && !card.freeToPlayOnce) || (card.cost == -1 && card.energyOnUse >= 3)) && this.activated) {
            this.activated = false;
            flash();
            AbstractMonster m = null;
            if (action.target != null)
                m = (AbstractMonster)action.target;
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractCard tmp = card.makeSameInstanceOf();
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = Settings.HEIGHT / 2.0F;
            tmp.applyPowers();
            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
            this.pulse = false;
        }
    }

    public void atTurnStart() {
        this.activated = true;
    }

    public boolean checkTrigger() {
        return this.activated;
    }
    public AbstractRelic makeCopy() {
        return (AbstractRelic) new DragonQueenCastle();
    }
}

