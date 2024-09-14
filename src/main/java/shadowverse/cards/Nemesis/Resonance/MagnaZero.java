package shadowverse.cards.Nemesis.Resonance;


import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.SweepingBeamEffect;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Nemesis;
import shadowverse.effect.moryyEffect;



public class MagnaZero extends CustomCard {
    public static final String ID = "shadowverse:MagnaZero";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:MagnaZero");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/MagnaZero.png";
    private static final Texture LEADER_SKIN_VERSION = ImageMaster.loadImage("img/cards/MagnaZero_L.png");

    public MagnaZero() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseDamage = 0;
        this.baseMagicNumber = 7;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(AbstractShadowversePlayer.Enums.MACHINE);
        this.jokePortrait = new TextureAtlas.AtlasRegion(LEADER_SKIN_VERSION, 0, 0, LEADER_SKIN_VERSION.getWidth(), LEADER_SKIN_VERSION.getHeight());
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }

    public void applyPowers() {
        super.applyPowers();
        int count = 0;
        if (AbstractDungeon.player instanceof AbstractShadowversePlayer)
            count = ((AbstractShadowversePlayer) AbstractDungeon.player).resonanceCount;
        int realBaseDamage = this.baseDamage;
        this.baseDamage += count * this.magicNumber;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int count = 0;
        if (AbstractDungeon.player instanceof AbstractShadowversePlayer)
            count = ((AbstractShadowversePlayer) AbstractDungeon.player).resonanceCount;
        int realBaseDamage = this.baseDamage;
        this.baseDamage += count * this.magicNumber;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }



    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if ((UnlockTracker.betaCardPref.getBoolean(this.cardID, false)))
            addToBot(new SFXAction("MagnaZero_L"));
        else
            addToBot(new SFXAction("MagnaZero"));
        if (Settings.FAST_MODE) {
            addToBot(new VFXAction(new moryyEffect(), 0.7F));
        } else {
            addToBot(new VFXAction(new moryyEffect(), 1.0F));
        }
        int count = 0;
        if (AbstractDungeon.player instanceof AbstractShadowversePlayer)
            count = ((AbstractShadowversePlayer) AbstractDungeon.player).resonanceCount;
        addToBot(new DamageAllEnemiesAction(abstractPlayer, DamageInfo.createDamageMatrix(this.magicNumber * count, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new MagnaZero();
    }
}
