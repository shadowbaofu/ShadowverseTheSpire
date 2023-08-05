package shadowverse.cards.Witch.Default;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import shadowverse.cards.Witch.AbstractAccelerateCard;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Witchcraft;
import shadowverse.powers.DarkMagePower;
import shadowverse.powers.EarthEssence;


public class ForbiddenDarkMage
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:ForbiddenDarkMage";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:ForbiddenDarkMage");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/ForbiddenDarkMage.png";
    private boolean played;

    public ForbiddenDarkMage() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.ATTACK, Witchcraft.Enums.COLOR_BLUE, CardRarity.RARE, CardTarget.ENEMY, 0, CardType.POWER);
        this.baseDamage = 7;
        this.tags.add(AbstractShadowversePlayer.Enums.EARTH_RITE);
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    public void applyPowers() {
        AbstractShadowversePlayer w = (AbstractShadowversePlayer) AbstractDungeon.player;
        int realBaseDamage = this.baseDamage;
        this.baseDamage += w.earthCount * this.baseDamage;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }

    public void calculateCardDamage(AbstractMonster mo) {
        AbstractShadowversePlayer w = (AbstractShadowversePlayer) AbstractDungeon.player;
        int realBaseDamage = this.baseDamage;
        this.baseDamage += w.earthCount * this.baseDamage;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }


    @Override
    public void baseUse(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("ForbiddenDarkMage"));
        addToBot(new WaitAction(0.8F));
        addToBot(new VFXAction(new WeightyImpactEffect(abstractMonster.hb.cX, abstractMonster.hb.cY)));
        calculateCardDamage(abstractMonster);
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void accUse(AbstractPlayer abstractPlayer, AbstractMonster m) {
        addToBot(new SFXAction("DarkMagePower"));
        boolean powerExists = false;
        AbstractPower earthEssence = null;
        for (AbstractPower pow : abstractPlayer.powers) {
            if (pow.ID.equals("shadowverse:EarthEssence")) {
                earthEssence = pow;
                powerExists = true;
                break;
            }
        }
        if (powerExists) {
            ((AbstractShadowversePlayer) abstractPlayer).earthCount += earthEssence.amount;
            addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new EarthEssence(abstractPlayer, -earthEssence.amount), -earthEssence.amount));
            addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new DarkMagePower(abstractPlayer, earthEssence.amount), earthEssence.amount));
        }
    }

    public AbstractCard makeCopy() {
        return (AbstractCard) new ForbiddenDarkMage();
    }
}



