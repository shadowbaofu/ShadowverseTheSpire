package shadowverse.cards.Witch.Natura;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.cards.Neutral.Temp.HasteningConviction;
import shadowverse.cards.Neutral.Temp.Horse;
import shadowverse.cards.Neutral.Temp.RapidFire;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Witchcraft;

import java.util.ArrayList;
import java.util.List;


public class Maiser
        extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:Maiser";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Maiser");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Maiser2");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Maiser.png";

    public Maiser() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Witchcraft.Enums.COLOR_BLUE, CardRarity.RARE, CardTarget.NONE);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = new RapidFire();
        this.tags.add(AbstractShadowversePlayer.Enums.HERO);
    }


    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        switch (chosenBranch()){
            case 0:
                addToBot(new SFXAction("Maiser"));
                AbstractCard c = this.cardsToPreview.makeStatEquivalentCopy();
                AbstractCard a = new Horse();
                addToBot(new MakeTempCardInHandAction(a, 1));
                addToBot(new MakeTempCardInHandAction(c, this.magicNumber));
                break;
            case 1:
                addToBot(new SFXAction("Maiser2"));
                AbstractCard h = this.cardsToPreview.makeStatEquivalentCopy();
                addToBot(new MakeTempCardInHandAction(h, 2));
                if (this.magicNumber >= 3){
                    addToBot(new MakeTempCardInHandAction(new HasteningConviction()));
                }
                break;
        }
    }


    public AbstractCard makeCopy() {
        return new Maiser();
    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c.type == CardType.SKILL && this.chosenBranch() == 1) {
            flash();
            this.magicNumber = ++this.baseMagicNumber;
            addToBot(new SFXAction("spell_boost"));
        }
    }

    public void applyPowers() {
        super.applyPowers();
        if (chosenBranch() == 1){
            int count = this.magicNumber;
            this.rawDescription = cardStrings2.DESCRIPTION;
            this.rawDescription += cardStrings2.EXTENDED_DESCRIPTION[0] + count;
            this.rawDescription += cardStrings2.EXTENDED_DESCRIPTION[1];
            initializeDescription();
        }
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Maiser.this.timesUpgraded;
                Maiser.this.upgraded = true;
                Maiser.this.name = cardStrings.NAME + "+";
                Maiser.this.initializeTitle();
                Maiser.this.baseMagicNumber = 3;
                Maiser.this.magicNumber = Maiser.this.baseMagicNumber;
                Maiser.this.upgradedMagicNumber = true;
                Maiser.this.initializeDescription();
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Maiser.this.timesUpgraded;
                Maiser.this.upgraded = true;
                Maiser.this.name = cardStrings2.NAME;
                Maiser.this.initializeTitle();
                Maiser.this.baseMagicNumber = 0;
                Maiser.this.magicNumber = Maiser.this.baseMagicNumber;
                Maiser.this.upgradedMagicNumber = true;
                Maiser.this.tags.add(AbstractShadowversePlayer.Enums.SPELL_BOOST_ATTACK);
                Maiser.this.cardsToPreview = new HasteningConviction();
                Maiser.this.rawDescription = cardStrings2.DESCRIPTION;
                Maiser.this.initializeDescription();
            }
        });
        return list;
    }
}

