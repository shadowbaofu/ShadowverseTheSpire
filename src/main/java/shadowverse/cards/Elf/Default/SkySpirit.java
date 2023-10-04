package shadowverse.cards.Elf.Default;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
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
import shadowverse.cards.Neutral.Temp.SoaringSpirit;
import shadowverse.characters.Elf;

import java.util.ArrayList;

public class SkySpirit extends AbstractRightClickCard2 {
    public static final String ID = "shadowverse:SkySpirit";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:SkySpirit");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/SkySpirit.png";
    private boolean hasFusion = false;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private ArrayList<String> box = new ArrayList<String>();

    public SkySpirit() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Elf.Enums.COLOR_GREEN, CardRarity.RARE, CardTarget.SELF);
        this.baseBlock = 6;
        this.cardsToPreview = new SoaringSpirit();
        this.selfRetain = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBlock(3);
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
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("SkySpirit"));
        addToBot(new GainBlockAction(p,this.block));
        if (box.size()>0){
            addToBot(new DrawCardAction(2));
            AbstractDungeon.actionManager.cardsPlayedThisTurn.add(this);
            AbstractDungeon.actionManager.cardsPlayedThisTurn.add(this);
        }
    }

    @Override
    public void atTurnStart(){
        hasFusion = false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new SkySpirit();
    }

    @Override
    protected void onRightClick() {
        if (!this.hasFusion && AbstractDungeon.player!=null){
            addToBot(new SelectCardsInHandAction(1,TEXT[0],true,true, card -> {
                return CardLibrary.getCard(card.cardID) != null && card.color== Elf.Enums.COLOR_GREEN && card != this;
            }, abstractCards -> {
                if (abstractCards.size()>0){
                    this.hasFusion = true;
                }
                for (AbstractCard card : abstractCards){
                    if (!box.contains(card.cardID)){
                        box.add(card.cardID);
                    }
                    addToBot(new ExhaustSpecificCardAction(card,AbstractDungeon.player.hand));
                }
                if (box.size()>2){
                    AbstractCard toAdd = this.cardsToPreview.makeStatEquivalentCopy();
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            AbstractDungeon.player.hand.removeCard(SkySpirit.this);
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