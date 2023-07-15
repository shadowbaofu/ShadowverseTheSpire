package shadowverse.cards.Neutral.Temp;



import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.characters.Necromancer;


public class VengefulMantis extends CustomCard {
    public static final String ID = "shadowverse:VengefulMantis";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/VengefulMantis.png";

    public VengefulMantis() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.ATTACK, Necromancer.Enums.COLOR_PURPLE, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.baseDamage = 12;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(1);
        }
    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c.exhaust && c.type==CardType.ATTACK) {
            flash();
            this.baseDamage += this.magicNumber;
            this.applyPowers();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new VengefulMantis();
    }
}

