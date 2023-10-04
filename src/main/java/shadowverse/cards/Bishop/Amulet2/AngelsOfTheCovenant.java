package shadowverse.cards.Bishop.Amulet2;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
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
import com.megacrit.cardcrawl.vfx.combat.MiracleEffect;
import shadowverse.action.SkullFaneAccAction;
import shadowverse.cards.AbstractRightClickCard2;
import shadowverse.cards.Neutral.Temp.SeraphsOfTheTrueKey;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Bishop;

public class AngelsOfTheCovenant extends AbstractRightClickCard2 {
    public static final String ID = "shadowverse:AngelsOfTheCovenant";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:AngelsOfTheCovenant");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/AngelsOfTheCovenant.png";
    private boolean hasFusion = false;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
    public static final String[] TEXT = uiStrings.TEXT;

    public AngelsOfTheCovenant() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.ATTACK, Bishop.Enums.COLOR_WHITE, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 5;
        this.cardsToPreview = new SeraphsOfTheTrueKey();
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
        addToBot(new SFXAction("AngelsOfTheCovenant"));
        addToBot(new VFXAction(new MiracleEffect()));
        addToBot(new SkullFaneAccAction(this));
        addToBot(new DamageAction(abstractMonster, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    public void applyPowers() {
        super.applyPowers();
        int count = 0;
        if (AbstractDungeon.player instanceof AbstractShadowversePlayer) {
            count = ((AbstractShadowversePlayer) AbstractDungeon.player).amuletCount;
        }
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + count;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    @Override
    public void atTurnStart() {
        hasFusion = false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new AngelsOfTheCovenant();
    }

    @Override
    protected void onRightClick() {
        if (!this.hasFusion && AbstractDungeon.player != null) {
            if (AbstractDungeon.player instanceof AbstractShadowversePlayer) {
                if (((AbstractShadowversePlayer) AbstractDungeon.player).amuletCount >= 10) {
                    addToBot(new SelectCardsInHandAction(9, TEXT[0], true, true, card -> {
                        return CardLibrary.getCard(card.cardID) != null && card.color == Bishop.Enums.COLOR_WHITE && card != this;
                    }, abstractCards -> {
                        if (abstractCards.size() > 0) {
                            this.hasFusion = true;
                            AbstractCard toAdd = this.cardsToPreview.makeStatEquivalentCopy();
                            addToBot(new AbstractGameAction() {
                                @Override
                                public void update() {
                                    AbstractDungeon.player.hand.removeCard(AngelsOfTheCovenant.this);
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
    }
}