package shadowverse.cards.Neutral.Temp;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.Neutral.Neutral.WingedInversion;
import shadowverse.characters.AbstractShadowversePlayer;

public class SingleWingedRebellion extends CustomCard {
    public static final String ID = "shadowverse:SingleWingedRebellion";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/SingleWingedRebellion.png";
    private boolean triggered;

    public SingleWingedRebellion() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
        this.baseBlock = 6;
        this.cardsToPreview = new SingleWingedExecutor();
        this.tags.add(AbstractShadowversePlayer.Enums.DARK_ANGEL);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
            this.cardsToPreview.upgrade();
        }
    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainEnergyAction(1));
        addToBot(new GainBlockAction(abstractPlayer,this.block));
        addToBot(new MakeTempCardInHandAction(new WingedInversion()));
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c.hasTag(AbstractShadowversePlayer.Enums.DARK_ANGEL)){
            c.upgrade();
            this.upgrade();
            triggered = true;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new SingleWingedRebellion();
    }
}


