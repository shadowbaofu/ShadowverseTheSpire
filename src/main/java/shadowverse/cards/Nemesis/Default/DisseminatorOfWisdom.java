package shadowverse.cards.Nemesis.Default;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Nemesis;

public class DisseminatorOfWisdom
        extends CustomCard {
    public static final String ID = "shadowverse:DisseminatorOfWisdom";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:DisseminatorOfWisdom");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/DisseminatorOfWisdom.png";

    public DisseminatorOfWisdom() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = 8;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(4);
            upgradeMagicNumber(1);
        }
    }


    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("DisseminatorOfWisdom"));
        addToBot(new GainBlockAction(p,this.block));
        addToBot(new DrawCardAction(this.magicNumber));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard c : p.hand.group){
                    if (c.hasTag(AbstractShadowversePlayer.Enums.ARTIFACT)){
                        addToBot(new ReduceCostForTurnAction(c,1));
                    }
                }
                this.isDone = true;
            }
        });
    }


    public AbstractCard makeCopy() {
        return  new DisseminatorOfWisdom();
    }
}


