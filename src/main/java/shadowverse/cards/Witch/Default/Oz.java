package shadowverse.cards.Witch.Default;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import shadowverse.action.OzSetCostAction;
import shadowverse.characters.Witchcraft;
import shadowverse.powers.OzPower;


public class Oz
        extends CustomCard {
    public static final String ID = "shadowverse:Oz";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Oz");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Oz.png";
    private static final Texture LEADER_SKIN_VERSION = ImageMaster.loadImage("img/cards/Oz_L.png");

    public Oz() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Witchcraft.Enums.COLOR_BLUE, CardRarity.RARE, CardTarget.NONE);
        this.jokePortrait = new TextureAtlas.AtlasRegion(LEADER_SKIN_VERSION, 0, 0, LEADER_SKIN_VERSION.getWidth(), LEADER_SKIN_VERSION.getHeight());
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if ((UnlockTracker.betaCardPref.getBoolean(this.cardID, false))) {
            addToBot(new SFXAction("Oz_L"));
        }else {
            addToBot(new SFXAction("Oz"));
        }
        addToBot(new ExpertiseAction(abstractPlayer, 8));
        addToBot(new OzSetCostAction(abstractPlayer.hand.group));
        boolean powerExists = false;
        for (AbstractPower pow : abstractPlayer.powers) {
            if (pow.ID.equals("shadowverse:OzPower")) {
                powerExists = true;
                break;
            }
        }
        if (!powerExists) {
            addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new OzPower(abstractPlayer)));
        }
    }


    public AbstractCard makeCopy() {
        return new Oz();
    }
}

