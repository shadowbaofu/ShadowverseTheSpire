package shadowverse.cards.Neutral.Temp;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import shadowverse.characters.Dragon;

public class TheTower extends CustomCard {
    public static final String ID = "shadowverse:TheTower";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:TheTower");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/TheTower.png";

    public TheTower() {
        super(ID, NAME, IMG_PATH, -2, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.SPECIAL, CardTarget.SELF);
        this.baseBlock = 8;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
            upgradeMagicNumber(1);
        }
    }



    public void use(AbstractPlayer p, AbstractMonster m) {
        onChoseThisOption();
    }

    public void onChoseThisOption() {
        addToBot(new SFXAction("TheTower"));
        int dAmt = 0;
        for (AbstractPower p :AbstractDungeon.player.powers){
            if (p instanceof DexterityPower){
                dAmt = p.amount;
                break;
            }
        }
        addToBot(new GainBlockAction(AbstractDungeon.player,this.baseBlock+dAmt));
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof Burn){
                addToBot(new ExhaustSpecificCardAction(c,AbstractDungeon.player.hand));
                addToBot(new GainBlockAction(AbstractDungeon.player,this.magicNumber));
            }

        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c instanceof Burn){
                addToBot(new ExhaustSpecificCardAction(c,AbstractDungeon.player.drawPile));
                addToBot(new GainBlockAction(AbstractDungeon.player,this.magicNumber));
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c instanceof Burn){
                addToBot(new ExhaustSpecificCardAction(c,AbstractDungeon.player.discardPile));
                addToBot(new GainBlockAction(AbstractDungeon.player,this.magicNumber));
            }
        }
    }

    public AbstractCard makeCopy() {
        return new TheTower();
    }
}
