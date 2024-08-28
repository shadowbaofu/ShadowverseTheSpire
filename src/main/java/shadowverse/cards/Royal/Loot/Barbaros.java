package shadowverse.cards.Royal.Loot;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
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
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import shadowverse.cards.AbstractRightClickCard2;
import shadowverse.cards.Neutral.Temp.DreadPirateFlag;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Royal;
import shadowverse.powers.BarbarosPower;
import shadowverse.relics.KagemitsuSword;

public class Barbaros
        extends AbstractRightClickCard2 {
    public static final String ID = "shadowverse:Barbaros";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Barbaros");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Barbaros.png";
    public static final String IMG_PATH_EV = "img/cards/Barbaros_Ev.png";
    private static final Texture LEADER_SKIN_VERSION = ImageMaster.loadImage("img/cards/Barbaros_L.png");
    private static final String LEADER_SKIN_VERSION_EV = "img/cards/Barbaros_Ev_L.png";
    private boolean hasFusion = false;
    public boolean triggered;

    public Barbaros() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Royal.Enums.COLOR_YELLOW, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 8;
        this.tags.add(AbstractShadowversePlayer.Enums.CONDEMNED);
        this.cardsToPreview = new DreadPirateFlag();
        this.jokePortrait = new TextureAtlas.AtlasRegion(LEADER_SKIN_VERSION, 0, 0, LEADER_SKIN_VERSION.getWidth(), LEADER_SKIN_VERSION.getHeight());
        this.triggered = false;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            if ((UnlockTracker.betaCardPref.getBoolean(this.cardID, false))) {
                this.textureImg = LEADER_SKIN_VERSION_EV;
                this.loadCardImage(LEADER_SKIN_VERSION_EV);
            } else {
                this.textureImg = IMG_PATH_EV;
                this.loadCardImage(IMG_PATH_EV);
            }
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    protected void onRightClick() {
        if (!this.hasFusion && AbstractDungeon.player != null) {
            addToBot(new SelectCardsInHandAction(9, TEXT[0], true, true, card -> {
                return card.hasTag(AbstractShadowversePlayer.Enums.GILDED);
            }, abstractCards -> {
                if (abstractCards.size() > 0) {
                    this.hasFusion = true;
                }
                for (AbstractCard c : abstractCards) {
                    addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                }
            }));
        }
    }


    @Override
    public void atTurnStart() {
        hasFusion = false;
    }

    public void degrade() {
        if (this.upgraded) {
            degradeName();
            this.textureImg = IMG_PATH;
            this.loadCardImage(IMG_PATH);
            this.rawDescription = cardStrings.DESCRIPTION;
            initializeDescription();
            this.superFlash();
            this.applyPowers();
        }
    }


    public void degradeName() {
        --this.timesUpgraded;
        this.upgraded = false;
        this.name = NAME;
        this.initializeTitle();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.BLUE, true));
        if (this.hasFusion) {
            if ((UnlockTracker.betaCardPref.getBoolean(this.cardID, false))) {
                addToBot(new SFXAction("Barbaros_Eh_L"));
            } else {
                addToBot(new SFXAction("Barbaros_Eh"));
            }
        } else {
            if ((UnlockTracker.betaCardPref.getBoolean(this.cardID, false))) {
                addToBot(new SFXAction("Barbaros_Eh_L"));
            } else {
                addToBot(new SFXAction("Barbaros_Eh"));
            }
        }
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage * 2, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        if (this.upgraded && !this.triggered) {
            addToBot(new ApplyPowerAction(p, p, new BarbarosPower(p, 1)));
            this.triggered = true;
        } else {
            addToBot(new MakeTempCardInHandAction(new DreadPirateFlag(), 1));
            addToBot(new GainEnergyAction(1));
        }
        if (this.hasFusion) {
            addToBot(new MakeTempCardInHandAction(new DreadPirateFlag(), 1));
            addToBot(new GainEnergyAction(1));
        }
        this.degrade();
        if (p.hasRelic(KagemitsuSword.ID) || p.hasPower("shadowverse:SeofonPower")) {
            this.upgrade();
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new Barbaros();
    }
}

