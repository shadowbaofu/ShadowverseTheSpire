package shadowverse.cards.Neutral.Temp;


import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.MiracleEffect;
import shadowverse.action.GarnetReleaseAction;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Vampire;


public class GarnetRelease
        extends CustomCard {
    public static final String ID = "shadowverse:GarnetRelease";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:GarnetRelease");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/FirstOne.png";

    public GarnetRelease() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, Vampire.Enums.COLOR_SCARLET, CardRarity.SPECIAL, CardTarget.NONE);
        this.exhaust = true;
        this.tags.add(AbstractShadowversePlayer.Enums.MACHINE);
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("GarnetRelease"));
        addToBot(new VFXAction(new BorderFlashEffect(Color.SCARLET, true)));
        addToBot(new VFXAction(new MiracleEffect(Color.SCARLET.cpy(), Color.WHITE.cpy(), "HEAL_3")));
        addToBot(new GarnetReleaseAction());
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new GarnetRelease();
    }
}

