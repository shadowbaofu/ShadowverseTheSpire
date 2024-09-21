package shadowverse.cards.Neutral.Neutral;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import shadowverse.cards.AbstractNeutralCard;

public class BellAngle extends AbstractNeutralCard {
    public static final String ID = "shadowverse:BellAngle";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:BellAngle");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/BellAngle.png";

    public BellAngle() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = 16;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded){
            upgradeName();
            upgradeBlock(4);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("BellAngle"));
        addToBot(new GainBlockAction(abstractPlayer, abstractPlayer, this.block));
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new DrawCardNextTurnPower(abstractPlayer, 2), 2));
    }
}
