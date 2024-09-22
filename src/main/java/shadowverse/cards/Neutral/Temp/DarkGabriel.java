package shadowverse.cards.Neutral.Temp;

import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;
import shadowverse.action.DarkGabrielAction;
import shadowverse.action.GabrielAction;
import shadowverse.cards.AbstractNeutralCard;
import shadowverse.cards.Neutral.Neutral.Gabriel;
import shadowverse.characters.AbstractShadowversePlayer;

public class DarkGabriel extends AbstractNeutralCard {
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:DarkGabriel");
    public static final String ID = "shadowverse:Gabriel";
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION; public static final String IMG_PATH = "img/cards/DarkGabriel.png";
    public DarkGabriel() {
        super(ID, NAME, IMG_PATH, -1, DESCRIPTION, CardType.POWER, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.tags.add(AbstractShadowversePlayer.Enums.DARK_ANGEL);
        this.cardsToPreview = new Gabriel();
    }

    @Override
    public void upgrade() {
        upgradeName();
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
        this.cardsToPreview.upgrade();
    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("Gabriel"));
        AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
        addToBot(new DarkGabrielAction());
        addToBot(new GabrielAction(abstractMonster,this.upgraded,this.freeToPlayOnce,-this.energyOnUse));
    }

    public AbstractCard makeCopy() {
        return new DarkGabriel();
    }
}
