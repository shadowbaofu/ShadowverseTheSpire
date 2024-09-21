package shadowverse.cards.Neutral.Temp;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.relics.KagemitsuSword;

public class HarbingerOfTheDaylight extends CustomCard {
    public static final String ID = "shadowverse:HarbingerOfTheDaylight";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/HarbingerOfTheDaylight.png";


    public HarbingerOfTheDaylight() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.baseDamage = 4;
        this.tags.add(AbstractShadowversePlayer.Enums.EVOLVEABLE);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeDamage(3);
            initializeDescription();
        }
    }

    @Override
    public void triggerWhenDrawn() {
        if (!this.upgraded)
            this.upgrade();
    }

    public void degrade() {
        if (this.upgraded) {
            degradeName();
            this.rawDescription = cardStrings.DESCRIPTION;
            initializeDescription();
            this.superFlash();
            this.applyPowers();
        }
    }
    public void degradeName() {
        --this.timesUpgraded;
        this.upgraded = false;
        this.name = NAME;
        this.initializeTitle();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("HarbingerOfTheNight"));
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        if (this.upgraded) {;
            addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.magicNumber, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            this.degrade();
            if(abstractPlayer.hasRelic(KagemitsuSword.ID) || abstractPlayer.hasPower("shadowverse:SeofonPower")){
                this.upgrade();
            }
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new HarbingerOfTheDaylight();
    }
}


