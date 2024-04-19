package shadowverse.cards.Neutral.Temp;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Nemesis;
import shadowverse.powers.NeoAlector2Power;


public class NeoAlector2
        extends CustomCard {
    public static final String ID = "shadowverse:NeoAlector2";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:NeoAlector2");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/NeoAlector.png";

    public NeoAlector2() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.SPECIAL, CardTarget.SELF);
        this.baseBlock = 15;
        this.tags.add(AbstractShadowversePlayer.Enums.MACHINE);
        this.exhaust = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(5);
        }
    }


    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("NeoAlector2"));
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new ApplyPowerAction(p,p,new NeoAlector2Power(p)));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new NeoAlector2();
    }
}

