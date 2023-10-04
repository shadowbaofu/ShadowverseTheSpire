package shadowverse.cards.Neutral.Temp;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;


public class GoddessOfCondemnation
        extends CustomCard {
    public static final String ID = "shadowverse:GoddessOfCondemnation";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:GoddessOfCondemnation");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/GoddessOfCondemnation.png";

    public GoddessOfCondemnation() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ALL);
        this.baseBlock = 30;
        this.exhaust = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(10);
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("GoddessOfCondemnation"));
        addToBot(new GainBlockAction(abstractPlayer, this.block));
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if (mo != null && !mo.isDeadOrEscaped() && !mo.isDying && !mo.halfDead) {
                addToBot(new ApplyPowerAction(mo, abstractPlayer, new StrengthPower(mo, -5), -5, true, AbstractGameAction.AttackEffect.NONE));
                addToBot(new ApplyPowerAction(mo, abstractPlayer, new DexterityPower(mo, -5), -5, true, AbstractGameAction.AttackEffect.NONE));
            }
        }
        AbstractDungeon.player.increaseMaxHp(5, true);
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new GoddessOfCondemnation();
    }
}

