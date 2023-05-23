package shadowverse.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GarudaMod extends AbstractCardModifier {
    public static String ID = "shadowverse:GarudaMod";
    int cost;

    public GarudaMod(int cost) {
        super();
        this.cost = cost;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        super.onUse(card, target, action);
        AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player,this.cost*10), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return  rawDescription + CardCrawlGame.languagePack.getUIString("shadowverse:GarudaMod").TEXT[0];
    }

    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }


    @Override
    public AbstractCardModifier makeCopy() {
        return new GarudaMod(cost);
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
