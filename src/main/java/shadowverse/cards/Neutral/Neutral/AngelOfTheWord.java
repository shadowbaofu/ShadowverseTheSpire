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

public class AngelOfTheWord extends CustomCard {
    public static final String ID = "shadowverse:AngelOfTheWord";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/AngelOfTheWord.png";


    public AngelOfTheWord() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.BASIC, CardTarget.ENEMY);
        this.baseDamage = 8;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeDamage(3);
        }
    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction(ID.replace("shadowverse:", "")));
        addToTop(new VFXAction(new ThrowDaggerEffect(abstractMonster.hb.cX, abstractMonster.hb.cY),0.2F));
        this.addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.LIGHTNING));
    }


    @Override
    public AbstractCard makeCopy() {
        return new AngelOfTheWord();
    }
}


