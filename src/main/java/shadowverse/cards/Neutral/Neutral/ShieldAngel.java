package shadowverse.cards.Neutral.Neutral;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShieldAngel extends CustomCard {
    public static final String ID = "shadowverse:ShieldAngel";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/ShieldAngel.png";
    public boolean triggered;


    public ShieldAngel() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.BASIC, CardTarget.SELF);
        this.baseBlock = 8;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("ShieldAngel"));
        addToBot(new GainBlockAction(abstractPlayer, this.block));
    }


    @Override
    public AbstractCard makeCopy() {
        return new ShieldAngel();
    }
}

