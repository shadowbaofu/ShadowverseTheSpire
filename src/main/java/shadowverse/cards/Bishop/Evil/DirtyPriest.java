package shadowverse.cards.Bishop.Evil;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.AbstractCrystalizeCard;
import shadowverse.cards.Neutral.Curse.Indulgence;
import shadowverse.cards.Witch.AbstractAccelerateCard;
import shadowverse.characters.Bishop;
import shadowverse.orbs.AmuletOrb;

public class DirtyPriest
        extends AbstractAccelerateCard implements AbstractCrystalizeCard {
    public static final String ID = "shadowverse:DirtyPriest";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:DirtyPriest");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/DirtyPriest.png";
    private boolean played;


    public DirtyPriest() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Bishop.Enums.COLOR_WHITE, CardRarity.COMMON, CardTarget.SELF, 0, CardType.POWER);
        this.baseBlock = 12;
        this.cardsToPreview = new Indulgence();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("DirtyPriest"));
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
        addToBot(new MakeTempCardInDrawPileAction(this.cardsToPreview, 1, true, true, false));
        addToBot(new MakeTempCardInDiscardAction(this.cardsToPreview, 1));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("DirtyPriest_Acc"));
    }

    public AbstractCard makeCopy() {
        return (AbstractCard) new DirtyPriest();
    }

    @Override
    public void onStartOfTurn(AmuletOrb paramOrb) {
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
    }

    @Override
    public void onEvoke(AmuletOrb paramOrb) {
    }

    @Override
    public void endOfTurn(AmuletOrb paramOrb) {

    }

    @Override
    public void onGainedBlock(int blockAmt, AmuletOrb paramOrb) {
    }

    @Override
    public void onOtherCardPlayed(AbstractCard c, AmuletOrb paramOrb) {
    }

    @Override
    public int onHeal(int healAmount, AmuletOrb paramOrb) {
        return healAmount;
    }

    @Override
    public int returnCountDown() {
        return 2;
    }
}


