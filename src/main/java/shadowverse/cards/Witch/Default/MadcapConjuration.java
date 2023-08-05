package shadowverse.cards.Witch.Default;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Witchcraft;

public class MadcapConjuration extends CustomCard {
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:MadcapConjuration");
    public static final String ID = "shadowverse:MadcapConjuration";
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/MadcapConjuration.png";

    public MadcapConjuration() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, Witchcraft.Enums.COLOR_BLUE, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.baseDamage = 6;
        this.exhaust = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.exhaust = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int count = AbstractDungeon.player.hand.size();
        addToTop(new DiscardAction(abstractPlayer, abstractPlayer, count, true));
        int attackCount = 0;
        int skillCount = 0;
        int powerCount = 0;
        for (AbstractCard c : abstractPlayer.hand.group) {
            if (CardLibrary.getCard(c.cardID) != null && CardLibrary.getCard(c.cardID).type == CardType.ATTACK) {
                attackCount++;
                continue;
            }
            if (CardLibrary.getCard(c.cardID) != null && CardLibrary.getCard(c.cardID).type == CardType.SKILL) {
                skillCount++;
                continue;
            }
            if (CardLibrary.getCard(c.cardID) != null && CardLibrary.getCard(c.cardID).type == CardType.POWER) {
                powerCount++;
            }
        }
        if (attackCount >= 2) {
            for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                addToBot(new ApplyPowerAction(mo, abstractPlayer, new WeakPower(mo, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                addToBot(new ApplyPowerAction(mo, abstractPlayer, new VulnerablePower(mo, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            }
        }
        if (skillCount >= 2) {
            addToBot(new DrawCardAction(abstractPlayer, 5));
        }
        if (powerCount >= 2) {
            addToBot(new DamageAllEnemiesAction(abstractPlayer, DamageInfo.createDamageMatrix(this.damage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.LIGHTNING, true));
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new MadcapConjuration();
    }
}

