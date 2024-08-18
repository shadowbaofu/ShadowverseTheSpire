package shadowverse.cards.Necromancer.Necromancy;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Necromancer;
import shadowverse.powers.Cemetery;


public class Atem
        extends CustomCard {
    public static final String ID = "shadowverse:Atem";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Atem");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Atem.png";

    public Atem() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.ATTACK, Necromancer.Enums.COLOR_PURPLE, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 28;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(7);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (this.upgraded){
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        }else {
            this.rawDescription = cardStrings.DESCRIPTION;
        }
        int necromance = 0;
        if (AbstractDungeon.player instanceof AbstractShadowversePlayer){
            necromance = ((AbstractShadowversePlayer)AbstractDungeon.player).necromanceCount;
        }
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + necromance + cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("Atem"));
        if (abstractMonster != null)
            addToBot(new VFXAction(new WeightyImpactEffect(abstractMonster.hb.cX, abstractMonster.hb.cY)));
        addToBot(new WaitAction(0.8F));
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        if (upgraded) {
            addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new Cemetery(abstractPlayer, 10), 10));
        }
        if (abstractPlayer instanceof AbstractShadowversePlayer) {
            if (((AbstractShadowversePlayer) abstractPlayer).necromanceCount >= 20) {
                addToBot(new GainEnergyAction(2));
            }
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new Atem();
    }
}

