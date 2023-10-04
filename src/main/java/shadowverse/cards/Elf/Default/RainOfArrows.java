package shadowverse.cards.Elf.Default;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
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
import shadowverse.cards.Neutral.Temp.DelugeOfArrows;
import shadowverse.characters.Elf;

public class RainOfArrows extends AbstractRightClickCard2 {
    public static final String ID = "shadowverse:RainOfArrows";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:RainOfArrows");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/RainOfArrows.png";
    private boolean hasFusion = false;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
    public static final String[] TEXT = uiStrings.TEXT;

    public RainOfArrows() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, Elf.Enums.COLOR_GREEN, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.baseDamage = 7;
        this.cardsToPreview = new DelugeOfArrows();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeDamage(2);
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        int count = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
        if (count > 2){
            addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(this.damage*2, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE, true));
            addToBot(new DrawCardAction(1));
        }else {
            addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(this.damage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE, true));
        }
        addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
    }

    @Override
    public void atTurnStart(){
        hasFusion = false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new RainOfArrows();
    }

    @Override
    protected void onRightClick() {
        if (!this.hasFusion && AbstractDungeon.player!=null){
            addToBot(new SelectCardsInHandAction(9,TEXT[0],true,true, card -> {
                return CardLibrary.getCard(card.cardID) != null && card.color== Elf.Enums.COLOR_GREEN && card != this
                &&!card.hasTag(CardTags.STARTER_STRIKE) && !card.hasTag(CardTags.STARTER_DEFEND);
            }, abstractCards -> {
                if (abstractCards.size()>0){
                    this.hasFusion = true;
                }
                for (AbstractCard card : abstractCards){
                    addToBot(new ExhaustSpecificCardAction(card,AbstractDungeon.player.hand));
                }
                if (abstractCards.size()>5){
                    AbstractCard toAdd = this.cardsToPreview.makeStatEquivalentCopy();
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            AbstractDungeon.player.hand.removeCard(RainOfArrows.this);
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