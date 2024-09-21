package shadowverse.cards.Witch.Spellboost2;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Witchcraft;


public class GigantChimera
        extends CustomCard {
    public static final String ID = "shadowverse:GigantChimera";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:GigantChimera");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/GigantChimera.png";

    public GigantChimera() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.ATTACK, Witchcraft.Enums.COLOR_BLUE, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseDamage = 7;
        this.baseMagicNumber = 0;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(AbstractShadowversePlayer.Enums.SPELL_BOOST_ATTACK);
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c.type == CardType.SKILL) {
            flash();

            this.magicNumber = ++this.baseMagicNumber;
            addToBot(new SFXAction("spell_boost"));
        }
    }

    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.baseMagicNumber * this.baseDamage;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.baseMagicNumber * this.baseDamage;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        calculateCardDamage(abstractMonster);
        addToBot(new SFXAction("ATTACK_HEAVY"));
        addToBot(new VFXAction(abstractPlayer, new MindblastEffect(abstractPlayer.dialogX, abstractPlayer.dialogY, abstractPlayer.flipHorizontal), 0.1F));
        addToBot(new DamageAllEnemiesAction(abstractPlayer, DamageInfo.createDamageMatrix(this.damage, true), this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE, true));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new GigantChimera();
    }
}
