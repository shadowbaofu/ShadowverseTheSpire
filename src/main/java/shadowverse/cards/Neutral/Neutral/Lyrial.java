package shadowverse.cards.Neutral.Neutral;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.relics.KagemitsuSword;

public class Lyrial extends CustomCard {
    public static final String ID = "shadowverse:Lyrial";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Lyrial.png";


    public Lyrial() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.BASIC, CardTarget.ENEMY);
        this.baseDamage = 6;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(AbstractShadowversePlayer.Enums.EVOLVEABLE);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeDamage(3);
            initializeDescription();
        }
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

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction(ID.replace("shadowverse:", "")));
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        if (this.upgraded) {
            if (abstractMonster != null)
                addToTop(new VFXAction(new ThrowDaggerEffect(abstractMonster.hb.cX, abstractMonster.hb.cY),0.2F));
            addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.magicNumber, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            this.degrade();
            if(abstractPlayer.hasRelic(KagemitsuSword.ID) || abstractPlayer.hasPower("shadowverse:SeofonPower")){
                this.upgrade();
            }
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new Lyrial();
    }
}


