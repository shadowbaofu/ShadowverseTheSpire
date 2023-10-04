package shadowverse.cards.Witch.Default;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.actions.unique.AttackFromDeckToHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.AbstractRightClickCard2;
import shadowverse.cards.Neutral.Temp.ScornerOfTruths;
import shadowverse.characters.Witchcraft;

import java.util.ArrayList;


public class SeekerOfTruths extends AbstractRightClickCard2 {
    public static final String ID = "shadowverse:SeekerOfTruths";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:SeekerOfTruths");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/SeekerOfTruths.png";
    private boolean hasFusion = false;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private ArrayList<String> box = new ArrayList<>();

    public SeekerOfTruths() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Witchcraft.Enums.COLOR_BLUE, CardRarity.RARE, CardTarget.SELF);
        this.baseBlock = 6;
        this.cardsToPreview = new ScornerOfTruths();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public void applyPowers() {
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + box.size();
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    @Override
    protected void onRightClick() {
        if (!this.hasFusion && AbstractDungeon.player!=null){
            addToBot(new SelectCardsInHandAction(9,TEXT[0],true,true, card -> {
                return CardLibrary.getCard(card.cardID) != null && card.color == Witchcraft.Enums.COLOR_BLUE && card != this;
            }, abstractCards -> {
                if (abstractCards.size()>0){
                    this.hasFusion = true;
                }
                for(AbstractCard c:abstractCards){
                    addToBot(new ExhaustSpecificCardAction(c,AbstractDungeon.player.hand));
                    if (!this.box.contains(c.cardID)){
                        box.add(c.cardID);
                    }
                }
                if (box.size()>4){
                    AbstractCard toAdd = this.cardsToPreview.makeStatEquivalentCopy();
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            AbstractDungeon.player.hand.removeCard(SeekerOfTruths.this);
                            this.isDone = true;
                        }
                    });
                    addToBot(new MakeTempCardInHandAction(toAdd));
                    toAdd.superFlash();
                }
            }));
        }
    }

    @Override
    public void atTurnStart(){
        hasFusion = false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("SeekerOfTruths"));
        addToBot(new GainBlockAction(p,this.block));
        int amount = 1;
        if (box.size()>0){
            amount++;
        }
        addToBot(new MoveCardsAction(p.hand,p.drawPile, card ->
                card.color == Witchcraft.Enums.COLOR_BLUE,amount));
    }


    public AbstractCard makeCopy() {
        return new SeekerOfTruths();
    }
}
