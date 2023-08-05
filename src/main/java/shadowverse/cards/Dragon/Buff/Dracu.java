package shadowverse.cards.Dragon.Buff;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import shadowverse.cards.Neutral.Temp.HowlingConflagration;
import shadowverse.cards.Witch.AbstractAccelerateCard;
import shadowverse.characters.Dragon;


public class Dracu
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:Dracu";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Dracu");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Dracu.png";

    public Dracu() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.RARE, CardTarget.ALL_ENEMY,0,CardType.SKILL);
        this.baseDamage = 15;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = new HowlingConflagration();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(5);
            upgradeMagicNumber(2);
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public void applyPowers() {
        int amount = 0;
        if (AbstractDungeon.player.hasPower(DexterityPower.POWER_ID)){
            amount += this.magicNumber * AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;
        }
        int realBaseDamage = this.baseDamage;
        this.baseDamage += amount * this.magicNumber / this.magicNumber;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int amount = 0;
        if (AbstractDungeon.player.hasPower(DexterityPower.POWER_ID)){
            amount += this.magicNumber * AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;
        }
        int realBaseDamage = this.baseDamage;
        this.baseDamage += amount * this.magicNumber / this.magicNumber;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("Dracu"));
        addToBot(new VFXAction(new InflameEffect(p)));
        addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(this.damage, true), this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        if (p.hasPower(DexterityPower.POWER_ID) && p.getPower(DexterityPower.POWER_ID).amount >= 4) {
            addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(20, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("Dracu_Acc"));
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, 1), 1));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new Dracu();
    }
}

