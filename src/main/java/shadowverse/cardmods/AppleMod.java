package shadowverse.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowverse.cards.Necromancer.Default.Mordecai;
import shadowverse.cards.Necromancer.Mech.Nicola;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Necromancer;

public class AppleMod extends AbstractCardModifier {
    public static String ID = "shadowverse:AppleMod";
    private static final String TEXT1 = CardCrawlGame.languagePack.getUIString("shadowverse:Exhaust").TEXT[0];
    private static final String TEXT2 = CardCrawlGame.languagePack.getUIString("shadowverse:Ethereal").TEXT[0];

    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (card.color != Necromancer.Enums.COLOR_PURPLE){
            return  rawDescription + CardCrawlGame.languagePack.getUIString(ID).TEXT[0];
        }
        return  rawDescription + CardCrawlGame.languagePack.getUIString(ID).TEXT[1];
    }

    public void onInitialApplication(AbstractCard card) {
        if (!card.hasTag(AbstractShadowversePlayer.Enums.LASTWORD)){
            card.tags.add(AbstractShadowversePlayer.Enums.LASTWORD);
        }
    }

    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    public void onExhausted(AbstractCard card) {
        if (card.color != Necromancer.Enums.COLOR_PURPLE){
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DrawCardAction(2));
        }else {
            AbstractCard copy  = card.makeStatEquivalentCopy();
            if (!(copy instanceof Mordecai) && !(copy instanceof Nicola))
                copy.freeToPlayOnce = true;
            copy.exhaustOnUseOnce = true;
            copy.exhaust = true;
            copy.isEthereal = true;
            copy.rawDescription += " NL "+TEXT2+" 。 NL "+TEXT1+" 。";
            copy.initializeDescription();
            copy.applyPowers();
            AbstractDungeon.player.hand.addToTop(copy);
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new AppleMod();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
