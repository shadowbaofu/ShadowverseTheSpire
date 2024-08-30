package shadowverse.cards.Neutral.Temp;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Witchcraft;


public class WordsOfSanction
        extends CustomCard {
    public static final String ID = "shadowverse:WordsOfSanction";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:WordsOfSanction");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/JudgmentWord.png";

    public WordsOfSanction() {
        super(ID, NAME, IMG_PATH, 4, DESCRIPTION, CardType.SKILL, Witchcraft.Enums.COLOR_BLUE, CardRarity.SPECIAL, CardTarget.ALL_ENEMY);
        this.selfRetain = true;
        this.exhaust = true;
        this.tags.add(AbstractShadowversePlayer.Enums.SPELL_BOOST);
    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c.type == CardType.SKILL) {
            flash();
            addToBot(new ReduceCostAction(this));
            addToBot(new SFXAction("spell_boost"));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(3);
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("WordsOfSanction"));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo != null && !mo.isDeadOrEscaped()) {
                if (mo.hasPower("Artifact")) {
                    addToBot(new RemoveSpecificPowerAction(mo, abstractPlayer, "Artifact"));
                } else {
                    for (AbstractPower pow : mo.powers) {
                        if (pow.type == AbstractPower.PowerType.BUFF && pow.ID != "Invincible" && pow.ID != "Mode Shift" && pow.ID != "Split" && pow.ID != "Unawakened" && pow.ID != "Life Link" && pow.ID != "Fading" && pow.ID != "Stasis" && pow.ID != "Minion" && pow.ID != "Shifting" && pow.ID != "shadowverse:chushouHealPower") {
                            addToBot(new RemoveSpecificPowerAction(pow.owner, abstractPlayer, pow.ID));
                        }
                    }
                }
            }
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new WordsOfSanction();
    }
}

