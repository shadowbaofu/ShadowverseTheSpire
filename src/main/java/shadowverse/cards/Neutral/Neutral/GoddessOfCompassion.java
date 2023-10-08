package shadowverse.cards.Neutral.Neutral;


import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BerserkPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import shadowverse.action.DrawPileToHandAction_Tag;
import shadowverse.action.NecromanceAction;
import shadowverse.cards.AbstractRightClickCard;
import shadowverse.cards.Necromancer.LastWords.GraveVoidknight;
import shadowverse.cards.Neutral.Temp.GoddessOfCondemnation;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Necromancer;
import shadowverse.powers.OverflowPower;

import java.util.ArrayList;


public class GoddessOfCompassion
        extends AbstractRightClickCard {
    public static final String ID = "shadowverse:GoddessOfCompassion";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:GoddessOfCompassion");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/GoddessOfCompassion.png";
    private boolean hasFusion = false;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private ArrayList<String> box = new ArrayList<String>();

    public GoddessOfCompassion() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.RARE, CardTarget.ALL);
        this.baseBlock = 18;
        this.cardsToPreview = new GoddessOfCondemnation();
        this.exhaust = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(5);
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("GoddessOfCompassion"));
        addToBot(new GainBlockAction(abstractPlayer, this.block));
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if (mo != null && !mo.isDeadOrEscaped() && !mo.isDying && !mo.halfDead) {
                addToBot(new ApplyPowerAction(mo, abstractPlayer, new StrengthPower(mo, -2), -2, true, AbstractGameAction.AttackEffect.NONE));
                addToBot(new ApplyPowerAction(mo, abstractPlayer, new DexterityPower(mo, -2), -2, true, AbstractGameAction.AttackEffect.NONE));
            }
        }
        if (box.size() > 0){
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new OverflowPower(AbstractDungeon.player,1),1));
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new GoddessOfCompassion();
    }

    public void atTurnStart(){
        hasFusion = false;
    }

    @Override
    protected void onRightClick() {
        if (!this.hasFusion && AbstractDungeon.player!=null){
            addToBot(new SelectCardsInHandAction(9,TEXT[0],true,true, card -> {
                return CardLibrary.getCard(card.cardID) != null  && card != this
                        && (card.color == CardColor.COLORLESS || CardLibrary.getCard(card.cardID).type == CardType.SKILL || CardLibrary.getCard(card.cardID).type == CardType.POWER);
            }, abstractCards -> {
                if (abstractCards.size()>0){
                    this.hasFusion = true;
                }
                for (AbstractCard card : abstractCards){
                    addToBot(new ExhaustSpecificCardAction(card,AbstractDungeon.player.hand));
                    if (!box.contains(card.cardID)){
                        box.add(card.cardID);
                    }
                }
                if (box.size()>2){
                    AbstractCard toAdd = this.cardsToPreview.makeStatEquivalentCopy();
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            AbstractDungeon.player.hand.removeCard(GoddessOfCompassion.this);
                            this.isDone = true;
                        }
                    });
                    addToBot(new MakeTempCardInHandAction(toAdd));
                    toAdd.superFlash();
                }
            }));
        }
    }
}

