package shadowverse.cards.Neutral.Temp;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.action.GemLightAction;
import shadowverse.cards.AbstractRightClickCard2;

public class GemLight extends AbstractRightClickCard2 {
    public static final String ID = "shadowverse:GemLight";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:GemLight");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/GemLight.png";

    public GemLight() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        this.baseMagicNumber = 2;
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

    private boolean canFusion(){
        int amount = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group){
            if (c instanceof GemLight){
                amount++;
            }
        }
        return amount > 1;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DrawCardAction(this.magicNumber));
    }

    @Override
    protected void onRightClick() {
            if (canFusion() && AbstractDungeon.player != null) {
                int amount = -1;
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    if (c instanceof GemLight){
                        amount++;
                        addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                    }
                }
                if (amount>0){
                    addToBot(new GemLightAction(amount, false));
                }
            }
    }


}
