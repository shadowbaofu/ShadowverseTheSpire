package shadowverse.cards.Nemesis.Resonance;

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
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;
import shadowverse.action.DrawPileToHandAction_Tag;
import shadowverse.cards.AbstractRightClickCard2;
import shadowverse.cards.Neutral.Temp.AnalyzeArtifact;
import shadowverse.cards.Neutral.Temp.BlazingBarrage;
import shadowverse.cards.Neutral.Temp.PurelightExemplar;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Nemesis;
import shadowverse.stance.Resonance;

public class FieryBarrage extends AbstractRightClickCard2 {
    public static final String ID = "shadowverse:FieryBarrage";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:FieryBarrage");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/FieryBarrage.png";
    private boolean hasFusion = false;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
    public static final String[] TEXT = uiStrings.TEXT;

    public FieryBarrage() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 3;
        this.cardsToPreview = new BlazingBarrage();
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeDamage(3);
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        if (abstractMonster.hasPower("Artifact")) {
            addToBot(new RemoveSpecificPowerAction(abstractMonster, p, "Artifact"));
        } else {
            for (AbstractPower pow : abstractMonster.powers) {
                if (pow.type == AbstractPower.PowerType.BUFF && pow.ID != "Invincible" && pow.ID != "Mode Shift" && pow.ID != "Split" && pow.ID != "Unawakened" && pow.ID != "Life Link" && pow.ID != "Fading" && pow.ID != "Stasis" && pow.ID != "Minion" && pow.ID != "Shifting" && pow.ID != "shadowverse:chushouHealPower" && !(pow instanceof StrengthPower) && !(pow instanceof DexterityPower)) {
                    addToBot(new RemoveSpecificPowerAction(pow.owner, p, pow.ID));
                    break;
                }
            }
        }
    }

    @Override
    public void atTurnStart() {
        hasFusion = false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new FieryBarrage();
    }

    @Override
    protected void onRightClick() {
        if (!this.hasFusion && AbstractDungeon.player != null) {
            addToBot(new SelectCardsInHandAction(9, TEXT[0], true, true, card -> {
                return CardLibrary.getCard(card.cardID) != null && card.color == Nemesis.Enums.COLOR_SKY && card != this
                        && AbstractDungeon.player.stance.ID.equals(Resonance.STANCE_ID)
                        && !card.hasTag(CardTags.STARTER_STRIKE) && !card.hasTag(CardTags.STARTER_DEFEND);
            }, abstractCards -> {
                if (abstractCards.size() > 0) {
                    this.hasFusion = true;
                    AbstractCard toAdd = this.cardsToPreview.makeStatEquivalentCopy();
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            AbstractDungeon.player.hand.removeCard(FieryBarrage.this);
                            this.isDone = true;
                        }
                    });
                    addToBot(new MakeTempCardInHandAction(toAdd));
                    toAdd.superFlash();
                }
                for (AbstractCard card : abstractCards) {
                    addToBot(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
                }
            }));
        }
    }
}