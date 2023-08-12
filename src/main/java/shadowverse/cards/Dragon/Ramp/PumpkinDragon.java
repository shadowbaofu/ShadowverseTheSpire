package shadowverse.cards.Dragon.Ramp;


import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.AbstractAccelerateCard;
import shadowverse.characters.Dragon;


public class PumpkinDragon extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:PumpkinDragon";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:PumpkinDragon");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/PumpkinDragon.png";

    public PumpkinDragon() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.COMMON, CardTarget.SELF, 1, CardType.SKILL);
        this.baseBlock = 20;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(5);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new DrawCardAction(this.magicNumber));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(this.magicNumber));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new PumpkinDragon();
    }
}

