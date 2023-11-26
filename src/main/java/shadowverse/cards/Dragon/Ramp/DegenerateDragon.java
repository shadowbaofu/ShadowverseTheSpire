package shadowverse.cards.Dragon.Ramp;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.characters.Dragon;

public class DegenerateDragon extends CustomCard {
    public static final String ID = "shadowverse:DegenerateDragon";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:DegenerateDragon");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/DegenerateDragon.png";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("DualWieldAction").TEXT;

    public DegenerateDragon() {
        super(ID, NAME, IMG_PATH, 5, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.RARE, CardTarget.SELF);
        this.baseBlock = 24;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBlock(8);
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(p,this.block));
        addToBot(new SelectCardsInHandAction(1, TEXT[0], false, true, card -> card.type == CardType.ATTACK && card != this, abstractCards -> {
            for (AbstractCard c : abstractCards) {
                if (c.cost>0){
                    c.freeToPlayOnce = true;
                }
            }
        }));
        addToBot(new SelectCardsInHandAction(1, TEXT[0], false, true, card -> card.type == CardType.SKILL, abstractCards -> {
            for (AbstractCard c : abstractCards) {
                if (c.cost>0){
                    c.freeToPlayOnce = true;
                }
            }
        }));
    }

    @Override
    public AbstractCard makeCopy() {
        return new DegenerateDragon();
    }
}
