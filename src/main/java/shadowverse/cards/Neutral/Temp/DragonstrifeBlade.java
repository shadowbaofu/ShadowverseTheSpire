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
import shadowverse.characters.Dragon;
import shadowverse.powers.DragonTagPower;


public class DragonstrifeBlade
        extends CustomCard {
    public static final String ID = "shadowverse:DragonstrifeBlade";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:DragonstrifeBlade");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/DragonstrifeBlade.png";

    public DragonstrifeBlade() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, Dragon.Enums.COLOR_BROWN, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.baseDamage = 9;
        this.exhaust = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("DragonstrifeBlade"));
        int dmg = this.damage;
        if (abstractMonster.hasPower(DragonTagPower.POWER_ID)){
            dmg *= 2;
        }
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,dmg,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new DragonstrifeBlade();
    }
}

