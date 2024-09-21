package shadowverse.cards.Neutral.Temp;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.powers.DarkAngelPower;

public class AngelOfBrightness extends CustomCard {
    public static final String ID = "shadowverse:AngelOfBrightness";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/AngelOfBrightness.png";


    public AngelOfBrightness() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.baseDamage = 12;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeDamage(3);
            upgradeMagicNumber(1);
        }
    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("AngelOfDarkness"));
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new DarkAngelPower(abstractPlayer,this.magicNumber)));
    }


    @Override
    public AbstractCard makeCopy() {
        return new AngelOfBrightness();
    }
}


