package shadowverse.cards.Bishop.Amulet2;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import shadowverse.cards.AbstractAmuletCard;
import shadowverse.cards.AbstractCrystalizeCard;
import shadowverse.cards.AbstractAccelerateCard;
import shadowverse.characters.Bishop;
import shadowverse.orbs.AmuletOrb;

public class HolyLightningBird
        extends AbstractAccelerateCard implements AbstractCrystalizeCard {
    public static final String ID = "shadowverse:HolyLightningBird";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:HolyLightningBird");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/HolyLightningBird.png";

    public HolyLightningBird() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Bishop.Enums.COLOR_WHITE, CardRarity.RARE, CardTarget.ENEMY, 0, CardType.POWER);
        this.baseDamage = 20;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(8);
            upgradeMagicNumber(1);
        }
    }


    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        if (!m.isDeadOrEscaped())
            addToBot(new VFXAction(new LightningEffect(m.drawX, m.drawY), 0.05F));
        int count = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c instanceof AbstractAmuletCard || (c instanceof AbstractCrystalizeCard && c.type == CardType.POWER)) {
                count++;
            }
        }
        if (count >= 5) {
            addToBot(new GainEnergyAction(1));
        }
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {

    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new HolyLightningBird();
    }

    @Override
    public void onStartOfTurn(AmuletOrb paramOrb) {

    }

    @Override
    public void onEvoke(AmuletOrb paramOrb) {
        addToBot(new GainEnergyAction(this.magicNumber));
        played = false;
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
        return 1;
    }
}


