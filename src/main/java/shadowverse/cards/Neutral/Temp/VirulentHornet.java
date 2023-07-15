package shadowverse.cards.Neutral.Temp;



import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import shadowverse.characters.Necromancer;


public class VirulentHornet extends CustomCard {
    public static final String ID = "shadowverse:VirulentHornet";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/VirulentHornet.png";

    public VirulentHornet() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.ATTACK, Necromancer.Enums.COLOR_PURPLE, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.baseMagicNumber = 9;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(3);
        }
    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractMonster,abstractPlayer,new PoisonPower(abstractMonster,abstractPlayer,this.magicNumber),this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new VirulentHornet();
    }
}

