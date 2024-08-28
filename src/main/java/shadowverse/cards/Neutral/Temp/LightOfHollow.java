package shadowverse.cards.Neutral.Temp;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import shadowverse.cards.AbstractRightClickCard2;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Royal;

public class LightOfHollow
        extends AbstractRightClickCard2 {
    public static final String ID = "shadowverse:LightOfHollow";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:LightOfHollow");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/LightOfHollow.png";
    private boolean hasFusion = false;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
    public static final String[] TEXT = uiStrings.TEXT;


    public LightOfHollow() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, Royal.Enums.COLOR_YELLOW, CardRarity.SPECIAL, CardTarget.ALL_ENEMY);
        this.baseDamage = 3;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.selfRetain = true;
        this.tags.add(AbstractShadowversePlayer.Enums.GILDED);
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(1);
            upgradeMagicNumber(1);
        }
    }

    @Override
    protected void onRightClick() {
        if (!this.hasFusion && AbstractDungeon.player != null) {
            addToBot(new SelectCardsInHandAction(9, TEXT[0], true, true, card -> {
                return card.hasTag(AbstractShadowversePlayer.Enums.GILDED) && card != this;
            }, abstractCards -> {
                if (abstractCards.size() > 0) {
                    addToBot(new DrawCardAction(1));
                }
                for (AbstractCard c : abstractCards) {
                    addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                }
            }));
            this.hasFusion = true;
        }
    }

    @Override
    public void atTurnStart() {
        hasFusion = false;
    }

    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("LightOfHollow"));
        int necklace = 0;
        int boots = 0;
        int goblet = 0;
        int blade = 0;
        for (AbstractCard c : abstractPlayer.exhaustPile.group) {
            if (c.cardID == "shadowverse:GildedNecklace") {
                necklace++;
            } else if (c.cardID == "shadowverse:GildedBoots") {
                boots++;
            } else if (c.cardID == "shadowverse:Goblet") {
                goblet++;
            } else if (c.cardID == "shadowverse:GildedBlade") {
                blade++;
            }
        }
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new StrengthPower(abstractPlayer, necklace), necklace));
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new DexterityPower(abstractPlayer, boots), boots));
        AbstractDungeon.actionManager.addToBottom(new HealAction(abstractPlayer, AbstractDungeon.player, this.magicNumber * goblet));
        for (int i = 0; i < blade; i++) {
            addToBot(new DamageRandomEnemyAction(new DamageInfo(abstractPlayer, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new LightOfHollow();
    }
}

