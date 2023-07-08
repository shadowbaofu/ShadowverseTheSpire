package shadowverse.cards.Nemesis.Resonance;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shadowverse.characters.Nemesis;
import shadowverse.powers.RosaPower;

public class Rosa
        extends CustomCard {
    public static final String ID = "shadowverse:Rosa";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Rosa");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Rosa.png";
    public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString("shadowverse:applyEffect")).TEXT;

    public Rosa() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = 6;
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


    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("Rosa"));
        addToBot(new GainBlockAction(p,this.block));
        addToBot( new ApplyPowerAction( p,  p,  new RosaPower( p, this.magicNumber), this.magicNumber));
        addToBot(new SelectCardsInHandAction(1,TEXT[0],false,false, card -> {
            return true;
        }, abstractCards ->{
            for (AbstractCard c:abstractCards){
                addToBot(new ExhaustSpecificCardAction(c,p.hand));
                addToBot(new MakeTempCardInDrawPileAction(c.makeStatEquivalentCopy(),1,true,true,false));
            }
        } ));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new Rosa();
    }
}


