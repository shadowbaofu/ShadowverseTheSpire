package shadowverse.cards.Vampire.Self;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import shadowverse.cards.AbstractRightClickCard2;
import shadowverse.cards.Neutral.Temp.HowToTrainYourWolf;
import shadowverse.characters.Elf;
import shadowverse.characters.Vampire;
import shadowverse.powers.EpitaphPower;
import shadowverse.powers.PrisonOfPainPower;
import shadowverse.powers.WrathPower;

public class HowToTrainYourBat extends AbstractRightClickCard2 {
    public static final String ID = "shadowverse:HowToTrainYourBat";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:HowToTrainYourBat");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/HowToTrainYourBat.png";
    private boolean hasFusion = false;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
    public static final String[] TEXT = uiStrings.TEXT;

    public HowToTrainYourBat() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, Vampire.Enums.COLOR_SCARLET, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = new HowToTrainYourWolf();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new LoseHPAction(p,p,1));
        addToBot(new DrawCardAction(this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new PrisonOfPainPower(p, 1), 1));
    }

    @Override
    public void atTurnStart(){
        hasFusion = false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new HowToTrainYourBat();
    }

    @Override
    protected void onRightClick() {
        if (!this.hasFusion && AbstractDungeon.player!=null && (AbstractDungeon.player.hasPower(WrathPower.POWER_ID)||AbstractDungeon.player.hasPower(EpitaphPower.POWER_ID))){
            addToBot(new SelectCardsInHandAction(9,TEXT[0],true,true, card -> {
                return  card != this
                &&!card.hasTag(CardTags.STARTER_STRIKE) && !card.hasTag(CardTags.STARTER_DEFEND);
            }, abstractCards -> {
                if (abstractCards.size()>0){
                    this.hasFusion = true;
                    AbstractCard toAdd = this.cardsToPreview.makeStatEquivalentCopy();
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            AbstractDungeon.player.hand.removeCard(HowToTrainYourBat.this);
                            this.isDone = true;
                        }
                    });
                    addToBot(new MakeTempCardInHandAction(toAdd));
                    toAdd.superFlash();
                }
                for (AbstractCard card : abstractCards){
                    addToBot(new ExhaustSpecificCardAction(card,AbstractDungeon.player.hand));
                }
            }));
        }
    }
}