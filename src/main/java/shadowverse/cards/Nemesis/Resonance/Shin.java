package shadowverse.cards.Nemesis.Resonance;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Nemesis;

public class Shin
        extends CustomCard {
    public static final String ID = "shadowverse:Shin";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Shin");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Shin.png";

    public Shin() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.RARE, CardTarget.SELF);
        this.baseBlock = 6;
        this.baseDamage = 36;
        this.baseMagicNumber = 36;
        this.magicNumber = this.baseMagicNumber;
    }



    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
            upgradeDamage(6);
        }
    }

    public void applyPowers() {
        super.applyPowers();
        int count = 0;
        if (AbstractDungeon.player instanceof AbstractShadowversePlayer)
            count = ((AbstractShadowversePlayer) AbstractDungeon.player).resonanceCount;
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + count;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("Shin"));
        addToBot(new VFXAction(new BorderFlashEffect(Color.GOLD, true)));
        addToBot(new VFXAction(p, new IntenseZoomEffect(p.hb.cX, p.hb.cY, false), 0.5F, true));
        addToBot(new GainBlockAction(p,this.block));
        if (p instanceof AbstractShadowversePlayer) {
            int resonanceCount = ((AbstractShadowversePlayer) p).resonanceCount;
            if (resonanceCount >= 5){
                for (AbstractMonster m :AbstractDungeon.getCurrRoom().monsters.monsters){
                    if (m!=null && !m.isDeadOrEscaped()){
                        addToBot(new ApplyPowerAction(m,p,new VulnerablePower(m,2,false),2));
                        addToBot(new ApplyPowerAction(m,p,new WeakPower(m,2,false),2));
                    }
                }
            }
            if (resonanceCount >= 10){
                addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(this.damage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
            }
            if (resonanceCount >= 15){
                addToBot(new GainBlockAction(p,this.magicNumber));
            }
            if (resonanceCount >= 20){
                for (AbstractCard c : p.hand.group){
                    addToBot(new ReduceCostForTurnAction(c,1));
                }
            }
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new Shin();
    }
}


