package shadowverse.cards.Necromancer.Necromancy;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import shadowverse.action.DestroyAction;
import shadowverse.cards.Witch.AbstractAccelerateCard;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Necromancer;
import shadowverse.powers.Cemetery;


public class Krampus extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:Krampus";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Krampus");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Krampus.png";

    public Krampus() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.ATTACK, Necromancer.Enums.COLOR_PURPLE, CardRarity.COMMON, CardTarget.SELF_AND_ENEMY, 0, CardType.SKILL);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.baseDamage = 20;
        this.tags.add(AbstractShadowversePlayer.Enums.LASTWORD);
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(4);
            upgradeMagicNumber(1);
        }
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse)
            return false;
        if (this.type == baseType)
            return canUse;
        boolean hasAttack = false;
        for (AbstractCard c : p.discardPile.group) {
            if (c.type == CardType.ATTACK)
                hasAttack = true;
        }
        if (!hasAttack) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            canUse = false;
        }
        return canUse;
    }

    @Override
    public void triggerOnExhaust() {
        addToBot(new DrawCardAction(1));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Cemetery(AbstractDungeon.player, 2), 2));
        AbstractMonster mo = AbstractDungeon.getCurrRoom().monsters.getRandomMonster();
        if (mo != null && !mo.isDeadOrEscaped()) {
            addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new WeakPower(mo, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        }
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Cemetery(AbstractDungeon.player, 2), 2));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new DrawCardAction(1));
        AbstractMonster mo = AbstractDungeon.getCurrRoom().monsters.getRandomMonster();
        if (!mo.isDeadOrEscaped() && !mo.halfDead) {
            addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new WeakPower(mo, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        }
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Cemetery(AbstractDungeon.player, 2), 2));
        addToBot(new DamageRandomEnemyAction(new DamageInfo(p, this.magicNumber, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new DestroyAction(1, new DrawCardAction(1)));
    }


    public AbstractCard makeCopy() {
        return new Krampus();
    }
}
