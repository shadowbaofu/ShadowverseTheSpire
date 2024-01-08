package shadowverse.cards.Neutral.Temp;


import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Necromancer;

import java.util.ArrayList;


public class DarkAlice_Crystalize
        extends CustomCard {
    public static final String ID = "shadowverse:DarkAlice_Crystalize";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:DarkAlice_Crystalize");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/DarkAlice.png";
    private static final Texture LEADER_SKIN_VERSION = ImageMaster.loadImage("img/cards/DarkAlice_L.png");
    private float rotationTimer;
    private int previewIndex;

    public static ArrayList<AbstractCard> returnChoice() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new DarkAlice_Temp());
        list.add(new DarkAlice_Ev());
        return list;
    }

    public DarkAlice_Crystalize() {
        super(ID, NAME, IMG_PATH, -2, DESCRIPTION, CardType.POWER, Necromancer.Enums.COLOR_PURPLE, CardRarity.SPECIAL, CardTarget.NONE);
        this.tags.add(AbstractShadowversePlayer.Enums.LASTWORD);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.selfRetain = true;
        this.exhaust = true;
        this.jokePortrait = new TextureAtlas.AtlasRegion(LEADER_SKIN_VERSION, 0, 0, LEADER_SKIN_VERSION.getWidth(), LEADER_SKIN_VERSION.getHeight());
    }

    public DarkAlice_Crystalize(int countDown) {
        super(ID, NAME, IMG_PATH, -2, DESCRIPTION, CardType.POWER, Necromancer.Enums.COLOR_PURPLE, CardRarity.SPECIAL, CardTarget.NONE);
        this.tags.add(AbstractShadowversePlayer.Enums.LASTWORD);
        this.baseMagicNumber = countDown;
        this.magicNumber = this.baseMagicNumber;
        this.selfRetain = true;
        this.exhaust = true;
        this.jokePortrait = new TextureAtlas.AtlasRegion(LEADER_SKIN_VERSION, 0, 0, LEADER_SKIN_VERSION.getWidth(), LEADER_SKIN_VERSION.getHeight());
    }

    public void update() {
        super.update();
        if (this.hb.hovered)
            if (this.rotationTimer <= 0.0F) {
                this.rotationTimer = 2.0F;
                this.cardsToPreview = returnChoice().get(previewIndex).makeCopy();
                if (this.previewIndex == returnChoice().size() - 1) {
                    this.previewIndex = 0;
                } else {
                    this.previewIndex++;
                }
                if (this.upgraded)
                    this.cardsToPreview.upgrade();
            } else {
                this.rotationTimer -= Gdx.graphics.getDeltaTime();
            }
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
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
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void atTurnStart() {
        if (this.baseMagicNumber > 0) {
            this.baseMagicNumber--;
            this.magicNumber = this.baseMagicNumber;
            if (this.magicNumber == 0) {
                addToBot(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
            }
        }
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c.color != Necromancer.Enums.COLOR_PURPLE) {
            if (this.baseMagicNumber > 0) {
                this.baseMagicNumber--;
                this.magicNumber = this.baseMagicNumber;
                if (this.magicNumber == 0) {
                    addToBot(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
                }
            }
        }
    }

    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public void triggerOnExhaust() {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.hasTag(AbstractShadowversePlayer.Enums.LASTWORD))
                count++;
        }
        if (count > 9) {
            AbstractCard ev = new DarkAlice_Ev();
            if (upgraded)
                ev.upgrade();
            addToBot(new MakeTempCardInHandAction(ev));
        } else {
            AbstractCard tmp = new DarkAlice_Temp();
            if (upgraded)
                tmp.upgrade();
            addToBot(new MakeTempCardInHandAction(tmp));
        }
    }

    public AbstractCard makeCopy() {
        return (AbstractCard) new DarkAlice_Crystalize();
    }
}

