package shadowverse.cards.Necromancer.LastWords;


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
import shadowverse.cards.AbstractAccelerateCard;
import shadowverse.cards.Neutral.Temp.DarkAlice_Crystalize;
import shadowverse.cards.Neutral.Temp.DarkAlice_Ev;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Necromancer;


public class DarkAlice
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:DarkAlice";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:DarkAlice");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/DarkAlice.png";
    private static final Texture LEADER_SKIN_VERSION = ImageMaster.loadImage("img/cards/DarkAlice_L.png");

    public DarkAlice() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.ATTACK, Necromancer.Enums.COLOR_PURPLE, CardRarity.RARE, CardTarget.ENEMY, 1, CardType.POWER);
        this.baseDamage = 18;
        this.tags.add(AbstractShadowversePlayer.Enums.LASTWORD);
        this.cardsToPreview = new DarkAlice_Crystalize();
        this.jokePortrait = new TextureAtlas.AtlasRegion(LEADER_SKIN_VERSION, 0, 0, LEADER_SKIN_VERSION.getWidth(), LEADER_SKIN_VERSION.getHeight());
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public void applyPowers() {
        super.applyPowers();
        int count = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.hasTag(AbstractShadowversePlayer.Enums.LASTWORD))
                count++;
        }
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + count;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        if ((UnlockTracker.betaCardPref.getBoolean(this.cardID, false))) {
            addToBot(new SFXAction("DarkAlice_L"));
        }else {
            addToBot(new SFXAction("DarkAlice"));
        }
        int count = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.hasTag(AbstractShadowversePlayer.Enums.LASTWORD))
                count++;
        }
        if (count > 10) {
            p.hand.removeCard(this);
            AbstractCard ev = new DarkAlice_Ev();
            if (upgraded)
                ev.upgrade();
            addToBot(new MakeTempCardInHandAction(ev));
        }else {
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        }
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        if ((UnlockTracker.betaCardPref.getBoolean(this.cardID, false))) {
            addToBot(new SFXAction("DarkAlice_Acc_L"));
        }else {
            addToBot(new SFXAction("DarkAlice_Acc"));
        }
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), 1));
    }

    @Override
    public void triggerOnExhaust() {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.hasTag(AbstractShadowversePlayer.Enums.LASTWORD))
                count++;
        }
        if (count > 9) {
            AbstractCard c = new DarkAlice_Crystalize(1);
            if (upgraded)
                c.upgrade();
            addToBot(new MakeTempCardInHandAction(c));
        }else {
            addToBot(new MakeTempCardInHandAction(this.makeStatEquivalentCopy()));
        }
    }

    public AbstractCard makeCopy() {
        return (AbstractCard) new DarkAlice();
    }
}

