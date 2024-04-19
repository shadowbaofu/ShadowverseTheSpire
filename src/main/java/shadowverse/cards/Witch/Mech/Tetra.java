package shadowverse.cards.Witch.Mech;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.cards.Neutral.Temp.AzureBlast;
import shadowverse.cards.Neutral.Temp.RepairMode;
import shadowverse.cards.Neutral.Temp.SonicFour;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Witchcraft;
import shadowverse.powers.SonicFourPower2;
import shadowverse.powers.Tetra4Power;

import java.util.ArrayList;
import java.util.List;


public class Tetra
        extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:Tetra";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Tetra");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Tetra2");
    public static CardStrings cardStrings3 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Tetra3");
    public static CardStrings cardStrings4 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Tetra4");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Tetra.png";
    public static final String IMG_PATH3 = "img/cards/Tetra3.png";
    public static final String IMG_PATH4 = "img/cards/Tetra4.png";

    public Tetra() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Witchcraft.Enums.COLOR_BLUE, CardRarity.RARE, CardTarget.SELF);
        this.baseBlock = 8;
        this.tags.add(AbstractShadowversePlayer.Enums.MACHINE);
        this.cardsToPreview = new SonicFour();
    }


    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, abstractPlayer, this.block));
        AbstractCard c = this.cardsToPreview.makeStatEquivalentCopy();
        switch (chosenBranch()) {
            case 0:
                addToBot(new SFXAction("Tetra"));
                addToBot(new MakeTempCardInHandAction(c, 1));
                addToBot(new MakeTempCardInHandAction(new RepairMode(), 1));
                break;
            case 1:
                addToBot(new SFXAction("Tetra2"));
                if (!abstractPlayer.hasPower(SonicFourPower2.POWER_ID))
                    addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new SonicFourPower2(abstractPlayer)));
                addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new ArtifactPower(abstractPlayer, 1), 1));
                break;
            case 2:
                addToBot(new SFXAction("Tetra3"));
                addToBot(new MakeTempCardInHandAction(c, 2));
                addToBot(new GainEnergyAction(1));
                break;
            case 3:
                addToBot(new SFXAction("Tetra4"));
                addToBot(new MakeTempCardInHandAction(new RepairMode(), 1));
                addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new Tetra4Power(abstractPlayer)));
                break;
            default:
                break;
        }
    }


    public AbstractCard makeCopy() {
        return new Tetra();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Tetra.this.timesUpgraded;
                Tetra.this.upgraded = true;
                Tetra.this.name = cardStrings.NAME + "+";
                Tetra.this.initializeTitle();
                Tetra.this.baseBlock = 10;
                Tetra.this.upgradedBlock = true;
                Tetra.this.cardsToPreview.upgrade();
                Tetra.this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                Tetra.this.initializeDescription();
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Tetra.this.timesUpgraded;
                Tetra.this.upgraded = true;
                Tetra.this.name = cardStrings2.NAME;
                Tetra.this.initializeTitle();
                Tetra.this.rawDescription = cardStrings2.DESCRIPTION;
                Tetra.this.initializeDescription();
                Tetra.this.baseBlock = 12;
                Tetra.this.upgradedBlock = true;
                Tetra.this.cardsToPreview = new SonicFour();
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Tetra.this.timesUpgraded;
                Tetra.this.upgraded = true;
                Tetra.this.textureImg = IMG_PATH3;
                Tetra.this.loadCardImage(IMG_PATH3);
                Tetra.this.name = cardStrings3.NAME;
                Tetra.this.initializeTitle();
                Tetra.this.rawDescription = cardStrings3.DESCRIPTION;
                Tetra.this.initializeDescription();
                Tetra.this.baseBlock = 6;
                Tetra.this.upgradedBlock = true;
                Tetra.this.cardsToPreview = new SonicFour();
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Tetra.this.timesUpgraded;
                Tetra.this.upgraded = true;
                Tetra.this.textureImg = IMG_PATH4;
                Tetra.this.loadCardImage(IMG_PATH4);
                Tetra.this.name = cardStrings4.NAME;
                Tetra.this.initializeTitle();
                Tetra.this.rawDescription = cardStrings4.DESCRIPTION;
                Tetra.this.initializeDescription();
                Tetra.this.baseBlock = 12;
                Tetra.this.upgradedBlock = true;
                Tetra.this.cardsToPreview = new AzureBlast();
            }
        });
        return list;
    }
}


