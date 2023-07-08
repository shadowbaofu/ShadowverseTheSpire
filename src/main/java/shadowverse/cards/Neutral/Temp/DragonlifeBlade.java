package shadowverse.cards.Neutral.Temp;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import shadowverse.characters.Dragon;
import shadowverse.powers.DragonTagPower;
import shadowverse.powers.OverflowPower;


public class DragonlifeBlade
        extends CustomCard {
    public static final String ID = "shadowverse:DragonlifeBlade";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:DragonlifeBlade");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/DragonlifeBlade.png";

    public DragonlifeBlade() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, Dragon.Enums.COLOR_BROWN, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.exhaust = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("DragonlifeBlade"));
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new EnergizedPower(abstractPlayer, 1)));
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new OverflowPower(abstractPlayer, 1)));
        addToBot(new ApplyPowerAction(abstractMonster, abstractPlayer, new DragonTagPower(abstractMonster)));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new DragonlifeBlade();
    }
}

