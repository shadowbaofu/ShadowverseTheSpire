package shadowverse.cards.Elf.Left;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import shadowverse.cards.Neutral.Temp.Fairy;
import shadowverse.characters.Elf;


public class NatureCorroded
        extends CustomCard {
    public static final String ID = "shadowverse:NatureCorroded";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:NatureCorroded");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/NatureCorroded.png";

    public NatureCorroded() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.SKILL, Elf.Enums.COLOR_GREEN, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = (AbstractCard) new Fairy();
        this.exhaust = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c = this.cardsToPreview.makeStatEquivalentCopy();
        addToBot(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false), this.magicNumber));
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
        addToBot(new MakeTempCardInHandAction(c, 1));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new NatureCorroded();
    }
}

