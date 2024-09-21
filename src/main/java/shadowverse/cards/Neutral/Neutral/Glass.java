package shadowverse.cards.Neutral.Neutral;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shadowverse.cards.AbstractNeutralCard;
import shadowverse.powers.FlameNGlassPower;
import shadowverse.powers.GlassPower;

public class Glass extends AbstractNeutralCard {
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Glass");
    public static final String ID = "shadowverse:Glass";
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Glass.png";

    public Glass() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeMagicNumber(1);
            upgradeName();
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("Glass"));
        if (abstractPlayer.hasPower("shadowverse:FlamePower")) {
            addToTop(new ReducePowerAction(abstractPlayer, abstractPlayer, "shadowverse:FlamePower", 2));
            addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new FlameNGlassPower(abstractPlayer, 1), 1));
        } else
            addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new GlassPower(abstractPlayer, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new Glass();
    }
}
