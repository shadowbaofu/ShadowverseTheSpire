package charbosses.cards.bishop;

import charbosses.cards.AbstractBossCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

public class EnArdentSister extends AbstractBossCard {
    public static final String ID = "shadowverse:EnArdentSister";

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:EnArdentSister");

    public static final String IMG_PATH = "img/cards/ArdentSister.png";

    public EnArdentSister() {
        super(ID, cardStrings.NAME, IMG_PATH, 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.COMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK_BUFF);
        this.baseDamage = 8;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new SFXAction("ArdentSister"));
        addToBot((AbstractGameAction)new DamageAction((AbstractCreature)p, new DamageInfo((AbstractCreature)m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                addToBot((AbstractGameAction)new ApplyPowerAction(mo,mo,(AbstractPower)new StrengthPower(mo,this.magicNumber),this.magicNumber));
            }
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(4);
            upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy() {
        return (AbstractCard)new EnArdentSister();
    }
}
