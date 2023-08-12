package shadowverse.cards.Nemesis.Default;


import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import shadowverse.Shadowverse;
import shadowverse.action.ExhaustSpecificGroupAndDrawAction;
import shadowverse.cards.AbstractAccelerateCard;
import shadowverse.characters.Nemesis;

import java.util.ArrayList;


public class OmniscientKaiser extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:OmniscientKaiser";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:OmniscientKaiser");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/OmniscientKaiser.png";
    public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString("shadowverse:applyEffect")).TEXT;

    public OmniscientKaiser() {
        super(ID, NAME, IMG_PATH, 5, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.RARE, CardTarget.ALL, 0, CardType.SKILL);
        this.baseBlock = 28;
        this.baseDamage = 56;
        this.isMultiDamage = true;
        this.exhaust = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(7);
            upgradeDamage(7);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void update() {
        if (AbstractDungeon.currMapNode != null && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT &&
                Shadowverse.Accelerate(this)) {
            setCostForTurn(0);
            this.type = CardType.SKILL;
        } else {
            if (this.type == CardType.SKILL) {
                setCostForTurn(5);
                this.type = CardType.ATTACK;
            }
        }
        super.update();
    }

    public void triggerOnGlowCheck() {
        if (Shadowverse.Accelerate(this)) {
            this.glowColor = AbstractCard.GREEN_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }


    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("OmniscientKaiser"));
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new ApplyPowerAction(p, p, new BlurPower(p, 1), 1));
        addToBot(new VFXAction(p, new MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.1F));
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> cardsNotToExhaust = new ArrayList<>();
        addToBot(new SelectCardsInHandAction(1, TEXT[0], false, false, card -> {
            return true;
        }, abstractCards -> {
            for (AbstractCard c : abstractCards) {
                cardsNotToExhaust.add(c);
            }
        }));
        addToBot(new SFXAction("OmniscientKaiser_Acc"));
        addToBot(new ExhaustSpecificGroupAndDrawAction(cardsNotToExhaust, p.hand, this.upgraded));
    }

    public AbstractCard makeCopy() {
        return new OmniscientKaiser();
    }
}
