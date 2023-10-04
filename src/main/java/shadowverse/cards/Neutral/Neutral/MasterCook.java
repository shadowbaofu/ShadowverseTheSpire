package shadowverse.cards.Neutral.Neutral;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.AbstractNeutralCard;

public class MasterCook extends AbstractNeutralCard {
    public static final String ID = "shadowverse:MasterCook";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:MasterCook");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/MasterCook.png";

    public MasterCook() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = 8;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded){
            upgradeName();
            upgradeBlock(3);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("MasterCook"));
        addToBot(new GainBlockAction(abstractPlayer, abstractPlayer, this.block));
        addToBot( new DrawCardAction(1));
        AbstractDungeon.player.increaseMaxHp(this.magicNumber, true);
    }
}
