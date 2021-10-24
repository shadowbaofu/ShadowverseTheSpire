package shadowverse.cards.Uncommon;


import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import shadowverse.Shadowverse;
import shadowverse.action.DestroyAction;
import shadowverse.action.ExhaustSpecificGroupAndDrawAction;
import shadowverse.cards.Temp.Miriam_Acc;
import shadowverse.cards.Temp.OmniscientKaiser_Acc;
import shadowverse.cards.Temp.ParadigmShift;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Nemesis;
import shadowverse.powers.MiriamPower;
import shadowverse.stance.Resonance;

import java.util.ArrayList;


public class Miriam extends CustomCard {
    public static final String ID = "shadowverse:Miriam";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Miriam");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Miriam.png";
    public boolean doubleCheck = false;

    public Miriam() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = 12;
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(AbstractShadowversePlayer.Enums.ACCELERATE);
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
            upgradeMagicNumber(2);
        }
    }


    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (AbstractDungeon.player.hasPower("Burst") || AbstractDungeon.player.hasPower("Double Tap") || AbstractDungeon.player.hasPower("Amplified")) {
            doubleCheck = true;
            if (EnergyPanel.getCurrentEnergy() - c.costForTurn < this.cost) {
                setCostForTurn(0);
                this.type = CardType.SKILL;
                applyPowers();
            }
        } else {
            if (doubleCheck) {
                doubleCheck = false;
            } else {
                if (EnergyPanel.getCurrentEnergy() - c.costForTurn < this.cost) {
                    setCostForTurn(0);
                    this.type = CardType.SKILL;
                    applyPowers();
                }
            }
        }
    }

    public void triggerOnGainEnergy(int e, boolean dueToCard) {
        if (EnergyPanel.getCurrentEnergy() >= 2 && this.type != CardType.ATTACK) {
            resetAttributes();
            this.type = CardType.ATTACK;
            applyPowers();
        }
    }

    public void triggerWhenDrawn() {
        if (Shadowverse.Accelerate((AbstractCard) this)) {
            super.triggerWhenDrawn();
            setCostForTurn(0);
            this.type = CardType.SKILL;
        } else {
            this.type = CardType.ATTACK;
        }
        applyPowers();
    }

    @Override
    public void atTurnStart() {
        if (AbstractDungeon.player.hand.group.contains(this)) {
            if (EnergyPanel.getCurrentEnergy() < 2) {
                setCostForTurn(0);
                this.type = CardType.SKILL;
            } else {
                resetAttributes();
                this.type = CardType.ATTACK;
            }
            applyPowers();
        }
    }

    public void onMoveToDiscard() {
        resetAttributes();
        this.type = CardType.ATTACK;
        applyPowers();
    }

    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        if (Shadowverse.Accelerate((AbstractCard) this) && this.type == CardType.SKILL) {
            addToBot((AbstractGameAction) new SFXAction("Miriam_Acc"));
            addToBot((AbstractGameAction)new DestroyAction(1,(AbstractGameAction)new MakeTempCardInHandAction((AbstractCard)new ParadigmShift())));
        } else {
            addToBot((AbstractGameAction) new SFXAction("Miriam"));
            addToBot((AbstractGameAction) new GainBlockAction(p, p, this.block));
            addToBot((AbstractGameAction)new ApplyPowerAction(p,p,(AbstractPower)new MiriamPower(p,this.magicNumber),this.magicNumber));
            if (p.stance.ID.equals(Resonance.STANCE_ID)){
                addToBot((AbstractGameAction)new GainEnergyAction(1));
            }
        }
    }

    public AbstractCard makeSameInstanceOf() {
        AbstractCard card = null;
        if (Shadowverse.Accelerate((AbstractCard) this) && this.type == CardType.SKILL) {
            card = (new Miriam_Acc()).makeStatEquivalentCopy();
            card.uuid = (new Miriam_Acc()).uuid;
        } else {
            card = makeStatEquivalentCopy();
            card.uuid = this.uuid;
        }
        return card;
    }

    public AbstractCard makeCopy() {
        return (AbstractCard) new Miriam();
    }
}
