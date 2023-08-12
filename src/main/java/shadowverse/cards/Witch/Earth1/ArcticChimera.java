package shadowverse.cards.Witch.Earth1;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;
import shadowverse.cards.AbstractAccelerateCard;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Witchcraft;
import shadowverse.powers.EarthEssence;

public class ArcticChimera
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:ArcticChimera";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:ArcticChimera");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/ArcticChimera.png";

    public ArcticChimera() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Witchcraft.Enums.COLOR_BLUE, CardRarity.RARE, CardTarget.ENEMY, 0, CardType.SKILL);
        this.baseBlock = 8;
        this.baseDamage = 6;
        this.tags.add(AbstractShadowversePlayer.Enums.EARTH_RITE);
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeBlock(2);
        }
    }


    @Override
    public void baseUse(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractPower p = abstractPlayer.getPower(EarthEssence.POWER_ID);
        addToBot(new GainBlockAction(abstractPlayer, this.block));
        addToBot(new GainBlockAction(abstractPlayer, this.block));
        if (p != null && p.amount > 2) {
            addToBot(new GainBlockAction(abstractPlayer, this.block));
            addToBot(new VFXAction(new BiteEffect(abstractMonster.hb.cX, abstractMonster.hb.cY - 40.0F * Settings.scale, Color.WHITE.cpy()), 0.2F));
            addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, abstractPlayer.currentBlock + this.block * 3, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
            addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, abstractPlayer.getPower(EarthEssence.POWER_ID), -3));
            if (abstractPlayer instanceof AbstractShadowversePlayer) {
                ((AbstractShadowversePlayer) abstractPlayer).earthCount += 2;
            }
        } else {
            addToBot(new ApplyPowerAction(abstractMonster, abstractPlayer, new VulnerablePower(abstractMonster, 2, false), 2));
            addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new EarthEssence(abstractPlayer, 2), 2));
        }
    }

    @Override
    public void accUse(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractPower p = abstractPlayer.getPower(EarthEssence.POWER_ID);
        if (p != null && p.amount > 0) {
            if (abstractMonster != null)
                addToBot(new VFXAction(new ClawEffect(abstractMonster.hb.cX, abstractMonster.hb.cY, Color.BLUE, Color.WHITE), 0.1F));
            addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
            addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, abstractPlayer.getPower(EarthEssence.POWER_ID), -1));
            addToBot(new DrawCardAction(1));
            if (abstractPlayer instanceof AbstractShadowversePlayer) {
                ((AbstractShadowversePlayer) abstractPlayer).earthCount++;
            }
        }
    }


    public AbstractCard makeCopy() {
        return new ArcticChimera();
    }
}

