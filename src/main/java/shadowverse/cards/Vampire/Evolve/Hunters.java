package shadowverse.cards.Vampire.Evolve;


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
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlameBarrierPower;
import shadowverse.cards.AbstractRightClickCard2;
import shadowverse.cards.Neutral.Status.EvolutionPoint;
import shadowverse.cards.Neutral.Temp.Companions;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Vampire;

public class Hunters extends AbstractRightClickCard2 {
    public static final String ID = "shadowverse:Hunters";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Hunters");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Hunters.png";
    private boolean hasFusion = false;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
    public static final String[] TEXT = uiStrings.TEXT;

    public Hunters() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Vampire.Enums.COLOR_SCARLET, CardRarity.RARE, CardTarget.SELF);
        this.baseBlock = 8;
        this.cardsToPreview = new Companions();
        this.tags.add(AbstractShadowversePlayer.Enums.EVOLVEABLE);
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public void degrade() {
        if (this.upgraded) {
            degradeName();
            this.rawDescription = cardStrings.DESCRIPTION;
            initializeDescription();
            this.superFlash();
            this.applyPowers();
        }
    }

    public void degradeName() {
        --this.timesUpgraded;
        this.upgraded = false;
        this.name = NAME;
        this.initializeTitle();
    }

    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("Hunters"));
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new FlameBarrierPower(abstractPlayer,this.upgraded?3:1),this.upgraded?3:1));
        addToBot(new GainBlockAction(abstractPlayer,this.block));
        if (this.upgraded)
            degrade();
    }


    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c instanceof EvolutionPoint && !this.upgraded) {
            this.upgrade();
            addToBot(new SFXAction("Hunters_Eff"));
            addToBot(new MakeTempCardInHandAction(new EvolutionPoint()));
        }
    }
    public AbstractCard makeCopy() {
        return (AbstractCard) new Hunters();
    }

    @Override
    public void atTurnStart(){
        hasFusion = false;
    }

    @Override
    protected void onRightClick() {
        if (!this.hasFusion && AbstractDungeon.player!=null){
            int amount = 0;
            for (AbstractCard c : AbstractDungeon.player.exhaustPile.group){
                if (c instanceof EvolutionPoint)
                    amount++;
            }
            if (amount >= 5){
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
                                AbstractDungeon.player.hand.removeCard(Hunters.this);
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
}

