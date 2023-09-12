package shadowverse.cards.Bishop.Default;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import shadowverse.cards.AbstractCrystalizeCard;
import shadowverse.cards.AbstractAccelerateCard;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Bishop;
import shadowverse.orbs.AmuletOrb;
import shadowverse.orbs.Minion;

public class PrimalShipwright
        extends AbstractAccelerateCard implements AbstractCrystalizeCard {
    public static final String ID = "shadowverse:PrimalShipwright";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:PrimalShipwright");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/PrimalShipwright.png";

    public PrimalShipwright() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.ATTACK, Bishop.Enums.COLOR_WHITE, CardRarity.UNCOMMON, CardTarget.ENEMY, 0, CardType.POWER);
        this.baseDamage = 0;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(AbstractShadowversePlayer.Enums.AMULET_FOR_ONECE);
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }


    public int rally() {
        int rally = 0;

        for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisCombat) {
            if (o instanceof Minion) {
                rally++;
            }
        }

        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.type == CardType.ATTACK && !(c.hasTag(CardTags.STRIKE))) {
                rally++;
            }
        }
        return rally;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int realBaseDamage = this.baseDamage;
        this.baseDamage = rally() * this.magicNumber;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage = rally() * this.magicNumber;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }


    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("PrimalShipwright"));
        calculateCardDamage(m);
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("PrimalShipwright_Acc"));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new PrimalShipwright();
    }

    @Override
    public void onStartOfTurn(AmuletOrb paramOrb) {
    }

    @Override
    public void onEvoke(AmuletOrb paramOrb) {
        AbstractCard c = this.makeCopy();
        if (this.upgraded)
            c.upgrade();
        c.setCostForTurn(0);
        c.type = CardType.ATTACK;
        AbstractDungeon.player.hand.addToTop(c);
    }

    @Override
    public void endOfTurn(AmuletOrb paramOrb) {

    }

    @Override
    public void onGainedBlock(int blockAmt, AmuletOrb paramOrb) {
    }

    @Override
    public void onOtherCardPlayed(AbstractCard c, AmuletOrb paramOrb) {
    }

    @Override
    public int onHeal(int healAmount, AmuletOrb paramOrb) {
        return healAmount;
    }

    @Override
    public int returnCountDown() {
        return 3;
    }
}


