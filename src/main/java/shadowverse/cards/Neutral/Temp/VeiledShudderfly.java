package shadowverse.cards.Neutral.Temp;



import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import shadowverse.characters.Necromancer;


public class VeiledShudderfly extends CustomCard {
    public static final String ID = "shadowverse:VeiledShudderfly";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/VeiledShudderfly.png";

    public VeiledShudderfly() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.ATTACK, Necromancer.Enums.COLOR_PURPLE, CardRarity.SPECIAL, CardTarget.SELF);
        this.baseBlock = 9;
        this.exhaust = true;
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer,this.block));
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new BlurPower(abstractPlayer,1),1));
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
        return new VeiledShudderfly();
    }
}

