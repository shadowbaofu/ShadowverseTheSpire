package shadowverse.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.GamblingChipAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import shadowverse.cards.Status.EvolutionPoint;

public class ValhoreanDealer extends CustomRelic implements BetterClickableRelic<ValhoreanDealer> {
    public static final String ID = "shadowverse:ValhoreanDealer";
    public static final String IMG = "img/relics/ValhoreanDealer.png";
    public static final String OUTLINE_IMG = "img/relics/outline/ValhoreanDealer_Outline.png";
    public boolean lifeCheck;
    private boolean triggeredThisTurn;

    public ValhoreanDealer() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    @Override
    public void onEachRightClick() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.gold >= 30) {
            p.gold -= 30;
            this.addToBot(new GamblingChipAction(p));
        }
    }


    @Override
    public AbstractRelic makeCopy() {
        return new ValhoreanDealer();
    }
}
