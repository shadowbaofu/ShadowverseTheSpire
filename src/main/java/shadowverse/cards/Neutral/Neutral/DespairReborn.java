package shadowverse.cards.Neutral.Neutral;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.action.DespairRebornAction;
import shadowverse.cards.AbstractNeutralCard;

public class DespairReborn extends AbstractNeutralCard {
    public static final String ID = "shadowverse:DespairReborn";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:DespairReborn");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/DespairReborn.png";

    public DespairReborn() {
        super(ID, NAME, IMG_PATH, 4, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.RARE, CardTarget.NONE);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded){
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void triggerAtStartOfTurn() {
        if (AbstractDungeon.ascensionLevel == 20){
            this.setCostForTurn(2);
            this.superFlash();
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DiscardAction(abstractPlayer,abstractPlayer,2,true));
        addToBot(new DespairRebornAction(this.upgraded));
    }
}
