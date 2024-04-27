package shadowverse.cards.Witch.Spellboost1;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import shadowverse.action.SpellBoostAction;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Witchcraft;

public class RosyCourtMagician
        extends CustomCard {
    public static final String ID = "shadowverse:RosyCourtMagician";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:RosyCourtMagician");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/RosyCourtMagician.png";
    public static final int BASE_COST = 5;

    public RosyCourtMagician() {
        super(ID, NAME, IMG_PATH, 5, DESCRIPTION, CardType.ATTACK, Witchcraft.Enums.COLOR_BLUE, CardRarity.RARE, CardTarget.SELF);
        this.baseBlock = 24;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(AbstractShadowversePlayer.Enums.SPELL_BOOST);
        this.exhaust = true;
    }


    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c.type == CardType.SKILL) {
            flash();
            addToBot(new SFXAction("spell_boost"));
            addToBot(new ReduceCostAction(this));
        }
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(6);
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("RosyCourtMagician"));
        addToBot(new GainBlockAction(abstractPlayer, abstractPlayer, this.block));
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new ArtifactPower(abstractPlayer, 1), 1));
        addToBot(new HealAction(abstractPlayer,abstractPlayer,3));
        addToBot(new SpellBoostAction(abstractPlayer,this,abstractPlayer.hand.group));
        this.cost = BASE_COST;
    }


    public AbstractCard makeCopy() {
        return new RosyCourtMagician();
    }
}

