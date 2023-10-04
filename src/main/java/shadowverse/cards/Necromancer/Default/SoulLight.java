package shadowverse.cards.Necromancer.Default;

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
import shadowverse.cards.Neutral.Temp.HeartRadiance;
import shadowverse.characters.Elf;
import shadowverse.characters.Necromancer;

public class SoulLight extends AbstractRightClickCard2 {
    public static final String ID = "shadowverse:SoulLight";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:SoulLight");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/SoulLight.png";
    private boolean hasFusion = false;
    private boolean fused;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
    public static final String[] TEXT = uiStrings.TEXT;

    public SoulLight() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, Necromancer.Enums.COLOR_PURPLE, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.baseDamage = 9;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = new HeartRadiance();
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
        addToBot(new DrawCardAction(this.magicNumber));
        if (this.fused){
            addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(this.damage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE, true));
            addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
        }
    }

    @Override
    public void atTurnStart(){
        hasFusion = false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new SoulLight();
    }

    @Override
    protected void onRightClick() {
        if (!this.hasFusion && AbstractDungeon.player!=null){
            addToBot(new SelectCardsInHandAction(9,TEXT[0],true,true, card -> {
                return CardLibrary.getCard(card.cardID) != null && card.color== Necromancer.Enums.COLOR_PURPLE && card != this
                &&!card.hasTag(CardTags.STARTER_STRIKE) && !card.hasTag(CardTags.STARTER_DEFEND);
            }, abstractCards -> {
                if (abstractCards.size()>0){
                    this.hasFusion = true;
                    fused = true;
                }
                for (AbstractCard card : abstractCards){
                    addToBot(new ExhaustSpecificCardAction(card,AbstractDungeon.player.hand));
                }
                if (abstractCards.size()>1){
                    AbstractCard toAdd = this.cardsToPreview.makeStatEquivalentCopy();
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            AbstractDungeon.player.hand.removeCard(SoulLight.this);
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