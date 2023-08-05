package shadowverse.cards.Dragon.Ramp;


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import shadowverse.cards.Neutral.Temp.Zoe_Copy;
import shadowverse.cards.Witch.AbstractAccelerateCard;
import shadowverse.characters.Dragon;
import shadowverse.powers.ZoePower;


public class Zoe
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:Zoe";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Zoe");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Zoe.png";

    public Zoe() {
        super(ID, NAME, IMG_PATH, 6, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.RARE, CardTarget.ENEMY, 0, CardType.SKILL);
        this.baseDamage = 54;
        this.cardsToPreview = new Zoe_Copy();
        this.exhaust = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(10);
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void baseUse(AbstractPlayer abstractPlayer, AbstractMonster m) {
        addToBot(new SFXAction("Zoe"));
        addToBot(new VFXAction(new BorderFlashEffect(Color.BLUE, true), 0.7f));
        addToBot(new VFXAction(new ShockWaveEffect(abstractPlayer.hb.cX, abstractPlayer.hb.cY, Color.WHITE, ShockWaveEffect.ShockWaveType.CHAOTIC)));
        addToBot(new LoseHPAction(abstractPlayer, abstractPlayer, abstractPlayer.currentHealth - 1));
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new ZoePower(abstractPlayer, abstractPlayer.currentHealth - 1), abstractPlayer.currentHealth - 1));
        addToBot(new DamageAction(m, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("Zoe_Acc"));
        addToBot(new MakeTempCardInDiscardAction(this.cardsToPreview.makeStatEquivalentCopy(), 1));
        addToBot(new DrawCardAction(1));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new Zoe();
    }
}

