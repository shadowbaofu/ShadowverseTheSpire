package shadowverse.cards.Elf.Left;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import shadowverse.cards.Neutral.Temp.Fairy;
import shadowverse.characters.Elf;
import shadowverse.powers.BriarmaidenPower;


public class Briarmaiden extends CustomCard {
    public static final String ID = "shadowverse:Briarmaiden";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Briarmaiden");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Briarmaiden.png";

    public Briarmaiden() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Elf.Enums.COLOR_GREEN, CardRarity.RARE, CardTarget.SELF);
        this.baseBlock = 4;
        this.cardsToPreview = new Fairy();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(2);
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public void applyPowers() {
        super.applyPowers();
        int count = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.type == CardType.ATTACK && !(c.hasTag(CardTags.STRIKE)) && c.color == Elf.Enums.COLOR_GREEN) {
                count++;
            }
        }
        this.rawDescription = this.upgraded?cardStrings.UPGRADE_DESCRIPTION:cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + count;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("Briarmaiden"));
        addToBot(new GainBlockAction(abstractPlayer, this.block));
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new BriarmaidenPower(abstractPlayer,1),1));
        if (upgraded){
            addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(),2));
            int count = 0;
            for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
                if (c.type == CardType.ATTACK && !(c.hasTag(CardTags.STRIKE)) && c.color == Elf.Enums.COLOR_GREEN) {
                    count++;
                }
            }
            if (count > 10) {
                addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new ThornsPower(abstractPlayer,1),1));
            }
        }
    }


    public AbstractCard makeCopy() {
        return new Briarmaiden();
    }
}
