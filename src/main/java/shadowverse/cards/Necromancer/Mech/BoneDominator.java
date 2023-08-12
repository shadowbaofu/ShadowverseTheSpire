package shadowverse.cards.Necromancer.Mech;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.action.BurialAction;
import shadowverse.cards.Necromancer.Burial.DemonicProcession;
import shadowverse.cards.Necromancer.Burial.SpiritCurator;
import shadowverse.cards.Necromancer.Default.HungrySlash;
import shadowverse.cards.Necromancer.Ghosts.Ferry;
import shadowverse.cards.Necromancer.Shadows.HinterlandGhoul;
import shadowverse.cards.Necromancer.Burial.TheLovers;
import shadowverse.cards.Neutral.Temp.InstantPotion;
import shadowverse.cards.AbstractAccelerateCard;
import shadowverse.characters.Necromancer;
import shadowverse.powers.MementoPower;


public class BoneDominator
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:BoneDominator";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:BoneDominator");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/BoneDominator.png";

    public BoneDominator() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.ATTACK, Necromancer.Enums.COLOR_PURPLE, CardRarity.UNCOMMON, CardTarget.SELF, 0, CardType.SKILL);
        this.baseBlock = 18;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = new HinterlandGhoul();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(4);
            upgradeMagicNumber(2);
        }
    }


    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c instanceof DemonicProcession || c instanceof TheLovers || c instanceof HungrySlash || c instanceof SpiritCurator || c instanceof Ferry || c instanceof InstantPotion) {
            this.type = CardType.ATTACK;
            this.resetAttributes();
        }
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("BoneDominator"));
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("BoneDominator_Acc"));
        addToBot(new DrawCardAction(1));
        addToBot(new BurialAction(1, new GainBlockAction(p, this.magicNumber)));
        int attackAmt = 0;
        for (AbstractCard c : p.hand.group) {
            if (c != this && c.type == CardType.ATTACK)
                attackAmt++;
        }
        if (attackAmt >= 1) {
            if (p.hasPower(MementoPower.POWER_ID)) {
                addToBot(new GainBlockAction(p, this.magicNumber));
            }
        }
    }


    public AbstractCard makeCopy() {
        return new BoneDominator();
    }
}

