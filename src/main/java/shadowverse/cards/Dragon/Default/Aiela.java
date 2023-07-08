package shadowverse.cards.Dragon.Default;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.cards.Neutral.Temp.DevotedDragon;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Dragon;
import shadowverse.powers.OverflowPower;

import java.util.ArrayList;
import java.util.List;

public class Aiela extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:Aiela";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Aiela");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Aiela2");
    public static CardStrings cardStrings3 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Aiela3");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Aiela.png";
    public static final String IMG_PATH2 = "img/cards/Aiela2.png";
    public static final String IMG_PATH3 = "img/cards/Aiela3.png";

    public Aiela() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = 6;
        this.tags.add(AbstractShadowversePlayer.Enums.LASTWORD);
    }


    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        switch (chosenBranch()) {
            case 0:
                addToBot(new SFXAction("Aiela"));
                addToBot(new GainBlockAction(abstractPlayer,this.block));
                break;
            case 1:
                addToBot(new SFXAction("Aiela2"));
                addToBot(new GainBlockAction(abstractPlayer,this.block));
                addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new OverflowPower(abstractPlayer,1)));
                break;
            case 2:
                addToBot(new SFXAction("Aiela3"));
                addToBot(new GainBlockAction(abstractPlayer,this.block));
                addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new OverflowPower(abstractPlayer,1)));
                addToBot(new GainEnergyAction(1));
                if (EnergyPanel.getCurrentEnergy()-this.costForTurn>=1){
                    EnergyPanel.useEnergy(2);
                    addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
                }
                break;
        }
    }

    @Override
    public void triggerOnExhaust() {
        if (this.hasTag(AbstractShadowversePlayer.Enums.LASTWORD)){
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new OverflowPower(AbstractDungeon.player,1)));
        }
    }

    public AbstractCard makeCopy() {
        return (AbstractCard) new Aiela();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Aiela.this.timesUpgraded;
                Aiela.this.upgraded = true;
                Aiela.this.name = NAME + "+";
                Aiela.this.initializeTitle();
                Aiela.this.baseBlock = 9;
                Aiela.this.upgradedBlock = true;
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Aiela.this.timesUpgraded;
                Aiela.this.textureImg = IMG_PATH2;
                Aiela.this.loadCardImage(IMG_PATH2);
                Aiela.this.name = cardStrings2.NAME;
                Aiela.this.initializeTitle();
                Aiela.this.rawDescription = cardStrings2.DESCRIPTION;
                Aiela.this.initializeDescription();
                Aiela.this.rarity = CardRarity.UNCOMMON;
                Aiela.this.setDisplayRarity(Aiela.this.rarity);
                Aiela.this.tags.remove(AbstractShadowversePlayer.Enums.LASTWORD);
                Aiela.this.upgraded = true;
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Aiela.this.timesUpgraded;
                Aiela.this.textureImg = IMG_PATH3;
                Aiela.this.loadCardImage(IMG_PATH3);
                Aiela.this.name = cardStrings3.NAME;
                Aiela.this.initializeTitle();
                Aiela.this.rawDescription = cardStrings3.DESCRIPTION;
                Aiela.this.initializeDescription();
                Aiela.this.rarity = CardRarity.RARE;
                Aiela.this.setDisplayRarity(Aiela.this.rarity);
                Aiela.this.upgradeBaseCost(2);
                Aiela.this.cardsToPreview = new DevotedDragon();
                Aiela.this.tags.remove(AbstractShadowversePlayer.Enums.LASTWORD);
                Aiela.this.upgraded = true;
            }
        });
        return list;
    }
}

