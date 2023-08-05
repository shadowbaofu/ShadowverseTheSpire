package shadowverse.cards.Elf.Left;


import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.action.PrimalGigantAction;
import shadowverse.cards.Witch.AbstractAccelerateCard;
import shadowverse.characters.Elf;


public class PrimalGigant extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:PrimalGigant";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:PrimalGigant");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/PrimalGigant.png";

    public PrimalGigant() {
        super(ID, NAME, IMG_PATH, 4, DESCRIPTION, CardType.ATTACK, Elf.Enums.COLOR_GREEN, CardRarity.RARE, CardTarget.SELF, 0, CardType.SKILL);
        this.exhaust = true;
        this.baseBlock = 35;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(7);
        }
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PrimalGigantAction());
        addToBot(new GainBlockAction(p, this.block));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HealAction(p, p, 4));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new PrimalGigant();
    }
}

