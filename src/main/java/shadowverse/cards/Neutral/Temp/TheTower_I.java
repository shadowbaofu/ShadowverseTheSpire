package shadowverse.cards.Neutral.Temp;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import shadowverse.characters.Dragon;

public class TheTower_I extends CustomCard {
    public static final String ID = "shadowverse:TheTower_I";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:TheTower_I");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/TheTower_I.png";

    public TheTower_I() {
        super(ID, NAME, IMG_PATH, -2, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.SPECIAL, CardTarget.ALL_ENEMY);
        this.baseMagicNumber = 16;
        this.magicNumber = this.baseMagicNumber;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(6);
        }
    }



    public void use(AbstractPlayer p, AbstractMonster m) {
        onChoseThisOption();
    }

    public void onChoseThisOption() {
        addToBot(new SFXAction("TheTower_I"));
        int sAmt = 0;
        for (AbstractPower p :AbstractDungeon.player.powers){
            if (p instanceof StrengthPower){
                sAmt = p.amount;
                break;
            }
        }
        addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(this.magicNumber+sAmt, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        addToBot(new MakeTempCardInHandAction(new Burn(),2));
    }

    public AbstractCard makeCopy() {
        return new TheTower_I();
    }
}
