package shadowverse.cards.Royal.Evolve;


import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import shadowverse.cards.AbstractAccelerateCard;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Royal;
import shadowverse.relics.KagemitsuSword;


public class WardenOfHonor
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:WardenOfHonor";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:WardenOfHonor");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/WardenOfHonor.png";

    public WardenOfHonor() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Royal.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.SELF, 0, CardType.POWER);
        this.baseBlock = 9;
        this.tags.add(AbstractShadowversePlayer.Enums.CONDEMNED);
        this.exhaust = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void baseUse(AbstractPlayer abstractPlayer, AbstractMonster m) {
        this.upgrade();
        addToBot(new GainBlockAction(abstractPlayer, this.block));
        addToBot(new GainBlockAction(abstractPlayer, this.block));
        AbstractCard copy = this.makeCopy();
        if (abstractPlayer.hasRelic(KagemitsuSword.ID) || abstractPlayer.hasPower("shadowverse:SeofonPower")) {
            copy.upgrade();
        }
        addToBot(new MakeTempCardInDiscardAction(copy, 1));
    }

    @Override
    public void accUse(AbstractPlayer abstractPlayer, AbstractMonster m) {
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new DrawCardNextTurnPower(abstractPlayer, 1), 1));
        addToBot(new MakeTempCardInDiscardAction(this.makeCopy(), 1));
    }

    public AbstractCard makeCopy() {
        return (AbstractCard) new WardenOfHonor();
    }
}

