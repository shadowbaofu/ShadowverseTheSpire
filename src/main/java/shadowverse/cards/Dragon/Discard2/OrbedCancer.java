package shadowverse.cards.Dragon.Discard2;


import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.AbstractAccelerateCard;
import shadowverse.characters.Dragon;


public class OrbedCancer extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:OrbedCancer";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:OrbedCancer");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/OrbedCancer.png";

    public OrbedCancer() {
        super(ID, NAME, IMG_PATH, 5, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.COMMON, CardTarget.SELF, 1, CardType.SKILL);
        this.baseBlock = 24;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(4);
            upgradeMagicNumber(1);
        }
    }


    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new GainBlockAction(p, this.block));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DiscardAction(p, p, 1, false));
        addToBot(new DrawCardAction(this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return (AbstractCard) new OrbedCancer();
    }
}

