package shadowverse.cards.Elf.Return;

import com.badlogic.gdx.graphics.Color;
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
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import shadowverse.Shadowverse;
import shadowverse.action.BounceAction;
import shadowverse.cards.Witch.AbstractAccelerateCard;
import shadowverse.characters.Elf;


public class ThicketOfGnarledHands
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:ThicketOfGnarledHands";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:ThicketOfGnarledHands");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/ThicketOfGnarledHands.png";

    public ThicketOfGnarledHands() {
        super(ID, NAME, IMG_PATH, 4, DESCRIPTION, CardType.ATTACK, Elf.Enums.COLOR_GREEN, CardRarity.RARE, CardTarget.ALL, 0, CardType.SKILL);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.baseDamage = 6;
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(1);
        }
    }

    public void applyPowers() {
        super.applyPowers();
        int count = AbstractDungeon.actionManager.cardsPlayedThisCombat.size();
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + count;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse)
            return false;
        boolean hasAttack = false;
        if (Shadowverse.Accelerate(this) && this.type == accType) {
            for (AbstractCard c : p.discardPile.group) {
                if (c.type == CardType.ATTACK || c.type == CardType.POWER)
                    hasAttack = true;
            }
            if (!hasAttack) {
                this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
                canUse = false;
            }
        }
        return canUse;
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        int rand = AbstractDungeon.cardRandomRng.random(AbstractDungeon.actionManager.cardsPlayedThisCombat.size());
        int rand2 = AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - rand;
        addToBot(new VFXAction(new ShockWaveEffect(p.hb.cX, p.hb.cY, Color.GREEN, ShockWaveEffect.ShockWaveType.ADDITIVE)));
        addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(rand * this.magicNumber, true), this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE, true));
        addToBot(new GainBlockAction(p, rand2 * this.magicNumber));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new BounceAction(1));
        addToBot(new DamageRandomEnemyAction(new DamageInfo(p, this.damage), AbstractGameAction.AttackEffect.POISON));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new ThicketOfGnarledHands();
    }
}

