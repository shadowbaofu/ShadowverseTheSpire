package shadowverse.cards.Witch.Academic;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shadowverse.cards.Neutral.Temp.ScorchingCurse;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Witchcraft;
import shadowverse.powers.GingerPower;

public class Ginger
        extends CustomCard {
    public static final String ID = "shadowverse:Ginger";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Ginger");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Ginger.png";

    public Ginger() {
        super(ID, NAME, IMG_PATH, 6, DESCRIPTION, CardType.POWER, Witchcraft.Enums.COLOR_BLUE, CardRarity.RARE, CardTarget.SELF);
        this.cardsToPreview = new ScorchingCurse();
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c.hasTag(AbstractShadowversePlayer.Enums.MYSTERIA) ||
                c.hasTag(AbstractShadowversePlayer.Enums.ARMED) ||
                c.hasTag(AbstractShadowversePlayer.Enums.ARTIFACT) ||
                c.hasTag(AbstractShadowversePlayer.Enums.ACADEMIC) ||
                c.hasTag(AbstractShadowversePlayer.Enums.HERO) ||
                c.hasTag(AbstractShadowversePlayer.Enums.CONDEMNED) ||
                c.hasTag(AbstractShadowversePlayer.Enums.CHESS) ||
                c.hasTag(AbstractShadowversePlayer.Enums.FES) ||
                c.hasTag(AbstractShadowversePlayer.Enums.NATURAL) ||
                c.hasTag(AbstractShadowversePlayer.Enums.MACHINE) ||
                c.hasTag(AbstractShadowversePlayer.Enums.LEVIN)) {
            flash();
            addToBot(new SFXAction("spell_boost"));
            addToBot(new ReduceCostAction(this));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("Ginger"));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, (AbstractPower) new GingerPower(abstractPlayer,this.upgraded)));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new Ginger();
    }
}