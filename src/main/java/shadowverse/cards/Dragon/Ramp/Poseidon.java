package shadowverse.cards.Dragon.Ramp;


import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.Neutral.Temp.PoseidonGuard;
import shadowverse.cards.Witch.AbstractAccelerateCard;
import shadowverse.characters.Dragon;


public class Poseidon extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:Poseidon";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Poseidon");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Poseidon.png";

    public Poseidon() {
        super(ID, NAME, IMG_PATH, 4, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.UNCOMMON, CardTarget.SELF, 1, CardType.SKILL);
        this.baseBlock = 28;
        this.cardsToPreview = new PoseidonGuard();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(2);
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("Poseidon"));
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), 2));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("Poseidon_Acc"));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), 1));
    }

    public AbstractCard makeCopy() {
        return (AbstractCard) new Poseidon();
    }
}

