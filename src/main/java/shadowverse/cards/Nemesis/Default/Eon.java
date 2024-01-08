package shadowverse.cards.Nemesis.Default;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import shadowverse.characters.Nemesis;

public class Eon extends CustomCard {
    public static final String ID = "shadowverse:Eon";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Eon");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Eon.png";

    public Eon() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.RARE, CardTarget.ALL);
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.exhaust = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("Eon"));
        int amount = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == CardType.ATTACK && c!=this){
                addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                amount++;
            }
        }
        addToBot(new GainEnergyAction(amount));
        addToBot(new ApplyPowerAction(p,p,new StrengthPower(p,amount),amount));
        addToBot(new ApplyPowerAction(p,p,new DexterityPower(p,amount),amount));
        if (amount > 1){
            addToBot(new DrawCardAction(3));
            for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                if (!mo.isDeadOrEscaped()){
                    addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, 2, false), 2, true, AbstractGameAction.AttackEffect.NONE));
                    addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, 2, false), 2, true, AbstractGameAction.AttackEffect.NONE));
                }
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Eon();
    }
}
