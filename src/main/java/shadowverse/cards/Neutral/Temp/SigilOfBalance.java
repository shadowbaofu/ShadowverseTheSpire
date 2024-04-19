package shadowverse.cards.Neutral.Temp;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shadowverse.characters.Bishop;


public class SigilOfBalance extends CustomCard {
    public static final String ID = "shadowverse:SigilOfBalance";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:SigilOfBalance");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/MarkOfBalance.png";

    public SigilOfBalance() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.SKILL, Bishop.Enums.COLOR_WHITE, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
        this.selfRetain = true;
        this.exhaust = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("SigilOfBalance"));
        addToBot(new HealAction(abstractPlayer, abstractPlayer, this.magicNumber));
        addToBot(new HealAction(abstractMonster, abstractPlayer, this.magicNumber));
        for (AbstractPower pow : abstractPlayer.powers) {
            if (pow.type == AbstractPower.PowerType.DEBUFF) {
                addToBot(new RemoveSpecificPowerAction(pow.owner, abstractPlayer, pow.ID));
            }
        }
        for (AbstractPower pow : abstractMonster.powers) {
            if (pow.type == AbstractPower.PowerType.DEBUFF) {
                addToBot(new RemoveSpecificPowerAction(pow.owner, abstractPlayer, pow.ID));
            }
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new SigilOfBalance();
    }
}

