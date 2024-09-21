package shadowverse.cards.Neutral.Temp;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.Neutral.Neutral.HealingAngel;
import shadowverse.characters.AbstractShadowversePlayer;

public class DarkHealingAngel extends CustomCard {
    public static final String ID = "shadowverse:DarkHealingAngel";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/DarkHealingAngel.png";
    public boolean triggered;


    public DarkHealingAngel() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
        this.baseBlock = 5;
        this.magicNumber = this.baseMagicNumber = 2;
        this.exhaust = true;
        this.tags.add(AbstractShadowversePlayer.Enums.DARK_ANGEL);
        this.cardsToPreview = new HealingAngel();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
            this.cardsToPreview.upgrade();
        }
    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("HealingAngel"));
        addToBot(new GainBlockAction(abstractPlayer, this.block));
        addToBot(new HealAction(abstractPlayer, abstractPlayer, this.magicNumber));
        addToBot(new DamageAllEnemiesAction(abstractPlayer, DamageInfo.createDamageMatrix(this.magicNumber, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));
        addToBot(new DrawCardAction(this.magicNumber));
    }


    @Override
    public AbstractCard makeCopy() {
        return new DarkHealingAngel();
    }
}

