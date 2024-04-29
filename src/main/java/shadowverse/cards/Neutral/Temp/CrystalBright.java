package shadowverse.cards.Neutral.Temp;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.action.GemLightAction;
import shadowverse.cards.AbstractRightClickCard2;

public class CrystalBright extends AbstractRightClickCard2 {
    public static final String ID = "shadowverse:CrystalBright";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:CrystalBright");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/CrystalBright.png";
    private static boolean hasFusion = false;

    public CrystalBright() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        this.baseMagicNumber = 8;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            this.selfRetain = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ExpertiseAction(abstractPlayer, this.magicNumber));
    }

    @Override
    public void atTurnStart() {
        hasFusion = false;
    }

    @Override
    protected void onRightClick() {
        if (!hasFusion && AbstractDungeon.player != null) {
            addToBot(new SelectCardsInHandAction(9, TEXT[0], true, true, card -> {
                return !(card instanceof GemLight)  && card != this;
            }, abstractCards -> {
                int amount = 0;
                if (abstractCards.size() > 0) {
                    hasFusion = true;
                }
                for (AbstractCard c : abstractCards) {
                    addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                    amount++;
                }
                if (amount>0){
                    addToBot(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
                    addToBot(new GemLightAction(amount, true));
                }
            }));
        }
    }
}
