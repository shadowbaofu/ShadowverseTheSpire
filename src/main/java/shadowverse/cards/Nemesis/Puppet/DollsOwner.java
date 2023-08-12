package shadowverse.cards.Nemesis.Puppet;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.Neutral.Temp.Puppet;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Nemesis;

public class DollsOwner
        extends CustomCard {
    public static final String ID = "shadowverse:DollsOwner";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:DollsOwner");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/DollsOwner.png";

    public DollsOwner() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.COMMON, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.baseBlock = 5;
        this.cardsToPreview = new Puppet();
        this.tags.add(AbstractShadowversePlayer.Enums.LASTWORD);
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }

    @Override
    public void triggerOnExhaust() {
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(),this.magicNumber));
    }


    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("DollsOwner"));
        addToBot(new GainBlockAction(p,this.block));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(),this.magicNumber));
    }


    public AbstractCard makeCopy() {
        return  new DollsOwner();
    }
}


