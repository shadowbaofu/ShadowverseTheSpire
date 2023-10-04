package shadowverse.cards.Bishop.Recover1;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shadowverse.characters.Bishop;
import shadowverse.powers.TanzaniteConvictorPower;


public class TanzaniteConvictor extends CustomCard {
    public static final String ID = "shadowverse:TanzaniteConvictor";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:TanzaniteConvictor");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/TanzaniteConvictor.png";

    public TanzaniteConvictor() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Bishop.Enums.COLOR_WHITE, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 8;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(2);
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new TanzaniteConvictorPower(abstractPlayer)));
    }

    public void applyPowers() {
        AbstractPower strength = AbstractDungeon.player.getPower("Strength");
        if (strength != null)
            strength.amount *= this.magicNumber;
        super.applyPowers();
        if (strength != null)
            strength.amount /= this.magicNumber;
    }

    public void calculateCardDamage(AbstractMonster mo) {
        AbstractPower strength = AbstractDungeon.player.getPower("Strength");
        if (strength != null)
            strength.amount *= this.magicNumber;
        super.calculateCardDamage(mo);
        if (strength != null)
            strength.amount /= this.magicNumber;
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new TanzaniteConvictor();
    }
}

