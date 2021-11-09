package charbosses.cards.nemesis;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.general.EnemyEnergizedPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shadowverse.characters.Nemesis;
import shadowverse.powers.AncientAmplifierPower;

public class EnAcceleratium extends AbstractBossCard {
    public static final String ID = "shadowverse:EnAcceleratium";

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:EnAcceleratium");

    public static final String IMG_PATH = "img/cards/Acceleratium.png";

    public EnAcceleratium() {
        super(ID, cardStrings.NAME, IMG_PATH, 1, cardStrings.DESCRIPTION, CardType.POWER, Nemesis.Enums.COLOR_SKY, CardRarity.COMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)m, (AbstractCreature)m, (AbstractPower)new EnemyEnergizedPower((AbstractCreature)m, this.magicNumber), this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy() {
        return (AbstractCard)new EnAcceleratium();
    }
}
