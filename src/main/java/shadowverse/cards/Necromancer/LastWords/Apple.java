package shadowverse.cards.Necromancer.LastWords;

import basemod.abstracts.CustomCard;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cardmods.AppleMod;
import shadowverse.characters.Necromancer;


public class Apple
        extends CustomCard {
    public static final String ID = "shadowverse:Apple";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Apple");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Apple.png";
    public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString("shadowverse:applyEffect")).TEXT;

    public Apple() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, Necromancer.Enums.COLOR_PURPLE, CardRarity.UNCOMMON, CardTarget.NONE);
        this.isEthereal = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeBaseCost(0);
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SelectCardsInHandAction(1, TEXT[0], false, true, card -> {
            return (CardLibrary.getCard(card.cardID)!= null && CardLibrary.getCard(card.cardID).type == CardType.ATTACK) || card.type == CardType.ATTACK;
        }, abstractCards -> {
            for (AbstractCard c : abstractCards) {
                CardModifierManager.addModifier(c, new AppleMod());
                c.superFlash();
            }
        }));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new Apple();
    }
}

