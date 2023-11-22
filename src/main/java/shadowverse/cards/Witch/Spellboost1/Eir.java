package shadowverse.cards.Witch.Spellboost1;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import shadowverse.action.ReanimateAction;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Witchcraft;


public class Eir
        extends CustomCard {
    public static final String ID = "shadowverse:Eir";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Eir");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Eir.png";

    public Eir() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Witchcraft.Enums.COLOR_BLUE, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = 0;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(AbstractShadowversePlayer.Enums.SPELL_BOOST_ATTACK);
        this.exhaust = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.selfRetain = true;
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


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        for (AbstractCard c : abstractPlayer.hand.group) {
            if (c.hasTag(AbstractShadowversePlayer.Enums.SPELL_BOOST)) {
                for (int i = 0; i < 2; i++) {
                    c.flash();
                    addToBot(new SFXAction("spell_boost"));
                    addToBot(new ReduceCostAction(c));
                }
                continue;
            }
            if (c.hasTag(AbstractShadowversePlayer.Enums.SPELL_BOOST_ATTACK) && c != this) {
                for (int i = 0; i < 2; i++) {
                    c.flash();
                    c.magicNumber = ++c.baseMagicNumber;
                    addToBot(new SFXAction("spell_boost"));
                }
            }
        }
        addToBot(new SFXAction("Eir"));
        addToBot(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));
        addToBot(new ReanimateAction(this.magicNumber / 2));
        if (this.magicNumber >= 5) {
            addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new BufferPower(abstractPlayer, 1), 1));
        }
    }


    public void applyPowers() {
        super.applyPowers();
        int count = this.magicNumber;
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + count;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }


    public AbstractCard makeCopy() {
        return new Eir();
    }
}

