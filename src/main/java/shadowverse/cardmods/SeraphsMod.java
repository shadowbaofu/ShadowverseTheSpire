package shadowverse.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import shadowverse.characters.AbstractShadowversePlayer;

public class SeraphsMod extends AbstractCardModifier {
    public static String ID = "shadowverse:SeraphsMod";

    public SeraphsMod() {
        super();
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return  rawDescription + CardCrawlGame.languagePack.getUIString("shadowverse:SeraphsMod").TEXT[0];
    }

    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    public void onInitialApplication(AbstractCard card) {
        if (!card.hasTag(AbstractShadowversePlayer.Enums.AMULET_FOR_ONECE)){
            card.tags.add(AbstractShadowversePlayer.Enums.AMULET_FOR_ONECE);
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new SeraphsMod();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
