package shadowverse.relics;


import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import shadowverse.cards.Dragon.Basic.DragonOracle;


public class Hiro
        extends CustomRelic {
    public static final String ID = "shadowverse:Hiro";
    public static final String IMG = "img/relics/Hiro.png";
    public static final String OUTLINE_IMG = "img/relics/outline/Hiro_Outline.png";

    public Hiro() {
        super(ID, ImageMaster.loadImage(IMG), RelicTier.COMMON, LandingSound.CLINK);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        super.onEquip();
        for (int i = 0; i < AbstractDungeon.player.masterDeck.group.size(); i++) {
            AbstractCard c = AbstractDungeon.player.masterDeck.group.get(i);
            if (c instanceof DragonOracle) {
                AbstractDungeon.player.masterDeck.removeCard(c);
                AbstractCard h = new shadowverse.cards.Dragon.Basic.Hiro();
                if (c.upgraded)
                    h.upgrade();
                AbstractDungeon.player.masterDeck.addToTop(h);
            }
        }
    }

    public AbstractRelic makeCopy() {
        return (AbstractRelic) new Hiro();
    }
}

