package shadowverse.cards.Neutral.Temp;


import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Necromancer;


public class DarkAlice_Ev
        extends CustomCard {
    public static final String ID = "shadowverse:DarkAlice_Ev";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:DarkAlice_Ev");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/DarkAlice_Ev.png";
    private static final Texture LEADER_SKIN_VERSION = ImageMaster.loadImage("img/cards/DarkAlice_Ev_L.png");

    public DarkAlice_Ev() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.ATTACK, Necromancer.Enums.COLOR_PURPLE, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.baseDamage = 40;
        this.tags.add(AbstractShadowversePlayer.Enums.LASTWORD);
        this.exhaust = true;
        this.cardsToPreview = new DarkAlice_Crystalize();
        this.jokePortrait = new TextureAtlas.AtlasRegion(LEADER_SKIN_VERSION, 0, 0, LEADER_SKIN_VERSION.getWidth(), LEADER_SKIN_VERSION.getHeight());
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(5);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if ((UnlockTracker.betaCardPref.getBoolean(this.cardID, false))) {
            addToBot(new SFXAction("DarkAlice_L"));
        }else {
            addToBot(new SFXAction("DarkAlice"));
        }
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    @Override
    public void triggerOnExhaust() {
        AbstractCard c = new DarkAlice_Crystalize(1);
        if (upgraded)
            c.upgrade();
        addToBot(new MakeTempCardInHandAction(c));
    }

    public AbstractCard makeCopy() {
        return (AbstractCard) new DarkAlice_Ev();
    }
}

