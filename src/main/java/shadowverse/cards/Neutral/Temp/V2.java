package shadowverse.cards.Neutral.Temp;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.AbstractVehicleCard;
import shadowverse.characters.Royal;


public class V2
        extends AbstractVehicleCard {
    public static final String ID = "shadowverse:V2";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:V2");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/V.png";

    public V2() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Royal.Enums.COLOR_YELLOW, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.baseDamage = 12;
        this.selfRetain = true;
        this.predicate = card -> card.type == CardType.ATTACK && card.costForTurn <= 2;
        this.exhaust = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(4);
        }
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse)
            return false;
        if (!this.maneuver) {
            canUse = false;
        }
        return canUse;
    }

    public void triggerOnGlowCheck() {
        boolean glow = true;
        if (!this.maneuver) {
            glow = false;
        }
        if (glow) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c.costForTurn <= 2 && c.type == CardType.ATTACK && !this.maneuver) {
            this.maneuver = true;
            flash();
            addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
            AbstractCard toReturn = c.makeStatEquivalentCopy();
            c.upgrade();
            this.cardsToPreview = c;
            applyPowers();
        }
    }

    @Override
    public void triggerOnExhaust() {
        if (this.cardsToPreview != null) {
            AbstractCard c = this.cardsToPreview.makeStatEquivalentCopy();
            c.setCostForTurn(0);
            addToBot(new MakeTempCardInHandAction(c));
            this.cardsToPreview = null;
            applyPowers();
            this.maneuver = false;
        }
    }

    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new V2();
    }
}

