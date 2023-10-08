package shadowverse.cards.Necromancer.LastWords;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
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
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import shadowverse.action.DrawPileToHandAction_Tag;
import shadowverse.action.NecromanceAction;
import shadowverse.cards.AbstractRightClickCard2;
import shadowverse.cards.Neutral.Status.EvolutionPoint;
import shadowverse.cards.Neutral.Temp.GraveAdjudicator;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Necromancer;

import java.util.ArrayList;

public class GraveVoidknight extends AbstractRightClickCard2 {
    public static final String ID = "shadowverse:GraveVoidknight";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:GraveVoidknight");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/GraveVoidknight.png";
    private boolean hasFusion = false;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private ArrayList<String> box = new ArrayList<>();

    public GraveVoidknight() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Necromancer.Enums.COLOR_PURPLE, CardRarity.RARE, CardTarget.ENEMY);
        this.baseBlock = 8;
        this.baseDamage = 8;
        this.cardsToPreview = new GraveAdjudicator();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBlock(3);
            upgradeDamage(3);
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("GraveVoidknight"));
        addToBot(new VFXAction(new BorderFlashEffect(Color.FIREBRICK, true), 0.7f));
        addToBot(new GainBlockAction(p,this.block));
        AbstractGameAction[] gameActions = new AbstractGameAction[]{
                new MakeTempCardInHandAction(new EvolutionPoint()),
                new DamageAction(abstractMonster,new DamageInfo(p,this.damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL)
        };
        addToBot(new NecromanceAction(4,null,gameActions));
    }

    public void applyPowers() {
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + box.size();
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    @Override
    public void atTurnStart(){
        hasFusion = false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new GraveVoidknight();
    }

    @Override
    protected void onRightClick() {
        if (!this.hasFusion && AbstractDungeon.player!=null){
            addToBot(new SelectCardsInHandAction(9,TEXT[0],true,true, card -> {
                return CardLibrary.getCard(card.cardID) != null && card.color== Necromancer.Enums.COLOR_PURPLE && card != this
                        && !card.hasTag(CardTags.STARTER_STRIKE) && !card.hasTag(CardTags.STARTER_DEFEND);
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
                            AbstractDungeon.player.hand.removeCard(GraveVoidknight.this);
                            this.isDone = true;
                        }
                    });
                    addToBot(new MakeTempCardInHandAction(toAdd));
                    toAdd.superFlash();
                }else {
                    addToBot(new NecromanceAction(4,null,new DrawPileToHandAction_Tag(1, AbstractShadowversePlayer.Enums.LASTWORD,null)));
                }
            }));
        }
    }
}