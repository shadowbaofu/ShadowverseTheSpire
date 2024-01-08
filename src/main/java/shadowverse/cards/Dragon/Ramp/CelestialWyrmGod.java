package shadowverse.cards.Dragon.Ramp;


import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;
import shadowverse.characters.Dragon;
import shadowverse.powers.OverflowPower;


public class CelestialWyrmGod extends CustomCard {
    public static final String ID = "shadowverse:CelestialWyrmGod";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:CelestialWyrmGod");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/CelestialWyrmGod.png";
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
    public static final String[] TEXT = uiStrings.TEXT;

    public CelestialWyrmGod() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.RARE, CardTarget.ENEMY);
        this.baseBlock = 18;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(6);
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("CelestialWyrmGod"));
        addToBot(new GainBlockAction(abstractPlayer, abstractPlayer, this.block));
        addToBot(new ApplyPowerAction(abstractMonster,abstractPlayer,new VulnerablePower(abstractMonster,2,false),2));
        addToBot(new ApplyPowerAction(abstractMonster,abstractPlayer,new WeakPower(abstractMonster,2,false),2));
        AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
        if (abstractPlayer.hasPower(OverflowPower.POWER_ID)) {
            TwoAmountPower p = (TwoAmountPower) abstractPlayer.getPower(OverflowPower.POWER_ID);
            if (p.amount2 > 1) {
                for (AbstractCard c : abstractPlayer.drawPile.group) {
                    if (CardLibrary.getCard(c.cardID) != null) {
                        if ((CardLibrary.getCard(c.cardID).cost) < 3) {
                            addToBot(new ExhaustSpecificCardAction(c, abstractPlayer.drawPile));
                        }
                    } else {
                        if (c.cost < 3) {
                            addToBot(new ExhaustSpecificCardAction(c, abstractPlayer.drawPile));
                        }
                    }
                }
                for (AbstractCard card : abstractPlayer.hand.group) {
                    int cost = 0;
                    if (CardLibrary.getCard(card.cardID) != null) {
                        cost = CardLibrary.getCard(card.cardID).cost;
                    } else {
                        cost = card.cost;
                    }
                    cost = (int) Math.ceil((double) cost / 2);
                    if (card.costForTurn > 0 && card.type == CardType.ATTACK && card.color == Dragon.Enums.COLOR_BROWN) {
                        card.setCostForTurn(cost);
                    }
                }
            }
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new CelestialWyrmGod();
    }
}

