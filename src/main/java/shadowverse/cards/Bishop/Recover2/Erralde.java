package shadowverse.cards.Bishop.Recover2;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.HeartBuffEffect;
import shadowverse.cards.AbstractAccelerateCard;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Bishop;
import shadowverse.powers.ErraldePower;

public class Erralde
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:Erralde";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Erralde");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Erralde.png";

    public Erralde() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.POWER, Bishop.Enums.COLOR_WHITE, CardRarity.RARE, CardTarget.SELF, 0, CardType.SKILL);
        this.isEthereal = true;
        this.tags.add(AbstractShadowversePlayer.Enums.CONDEMNED);
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.isEthereal = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("Erralde"));
        addToBot(new VFXAction(new HeartBuffEffect(p.hb.cX, p.hb.cY)));
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if (mo != null && !mo.isDeadOrEscaped()) {
                addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, 2, false), 2, true, AbstractGameAction.AttackEffect.NONE));
                addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, 2, false), 2, true, AbstractGameAction.AttackEffect.NONE));
            }
        }
        addToBot(new ApplyPowerAction(p, p, new ErraldePower(p)));
        this.exhaust = true;
        p.hand.removeCard(this);
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("Erralde_Acc"));
        addToBot(new HealAction(p, p, 2));
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.stream().anyMatch(
                card -> (card.hasTag(AbstractShadowversePlayer.Enums.CONDEMNED) && card != this) ||
                        (card.cost > 1 && card.type == AbstractCard.CardType.ATTACK))) {
            addToBot(new DrawCardAction(1));
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new Erralde();
    }
}


