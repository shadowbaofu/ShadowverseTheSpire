package shadowverse.cards.Neutral.Temp;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.AbstractRightClickCard2;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Necromancer;


public class ForbiddenPower extends AbstractRightClickCard2 {
    public static final String ID = "shadowverse:ForbiddenPower";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:ForbiddenPower");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/ForbiddenArt.png";
    private boolean hasFusion = false;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
    public static final String[] TEXT = uiStrings.TEXT;

    public ForbiddenPower() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, Necromancer.Enums.COLOR_PURPLE, CardRarity.SPECIAL, CardTarget.ALL_ENEMY);
        this.baseDamage = 6;
        this.baseMagicNumber = this.baseDamage;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(AbstractShadowversePlayer.Enums.MACHINE);
        this.selfRetain = true;
        this.exhaust = true;
        this.isMultiDamage = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
            this.baseMagicNumber = this.baseDamage;
            this.magicNumber = this.baseMagicNumber;
        }
    }

    @Override
    protected void onRightClick() {
        if (!this.hasFusion && AbstractDungeon.player != null) {
            addToBot(new SelectCardsInHandAction(9, TEXT[0], true, true, card -> {
                return card.hasTag(AbstractShadowversePlayer.Enums.MACHINE) && card != this;
            }, abstractCards -> {
                if (abstractCards.size() > 0) {
                    this.hasFusion = true;
                }
                for (AbstractCard c : abstractCards) {
                    addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                }
            }));
        }
    }

    @Override
    public void atTurnStart() {
        hasFusion = false;
    }

    public void applyPowers() {
        super.applyPowers();
        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.hasTag(AbstractShadowversePlayer.Enums.MACHINE) && c != this)
                count++;
        }
        int realBaseDamage = this.baseDamage;
        this.baseDamage += count * this.baseDamage;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.hasTag(AbstractShadowversePlayer.Enums.MACHINE) && c != this)
                count++;
        }
        int realBaseDamage = this.baseDamage;
        this.baseDamage += count * this.baseDamage;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("ForbiddenPower"));
        calculateCardDamage(m);
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        if (this.hasFusion) {
            addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        }
    }

    public AbstractCard makeCopy() {
        return new ForbiddenPower();
    }
}
