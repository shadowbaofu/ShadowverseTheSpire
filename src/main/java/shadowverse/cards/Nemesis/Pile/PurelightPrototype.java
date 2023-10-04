package shadowverse.cards.Nemesis.Pile;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;
import com.megacrit.cardcrawl.vfx.combat.MiracleEffect;
import shadowverse.action.DrawPileToHandAction_Tag;
import shadowverse.action.SkullFaneAccAction;
import shadowverse.cards.AbstractRightClickCard2;
import shadowverse.cards.Neutral.Temp.AnalyzeArtifact;
import shadowverse.cards.Neutral.Temp.PurelightExemplar;
import shadowverse.cards.Neutral.Temp.SeraphsOfTheTrueKey;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Bishop;
import shadowverse.characters.Nemesis;

public class PurelightPrototype extends AbstractRightClickCard2 {
    public static final String ID = "shadowverse:PurelightPrototype";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:PurelightPrototype");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/PurelightPrototype.png";
    private boolean hasFusion = false;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
    public static final String[] TEXT = uiStrings.TEXT;

    public PurelightPrototype() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 8;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = new PurelightExemplar();
        this.tags.add(AbstractShadowversePlayer.Enums.ARTIFACT);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("PurelightPrototype"));
        addToBot(new VFXAction(new GrandFinalEffect()));
        addToBot(new DrawPileToHandAction_Tag(this.magicNumber, AbstractShadowversePlayer.Enums.ARTIFACT, null));
    }

    @Override
    public void atTurnStart() {
        hasFusion = false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new PurelightPrototype();
    }

    @Override
    protected void onRightClick() {
        if (!this.hasFusion && AbstractDungeon.player != null) {
            addToBot(new SelectCardsInHandAction(9, TEXT[0], true, true, card -> {
                return CardLibrary.getCard(card.cardID) != null && card.color == Nemesis.Enums.COLOR_SKY && card != this
                        && !card.hasTag(CardTags.STARTER_STRIKE) && !card.hasTag(CardTags.STARTER_DEFEND);
            }, abstractCards -> {
                if (abstractCards.size() > 0) {
                    this.hasFusion = true;
                    int artifactAmount = 0;
                    for (AbstractCard card : AbstractDungeon.player.exhaustPile.group){
                        if (card.hasTag(AbstractShadowversePlayer.Enums.ARTIFACT))
                            artifactAmount++;
                    }
                    if (artifactAmount > 5){
                        AbstractCard toAdd = this.cardsToPreview.makeStatEquivalentCopy();
                        addToBot(new AbstractGameAction() {
                            @Override
                            public void update() {
                                AbstractDungeon.player.hand.removeCard(PurelightPrototype.this);
                                this.isDone = true;
                            }
                        });
                        addToBot(new MakeTempCardInHandAction(toAdd));
                        toAdd.superFlash();
                    }else {
                        addToBot(new MakeTempCardInDrawPileAction(new AnalyzeArtifact(),2,true,true));
                    }
                }
                for (AbstractCard card : abstractCards) {
                    addToBot(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
                }
            }));
        }
    }
}