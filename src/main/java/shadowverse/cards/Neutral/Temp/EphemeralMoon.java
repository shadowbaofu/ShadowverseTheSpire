package shadowverse.cards.Neutral.Temp;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import shadowverse.cards.AbstractAmuletCard;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Bishop;
import shadowverse.orbs.AmuletOrb;

import java.util.ArrayList;

public class EphemeralMoon extends AbstractAmuletCard {
    public static final String ID = "shadowverse:EphemeralMoon";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:EphemeralMoon");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/EphemeralMoon.png";

    public EphemeralMoon() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, Bishop.Enums.COLOR_WHITE, CardRarity.SPECIAL, CardTarget.NONE);
        this.countDown = 2;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(AbstractShadowversePlayer.Enums.AMULET_FOR_ONECE);
    }


    @Override
    public void onStartOfTurn(AmuletOrb paramOrb) {
        if (AbstractDungeon.player.discardPile.size() > 0) {
            addToBot(new EmptyDeckShuffleAction());
            addToBot(new ShuffleAction(AbstractDungeon.player.drawPile, false));
        }
        addToBot(new DrawCardAction(AbstractDungeon.player, this.magicNumber));
    }

    @Override
    public void onEvoke(AmuletOrb paramOrb) {
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new BlurPower(AbstractDungeon.player,1),1));
    }

    @Override
    public void endOfTurn(AmuletOrb paramOrb) {

    }

    @Override
    public int onHeal(int healAmount, AmuletOrb paramOrb) {
        return 0;
    }

    @Override
    public void onOtherCardPlayed(AbstractCard c, AmuletOrb paramOrb) {

    }

    @Override
    public void onGainedBlock(int blockAmt, AmuletOrb paramOrb) {

    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}
