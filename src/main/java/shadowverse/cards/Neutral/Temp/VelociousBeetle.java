package shadowverse.cards.Neutral.Temp;



import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.characters.Necromancer;


public class VelociousBeetle extends CustomCard {
    public static final String ID = "shadowverse:VelociousBeetle";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/VelociousBeetle.png";

    public VelociousBeetle() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.ATTACK, Necromancer.Enums.COLOR_PURPLE, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.baseDamage = 8;
        this.exhaust = true;
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
        }
    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new VampireDamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }


    @Override
    public AbstractCard makeCopy() {
        return new VelociousBeetle();
    }
}

