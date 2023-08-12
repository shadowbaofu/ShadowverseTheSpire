package shadowverse.cards.Witch.Earth2;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.action.BlockPerCardAction;
import shadowverse.cards.Neutral.Temp.VeridicRitual;
import shadowverse.cards.AbstractAccelerateCard;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Witchcraft;
import shadowverse.powers.EarthEssence;


public class Clarke
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:Clarke";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Clarke");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Clarke.png";


    public Clarke() {
        super(ID, NAME, IMG_PATH, 5, DESCRIPTION, CardType.ATTACK, Witchcraft.Enums.COLOR_BLUE, CardRarity.UNCOMMON, CardTarget.SELF, 0, CardType.SKILL);
        this.baseBlock = 3;
        this.tags.add(AbstractShadowversePlayer.Enums.EARTH_RITE);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = new VeridicRitual();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(2);
        }
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("Clarke"));
        addToBot(new GainEnergyAction(5));
        boolean powerExists = p.hasPower(EarthEssence.POWER_ID);
        if (powerExists) {
            if (p instanceof AbstractShadowversePlayer) {
                ((AbstractShadowversePlayer) p).earthCount++;
            }
            addToBot(new BlockPerCardAction(this.block));
            addToBot(new ExpertiseAction(p, 10));
            addToBot(new ApplyPowerAction(p, p, new EarthEssence(p, -this.magicNumber), -this.magicNumber));
        }
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("Clarke_Accelerate"));
        addToBot(new ApplyPowerAction(p, p, new EarthEssence(p, 1), 1));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), 1));
    }

    public AbstractCard makeCopy() {
        return new Clarke();
    }
}

