package shadowverse.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowverse.characters.AbstractShadowversePlayer;

public class HannaMod extends AbstractCardModifier {
    public static String ID = "shadowverse:HannaMod";

    public void onInitialApplication(AbstractCard card) {
        card.rawDescription = CardCrawlGame.languagePack.getUIString("shadowverse:HannaMod").TEXT[0] + card.rawDescription;
    }

    public boolean shouldApply(AbstractCard card) {
        return true;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractDungeon.actionManager.addToBottom((new DamageAllEnemiesAction(AbstractDungeon.player, 3, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_DIAGONAL)));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new HannaMod();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
