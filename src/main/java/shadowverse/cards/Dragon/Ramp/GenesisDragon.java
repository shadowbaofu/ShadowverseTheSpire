package shadowverse.cards.Dragon.Ramp;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.characters.Dragon;
import shadowverse.powers.OverflowPower;

import java.util.ArrayList;
import java.util.List;

public class GenesisDragon extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:GenesisDragon";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:GenesisDragon");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:GenesisDragon2");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/GenesisDragon.png";
    public static final String IMG_PATH2 = "img/cards/GenesisDragon2.png";
    private boolean branch2 = false;
    public int baseCost;
    public int accCost;
    public CardType baseType;
    public CardType accType;
    public boolean played;

    public GenesisDragon() {
        super(ID, NAME, IMG_PATH, 5, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 50;
        this.accCost = 2;
        this.baseCost = cost;
        this.baseType = type;
        this.accType = CardType.SKILL;
    }


    public void update() {
        super.update();
        if (branch2) {
            if (AbstractDungeon.currMapNode != null && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
                if (this.type == baseType) {
                    this.baseCost = this.costForTurn;
                } else {
                    if (this.cost <= this.accCost) {
                        this.baseCost = this.cost;
                    }
                }
                if (!played) {
                    if (EnergyPanel.getCurrentEnergy() < baseCost) {
                        setCostForTurn(accCost);
                        this.type = accType;
                    } else {
                        if (this.type == accType) {
                            setCostForTurn(baseCost);
                            this.type = baseType;
                        }
                    }
                }
            }else {
                resetAttributes();
                this.type = baseType;
            }
            super.update();
        }
    }

    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        switch (chosenBranch()) {
            case 0:
                addToBot(new SFXAction("GenesisDragon"));
                addToBot(new VFXAction(new WeightyImpactEffect(abstractMonster.hb.cX, abstractMonster.hb.cY)));
                addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
                break;
            case 1:
                if (this.type == accType && this.costForTurn == accCost) {
                    accUse(abstractPlayer, abstractMonster);
                } else {
                    baseUse(abstractPlayer, abstractMonster);
                }
                played = true;
                break;
        }
    }

    public void baseUse(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("GenesisDragon2"));
        addToBot(new VFXAction(new WeightyImpactEffect(abstractMonster.hb.cX, abstractMonster.hb.cY)));
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    public void accUse(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(18, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.POISON));
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new OverflowPower(abstractPlayer, 1)));
        addToBot(new SFXAction("GenesisDragon2_Acc"));
    }


    public void triggerOnGlowCheck() {
        if (EnergyPanel.getCurrentEnergy() < baseCost && this.type == accType) {
            this.glowColor = AbstractCard.GREEN_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        played = false;
    }

    public AbstractCard makeCopy() {
        return (AbstractCard) new GenesisDragon();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++GenesisDragon.this.timesUpgraded;
                GenesisDragon.this.upgraded = true;
                GenesisDragon.this.name = cardStrings.NAME + "+";
                GenesisDragon.this.initializeTitle();
                GenesisDragon.this.baseDamage = 60;
                GenesisDragon.this.upgradedDamage = true;
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++GenesisDragon.this.timesUpgraded;
                GenesisDragon.this.baseDamage = 56;
                GenesisDragon.this.upgradedDamage = true;
                GenesisDragon.this.upgraded = true;
                GenesisDragon.this.textureImg = IMG_PATH2;
                GenesisDragon.this.loadCardImage(IMG_PATH2);
                GenesisDragon.this.name = cardStrings2.NAME;
                GenesisDragon.this.initializeTitle();
                GenesisDragon.this.rawDescription = cardStrings2.DESCRIPTION;
                GenesisDragon.this.initializeDescription();
                GenesisDragon.this.branch2 = true;
                GenesisDragon.this.rarity = CardRarity.RARE;
                GenesisDragon.this.setDisplayRarity(GenesisDragon.this.rarity);
            }
        });
        return list;
    }
}

