package shadowverse.cards.Neutral.Temp;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;
import shadowverse.characters.Dragon;


public class WhitefrostWhisper extends CustomCard {
    public static final String ID = "shadowverse:WhitefrostWhisper";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:WhitefrostWhisper");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/WhitefrostWhisper.png";

    public WhitefrostWhisper() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, Dragon.Enums.COLOR_BROWN, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.baseDamage = 6;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(1);
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("WhitefrostWhisper"));
        addToBot(new VFXAction(new BlizzardEffect(this.magicNumber + 1, AbstractDungeon.getMonsters().shouldFlipVfx()), 0.25F));
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        for (AbstractCard c : abstractPlayer.hand.group) {
            if (c.type == CardType.STATUS || c.type == CardType.CURSE) {
                addToBot(new ExhaustSpecificCardAction(c, abstractPlayer.hand));
            }
        }
        addToBot(new ApplyPowerAction(abstractMonster, abstractMonster, new WeakPower(abstractMonster, this.magicNumber, false), this.magicNumber));
        if (abstractMonster.currentHealth < abstractMonster.maxHealth) {
            addToBot(new GainEnergyAction(1));
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new WhitefrostWhisper();
    }
}

