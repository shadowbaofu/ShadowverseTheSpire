package shadowverse.cards.Nemesis.Pile;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import shadowverse.action.ChoiceToDrawPileAction;
import shadowverse.cards.Neutral.Temp.AnalyzeArtifact;
import shadowverse.cards.Neutral.Temp.AncientArtifact;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Nemesis;

import java.util.ArrayList;

public class LaserTyranny
        extends CustomCard {
    public static final String ID = "shadowverse:LaserTyranny";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:LaserTyranny");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/LaserTyranny.png";

    private float rotationTimer;
    private int previewIndex;

    public static ArrayList<AbstractCard> returnChoice() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new AnalyzeArtifact());
        list.add(new AncientArtifact());
        list.add(new LaserTyranny());
        return list;
    }


    public LaserTyranny() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.SKILL, Nemesis.Enums.COLOR_SKY, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.baseDamage = 4;
        this.exhaust = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public void update() {
        super.update();
        if (this.hb.hovered)
            if (this.rotationTimer <= 0.0F) {
                this.rotationTimer = 2.0F;
                this.cardsToPreview = (AbstractCard) returnChoice().get(previewIndex).makeCopy();
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

    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractCard[] list = new AbstractCard[returnChoice().size()];
        returnChoice().toArray(list);
        if (this.upgraded){
            for (AbstractCard c : list){
                c.upgrade();
            }
        }
        addToBot(new ChoiceToDrawPileAction(false, list));
        AbstractMonster mo = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
        addToBot(new DamageAction(mo, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        addToBot(new SFXAction("LaserTyranny"));
        if (Settings.FAST_MODE) {
            addToBot(new VFXAction(new SmallLaserEffect(mo.hb.cX, mo.hb.cY, abstractPlayer.hb.cX, abstractPlayer.hb.cY), 0.1F));
        } else {
            addToBot(new VFXAction(new SmallLaserEffect(mo.hb.cX, mo.hb.cY, abstractPlayer.hb.cX, abstractPlayer.hb.cY), 0.3F));
        }
        addToBot(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
        int artifactAmount = 0;
        for (AbstractCard card : AbstractDungeon.player.exhaustPile.group) {
            if (card.hasTag(AbstractShadowversePlayer.Enums.ARTIFACT))
                artifactAmount++;
        }
        if (artifactAmount > 5) {
            addToBot(new DamageAllEnemiesAction(abstractPlayer, DamageInfo.createDamageMatrix(this.damage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));
        }

    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new LaserTyranny();
    }
}

