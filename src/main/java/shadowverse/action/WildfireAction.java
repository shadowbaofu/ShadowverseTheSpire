package shadowverse.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.powers.DiscardPower;

public class WildfireAction extends AbstractGameAction {
    private int amount;
    public WildfireAction(int amount) {
        this.amount = amount;
        this.actionType = ActionType.DISCARD;
    }

    @Override
    public void update() {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group){
            if (c.hasTag(AbstractShadowversePlayer.Enums.NATURAL)){
                addToBot(new DiscardSpecificCardAction(c));
                addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.NORMAL, AttackEffect.FIRE));
                count++;
            }
        }
        if (count > 0){
            if (AbstractDungeon.player instanceof AbstractShadowversePlayer){
                ((AbstractShadowversePlayer) AbstractDungeon.player).discardCount++;
            }
            for (AbstractPower power : AbstractDungeon.player.powers){
                if (power instanceof DiscardPower){
                    ((DiscardPower) power).triggerOnDiscard(count);
                }
            }
        }
        this.isDone = true;
    }
}
