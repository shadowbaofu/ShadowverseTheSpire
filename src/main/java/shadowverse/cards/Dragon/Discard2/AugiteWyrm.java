package shadowverse.cards.Dragon.Discard2;


import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import shadowverse.cards.Witch.AbstractAccelerateCard;
import shadowverse.characters.Dragon;
import shadowverse.powers.OverflowPower;


public class AugiteWyrm extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:AugiteWyrm";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:AugiteWyrm");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/AugiteWyrm.png";

    public AugiteWyrm() {
        super(ID, NAME, IMG_PATH, 4, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.COMMON, CardTarget.ALL_ENEMY, 0, CardType.SKILL);
        this.baseDamage = 30;
        this.isMultiDamage = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(4);
        }
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new CleaveEffect()));
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DiscardAction(p, p, 1, false));
        addToBot(new DrawCardAction(1));
        if (p.hasPower(OverflowPower.POWER_ID)) {
            TwoAmountPower power = (TwoAmountPower) p.getPower(OverflowPower.POWER_ID);
            if (power.amount2 > 0) {
                addToBot(new DrawCardAction(1));
            }
        }
    }

    public AbstractCard makeCopy() {
        return (AbstractCard) new AugiteWyrm();
    }
}

