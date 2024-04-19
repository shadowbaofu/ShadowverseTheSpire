package shadowverse.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import shadowverse.cards.Neutral.Status.EvolutionPoint;
import shadowverse.cards.Vampire.NatMech.Mono;
import shadowverse.characters.AbstractShadowversePlayer;

public class GarnetReleaseAction extends AbstractGameAction {

    public GarnetReleaseAction(){
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            AbstractPlayer p = AbstractDungeon.player;
            upgradeAllCardsInGroup(p.hand);
            upgradeAllCardsInGroup(p.drawPile);
            upgradeAllCardsInGroup(p.discardPile);
            this.isDone = true;
        }
    }

    private void upgradeAllCardsInGroup(CardGroup cardGroup) {
        AbstractDungeon.effectList.add(new ExhaustCardEffect(new EvolutionPoint()));
        for (AbstractCard c : cardGroup.group) {
            if (c.type== AbstractCard.CardType.ATTACK&&c.hasTag(AbstractShadowversePlayer.Enums.MACHINE)&& !(c instanceof Mono)) {
                AbstractDungeon.player.exhaustPile.addToTop(new EvolutionPoint());
                if (cardGroup.type == CardGroup.CardGroupType.HAND)
                    c.superFlash();
                if (c.canUpgrade()){
                    c.upgrade();
                    c.applyPowers();
                }
            }
            if (c instanceof Mono && ((Mono) c).chosenBranch() == 2) {
                ((Mono) c).released = true;
            }
        }
    }
}
