package shadowverse.cards.Dragon.Buff;


import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowverse.cards.AbstractAccelerateCard;
import shadowverse.characters.Dragon;
import shadowverse.powers.OverflowPower;


public class PropheticDragon
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:PropheticDragon";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:PropheticDragon");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/PropheticDragon.png";

    public PropheticDragon() {
        super(ID, NAME, IMG_PATH, 4, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.UNCOMMON, CardTarget.SELF, 0, CardType.SKILL);
        this.baseBlock = 10;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(2);
        }
    }

    @Override
    public void update() {
        super.update();
        if (AbstractDungeon.currMapNode != null && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
            if (this.type == baseType) {
                this.baseCost = this.costForTurn;
            } else {
                if (this.cost <= this.accCost) {
                    this.baseCost = this.cost;
                }
            }
            if (!played) {
                if (EnergyPanel.getCurrentEnergy() < baseCost && AbstractDungeon.player.hasPower(DexterityPower.POWER_ID) && AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount >= 2) {
                    setCostForTurn(accCost);
                    this.type = accType;
                } else {
                    if (this.type == accType) {
                        setCostForTurn(baseCost);
                        this.type = baseType;
                    }
                }
            }
        }
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new GainBlockAction(p, this.block));
    }

    @Override
    public void accUse(AbstractPlayer abstractPlayer, AbstractMonster m) {
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new OverflowPower(abstractPlayer, 1)));
        if (abstractPlayer.hasPower(OverflowPower.POWER_ID)) {
            TwoAmountPower p = (TwoAmountPower) abstractPlayer.getPower(OverflowPower.POWER_ID);
            if (p.amount2 > 0) {
                addToBot(new DrawCardAction(1));
            }
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new PropheticDragon();
    }
}

